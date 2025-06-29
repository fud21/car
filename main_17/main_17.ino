#include <WiFi.h>
#include <esp_now.h>
#include <HTTPClient.h>

// WiFi
const char* ssid = "iptime_cclab";
const char* password = "cclab1511";
// Spring Boot 서버 주소
const char* serverUrl = "http://192.168.0.162:8080/api/vehicle/entry";

// 송신된 구역 정보
typedef struct struct_message {
  char zone[10];
  int rssi;
} struct_message;

struct_message incomingData;

// 📦 최근 수신된 구역들 & RSSI 저장
String zones[3];
int rssis[3];
int zoneIndex = 0;

void sendToServer(String zone){
  if(WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(serverUrl);
    http.addHeader("Content-Type", "application/json");

    String jsonData = "{\"plateNumber\":\"137하7288\","
                      "\"registered\":false,"
                      "\"location\":\"" + zone + "\","
                      "\"lastSeen\":\"2025-05-24T20:00:00\"}";
    
    int responseCode = http.POST(jsonData);

    if(responseCode > 0 ) {
      Serial.println("서버 응답 : " + http.getString());
    } else {
      Serial.println("전송 실패, 코드: " + String(responseCode));
    }
    
    http.end();
  } else {
    Serial.println("Wi-Fi 연결 안됨");
  }
}

void OnDataRecv(const esp_now_recv_info_t *recv_info, const uint8_t *incomingDataBytes, int len) {
  struct_message incomingData;
  memcpy(&incomingData, incomingDataBytes, sizeof(incomingData));

  Serial.print("📩 수신됨 → ");
  Serial.print(incomingData.zone);
  Serial.print(" | RSSI: ");
  Serial.println(incomingData.rssi);

  // 저장
  zones[zoneIndex] = String(incomingData.zone);
  rssis[zoneIndex] = incomingData.rssi;
  zoneIndex++;

  // 3개 다 받으면 비교 후 출력
  if (zoneIndex >= 3) {
    int maxRSSI = -999;
    String closestZone = "";
    for (int i = 0; i < 3; i++) {
      if (rssis[i] > maxRSSI) {
        maxRSSI = rssis[i];
        closestZone = zones[i];
      }
    }

    Serial.println("🚗 차량 현재 위치 → " + closestZone + " (RSSI: " + String(maxRSSI) + ")");
    Serial.println("----------------------------------");

    // 서버로 POST 요청
    sendToServer(closestZone);

    // 다시 받을 수 있도록 초기화
    zoneIndex = 0;
  }
}

void setup() {
  Serial.begin(115200);

  // Wi-Fi 연결 추가
  //WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);    // disconnect
  Serial.print("WiFi 연결 중");
  
  while(WiFi.status() != WL_CONNECTED){
    delay(1000);
    Serial.print(".");
  }
  Serial.println("\n Wi-Fi 연결 완료");
  Serial.println(WiFi.localIP());

  if (esp_now_init() != ESP_OK) {
    Serial.println("ESP-NOW 초기화 실패");
    return;
  }

  esp_now_register_recv_cb(OnDataRecv);

  Serial.print("📡 중앙 ESP MAC: ");
  Serial.println(WiFi.macAddress());
  Serial.println("중앙 리시버 시작됨 ✅");
}

void loop() {
  delay(100);
}

#include <WiFi.h>
#include <esp_now.h>

typedef struct struct_message {
  char zone[10];
  int rssi;
} struct_message;

struct_message incomingData;

// 📦 최근 수신된 구역들 & RSSI 저장
String zones[3];
int rssis[3];
int zoneIndex = 0;

void OnDataRecv(const uint8_t * mac, const uint8_t *incomingDataBytes, int len) {
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

    // 다시 받을 수 있도록 초기화
    zoneIndex = 0;
  }
}

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();

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

#include <WiFi.h>
#include <esp_now.h>
#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEScan.h>
#include <BLEAdvertisedDevice.h>

BLEScan* pBLEScan;
int scanTime = 5;

String targetMAC = "f4:65:0b:e7:25:1a";  // 차량 BLE MAC 주소
String myZone = "C구역";  // ← B구역, C구역으로 바꿔줘

// 전송할 구조체 정의
typedef struct struct_message {
  char zone[10];
  int rssi;
} struct_message;

struct_message outgoingData;

uint8_t broadcastAddress[] = {0xEC, 0xE3, 0x34, 0x88, 0x8B, 0x08};
// 중앙 ESP의 MAC 주소로 바꿔줘!

void OnDataSent(const uint8_t *mac_addr, esp_now_send_status_t status) {
  Serial.println(status == ESP_NOW_SEND_SUCCESS ? "전송 성공 ✅" : "전송 실패 ❌");
}

class MyAdvertisedDeviceCallbacks: public BLEAdvertisedDeviceCallbacks {
  void onResult(BLEAdvertisedDevice advertisedDevice) {
    String foundMAC = advertisedDevice.getAddress().toString().c_str();
    int rssi = advertisedDevice.getRSSI();

    if (foundMAC.equalsIgnoreCase(targetMAC)) {
      Serial.print("📡 차량 감지 @ ");
      Serial.print(myZone);
      Serial.print(" | RSSI: ");
      Serial.println(rssi);

      strcpy(outgoingData.zone, myZone.c_str());
      outgoingData.rssi = rssi;

      esp_err_t result = esp_now_send(broadcastAddress, (uint8_t *) &outgoingData, sizeof(outgoingData));
    }
  }
};

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.disconnect();

  // ESP-NOW 초기화
  if (esp_now_init() != ESP_OK) {
    Serial.println("ESP-NOW 초기화 실패");
    return;
  }

  esp_now_register_send_cb(OnDataSent);
  esp_now_peer_info_t peerInfo = {};
  memcpy(peerInfo.peer_addr, broadcastAddress, 6);
  peerInfo.channel = 0;  
  peerInfo.encrypt = false;

  if (esp_now_add_peer(&peerInfo) != ESP_OK) {
    Serial.println("페어링 실패");
    return;
  }

  // BLE 초기화
  BLEDevice::init("");
  pBLEScan = BLEDevice::getScan();
  pBLEScan->setAdvertisedDeviceCallbacks(new MyAdvertisedDeviceCallbacks());
  pBLEScan->setActiveScan(true);
  pBLEScan->setInterval(100);
  pBLEScan->setWindow(99);

  Serial.println("스캐너 시작: " + myZone);
}

void loop() {
  pBLEScan->start(scanTime, false);
  delay(1000);
}

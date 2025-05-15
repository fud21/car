#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>

void setup() {
  Serial.begin(115200);
  BLEDevice::init("CAR_TAG");

  BLEServer *pServer = BLEDevice::createServer();
  BLEAdvertising *pAdvertising = BLEDevice::getAdvertising();
  pAdvertising->start();

  Serial.print("📡 광고 시작, MAC: ");
  Serial.println(BLEDevice::getAddress().toString().c_str());
}

void loop() {
  delay(1000);  // nothing to do
}

#include <Arduino.h>
#include "servers.h"

//Raw data string creator
String sendRAW(float temperature,float humidity,float pressure,float altitude, float wifiStrength){
  String ptr = "[";
  ptr +=sizeof(temperature);
  ptr +=",";
  ptr +=sizeof(humidity);
  ptr +=",";
  ptr +=sizeof(pressure);
  ptr +=",";
  ptr +=altitude;
  ptr +=",";
  ptr +=wifiStrength;
  ptr +="]";
  return ptr;
}

//HTML Creator
String sendHTML(float temperature,float humidity,float pressure,float altitude, float wifiStrength){
  String ptr = "<!DOCTYPE html> <html>\n";
  ptr +="<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">\n";
  ptr +="<meta http-equiv=\"refresh\" content=\"5\">\n";
  ptr +="<title>ESP8266 Weather Station</title>\n";
  ptr +="<style>html { font-family: Helvetica; display: inline-block; margin: 0px auto; text-align: center;}\n";
  ptr +="body{margin-top: 50px;} h1 {color: #444444;margin: 50px auto 30px;}\n";
  ptr +="p {font-size: 24px;color: #444444;margin-bottom: 10px;}\n";
  ptr +="</style>\n";
  ptr +="</head>\n";
  ptr +="<body>\n";
  ptr +="<div id=\"webpage\">\n";
  ptr +="<h1>ESP8266 Weather Station</h1>\n";
  ptr +="<p>Temperature: ";
  ptr +=temperature;
  ptr +="&deg;C</p>";
  ptr +="<p>Humidity: ";
  ptr +=humidity;
  ptr +="%</p>";
  ptr +="<p>Pressure: ";
  ptr +=pressure;
  ptr +="hPa</p>";
  ptr +="<p>Altitude: ";
  ptr +=altitude;
  ptr +="m</p>";
  ptr +="<p>WIFI strength: ";
  ptr +=wifiStrength;
  ptr +="</p>";
  ptr +="</div>\n";
  ptr +="</body>\n";
  ptr +="</html>\n";
  return ptr;
}

//Create output webpage
void outputHTML() {
  digitalWrite(LED_BUILTIN, LOW);
  delay(200);
  digitalWrite(LED_BUILTIN, HIGH);
  temperature = bme.readTemperature();
  humidity = bme.readHumidity();
  pressure = (bme.readPressure() / 100.0f) + 10.44f;
  altitude = bme.readAltitude(pressure);
  wifiStrength = WiFi.RSSI();
  server.send(200, "text/html", sendHTML(temperature, humidity, pressure, altitude, wifiStrength)); 
}

//Create output RAW data
void outputRAW() {
  temperature = bme.readTemperature();
  humidity = bme.readHumidity();
  pressure = bme.readPressure() / 100.0F;
  altitude = bme.readAltitude(SEALEVELPRESSURE_HPA);
  wifiStrength = WiFi.RSSI();
  server.send(200, "text/plain", sendRAW(temperature, humidity, pressure, altitude, wifiStrength)); 
}

//Notfound page
void outputNotFound(){
  server.send(404, "text/plain", "Not found");
}
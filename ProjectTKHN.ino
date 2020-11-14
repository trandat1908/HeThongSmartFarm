#include "FirebaseESP8266.h"  
#include <ESP8266WiFi.h>
#include <DHT.h>    


#define FIREBASE_HOST "projecttkhn.firebaseio.com" 
#define FIREBASE_AUTH "HGjYsUwIe1wIpo9Xp7YXJMOiLBjJyJzDcQr3bvSY"
#define WIFI_SSID "FPT Telecom"
#define WIFI_PASSWORD "12345678"

#define DHTPIN 2    
#define led  D5      
#define led1  D1
#define led2 D2
#define led3  D3

#define DHTTYPE    DHT11
DHT dht(DHTPIN, DHTTYPE);

String apiKey = "MCQXAFCMLS1XX45C";       

const char* server = "api.thingspeak.com";

WiFiClient client;


//Define FirebaseESP8266 data object
FirebaseData firebaseData;
FirebaseData ledData;
FirebaseData ledData1;
FirebaseData ledData2;
FirebaseData ledData3;
FirebaseJson json;


void setup()
{

  Serial.begin(9600);

  dht.begin();
  pinMode(led,OUTPUT);
  pinMode(led1,OUTPUT);
  pinMode(led2,OUTPUT);
  pinMode(led3,OUTPUT);
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);

}
float doAmDat(){
  float moisture_value = analogRead(A0);
  float value_moi = moisture_value/10;
  return value_moi;
}

void sensorUpdate(){

  float h = dht.readHumidity();

  float t = dht.readTemperature();

  float f = dht.readTemperature(true);

  float moisture_value = analogRead(A0);
  float value_moi = map(moisture_value, 0 , 1023, 0, 100);
  value_moi = 100 - value_moi;


  

  if (isnan(h) || isnan(t) || isnan(f)||isnan(value_moi)) {
    Serial.println(F("Failed to read from sensor!"));
    return;
  }
  if(digitalRead(D0)==1){
    Serial.print("Toi");
    Firebase.setString(firebaseData, "/Sensor/light","Trời tối");
  }
  else{
    Serial.print("Sang");
    Firebase.setString(firebaseData,"/Sensor/light","Trời sáng");
  }

  Serial.print(F("Humidity: "));
  Serial.print(h);
  Serial.print(F("%  Temperature: "));
  Serial.print(t);
  Serial.print(F("% Moisture: "));
  Serial.print(moisture_value);
  Serial.print(F("°C  ,"));
  Serial.print(f);
  Serial.println(F("°F  "));
  

  if (Firebase.setInt(firebaseData, "/Sensor/temperature", t))
  {
    Serial.println("PASSED");
    Serial.println("PATH: " + firebaseData.dataPath());
    Serial.println("TYPE: " + firebaseData.dataType());
    Serial.println("ETag: " + firebaseData.ETag());
    Serial.println("------------------------------------");
    Serial.println();
  }
  else
  {
    Serial.println("FAILED");
    Serial.println("REASON: " + firebaseData.errorReason());
    Serial.println("------------------------------------");
    Serial.println();
  }

  if (Firebase.setInt(firebaseData, "/Sensor/humidity", h))
  {
    Serial.println("PASSED");
    Serial.println("PATH: " + firebaseData.dataPath());
    Serial.println("TYPE: " + firebaseData.dataType());
    Serial.println("ETag: " + firebaseData.ETag());
    Serial.println("------------------------------------");
    Serial.println();
  }
  else
  {
    Serial.println("FAILED");
    Serial.println("REASON: " + firebaseData.errorReason());
    Serial.println("------------------------------------");
    Serial.println();
  }
   if (Firebase.setInt(firebaseData, "/Sensor/soilmoisture", value_moi))
  {
    Serial.println("PASSED");
    Serial.println("PATH: " + firebaseData.dataPath());
    Serial.println("TYPE: " + firebaseData.dataType());
    Serial.println("ETag: " + firebaseData.ETag());
    Serial.println("------------------------------------");
    Serial.println();
  }
  else
  {
    Serial.println("FAILED");
    Serial.println("REASON: " + firebaseData.errorReason());
    Serial.println("------------------------------------");
    Serial.println();
  }
}
void control(){
  if (Firebase.getString(ledData, "/Control/fan")){
    Serial.println(ledData.stringData());
    if (ledData.stringData() == "1") {
    digitalWrite(led, HIGH);
    }
  else if (ledData.stringData() == "0"){
    digitalWrite(led, LOW);
    }
  }
  if (Firebase.getString(ledData1, "/Control/mis")){
    Serial.println(ledData1.stringData());
    if (ledData1.stringData() == "1") {
    digitalWrite(led1, HIGH);
    }
  else if (ledData1.stringData() == "0"){
    digitalWrite(led1, LOW);
    }
  }
  if (Firebase.getString(ledData2, "/Control/lamp")){
    Serial.println(ledData2.stringData());
    if (ledData2.stringData() == "1") {
    digitalWrite(led2, HIGH);
    }
  else if (ledData2.stringData() == "0"){
    digitalWrite(led2, LOW);
    }
  }
  if (Firebase.getString(ledData3, "/Control/pump")){
    Serial.println(ledData3.stringData());
    if (ledData3.stringData() == "1") {
    digitalWrite(led3, HIGH);
    }
  else if (ledData3.stringData() == "0"){
    digitalWrite(led3, LOW);
    }
  }
  
  }
void loop() {
  sensorUpdate();
  control();
   
  float t = dht.readTemperature();
  float h = dht.readHumidity();
  float d = doAmDat(); 
   if (client.connect(server,80))   //   "184.106.153.149" or api.thingspeak.com
              {  
                    
                     String postStr = apiKey;
                     String body = "field1=" + String(t, 1) + "&field2=" + String(h, 1) + "&field3=" + String(d, 1);                   

                     client.print("POST /update HTTP/1.1\n");
                     client.print("Host: api.thingspeak.com\n");
                     client.print("Connection: close\n");
                     client.print("X-THINGSPEAKAPIKEY: "+apiKey+"\n");
                     client.print("Content-Type: application/x-www-form-urlencoded\n");
                     client.print("Content-Length: ");
                     client.print(body.length());
                     client.print("\n\n");
                     client.print(body);

                     Serial.print("Temperature: ");
                     Serial.print(t);
                     Serial.print(" degrees Celcius, Humidity: ");
                     Serial.print(h);
                     Serial.println("%. Send to Thingspeak.");
                }
}

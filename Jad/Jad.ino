
#include <ESP32Servo.h>
#include <Keypad.h>
#include <SPI.h>
#include <MFRC522.h>
#include <LiquidCrystal_I2C.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <WiFiClient.h>





//----------------------------------------SSID and Password of your WiFi router.
const char* ssid = "Test"; //--> Your wifi name or  SSID.
const char* pass = "123456789"; //--> Your wifi password.
//----------------------------------------

//----------------------------------------Web Server address / IPv4
// If using IPv4, press Windows key + R then type cmd, then type ipconfig (If using Windows OS).
// String host_or_IPv4 = "http://Your_Host_or_IP";
// Example :
// String host_or_IPv4 = "http://192.168.0.0/";
String host_or_IPv4 = "http://yourportablehouse.000webhostapp.com/";

String Destination = "";
String URL_Server = "";
//----------------------------------------  

//----------------------------------------
String getData = "";
String payloadGet = "";
//----------------------------------------

//----------------------------------------
HTTPClient http; //--> Declare object of class HTTPClient
WiFiClient client;
//----------------------------------------






// LCD setup
LiquidCrystal_I2C lcd(0x27, 16, 2); // Adjust the address if needed

constexpr uint8_t RST_PIN = 4;     
constexpr uint8_t SS_PIN = 5; 
MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class
MFRC522::MIFARE_Key key;
String tag;
#define ON_Board_LED 2 
Servo ServoMotor;
const char* password = "153";  // change the password here, just pick any 3 numbers
int position = 0;
double counter = 0;
const byte ROWS = 4;
const byte COLS = 4;
char keys[ROWS][COLS] = {
{'1','2','3','A'},
{'4','5','6','B'},
{'7','8','9','C'},
{'*','0','#','D'}
};

byte rowPins[ROWS] = {13, 12, 14, 27};
byte colPins[COLS] = {33, 32, 25, 26};
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );
int RedpinLock = 12;
int GreenpinUnlock = 13;

void setup()
{
  Serial.begin(115200);
  delay(500);

  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, pass); //--> Connect to your WiFi router
  Serial.println("");

  //----------------------------------------Wait for connection
  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");

  }
  //----------------------------------------

  //----------------------------------------If successfully connected to the wifi router, the IP Address that will be visited is displayed in the serial monitor
  Serial.println("");
  Serial.print("Successfully connected to : ");
  Serial.println(ssid);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
  Serial.println();
  //----------------------------------------

  delay(2000);
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("Hello, please");
  lcd.setCursor(0, 1);
  lcd.print("swipe the card!");
  ServoMotor.attach(15);
  LockedPosition(true);
  SPI.begin(); // Init SPI bus
  rfid.PCD_Init(); // Init MFRC522
  pinMode(17, OUTPUT);
  pinMode(ON_Board_LED, OUTPUT);
}

void loop()
{

  lcd.setCursor(0, 1);

  if (counter >= 150){
    
  int id = 1; //--> ID in Database 
  getData = "ID=" + String(id);
  Destination = "postServoRead.php";
  URL_Server = host_or_IPv4 + Destination;
  Serial.println("----------------Connect to Server-----------------");
  Serial.println("Get LED Status from Server or Database");
  Serial.print("Request Link : ");
  Serial.println(URL_Server);
  http.begin(client, URL_Server); //--> Specify request destination
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");    //Specify content-type header
  int httpCodeGet = http.POST(getData); //--> Send the request
  payloadGet = http.getString(); //--> Get the response payload from server
  Serial.print("Response Code : "); //--> If Response Code = 200 means Successful connection, if -1 means connection failed. For more information see here : https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
  Serial.println(httpCodeGet); //--> Print HTTP return code
  Serial.print("Returned data from Server : ");
  Serial.println(payloadGet); //--> Print request response payload

  if (payloadGet == "1") {
    ServoMotor.write(90);
  }
  if (payloadGet == "2") {
    ServoMotor.write(0);
  }
  
  Destination = "postLedRead.php";
  URL_Server = host_or_IPv4 + Destination;
  Serial.println("----------------Connect to Server-----------------");
  Serial.println("Get LED Status from Server or Database");
  Serial.print("Request Link : ");
  Serial.println(URL_Server);
  http.begin(client, URL_Server); //--> Specify request destination
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");    //Specify content-type header
  httpCodeGet = http.POST(getData); //--> Send the request
  payloadGet = http.getString(); //--> Get the response payload from server
  Serial.print("Response Code : "); //--> If Response Code = 200 means Successful connection, if -1 means connection failed. For more information see here : https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
  Serial.println(httpCodeGet); //--> Print HTTP return code
  Serial.print("Returned data from Server : ");
  Serial.println(payloadGet); //--> Print request response payload


  if (payloadGet == "1") {
    digitalWrite(ON_Board_LED, HIGH); //--> Turn on Led
  }
  if (payloadGet == "2") {
    digitalWrite(ON_Board_LED, LOW); //--> Turn off Led
  }



  Serial.println("----------------Closing Connection----------------");
  http.end(); //--> Close connection
  Serial.println();
  Serial.println("Please wait 10 seconds for the next connection.");
  Serial.println();
  //delay(6000);
  counter = 0;
  }
counter ++;




  

  char key = keypad.getKey();
  if (key == '*' || key == '#')
{
    position = 0;
    LockedPosition(true);
    lcd.clear();
    lcd.print("Hello, please");
    lcd.setCursor(0, 1);
    lcd.print("swipe the card!");
}

  if (key == password[position])
{
    position ++;
}
  if (position == 3)
{
    LockedPosition(false);
    lcd.clear();
    lcd.print("Access Granted!");
    delay(3500);
    lcd.clear();
    lcd.print("Hello, please");
    lcd.setCursor(0, 1);
    lcd.print("swipe the card!");


}

  if(key == 'A'){
    return;
  }

  if ( ! rfid.PICC_IsNewCardPresent())
    return;

  if (rfid.PICC_ReadCardSerial()) {
  for (byte i = 0; i < 4; i++) {
    tag += rfid.uid.uidByte[i];
  }
  Serial.println(tag);

  if (tag == "671498622")
{
    LockedPosition(false);
    Serial.println("Access Granted!");
    lcd.clear();
    lcd.print("Access Granted!");
    digitalWrite(17, HIGH);
    delay(100);
    digitalWrite(17, LOW);
    delay(100);
    digitalWrite(17, HIGH);
    delay(100);
    digitalWrite(17, LOW);
    delay(100);
    digitalWrite(17, HIGH);
    delay(100);
    digitalWrite(17, LOW);
    delay(100);
    delay(3500);
    lcd.clear();
    lcd.print("Hello, please");
    lcd.setCursor(0, 1);
    lcd.print("swipe the card!");


} else {
    Serial.println("Access Denied!");
    lcd.clear();
    lcd.print("Access Denied!");
    digitalWrite(17, HIGH);
    delay(2000);
    digitalWrite(17, LOW);
    delay(1500);
    lcd.clear();
    lcd.print("Hello, please");
    lcd.setCursor(0, 1);
    lcd.print("swipe the card!");
    
  }
}
  tag = "";
  rfid.PICC_HaltA();
  rfid.PCD_StopCrypto1();

delay(100);


} 

void LockedPosition(int locked)
{
  if (locked)
{
    digitalWrite(RedpinLock, HIGH);
    digitalWrite(GreenpinUnlock, LOW);
    ServoMotor.write(0);
}
else
{
    digitalWrite(RedpinLock, LOW);
    digitalWrite(GreenpinUnlock, HIGH);
    ServoMotor.write(90);
}

}


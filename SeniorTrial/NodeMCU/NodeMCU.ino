/* Code Written by Rishi Tiwari
 *  Website:- https://tricksumo.com
*/



#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

// Update HOST URL here

#define HOST "seniorprojectdatabase.000webhostapp.com"          // Enter HOST URL without "http:// "  and "/" at the end of URL

#define WIFI_SSID "Hamze net"            // WIFI SSID here                                   
#define WIFI_PASSWORD "71734949"        // WIFI password here

#define hallSensor A0
#define DHT 16 //D0
#define Led 5 //D1
// Declare global variables which will be uploaded to server

int val = 1;
int val2 = 99;

String sendval, sendval2, postData;
//String value, postValue;

String hallStatus="0";
String hallValue="";

void setup() {

     
Serial.begin(115200); 
Serial.println("Communication Started \n\n");  
delay(1000);
  

pinMode(LED_BUILTIN, OUTPUT);     // initialize built in led on the board
pinMode(hallSensor, INPUT);


WiFi.mode(WIFI_STA);           
WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                     //try to connect with wifi
Serial.print("Connecting to ");
Serial.print(WIFI_SSID);
while (WiFi.status() != WL_CONNECTED) 
{ Serial.print(".");
    delay(500); }

Serial.println();
Serial.print("Connected to ");
Serial.println(WIFI_SSID);
Serial.print("IP Address is : ");
Serial.println(WiFi.localIP());    //print local IP address

delay(30);
}



void loop() { 

HTTPClient http;    // http object of class HTTPClient
WiFiClient wclient; // wclient object of class HTTPClient  

//state = digitalRead(A0);

// Convert integer variables to string
hallValue = String(analogRead(hallSensor));  
//sendval2 = String(val2);   

if(hallValue<"100"){
  hallStatus = "1";
}
else{
  hallStatus="0";
}




postData = "status=" + hallStatus; 
 

// We can post values to PHP files as  example.com/dbwrite.php?name1=val1&name2=val2&name3=val3
// Hence created variable postDAta and stored our variables in it in desired format
// For more detials, refer:- https://www.tutorialspoint.com/php/php_get_post.htm

// Update Host URL here:-  
  
http.begin(wclient, "http://seniorprojectdatabase.000webhostapp.com/postHallWrite.php");              // Connect to host where MySQL databse is hosted
http.addHeader("Content-Type", "application/x-www-form-urlencoded");            //Specify content-type header

  
 
int httpCode = http.POST(postData);   // Send POST request to php file and store server response code in variable named httpCode
Serial.println("Values are, status="+ hallStatus );


// if connection eatablished then do this
if (httpCode == 200) { Serial.println("Values uploaded successfully."); Serial.println(httpCode); 
String webpage = http.getString();    // Get html webpage output and store it in a string
Serial.println(webpage + "\n"); 
}

// if failed to connect then return and restart

else { 
  Serial.println(httpCode); 
  Serial.println("Failed to upload values. \n"); 
  http.end(); 
  return; }


delay(3000); 
digitalWrite(LED_BUILTIN, LOW);
delay(3000);
digitalWrite(LED_BUILTIN, HIGH);




//if(state==1){ Serial.println("mag" + sendval);}

 //val+=1;
 val2+=10; // Incrementing value of variables

  
  Serial.println("----------------Closing Connection----------------");
  http.end(); //--> Close connection
  Serial.println();
  Serial.println("Please wait 2 seconds for the next connection.");
  Serial.println();
  delay(2000);
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////



//covert sensors value to string

//value= String(analogRead(A0));
//postValue= "value="+value;


/*http.begin(wclient, "http://seniorprojectdatabase.000webhostapp.com/dbWriteValue.php");              // Connect to host where MySQL databse is hosted
http.addHeader("Content-Type", "application/x-www-form-urlencoded");            //Specify content-type header

  
 
int code = http.POST(postData);   // Send POST request to php file and store server response code in variable named httpCode
Serial.println("Values are, sendval = " + sendval + " and sendval2 = "+sendval2 );


// if connection eatablished then do this
if (code == 200) { Serial.println("Values uploaded successfully."); Serial.println(code); 
String webpage = http.getString();    // Get html webpage output and store it in a string
Serial.println(webpage + "\n"); 
}

// if failed to connect then return and restart

else { 
  Serial.println(code); 
  Serial.println("Failed to upload values. \n"); 
  http.end(); 
  return; }


}*/

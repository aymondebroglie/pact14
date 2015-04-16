#include <Wire.h>
#include <PN532_I2C.h>
#include "PN532.h"

PN532_I2C pn532i2c(Wire);
PN532 nfc(pn532i2c);
int identification;
char scan[116];

byte statusLed    = 13;

byte sensorInterrupt = 2;  // 0 = digital pin 2
byte sensorPin       = 21;

// The hall-effect flow sensor outputs approximately 4.5 pulses per second per
// litre/minute of flow.
float calibrationFactor = 4.5;

volatile byte pulseCount;  

float flowRate;
unsigned int flowMilliLitres;
unsigned long totalMilliLitres;

unsigned long oldTime;

void setup(void) {
  // has to be fast to dump the entire memory contents!
  identification = 0;
  Serial1.begin(115200);
  


  nfc.begin();

  uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {

    while (1); // halt
  }
  // Got ok data, 

  // configure board to read RFID tags
  nfc.SAMConfig();
 
 
 
  pinMode(statusLed, OUTPUT);
  digitalWrite(statusLed, HIGH);  // We have an active-low LED attached
  
  pinMode(sensorPin, INPUT);
  digitalWrite(sensorPin, HIGH);

  pulseCount        = 0;
  flowRate          = 0.0;
  flowMilliLitres   = 0;
  totalMilliLitres  = 0;
  oldTime           = 0;

  // The Hall-effect sensor is connected to pin 2 which uses interrupt 0.
  // Configured to trigger on a FALLING state change (transition from HIGH
  // state to LOW state)
  attachInterrupt(sensorInterrupt, pulseCounter, FALLING);
}


void loop(void) {
  if(identification == 0){
 
   
     detachInterrupt(sensorInterrupt);
   
  uint8_t success;                          // Flag to check if there was an error with the PN532
  uint8_t uid[] = { 0, 0, 0, 0, 0, 0, 0 };  // Buffer to store the returned UID
  uint8_t uidLength;                        // Length of the UID (4 or 7 bytes depending on ISO14443A card type)
  uint8_t currentblock;                     // Counter to keep track of which block we're on
  bool authenticated = false;               // Flag to indicate if the sector is authenticated
  uint8_t data[16];                         // Array to store block data during reads

  // Keyb on NDEF and Mifare Classic should be the same
  uint8_t keyuniversal[6] = { 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF };

  // Wait for an ISO14443A type cards (Mifare, etc.).  When one is found
  // 'uid' will be populated with the UID, and uidLength will indicate
  // if the uid is 4 bytes (Mifare Classic) or 7 bytes (Mifare Ultralight)
  
  success = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A, uid, &uidLength);
  
  if (success) {

    if (uidLength == 4)
    {
      // Now we try to go through sectors 4 and 5 (each having 4 blocks)
      // authenticating each sector, and then dumping the blocks
      for (currentblock = 4; currentblock < 5; currentblock++)
      {
        // Check if this is a new block so that we can reauthenticate
        if (nfc.mifareclassic_IsFirstBlock(currentblock)) authenticated = false;

        // If the sector hasn't been authenticated, do so first
        if (!authenticated)
        {
          // Starting of a new sector ... try to to authenticate, essaye 2 types de blocks
          if (currentblock == 0)
          {
              success = nfc.mifareclassic_AuthenticateBlock (uid, uidLength, currentblock, 1, keyuniversal);
          }
          else
          {
              success = nfc.mifareclassic_AuthenticateBlock (uid, uidLength, currentblock, 1, keyuniversal);
          }
          if (success)
          {
            authenticated = true;
          }
         
        }
        // If we're still not authenticated just skip the block
        if (authenticated)
        {
          success = nfc.mifareclassic_ReadDataBlock(currentblock, data);
          if (success)
          {
           /* int i;
           for (i = 7; i <15;i++){ 
            Serial1.write(data[i]);
           }
           Serial1.println("");*/// passage a la ligne
            Serial.write(data[7] + data[8] + data[9] + data[10] + data[11] + data[12] + data[13] + data[14] + "Goulot numero : 1 \n");
            identification =1;
            attachInterrupt(sensorInterrupt, pulseCounter, FALLING);
            
          }
        }
      }
      
     
    }
  }
  }
  if(identification ==1){
    
 if((millis() - oldTime) > 1000)    // Only process counters once per second
  { 
    // Disable the interrupt while calculating flow rate and sending the value to
    // the host
    detachInterrupt(sensorInterrupt);
        
    // Because this loop may not complete in exactly 1 second intervals we calculate
    // the number of milliseconds that have passed since the last execution and use
    // that to scale the output. We also apply the calibrationFactor to scale the output
    // based on the number of pulses per second per units of measure (litres/minute in
    // this case) coming from the sensor.
    flowRate = ((1000.0 / (millis() - oldTime)) * pulseCount) / calibrationFactor;
    
    // Note the time this processing pass was executed. Note that because we've
    // disabled interrupts the millis() function won't actually be incrementing right
    // at this point, but it will still return the value it was set to just before
    // interrupts went away.
    oldTime = millis();
    
    // Divide the flow rate in litres/minute by 60 to determine how many litres have
    // passed through the sensor in this 1 second interval, then multiply by 1000 to
    // convert to millilitres.
    flowMilliLitres = (flowRate / 60) * 1000;
    
    // Add the millilitres passed in this second to the cumulative total
    totalMilliLitres += flowMilliLitres;
      
    unsigned int frac;
    
    
   // Print the flow rate for this second in litres / minute
   String chaine;
     chaine = (String) (totalMilliLitres*20/(23*100));
   if (flowRate == 0 & totalMilliLitres !=0 & chaine != "0"){
     
     chaine = (String) (totalMilliLitres*20/(23*100));
    Serial1.println(chaine); 
    totalMilliLitres =0;
    identification =0;
   }

    // Reset the pulse counter so we can start incrementing again
    pulseCount = 0;
    
    // Enable the interrupt again now that we've finished sending output
    attachInterrupt(sensorInterrupt, pulseCounter, FALLING);
  }
}
}


void pulseCounter()
{
  // Increment the pulse counter
  pulseCount++;
}

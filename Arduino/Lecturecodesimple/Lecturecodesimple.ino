

/**************************************************************************/
/*!
    This example attempts to dump the contents of a Mifare Classic 1K card

    Note that you need the baud rate to be 115200 because we need to print
    out the data and read from the card at the same time!

    To enable debug message, define DEBUG in PN532/PN532_debug.h
*/
/**************************************************************************/
#include <Wire.h>

#include <PN532_I2C.h>

#include <emulatetag.h>
#include <llcp.h>
#include <mac_link.h>
#include <PN532.h>
#include <PN532Interface.h>
#include <PN532_debug.h>
#include <snep.h>

#include <Wire.h>
#include <PN532_I2C.h>
#include "PN532.h"

PN532_I2C pn532i2c(Wire);
PN532 nfc(pn532i2c);

void setup(void) {
  // has to be fast to dump the entire memory contents!
  Serial.begin(115200);


  nfc.begin();

  uint32_t versiondata = nfc.getFirmwareVersion();
  if (! versiondata) {

    while (1); // halt
  }
  // Got ok data, 

  // configure board to read RFID tags
  nfc.SAMConfig();
 
  
}


void loop(void) {
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
            int i;
           for (i =4 ; i<15;i++){
            Serial.write(data[i]);
           }
          }
        }
      }
      
     
    }
  }

}

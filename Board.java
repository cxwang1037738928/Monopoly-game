package Final_project;
       import java.io.*;
       import java.util.*;
public class Board {
   Property[] properties;
   Player[] players;
   DiceRoll diceroll;
   int currentplayerindex;
   int currentplayercoordinate;
   Scanner newscan = new Scanner(System.in);
   Scanner newscan2 = new Scanner(System.in);
   Scanner newscan3 = new Scanner(System.in);
   public Board(){
       properties = new Property[26];
       // owner = -1 because it indicates no ownership in the beginning
       properties[0] = new Property("Start",0, 10, 0, -1);
       properties[1] = new Property("Old Kent Road",60, 20, 1, -1 );
       properties[2] = new Property("White Chapel Road",60, 30, 2, -1 );
       properties[3] = new Property("The Angle Islinton",100, 30, 3, -1 );
       properties[4] = new Property( "Euston Road",100, 30, 4, -1 );
       properties[5] = new Property( "Pentonville Road",100, 40, 5, -1 );
       properties[6] = new Property( "Income Tax",0, 0, 6, -1 );
       properties[7] = new Property( "Pall Mall",120, 50, 7, -1 );
       properties[8] = new Property( "White Chapel Hall",140, 60, 8, -1 );
       properties[9] = new Property( "Northumberland Avenue",140, 70, 9, -1 );
       properties[10] = new Property( "Bow Street",160, 70, 10, -1 );
       properties[11] = new Property( "Marlborough Street",180, 80, 11, -1 );
       properties[12] = new Property( "Vine Street",180, 90, 12, -1 );
       properties[13] = new Property( "Free Parking",0, 0, 13, -1 );
       properties[14] = new Property("The Stand",220, 90, 14, -1 );
       properties[15] = new Property( "Fleet Street",220, 100, 15, -1 );
       properties[16] = new Property( "Trafalgar Square",240, 110, 16, -1 );
       properties[17] = new Property( "Leicester Square",260, 110, 17, -1 );
       properties[18] = new Property( "Conventry Street",260, 120, 18, -1 );
       properties[19] = new Property( "Piccadilly",280, 130, 19, -1 );
       properties[20] = new Property( "Jail",0, 0, 20, -1 );
       properties[21] = new Property( "Regent Street",300, 130, 21, -1 );
       properties[22] = new Property( "Oxford Street",300, 130, 22, -1 );
       properties[23] = new Property( "Bond Street",320, 150, 23, -1 );
       properties[24] = new Property( "Park Space",350, 175, 24, -1 );
       properties[25] = new Property( "Board Walk",400, 200, 25, -1 );

       //Status: On the move / In jail / Waiting / Bankrupt
       players = new Player[3];
       players[0] = new Player(1500, "Waiting", 0, 0);
       players[1] = new Player(1500, "Waiting", 0, 0);
       players[2] = new Player(1500, "Waiting", 0, 0);

       diceroll = new DiceRoll(1,6);

   }

   public void InitializeBoard()
   {
       currentplayerindex = 0;
       currentplayercoordinate = 0;
   }


   //return -1: nobody goes into bankruptcy
   //otherwise return player index who goes int bankruptcy
   public int isplayerbankrupt()
   {
       int bankruptnum  = 0;
       for (int idx=0; idx<3; idx++)
       {
           if (players[idx].balance <= 0) {
               bankruptnum += 1;
               players[idx].status = "Bankrupt";

           }
       }
       if (bankruptnum>1)
           return 0;   //Game over
       else
           return -1;
   }

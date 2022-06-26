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


   public void playmonopoly() {
       do {
           /* Check current player's status; if they are waiting, change to on the move; if they
            *  are bankrupt, skip their turn; if they are in jail, subtract one from the jail
            *  sentence; if their jail sentence is equal to zero, subtract $50 from the player's
            *  balance and change their status to on the move.
            */
           System.out.println(" 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25");
           for (int i=0; i<26; i++)
               System.out.print( properties[i].owner + " ");
           System.out.print("\n");

           int dicenumber = diceroll.dice();
           currentplayercoordinate = players[currentplayerindex].playercoordinate;
           switch (players[currentplayerindex].status) {
               case "Waiting":
                   players[currentplayerindex].status = "On the move";
                   currentplayercoordinate += dicenumber;
                   if (currentplayercoordinate >= 26) {
                       currentplayercoordinate -= 26;
                   }
                   players[currentplayerindex].playercoordinate = currentplayercoordinate;
                   System.out.println("player " + currentplayerindex + " rolled a " + dicenumber);

                   switch (properties[currentplayercoordinate].name) {
                       case "Start":
                           players[currentplayerindex].balance += 200;
                           System.out.println("player " + currentplayerindex + " received $200 for returning to spawn.");
                           players[currentplayerindex].status = "Waiting";
                           break;
                       case "Jail":
                           players[currentplayerindex].status = "In jail";
                           System.out.println("player " + currentplayerindex + " went to jail for insurance fraud.");
                           players[currentplayerindex].jailsentence = 5;
                           break;
                       case "Free Parking":
                           System.out.println("player " + currentplayerindex + " landed on Free Parking.");
                           players[currentplayerindex].status = "Waiting";
                           break;
                       case "Income Tax":
                           System.out.println("player " + currentplayerindex + " landed on Income Tax.");
                           players[currentplayerindex].status = "Waiting";
                           break;
                       default:
                           System.out.println("player " + currentplayerindex + " landed on " +
                                   properties[currentplayercoordinate].name);
                           if (properties[currentplayercoordinate].owner != -1) {     //If the property if owned
                               int owner = properties[currentplayercoordinate].owner;
                               players[owner].balance += properties[currentplayercoordinate].rent;
                               players[currentplayerindex].balance -= properties[currentplayercoordinate].rent;
                               System.out.println("player " + currentplayerindex + " paid $" +
                                       properties[currentplayercoordinate].rent + " in rent.");
                               if (players[currentplayerindex].balance <= 0) {
                                   for(int i = 0; i < 26; i++)
                                   {
                                       if(properties[i].owner == currentplayerindex)
                                       {
                                           properties[i].owner = -1;
                                           players[currentplayerindex].balance += (properties[i].cost)/2;
                                           System.out.println("player " + currentplayerindex + " sold " +
                                                   properties[i].name + " for $" + (properties[i].cost)/2);
                                       }
                                   }
                                   players[currentplayerindex].balance -= properties[currentplayercoordinate].rent;
                               }
                           }
                           else if (properties[currentplayercoordinate].owner == -1) // If the property is un-owned
                           {
                               System.out.println(properties[currentplayercoordinate].name + " is un-owned " +
                                       "and costs $" + properties[currentplayercoordinate].cost + ", would"
                                       + " you likes to buy it?");
                               String player_input = newscan.next();
                               if (player_input.equals("YES")) {
                                   if ((players[currentplayerindex].balance >= properties[currentplayercoordinate].cost))
                                   {
                                       players[currentplayerindex].balance -= properties[currentplayercoordinate].cost;
                                       properties[currentplayercoordinate].owner = currentplayerindex;
                                       System.out.println("player " + currentplayerindex + " purchased " +
                                               properties[currentplayercoordinate].name);
                                   }
                                   else {
                                       System.out.println("You do not have enough money to purchase this" +
                                               " property");
                                   }
                               }
                               else if (player_input.equals("AUCTION")) {
                                   System.out.println("This property is worth $" + properties[currentplayercoordinate].cost);

                                   int user_bid1, user_bid2;
                                   int auctionedcost = 0, auctionwinner = 0;
                                   do {
                                       System.out.print("Player"+ (currentplayerindex + 1) % 3 + " bidding:");
                                       user_bid1 = newscan2.nextInt();
                                       System.out.print("Player"+ (currentplayerindex + 2) % 3 + " bidding:");
                                       user_bid2 = newscan3.nextInt();
                                   }while(user_bid1!=0 & user_bid2!=0 );

                                   if(user_bid1!=0) {
                                       auctionedcost = user_bid1;
                                       auctionwinner = (currentplayerindex + 1) % 3;
                                   }else{
                                       auctionedcost = user_bid1;
                                       auctionwinner = (currentplayerindex + 2) % 3;
                                   }

                                   properties[currentplayercoordinate].owner = auctionwinner;
                                   players[auctionwinner].balance -= auctionedcost;
                                   System.out.println("player " + auctionwinner + " bought " +
                                           properties[currentplayercoordinate].name + " for $" +
                                           properties[currentplayercoordinate].cost);
                               }
                           }
                           players[currentplayerindex].status = "Waiting";
                   }
                   currentplayerindex += 1;
                   if (currentplayerindex > 2) {
                       currentplayerindex += 1;
                       currentplayerindex = 0;
                   }
                   System.out.print("\n");
                   break;
               case "In jail":
                   if (players[currentplayerindex].jailsentence != 0) {
                       if (diceroll.doubleroll() == 1) {
                           System.out.println("Double! You've been bailed out of prison.");
                       }
                       else {
                           players[currentplayerindex].jailsentence -= 1;
                           System.out.println("player " + currentplayerindex + " is still in prison for " +
                                   players[currentplayerindex].jailsentence + " more turns");
                           currentplayerindex += 1;
                           if (currentplayerindex > 2) {
                               currentplayerindex = 0;
                           }
                       }
                   }
                   else {
                       players[currentplayerindex].status = "Waiting";
                       System.out.println("player " + currentplayerindex + " is out of prison! " +
                               "They lost $50.");
                       players[currentplayerindex].balance -= 50;
                   }
                   break;
               case "Bankrupt":
                   System.out.println("player " + currentplayerindex + " is bankrupt :(");
                   currentplayerindex += 1;
                   if (currentplayerindex > 2) {
                       currentplayerindex = 0;
                   }
                   break;
           }
       } while (isplayerbankrupt() == -1);
       System.out.println("game over");
   }

}

package Final_project;

public class DiceRoll {
   int max;
   int min;
   double x;
   double rand1;
   double rand2;
   int doubledice;
   public DiceRoll(int min, int max)
   {
       // Setter, and used for updating values of the max and min variable
       this.min = min;
       this.max = max;
   }
   public int dice()
   {
       rand1 = ( Math.random()*( (max-min)+1)) +min;
       rand2 = ( Math.random()*( (max-min)+1)) +min;
       x = rand1 + rand2;
       return (int)x;
   }
   public int doubleroll()
   {
       if(rand1 == rand2)
           doubledice = 1;
       else
           doubledice = 0;
       return doubledice;
   }
}

package Final_project;

public class Player {
   public Player(){} // create array of object for players = new Player[3];
   public Player(int balance, String status, int playercoordinate, int jailsentence) { // creating the objects seperately
       this.balance = balance;
       this.status = status;
       this.playercoordinate = playercoordinate;
       this.jailsentence = jailsentence;
   }
   int jailsentence;
   int balance;
   String status; //Status: On the move / In jail / Waiting / Bankrupt
   int playercoordinate;
}

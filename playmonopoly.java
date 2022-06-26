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

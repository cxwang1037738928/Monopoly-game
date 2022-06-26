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

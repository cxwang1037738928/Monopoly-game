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

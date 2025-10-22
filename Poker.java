import java.util.ArrayList;
import java.util.Scanner; 

/** Main class for poker. */
public class Poker {
    static Round round; 

    static Poker poker = new Poker();
    static StartMenu menu; 


    public static void main(String[] args) {

        menu = new StartMenu(); 
        round = new Round(); 


        //Round round = new Round(); 
        //round.flop(); 

    }
}

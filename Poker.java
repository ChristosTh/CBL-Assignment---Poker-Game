import java.util.ArrayList;
import java.util.Scanner; 

/** Main class for poker. */
public class Poker {

    static Player player; // player and bot should go to the poker class and also change what changed for them in other classes
    static Bot bot;

    static Round round; 

    static Poker poker = new Poker(); 
    static StartMenu menu; 


    public static void main(String[] args) {

        menu = new StartMenu(); 

        player = new Player();
        bot = new Bot(0, 0);


        //Round round = new Round(); 
        //round.flop(); 

    }
}

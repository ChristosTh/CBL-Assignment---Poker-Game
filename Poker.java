/** Main class for poker. */
public class Poker {

    static Player player; 
    static Bot bot;

    static Round round; 

    static Poker poker = new Poker();
    static StartMenu menu; 


    public static void main(String[] args) {

        menu = new StartMenu(); 

        player = new Player();
        bot = new Bot(0, 0);

    }
}

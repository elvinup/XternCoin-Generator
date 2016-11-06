import java.util.*;

/**
 * Created by Elvin Uthuppan on 10/29/16.
 *
 * I decided to make a full implementation that is fully interactive
 *
 * If I'm interpreting the instructions correctly, I have it so that regular users can only earn by guessing a number
 * Only a fake user, the "Xtern Robot", can automatically enter random numbers as much as the user lets it
 *
 * There is a menu for logging in to different profiles and a leaderboard to keep track of their scores
 *
 *
 */
public class XternCoin {

    Random randomNum = new Random();
    int range = 10;                                     //Range of numbers that can be guessed
    int winningNumber = 7;                              //First winner guesses lucky number 7

    HashMap<String, Integer> profile = new HashMap<>(); //Stores all the profiles with their wins

    int coinsWanted;                                    //# of coins the user wants to earn
    int coinsEarned;                                    // # Earned so far
    String Id = "";

    /* Checks to see if User's guess was correct */
    boolean HandleGuess(String userId, int guess) {
        if (guess == winningNumber) {
            coinsEarned++;
            System.out.println("Coin Earned!  Coins you Earned: " + coinsEarned);
            profile.put(userId, profile.get(userId) + 1);   //Updates wins + 1 for lucky user
            winningNumber = randomNum.nextInt(range);       //Updates the num randomly
            return true;
        }
        else {
            return false;
        }
    }

    /* Number of coins on the profile id */
    int GetCoins(String userId) {
        return profile.get(userId);
    }

    /*Automatic Guesser */
    void StartGuessing() {
        coinsEarned = GetCoins("Xtern Robot"); //# of coins the user has
        int guess;
        while (coinsEarned < coinsWanted) {
            guess = randomNum.nextInt(range);
            HandleGuess("Xtern Robot", guess);
        }
    }

    /* Interactive Program */
    public static void main (String[] args) {
        XternCoin user = new XternCoin();
        Scanner s = new Scanner(System.in);

        int option = 0;
        int guessOption;
        System.out.println("Ready to make some XternCoin?");
        System.out.println("Enter your User ID");
        user.Id = s.nextLine();
        user.profile.put(user.Id, 0);   //Initializes 1st user

        /*
       The Menu of 4 options until exit
         */

        while (option != 4) {
            System.out.println("\nEnter the Option Number Below");
            System.out.println("(1) Login as a New User");
            System.out.println("(2) Gain Some Coins!"); //Allows you to either automatically guess or legit guess
            System.out.println("(3) Check the Current LeaderBoard");
            System.out.println("(4) Exit the Program");

            option = Integer.valueOf(s.nextLine());
            switch (option) {
                case 1: option = 1;
                    user.coinsEarned = 0; //reset for next user
                    System.out.println("Enter your User ID");
                    user.Id = s.nextLine();

                    /*Checks if the user is already in the system */
                    if (user.profile.containsKey(user.Id)) {
                        user.profile.put(user.Id, user.profile.get(user.Id));
                    }
                    else {
                        user.profile.put(user.Id, 0);
                    }
                    break;

                case 2: option = 2;
                    System.out.println("Earn Real Coins by Guessing (0) or watch how an Xtern Robot does it Automatically (1)");
                    guessOption = Integer.valueOf(s.nextLine());
                    if (guessOption == 0) {
                        System.out.println("Guess a number from 1 - 10");
                        int guess = user.randomNum.nextInt(10);
                        user.HandleGuess(user.Id, guess);
                    }
                    else if (guessOption == 1) {
                        user.profile.put("Xtern Robot", 0); // Reset the Robot for Users to Test
                        System.out.println("How many coins do you want the bot to earn?");
                        user.coinsWanted = Integer.valueOf(s.nextLine());
                        user.StartGuessing();
                    }
                    break;

                case 3: option = 3;
                    for (Map.Entry<String, Integer> entry : user.profile.entrySet())    // Goes through each profile
                        System.out.println("User: " + entry.getKey() + " has " + entry.getValue() + " Coins");
                    break;

                case 4: option = 4; //Exit
                    break;

                default: System.out.println("Invalid Input");
                    break;
            }
        }
        System.out.println("Exiting Program...");
    }
}

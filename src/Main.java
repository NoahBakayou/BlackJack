import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String[] shuffledDeck;  // Global deck variable
    static int topCardIndex;       // Index of the top card in the deck
    public static boolean isDeckOutOfCards = false; // Global flag to indicate deck status


    public static void main(String[] args) {

        System.out.println("♣\uFE0F Welcome to The Code Research Lab Casino ♣\uFE0F");
        Scanner input = new Scanner(System.in);

        while (true) { // Infinite loop to keep playing new games
            shuffledDeck = deckMaker(); // Shuffle deck at the start of each game
            topCardIndex = 0;           // Reset the top card index for each new game
            isDeckOutOfCards = false;   // Reset the flag for the new game
            playGame(input);

            if (!isDeckOutOfCards) {
                // If the game ended normally (not because the deck was out of cards)
                System.out.println("----------------------------------------------");
                System.out.println("Do you want to play again? (y or n)");
                String answer = input.nextLine().trim().toLowerCase();
                if ("y".equals(answer)) {
                    //continues
                } else if ("n".equals(answer)) {
                    System.out.println("Remember, 99% of gamblers quit before winning big.");
                    System.out.println("Exiting...");
                    return; // Exit the method, ending the program
                } else {
                    System.out.println("Invalid input. Please type 'y' or 'n'.");
                }
            }
        }

    }
    public static void playGame(Scanner input) {

        shuffledDeck = deckMaker(); // Initialize and shuffle a new deck
        topCardIndex = 0; // Reset the top card index
        ArrayList<String> userHand = new ArrayList<>();
        ArrayList<String> dealerHand = new ArrayList<>();

        userHand = hit(userHand);
        userHand = hit(userHand);

        dealerHand = hit(dealerHand);


        int userScore = checkScore(userHand);
        System.out.println("Your hand: " + userHand + " (score: " + userScore + ")");
        if (userScore == 21){
            System.out.println("Blackjack!");
            return;
        }
        System.out.println("Dealer's hand: " + dealerHand);

        //Scanner input = new Scanner(System.in);
        boolean userTurn = true;

        while (userTurn) {
            System.out.println("Hit or Stay? (h or s)");
            String str = input.nextLine().trim().toLowerCase();

            if ("h".equals(str)) {
                userHand = hit(userHand);
                userScore = checkScore(userHand);
                System.out.println("Your hand: " + userHand + " (score: " + userScore + ")");
                if (userScore > 21) {
                    System.out.println("Bust! You have exceeded 21.");
                    break;
                }
            } else if ("s".equals(str)) {
                break;
            } else {
                System.out.println("Invalid input. Please type 'h' or 's'.");
            }
        }

        if (userScore <= 21) {
            int dealerScore = checkScore(dealerHand);
            System.out.println("Dealer's hand: " + dealerHand + " (score: " + dealerScore + ")");
            while (dealerScore < 17) {
                dealerHand = hit(dealerHand);
                dealerScore = checkScore(dealerHand);
                System.out.println("Dealer's hand: " + dealerHand + " (score: " + dealerScore + ")");
                if (dealerScore > 21) {
                    System.out.println("Dealer busts!");
                    System.out.println("You win!");
                    break;
                }
            }

            if (dealerScore <= 21) {
                if (userScore > dealerScore) {
                    System.out.println("You win!");
                } else if (userScore == dealerScore) {
                    System.out.println("Push!");
                } else {
                    System.out.println("HAHAHA! You lose.");
                }
            }
        }

        //input.close(); // Close the scanner when done to avoid resource leaks.
    }

    public static String[] deckMaker() {
        String[] suit = {"S", "D", "C", "H"};
        String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] deck = new String[52];

        // Creates the deck
        for (int i = 0; i < deck.length; i++) {
            deck[i] = rank[i % 13] + suit[i / 13];
            // System.out.println(deck[i]);
        }

        // Shuffle the deck
        for (int i = 0; i < deck.length; i++) {
            int index = (int) (Math.random() * deck.length);

            String temp = deck[i];
            deck[i] = deck[index];
            deck[index] = temp;

        }
        return deck;
    }
    public static ArrayList<String> hit(ArrayList<String> userHand) {

        if (topCardIndex < shuffledDeck.length) {
            String drawnCard = shuffledDeck[topCardIndex];
            userHand.add(drawnCard);
            topCardIndex++;  // Move to the next card in the deck
        } else {
            // Handle the case where the deck is empty (e.g., print a message or end the game)
            System.out.println("The deck is out of cards!");
            isDeckOutOfCards = true;
        }

        return userHand;
    }

    public static int checkScore(ArrayList<String> userHand) {
        int score = 0;
        int aces = 0; // Keep track of Aces

        for (String card : userHand) {
            int cardValue;
            if (card.startsWith("A")) {
                cardValue = 11; // Assume Ace is 11 initially
                aces++;
            } else if (card.startsWith("K") || card.startsWith("Q") || card.startsWith("J") || card.startsWith("10")) {
                cardValue = 10;
            } else {
                cardValue = Integer.parseInt(card.substring(0, card.length() - 1)); // This will parse the number for 2-9
            }
            score += cardValue;
        }

        // Adjust for Aces if the score is over 21
        while (score > 21 && aces > 0) {
            score -= 10; // Count each Ace as 1 instead of 11
            aces--;
        }

        return score;

    }
}


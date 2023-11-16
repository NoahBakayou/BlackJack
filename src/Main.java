import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String[] deck; // Global deck variable
    static int topCardIndex; // Index of the top card in the deck

    public static void main(String[] args) {
        System.out.println("♣️ Welcome to The Code Research Lab Casino ♣️");
        Scanner input = new Scanner(System.in);
        String answer;

        // Initialize and shuffle the deck only once at the start
        deckMaker();
        topCardIndex = 0;

        do {
            playGame(input);

            System.out.println("----------------------------------------------");
            System.out.println("Do you want to play again? (y or n)");
            answer = input.nextLine().trim().toLowerCase();

        } while ("y".equals(answer));

        System.out.println("Remember, 99% of gamblers quit before winning big.");
        System.out.println("Exiting...");
        input.close();
    }
    public static void playGame(Scanner input) {
        ArrayList<String> userHand = new ArrayList<>();
        ArrayList<String> dealerHand = new ArrayList<>();

        //starting hand for user and dealer
        hit(userHand);
        hit(userHand);
        hit(dealerHand);

        int userScore = checkScore(userHand);
        System.out.println("Your hand: " + userHand + " (score: " + userScore + ")");
        if (userScore == 21) {
            System.out.println("Blackjack!");
        }
        System.out.println("Dealer's hand: " + dealerHand);

        boolean userTurn = true;
        while (userTurn) {
            System.out.println("Hit or Stay? (h or s)");
            String str = input.nextLine().trim().toLowerCase();

            if ("h".equals(str)) {
                hit(userHand);
                userScore = checkScore(userHand);
                System.out.println("Your hand: " + userHand + " (score: " + userScore + ")");
                if (userScore > 21) {
                    System.out.println("Bust! You have exceeded 21.");
                    break;
                }
            } else if ("s".equals(str)) {
                userTurn = false;
            } else {
                System.out.println("Invalid input. Please type 'h' or 's'.");
            }
        }

        if (userScore <= 21) {
            int dealerScore = checkScore(dealerHand);
            System.out.println("Dealer's hand: " + dealerHand + " (score: " + dealerScore + ")");
            while (dealerScore < 17) {
                hit(dealerHand);
                dealerScore = checkScore(dealerHand);
                System.out.println("Dealer's hand: " + dealerHand + " (score: " + dealerScore + ")");
                if (dealerScore > 21) {
                    System.out.println("Dealer busts!");
                    System.out.println("You win!");
                    return;
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
    }

    public static void deckMaker() {
        String[] suit = {"♠", "♦", "♣", "♥"};
        String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        deck = new String[52];

        // Creates the deck
        for (int i = 0; i < deck.length; i++) {
            deck[i] = rank[i % 13] + suit[i / 13];
        }

        // Fisher-Yates shuffle algorithm
        for (int i = deck.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1)); // Random index from 0 to i
            String temp = deck[i];
            deck[i] = deck[index];
            deck[index] = temp;
        }
    }

    public static void hit(ArrayList<String> hand) {
        if (topCardIndex >= deck.length) {
            System.out.println("The deck is out of cards, reshuffling...");
            deckMaker();
            topCardIndex = 0;
        }

        String drawnCard = deck[topCardIndex];
        hand.add(drawnCard);
        topCardIndex++;
    }

    public static int checkScore(ArrayList<String> hand) {
        int score = 0;
        int aces = 0; // Keep track of Aces

        for (String card : hand) {
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

//TODO: Make OOP for readability sake
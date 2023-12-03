import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private ArrayList<String> userHand;
    private ArrayList<String> dealerHand;

    public Game() {
        deck = new Deck();
        userHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
    }

    public void play(Scanner input) {
        // Starting hand for user and dealer
        hit(userHand);
        hit(userHand);
        hit(dealerHand);

        int userScore = checkScore(userHand);
        System.out.println("Your hand: " + userHand + " (score: " + userScore + ")");
        if (userScore == 21) {
            System.out.println("Blackjack!");
            return;  // End the game if the user hits Blackjack
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
                    return;  // End the game if the user busts
                }
            } else if ("s".equals(str)) {
                userTurn = false;
            } else {
                System.out.println("Invalid input. Please type 'h' or 's'.");
            }
        }

        // Dealer's turn
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
                    return;  // End the game if the dealer busts
                }
            }

            // Compare scores to determine winner
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

    public void hit(ArrayList<String> hand) {
        hand.add(deck.drawCard());
    }

    public int checkScore(ArrayList<String> hand) {
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

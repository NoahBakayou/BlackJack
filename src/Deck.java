public class Deck {
    private String[] deck;
    private int topCardIndex;

    public Deck() {
        deckMaker();
    }

    public void deckMaker() {
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
        topCardIndex = 0;
    }

    public String drawCard() {
        if (topCardIndex >= deck.length) {
            System.out.println("The deck is out of cards, reshuffling...");
            deckMaker();
            topCardIndex = 0;
        }
        return deck[topCardIndex++];
    }
}

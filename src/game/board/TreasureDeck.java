package game.board;

import gfx.Assets;
import util.Handler;
import util.Utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TreasureDeck {
    /* Sub-Class Card */
    static class Card {
        private BufferedImage sprite;
        private String value;

        public Card(BufferedImage sprite, String value) {
            this.sprite = sprite;
            this.value = value;
        }

        public BufferedImage getSprite() {
            return sprite;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private ArrayList<Card> deck;
    private ArrayList<Card> graveyard;

    public TreasureDeck() {
        deck = shuffleDeck();
        graveyard = new ArrayList<>();
    }

    /* Shuffle the deck */
    private ArrayList<Card> shuffleDeck() {
        ArrayList<Integer> alreadyUsedNumbers = new ArrayList<>();
        ArrayList<Card> res = new ArrayList<>();
        int randomValue;
        while (alreadyUsedNumbers.size() != Assets.deck.length) {
            randomValue = Handler.r.nextInt(Assets.deck.length);
            if (!alreadyUsedNumbers.contains(randomValue)) {
                res.add(new Card(Assets.deck[randomValue], Utils.valueToString(randomValue)));
                alreadyUsedNumbers.add(randomValue);
            }
        }
        return res;
    }

    private void shuffleGraveyard(){
        while(!graveyard.isEmpty()){
            deck.add(graveyard.get(0));
            graveyard.remove(0);
        }
    }

    /* Draw a card */
    public String drawCard() {
        if (deck.isEmpty()) {
            shuffleGraveyard();
        }
        Card card = deck.get(0);
        deck.remove(0);
        if (card.value.equals("flooded")) graveyard.add(card);
        return card.getValue();
    }

    /* Return the sprite of the last card in the graveyard */
    public BufferedImage lastGraveCardSprite() {
        return graveyard.isEmpty() ? new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB) : graveyard.get(graveyard.size() - 1).getSprite();
    }

    /* Return true if the deck is empty */
    public boolean isEmpty() {
        return this.deck.isEmpty();
    }

    /* Add a card to the graveyard */
    public void discard(Card card) {
        this.graveyard.add(card);
    }
}

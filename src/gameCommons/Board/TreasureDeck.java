package gameCommons.Board;

import gfx.Assets;
import util.Utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TreasureDeck {
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
    }

    private ArrayList<Card> deck;
    private ArrayList<Card> grave;

    public TreasureDeck() {
        deck = shuffleDeck();
        grave = new ArrayList<>();
    }

    private ArrayList<Card> shuffleDeck() {
        ArrayList<Integer> alreadyUsedNumbers = new ArrayList<>();
        ArrayList<Card> res = new ArrayList<>();
        int randomValue;
        while (alreadyUsedNumbers.size() != Assets.deck.length) {
            randomValue = Island.r.nextInt(Assets.deck.length);
            if (!alreadyUsedNumbers.contains(randomValue)) {
                res.add(new Card(Assets.deck[randomValue], Utils.valueToString(randomValue)));
                alreadyUsedNumbers.add(randomValue);
            }
        }
        return res;
    }

    public String drawCard() {
        if (deck.isEmpty()) {
            shuffleDeck();
        }
        Card card = deck.get(0);
        if (!card.getValue().equals("flooded")) deck.remove(card);
        else grave.add(card);
        return card.getValue();
    }

    public BufferedImage lastGraveCardSprite() {
        return grave.isEmpty() ? new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB) : grave.get(grave.size() - 1).getSprite();
    }

    public boolean isEmpty() {
        return this.deck.isEmpty();
    }

    public void discard(Card card) {
        this.grave.add(card);
    }
}

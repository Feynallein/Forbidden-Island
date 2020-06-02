package game.board;

import util.Handler;

import java.util.ArrayList;

public class FloodDeck {
    private Handler handler;
    private ArrayList<Integer[]> board;
    private ArrayList<Integer[]> graveyard;
    private ArrayList<Integer[]> flooded;

    public FloodDeck(Handler handler) {
        this.handler = handler;
        board = new ArrayList<>();
        graveyard = new ArrayList<>();
        flooded = new ArrayList<>();
        init();
    }

    private void init() {
        for (int i = 0; i < handler.getIslandLength(); i++) {
            for (int j = 0; j < handler.getIslandLength(); j++) {
                if ((j != 0 && j != 5 && i != 0 && i != 5) || (j != 0 && j != 5 && j != 1 && j != 4) || (i != 0 && i != 1 && i != 4 && i != 5))
                    graveyard.add(new Integer[]{i, j});
                else flooded.add(new Integer[]{i, j});
            }
        }
    }

    /* Shuffle the deck */
    private ArrayList<Integer[]> shuffleDeck() {
        ArrayList<Integer[]> res = new ArrayList<>();
        int randomValue;
        while (!graveyard.isEmpty()) {
            randomValue = Handler.r.nextInt(graveyard.size());
            if (graveyard.get(randomValue) != null) {
                res.add(graveyard.get(randomValue));
                graveyard.remove(randomValue);
            }
        }
        return res;
    }

    /* Draw a card */
    public Integer[] drawCard() {
        if (board.isEmpty()) {
            board = shuffleDeck();
        }
        Integer[] card = board.get(0);
        board.remove(card);
        graveyard.add(card);
        return card;
    }

    /* Add a card to the graveyard */
    public void discard(Integer[] tile) {
        flooded.add(tile);
        graveyard.remove(tile);
    }
}

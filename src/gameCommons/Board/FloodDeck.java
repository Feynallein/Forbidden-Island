package gameCommons.Board;

import util.Handler;

import java.util.ArrayList;

public class FloodDeck{
    private Handler handler;
    private ArrayList<Integer[]> board;
    private ArrayList<Integer[]> grave;
    private ArrayList<Integer[]>  flooded;

    public FloodDeck(Handler handler){
        this.handler = handler;
        board = new ArrayList<>();
        grave = new ArrayList<>();
        flooded = new ArrayList<>();
        init();
    }

    private void init() {
        for(int i = 0; i < handler.getIslandLength(); i++) {
            for(int j = 0; j < handler.getIslandLength(); j++) {
                if((j != 0 && j != 5 && i != 0 && i != 5) || (j != 0 && j != 5 && j != 1 && j != 4) || (i != 0 && i != 1 && i != 4 && i != 5)) grave.add(new Integer[]{i, j});
                else flooded.add(new Integer[]{i, j});
            }
        }
    }

    private ArrayList<Integer[]> shuffleDeck() {
        ArrayList<Integer[]> res = new ArrayList<>();
        int randomValue;
        while (!grave.isEmpty()) {
            randomValue = Island.r.nextInt(grave.size());
            if (grave.get(randomValue) != null) {
                res.add(grave.get(randomValue));
                grave.remove(randomValue);
            }
        }
        return res;
    }

    public Integer[] drawCard() {
        if(board.isEmpty()){
            board = shuffleDeck();
        }
        Integer[] card = board.get(0);
        board.remove(card);
        grave.add(card);
        return card;
    }

    public void discard(Integer[] tile) {
        flooded.add(tile);
        grave.remove(tile);
    }
}

package com.project.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Pawn extends Board {
    private boolean queenPawn;
    private boolean lowerPawn;
    private boolean isClicked;
    private Board board;
    private int index;



    private List<Integer> differenceList = new LinkedList<>();
    private HashMap<Integer, LinkedList<Integer>> availableTiles = new HashMap<>();
    private HashMap<Integer, Integer> beatTiles = new HashMap<>();


    public Pawn(boolean queenPawn, boolean lowerPawn, boolean isClicked, Settings settings, Board board, int index) {
        super(settings);
        this.queenPawn = queenPawn;
        this.lowerPawn = lowerPawn;
        this.isClicked = isClicked;
        this.board = board;
        this.index = index;
        makePawn();
        differenceList.add(-9);
        differenceList.add(-7);
        differenceList.add(7);
        differenceList.add(9);
    }

        public Circle makePawn(){
        Color color;
        if (lowerPawn){
            color = board.getSettings().getPawnLowerColor();
        } else {
            color = board.getSettings().getPawnUpperColor();
        }
        Circle pawn = new Circle(40, color);
        pawn.setStroke(Color.BLACK);
        pawn.setStrokeWidth(3);


        if(lowerPawn){
            if (index <= 7){
                setQueenPawn(true);
            }
        }

        if(!lowerPawn) {
            if (index / 8 == 7) {
                setQueenPawn(true);
            }
        }

        if(queenPawn){
            pawn.setStroke(board.getSettings().getQueenStrokeColor());
        }

        return pawn;

    }


    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }


    public void makeOptionTiles() {
        resetOptionTiles(); //Delete previous choice
        makeMoveTiles();
        //makeBeatTiles();
    }

    public void resetOptionTiles(){
        for (Tile tile : board.getTileList()) {
            if (tile.getTileType() != TileType.NONE) {
                tile.setTileType(TileType.NONE);
                tile.makeResetTile();
            }
        }
    }


    public void makeMoveTiles(){
        if(!queenPawn) {
            availableTiles.clear();
            beatTiles.clear();
            for (Integer diff: differenceList){
                int dir = multiplier(); //directory - UP or DOWN
                int prev = index;
                int count = index - diff*multiplier();
                availableTiles.put(diff, new LinkedList<>());

                if((diff>0) &&                      //MOVE REGULAR PAWN
                        ((prev/8) == (count/8 + dir)) &&
                        (board.getTileList().get(count).isEmpty())
                ){
                    movePawnInstruction(diff, count);

                } else if ((diff>0) &&              //BEAT REGULAR PAWN
                        ((prev/8) == (count/8 + dir)) &&
                        !(board.getTileList().get(count).isEmpty()) &&
                        (board.getTileList().get(count).getPawn().isLowerPawn() != lowerPawn) &&
                        ((count / 8) == ((count-diff*multiplier()) / 8 + dir)) &&
                        (board.getTileList().get(count-diff*multiplier()).isEmpty())
                ){
                    beatPawnInstruction(diff*multiplier(),count);
                }
            }

        } else { //KROLOWA
            availableTiles.clear();
            beatTiles.clear();

            try {
                for (Integer diff : differenceList) {
                    int metPawn = 0;
                    int dir = Math.abs(diff) / diff; //directory - UP or DOWN
                    int prev = index;
                    int count = index - diff;
                    availableTiles.put(diff, new LinkedList<>());


                    while ((count >= 0) && (count <= 63)) {
                        if (((prev / 8) == (count / 8 + dir)) &&            //MOVE QUEEN PAWN
                                (board.getTileList().get(count).isEmpty()) &&
                                (metPawn <= 1)
                        ) {
                            movePawnInstruction(diff, count);
                            prev = count;
                            count = count - diff;

                        } else if(((prev / 8) == (count / 8 + dir)) &&      //BEAT QUEEN PAWN
                                !(board.getTileList().get(count).isEmpty()) &&
                                (board.getTileList().get(count).getPawn().isLowerPawn() != lowerPawn) &&
                                ((count / 8) == ((count-diff) / 8 + dir)) &&
                                (board.getTileList().get(count-diff).isEmpty()) &&
                                (metPawn < 1)
                        ) {
                            metPawn += 1;
                            beatPawnInstruction(diff, count);
                            prev = count;
                            count = count - diff;
                        } else {
                            break;
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        }
    }

    public void beatTilesOnly(){
        for (Integer diff: differenceList){
            if(beatTiles.get(diff) == null) {
                for (Integer indexToDelete : availableTiles.get(diff)) {
                    board.getTileList().get(indexToDelete).setTileType(TileType.NONE);
                    board.getTileList().get(indexToDelete).makeResetTile();
                }
                availableTiles.get(diff).clear();

            }
        }
    }


    public boolean isMoveAvailable(){
        boolean result = false;
        resetOptionTiles();
        makeMoveTiles();
        for(Integer diff: differenceList) {
            if (!this.availableTiles.get(diff).isEmpty()) {
                result = true;
                break;
            }
        }
        resetOptionTiles();
        return result;
    }


    public boolean isAnotherMovePossible(){
        boolean result = false;
        resetOptionTiles();
        HashMap<Integer, LinkedList<Integer>> previousAvailableTiles = getAvailableTiles();
        HashMap<Integer, Integer> previousBeatTiles = getBeatTiles();
        makeMoveTiles();
        beatTilesOnly();
        if(!this.beatTiles.isEmpty()){
            result = true;
        }
        this.availableTiles = previousAvailableTiles;
        this.beatTiles = previousBeatTiles;

        return result;
    }


    public boolean isLowerPawn() {
        return lowerPawn;
    }



    public void setIndex(int index) {
        this.index = index;
    }

    public int multiplier() {
        if (lowerPawn) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setQueenPawn(boolean queenPawn) {
        this.queenPawn = queenPawn;
    }

    public void movePawnInstruction(Integer indexDifference, Integer counterIndex){
        board.getTileList().get(counterIndex).setTileType(TileType.MOVE);
        availableTiles.get(indexDifference).add(counterIndex);
        board.getTileList().get(counterIndex).makeResetTile();
    }

    public void beatPawnInstruction(Integer indexDifference, Integer counterIndex){
        board.getTileList().get(counterIndex).setTileType(TileType.BEAT);
        beatTiles.put(indexDifference,counterIndex);
        availableTiles.get(indexDifference).add(counterIndex);
        board.getTileList().get(counterIndex-indexDifference).setTileType(TileType.MOVE);
        availableTiles.get(indexDifference).add(counterIndex-indexDifference);
        board.getTileList().get(counterIndex).makeResetTile();
        board.getTileList().get(counterIndex-indexDifference).makeResetTile();
    }


    public boolean isQueenPawn() {
        return queenPawn;
    }

    public HashMap<Integer, LinkedList<Integer>> getAvailableTiles() {
        return availableTiles;
    }

    public HashMap<Integer, Integer> getBeatTiles() {
        return beatTiles;
    }

    public int getIndex() {
        return index;
    }
}

package com.project.checkers;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.lang.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Board {

    List<Tile> tileList = new LinkedList<>();
    Settings settings;
    private boolean teamTurn = true;
    public Board(Settings settings) {
        this.settings = settings;
    }
    //GridPane grid = new GridPane();
    boolean isAnotherMove = false;
    Tile previousTile;
    private boolean isSingleplayerGame;



    public void generateTileList() {
        for (int col = 0; col < 8; col += 1) {
            for (int row = 0; row < 8; row += 1) {
                Tile tileToAdd = new Tile(settings, col, row, TileType.NONE, true);
                    tileList.add(tileToAdd);

                }
            }
        }

    public GridPane makeBoard() {
        GridPane grid = new GridPane();
        generateTileList();
        for (int col = 0; col < 8; col += 1) {
            ColumnConstraints gridColumn = new ColumnConstraints(100);
            gridColumn.setHalignment(HPos.CENTER);
            RowConstraints gridRow = new RowConstraints(100);
            gridRow.setValignment(VPos.CENTER);
            grid.getColumnConstraints().add(gridColumn);
            grid.getRowConstraints().add(gridRow);
            for (int row = 0; row < 8; row += 1) {
                grid.add(tileList.get(8*col+row).makeResetTile(), row, col);
            }
        }

        addPawnsToBoard();

        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(25,25,25,25));
        return grid;
    }

    public void addPawnsToBoard(){
        for (int pawnPcs = 0; pawnPcs < 12; pawnPcs++){
            if(pawnPcs > 3 && pawnPcs < 8){
                tileList.get(pawnPcs*2).addPawn(new Pawn(false, false, false,settings, this, pawnPcs*2));
                tileList.get(63-(pawnPcs*2)).addPawn(new Pawn(false, true, false,settings, this, (63-pawnPcs*2)));

            } else {
                tileList.get(pawnPcs*2+1).addPawn(new Pawn(false, false, false,settings, this,(pawnPcs*2+1)));
                tileList.get(63-(pawnPcs*2+1)).addPawn(new Pawn(false, true, false,settings, this,(63-(pawnPcs*2+1))));
            }
        }
    }


    public Tile getTile(int col, int row){
        int index = 8*row + col;
        return tileList.get(index);
    }

    public void setSingleplayerGame(boolean singleplayerGame) {
        isSingleplayerGame = singleplayerGame;
    }

    public void doClick(int col, int row){
        Tile clickedTile = getTile(col, row);
        if(previousTile != null) {
            previousTile.getPawn().setClicked(isAnotherMove);
        }
        if(!isAnotherMove) {
            selectPawn(clickedTile);
        }


        //Select highlight (and remove previously selected highlights) and move Pawn
        if (clickedTile.getTileType() == TileType.MOVE) {
            for (Tile checkTile : tileList) {
                if (!checkTile.isEmpty() &&
                        (checkTile.getPawn().isClicked())) {
                    if (isBeatingClick(checkTile, clickedTile)) {
                        for (Tile redTile : tileList) {
                            try {
                                if (redTile.getTileType() == TileType.BEAT) {
                                    if (checkTile.getPawn().isQueenPawn() && checkDirections(checkTile, redTile, clickedTile)) {
                                        beatPawnInstruction(checkTile, redTile, clickedTile);
                                        this.isAnotherMove = checkNextPawnMove(clickedTile);
                                        break;
                                    } else if (!checkTile.getPawn().isQueenPawn() && checkDirections(checkTile, redTile, clickedTile)) {
                                        beatPawnInstruction(checkTile, redTile, clickedTile);
                                        this.isAnotherMove = checkNextPawnMove(clickedTile);
                                        break;
                                    }

                                }
                            } catch (IndexOutOfBoundsException e) {
                            }
                        }
                    } else {
                        movePawn(checkTile, clickedTile);
                    }


                    // Delete highlighted fields after moving Pawn
                    clearBoardFromHighlights();
                    if (!isAnotherMove) {
                        teamTurn = !teamTurn;
                        this.previousTile = clickedTile;
                        if(!teamTurn && isSingleplayerGame){
                            computerMove();
                        }
                    } else {
                        this.previousTile = clickedTile;
                        previousTile.getPawn().resetOptionTiles();
                        previousTile.getPawn().makeMoveTiles();
                        previousTile.getPawn().beatTilesOnly();
                    }
                }
            }
        }

    }

    public void movePawn(Tile tileToMove, Tile finalTile){
        finalTile.addPawn(tileToMove.getPawn());
        finalTile.setEmpty(false);
        tileToMove.setEmpty(true);
        tileToMove.getPawn().setClicked(false);
        tileToMove.getPawn().setIndex(finalTile.getIndex());
        tileToMove.makeResetTile();
    }

    public void beatPawnInstruction(Tile initialTile, Tile beatedTile, Tile finalTile){

        beatedTile.setEmpty(true);
        beatedTile.makeResetTile();
        movePawn(initialTile, finalTile);
    }


    public boolean isBeatingClick(Tile pawnTile, Tile clickedTile){
        boolean isTrue = false;
        HashMap<Integer, LinkedList<Integer>> availableTiles = pawnTile.getPawn().getAvailableTiles();
        HashMap<Integer, Integer> beatTiles = pawnTile.getPawn().getBeatTiles();

        for(Map.Entry<Integer, LinkedList<Integer>> entry: availableTiles.entrySet()) {
            for (Integer availableIndex : availableTiles.get(entry.getKey())) {
                if (availableTiles.get(entry.getKey()).contains(beatTiles.get(entry.getKey()))) { //czerwone pole jest w zasięgu żółtych
                    if (availableIndex == clickedTile.getIndex()) {  //wybór grupy
                        if((Math.abs(clickedTile.getIndex()-pawnTile.getIndex())) > (Math.abs(beatTiles.get(entry.getKey())-pawnTile.getIndex()))){ //czy bije
                            isTrue = true;
                            break;
                        }
                    }
                }
            }
        }
        return isTrue;
    }

    public boolean checkNextPawnMove(Tile finalTile){
        return finalTile.getPawn().isAnotherMovePossible();
    }


    public void selectPawn(Tile clickedTile){
        if(!isAnotherMove) {
            if (!clickedTile.isEmpty()) {
                for (Tile checkTile : tileList) {
                    if (!checkTile.isEmpty()) {
                        if (checkTile.getPawn().isClicked()) {
                            checkTile.getPawn().setClicked(false);
                        }
                    }
                }
                if (clickedTile.getPawn().isLowerPawn() == teamTurn) {
                    clickedTile.getPawn().setClicked(true);
                    clickedTile.getPawn().makeOptionTiles();
                }
            } else {
                if (clickedTile.getTileType() == TileType.NONE) {
                    for (Tile checkTile : tileList) {
                        if (checkTile.getTileType() != TileType.NONE) {
                            checkTile.setTileType(TileType.NONE);
                            checkTile.makeResetTile();
                        }
                    }
                }
            }
        }
    }

    public boolean checkDirections(Tile finishTile, Tile beatedTile, Tile initialTile){
        boolean result = false;
        if((Math.abs(finishTile.getCol()-beatedTile.getCol()))/(finishTile.getCol()-beatedTile.getCol()) ==
                (Math.abs(finishTile.getCol()-initialTile.getCol()))/(finishTile.getCol()-initialTile.getCol()) &&
        ((Math.abs(finishTile.getRow()-beatedTile.getRow()))/(finishTile.getRow()-beatedTile.getRow()) ==
                (Math.abs(finishTile.getRow()-initialTile.getRow()))/(finishTile.getRow()-initialTile.getRow()))
        ){
            if((Math.abs(finishTile.getRow()-initialTile.getRow())-Math.abs(beatedTile.getRow()-initialTile.getRow())) > 0
            ){
                result = true;
            } else {
                return result;
            }

        }
        return result;
    }

    public void computerMove(){
        Random random = new Random();
        List<Pawn>computerPawns = tileList.stream().filter(tile -> (!tile.isEmpty() &&!tile.getPawn().isLowerPawn())).map(tile -> tile.getPawn()).collect(Collectors.toList());

        List<Pawn> availablePawnList =  computerPawns.stream().filter(pawn -> pawn.isMoveAvailable()).collect(Collectors.toList());
        Pawn availablePawn = availablePawnList.get(random.nextInt(availablePawnList.size()));
        doClick(availablePawn.getIndex()%8, availablePawn.getIndex()/8);
        List<Tile> availableMoveList = tileList.stream().filter(s -> (s.getTileType()==TileType.MOVE)).collect(Collectors.toList());
        Tile availableMoveTile = availableMoveList.get(random.nextInt(availableMoveList.size()));
        doClick(availableMoveTile.getCol(), availableMoveTile.getRow());

    }

    public void clearBoardFromHighlights(){
        for (Tile checkHighlitedTiles : tileList) {
            if (checkHighlitedTiles.getTileType() != TileType.NONE) {
                checkHighlitedTiles.setTileType(TileType.NONE);
                checkHighlitedTiles.makeResetTile();

            }
        }
    }

}
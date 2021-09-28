package com.project.checkers;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;



public class Tile{
    private Settings settings;
    private int col;
    private int row;
    private TileType tileType;
    private boolean isEmpty;
    private Pawn pawn = null;

    private StackPane stackPane = new StackPane();

    public Tile(Settings settings, int col, int row, TileType tileType, boolean isEmpty){
        this.settings = settings;
        this.col = col;
        this.row = row;
        this.tileType = tileType;
        this.isEmpty = isEmpty;
    }


    public StackPane makeResetTile() {

        stackPane.getChildren().clear();

        Rectangle tile = new Rectangle(100, 100);
        stackPane.getChildren().add(new Rectangle(100, 100, settings.getBoardLightColor()));
        if ((col+row) % 2 == 0) {
            tile.setFill(settings.getBoardDarkColor());
        } else {
            tile.setFill(settings.getBoardLightColor());
        }
        stackPane.getChildren().add(tile);

        //Tile Highlight - move
        Rectangle moveHighlight = new Rectangle(100, 100, Color.YELLOW);
        if (this.tileType == TileType.MOVE) {
            stackPane.getChildren().add(moveHighlight);
        }

        //Tile Highlight - beat
        Rectangle beatHighlight = new Rectangle(100, 100, Color.RED);
        if (this.tileType == TileType.BEAT) {
            stackPane.getChildren().add(beatHighlight);
        }

        Rectangle pawnHighlight = new Rectangle(100,100,Color.TURQUOISE);
        if ((!isEmpty) &&
                (this.getPawn().isClicked())) {
            stackPane.getChildren().add(pawnHighlight);
        }



        //Tile Pawn
        if (!isEmpty){
            stackPane.getChildren().add(pawn.makePawn());
        }
        return stackPane;

    }

    public void addPawn(Pawn pawn){
        this.pawn = pawn;
        setEmpty(false);
        stackPane.getChildren().add(pawn.makePawn());
    }

    public Pawn getPawn() {
        try {
            return pawn;
        } catch (NullPointerException exception){
            return null;
        }
    }


    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }


    public boolean isEmpty() {
        return isEmpty;
    }

    public int getIndex() {
        return (8*col+row);
    }

    public int getCol() {
        return row;
    }

    public int getRow() {
        return col;
    }

    public TileType getTileType() {
        return tileType;
    }
}


package com.project.checkers;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Pawn extends StackPane {
    private boolean queenPawn;
    private boolean lowerPawn;
    private Color color;
    private GridPane grid;
    private int posX;
    private int posY;




    public Pawn(GridPane grid, boolean queenPawn, boolean lowerPawn, Color color, int posX, int posY) {
        this.queenPawn = queenPawn;
        this.lowerPawn = lowerPawn;
        this.color = color;
        this.grid = grid;
        this.posX = posX;
        this.posY = posY;

    }

    public void makePawn(){
        Circle pawn = new Circle(40, color);
        pawn.setStroke(Color.BLACK);
        pawn.setStrokeWidth(3);

        grid.add(pawn, posX, posY);
        pawn.setOnMouseClicked((e) -> {
            //Tu musi być coś co usuwa makeOptionTiles() utworzone wcześniej
            makeOptionTiles();
        });
    }

    public boolean isQueenPawn() {
        return queenPawn;
    }

    public boolean isLowerPawn() {
        return lowerPawn;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getIndex(){
        return posX*8+posY;
    }

    public void makeOptionTiles() {
        Rectangle optionSquare1 = new Rectangle(100, 100, Color.YELLOW);
        Rectangle optionSquare2 = new Rectangle(100, 100, Color.YELLOW);
        if (lowerPawn & !queenPawn) {
            grid.add(optionSquare1, posX-1 ,posY-1);
            grid.add(optionSquare2, posX+1 ,posY-1);
        } else if (!lowerPawn & !queenPawn) {
            grid.add(optionSquare1, posX-1 ,posY+1);
            grid.add(optionSquare2, posX+1 ,posY+1);
        } else if (queenPawn) {

        }
    }

}

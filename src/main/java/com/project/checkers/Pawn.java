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
    private Board board;
    private int posX;
    private int posY;
    Rectangle optionSquare1 = new Rectangle(100, 100, Color.YELLOW);
    Rectangle optionSquare2 = new Rectangle(100, 100, Color.YELLOW);



    public Pawn(Board board, boolean queenPawn, boolean lowerPawn, Color color, int posX, int posY) {
        this.queenPawn = queenPawn;
        this.lowerPawn = lowerPawn;
        this.color = color;
        this.board = board;
        this.posX = posX;
        this.posY = posY;

    }

    public void makePawn(){
        Circle pawn = new Circle(40, color);
        pawn.setStroke(Color.BLACK);
        pawn.setStrokeWidth(3);

        board.grid.add(pawn, posX, posY);
        pawn.setOnMouseClicked((e) -> {
            for (int i = 0; i < board.boardData.length; i++) {
                for (int j = 0; j < board.boardData[i].length; j++) {
                    if(board.boardData[i][j].equals("OptionSquare")){
                        //Funkcja, która powinna usunąć kafelki optionSquare1 i optionSquare2
                    }
                }
            }
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
        if (lowerPawn & !queenPawn) {
            if(!board.boardData[posX-1][posY-1].equals("lower") & posX>=1) {
                board.grid.add(optionSquare1, posX - 1, posY - 1);
                board.boardData[posX-1][posY-1] = "OptionSquare";
            }
            if(!board.boardData[posX + 1][posY-1].equals("lower") & posX<=7) {
                board.grid.add(optionSquare2, posX + 1, posY - 1);
                board.boardData[posX + 1][posY-1] = "OptionSquare";
            }
        } else if (!lowerPawn & !queenPawn) {
            if(!board.boardData[posX-1][posY+1].equals("upper") & posX>=1) {
                board.grid.add(optionSquare1, posX - 1, posY + 1);
                board.boardData[posX-1][posY+1] = "OptionSquare";
            }
            if(!board.boardData[posX + 1][posY + 1].equals("upper") & posX>=1) {
                board.boardData[posX + 1][posY + 1] = "OptionSquare";
                board.grid.add(optionSquare2, posX + 1, posY + 1);
            }
        } else if (queenPawn) {

        }
    }

}

package com.project.checkers;

import javafx.scene.paint.Color;

public class Settings {
    private Color pawnLowerColor = Color.WHEAT;
    private Color pawnUpperColor = Color.SADDLEBROWN;
    private Color boardLightColor = Color.BEIGE;
    private Color boardDarkColor = Color.DIMGREY;
    private Color queenStrokeColor = Color.ORANGE;
    private Color backgroundColor = Color.WHITESMOKE;
    private Color buttonBackgroundColor = Color.WHITESMOKE;


    public Color getPawnLowerColor() {
        return pawnLowerColor;
    }

    public Color getPawnUpperColor() {
        return pawnUpperColor;
    }

    public Color getBoardLightColor() {
        return boardLightColor;
    }

    public Color getBoardDarkColor() {
        return boardDarkColor;
    }

    public Color getQueenStrokeColor() {
        return queenStrokeColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public void darkMode(){
        this.pawnLowerColor = Color.DARKBLUE;
        this.pawnUpperColor = Color.MAROON;
        this.boardDarkColor = Color.BLACK;
        this.boardLightColor = Color.DIMGREY;
        this.backgroundColor = Color.DIMGREY;
        this.buttonBackgroundColor = Color.GRAY;
    }

    public void normalMode(){
        this.pawnLowerColor = Color.WHEAT;
        this.pawnUpperColor = Color.SADDLEBROWN;
        this.boardDarkColor = Color.DIMGREY;
        this.boardLightColor = Color.BEIGE;
        this.backgroundColor = Color.WHITESMOKE;
        this.buttonBackgroundColor = Color.WHITESMOKE;
    }

    public void setQueenStrokeColor(Color queenStrokeColor) {
        this.queenStrokeColor = queenStrokeColor;
    }

    public void setPawnLowerColor(Color pawnLowerColor) {
        this.pawnLowerColor = pawnLowerColor;
    }

    public void setPawnUpperColor(Color pawnUpperColor) {
        this.pawnUpperColor = pawnUpperColor;
    }

    public void setBoardLightColor(Color boardLightColor) {
        this.boardLightColor = boardLightColor;
    }

    public void setBoardDarkColor(Color boardDarkColor) {
        this.boardDarkColor = boardDarkColor;
    }
}

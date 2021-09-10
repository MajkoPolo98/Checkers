package com.project.checkers;

import javafx.scene.paint.Color;

public class Settings {
    private Color pawnLowerColor = Color.WHEAT;
    private Color pawnUpperColor = Color.SADDLEBROWN;
    private Color boardLightColor = Color.WHITESMOKE;
    private Color boardDarkColor = Color.DIMGREY;


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

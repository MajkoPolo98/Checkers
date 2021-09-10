package com.project.checkers;

import java.awt.*;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


public class Board {

    GridPane grid = new GridPane();
    private Color lightColor = Color.WHITESMOKE;
    private Color darkColor = Color.DIMGREY;
    Settings settings;
    private Pawn[][] piece;
    public Board(Settings settings) {
        this.settings = settings;
    }

    public void setLightColor(Color lightColor) {
        this.lightColor = lightColor;
    }

    public void setDarkColor(Color darkColor) {
        this.darkColor = darkColor;
    }

    public GridPane makeBoard() {

        for (int i = 0; i < 8; i += 1) {
            ColumnConstraints column = new ColumnConstraints(100);
            column.setHalignment(HPos.CENTER);
            RowConstraints row = new RowConstraints(100);
            row.setValignment(VPos.CENTER);
            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
            for (int j = 0; j < 8; j += 1) {
                if ((i + j) % 2 == 0) {
                    grid.add(new Rectangle(100, 100, settings.getBoardLightColor()), i, j);
                } else {
                    grid.add(new Rectangle(100, 100, settings.getBoardDarkColor()), i, j);
                }
            }
        }
        for (int i = 0; i < 8; i+= 1){
            for (int j = 0; j < 3; j+=1) {
                if((i+j)%2 == 0){
                    new Pawn(grid,false,false, settings.getPawnUpperColor(), i, j).makePawn();
                } else {
                    new Pawn(grid,false,true,settings.getPawnLowerColor(),i, (7-j)).makePawn();
                }
            }
        }

        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(25,25,25,25));
        return grid;
    }

    public int getIndexFromCoord(int x, int y){
        return (x+y)*y;
    }
}
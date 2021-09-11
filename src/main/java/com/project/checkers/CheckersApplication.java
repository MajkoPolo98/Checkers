package com.project.checkers;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CheckersApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Settings settings = new Settings();
        Board board = new Board(settings);
        GridPane gridPane = board.makeBoard();

        for (int i = 0; i < 8; i+= 1){
            for (int j = 0; j < 3; j+=1) {
                if((i+j)%2 == 0){
                    new Pawn(board,false,false, settings.getPawnUpperColor(), i, j).makePawn();
                    board.boardData[i][j] = "upper";
                } else {
                    new Pawn(board,false,true,settings.getPawnLowerColor(),i, (7-j)).makePawn();
                    board.boardData[i][7-j] = "lower";
                }
            }
        }

        Scene scene = new Scene(gridPane, 850, 850);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package com.project.checkers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;


public class Menu {

    boolean gameInProgress = false;
    private Settings settings = new Settings();
    private boolean isSingleplayerGame;
    private Scene gameScene;

    public Scene mainMenu(Stage primaryStage){
        VBox mainMenuVBox = new VBox();
        Label menuTitle = new Label("MENU - CHECKERS");
        menuTitle.setTextFill(Color.BLUE);
        menuTitle.setFont(new Font(60));
        Button singleplayer = new Button("Play singleplayer");
        Button multiplayer = new Button("Play multiplayer");
        Button options = new Button("settings");
        Button continueGame = new Button("Continue");
        mainMenuVBox.setSpacing(25);
        mainMenuVBox.setAlignment(Pos.CENTER);
        mainMenuVBox.getChildren().addAll(menuTitle, singleplayer, multiplayer, options);

        BackgroundFill backgroundFill = new BackgroundFill(settings.getBackgroundColor(), null, null);
        Background background = new Background(backgroundFill);
        mainMenuVBox.setBackground(background);

        if(gameInProgress){
            mainMenuVBox.getChildren().add(1, continueGame);
            System.out.println();
        }
        Scene menuScene = new Scene(mainMenuVBox, 850, 850);


        //Options button
        options.setOnMouseClicked(b1 -> {
            primaryStage.setScene(optionMenu(primaryStage));
        });

        //Multiplayer button
        multiplayer.setOnMouseClicked(b1 -> {
            this.isSingleplayerGame = false;
            primaryStage.setScene(gameRunner(primaryStage));
            this.gameInProgress = true;
            menuScene.getWindow();
        });

        //Singleplayer buttor
        singleplayer.setOnMouseClicked(e -> {
            this.isSingleplayerGame = true;
            primaryStage.setScene(gameRunner(primaryStage));
            this.gameInProgress = true;
        });

        //Continue button
        continueGame.setOnMouseClicked(e -> {
            primaryStage.setScene(gameScene);
        });

        return menuScene;
    }

    public Scene optionMenu(Stage primaryStage){
        VBox optionsVBox = new VBox();
        BackgroundFill backgroundFill = new BackgroundFill(settings.getBackgroundColor(), null, null);
        Background background = new Background(backgroundFill);
        optionsVBox.setBackground(background);
        Label optionTitle = new Label("Options");
        Button normalModeColors = new Button("Normal colors");
        Button darkModeColors = new Button("Night mode colors");
        Button goBack = new Button("Previous page");
        optionsVBox.setSpacing(25);
        optionsVBox.setAlignment(Pos.CENTER);
        optionsVBox.getChildren().addAll(optionTitle, normalModeColors, darkModeColors, goBack);
        Scene optionsScene = new Scene(optionsVBox, 850, 850);
        goBack.setOnMouseClicked(e -> {
            primaryStage.setScene(mainMenu(primaryStage));
        });

        darkModeColors.setOnMouseClicked(e -> {
            settings.darkMode();
            //primaryStage.getScene().setFill(Paint.valueOf("#FF00FF"));


            optionsVBox.setStyle("-fx-background-color: #696969");
            primaryStage.setScene(optionsScene);
            primaryStage.show();
        });

        normalModeColors.setOnMouseClicked(e -> {
            settings.normalMode();
            optionsVBox.setStyle("-fx-background-color: #F5F5F5");
            primaryStage.setScene(optionsScene);
            primaryStage.show();
        });

        return optionsScene;
    }

    public Scene gameRunner(Stage primaryStage){
        Board board = new Board(settings);
        board.setSingleplayerGame(isSingleplayerGame);
        GridPane boardGridPane = board.makeBoard();
        Scene gameScene = new Scene(boardGridPane, 850, 850);
        boardGridPane.setOnMouseClicked(e -> {
            int yCoord = (int) (e.getY()-25)/100;
            int xCoord = (int) (e.getX()-25)/100;
            board.doClick(xCoord, yCoord);
        });

        boardGridPane.setOnScroll(e -> {
            System.out.println();
        });


        gameScene.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ESCAPE) {
                primaryStage.setScene(mainMenu(primaryStage));
            }
        });
        this.gameScene = gameScene;

        return gameScene;
    }



}

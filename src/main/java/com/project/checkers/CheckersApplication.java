package com.project.checkers;
import javafx.application.Application;
import javafx.stage.Stage;
import java.lang.*;



public class CheckersApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Menu menu = new Menu();
        primaryStage.setScene(menu.mainMenu(primaryStage));

        primaryStage.setTitle("Checkers");
        primaryStage.show();


    }
}

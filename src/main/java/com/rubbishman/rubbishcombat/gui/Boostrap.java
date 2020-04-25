package com.rubbishman.rubbishcombat.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Boostrap extends Application {
    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HealthBarPane hpPane = new HealthBarPane(stage);

        stage.show();
    }
}

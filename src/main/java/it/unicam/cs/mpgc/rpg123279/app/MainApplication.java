package it.unicam.cs.mpgc.rpg123279.app;

import it.unicam.cs.mpgc.rpg123279.repository.HibernateConfig;
import it.unicam.cs.mpgc.rpg123279.controller.ControllerMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HibernateConfig.getSessionFactory();
        AppConfig appConfig = new AppConfig();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
        Parent root = loader.load();
        ControllerMenu menuController = loader.getController();
        menuController.setAppConfig(appConfig);
        primaryStage.setTitle("Jack Sparrow's Curse");
        primaryStage.setScene(new Scene(root, 900, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> HibernateConfig.shutdown());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
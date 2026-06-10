package it.unicam.cs.mpgc.rpg123279.controller;

import it.unicam.cs.mpgc.rpg123279.app.AppConfig;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerVittoria implements Initializable {

    @FXML private Label  lblMessaggio;
    @FXML private Button btnMenu;
    private AppConfig appConfig;

    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    public void setAppConfig(AppConfig appConfig, Giocatore giocatore) {
        this.appConfig = appConfig;
        lblMessaggio.setText(giocatore.getNome() + ", hai sconfitto il Kraken e liberato i mari dei Caraibi.\n"
                + "Livello raggiunto: " + giocatore.getLivello() + "\n" + "Oro accumulato: " + giocatore.getOro()
        );
    }

    @FXML
    private void onTornaAlMenu() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/menu.fxml"));
            Parent root = loader.load();
            ControllerMenu controller = loader.getController();
            controller.setAppConfig(appConfig);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cambiaScena(Parent root) {
        Stage stage = (Stage) btnMenu.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
package it.unicam.cs.mpgc.rpg123279.controller;

import it.unicam.cs.mpgc.rpg123279.app.AppConfig;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.ClassePersonaggio;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCreaPersonaggio implements Initializable {

    @FXML private TextField txtNome;
    @FXML private RadioButton rbCorsaro;
    @FXML private RadioButton rbCapitano;
    @FXML private RadioButton rbCacciatore;
    @FXML private Label lblDescrizioneClasse;
    @FXML private Label lblStatClasse;
    @FXML private Label lblErrore;

    private ToggleGroup toggleClasse;
    private AppConfig appConfig;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toggleClasse = new ToggleGroup();
        rbCorsaro.setToggleGroup(toggleClasse);
        rbCapitano.setToggleGroup(toggleClasse);
        rbCacciatore.setToggleGroup(toggleClasse);
        rbCorsaro.setSelected(true);
        lblErrore.setVisible(false);
        toggleClasse.selectedToggleProperty().addListener((obs, old, nw) -> aggiornaDescrizione());
        aggiornaDescrizione();
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @FXML
    private void onConferma() {
        String nome = txtNome.getText().trim();
        if (nome.isBlank()) {
            lblErrore.setText("Inserisci un nome per il personaggio.");
            lblErrore.setVisible(true);
            return;
        }
        Giocatore giocatore = new Giocatore(nome, getClasseSelezionata());
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/navigazione.fxml"));
            Parent root = loader.load();
            ControllerNavigazione ctrl = loader.getController();
            ctrl.setAppConfig(appConfig, giocatore);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onIndietro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/menu.fxml"));
            Parent root = loader.load();
            ControllerMenu ctrl = loader.getController();
            ctrl.setAppConfig(appConfig);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ClassePersonaggio getClasseSelezionata() {
        RadioButton sel = (RadioButton) toggleClasse.getSelectedToggle();
        return switch (sel.getId()) {
            case "rbCapitano" -> ClassePersonaggio.CAPITANO;
            case "rbCacciatore" -> ClassePersonaggio.CACCIATORE;
            default -> ClassePersonaggio.CORSARO;
        };
    }

    private void aggiornaDescrizione() {
        ClassePersonaggio c = getClasseSelezionata();
        lblDescrizioneClasse.setText(switch (c) {
            case CORSARO -> "Attacco e difesa in egual misura: il punto di partenza di ogni buon pirata.";
            case CAPITANO -> "Se affonda lo fa insieme alla nave, difficile da affondare.";
            case CACCIATORE -> "Affonda i nemici prima che possano reagire... o viene affondato lui.";
        });
        lblStatClasse.setText("HP: " + c.getMaxHpBase() + "   ATK: " + c.getAttaccoBase() + "   DEF: " + c.getDifesaBase());
    }

    private void cambiaScena(Parent root) {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
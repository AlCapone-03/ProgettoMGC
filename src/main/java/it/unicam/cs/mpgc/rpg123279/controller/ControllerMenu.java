package it.unicam.cs.mpgc.rpg123279.controller;

import it.unicam.cs.mpgc.rpg123279.app.AppConfig;
import it.unicam.cs.mpgc.rpg123279.model.PartitaRipristinata;
import it.unicam.cs.mpgc.rpg123279.model.Salvataggio;
import it.unicam.cs.mpgc.rpg123279.service.IServiceSalvataggio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {

    @FXML private VBox boxSalvataggi;

    private AppConfig appConfig;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boxSalvataggi.setVisible(false);
        boxSalvataggi.setManaged(false);
    }

    public void setAppConfig(AppConfig appConfig) {this.appConfig = appConfig;}

    @FXML
    private void onNuovaPartita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/creaPersonaggio.fxml"));
            Parent root = loader.load();
            ControllerCreaPersonaggio ctrl = loader.getController();
            ctrl.setAppConfig(appConfig);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onCaricaPartita() {
        boolean visibile = boxSalvataggi.isVisible();
        if (!visibile) mostraSalvataggi();
        boxSalvataggi.setVisible(!visibile);
        boxSalvataggi.setManaged(!visibile);
    }

    @FXML
    private void onEsci() {System.exit(0);}

    private void mostraSalvataggi() {
        boxSalvataggi.getChildren().clear();
        IServiceSalvataggio serviceSalvataggio = appConfig.getServiceSalvataggio();
        List<Salvataggio> lista = serviceSalvataggio.getSalvataggiByDate();
        if (lista.isEmpty()) {
            boxSalvataggi.getChildren().add(new Label("Nessun salvataggio trovato."));
            return;
        }
        for (Salvataggio s : lista) {
            String etichetta = s.getNomeSlot() + "  —  " + s.getGiocatore().getNome()
                    + "  Lv." + s.getGiocatore().getLivello() + "  (" + s.getNomeIsolaCorrente() + ")"
                    + "  " + s.getDataSalvataggio().toLocalDate();
            Button btnCarica = new Button(etichetta);
            HBox.setHgrow(btnCarica, Priority.ALWAYS);
            btnCarica.setMaxWidth(Double.MAX_VALUE);
            btnCarica.setOnAction(e -> caricaSalvataggio(s.getNomeSlot()));
            Button btnElimina = new Button("Elimina");
            btnElimina.setOnAction(e -> eliminaSalvataggio(s.getNomeSlot()));
            HBox riga = new HBox(6, btnCarica, btnElimina);
            boxSalvataggi.getChildren().add(riga);
        }
    }

    private void eliminaSalvataggio(String nomeSlot) {
        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.setTitle("Elimina salvataggio");
        conferma.setHeaderText("Vuoi eliminare lo slot '" + nomeSlot + "'?");
        conferma.showAndWait().ifPresent(risposta -> {
            if (risposta == ButtonType.OK) {
                try {
                    appConfig.getServiceSalvataggio().elimina(nomeSlot);
                    mostraSalvataggi();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Errore nell'eliminazione: " + ex.getMessage(), ButtonType.OK)
                            .showAndWait();
                }
            }
        });
    }

    private void caricaSalvataggio(String nomeSlot) {
        try {
            PartitaRipristinata partita = appConfig.getServiceSalvataggio().ripristinaPartita(nomeSlot, appConfig.getMappa());
            appConfig.reinizializzaNavigazione(partita.isolaCorrente());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/navigazione.fxml"));
            Parent root = loader.load();
            ControllerNavigazione ctrl = loader.getController();
            ctrl.setAppConfig(appConfig, partita.giocatore());
            cambiaScena(root);
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR,"Errore nel caricamento: " + ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    private void cambiaScena(Parent root) {
        Stage stage = (Stage) boxSalvataggi.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
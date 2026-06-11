package it.unicam.cs.mpgc.rpg123279.controller;

import it.unicam.cs.mpgc.rpg123279.app.AppConfig;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractEquipaggiamento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.IUsable;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Arma;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Armatura;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Reliquia;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.service.IServiceInventario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInventario implements Initializable {

    @FXML private Label lblNome;
    @FXML private Label lblClasse;
    @FXML private Label lblLivello;
    @FXML private Label lblHp;
    @FXML private Label lblAttacco;
    @FXML private Label lblDifesa;
    @FXML private Label lblOro;
    @FXML private Label lblArmaEquip;
    @FXML private Label lblArmaturaEquip;
    @FXML private Label lblReliquiaEquip;
    @FXML private ListView<String> listInventario;
    @FXML private Label lblDettaglio;
    @FXML private HBox boxAzioni;
    @FXML private Button btnEquipaggia;
    @FXML private Button btnUsa;
    @FXML private Button btnScarta;
    private AppConfig appConfig;
    private Giocatore giocatore;
    private AbstractOggetto oggettoSelezionato;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boxAzioni.setVisible(false);
        boxAzioni.setManaged(false);
        lblDettaglio.setText("");
        listInventario.getSelectionModel().selectedIndexProperty()
                .addListener((obs, old, nw) -> onSelezione(nw.intValue()));
    }

    public void setContesto(AppConfig appConfig, Giocatore giocatore) {
        this.appConfig = appConfig;
        this.giocatore = giocatore;
        aggiornaUI();
    }

    private void aggiornaUI() {
        lblNome.setText(giocatore.getNome());
        lblClasse.setText("Classe: " + giocatore.getClassePersonaggio().name());
        lblLivello.setText("Livello: " + giocatore.getLivello());
        lblHp.setText("HP: " + giocatore.getHp() + " / " + giocatore.getMaxHp());
        lblAttacco.setText("ATK: " + giocatore.getAttacco());
        lblDifesa.setText("DEF: " + giocatore.getDifesa());
        lblOro.setText("Oro: " + giocatore.getOro());

        Arma arma = giocatore.getArmaEquipaggiata();
        Armatura armatura = giocatore.getArmaturaEquipaggiata();
        Reliquia reliquia = giocatore.getReliquiaEquipaggiata();
        lblArmaEquip.setText("Arma: " + (arma != null ? arma.getNome() : "—"));
        lblArmaturaEquip.setText("Armatura: " + (armatura != null ? armatura.getNome() : "—"));
        lblReliquiaEquip.setText("Reliquia: " + (reliquia != null ? reliquia.getNome() : "—"));
        listInventario.getItems().clear();
        for (AbstractOggetto o : giocatore.getInventario()) {
            listInventario.getItems().add(o.getNome() + "  (" + o.getRarita() + ")  " + o.getTipoOggetto());
        }
        boxAzioni.setVisible(false);
        boxAzioni.setManaged(false);
        lblDettaglio.setText("");
        oggettoSelezionato = null;
    }

    private void onSelezione(int indice) {
        if (indice < 0 || indice >= giocatore.getInventario().size()) {
            boxAzioni.setVisible(false);
            boxAzioni.setManaged(false);
            return;
        }
        oggettoSelezionato = giocatore.getInventario().get(indice);
        String dettaglio = oggettoSelezionato.getNome() + "\n" + oggettoSelezionato.getDescrizione() + "\n" + "Rarita': "
                + oggettoSelezionato.getRarita() + "\n" + "Valore: " + oggettoSelezionato.getValore() + " oro";
        if (oggettoSelezionato instanceof Reliquia r && r.getEffettoSpeciale() != null) {
            dettaglio += "\nEffetto: " + r.getEffettoSpeciale();
        }
        lblDettaglio.setText(dettaglio);
        boolean isEquipaggiamento = oggettoSelezionato instanceof AbstractEquipaggiamento;
        boolean isUsable = oggettoSelezionato instanceof IUsable;
        btnEquipaggia.setVisible(isEquipaggiamento);
        btnEquipaggia.setManaged(isEquipaggiamento);
        btnUsa.setVisible(isUsable && !isEquipaggiamento);
        btnUsa.setManaged(isUsable && !isEquipaggiamento);
        boxAzioni.setVisible(true);
        boxAzioni.setManaged(true);
    }

    @FXML
    private void onEquipaggia() {
        if (!(oggettoSelezionato instanceof AbstractEquipaggiamento eq)) return;
        giocatore.equipaggia(eq);
        aggiornaUI();
    }

    @FXML
    private void onUsa() {
        if (oggettoSelezionato == null) return;
        IServiceInventario serviceInventario = appConfig.getServiceInventario();
        try {
            serviceInventario.usaOggetto(giocatore, oggettoSelezionato);
            aggiornaUI();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.WARNING, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    @FXML
    private void onScarta() {
        if (oggettoSelezionato == null) return;
        appConfig.getServiceInventario().rimuoviOggetto(giocatore, oggettoSelezionato);
        aggiornaUI();
    }

    @FXML
    private void onTornaAlMare() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/navigazione.fxml"));
            Parent root = loader.load();
            ControllerNavigazione controller = loader.getController();
            controller.setAppConfig(appConfig, giocatore);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cambiaScena(Parent root) {
        Stage stage = (Stage) listInventario.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
package it.unicam.cs.mpgc.rpg123279.controller;

import it.unicam.cs.mpgc.rpg123279.app.AppConfig;
import it.unicam.cs.mpgc.rpg123279.model.battaglia.BattleState;
import it.unicam.cs.mpgc.rpg123279.model.battaglia.DatiTurno;
import it.unicam.cs.mpgc.rpg123279.model.battaglia.RisultatoBattaglia;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.AzioneCombattimento;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.IUsable;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici.KrakenBoss;
import it.unicam.cs.mpgc.rpg123279.service.IServiceCombattimento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerBattaglia implements Initializable {

    @FXML private Label lblNomeGiocatore;
    @FXML private Label lblHpGiocatore;
    @FXML private Label lblNomeNemico;
    @FXML private Label lblLivelloNemico;
    @FXML private Label lblHpNemico;
    @FXML private Button btnAttacca;
    @FXML private Button btnUsaOggetto;
    @FXML private Button btnScappa;
    @FXML private Button btnTornaNavigazione;
    @FXML private VBox boxOggetti;
    @FXML private TextArea txtLog;
    private AppConfig appConfig;
    private Giocatore giocatore;
    private AbstractNemico nemico;
    private Isola isolaCorrente;
    private boolean isBoss;
    private BattleState battleState;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boxOggetti.setVisible(false);
        boxOggetti.setManaged(false);
    }

    public void setContesto(AppConfig appConfig, Giocatore giocatore, AbstractNemico nemico, Isola isolaCorrente, boolean isBoss) {
        this.appConfig = appConfig;
        this.giocatore = giocatore;
        this.nemico = nemico;
        this.isolaCorrente = isolaCorrente;
        this.isBoss = isBoss;
        IServiceCombattimento service = appConfig.getServiceCombattimento();
        battleState = service.iniziaBattaglia(giocatore, nemico);
        aggiornaUI();
        log("BATTAGLIA INIZIATA");
        log(giocatore.getNome() + " (Lv." + giocatore.getLivello() + ")" + "  vs  " + nemico.getNome()
                + " (Lv." + nemico.getLivello() + ")");
        if (isBoss) log("SCONTRO CON IL BOSS DELL'ISOLA");
    }

    @FXML
    private void onAttacca() {
        eseguiAzione(AzioneCombattimento.ATTACCA, null);
    }

    @FXML
    private void onUsaOggetto() {
        boolean aperto = boxOggetti.isVisible();
        if (!aperto) popolaListaOggetti();
        boxOggetti.setVisible(!aperto);
        boxOggetti.setManaged(!aperto);
    }

    @FXML
    private void onScappa() {
        if (isBoss) {
            log("Non puoi fuggire dal boss dell'isola!");
            return;
        }
        eseguiAzione(AzioneCombattimento.SCAPPA, null);
    }

    private void popolaListaOggetti() {
        boxOggetti.getChildren().clear();
        List<AbstractOggetto> usabili = giocatore.getInventario().stream().filter(o -> o instanceof IUsable).toList();
        if (usabili.isEmpty()) {
            boxOggetti.getChildren().add(new Label("Nessun oggetto usabile."));
            return;
        }
        for (AbstractOggetto oggetto : usabili) {
            Button btn = new Button(oggetto.getNome() + "  (" + oggetto.getRarita() + ")");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> {
                boxOggetti.setVisible(false);
                boxOggetti.setManaged(false);
                eseguiAzione(AzioneCombattimento.USA_OGGETTO, oggetto);
            });
            boxOggetti.getChildren().add(btn);
        }
    }

    private void eseguiAzione(AzioneCombattimento azione, AbstractOggetto oggetto) {
        IServiceCombattimento serviceCombattimento = appConfig.getServiceCombattimento();
        DatiTurno turno = serviceCombattimento.eseguiTurno(battleState, azione, oggetto);
        log(turno.getDescrizione());
        aggiornaUI();
        if (!giocatore.isVivo() || !nemico.isVivo() || battleState.isBattagliaFinita()) {
            concludi();
        }
    }

    private void concludi() {
        IServiceCombattimento svc = appConfig.getServiceCombattimento();
        RisultatoBattaglia risultato = svc.concludiBattaglia(battleState);
        disabilitaAzioni();
        if (risultato.isVittoria()) {
            log("VITTORIA!");
            log("XP: +" + risultato.getXpGuadagnati() + "  Oro: +" + risultato.getOroGuadagnato());
            if (risultato.haOggettoDroppato())
                log("Oggetto trovato: " + risultato.getOggettoDroppato().getNome() + " ("
                        + risultato.getOggettoDroppato().getRarita() + ")");
            if (isBoss) gestisciVittoriaBoss();
            else mostraBottoneTornaNavigazione();
        } else if (risultato.isSconfitta()) {
            log("SCONFITTA");
            apriGameOver();
        } else {
            log("SEI FUGGITO");
            mostraBottoneTornaNavigazione();
        }
    }

    private void gestisciVittoriaBoss() {
        isolaCorrente.setBossSconfitto(true);
        appConfig.getMappa().sbloccaIsolaSuccessiva(isolaCorrente);
        if (nemico instanceof KrakenBoss && nemico.getLivello() == 30) {
            log("HAI COMPLETATO IL GIOCO!");
            log("Il Kraken e' stato sconfitto. I mari dei Caraibi sono finalmente liberi.");
            apriVittoria();
        } else {
            log("Il boss e' stato sconfitto! Una nuova rotta si e' aperta.");
            mostraBottoneTornaNavigazione();
        }
    }

    @FXML
    private void onTornaNavigazione() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/navigazione.fxml"));
            Parent root = loader.load();
            ControllerNavigazione controller = loader.getController();
            controller.setAppConfig(appConfig, giocatore);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostraBottoneTornaNavigazione() {
        btnTornaNavigazione.setVisible(true);
        btnTornaNavigazione.setManaged(true);
    }

    private void apriGameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/gameOver.fxml"));
            Parent root = loader.load();
            ControllerGameOver controller = loader.getController();
            controller.setAppConfig(appConfig);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void apriVittoria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/vittoria.fxml"));
            Parent root = loader.load();
            ControllerVittoria controller = loader.getController();
            controller.setAppConfig(appConfig, giocatore);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void aggiornaUI() {
        lblNomeGiocatore.setText(giocatore.getNome());
        lblHpGiocatore.setText("HP: " + giocatore.getHp() + " / " + giocatore.getMaxHp());
        lblNomeNemico.setText(nemico.getNome());
        lblLivelloNemico.setText("Livello: " + nemico.getLivello());
        lblHpNemico.setText("HP: " + nemico.getHp() + " / " + nemico.getMaxHp());
    }

    private void disabilitaAzioni() {
        btnAttacca.setDisable(true);
        btnUsaOggetto.setDisable(true);
        btnScappa.setDisable(true);
    }

    private void log(String msg) {
        txtLog.appendText(msg + "\n");
    }

    private void cambiaScena(Parent root) {
        Stage stage = (Stage) txtLog.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
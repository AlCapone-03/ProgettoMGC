package it.unicam.cs.mpgc.rpg123279.controller;

import it.unicam.cs.mpgc.rpg123279.app.AppConfig;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;
import it.unicam.cs.mpgc.rpg123279.model.eventi.*;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.mappe.MappaGioco;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Rotta;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici.KrakenBoss;
import it.unicam.cs.mpgc.rpg123279.service.IServiceInventario;
import it.unicam.cs.mpgc.rpg123279.service.IServiceNavigazione;
import it.unicam.cs.mpgc.rpg123279.service.IServiceSalvataggio;
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

public class ControllerNavigazione implements Initializable {

    @FXML private Label lblNome;
    @FXML private Label lblClasse;
    @FXML private Label lblLivello;
    @FXML private Label lblHp;
    @FXML private Label lblXp;
    @FXML private Label lblOro;
    @FXML private Label lblIsola;
    @FXML private Label lblDescIsola;
    @FXML private Label lblLvlConsigliato;
    @FXML private Label lblStatoBoss;
    @FXML private VBox  boxRotte;
    @FXML private Button btnSfidaBoss;
    @FXML private TextArea txtLog;

    private AppConfig appConfig;
    private Giocatore giocatore;

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    public void setAppConfig(AppConfig appConfig, Giocatore giocatore) {
        this.appConfig = appConfig;
        this.giocatore = giocatore;
        aggiornaUI();
        log("Sei arrivato a " + appConfig.getServiceNavigazione().getIsolaCorrente().getNome());
    }

    private void aggiornaUI() {
        lblNome.setText(giocatore.getNome());
        lblClasse.setText("Classe: " + giocatore.getClassePersonaggio().name());
        lblLivello.setText("Livello: " + giocatore.getLivello());
        lblHp.setText("HP: " + giocatore.getHp() + " / " + giocatore.getMaxHp());
        lblXp.setText("XP: " + giocatore.getEsperienza());
        lblOro.setText("Oro: " + giocatore.getOro());
        Isola isola = appConfig.getServiceNavigazione().getIsolaCorrente();
        lblIsola.setText(isola.getNome());
        lblDescIsola.setText(isola.getDescrizione());
        lblLvlConsigliato.setText("Livello consigliato: " + isola.getLivelloConsigliato());
        KrakenBoss boss = isola.getBoss();
        if (isola.isBossSconfitto()) {
            lblStatoBoss.setText("Boss: SCONFITTO");
            btnSfidaBoss.setDisable(true);
        } else {
            lblStatoBoss.setText("Boss: Kraken Lv." + boss.getLivello() + " da sconfiggere");
            btnSfidaBoss.setDisable(false);
        }
        aggiornaRotte();
    }

    private void aggiornaRotte() {
        boxRotte.getChildren().clear();
        List<Rotta> rotte = appConfig.getServiceNavigazione().getRotteDisponibili();
        if (rotte.isEmpty()) {
            boxRotte.getChildren().add(new Label("Nessuna rotta disponibile. Sconfiggi il boss per proseguire."));
            return;
        }
        for (Rotta r : rotte) {
            String etichetta = r.getDestinazione().getNome() + "  (Lv." + r.getDestinazione().getLivelloConsigliato()
                    + ")" + "  — Pericolo: " + r.getPericolo() + "/10";
            Button btn = new Button(etichetta);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(ignored -> naviga(r));
            boxRotte.getChildren().add(btn);
        }
    }

    private void naviga(Rotta rotta) {
        IServiceNavigazione nav = appConfig.getServiceNavigazione();
        log("Navigazione verso " + rotta.getDestinazione().getNome() + "...");
        AbstractGameEvent evento = nav.naviga(rotta, giocatore);
        log(evento.getDescrizione());
        if (evento.getTipoEvento() == TipoEvento.BATTAGLIA) {
            var nemico = ((EventoBattaglia) evento).getNemico();
            apriCombattimento(nemico, false);
            return;
        }
        if (evento.getTipoEvento() == TipoEvento.TESORO) {
            gestisciTesoro((EventoTesoro) evento);
        } else if (evento.getTipoEvento() == TipoEvento.MERCANTE) {
            gestisciMercante((EventoMercante) evento);
        }
        aggiornaUI();
    }

    @FXML
    private void onEsplora() {
        IServiceNavigazione nav = appConfig.getServiceNavigazione();
        log("Esplori i dintorni di " + nav.getIsolaCorrente().getNome() + "...");
        AbstractGameEvent evento = nav.esplora(giocatore);
        log(evento.getDescrizione());
        if (evento.getTipoEvento() == TipoEvento.BATTAGLIA) {
            apriCombattimento(((EventoBattaglia) evento).getNemico(), false);
            return;
        }
        if (evento.getTipoEvento() == TipoEvento.TESORO) {
            gestisciTesoro((EventoTesoro) evento);
        } else if (evento.getTipoEvento() == TipoEvento.MERCANTE) {
            gestisciMercante((EventoMercante) evento);
        }
        aggiornaUI();
    }

    @FXML
    private void onSfidaBoss() {
        Isola isola = appConfig.getServiceNavigazione().getIsolaCorrente();
        if (isola.isBossSconfitto()) return;
        log("Hai sfidato il boss dell'isola: Kraken Lv." + isola.getBoss().getLivello());
        apriCombattimento(isola.getBoss(), true);
    }

    private void gestisciTesoro(EventoTesoro et) {
        IServiceInventario inventario = appConfig.getServiceInventario();
        if (et.getOro() > 0) {
            giocatore.aggiungiOro(et.getOro());
            log("Hai guadagnato " + et.getOro() + " monete d'oro.");
        }
        if (et.getOggetto() != null) {
            try {
                inventario.aggiungiOggetto(giocatore, et.getOggetto());
                log("Hai trovato: " + et.getOggetto().getNome() + " (" + et.getOggetto().getRarita() + ")");
            } catch (Exception ex) {
                log("Inventario pieno, " + et.getOggetto().getNome() + " non e' stata raccolta.");
            }
        }
    }

    private void gestisciMercante(EventoMercante em) {
        IServiceInventario inventario = appConfig.getServiceInventario();
        log("Il mercante ha " + em.getCatalogo().size() + " oggetti in vendita.");
        log("Oro disponibile: " + giocatore.getOro());
        em.getCatalogo().forEach((oggetto, prezzo) -> {
            String testo = oggetto.getNome() + " (" + oggetto.getRarita() + ")" + ", " + prezzo + " oro";
            ButtonType btnCompra = new ButtonType("Compra");
            ButtonType btnSalta  = new ButtonType("Salta");
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, testo, btnCompra, btnSalta);
            dialog.setTitle("Mercante");
            dialog.setHeaderText("Vuoi acquistare questo oggetto?");
            dialog.showAndWait().ifPresent(r -> {
                if (r == btnCompra) {
                    try {
                        inventario.acquista(giocatore, oggetto);
                        log("Acquistato: " + oggetto.getNome() + " per " + prezzo + " oro.");
                    } catch (Exception ex) {
                        log("Acquisto fallito: " + ex.getMessage());
                    }
                }
            });
        });
    }

    private void apriCombattimento(AbstractNemico nemico, boolean isBoss) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/battaglia.fxml"));
            Parent root = loader.load();
            ControllerBattaglia ctrl = loader.getController();
            ctrl.setContesto(appConfig, giocatore, nemico, appConfig.getServiceNavigazione().getIsolaCorrente(), isBoss);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onApriInventario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/inventario.fxml"));
            Parent root = loader.load();
            ControllerInventario ctrl = loader.getController();
            ctrl.setContesto(appConfig, giocatore);
            cambiaScena(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onSalva() {
        String isola = appConfig.getServiceNavigazione().getIsolaCorrente().getNome();
        MappaGioco mappa = appConfig.getMappa();
        IServiceSalvataggio svc = appConfig.getServiceSalvataggio();
        TextInputDialog dialog = new TextInputDialog("Slot");
        dialog.setTitle("Salva partita");
        dialog.setHeaderText("Inserisci il nome dello slot:");
        dialog.setContentText("Nome slot:");
        dialog.showAndWait().ifPresent(nomeSlot -> {
            if (!nomeSlot.isBlank()) {
                try {
                    svc.salva(giocatore, nomeSlot, isola, mappa);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/mpgc/rpg123279/view/menu.fxml"));
                    Parent root = loader.load();
                    ControllerMenu ctrl = loader.getController();
                    ctrl.setAppConfig(appConfig);
                    cambiaScena(root);
                } catch (Exception ex) {
                    log("Errore nel salvataggio: " + ex.getMessage());
                }
            }
        });
    }


    private void log(String msg) {
        txtLog.appendText(msg + "\n");
    }

    private void cambiaScena(Parent root) {
        Stage stage = (Stage) txtLog.getScene().getWindow();
        stage.getScene().setRoot(root);
    }
}
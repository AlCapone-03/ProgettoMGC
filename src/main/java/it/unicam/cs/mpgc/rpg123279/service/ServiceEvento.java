package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.factory.IFactoryNemico;
import it.unicam.cs.mpgc.rpg123279.factory.IFactoryOggetto;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;
import it.unicam.cs.mpgc.rpg123279.model.eventi.*;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Rotta;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.Costanti;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ServiceEvento implements IServiceEvento {

    private final IFactoryNemico factoryNemico;
    private final IFactoryOggetto factoryOggetto;
    private final Random random;

    public ServiceEvento(IFactoryNemico factoryNemico, IFactoryOggetto factoryOggetto) {
        this.factoryNemico= factoryNemico;
        this.factoryOggetto= factoryOggetto;
        this.random= new Random();
    }

    @Override
    public AbstractGameEvent generaEventoNavigazione(Rotta rotta, int livelloGiocatore, Isola isola) {
        if (random.nextDouble() >= rotta.getProbabilitaIncontro()) {
            return new EventoVuoto();
        }
        return generaEventoEsplorazione(rotta.getPericolo(), livelloGiocatore, isola);
    }

    @Override
    public AbstractGameEvent generaEventoEsplorazione(int pericolo, int livelloGiocatore, Isola isola) {
        return switch (calcolaTipoEvento(pericolo)) {
            case BATTAGLIA -> creaEventoBattaglia(livelloGiocatore, isola.getNemiciPossibili());
            case TESORO -> creaEventoTesoro(livelloGiocatore);
            case MERCANTE -> creaEventoMercante(livelloGiocatore);
            default -> new EventoVuoto();
        };
    }

    private TipoEvento calcolaTipoEvento(int pericolo) {
        double n = random.nextDouble();
        if (pericolo >= Costanti.PERICOLO_ALTO) {
            if (n < 0.80) return TipoEvento.BATTAGLIA;
            if (n < 0.95) return TipoEvento.TESORO;
            return TipoEvento.MERCANTE;
        }
        if (pericolo >= Costanti.PERICOLO_MEDIO) {
            if (n < 0.60) return TipoEvento.BATTAGLIA;
            if (n < 0.90) return TipoEvento.TESORO;
            return TipoEvento.MERCANTE;
        }
        if (n < 0.40) return TipoEvento.BATTAGLIA;
        if (n < 0.85) return TipoEvento.TESORO;
        return TipoEvento.MERCANTE;
    }

    private EventoBattaglia creaEventoBattaglia(int livelloGiocatore, List<Class<? extends AbstractNemico>> nemiciPossibili) {
        AbstractNemico nemico = factoryNemico.creaNemicoPerIsola(nemiciPossibili, livelloGiocatore);
        return new EventoBattaglia(nemico);
    }

    private EventoTesoro creaEventoTesoro(int livelloGiocatore) {
        AbstractOggetto oggetto = random.nextDouble() < Costanti.PROBABILITA_ITEM_IN_TESORO ?
                factoryOggetto.creaOggettoCasuale(livelloGiocatore) : null;
        int oro = (20 + random.nextInt(60)) + (livelloGiocatore * 10);
        return new EventoTesoro(oggetto, oro);
    }

    private EventoMercante creaEventoMercante(int livelloGiocatore) {
        Map<AbstractOggetto, Integer> catalogo = new LinkedHashMap<>();
        for (int i = 0; i < Costanti.N_OGGETTI_CATALOGO; i++) {
            AbstractOggetto oggetto = factoryOggetto.creaOggettoCasuale(livelloGiocatore);
            catalogo.put(oggetto, oggetto.getValore());
        }
        return new EventoMercante(catalogo);
    }

}
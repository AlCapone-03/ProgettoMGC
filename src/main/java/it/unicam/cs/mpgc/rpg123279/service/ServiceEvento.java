package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.factory.IFactoryNemico;
import it.unicam.cs.mpgc.rpg123279.factory.IFactoryOggetto;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;
import it.unicam.cs.mpgc.rpg123279.model.eventi.*;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Rotta;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class ServiceEvento implements IServiceEvento {

    private static final int pericoloAlto = 8;
    private static final int pericoloMedio = 4;
    private static final int nOggettiCatalogo = 4;
    private final IFactoryNemico factoryNemico;
    private final IFactoryOggetto factoryOggetto;
    private final Random random;

    public ServiceEvento(IFactoryNemico factoryNemico, IFactoryOggetto factoryOggetto) {
        this.factoryNemico= factoryNemico;
        this.factoryOggetto= factoryOggetto;
        this.random= new Random();
    }

    @Override
    public AbstractGameEvent generaEvento(Rotta rotta, int livelloGiocatore) {
        if (random.nextDouble() >= rotta.getProbabilitaIncontro()) {
            return new EventoVuoto();
        }
        return switch (calcolaTipoEvento(rotta.getPericolo())) {
            case BATTAGLIA -> creaEventoBattaglia(livelloGiocatore);
            case TESORO -> creaEventoTesoro(livelloGiocatore);
            case MERCANTE -> creaEventoMercante(livelloGiocatore);
            default -> new EventoVuoto();
        };
    }

    private TipoEvento calcolaTipoEvento(int pericolo) {
        double n = random.nextDouble();
        if (pericolo >= pericoloAlto) {
            if (n < 0.85) return TipoEvento.BATTAGLIA;
            if (n < 0.95) return TipoEvento.TESORO;
            return TipoEvento.MERCANTE;
        }
        if (pericolo >= pericoloMedio) {
            if (n < 0.55) return TipoEvento.BATTAGLIA;
            if (n < 0.80) return TipoEvento.TESORO;
            return TipoEvento.MERCANTE;
        }
        if (n < 0.25) return TipoEvento.BATTAGLIA;
        if (n < 0.60) return TipoEvento.TESORO;
        return TipoEvento.MERCANTE;
    }

    private EventoBattaglia creaEventoBattaglia(int livelloGiocatore) {
        AbstractNemico nemico = factoryNemico.creaNemicoCasuale(livelloGiocatore);
        return new EventoBattaglia(nemico);
    }

    private EventoTesoro creaEventoTesoro(int livelloGiocatore) {
        AbstractOggetto oggetto = random.nextDouble() < 0.6 ?
                factoryOggetto.creaOggettoCasuale(livelloGiocatore) : null;
        int oro = (20 + random.nextInt(60)) + (livelloGiocatore * 10);
        return new EventoTesoro(oggetto, oro);
    }

    private EventoMercante creaEventoMercante(int livelloGiocatore) {
        Map<AbstractOggetto, Integer> catalogo = new LinkedHashMap<>();
        for (int i = 0; i < nOggettiCatalogo; i++) {
            AbstractOggetto oggetto = factoryOggetto.creaOggettoCasuale(livelloGiocatore);
            catalogo.put(oggetto, oggetto.getValore());
        }
        return new EventoMercante(catalogo);
    }

}
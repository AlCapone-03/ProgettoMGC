package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.exception.AzioneIrregolareException;
import it.unicam.cs.mpgc.rpg123279.model.eventi.AbstractGameEvent;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.mappe.MappaGioco;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Rotta;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import java.util.List;

public class ServiceNavigazione implements IServiceNavigazione {

    private final MappaGioco mappa;
    private final IServiceEvento serviceEvento;
    private Isola isolaCorrente;

    public ServiceNavigazione(MappaGioco mappa, IServiceEvento serviceEvento, Isola isolaIniziale) {
        this.mappa= mappa;
        this.serviceEvento= serviceEvento;
        this.isolaCorrente= isolaIniziale;
    }

    @Override
    public Isola getIsolaCorrente() {return isolaCorrente;}
    @Override
    public void setIsolaCorrente(Isola isola) {
        if (isola == null) throw new IllegalArgumentException("L'isola non può essere null.");
        this.isolaCorrente = isola;
    }

    @Override
    public List<Rotta> getRotteDisponibili() {return mappa.getRotteDaIsola(isolaCorrente);}

    @Override
    public AbstractGameEvent naviga(Rotta rotta, Giocatore giocatore) {
        controllaRotta(rotta);
        isolaCorrente = rotta.getDestinazione();
        AbstractGameEvent evento = serviceEvento.generaEventoNavigazione(rotta, giocatore.getLivello(), isolaCorrente);
        logNavigazione(rotta, evento);
        return evento;
    }

    private void controllaRotta(Rotta rotta) {
        if (rotta == null) throw new IllegalArgumentException("La rotta non può essere null.");
        if (!rotta.getOrigine().equals(isolaCorrente)) throw new AzioneIrregolareException("La rotta selezionata (" +
                rotta + ") non parte dall'isola corrente: " + isolaCorrente.getNome());
        if (!mappa.getRotteDaIsola(isolaCorrente).contains(rotta))
            throw new AzioneIrregolareException("La rotta selezionata non è raggiungibile dall'isola corrente.");
    }

    @Override
    public AbstractGameEvent esplora(Giocatore giocatore) {
        int pericolo = 3 + isolaCorrente.getOrdineSequenza() * 3;
        return serviceEvento.generaEventoEsplorazione(pericolo, giocatore.getLivello(), isolaCorrente);
    }

    private void logNavigazione(Rotta rotta, AbstractGameEvent evento) {
        System.out.println("Navigazione: " + rotta);
        System.out.println("Evento: " + evento);
    }
}
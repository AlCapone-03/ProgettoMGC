package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.exception.AzioneIrregolareException;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.IUsable;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.Costanti;

public class ServiceInventario implements IServiceInventario {

    @Override
    public boolean aggiungiOggetto(Giocatore giocatore, AbstractOggetto oggetto) {
        if (oggetto == null) {
            throw new AzioneIrregolareException("Non è possibile aggiungere un oggetto null.");
        }
        if (giocatore.getInventario().size() >= Costanti.MAX_SLOT_INVENTARIO) {
            throw new AzioneIrregolareException("Inventario pieno! Non è possibile raccogliere " + oggetto.getNome() + ".");
        }
        giocatore.aggiungiAInventario(oggetto);
        return true;
    }

    @Override
    public boolean rimuoviOggetto(Giocatore giocatore, AbstractOggetto oggetto) {
        return giocatore.rimuoviDaInventario(oggetto);
    }

    @Override
    public void usaOggetto(Giocatore giocatore, AbstractOggetto oggetto) {
        if (!(oggetto instanceof IUsable usable)) {
            throw new AzioneIrregolareException(oggetto.getNome() + " non è un oggetto utilizzabile.");
        }
        if (!giocatore.getInventario().contains(oggetto)) {
            throw new AzioneIrregolareException("L'oggetto selezionato non è presente nell'inventario.");
        }
        usable.usa(giocatore);
        giocatore.rimuoviDaInventario(oggetto);
    }

    @Override
    public boolean acquista(Giocatore giocatore, AbstractOggetto oggetto) {
        int prezzo = oggetto.getValore();
        int oro = giocatore.getOro();
        if (oro < prezzo) {
            throw new AzioneIrregolareException("Oro insufficiente per acquistare " +
                    oggetto.getNome() + " (hai " + oro + ").");
        }
        if(aggiungiOggetto(giocatore, oggetto)){
            giocatore.setOro(giocatore.getOro() - prezzo);
            return true;
        }
        return false;
    }
}
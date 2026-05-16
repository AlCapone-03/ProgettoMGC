package it.unicam.cs.mpgc.rpg123279.model.oggetti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractConsumabile extends AbstractOggetto implements IUsable {

    protected AbstractConsumabile() {}
    protected AbstractConsumabile(String nome, String descrizione, int valore, Rarita rarita, TipoOggetto tipoOggetto) {
        super(nome, descrizione, valore, rarita, tipoOggetto);
    }
}
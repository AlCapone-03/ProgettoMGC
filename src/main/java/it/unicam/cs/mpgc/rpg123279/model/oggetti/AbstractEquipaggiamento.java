package it.unicam.cs.mpgc.rpg123279.model.oggetti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EQUIPAGGIAMENTO")
public abstract class AbstractEquipaggiamento extends AbstractOggetto {

    @Column
    private int bonusStatistica;

    protected AbstractEquipaggiamento() {}
    protected AbstractEquipaggiamento(String nome, String descrizione, int valore, Rarita rarita,
                                      TipoOggetto tipoOggetto, int livelloRichiesto, int bonusStatistica) {
        super(nome, descrizione, valore, rarita, tipoOggetto, livelloRichiesto);
        this.bonusStatistica = bonusStatistica;
    }

    public int getBonusStatistica() {return bonusStatistica;}
}
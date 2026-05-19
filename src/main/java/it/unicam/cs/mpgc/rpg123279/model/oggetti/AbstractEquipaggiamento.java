package it.unicam.cs.mpgc.rpg123279.model.oggetti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

@Entity
public abstract class AbstractEquipaggiamento extends AbstractOggetto {

    @Column(nullable = false)
    private int bonusStatistica;

    protected AbstractEquipaggiamento() {}
    protected AbstractEquipaggiamento(String nome, String descrizione, int valore, Rarita rarita,
                                      TipoOggetto tipoOggetto, int livelloRichiesto, int bonusStatistica) {
        super(nome, descrizione, valore, rarita, tipoOggetto, livelloRichiesto);
        this.bonusStatistica = bonusStatistica;
    }

    public int getBonusStatistica() {return bonusStatistica;}
    public void setBonusStatistica(int bonusStatistica) {this.bonusStatistica = bonusStatistica;}
}
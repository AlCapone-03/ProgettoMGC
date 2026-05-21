package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractMostroMarino;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "serpenti_marini")
public class SerpenteMarino extends AbstractMostroMarino {

    protected SerpenteMarino() {}
    public SerpenteMarino(int livello) {
        super("Serpente Marino", 120+(livello*15), 25+(livello*15), 12+(livello*15),
                livello, 60*livello, 45*livello, TipoNemico.SERPENTE_MARINO);
    }

}
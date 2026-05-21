package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "marinai_maledetti")
public class MarinaioMaledetto extends AbstractNemico {

    protected MarinaioMaledetto() {}
    public MarinaioMaledetto(int livello) {
        super("Marinaio Maledetto", 50+(livello*5), 12+(livello*5), 4+(livello*5),
                livello, 20+(livello*5), 15+(livello*5), TipoNemico.MARINAIO_MALEDETTO);
    }
}
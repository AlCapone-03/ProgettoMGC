package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pirati_scheletrici")
public class PirataScheletrico extends AbstractNemico {

    protected PirataScheletrico() {}
    public PirataScheletrico(int livello) {
        super("Pirata Scheletrico", 70+(livello*10), 15+(livello*10), 5+(livello*10),
                livello, 35+(livello*10), 25+(livello*10), TipoNemico.PIRATA_SCHELETRICO);
    }

}

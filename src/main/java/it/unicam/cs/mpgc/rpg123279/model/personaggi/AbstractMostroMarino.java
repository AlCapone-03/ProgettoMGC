package it.unicam.cs.mpgc.rpg123279.model.personaggi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractMostroMarino extends AbstractNemico {

    protected AbstractMostroMarino() {}
    protected AbstractMostroMarino( String nome, int maxHp, int attacco, int difesa, int livello,
                                    int esperienzaRilasciata, int oroRilasciato, TipoNemico tipoNemico) {
        super(nome, maxHp, attacco, difesa, livello, esperienzaRilasciata, oroRilasciato, tipoNemico);
    }
}

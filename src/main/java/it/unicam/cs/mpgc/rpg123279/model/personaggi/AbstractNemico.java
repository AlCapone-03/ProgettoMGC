package it.unicam.cs.mpgc.rpg123279.model.personaggi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractNemico extends AbstractPersonaggio {

    @Column(nullable = false)
    private int xpRilasciata;

    @Column(nullable = false)
    private int oroRilasciato;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNemico tipoNemico;

    protected AbstractNemico() {}
    protected AbstractNemico(String name, int maxHp, int attacco, int difesa, int livello, int xp,
                             int oro, TipoNemico tp) {
        super(name, maxHp, attacco, difesa, livello);
        this.xpRilasciata = xp;
        this.oroRilasciato = oro;
        this.tipoNemico = tp;
    }
}
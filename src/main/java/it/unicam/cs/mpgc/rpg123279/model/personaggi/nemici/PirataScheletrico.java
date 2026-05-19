package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pirati_scheletrici")
public class PirataScheletrico extends AbstractNemico {
    public PirataScheletrico(int livello) {
        super("Pirata Scheletrico", 70+(livello*10), 16+(livello*10), 8+(livello*10),
                livello, 35+(livello*10), 25+(livello*10), TipoNemico.PIRATA_SCHELETRICO);
    }
    @Override
    public void subisciDanno(int damage) {

    }

    @Override
    public boolean isVivo() {
        return false;
    }

    @Override
    public void recuperaSalute(int amount) {

    }

    @Override
    public int getAttacco() {
        return 0;
    }

    @Override
    public int getDifesa() {
        return 0;
    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public int getLivello() {
        return 0;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}

package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "marinai_maledetti")
public class MarinaioMaledetto extends AbstractNemico {
    public MarinaioMaledetto() {
        super("Marinaio Maledetto", 50, 12, 4,
                1, 20, 15, TipoNemico.MARINAIO_MALEDETTO);
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

package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractMostroMarino;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "kraken_boss")
public class KrakenBoss extends AbstractMostroMarino {

    public KrakenBoss(int livello) {
        super("Kraken", 300+(livello*20), 120+(livello*3), 80+(livello*2),
                livello, 250*(livello*3), 200*(livello*3), TipoNemico.KRAKEN_BOSS);
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

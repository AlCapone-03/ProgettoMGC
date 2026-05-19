package it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractMostroMarino;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "serpenti_marini")
public class SerpenteMarino extends AbstractMostroMarino {
    public SerpenteMarino(int livello) {
        super("Serpente Marino", 120+(livello*15), 25+(livello*15), 12+(livello*15),
                livello, 60*livello, 45*livello, TipoNemico.SERPENTE_MARINO);
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

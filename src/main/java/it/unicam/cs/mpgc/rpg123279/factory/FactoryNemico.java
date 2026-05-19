package it.unicam.cs.mpgc.rpg123279.factory;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici.*;
import java.util.Random;

public class FactoryNemico implements IFactoryNemico {

    private final Random random = new Random();

    @Override
    public AbstractNemico creaNemicoCasuale(int lvlGiocatore) {
        int n = random.nextInt(4);
        return switch (n) {
            case 0 -> creaPirataScheletrico(lvlGiocatore);
            case 1 -> creaMarinaioMaledetto(lvlGiocatore);
            case 2 -> creaKraken(lvlGiocatore);
            case 3 -> creaSerpenteMarino(lvlGiocatore);
            default -> creaNemicoCasuale(lvlGiocatore);
        };
    }

    @Override
    public KrakenBoss creaKraken( int lvlGiocatore) {
        return new KrakenBoss(lvlGiocatore);
    }

    @Override
    public MarinaioMaledetto creaMarinaioMaledetto( int lvlGiocatore) {
        return new MarinaioMaledetto(lvlGiocatore);
    }

    @Override
    public PirataScheletrico creaPirataScheletrico(int lvlGiocatore) {
        return new PirataScheletrico(lvlGiocatore);
    }

    @Override
    public SerpenteMarino creaSerpenteMarino(int lvlGiocatore) {
        return new SerpenteMarino(lvlGiocatore);
    }
}
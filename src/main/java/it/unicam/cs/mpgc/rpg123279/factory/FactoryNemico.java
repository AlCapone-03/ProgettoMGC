package it.unicam.cs.mpgc.rpg123279.factory;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici.*;
import java.util.List;
import java.util.Random;

public class FactoryNemico implements IFactoryNemico {

    private final Random random = new Random();

    @Override
    public AbstractNemico creaNemicoCasuale(int lvlGiocatore) {
        // 60% Marinaio, 40% Pirata — Kraken e Serpente sono boss/incontri speciali
        return random.nextInt(10) < 4
                ? creaPirataScheletrico(lvlGiocatore)
                : creaMarinaioMaledetto(lvlGiocatore);
    }

    @Override
    public AbstractNemico creaNemicoPerIsola(List<Class<? extends AbstractNemico>> nemiciPossibili, int lvlGiocatore) {
        if (nemiciPossibili == null || nemiciPossibili.isEmpty()) {
            return creaNemicoCasuale(lvlGiocatore);
        }
        Class<? extends AbstractNemico> classe = nemiciPossibili.get(random.nextInt(nemiciPossibili.size()));
        if (classe == MarinaioMaledetto.class) return creaMarinaioMaledetto(lvlGiocatore);
        if (classe == PirataScheletrico.class) return creaPirataScheletrico(lvlGiocatore);
        if (classe == SerpenteMarino.class)    return creaSerpenteMarino(lvlGiocatore);
        if (classe == KrakenBoss.class)        return creaKraken(lvlGiocatore);
        return creaNemicoCasuale(lvlGiocatore);
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
package it.unicam.cs.mpgc.rpg123279.factory;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.consumabili.PozioneSalute;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.consumabili.Rum;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Arma;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Armatura;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Reliquia;
import java.util.Random;

public class FactoryOggetto implements IFactoryOggetto {

    private final Random random = new Random();

    @Override
    public AbstractOggetto creaArma(int livelloGiocatore) {
        Rarita rarita = generaRarita(livelloGiocatore);
        int bonus = calcolaBonus(5, rarita, livelloGiocatore);
        return new Arma(getNomeArma(rarita), "Sciabola da pirata",
                100 * rarita.getMoltiplicatore(), livelloGiocatore, rarita, bonus);
    }

    @Override
    public AbstractOggetto creaArmatura(int livelloGiocatore) {
        Rarita rarita = generaRarita(livelloGiocatore);
        int bonus = calcolaBonus(4, rarita, livelloGiocatore);
        return new Armatura(getNomeArmatura(rarita), "Armatura consumata dal mare",
                120 * rarita.getMoltiplicatore(), rarita, livelloGiocatore,  bonus);
    }

    @Override
    public AbstractOggetto creaReliquia(int livelloGiocatore) {
        Rarita rarita = generaRarita(livelloGiocatore + 10);
        int bonus = calcolaBonus(8, rarita, livelloGiocatore);
        return new Reliquia(getNomeReliquia(rarita), "Antica reliquia maledetta",
                250 * rarita.getMoltiplicatore(), livelloGiocatore, rarita, bonus, getEffettoReliquia());
    }

    @Override
    public AbstractOggetto creaPozioneSalute(int livelloGiocatore) {
        Rarita rarita = generaRarita(livelloGiocatore);
        int cura = calcolaBonus(20, rarita, livelloGiocatore);
        return new PozioneSalute("Pozione di Cura", "Recupera salute",
                50 * rarita.getMoltiplicatore(), rarita, livelloGiocatore,  cura);
    }

    @Override
    public AbstractOggetto creaRum(int livelloGiocatore) {
        Rarita rarita = generaRarita(livelloGiocatore);
        int cura = calcolaBonus(15, rarita, livelloGiocatore);
        return new Rum("Rum del Corsaro", "Rum forte e speziato",
                40 * rarita.getMoltiplicatore(), rarita, livelloGiocatore, cura);
    }

    @Override
    public AbstractOggetto creaOggettoCasuale(int livelloGiocatore) {
        int tipoOggetto = random.nextInt(5);
        return switch (tipoOggetto) {
            case 0 -> creaArma(livelloGiocatore);
            case 1 -> creaArmatura(livelloGiocatore);
            case 2 -> creaPozioneSalute(livelloGiocatore);
            case 3 -> creaRum(livelloGiocatore);
            case 4 -> creaReliquia(livelloGiocatore);
            default -> creaOggettoCasuale(livelloGiocatore);
        };
    }

    private int calcolaBonus(int valoreBase, Rarita rarita, int livello) {
        return valoreBase + (livello * rarita.getMoltiplicatore());
    }

    private Rarita generaRarita(int livello) {
        int nr = random.nextInt(100);

        if (livello < 5) {
            return nr < 80 ? Rarita.COMUNE : Rarita.RARO;
        }

        if (livello < 15) {
            if (nr < 50) return Rarita.COMUNE;
            if (nr < 85) return Rarita.RARO;
            return Rarita.EPICO;
        }

        if (nr < 40) return Rarita.RARO;
        if (nr < 80) return Rarita.EPICO;
        return Rarita.LEGGENDARIO;
    }

    private String getNomeArma(Rarita rarita) {
        return switch (rarita) {
            case COMUNE -> "Sciabola di Jack Sparrow";
            case RARO -> "Spada di Will Turner";
            case EPICO -> "Pistola a canne rotanti";
            case LEGGENDARIO -> "Tridente di Poseidone";
        };
    }

    private String getNomeArmatura(Rarita rarita) {
        return switch (rarita) {
            case COMUNE -> "Giacca di Cuoio";
            case RARO -> "Corazza del Bucaniere";
            case EPICO -> "Armatura di Barbanera";
            case LEGGENDARIO -> "Armatura del Leviatano";
        };
    }

    private String getNomeReliquia(Rarita rarita) {
        return switch (rarita) {
            case RARO -> "Carte di Mao Kun";
            case EPICO -> "Bussola di Jack Sparrow";
            case LEGGENDARIO -> "Forziere di Davy Jones";
            default -> "Reliquia Antica";
        };
    }

    private String getEffettoReliquia() {
        String[] effetti = {"Fortuna Marina", "Ira degli Abissi", "Benedizione del Kraken", "Sussurro delle Sirene"};
        return effetti[random.nextInt(effetti.length)];
    }
}
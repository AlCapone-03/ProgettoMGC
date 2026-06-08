package it.unicam.cs.mpgc.rpg123279.model.battaglia;

import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;

public class RisultatoBattaglia {

    public enum EsitoBattaglia {
        VITTORIA,
        SCONFITTA,
        FUGA
    }

    private final EsitoBattaglia esito;
    private final int xpGuadagnati;
    private final int oroGuadagnato;
    private final AbstractOggetto oggettoDroppato;
    private final int hpRecuperati;

    private RisultatoBattaglia(EsitoBattaglia esito, int xp, int oro, AbstractOggetto drop, int hpRecuperati) {
        this.esito= esito;
        this.xpGuadagnati = xp;
        this.oroGuadagnato= oro;
        this.oggettoDroppato = drop;
        this.hpRecuperati= hpRecuperati;
    }

    public static RisultatoBattaglia vittoria(int xp, int oro, AbstractOggetto drop, int hpRecuperati) {
        return new RisultatoBattaglia(EsitoBattaglia.VITTORIA, xp, oro, drop, hpRecuperati);
    }
    public static RisultatoBattaglia sconfitta() {
        return new RisultatoBattaglia(EsitoBattaglia.SCONFITTA, 0, 0, null, 0);
    }
    public static RisultatoBattaglia fuga() {
        return new RisultatoBattaglia(EsitoBattaglia.FUGA, 0, 0, null, 0);
    }

    public boolean isVittoria(){ return esito == EsitoBattaglia.VITTORIA; }
    public boolean isSconfitta(){ return esito == EsitoBattaglia.SCONFITTA; }
    public boolean isFuga(){ return esito == EsitoBattaglia.FUGA;}
    public boolean haOggettoDroppato(){ return oggettoDroppato != null; }

    public EsitoBattaglia getEsito(){ return esito; }
    public int getXpGuadagnati(){ return xpGuadagnati; }
    public int getOroGuadagnato(){ return oroGuadagnato; }
    public AbstractOggetto getOggettoDroppato(){ return oggettoDroppato; }
    public int getHpRecuperati(){ return hpRecuperati; }

    @Override
    public String toString() {
        String esitoDes = switch(esito) {
            case VITTORIA -> "VITTORIA (XP: " + xpGuadagnati + ", Oro: " + oroGuadagnato + "HP: " + hpRecuperati + ")";
            case SCONFITTA -> "SCONFITTA";
            case FUGA -> "FUGA";
        };
        return "RisultatoBattaglia{" + esitoDes + "}";
    }
}
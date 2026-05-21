package it.unicam.cs.mpgc.rpg123279.model.battaglia;

import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import java.util.Collections;
import java.util.List;

public class RisultatoBattaglia {

    public enum EsitoBattaglia {
        VITTORIA,
        SCONFITTA,
        FUGA
    }

    private final EsitoBattaglia esito;
    private final int esperienzaGuadagnata;
    private final int oroGuadagnato;
    private final AbstractOggetto oggettoDroppato;
    private final List<String> logBattaglia;

    private RisultatoBattaglia(EsitoBattaglia esito, int xp, int oro, AbstractOggetto drop, List<String> log) {
        this.esito= esito;
        this.esperienzaGuadagnata= xp;
        this.oroGuadagnato= oro;
        this.oggettoDroppato = drop;
        this.logBattaglia= Collections.unmodifiableList(log);
    }

    public static RisultatoBattaglia vittoria(int xp, int oro, AbstractOggetto drop, List<String> log) {
        return new RisultatoBattaglia(EsitoBattaglia.VITTORIA, xp, oro, drop, log);
    }
    public static RisultatoBattaglia sconfitta(List<String> log) {
        return new RisultatoBattaglia(EsitoBattaglia.SCONFITTA, 0, 0, null, log);
    }
    public static RisultatoBattaglia fuga(List<String> log) {
        return new RisultatoBattaglia(EsitoBattaglia.FUGA, 0, 0, null, log);
    }

    public boolean isVittoria(){ return esito == EsitoBattaglia.VITTORIA;  }
    public boolean isSconfitta(){ return esito == EsitoBattaglia.SCONFITTA; }
    public boolean isFuga(){ return esito == EsitoBattaglia.FUGA;      }
    public boolean haOggettoDroppato(){ return oggettoDroppato != null; }

    public EsitoBattaglia getEsito(){ return esito; }
    public int getEsperienzaGuadagnata(){ return esperienzaGuadagnata; }
    public int getOroGuadagnato(){ return oroGuadagnato; }
    public AbstractOggetto getOggettoDroppato(){ return oggettoDroppato; }
    public List<String> getLogBattaglia(){ return logBattaglia; }

    @Override
    public String toString() {
        return "BattleResult{esito=" + esito + ", xp=" + esperienzaGuadagnata + ", oro=" + oroGuadagnato +
                ", drop=" + (oggettoDroppato != null ? oggettoDroppato.getNome() : "nessuno") + "}";
    }
}
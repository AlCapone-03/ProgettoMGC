package it.unicam.cs.mpgc.rpg123279.model.battaglia;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.AzioneCombattimento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;

public class DatiTurno {

    private final AzioneCombattimento azioneGiocatore;
    private final int dannoInflitto;
    private final int dannoRicevuto;
    private final AbstractOggetto oggettoUsato;
    private final boolean fugaRiuscita;
    private final String descrizione;

    private DatiTurno(Builder builder) {
        this.azioneGiocatore= builder.azioneGiocatore;
        this.dannoInflitto = builder.dannoInflitto;
        this.dannoRicevuto = builder.dannoRicevuto;
        this.oggettoUsato= builder.oggettoUsato;
        this.fugaRiuscita= builder.fugaRiuscita;
        this.descrizione= builder.descrizione;
    }

    public static class Builder {

        private final AzioneCombattimento azioneGiocatore;
        private int dannoInflitto = 0;
        private int dannoRicevuto = 0;
        private AbstractOggetto oggettoUsato= null;
        private boolean fugaRiuscita= false;
        private String descrizione= "";

        public Builder(AzioneCombattimento azione) {
            if (azione == null) throw new IllegalArgumentException("L'azione non può essere null.");
            this.azioneGiocatore = azione;
        }

        public Builder dannoInflitto(int valore) {
            this.dannoInflitto = valore;
            return this;
        }
        public Builder dannoRicevuto(int valore) {
            this.dannoRicevuto = valore;
            return this;
        }
        public Builder oggettoUsato(AbstractOggetto oggetto) {
            this.oggettoUsato = oggetto;
            return this;
        }
        public Builder fugaRiuscita(boolean esito) {
            this.fugaRiuscita = esito;
            return this;
        }
        public Builder descrizione(String testo) {
            this.descrizione = testo;
            return this;
        }
        public DatiTurno build() {return new DatiTurno(this);}
    }

    public AzioneCombattimento getAzioneGiocatore(){ return azioneGiocatore; }
    public int getDannoInflitto(){ return dannoInflitto; }
    public int getDannoRicevuto(){ return dannoRicevuto; }
    public AbstractOggetto getOggettoUsato(){ return oggettoUsato; }
    public String getDescrizione() {return descrizione; }
    public boolean isFugaRiuscita(){ return fugaRiuscita; }

    @Override
    public String toString() {
        return "DatiTurno{azione=" + azioneGiocatore + ", dannoInflitto=" + dannoInflitto +
                ", dannoRicevuto=" + dannoRicevuto + ", fuga=" + fugaRiuscita + "}";
    }
}
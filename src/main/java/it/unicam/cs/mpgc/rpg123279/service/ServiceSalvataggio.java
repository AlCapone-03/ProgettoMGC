package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.exception.SalvataggioException;
import it.unicam.cs.mpgc.rpg123279.model.PartitaRipristinata;
import it.unicam.cs.mpgc.rpg123279.model.Salvataggio;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.mappe.MappaGioco;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.repository.IRepoSalvataggio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ServiceSalvataggio implements IServiceSalvataggio {

    private final IRepoSalvataggio repoSalvataggio;

    public ServiceSalvataggio(IRepoSalvataggio repoSalvataggio) {
        if (repoSalvataggio == null) throw new IllegalArgumentException("Il repository non può essere null.");
        this.repoSalvataggio = repoSalvataggio;
    }

    @Override
    public Salvataggio salva(Giocatore giocatore, String nomeSlot, String nomeIsolaCorrente) {
        validaNomeSlot(nomeSlot);
        if (giocatore == null) throw new SalvataggioException("Il giocatore non può essere null.");
        if (nomeIsolaCorrente == null || nomeIsolaCorrente.isBlank())
            throw new SalvataggioException("L'isola corrente non può essere null.");
        Optional<Salvataggio> esistente= repoSalvataggio.findByNomeSlot(nomeSlot);
        if (esistente.isPresent()) {
            Salvataggio s = esistente.get();
            s.setGiocatore(giocatore);
            s.setNomeIsolaCorrente(nomeIsolaCorrente);
            s.setDataSalvataggio(LocalDateTime.now());
            return repoSalvataggio.update(s);
        }
        return repoSalvataggio.save(new Salvataggio(giocatore, nomeSlot, nomeIsolaCorrente));
    }

    @Override
    public PartitaRipristinata ripristinaPartita(String nomeSlot, MappaGioco mappa) {
        validaNomeSlot(nomeSlot);
        if (mappa == null) throw new IllegalArgumentException("La mappa non può essere null.");
        Salvataggio salvataggio= repoSalvataggio.findByNomeSlot(nomeSlot)
                .orElseThrow(() -> new SalvataggioException("Nessun salvataggio trovato per lo slot '" + nomeSlot + "'."));
        Isola isola = mappa.getIslands().stream().filter(i -> i.getNome().equalsIgnoreCase(salvataggio.getNomeIsolaCorrente())).findFirst()
                .orElseThrow(() -> new SalvataggioException("L'isola '" + salvataggio.getNomeIsolaCorrente() +
                        "' non esiste più nella mappa. Salvataggio corrotto o mappa modificata."));
        return new PartitaRipristinata(salvataggio.getGiocatore(), isola);
    }

    @Override
    public void elimina(String nomeSlot) {
        validaNomeSlot(nomeSlot);
        Salvataggio s = repoSalvataggio.findByNomeSlot(nomeSlot).orElseThrow(() -> new SalvataggioException(
                "Nessun salvataggio trovato per lo slot '" + nomeSlot + "'."));
        repoSalvataggio.delete(s);
    }

    @Override
    public List<Salvataggio> getSalvataggiByDate() {
        return repoSalvataggio.findAllSlotOrderByDate();
    }

    @Override
    public boolean esisteSlot(String nomeSlot) {
        validaNomeSlot(nomeSlot);
        return repoSalvataggio.findByNomeSlot(nomeSlot).isPresent();
    }

    private void validaNomeSlot(String nomeSlot) {
        if (nomeSlot == null || nomeSlot.isBlank())
            throw new SalvataggioException("Il nome dello slot non può essere vuoto.");
    }
}
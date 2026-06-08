package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.exception.SalvataggioException;
import it.unicam.cs.mpgc.rpg123279.model.PartitaRipristinata;
import it.unicam.cs.mpgc.rpg123279.model.Salvataggio;
import it.unicam.cs.mpgc.rpg123279.model.mappe.Isola;
import it.unicam.cs.mpgc.rpg123279.model.mappe.MappaGioco;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.repository.IRepoSalvataggio;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiceSalvataggio implements IServiceSalvataggio {

    private final IRepoSalvataggio repoSalvataggio;

    public ServiceSalvataggio(IRepoSalvataggio repoSalvataggio) {
        if (repoSalvataggio == null) throw new IllegalArgumentException("Il repository non può essere null.");
        this.repoSalvataggio = repoSalvataggio;
    }

    @Override
    public void salva(Giocatore giocatore, String nomeSlot, String nomeIsolaCorrente, MappaGioco mappa) {
        validaNomeSlot(nomeSlot);
        if (giocatore == null) throw new SalvataggioException("Il giocatore non può essere null.");
        if (nomeIsolaCorrente == null || nomeIsolaCorrente.isBlank())
            throw new SalvataggioException("L'isola corrente non può essere null.");
        if (mappa == null) throw new SalvataggioException("La mappa non può essere null.");

        String isoleBossSconfitto = mappa.getIsole().stream().filter(Isola::isBossSconfitto)
                .map(Isola::getNome).collect(Collectors.joining(","));

        Optional<Salvataggio> esistente = repoSalvataggio.findByNomeSlot(nomeSlot);
        if (esistente.isPresent()) {
            Salvataggio s = esistente.get();
            s.setGiocatore(giocatore);
            s.setNomeIsolaCorrente(nomeIsolaCorrente);
            s.setIsoleBossSconfitto(isoleBossSconfitto);
            s.setDataSalvataggio(LocalDateTime.now());
            repoSalvataggio.update(s);
            return;
        }
        repoSalvataggio.save(new Salvataggio(giocatore, nomeSlot, nomeIsolaCorrente, isoleBossSconfitto));
    }

    @Override
    public PartitaRipristinata ripristinaPartita(String nomeSlot, MappaGioco mappa) {
        validaNomeSlot(nomeSlot);
        if (mappa == null) throw new IllegalArgumentException("La mappa non può essere null.");
        Salvataggio salvataggio = caricaSlot(nomeSlot);
        ripristinaMappa(mappa, parseNomi(salvataggio.getIsoleBossSconfitto()));
        Isola isolaCorrente = trovaIsola(mappa, salvataggio.getNomeIsolaCorrente());
        return new PartitaRipristinata(salvataggio.getGiocatore(), isolaCorrente);
    }

    @Override
    public void elimina(String nomeSlot) {
        validaNomeSlot(nomeSlot);
        Salvataggio s = caricaSlot(nomeSlot);
        repoSalvataggio.delete(s);
    }

    @Override
    public List<Salvataggio> getSalvataggiByDate() {
        return repoSalvataggio.findAllSlotOrderByDate();
    }

    private Salvataggio caricaSlot(String nomeSlot) {
        return repoSalvataggio.findByNomeSlot(nomeSlot).orElseThrow(() -> new SalvataggioException(
                "Nessun salvataggio trovato per lo slot '" + nomeSlot + "'."));
    }

    private void ripristinaMappa(MappaGioco mappa, Set<String> bossSconfitti) {
        mappa.getIsole().forEach(isola -> {isola.setBossSconfitto(bossSconfitti.contains(isola.getNome()));
            isola.setSbloccata(isIsolaSbloccata(mappa, isola, bossSconfitti));
        });
    }

    private boolean isIsolaSbloccata(MappaGioco mappa, Isola isola, Set<String> bossSconfitti) {
        if (isola.getOrdineSequenza() == 0) return true;
        return mappa.getIsole().stream().filter(i -> i.getOrdineSequenza() == isola.getOrdineSequenza() - 1)
                .anyMatch(i -> bossSconfitti.contains(i.getNome()));
    }

    private Isola trovaIsola(MappaGioco mappa, String nomeIsola) {
        return mappa.getIsole().stream().filter(i -> i.getNome().equalsIgnoreCase(nomeIsola)).findFirst().orElseThrow(()
                -> new SalvataggioException("L'isola '" + nomeIsola + "' non esiste nella mappa. Salvataggio corrotto."));
    }

    private Set<String> parseNomi(String serializzato) {
        if (serializzato == null || serializzato.isBlank()) return Set.of();
        return Arrays.stream(serializzato.split(",")).map(String::trim)
                .filter(s -> !s.isEmpty()).collect(Collectors.toSet());
    }

    private void validaNomeSlot(String nomeSlot) {
        if (nomeSlot == null || nomeSlot.isBlank())
            throw new SalvataggioException("Il nome dello slot non può essere vuoto.");
    }
}
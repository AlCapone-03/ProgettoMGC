package it.unicam.cs.mpgc.rpg123279.model.mappe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MappaGioco {

    private List<Isola> isole = new ArrayList<>();
    private List<Rotta> rotte = new ArrayList<>();

    public void aggiungiIsola(Isola island) {isole.add(island);}
    public void aggiungiRotta(Rotta r) {rotte.add(r);}

    public List<Isola> getIsole() {return isole;}
    public List<Rotta> getRotte() {return rotte;}
    public List<Rotta> getRotteDaIsola(Isola origine) {
        return rotte.stream().filter(r -> r.getOrigine().equals(origine))
                .filter(r -> r.getDestinazione().isSbloccata()).toList();
    }

    public void sbloccaIsolaSuccessiva(Isola isolaCorrente) {
        int prossimoOrdine = isolaCorrente.getOrdineSequenza() + 1;
        isole.stream().filter(i -> i.getOrdineSequenza() == prossimoOrdine)
                .findFirst().ifPresent(i -> i.setSbloccata(true));
    }

    public Optional<Isola> findByNome(String nome) {
        return isole.stream().filter(i -> i.getNome().equalsIgnoreCase(nome)).findFirst();
    }
}
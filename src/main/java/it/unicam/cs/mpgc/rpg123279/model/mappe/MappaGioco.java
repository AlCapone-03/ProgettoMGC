package it.unicam.cs.mpgc.rpg123279.model.mappe;

import java.util.ArrayList;
import java.util.List;

public class MappaGioco {

    private List<Isola> isole = new ArrayList<>();
    private List<Rotta> rotte = new ArrayList<>();

    public void aggiungiIsola(Isola island) {
        isole.add(island);
    }

    public void aggiungiRotta(Rotta r) {
        rotte.add(r);
    }

    public List<Isola> getIslands() {
        return isole;
    }

    public List<Rotta> getSeaRoutes() {
        return rotte;
    }

    public List<Rotta> getRotteDaIsola(Isola i) {
        return rotte.stream().filter(r -> r.getOrigine().equals(i)).toList();
    }
}
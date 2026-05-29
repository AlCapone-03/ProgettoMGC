package it.unicam.cs.mpgc.rpg123279.repository;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import java.util.Optional;

public interface IRepoGiocatore extends IRepository<Giocatore, Long> {
    Optional<Giocatore> findByNome(String nome);
}
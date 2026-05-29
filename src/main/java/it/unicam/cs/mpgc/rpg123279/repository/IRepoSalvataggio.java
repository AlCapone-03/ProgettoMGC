package it.unicam.cs.mpgc.rpg123279.repository;

import it.unicam.cs.mpgc.rpg123279.model.Salvataggio;
import java.util.List;
import java.util.Optional;

public interface IRepoSalvataggio extends IRepository<Salvataggio, Long> {
    Optional<Salvataggio> findByNomeSlot(String nomeSlot);
    List<Salvataggio> findAllSlotOrderByDate();
}
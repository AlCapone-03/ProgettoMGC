package it.unicam.cs.mpgc.rpg123279.repository;

import it.unicam.cs.mpgc.rpg123279.model.Salvataggio;
import it.unicam.cs.mpgc.rpg123279.config.HibernateConfig;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public class SalvataggioRepoHibernate extends BaseRepoHibernate<Salvataggio, Long> implements IRepoSalvataggio {

    public SalvataggioRepoHibernate() {
        super(Salvataggio.class);
    }

    @Override
    public Optional<Salvataggio> findByNomeSlot(String nomeSlot) {
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            return s.createQuery("FROM Salvataggio s WHERE lower(s.nomeSlot) = lower(:nomeSlot)",
                            Salvataggio.class).setParameter("nomeSlot", nomeSlot).uniqueResultOptional();
        }
    }

    @Override
    public List<Salvataggio> findAllSlotOrderByDate() {
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            return s.createQuery("FROM Salvataggio s ORDER BY s.dataSalvataggio DESC",
                    Salvataggio.class).getResultList();
        }
    }
}
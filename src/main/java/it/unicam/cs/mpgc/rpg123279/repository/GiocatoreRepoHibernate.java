package it.unicam.cs.mpgc.rpg123279.repository;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.config.HibernateConfig;
import org.hibernate.Session;
import java.util.Optional;

public class GiocatoreRepoHibernate extends BaseRepoHibernate<Giocatore, Long> implements IRepoGiocatore {

    public GiocatoreRepoHibernate() {
        super(Giocatore.class);
    }

    @Override
    public Optional<Giocatore> findByNome(String nome) {
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            return s.createQuery("FROM Giocatore g WHERE lower(g.nome) = lower(:nome)", Giocatore.class)
                    .setParameter("nome", nome).uniqueResultOptional();
        }
    }
}
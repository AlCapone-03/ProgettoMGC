package it.unicam.cs.mpgc.rpg123279.repository;

import it.unicam.cs.mpgc.rpg123279.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class BaseRepoHibernate<T, ID extends Serializable> implements IRepository<T, ID> {

    private final Class<T> entita;

    public BaseRepoHibernate(Class<T> entita) {
        if (entita == null) throw new IllegalArgumentException("La classe entità non può essere null.");
        this.entita = entita;
    }

    @Override
    public T save(T entity) {
        Transaction t = null;
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            t = s.beginTransaction();
            s.persist(entity);
            t.commit();
            return entity;
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public T update(T entity) {
        Transaction t = null;
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            t = s.beginTransaction();
            T merged = s.merge(entity);
            t.commit();
            return merged;
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw new RuntimeException("Errore durante l'aggiornamento: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(T entity) {
        Transaction t = null;
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            t = s.beginTransaction();
            s.remove(s.contains(entity) ? entity : s.merge(entity));
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw new RuntimeException("Errore durante l'eliminazione: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(s.get(entita, id));
        }
    }

    @Override
    public List<T> findAll() {
        try (Session s = HibernateConfig.getSessionFactory().openSession()) {
            return s.createQuery("FROM " + entita.getSimpleName(), entita).getResultList();
        }
    }
}
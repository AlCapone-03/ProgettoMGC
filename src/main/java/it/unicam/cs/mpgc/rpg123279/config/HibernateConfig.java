package it.unicam.cs.mpgc.rpg123279.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static SessionFactory sessionFactory;
    private HibernateConfig() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException( "Errore durante la creazione della SessionFactory.");
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {sessionFactory.close();}
    }
}
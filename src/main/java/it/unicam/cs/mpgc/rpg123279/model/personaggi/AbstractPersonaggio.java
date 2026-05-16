package it.unicam.cs.mpgc.rpg123279.model.personaggi;

import jakarta.persistence.*;
import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Inheritance(strategy = JOINED)
@Table(name = "personaggi")
public abstract class AbstractPersonaggio implements IAttackable, IHealable, IEntitaCombattimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int hp;

    @Column(nullable = false)
    private int maxHp;

    @Column(nullable = false)
    private int attacco;

    @Column(nullable = false)
    private int difesa;

    @Column(nullable = false)
    private int livello;

    protected AbstractPersonaggio() {}
    protected AbstractPersonaggio(String nome, int maxHp, int attacco, int difesa, int livello) {
        this.nome = nome;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attacco = attacco;
        this.difesa = difesa;
        this.livello = livello;
    }

    public String getNome() {
        return nome;
    }
}
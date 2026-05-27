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

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public void setHp(int hp) { this.hp = Math.clamp(hp, 0, maxHp); }
    public void setAttacco(int attacco) { this.attacco = attacco; }
    public void setDifesa(int difesa) { this.difesa = difesa; }
    public void setLivello(int livello) { this.livello = livello; }
    public void setMaxHp(int maxHp) {this.maxHp = Math.max(this.maxHp, maxHp);}

    @Override
    public int getAttacco() { return attacco; }
    @Override
    public int getDifesa() { return difesa; }
    @Override
    public int getHp() { return hp; }
    @Override
    public int getMaxHp() { return maxHp; }
    @Override
    public int getLivello() { return livello; }

    @Override
    public boolean isVivo() { return hp > 0; }

    @Override
    public void recuperaSalute(int cura) {this.hp = Math.min(this.maxHp, this.hp + cura);}
}
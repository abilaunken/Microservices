package com.kamehouse.gamesinfoservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TrofeuInfo {

    @Id
    private Integer id;
    private String nome;
    private String descricao;

    @ManyToOne
    private GameInfo gameInfo;


    public TrofeuInfo() {
    }

    public TrofeuInfo(Integer id, String nome, String descricao, GameInfo gameInfo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.gameInfo = gameInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

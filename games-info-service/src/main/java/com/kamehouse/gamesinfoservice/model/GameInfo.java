package com.kamehouse.gamesinfoservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class GameInfo {

    @Id
    private Integer id;
    private String nome;
    private String descricao;
    @OneToMany
    private List<TrofeuInfo>  listaTrofeus;


    public GameInfo() {
    }

    public GameInfo(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;

    }

    public List<TrofeuInfo> getListaTrofeus() {
        return listaTrofeus;
    }

    public void setListaTrofeus(List<TrofeuInfo> listaTrofeus) {
        this.listaTrofeus = listaTrofeus;
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

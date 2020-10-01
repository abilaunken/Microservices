package com.kamehouse.userdataservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class UserData {

    @Id
    private Integer id;
    private String nome;

    @OneToMany
    private List<GameInfo> listaGameInfo;


    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", listaGameInfo=" + listaGameInfo +
                '}';
    }

    public UserData() {
    }

    public UserData(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public List<GameInfo> getListaGameInfo() {
        return listaGameInfo;
    }

    public void setListaGameInfo(List<GameInfo> listaGameInfo) {
        this.listaGameInfo = listaGameInfo;
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
}

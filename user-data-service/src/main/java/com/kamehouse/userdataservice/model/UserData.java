package com.kamehouse.userdataservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(id, userData.id) &&
                Objects.equals(nome, userData.nome) &&
                userData.getListaGameInfo().equals(this.getListaGameInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, listaGameInfo);
    }
}

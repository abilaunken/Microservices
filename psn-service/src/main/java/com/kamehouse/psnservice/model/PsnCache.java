package com.kamehouse.psnservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.function.Consumer;

@Entity
public class PsnCache implements Cache{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String jsonUsuario;
    private String jsonGames;

    public PsnCache() {
    }

    public PsnCache(Integer id, String jsonUsuario, String jsonGames) {
        this.id = id;
        this.jsonUsuario = jsonUsuario;
        this.jsonGames = jsonGames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJsonUsuario() {
        return jsonUsuario;
    }

    public void setJsonUsuario(String jsonUsuario) {
        this.jsonUsuario = jsonUsuario;
    }

    public String getJsonGames() {
        return jsonGames;
    }

    public void setJsonGames(String jsonGames) {
        this.jsonGames = jsonGames;
    }


    @Override
    public void salvarCache(Object o, Consumer c) {
        c.accept(o);
    }

    @Override
    public String toString() {
        return "PsnCache{" +
                "id=" + id +
                ", jsonUsuario='" + jsonUsuario + '\'' +
                ", jsonGames='" + jsonGames + '\'' +
                '}';
    }
}


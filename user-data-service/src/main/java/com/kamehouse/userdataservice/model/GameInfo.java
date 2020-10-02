package com.kamehouse.userdataservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class GameInfo {

    @Id
    private Integer id;

    @Override
    public String toString() {
        return "GameInfo{" +
                "id=" + id +
                '}';
    }

    public GameInfo() {
    }

    public GameInfo(Integer id) {
        this.id = id;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return Objects.equals(id, gameInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

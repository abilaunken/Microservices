package com.kamehouse.gamesinfoservice.repository;

import com.kamehouse.gamesinfoservice.model.GameInfo;
import org.springframework.data.repository.CrudRepository;

public interface GameInfoRepository extends CrudRepository<GameInfo, Integer> {

    public GameInfo findByNome(String nome);
}

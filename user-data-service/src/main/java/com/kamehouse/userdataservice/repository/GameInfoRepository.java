package com.kamehouse.userdataservice.repository;

import com.kamehouse.userdataservice.model.GameInfo;
import org.springframework.data.repository.CrudRepository;

public interface GameInfoRepository extends CrudRepository<GameInfo, Integer> {
}

package com.kamehouse.userdataservice.repository;

import com.kamehouse.userdataservice.model.UserData;
import org.springframework.data.repository.CrudRepository;

public interface UserDataRepository extends CrudRepository<UserData, Integer> {

    public UserData findByNome(String nome);
}

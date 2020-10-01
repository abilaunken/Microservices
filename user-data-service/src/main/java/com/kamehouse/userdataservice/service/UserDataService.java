package com.kamehouse.userdataservice.service;

import com.kamehouse.userdataservice.model.GameInfo;
import com.kamehouse.userdataservice.model.UserData;
import com.kamehouse.userdataservice.repository.GameInfoRepository;
import com.kamehouse.userdataservice.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDataService {

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    GameInfoRepository gameInfoRepository;

    @PostConstruct
    public void init() {
        UserData user1 = new UserData(1, "Gabriela");
        userDataRepository.save(user1);

        GameInfo gow =  new GameInfo(1);
        this.gameInfoRepository.save(gow);
        GameInfo lou = new GameInfo(2);
        this.gameInfoRepository.save(lou);
        GameInfo ow = new GameInfo(3);
        this.gameInfoRepository.save(ow);
        GameInfo duke = new GameInfo(4);
        this.gameInfoRepository.save(duke);
    }

    public List<UserData> listar(){
        List<UserData> value = new ArrayList<>();
        userDataRepository.findAll().forEach(user -> value.add(user));
        return value;
    }

    public String atualizaUsuario(UserData userData){
        UserData usuario = userDataRepository.findByNome(userData.getNome());
        userData.getListaGameInfo().stream().forEach((g)-> usuario.getListaGameInfo().add(g));
        userDataRepository.save(usuario);
        return "A Conta do Usuario: "+userData.getNome()+", foi atualizada.";
    }

    public UserData getUsuario(String nomeUsuario) {
        return userDataRepository.findByNome(nomeUsuario);
    }
}

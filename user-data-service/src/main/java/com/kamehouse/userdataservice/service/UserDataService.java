package com.kamehouse.userdataservice.service;

import com.kamehouse.userdataservice.model.GameInfo;
import com.kamehouse.userdataservice.model.UserData;
import com.kamehouse.userdataservice.repository.GameInfoRepository;
import com.kamehouse.userdataservice.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        atualizaUsuario(user1);

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

    public HttpStatus compraJogo(UserData userData){
        UserData usuario = userDataRepository.findByNome(userData.getNome());
        if(usuario.equals(userData)){
            return HttpStatus.CONFLICT;
        }
        userData.getListaGameInfo().stream().forEach((g)-> {
            if(usuario.getListaGameInfo().stream()
                    .noneMatch((u)-> u.getId() == g.getId())){
                usuario.getListaGameInfo().add(g);
            }
        });
        atualizaUsuario(usuario);
        return HttpStatus.OK;
    }

    private void atualizaUsuario(UserData usuario) {
        userDataRepository.save(usuario);
    }

    public UserData getUsuario(String nomeUsuario) {
        return userDataRepository.findByNome(nomeUsuario);
    }
}

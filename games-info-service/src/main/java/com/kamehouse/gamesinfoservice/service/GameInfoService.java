package com.kamehouse.gamesinfoservice.service;

import com.kamehouse.gamesinfoservice.model.GameInfo;
import com.kamehouse.gamesinfoservice.repository.GameInfoRepository;
import com.kamehouse.gamesinfoservice.repository.TrofeuInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GameInfoService {

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Autowired
    private TrofeuInfoRepository trofeuInfoRepository;

    @PostConstruct
    public void init(){
        List<GameInfo> listaJogos = new ArrayList<>(Arrays.asList(
                new GameInfo(1,"God of War","Jogo de PS2"),
                new GameInfo(2,"Last of Us","Jogo single player"),
                new GameInfo(3,"Overwatch","Jogo multiplayer"),
                new GameInfo(4,"Duke Nuken","Jogo de D.O.S")
        ));
        listaJogos.forEach(g -> this.gameInfoRepository.save(g));
    }


    public List<GameInfo> listar(){
        List<GameInfo> value = new ArrayList<>();
        gameInfoRepository.findAll().forEach(gInfo -> value.add(gInfo));
        return value;

    }

    public GameInfo recuperaJogoPorNome(String nomeJogo){
       /* try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return this.listar().stream().filter(j -> j.getNome().equals(nomeJogo)).findFirst().get();


    }

}

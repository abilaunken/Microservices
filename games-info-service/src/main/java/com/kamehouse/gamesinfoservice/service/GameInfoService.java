package com.kamehouse.gamesinfoservice.service;

import com.kamehouse.gamesinfoservice.model.GameInfo;
import com.kamehouse.gamesinfoservice.model.TrofeuInfo;
import com.kamehouse.gamesinfoservice.repository.GameInfoRepository;
import com.kamehouse.gamesinfoservice.repository.TrofeuInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameInfoService {

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Autowired
    private TrofeuInfoRepository trofeuInfoRepository;

    @PostConstruct
    public void init(){

        GameInfo gow =  new GameInfo(1,"God of War","Jogo de PS2");
        this.gameInfoRepository.save(gow);
        GameInfo lou = new GameInfo(2,"Last of Us","Jogo single player");
        this.gameInfoRepository.save(lou);
        GameInfo ow = new GameInfo(3,"Overwatch","Jogo multiplayer");
        this.gameInfoRepository.save(ow);
        GameInfo duke = new GameInfo(4,"Duke Nuken","Jogo de D.O.S");
        this.gameInfoRepository.save(duke);

        List<TrofeuInfo> trofeuGOW = new ArrayList<>(
                Arrays.asList(new TrofeuInfo(1,"God of War Trofeu 1","Trofeu 1", gow)
                        ,new TrofeuInfo(2,"God of War Trofeu 2","Platina", gow)
                ));


        trofeuGOW.forEach(t -> this.trofeuInfoRepository.save(t));
        gow.setListaTrofeus(trofeuGOW);



        List<TrofeuInfo> trofeuLOU = new ArrayList<>(
                Arrays.asList( new TrofeuInfo(13,"Last of Us Trofeu 1","Trofeu 1",lou)
                        , new TrofeuInfo(22,"Last of Us Trofeu 2","Trofeu 2",lou)
                        , new TrofeuInfo(33,"Last of Us Trofeu 3","Trofeu 3",lou)
        ));
        trofeuLOU.forEach(t -> this.trofeuInfoRepository.save(t));
        lou.setListaTrofeus(trofeuLOU);

        List<TrofeuInfo> trofeuOW = new ArrayList<>(
                Arrays.asList( new TrofeuInfo(11,"Overwatch Trofeu 1","Trofeu 1",ow)
       ));

        trofeuOW.forEach(t -> this.trofeuInfoRepository.save(t));
        ow.setListaTrofeus(trofeuOW);

        List<TrofeuInfo> trofeuDUKE = new ArrayList<>(
                Arrays.asList(new TrofeuInfo(12,"Duke Nuken Trofeu 1","Platina",duke)));

        trofeuDUKE.forEach(t -> this.trofeuInfoRepository.save(t));
        duke.setListaTrofeus(trofeuDUKE);

        List<GameInfo> listaJogos = new ArrayList<>();
        listaJogos.add(gow);
        listaJogos.add(lou);
        listaJogos.add(ow);
        listaJogos.add(duke);

        listaJogos.forEach(g -> this.gameInfoRepository.save(g));
    }


    public List<GameInfo> listar(){
        List<GameInfo> value = new ArrayList<>();
        gameInfoRepository.findAll().forEach(gInfo -> value.add(gInfo));
        return value;

    }

    public GameInfo recuperaJogoPorNome(String nomeJogo){
        return gameInfoRepository.findByNome(nomeJogo);
        //return this.listar().stream().findFirst().filter(j -> j.getNome().equals(nomeJogo)).get();
    }

}

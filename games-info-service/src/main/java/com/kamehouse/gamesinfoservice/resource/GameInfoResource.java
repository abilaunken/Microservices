package com.kamehouse.gamesinfoservice.resource;


import com.kamehouse.gamesinfoservice.model.GameInfo;
import com.kamehouse.gamesinfoservice.service.GameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameInfoResource {

    @Autowired
    GameInfoService gameInfoService;

    @RequestMapping("/listar")
    public List<GameInfo> listar(){
        return gameInfoService.listar();

    }

    @RequestMapping(method = RequestMethod.GET,value="/{nomeJogo}")
    public GameInfo recuperaJogo(@PathVariable("nomeJogo") String nomeJogo){
        return gameInfoService.recuperaJogoPorNome(nomeJogo);
    }



}

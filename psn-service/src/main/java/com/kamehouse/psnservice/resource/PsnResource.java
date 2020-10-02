package com.kamehouse.psnservice.resource;



import com.kamehouse.psnservice.model.GameInfo;
import com.kamehouse.psnservice.model.PsnCache;
import com.kamehouse.psnservice.model.UserData;
import com.kamehouse.psnservice.service.GameInfoService;
import com.kamehouse.psnservice.service.PsnService;
import com.kamehouse.psnservice.service.UserDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PsnResource {

    private static final Logger logger = LogManager.getLogger(PsnResource.class);

    @Autowired
    private PsnService psnService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private GameInfoService gameInfoService;


    @RequestMapping("/psn/listar")
    public List<PsnCache> listarCache(){
        return psnService.listar();
    }

    //TODO:Esse método teve que ser criado pois nao tenho interface ainda
    @RequestMapping(method = RequestMethod.GET, value="/psn/queroComprarJogo/{nomeJogo}/usuario/{nomeUsuario}")
    public UserData queroComprarJogo(@PathVariable("nomeJogo") String nomeJogo,
                              @PathVariable("nomeUsuario") String nomeUsuario){

       final GameInfo gameInfo = gameInfoService.getGameInfo(nomeJogo);
       UserData userData = userDataService.getUserData(nomeUsuario);
       userData.setListaGameInfo(new ArrayList<>(Arrays.asList(gameInfo)));
       return userData;
    }

    @RequestMapping(method= RequestMethod.PUT, value="/psn/comprar")
    public String comprarJogo(@RequestBody UserData userData){
        //TODO: Trocar 1 este ponto para usar rabbitmq, depois tornar tudo eventlog
        logger.debug("Atualizando informacoes de compra do usuario: {}", userData.getNome());
        return userDataService.atualizaBaseUsuario(userData);

    }


}

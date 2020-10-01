package com.kamehouse.psnservice.resource;



import com.kamehouse.psnservice.model.GameInfo;
import com.kamehouse.psnservice.model.PsnCache;
import com.kamehouse.psnservice.model.UserData;
import com.kamehouse.psnservice.service.PsnService;
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

    @Value("${microservice.gameInfo.name}")
    private String gameInfoMicroService;

    @Value("${microservice.userData.name}")
    private String userDataMicroService;

    @RequestMapping("/listar")
    public List<PsnCache> listarCache(){
        return psnService.listar();
    }

    private GameInfo getGameInfo(String nomeJogo){
        logger.debug("Pegando informacoes do jogo: ", nomeJogo);
        return restTemplate.getForObject("http://"+gameInfoMicroService+"/gamesInfo/"+nomeJogo, GameInfo.class);
    }

    private UserData getUserData(String nomeUsuario){
        logger.debug("Pegando informacoes do usuario: ", nomeUsuario);
        return restTemplate.getForObject("http://"+userDataMicroService+"/userService/"+nomeUsuario, UserData.class);
    }

    private void atualizaBaseUsuario(UserData userData){
            restTemplate.put("http://"+userDataMicroService+"/userService/atualizarUsuario/", userData);
    }

    //TODO:Esse método teve que ser criado pois nao tenho interface ainda
    @RequestMapping(method = RequestMethod.GET, value="/queroComprarJogo/{nomeJogo}/usuario/{nomeUsuario}")
    public UserData queroComprarJogo(@PathVariable("nomeJogo") String nomeJogo,
                              @PathVariable("nomeUsuario") String nomeUsuario){

       final GameInfo gameInfo = getGameInfo(nomeJogo);
       final UserData userData = getUserData(nomeUsuario);
       userData.setListaGameInfo(new ArrayList<>(Arrays.asList(gameInfo)));
       return userData;
    }

    @RequestMapping(method= RequestMethod.PUT, value="/comprar")
    public String comprarJogo(@RequestBody UserData userData){
        //TODO: Trocar 1 este ponto para usar rabbitmq, depois tornar tudo eventlog
        logger.debug("Atualizando informacoes de compra do usuario: ", userData.getNome());
        atualizaBaseUsuario(userData);
        return "Jogo Comprado por: "+userData.getNome();
    }


}

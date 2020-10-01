package com.kamehouse.psnservice.resource;



import com.kamehouse.psnservice.model.GameInfo;
import com.kamehouse.psnservice.model.PsnCache;
import com.kamehouse.psnservice.model.UserData;
import com.kamehouse.psnservice.service.PsnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class PsnResource {

    @Autowired
    private PsnService gamesCatalogService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${microservice.gameInfo.name}")
    private String gameInfoMicroService;

    @Value("${microservice.userData.name}")
    private String userDataMicroService;

    @RequestMapping("/listar")
    public List<PsnCache> listarCache(){
        return gamesCatalogService.listar();
    }

    private GameInfo getGameInfo(String nomeJogo){
        System.out.println("Pegando informacoes do jogo: "+nomeJogo);
        return restTemplate.getForObject("http://"+gameInfoMicroService+"/gamesInfo/"+nomeJogo, GameInfo.class);
    }

    private UserData getUserData(String nomeUsuario){
        System.out.println("Pegando informacoes do usuario: "+nomeUsuario);
        return restTemplate.getForObject("http://"+userDataMicroService+"/userService/"+nomeUsuario, UserData.class);
    }

    private void atualizaBaseUsuario(UserData userData){
            restTemplate.put("http://"+userDataMicroService+"/userService/atualizarUsuario/", userData);
    }

    @RequestMapping(method = RequestMethod.GET, value="/comprar/{nomeJogo}/usuario/{nomeUsuario}")
    public String comprarJogo(@PathVariable("nomeJogo") String nomeJogo,
                              @PathVariable("nomeUsuario") String nomeUsuario){

       final GameInfo gameInfo = getGameInfo(nomeJogo);
       final UserData userData = getUserData(nomeUsuario);
       userData.setListaGameInfo(new ArrayList<>(Arrays.asList(gameInfo)));

        //TODO: Trocar 1 este ponto para usar rabbitmq, depois tornar tudo eventlog
        System.out.println("Atualizando informacoes de compra do usuario: "+nomeUsuario);
        atualizaBaseUsuario(userData);

        return "O Usuário: "+nomeUsuario+" adquiriu o jogo: "+nomeJogo;
    }


}

package com.kamehouse.userdataservice.resource;

import com.kamehouse.userdataservice.model.GameInfo;
import com.kamehouse.userdataservice.model.UserData;
import com.kamehouse.userdataservice.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserDataResource {

    @Autowired
    UserDataService userDataService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${microservice.gameInfo.name}")
    private String gameInfoMicroservice;

    @RequestMapping("/listar")
    public List<UserData> listar(){
        return userDataService.listar();
    }
    @RequestMapping(value="/{nomeUsuario}")
    public UserData getUsuario(@PathVariable("nomeUsuario") String nomeUsuario){
        return userDataService.getUsuario(nomeUsuario);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/atualizarUsuario")
    public String atualizarUsuario(@RequestBody UserData userData){
        return userDataService.atualizaUsuario(userData);
    }

}

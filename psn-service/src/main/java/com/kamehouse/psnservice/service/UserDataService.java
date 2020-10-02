package com.kamehouse.psnservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamehouse.psnservice.model.GameInfo;
import com.kamehouse.psnservice.model.PsnCache;
import com.kamehouse.psnservice.model.UserData;
import com.kamehouse.psnservice.repository.PsnRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserDataService {

    private static final Logger logger = LogManager.getLogger(GameInfoService.class);

    @Value("${microservice.userData.name}")
    private String userDataMicroService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PsnService psnService;

    @HystrixCommand(fallbackMethod = "getFallbackUserData",
    commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="200"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000"),
    })
    public UserData getUserData(String nomeUsuario){
        logger.debug("Pegando informacoes do usuario: {}", nomeUsuario);
        UserData userData = restTemplate.getForObject("http://"+userDataMicroService+"/userService/"+nomeUsuario, UserData.class);

        PsnCache p = new PsnCache();
        p.salvarCache(userData, (g) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                p.setJsonUsuario(mapper.writeValueAsString(userData));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            psnService.salvarCache(p);
        });

        return userData;

    }

    @HystrixCommand(fallbackMethod = "fallbackAtualizaBaseUsuario",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="200"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000"),
            })
    public String atualizaBaseUsuario(UserData userData){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserData> requestEntity = new HttpEntity<>(userData, headers);

        ResponseEntity<HttpStatus> responseEntity = restTemplate.postForEntity("http://"+userDataMicroService+"/userService/atualizarUsuario/",requestEntity ,HttpStatus.class);

        if(responseEntity.getBody() != HttpStatus.OK){
            return "O Usuário(a): "+userData.getNome()+" já possúi este jogo.";
        }

       return "Jogo adquirido pelo Usuário: "+userData.getNome();
    }


    public UserData getFallbackUserData(String nomeUsuario){
        logger.debug("Pegando informacoes do Cache da PSN para o usuario: {}", nomeUsuario);
        //TODO:Tratar Cache futuramente
/*        List<PsnCache> cache = psnService.recuperaCacheList();
        PsnCache cacheUsuario = cache.stream().filter((c)-> c.getJsonUsuario().contains(nomeUsuario)).findFirst().get();
        UserData userioCache = new UserData();
        try {
            ObjectMapper o = new ObjectMapper();
            userioCache = o.readValue(cacheUsuario.getJsonUsuario(), UserData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userioCache;*/
        return new UserData();
    }

    public String fallbackAtualizaBaseUsuario(UserData userData){
        return "Compra não pode ser efetuada, tente novamente mais tarde.";
    }



}

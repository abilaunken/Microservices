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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Consumer;

@Service
public class GameInfoService {

    private static final Logger logger = LogManager.getLogger(GameInfoService.class);

    @Value("${microservice.gameInfo.name}")
    private String gameInfoMicroService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PsnService psnService;

    @HystrixCommand(fallbackMethod = "getFallbackGameInfo",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="200"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000"),
            })
    public GameInfo getGameInfo(String nomeJogo) {
        logger.debug("Pegando informacoes do jogo: {}", nomeJogo);
        GameInfo gameInfo = restTemplate.getForObject("http://" + gameInfoMicroService + "/gamesInfo/" + nomeJogo, GameInfo.class);

        PsnCache p = new PsnCache();
        p.salvarCache(gameInfo, (g) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                p.setJsonGames(mapper.writeValueAsString(gameInfo));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            psnService.salvarCache(p);
        });

        return gameInfo;
    }

    public GameInfo getFallbackGameInfo (String nomeJogo){
        logger.info("Pegando informacoes do Cache da PSN para informacoes do jogo : {}", nomeJogo);
        //TODO:Tratar Cache futuramente
       /* List<PsnCache> cache = psnService.recuperaCacheList();
        PsnCache cacheUsuario = cache.stream().filter((c)-> c.getJsonGames().contains(nomeJogo)).findFirst().get();
        GameInfo gameInfoCache = new GameInfo();
        try {
            ObjectMapper o = new ObjectMapper();
            gameInfoCache = o.readValue(cacheUsuario.getJsonGames(), GameInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
        return new GameInfo();
    }


}

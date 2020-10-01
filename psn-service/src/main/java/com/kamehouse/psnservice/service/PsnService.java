package com.kamehouse.psnservice.service;

import com.kamehouse.psnservice.model.PsnCache;
import com.kamehouse.psnservice.repository.PsnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class PsnService {

    @Autowired
    PsnRepository psnRepository;

    public List<PsnCache> listar() {
        List<PsnCache> value = new ArrayList<>();
        psnRepository.findAll().forEach(c -> value.add(c));
        return value;
    }

    public void salvarCache(PsnCache psnCache) {
        psnRepository.save(psnCache);
    }

    public List<PsnCache> recuperaCacheList(){
        List<PsnCache> value = new ArrayList<>();
        psnRepository.findAll().forEach(c -> value.add(c));
        return value;
    }


}

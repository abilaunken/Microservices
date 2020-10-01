package com.kamehouse.psnservice.repository;

import com.kamehouse.psnservice.model.PsnCache;
import org.springframework.data.repository.CrudRepository;

public interface PsnRepository extends CrudRepository<PsnCache, Integer> {
}

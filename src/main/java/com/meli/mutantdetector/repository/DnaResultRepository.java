package com.meli.mutantdetector.repository;

import com.meli.mutantdetector.model.DnaResult;
import org.springframework.data.repository.CrudRepository;

public interface DnaResultRepository extends CrudRepository<DnaResult, Long> {

    boolean existsByDna(String dna);

    Long countByIsMutant(boolean isMutant);
}

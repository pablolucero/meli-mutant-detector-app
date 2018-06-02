package com.meli.mutantdetector.repository;

import com.meli.mutantdetector.model.DnaResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DnaResultRepository extends JpaRepository<DnaResult, Long> {

    DnaResult findByDna(String dna);

    Long countByIsMutant(boolean isMutant);
}

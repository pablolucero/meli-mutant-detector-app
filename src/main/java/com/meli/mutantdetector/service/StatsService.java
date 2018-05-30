package com.meli.mutantdetector.service;

import com.meli.mutantdetector.model.Stats;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private static final Logger log = LoggerFactory.getLogger(StatsService.class);

    private DnaResultRepository dnaResultRepository;

    @Autowired
    public StatsService(DnaResultRepository dnaResultRepository) {
        this.dnaResultRepository = dnaResultRepository;
    }

    public Stats calculateStats() {

        log.info("Retrieving stats information");

        final long totalDnas = dnaResultRepository.count();
        final long mutant = dnaResultRepository.countByIsMutant(true);
        final long humans = totalDnas - mutant;

        return new Stats(mutant, humans);
    }
}

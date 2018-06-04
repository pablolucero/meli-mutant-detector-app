package com.meli.mutantdetector.service;

import com.meli.mutantdetector.model.Stats;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    private static final Logger log = LoggerFactory.getLogger(StatsServiceImpl.class);

    private DnaResultRepository dnaResultRepository;

    @Autowired
    public StatsServiceImpl(DnaResultRepository dnaResultRepository) {
        this.dnaResultRepository = dnaResultRepository;
    }

    @Override
    public Stats calculateStats() {

        long mutant = 0;
        long totalDnas = 0;

        try {
            totalDnas = dnaResultRepository.count();
            mutant = dnaResultRepository.countByIsMutant(true);
        } catch (DataAccessException e) {
            log.error("An error occurred while accessing the data store", e.getMessage());
        }

        log.info("Retrieving stats information");

        return new Stats(mutant, totalDnas);
    }
}

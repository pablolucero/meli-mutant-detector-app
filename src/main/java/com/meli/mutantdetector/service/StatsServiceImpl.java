package com.meli.mutantdetector.service;

import com.meli.mutantdetector.model.Stats;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        log.info("Retrieving stats information");

        final long totalDnas = dnaResultRepository.count();
        final long mutant = dnaResultRepository.countByIsMutant(true);
        final long humans = totalDnas - mutant;

        return new Stats(mutant, humans);
    }
}

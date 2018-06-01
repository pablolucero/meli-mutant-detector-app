package com.meli.mutantdetector.service;

import com.meli.mutantdetector.model.DnaResult;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersistenceServiceImpl implements PersistenceService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private DnaResultRepository dnaResultRepository;

    @Autowired
    public PersistenceServiceImpl(DnaResultRepository dnaResultRepository) {
        this.dnaResultRepository = dnaResultRepository;
    }

    @Async
    @Override
    public void persistDnaResult(List<String> dna, boolean isMutant) {

        final String dnaAsOneString = dna.stream().collect(Collectors.joining(""));
        final DnaResult dnaResult = new DnaResult(dnaAsOneString, isMutant);

        if (dnaResultRepository.existsByDna(dnaAsOneString)) {
            log.info(String.format("Dna %s already exist in the db", dna));
        } else {
            try {
                dnaResultRepository.save(dnaResult);
                log.info(String.format("Saved Dna: %s using async thread: %s", dna, Thread.currentThread()));

            } catch (DataAccessException e) {
                log.error(String.format("Error occurred while executing async saving in thread %s", Thread.currentThread()));
            }
        }
    }
}

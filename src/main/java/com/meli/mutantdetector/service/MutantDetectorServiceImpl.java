package com.meli.mutantdetector.service;

import com.meli.mutantdetector.detector.DnaDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MutantDetectorServiceImpl implements MutantDetectorService {

    private PersistenceService persistenceService;

    @Autowired
    public MutantDetectorServiceImpl(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public boolean isMutant(List<String> dna) throws IllegalArgumentException {
        final boolean isMutant = DnaDetector.isMutantDna(dna);
        persistenceService.persistDnaResult(dna, isMutant);
        return isMutant;
    }
}

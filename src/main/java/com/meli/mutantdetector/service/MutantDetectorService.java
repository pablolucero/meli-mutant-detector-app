package com.meli.mutantdetector.service;

import com.meli.mutantdetector.detector.DnaDetector;
import com.meli.mutantdetector.model.DnaResult;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MutantDetectorService {

    DnaResultRepository dnaResultRepository;

    @Autowired
    public MutantDetectorService(DnaResultRepository dnaResultRepository) {
        this.dnaResultRepository = dnaResultRepository;
    }

    public boolean isMutant(List<String> dna) {

        final boolean isMutant = DnaDetector.isMutantDna(dna);
        final DnaResult dnaResult = new DnaResult(dna.stream().collect(Collectors.joining("")), isMutant);
        if (!dnaResultRepository.existsByDna(dnaResult.getDna())) dnaResultRepository.save(dnaResult);

        return isMutant;
    }
}

package com.meli.mutantdetector.service;

import com.meli.mutantdetector.detector.DnaDetector;
import com.meli.mutantdetector.model.DnaResult;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MutantDetectorServiceImpl implements MutantDetectorService {

    private DnaResultRepository dnaResultRepository;

    @Autowired
    public MutantDetectorServiceImpl(DnaResultRepository dnaResultRepository) {
        this.dnaResultRepository = dnaResultRepository;
    }

    @Override
    public boolean isMutant(List<String> dna) throws IllegalArgumentException {

        final boolean isMutant = DnaDetector.isMutantDna(dna);
        final DnaResult dnaResult = new DnaResult(dna.stream().collect(Collectors.joining("")), isMutant);
        if (!dnaResultRepository.existsByDna(dnaResult.getDna())) dnaResultRepository.save(dnaResult);

        return isMutant;
    }
}

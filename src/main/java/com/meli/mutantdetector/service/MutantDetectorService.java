package com.meli.mutantdetector.service;

import com.meli.mutantdetector.detector.DnaDetector;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MutantDetectorService {
    public boolean isMutant(List<String> dna) {
        return DnaDetector.isMutantDna(dna);
    }
}

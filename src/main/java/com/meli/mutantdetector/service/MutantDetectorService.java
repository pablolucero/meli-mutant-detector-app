package com.meli.mutantdetector.service;

import java.util.List;

public interface MutantDetectorService {
    boolean isMutant(List<String> dna);
}

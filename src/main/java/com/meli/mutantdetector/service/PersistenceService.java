package com.meli.mutantdetector.service;

import java.util.List;

public interface PersistenceService {

    void persistDnaResult(List<String> dna, boolean isMutant);
}

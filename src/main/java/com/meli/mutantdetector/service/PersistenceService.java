package com.meli.mutantdetector.service;

import com.meli.mutantdetector.model.DnaResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PersistenceService {

    CompletableFuture<DnaResult> persistDnaResult(List<String> dna, boolean isMutant);
}

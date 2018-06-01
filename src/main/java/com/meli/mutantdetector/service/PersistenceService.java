package com.meli.mutantdetector.service;

import com.meli.mutantdetector.model.DnaResult;

public interface PersistenceService {

    void persistDnaResult(DnaResult dnaResult);
}

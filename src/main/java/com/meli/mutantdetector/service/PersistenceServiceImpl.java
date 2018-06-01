package com.meli.mutantdetector.service;

import com.meli.mutantdetector.model.DnaResult;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PersistenceServiceImpl implements PersistenceService {
    private DnaResultRepository dnaResultRepository;

    @Autowired
    public PersistenceServiceImpl(DnaResultRepository dnaResultRepository) {
        this.dnaResultRepository = dnaResultRepository;
    }

    @Async
    @Override
    public void persistDnaResult(DnaResult dnaResult) {
        if (!dnaResultRepository.existsByDna(dnaResult.getDna())) dnaResultRepository.save(dnaResult);
    }

}

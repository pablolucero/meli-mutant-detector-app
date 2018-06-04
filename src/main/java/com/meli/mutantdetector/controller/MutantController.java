package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.dto.DnaDTO;
import com.meli.mutantdetector.service.MutantDetectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MutantController {

    private static final Logger log = LoggerFactory.getLogger(MutantController.class);

    private MutantDetectorService mutantDetector;

    @Autowired
    public MutantController(MutantDetectorService mutantDetector) {
        this.mutantDetector = mutantDetector;
    }

    @RequestMapping(value = "/mutant", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> isMutant(@RequestBody DnaDTO dna) {

        log.info(String.format("Checking DNA: %s", dna.getDna()));

        try {
            if (mutantDetector.isMutant(dna.getDna())) {
                log.info(String.format("DNA: %s is mutant", dna.getDna()));
                return ResponseEntity.ok("OK");
            } else {
                log.info(String.format("DNA: %s is NOT mutant", dna.getDna()));
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

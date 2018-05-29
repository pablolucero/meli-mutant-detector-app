package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.dto.DnaDTO;
import com.meli.mutantdetector.service.MutantDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MutantController {

    @Autowired
    private MutantDetectorService mutantDetector;

    @RequestMapping(value = "/mutant", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<HttpStatus> isMutant(@RequestBody DnaDTO dna) {

        System.out.println(dna.toString());

        if (mutantDetector.isMutant(dna.getDna())) return ResponseEntity.ok(HttpStatus.OK);
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}

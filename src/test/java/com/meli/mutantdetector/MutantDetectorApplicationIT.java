package com.meli.mutantdetector;

import com.meli.mutantdetector.controller.MutantController;
import com.meli.mutantdetector.dto.DnaDTO;
import com.meli.mutantdetector.model.DnaResult;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MutantDetectorApplicationIT {

    @Autowired
    private DnaResultRepository dnaResultRepository;

    @Autowired
    private MutantController mutantController;

    @Before
    public void setUp() throws Exception {

    }

    @Test
	public void mutantAndStatsEndpointsIT() {

//        final DnaResult dnaResult = new DnaResult("AGDASASDASDAS", true);

        final List<String> mutantInputTwoMatches = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");

        final DnaDTO dnaDTO = new DnaDTO(mutantInputTwoMatches);

        final ResponseEntity<String> response = mutantController.isMutant(dnaDTO);
        assertSame(response.getStatusCode(), HttpStatus.OK);

//
//        dnaResultRepository.save(dnaResult);
//
//        final List<DnaResult> allDnas = dnaResultRepository.findAll();
//        assertEquals(1, allDnas.size());
    }

}

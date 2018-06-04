package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.MockRunnerBaseTest;
import com.meli.mutantdetector.dto.DnaDTO;
import com.meli.mutantdetector.service.MutantDetectorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MutantControllerTest extends MockRunnerBaseTest {

    private final List<String> testDnaMutant = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
    private final List<String> testDnaHuman = Arrays.asList("ATGTGT", "TAGTGC", "TTATGT", "AGACGG", "GAGTCA", "TCACTG");

    @Mock
    private MutantDetectorService mutantDetector;

    private MutantController mutantController;

    @Before
    public void setup() {
        mutantController = new MutantController(mutantDetector);
    }

    @Test
    public void isMutantTest() {
        DnaDTO input = new DnaDTO(testDnaMutant);

        when(mutantDetector.isMutant(testDnaMutant)).thenReturn(true);

        ResponseEntity<String> output = mutantController.isMutant(input);

        assertSame(output.getStatusCode(), HttpStatus.OK);

        verify(mutantDetector, times(1)).isMutant(testDnaMutant);
    }

    @Test
    public void isMutantTestHuman() {
        DnaDTO input = new DnaDTO(testDnaHuman);

        ResponseEntity<String> output = mutantController.isMutant(input);

        assertSame(output.getStatusCode(), HttpStatus.FORBIDDEN);

        verify(mutantDetector, times(1)).isMutant(testDnaHuman);
    }
}
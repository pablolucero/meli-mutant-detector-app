package com.meli.mutantdetector.service;

import com.meli.mutantdetector.MockRunnerBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MutantDetectorServiceImplTest extends MockRunnerBaseTest {
    private final List<String> testDna = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");

    @Mock
    private PersistenceService persistenceService;

    private MutantDetectorService mtsi;

    @Before
    public void setup() {
        mtsi = new MutantDetectorServiceImpl(persistenceService);
    }

    @Test
    public void isMutantTest() throws IllegalArgumentException {
        assertTrue(mtsi.isMutant(testDna));
        verify(persistenceService, atLeastOnce()).persistDnaResult(testDna, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isMutantTestInterruptedException() {

        doThrow(IllegalArgumentException.class)
                .when(persistenceService).persistDnaResult(testDna, true);

        assertTrue(mtsi.isMutant(testDna));
        verify(persistenceService, atLeastOnce()).persistDnaResult(testDna, true);
    }

}
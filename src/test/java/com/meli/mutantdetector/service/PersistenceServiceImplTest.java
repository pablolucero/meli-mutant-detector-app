package com.meli.mutantdetector.service;

import com.meli.mutantdetector.MockRunnerBaseTest;
import com.meli.mutantdetector.model.DnaResult;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersistenceServiceImplTest extends MockRunnerBaseTest {

    private final String testDnaId = "ATGCGACAGTGCTTATGTAGAAGGCCCCTATCACTG";
    private final List<String> testDna = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");

    @Mock
    private DnaResultRepository dnaResultRepository;

    private PersistenceService persistenceService;

    @Before
    public void setup() {
        persistenceService = new PersistenceServiceImpl(dnaResultRepository);
    }

    @Test
    public void persistDnaResult() throws ExecutionException, InterruptedException {
        when(dnaResultRepository.save(any())).thenReturn(new DnaResult(testDnaId, true));

        CompletableFuture<DnaResult> output = persistenceService.persistDnaResult(testDna, true);

        assertEquals(output.get().getDna(), testDnaId);
        assertTrue(output.get().isMutant());

        verify(dnaResultRepository, times(1)).save(any());
    }

    @Test
    public void alreadyPersistedDnaResult() throws ExecutionException, InterruptedException {
        final DnaResult dnaResult = new DnaResult(testDnaId, true);
        when(dnaResultRepository.save(any())).thenReturn(dnaResult);

        // the first time the DnaResult is not in the db, but yes it is the second time
        when(dnaResultRepository.findByDna(testDnaId)).thenReturn(null, dnaResult);

        CompletableFuture<DnaResult> output1 = persistenceService.persistDnaResult(testDna, true);
        CompletableFuture<DnaResult> output2 = persistenceService.persistDnaResult(testDna, true);

        assertEquals(output1.get().getDna(), testDnaId);
        assertTrue(output1.get().isMutant());
        assertEquals(output1.get(), output2.get());

        verify(dnaResultRepository, times(1)).save(any());
    }


    @Test
    public void persistDnaResultTestDataAccessException() {
        when(dnaResultRepository.save(any())).thenThrow(new DataRetrievalFailureException("reason") {});

        persistenceService.persistDnaResult(testDna, true);

        verify(dnaResultRepository, times(1)).save(any());
    }
}
package com.meli.mutantdetector.service;

import com.meli.mutantdetector.MockRunnerBaseTest;
import com.meli.mutantdetector.model.Stats;
import com.meli.mutantdetector.repository.DnaResultRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.dao.DataRetrievalFailureException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatsServiceImplTest extends MockRunnerBaseTest {

    @Mock
    private DnaResultRepository dnaResultRepository;

    private StatsService statsService;

    @Before
    public void setup() {
        statsService = new StatsServiceImpl(dnaResultRepository);
    }

    @Test
    public void calculateStats() {
        //total dna count
        when(dnaResultRepository.count()).thenReturn(110L);
        // mutant dna count
        when(dnaResultRepository.countByIsMutant(true)).thenReturn(10L);

        Stats output = statsService.calculateStats();

        assertEquals(100, output.getHumansCount());
        assertEquals(10, output.getMutantsCount());
        assertEquals(0.1, output.getRatio(), 0.0);

        verify(dnaResultRepository, times(1)).count();
        verify(dnaResultRepository, times(1)).countByIsMutant(true);
    }

    @Test
    public void getMutantStatisticsTestDataAccessException() {

        when(dnaResultRepository.count()).thenThrow(new DataRetrievalFailureException("reason") {
        });

        statsService.calculateStats();

        verify(dnaResultRepository, times(1)).count();
    }
}
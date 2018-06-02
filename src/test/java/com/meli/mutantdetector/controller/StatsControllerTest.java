package com.meli.mutantdetector.controller;

import com.meli.mutantdetector.MockRunnerBaseTest;
import com.meli.mutantdetector.dto.StatsDTO;
import com.meli.mutantdetector.model.Stats;
import com.meli.mutantdetector.service.StatsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StatsControllerTest extends MockRunnerBaseTest {

    @Mock
    private StatsService statsService;

    private StatsController statsController;

    @Before
    public void setup() {
        statsController = new StatsController(statsService);
    }

    @Test
    public void getMutantStatistics() {
        Stats input = new Stats(5, 100);

        when(statsService.calculateStats()).thenReturn(input);

        StatsDTO output = statsController.getMutantStatistics();

        assertEquals(100, output.getCount_human_dna());
        assertEquals(5, output.getCount_mutant_dna());
        assertEquals(0.05, output.getRatio(), 0.0);

        verify(statsService, times(1)).calculateStats();
    }
}
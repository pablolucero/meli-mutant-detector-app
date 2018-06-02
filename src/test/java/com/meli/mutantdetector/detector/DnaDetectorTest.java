package com.meli.mutantdetector.detector;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DnaDetectorTest {

    private final List<String> nullInput = null;
    private final List<String> notSquareMatrixInput = Arrays.asList("AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAA");
    private final List<String> tooSmallInput = Arrays.asList("AAA", "AAA", "AAA");
    private final List<String> invalidCharValueInput = Arrays.asList("AAAAAA", "AAAAAA", "AAZAAA", "AAAAAA", "AAAAAA", "AAAAAA");
    private final List<String> mutantInputOneMatchOnly = Arrays.asList("AAAAAA", "TAGTGC", "TTATGT", "AGACGG", "GAGTCA", "TCACTG");
    private final List<String> mutantInputTwoMatches = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
    private final List<String> mutantInputFourMatches = Arrays.asList("ATGCGA", "CGGGGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
    private final List<String> nonMutantInput = Arrays.asList("ATGTGT", "TAGTGC", "TTATGT", "AGACGG", "GAGTCA", "TCACTG");

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void isMutantDna_NullInput() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Null DNA");
        DnaDetector.isMutantDna(nullInput);
    }

    @Test
    public void isMutantDna_NotSquareMatrixInput() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("DNA sequence is not an NxN matrix");
        DnaDetector.isMutantDna(notSquareMatrixInput);
    }

    @Test
    public void isMutantDna_TooSmallInput() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage(startsWith("DNA sequence to small, must be at least:"));
        DnaDetector.isMutantDna(tooSmallInput);
    }

    @Test
    public void isMutantDna_InvalidCharValue() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Invalid argument. The DNA sequence must be conformed by the values A, T, C or G only.");
        DnaDetector.isMutantDna(invalidCharValueInput);
    }

    @Test
    public void isMutantDna_ZeroMatches() {
        boolean output = DnaDetector.isMutantDna(nonMutantInput);
        assertFalse("It's not a mutant because it needs more than one sequence to match", output);
    }

    @Test
    public void isMutantDna_OneMatch() {
        boolean output = DnaDetector.isMutantDna(mutantInputOneMatchOnly);
        assertFalse("It's not a mutant because it needs more than one sequence to match", output);
    }

    @Test
    public void isMutantDna_TwoMatches() {
        boolean output = DnaDetector.isMutantDna(mutantInputTwoMatches);
        assertTrue("It's a mutant", output);
    }

    @Test
    public void isMutantDna_FourMatches() {
        boolean output = DnaDetector.isMutantDna(mutantInputFourMatches);
        assertTrue("It's a mutant", output);
    }

}
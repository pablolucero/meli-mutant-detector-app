package com.meli.mutantdetector.detector;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DnaProcessorTest {

    private final List<String> stringsInput = Arrays.asList("AAAAAA", "BBBBBB", "CCCCCC", "DDDDDD", "EEEEEEE", "GGGGGG");
    private final List<String> verticalOutput = Arrays.asList("ABCDEG", "ABCDEG", "ABCDEG", "ABCDEG", "ABCDEG", "ABCDEG");
    private final List<String> diagonalOutputTB = Arrays.asList("ABCDEG", "ABCDE", "BCDEG", "ABCD", "CDEG");
    private final List<String> diagonalOutputBT = Arrays.asList("GEDCBA", "GEDCB", "EDCBA", "GEDC", "DCBA");

    @Test
    public void buildVerticalDnaStrings() {
        List<String> output = DnaProcessor.buildVerticalDnaStrings(stringsInput);

        assertEquals(output.size(), verticalOutput.size());
        assertEquals(verticalOutput, output);
    }

    @Test
    public void buildDnaStringsDiagonalFromTopLeftToBottom() {
        List<String> output = DnaProcessor.buildDnaStringsDiagonalFromTopLeftToBottom(stringsInput);

        assertEquals(output.size(), diagonalOutputTB.size());
        assertEquals(diagonalOutputTB, output);
    }

    @Test
    public void buildDnaStringsDiagonalFromBottomLeftToTop() {
        List<String> output = DnaProcessor.buildDnaStringsDiagonalFromBottomLeftToTop(stringsInput);

        assertEquals(output.size(), diagonalOutputBT.size());
        assertEquals(diagonalOutputBT, output);
    }
}
package com.meli.mutantdetector.detector;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DnaDetector {

    private static final Predicate<String> MUTANT_DNA_PREDICATE = s -> s.matches(".*(AAAA|CCCC|GGGG|TTTT).*");
    private static final int NUMBER_OF_SAME_CHARS_TO_CHECK = 4;

    public static boolean isMutantDna(final List<String> dnaStrings) throws IllegalArgumentException {

        validateDna(dnaStrings);

        // Horizontal processing don't need any special treatment
        int mutantDnaFoundCounter = dnaStrings.stream()
                .filter(MUTANT_DNA_PREDICATE)
                .collect(Collectors.toList()).size();
        if (mutantDnaFoundCounter > 1) return true;

        // Vertical processing
        mutantDnaFoundCounter += DnaProcessor.buildVerticalDnaStrings(dnaStrings).stream()
                .filter(MUTANT_DNA_PREDICATE)
                .collect(Collectors.toList()).size();
        if (mutantDnaFoundCounter > 1) return true;

        //Diagonal processing from top left to bottom
        mutantDnaFoundCounter += DnaProcessor.buildDnaStringsDiagonalFromTopLeftToBottom(dnaStrings).stream()
                .filter(MUTANT_DNA_PREDICATE)
                .collect(Collectors.toList()).size();
        if (mutantDnaFoundCounter > 1) return true;

        //Diagonal processing from bottom left to top
        mutantDnaFoundCounter += DnaProcessor.buildDnaStringsDiagonalFromBottomLeftToTop(dnaStrings).stream()
                .filter(MUTANT_DNA_PREDICATE)
                .collect(Collectors.toList()).size();
        return mutantDnaFoundCounter > 1;

    }

    private static void validateDna(List<String> dna) throws IllegalArgumentException {
        if (dna == null) {
            throw new IllegalArgumentException("Null DNA");
        }
        if (!isSquareMatrix(dna)) {
            throw new IllegalArgumentException("DNA sequence is not an NxN matrix");
        }
        if (dna.size() < NUMBER_OF_SAME_CHARS_TO_CHECK + 1) {
            throw new IllegalArgumentException("DNA sequence to small, must be at least: "
                    + (NUMBER_OF_SAME_CHARS_TO_CHECK + 1) + "x" + (NUMBER_OF_SAME_CHARS_TO_CHECK + 1));
        }
        if (dna.parallelStream().anyMatch(dnaRow -> !dnaRow.matches("^[ATCG]*$"))) {
            throw new IllegalArgumentException("Invalid argument. The DNA sequence must be conformed by the values A, T, C or G only.");
        }
    }

    private static boolean isSquareMatrix(List<String> dna) {
        final int rowAmount = dna.size();
        return dna.stream().noneMatch(row -> row.length() != rowAmount);
    }

}

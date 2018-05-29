package com.meli.mutantdetector.detector;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DnaDetector {

    private static final Predicate<String> MUTANT_DNA_PREDICATE = s -> s.matches(".*(AAAA|CCCC|GGGG|TTTT).*");

    public static boolean isMutantDna(final List<String> dnaStrings) {

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

}

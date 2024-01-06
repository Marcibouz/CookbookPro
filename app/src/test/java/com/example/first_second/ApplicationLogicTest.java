package com.example.first_second;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.first_second.application_logic.ApplicationLogic;


public class ApplicationLogicTest {
    /**
     * Im Normalfall wird ein zu erwartender String ausgegeben.
     */
    @Test
    public void calculatePortionNormal() {
        String testString = "500 g\tHackfleisch, gemischtes\n" +
                "1\tZwiebel(n)\n" +
                "2\tKnoblauchzehe(n)\n" +
                "1 Bund\tPetersilie oder TK\n" +
                "1 EL\tTomatenmark\n" +
                "1 Dose\tTomate(n), geschälte (800 g)\n" +
                "etwas\tRotwein";
        int multiplier = 2;
        assertEquals("1000 g\tHackfleisch, gemischtes\n" +
                "2\tZwiebel(n)\n" +
                "4\tKnoblauchzehe(n)\n" +
                "2 Bund\tPetersilie oder TK\n" +
                "2 EL\tTomatenmark\n" +
                "2 Dose\tTomate(n), geschälte (1600 g)\n" +
                "etwas\tRotwein", ApplicationLogic.calculatePortion(testString, multiplier));
    }

    /**
     * Wenn der Multiplikator 0 ist, soll sich der String nicht veraendern.
     */
    @Test
    public void calculatePortionMultiplikatorGleichNull() {
        String testString = "3 Eier und 5,5 Karoffeln.";
        int multiplier = 0;
        assertEquals("3 Eier und 5,5 Karoffeln.", ApplicationLogic.calculatePortion(testString, multiplier));
    }

    /**
     * Wenn der Multiplikator <0 ist, soll sich der String nicht veraendern.
     */
    @Test
    public void calculatePortionMultiplikatorKleinerNull() {
        String testString = "3 Eier und 5,5 Karoffeln.";
        int multiplier = -1;
        assertEquals("3 Eier und 5,5 Karoffeln.", ApplicationLogic.calculatePortion(testString, multiplier));
    }

    /**
     * Rezept ohne Zahlen bleibt unveraendert.
     */
    @Test
    public void calculatePortionNoNumbers() {
        String testString = "Ich bereite ein Rezept zu";
        int multiplier = 2;
        assertEquals("Ich bereite ein Rezept zu", ApplicationLogic.calculatePortion(testString, multiplier));
    }

    /**
     * Wenn der String null ist, wird IllegalArgumentException geworfen.
     */
    @Test
    public void calculatePortionStringNull() {
        String testString = null;
        int multiplier = 2;
        assertThrows(IllegalArgumentException.class, ()
                -> {
            ApplicationLogic.calculatePortion(testString, multiplier);
        });
    }

    /**
     * Jedes Komma einer Zahl sollte ein Komma bleiben.
     */
    @Test
    public void calculatePortionKommaBleibtKomma() {
        String testString = "123,123 14124,2324 234,234,234 3454,3454";
        int multiplier = 3;
        assertEquals("369,369 42372,6972 702,702,702 10363,0362",
                ApplicationLogic.calculatePortion(testString, multiplier));
    }

    /**
     * Jeder Punkt einer Zahl sollte ein Punkt bleiben.
     */
    @Test
    public void calculatePortionPunktBleibtPunkt() {
        String testString = "123.123 14124.2324 234.234.234 3454.3454";
        int multiplier = 3;
        assertEquals("369.369 42372.6972 702.702.702 10363.0362",
                ApplicationLogic.calculatePortion(testString, multiplier));
    }

    /**
     * Zahlen werden korrekt abgregrenzt, umgerechnet und dargestellt.
     */
    @Test
    public void calculatePortionKomplexeZahlenUndBuchstaben() {
        String testString = "123,123adw14124.2324.234adw234,234,234dw3454.3454";
        int multiplier = 3;
        assertEquals("369,369adw42372.6972.702adw702,702,702dw10363.0362",
                ApplicationLogic.calculatePortion(testString, multiplier));
    }
}
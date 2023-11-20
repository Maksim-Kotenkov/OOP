package ru.nsu.kotenkov.gradebook;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GradeBookTest {
    @Test
    @DisplayName("Init test")
    public void checkInit() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);

        HashSet<String> expected = new HashSet<>(startDisciplines);
        assertEquals(expected, book.getGradeBook().keySet());
    }

    @Test
    @DisplayName("Add mark test")
    public void checkAdd() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);
        book.addMark("Maths", 3);
        book.addMark("PE", 5);
        book.addMark("Programming", 4);
        book.addMark("Models", 5);

        assertEquals(3, book.getGradeBook().get("Maths").get(0));
        assertEquals(5, book.getGradeBook().get("PE").get(0));
        assertEquals(4, book.getGradeBook().get("Programming").get(0));
        assertEquals(5, book.getGradeBook().get("Models").get(0));
    }

    @Test
    @DisplayName("Average test")
    public void checkAvg() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);
        book.addMark("Maths", 4);
        book.addMark("PE", 5);
        book.addMark("Programming", 4);
        book.addMark("Models", 5);

        assertEquals(4.5, book.getAverage());
    }

    @Test
    @DisplayName("Red diploma false test")
    public void checkFalseDiploma() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);
        book.addMark("Maths", 4);
        book.addMark("PE", 5);
        book.addMark("Programming", 4);
        book.addMark("Models", 5);

        assertFalse(book.redDiploma());
    }

    @Test
    @DisplayName("2 Red diploma false test")
    public void checkFalseDiploma2() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);
        book.addMark("Maths", 4);
        book.addMark("PE", 5);
        book.addMark("Programming", 5);
        book.addMark("Models", 5);
        book.addMark("Some strange trash", 3);

        assertFalse(book.redDiploma());
    }

    @Test
    @DisplayName("Red diploma true test")
    public void checkTrueDiploma() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);
        book.addMark("Maths", 4);
        book.addMark("PE", 5);
        book.addMark("Programming", 5);
        book.addMark("Models", 5);

        assertTrue(book.redDiploma());
    }

    @Test
    @DisplayName("True Increased scholarship")
    public void checkScholarshipTrue() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);
        book.addMark("Maths", 5);
        book.addMark("PE", 5);
        book.addMark("Programming", 5);
        book.addMark("Models", 5);

        assertTrue(book.increasedScholarship());
    }

    @Test
    @DisplayName("False Increased scholarship")
    public void checkScholarshipFalse() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook(startDisciplines);
        book.addMark("Maths", 4);
        book.addMark("PE", 5);
        book.addMark("Programming", 5);
        book.addMark("Models", 5);

        assertFalse(book.increasedScholarship());
    }
}

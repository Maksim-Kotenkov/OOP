package ru.nsu.kotenkov.gradebook;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tester class.
 *
 */
public class GradeBookTest {
    @Test
    @DisplayName("Init test")
    public void checkInit() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);

        HashSet<String> expected = new HashSet<>(startDisciplines);
        assertEquals(expected, book.getGradeBook().get(1).keySet());
    }

    @Test
    @DisplayName("Username test")
    public void checkUsername() {
        GradeBook book = new GradeBook("Cringe Again");

        assertEquals("Cringe Again", book.getUsername());
    }

    @Test
    @DisplayName("Add mark test")
    public void checkAdd() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", 3);
        book.addMark(1, "PE", 5);
        book.addMark(1, "Programming", 4);
        book.addMark(1, "Models", 5);

        assertEquals(3, book.getSemesterMarks(1).get("Maths").get(0));
        assertEquals(5, book.getSemesterMarks(1).get("PE").get(0));
        assertEquals(4, book.getSemesterMarks(1).get("Programming").get(0));
        assertEquals(5, book.getSemesterMarks(1).get("Models").get(0));
    }

    @Test
    @DisplayName("Average test")
    public void checkAvg() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", 4);
        book.addMark(1, "PE", 5);
        book.addMark(1, "Programming", 4);
        book.addMark(1, "Models", 5);

        assertEquals(4.5, book.getAverage(1));
    }

    @Test
    @DisplayName("Red diploma false test")
    public void checkFalseDiploma() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", 4);
        book.addMark(1, "PE", 5);
        book.addMark(1, "Programming", 4);
        book.addMark(1, "Models", 5);

        assertFalse(book.redDiploma());
    }

    @Test
    @DisplayName("2 Red diploma false test")
    public void checkFalseDiploma2() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", 4);
        book.addMark(1, "PE", 5);
        book.addMark(1, "Programming", 5);
        book.addMark(1, "Models", 5);
        book.addMark(1, "Some strange trash", 3);

        assertFalse(book.redDiploma());
    }

    @Test
    @DisplayName("Red diploma true test")
    public void checkTrueDiploma() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", 4);
        book.addMark(1, "PE", 5);
        book.addMark(1, "Programming", 5);
        book.addMark(1, "Models", 5);

        assertTrue(book.redDiploma());
    }

    @Test
    @DisplayName("True Increased scholarship")
    public void checkScholarshipTrue() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", 5);
        book.addMark(1, "PE", 5);
        book.addMark(1, "Programming", 5);
        book.addMark(1, "Models", 5);

        assertTrue(book.increasedScholarship(1));
    }

    @Test
    @DisplayName("False Increased scholarship")
    public void checkScholarshipFalse() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", 4);
        book.addMark(1, "PE", 5);
        book.addMark(1, "Programming", 5);
        book.addMark(1, "Models", 5);

        assertFalse(book.increasedScholarship(1));
    }

    @Test
    @DisplayName("Exception test")
    public void checkException() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);

        assertThrowsExactly(IncorrectMarkException.class, () -> book.addMark(1, "Maths", 9));
    }
}

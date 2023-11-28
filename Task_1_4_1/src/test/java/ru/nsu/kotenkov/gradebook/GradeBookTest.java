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
        assertEquals(expected, book.getSemesterMarks(1).keySet());
    }

    @Test
    @DisplayName("Username test")
    public void checkUsername() {
        GradeBook book = new GradeBook("Cringe Again");

        assertEquals("Cringe Again", book.getOwnerName());
    }

    @Test
    @DisplayName("Add mark test")
    public void checkAdd() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", GradeBook.Mark.NOPASS);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.TWO);
        book.addMark(1, "Models", GradeBook.Mark.FOUR);

        assertEquals(GradeBook.Mark.NOPASS, book.getSemesterMarks(1).get("Maths").get(0));
        assertEquals(GradeBook.Mark.FIVE, book.getSemesterMarks(1).get("PE").get(0));
        assertEquals(GradeBook.Mark.TWO, book.getSemesterMarks(1).get("Programming").get(0));
        assertEquals(GradeBook.Mark.FOUR, book.getSemesterMarks(1).get("Models").get(0));
    }

    @Test
    @DisplayName("Average test")
    public void checkAvg() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", GradeBook.Mark.FOUR);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.FOUR);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);

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
        book.addMark(1, "Maths", GradeBook.Mark.FOUR);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.FOUR);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);

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
        book.addMark(1, "Maths", GradeBook.Mark.FOUR);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.FIVE);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);
        book.addMark(1, "Some strange trash", GradeBook.Mark.THREE);

        assertFalse(book.redDiploma());
    }

    @Test
    @DisplayName("Red diploma false because of not all semesters test")
    public void checkFalseDiploma3() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");
        startDisciplines.add("PE");
        startDisciplines.add("Programming");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);
        book.addMark(1, "Maths", GradeBook.Mark.FOUR);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.FIVE);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);

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
        book.addMark(1, "Maths", GradeBook.Mark.FOUR);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.FIVE);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);
        book.addMark(2, "Models", GradeBook.Mark.FIVE);
        book.addMark(3, "Models", GradeBook.Mark.FIVE);
        book.addMark(4, "Something", GradeBook.Mark.PASS);
        book.addMark(5, "Models", GradeBook.Mark.FIVE);
        book.addMark(6, "Models", GradeBook.Mark.FIVE);
        book.addMark(7, "Models", GradeBook.Mark.FIVE);
        book.addMark(8, "Models", GradeBook.Mark.FIVE);

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
        book.addMark(1, "Maths", GradeBook.Mark.FIVE);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.FIVE);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);

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
        book.addMark(1, "Maths", GradeBook.Mark.FOUR);
        book.addMark(1, "PE", GradeBook.Mark.FIVE);
        book.addMark(1, "Programming", GradeBook.Mark.FIVE);
        book.addMark(1, "Models", GradeBook.Mark.FIVE);

        assertFalse(book.increasedScholarship(1));
    }

    @Test
    @DisplayName("Semester exception test")
    public void checkSemesterException() {
        List<String> startDisciplines = new ArrayList<>();
        startDisciplines.add("Maths");

        GradeBook book = new GradeBook("Cringe Again", startDisciplines);

        assertThrowsExactly(IncorrectSemesterException.class,
                () -> book.addMark(13, "Maths", GradeBook.Mark.THREE)
        );
    }
}

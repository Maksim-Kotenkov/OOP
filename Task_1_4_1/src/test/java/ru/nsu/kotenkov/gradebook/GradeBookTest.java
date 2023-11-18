package ru.nsu.kotenkov.gradebook;


import static org.junit.jupiter.api.Assertions.assertEquals;

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

        HashSet<String> expected = new HashSet<>(startDisciplines);
        expected.add("Models");
        assertEquals(expected, book.getGradeBook().keySet());
        assertEquals(3, book.getGradeBook().get("Maths").get(0));
        assertEquals(5, book.getGradeBook().get("PE").get(0));
        assertEquals(4, book.getGradeBook().get("Programming").get(0));
        assertEquals(5, book.getGradeBook().get("Models").get(0));
    }
}

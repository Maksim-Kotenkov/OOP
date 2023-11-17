package ru.nsu.kotenkov.gradebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeBook {
    private final Map <String, List<Integer>> gradeBook;

    public GradeBook(List<String> disciplines) {
        gradeBook = HashMap.newHashMap(100);
        for (String str : disciplines) {
            gradeBook.put(str, new ArrayList<>());
        }
    }
}

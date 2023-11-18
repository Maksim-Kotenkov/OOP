package ru.nsu.kotenkov.gradebook;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GradeBook {
    private final HashMap<String, List<Integer>> gradeBook;

    public GradeBook(List<String> disciplines) {
        gradeBook = HashMap.newHashMap(100);
        for (String str : disciplines) {
            gradeBook.put(str, new ArrayList<>());
        }
    }

    public HashMap<String, List<Integer>> getGradeBook() {
        return gradeBook;
    }

    public void addMark(String discipline, int mark) throws IncorrectMarkException{
        if (!(2 <= mark && mark <= 5)) {
            throw new IncorrectMarkException("Incorrect mark");
        } else {
            if (this.gradeBook.containsKey(discipline)) {
                this.gradeBook.get(discipline).add(mark);
            } else {
                List<Integer> newMark = new ArrayList<>();
                newMark.add(mark);
                this.gradeBook.put(discipline, newMark);
            }
        }
    }
}

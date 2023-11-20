package ru.nsu.kotenkov.gradebook;


import java.util.*;

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

    public List<Integer> getLastMarks() {
        List<Integer> res = new ArrayList<>();
        for (String key : this.gradeBook.keySet()) {
            res.add(this.gradeBook.get(key).get(gradeBook.get(key).size() - 1));
        }

        return res;
    }

    public double getAverage() {
        List<Integer> lastMarks = this.getLastMarks();
        int res = 0;

        for (Integer val : lastMarks) {
            res = res + val;
        }

        return (double) res / lastMarks.size();
    }

    public boolean redDiploma() {
        List<Integer> lastMarks = this.getLastMarks();

        int res = 0;

        for (Integer val : lastMarks) {
            if (val == 5) {
                res++;
            }
        }

        return Double.compare((double) res / lastMarks.size(), 0.75) >= 0;
    }

    public boolean increasedScholarship() {
        Set<Integer> setMarks = new HashSet<>(this.getLastMarks());

        return setMarks.equals(new HashSet<>(List.of(5)));
    }
}

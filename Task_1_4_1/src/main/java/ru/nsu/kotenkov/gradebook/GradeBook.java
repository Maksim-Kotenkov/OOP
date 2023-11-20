package ru.nsu.kotenkov.gradebook;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * GradeBook class that can help with computing average gradebook
 * mark and student's chances to get red diploma or increased scholarship.
 */
public class GradeBook {
    /**
     * To store marks class uses HashMap.
     */
    private final HashMap<String, List<Integer>> gradeBook;

    /**
     * Constructor that fills HashMap with start disciplines.
     *
     * @param disciplines list of Strings
     */
    public GradeBook(List<String> disciplines) {
        gradeBook = new HashMap<>(100);
        for (String str : disciplines) {
            gradeBook.put(str, new ArrayList<>());
        }
    }

    /**
     * Basic getter.
     *
     * @return hashMap of disciplines and marks
     */
    public HashMap<String, List<Integer>> getGradeBook() {
        return gradeBook;
    }

    /**
     * Adding a new mark.
     * The discipline can already exist and can be new, both ways are implemented.
     *
     * @param discipline string name of a discipline
     * @param mark int value in bounds [2, 5]
     * @throws IncorrectMarkException is thrown if the mark value is out of bounds
     */
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

    /**
     * A method to get last marks in every discipline.
     *
     * @return List of integers in bounds [2, 5]
     */
    public List<Integer> getLastMarks() {
        List<Integer> res = new ArrayList<>();
        for (String key : this.gradeBook.keySet()) {
            res.add(this.gradeBook.get(key).get(gradeBook.get(key).size() - 1));
        }

        return res;
    }

    /**
     * A method to get double value of an average of all marks.
     *
     * @return double value
     */
    public double getAverage() {
        List<Integer> lastMarks = this.getLastMarks();
        int res = 0;

        for (Integer val : lastMarks) {
            res = res + val;
        }

        return (double) res / lastMarks.size();
    }

    /**
     * A method to check if it's possible to get a red diploma.
     *
     * @return true/false
     */
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

    /**
     * A method to check if it's possible to get increased scholarship according to last marks.
     *
     * @return true/false
     */
    public boolean increasedScholarship() {
        HashSet<Integer> setMarks = new HashSet<>(this.getLastMarks());

        return setMarks.equals(new HashSet<>(List.of(5)));
    }
}

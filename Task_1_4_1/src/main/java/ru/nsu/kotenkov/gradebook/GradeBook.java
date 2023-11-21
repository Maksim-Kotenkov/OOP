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
    private final HashMap<Integer, HashMap<String, List<Integer>>> gradeBook;
    private final String ownerName;
    private final int disciplineCapacity = 100;
    private final int marksCapacity = 30;

    /**
     * Constructor that fills HashMap with start disciplines.
     *
     * @param disciplines list of Strings
     */
    public GradeBook(String username, List<String> disciplines) {
        this.ownerName = username;
        gradeBook = new HashMap<>(this.disciplineCapacity);
        gradeBook.put(1, new HashMap<>());
        for (String str : disciplines) {
            gradeBook.get(1).put(str, new ArrayList<>());
        }
    }

    /**
     * Constructor without pre-initialized disciplines.
     *
     * @param username String name of the owner
     */
    public GradeBook(String username) {
        this.ownerName = username;
        gradeBook = new HashMap<>(100);
        gradeBook.put(1, new HashMap<>(30));
    }

    /**
     * Getter for user name.
     *
     * @return String username
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Basic getter.
     *
     * @return hashMap of semesters with disciplines and marks
     */
    public HashMap<Integer, HashMap<String, List<Integer>>> getGradeBook() {
        return gradeBook;
    }

    /**
     * Getter for one semester.
     *
     * @param semester the number of the semester
     * @return HashMap of disciplines and marks
     */
    public HashMap<String, List<Integer>> getSemesterMarks(int semester) {
        return this.gradeBook.get(semester);
    }

    /**
     * Adding a new mark.
     * The discipline can already exist and can be new, both ways are implemented.
     *
     * @param semester the number of the semester
     * @param discipline string name of a discipline
     * @param mark int value in bounds [2, 5]
     * @throws IncorrectMarkException is thrown if the mark value is out of bounds
     */
    public void addMark(int semester, String discipline, int mark) throws IncorrectMarkException {
        if (!(2 <= mark && mark <= 5)) {
            throw new IncorrectMarkException("Incorrect mark");
        } else if (!(1 <= semester && semester <= 12)) {
            throw new IncorrectSemesterException("Incorrect semester");
        } else {
            if (!this.gradeBook.containsKey(semester)) {
                this.gradeBook.put(semester, new HashMap<>(this.marksCapacity));
            }
            if (this.gradeBook.get(semester).containsKey(discipline)) {
                this.gradeBook.get(semester).get(discipline).add(mark);
            } else {
                List<Integer> newMark = new ArrayList<>();
                newMark.add(mark);
                this.gradeBook.get(semester).put(discipline, newMark);
            }
        }
    }

    /**
     * A method to get last marks in every discipline.
     *
     * @param semester the number of the semester
     * @return List of integers in bounds [2, 5]
     */
    public List<Integer> getLastMarks(int semester) {
        List<Integer> res = new ArrayList<>();
        for (String key : this.gradeBook.get(semester).keySet()) {
            res.add(this.gradeBook.get(semester).get(key).get(
                        gradeBook.get(semester).get(key).size() - 1
                    )
            );
        }

        return res;
    }

    /**
     * A method to get double value of an average of all marks.
     *
     * @param semester the number of the semester
     * @return double value
     */
    public double getAverage(int semester) {
        List<Integer> lastMarks = this.getLastMarks(semester);
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
        int res = 0;
        int totalNumber = 0;
        for (int semester : this.gradeBook.keySet()) {
            List<Integer> lastMarks = this.getLastMarks(semester);

            for (Integer val : lastMarks) {
                if (val == 5) {
                    res++;
                }
                totalNumber++;
            }
        }

        return Double.compare((double) res / totalNumber, 0.75) >= 0;
    }

    /**
     * A method to check if it's possible to get increased scholarship according to last marks.
     *
     * @param semester the number of the semester
     * @return true/false
     */
    public boolean increasedScholarship(int semester) {
        HashSet<Integer> setMarks = new HashSet<>(this.getLastMarks(semester));

        return setMarks.equals(new HashSet<>(List.of(5)));
    }
}

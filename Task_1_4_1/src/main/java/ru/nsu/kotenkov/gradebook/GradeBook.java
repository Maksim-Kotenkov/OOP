package ru.nsu.kotenkov.gradebook;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * GradeBook class that can help with computing average gradebook
 * mark and student's chances to get red diploma or increased scholarship.
 */
public class GradeBook {
    /**
     * Enum for marks and pass.
     */
    public enum Mark {
        FIVE(5),
        FOUR(4),
        THREE(3),
        TWO(2),
        PASS(1),
        NOPASS(0);

        public final int val;

        /**
         * Constructor to store int values in enum.
         *
         * @param i the value that is assigned to the enum value
         */
        Mark (int i) {
            this.val = i;
        }

        /**
         * Getter for int values.
         *
         * @return int
         */
        public int getVal() {
            return val;
        }
    }

    /**
     * To store marks class uses HashMap.
     */
    private final Map<Integer, Map<String, List<Mark>>> gradeBook;
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
        gradeBook = new HashMap<>(this.disciplineCapacity);
        gradeBook.put(1, new HashMap<>(this.marksCapacity));
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
    public Map<Integer, Map<String, List<Mark>>> getGradeBook() {
        return gradeBook;
    }

    /**
     * Getter for one semester.
     *
     * @param semester the number of the semester
     * @return HashMap of disciplines and marks
     */
    public Map<String, List<Mark>> getSemesterMarks(int semester) {
        return this.gradeBook.get(semester);
    }

    /**
     * Adding a new mark.
     * The discipline can already exist and can be new, both ways are implemented.
     *
     * @param semester the number of the semester
     * @param discipline string name of a discipline
     * @param mark int value in bounds [2, 5]
     * @throws IncorrectSemesterException is thrown if the semester value is out of bounds
     */
    public void addMark(int semester, String discipline, Mark mark) throws IncorrectSemesterException {
        if (!(1 <= semester && semester <= 12)) {
            throw new IncorrectSemesterException("Incorrect semester");
        } else {
            if (!this.gradeBook.containsKey(semester)) {
                this.gradeBook.put(semester, new HashMap<>(this.marksCapacity));
            }
            if (this.gradeBook.get(semester).containsKey(discipline)) {
                this.gradeBook.get(semester).get(discipline).add(mark);
            } else {
                List<Mark> newMark = new ArrayList<>();
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
    public List<Mark> getLastMarks(int semester) {
        List<Mark> res = new ArrayList<>();
        for (String key : this.gradeBook.get(semester).keySet()) {
            final var marks = this.gradeBook.get(semester).get(key);
            res.add(marks.get(
                        marks.size() - 1
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
        List<Mark> lastMarks = this.getLastMarks(semester);
        int res;

        res = lastMarks.stream()
                .filter(a -> a.getVal() != 0 && a.getVal() != 1)
                .mapToInt(Mark::getVal)
                .sum();

        return (double) res / lastMarks.size();
    }

    /**
     * A method to check if it's possible to get a red diploma.
     *
     * @return true/false
     */
    public boolean redDiploma() {
        long res = 0;
        int totalNumber = 0;

        int[] semesters = {1, 2, 3, 4, 5, 6, 7, 8};
        List<Integer> list = Arrays.stream(semesters).boxed().toList();

        if (!this.gradeBook.keySet().equals(new HashSet<>(list))) {
            return false;
        }

        for (int semester : this.gradeBook.keySet()) {
            List<Mark> lastMarks = this.getLastMarks(semester);

            res += lastMarks.stream()
                    .filter(a -> a.getVal() == 5 || a.getVal() == 1)
                    .count();
            totalNumber += lastMarks.size();
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
        HashSet<Mark> setMarks = new HashSet<>(this.getLastMarks(semester));

        return setMarks.equals(new HashSet<>(List.of(Mark.FIVE)));
    }
}

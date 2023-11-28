package ru.nsu.kotenkov.gradebook;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Grade book Map class for better API.
 * It lets us use intuitive algorithms of adding marks from the GradeBook class.
 *
 */
public class GradeBookMap {
    /**
     * Fields we need to perform everything.
     */
    private final Map<Integer, Map<String, List<GradeBook.Mark>>> gradeBook;
    private final int disciplineCapacity;
    private final int marksCapacity;

    /**
     * Constructor.
     *
     * @param disciplines String list of disciplines at the start
     * @param disciplineCapacity capacity we want to use
     * @param marksCapacity capasicty we want to use
     */
    public GradeBookMap(List<String> disciplines, int disciplineCapacity, int marksCapacity) {
        this.disciplineCapacity = disciplineCapacity;
        this.marksCapacity = marksCapacity;
        gradeBook = new HashMap<>(disciplineCapacity);
        gradeBook.put(1, new HashMap<>(marksCapacity));
        Map<String, List<GradeBook.Mark>> disciplineMap = gradeBook.get(1);
        for (String str : disciplines) {
            disciplineMap.put(str, new ArrayList<>());
        }
    }

    /**
     * Getter for the whole Map.
     *
     * @return awful Map
     */
    public Map<Integer, Map<String, List<GradeBook.Mark>>> getGradeBook() {
        return gradeBook;
    }

    /**
     * Get all semesters.
     *
     * @return semesters set
     */
    public Set<Integer> getSemesterSet() {
        return this.gradeBook.keySet();
    }

    /**
     * Putting a mark. There we can handle if there is semester and the discipline or not.
     *
     * @param semester int of semester
     * @param discipline str name of disciopline
     * @param mark the mark (enum)
     */
    public void putMark(int semester, String discipline, GradeBook.Mark mark) {
        if (!this.gradeBook.containsKey(semester)) {
            this.gradeBook.put(semester, new HashMap<>(this.disciplineCapacity));
        }
        if (this.gradeBook.get(semester).containsKey(discipline)) {
            this.gradeBook.get(semester).get(discipline).add(mark);
        } else {
            List<GradeBook.Mark> newMark = new ArrayList<>(this.marksCapacity);
            newMark.add(mark);
            this.gradeBook.get(semester).put(discipline, newMark);
        }
    }

    /**
     * Get all marks in the semester.
     *
     * @param semester int number of the sem
     * @return map of marks
     */
    public Map<String, List<GradeBook.Mark>> getSemester(int semester) {
        return this.gradeBook.get(semester);
    }
}

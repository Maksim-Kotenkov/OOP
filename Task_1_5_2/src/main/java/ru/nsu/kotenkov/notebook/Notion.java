package ru.nsu.kotenkov.notebook;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Notion class, that stores all the info we need.
 */
public class Notion implements Comparable<Notion> {
    /**
     * Fields to store all we need.
     */
    private String label;
    private String date;
    private String description;

    /**
     * Setter.
     *
     * @param label the label we want to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter.
     *
     * @return string label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter.
     *
     * @param date the date we want to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter.
     *
     * @return string format of the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter.
     *
     * @param description the description of the notion we want to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter.
     *
     * @return string description
     */
    public String getDescription() {
        return description;
    }

    /**
     * The comparing method for sorting.
     *
     * @param notion other notion object
     * @return int like the compare return
     */
    @Override
    public int compareTo(Notion notion) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
            Date thisDate = format.parse(this.date);
            Date otherDate = format.parse(notion.getDate());

            return thisDate.compareTo(otherDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

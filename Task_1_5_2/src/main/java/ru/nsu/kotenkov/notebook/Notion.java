package ru.nsu.kotenkov.notebook;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notion implements Comparable<Notion> {
    private String label;
    private String date;
    private String description;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

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

package ru.nsu.kotenkov.oopchecker.graphics;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ResultsTable {
    private int width = 1920;
    private int height = 1080;

    private final String[] columnNames = {"Name", "Age", "Student", "Checker results"};
    private HashMap<String, HashMap<Integer, HashMap<String, HashMap<String, String>>>> results;


    public ResultsTable(HashMap<String, HashMap<Integer, HashMap<String, HashMap<String, String>>>> results) {
        this.results = results;
    }

    private DefaultTableModel UglyTable() {

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String lab : this.results.keySet()) {
//            DefaultTableModel labTable = new DefaultTableModel(
//                    new Object[] {"Group", "Students"}, 0
//            );
            for (Integer group : this.results.get(lab).keySet()) {
                HashMap<String, HashMap<String, String>> groupMap = this.results.get(lab).get(group);
                for (String student : groupMap.keySet()) {
                    model.addRow(new Object[] {lab, group, student, groupMap.get(student)});
                }

//                model.addRow(new Object[] {lab, group, dataVector});
//                model.newRowsAdded(new TableModelEvent(model, 0, model.getRowCount() - 1,
//                        TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            }
        }

        return model;

//        this.data = new Object[][] {
//                {"Ken", 5, false},
//                {"Tom", 3, true},
//                {"Susam", 2, false},
//                {"Mark", 20, true},
//                {"Joe", 10, false}
//        };
//        this.data = results;
    }

    public void showUglyTable() {
        JFrame frame = new JFrame();

        DefaultTableModel model = UglyTable();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);

        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame PrettyPanels() {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(this.results.size(),1));
        ArrayList<String> sorted = new ArrayList<String> (this.results.keySet());
        Collections.sort(sorted);
        for (String lab : sorted) {
            JPanel labPanel = new JPanel();
            labPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//            labPanel.setSize(new Dimension((int) (width * 0.9), height / this.results.size()));
//            labPanel.setLayout(new GridLayout(1, this.results.get(lab).size()));
            labPanel.add(new JLabel(lab));

            for (Integer group : this.results.get(lab).keySet()) {
                HashMap<String, HashMap<String, String>> groupMap = this.results.get(lab).get(group);

                JPanel groupPanel = new JPanel();
                groupPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//                groupPanel.setSize(new Dimension((int) (labPanel.getWidth() * 0.9), labPanel.getHeight()));
                groupPanel.setLayout(new GridLayout(groupMap.size() + 1, 1));
                groupPanel.add(new JLabel(group.toString()));
                labPanel.add(new ScrollPane().add(groupPanel));

                for (String student : groupMap.keySet()) {
                    JPanel studentPanel = new JPanel();
//                    studentPanel.setSize(new Dimension((int) (groupPanel.getWidth() * 0.9), (int) (groupPanel.getHeight())));
                    studentPanel.setLayout(new GridLayout(1, groupMap.get(student).size() + 1));
                    studentPanel.add(new JLabel(student));
                    groupPanel.add(studentPanel);

                    DefaultTableModel model = new DefaultTableModel(
                            new Object[] {
                                    "BUILD",
                                    "JAVADOC",
                                    "TEST"
                            },
                            0);

                    model.addRow(new Object[]{
                            "build: " + groupMap.get(student).get("build"),
                            "javadoc: " + groupMap.get(student).get("javadoc"),
                            "test: " + groupMap.get(student).get("test")
                    });

//                    JScrollPane scrollPane = new JScrollPane();
//                    scrollPane.setViewportView(new JTable(model));
//                    scrollPane.setSize(new Dimension((int) (studentPanel.getWidth() * 0.5), (int) (studentPanel.getHeight())));

//                    studentPanel.add(scrollPane);
                    studentPanel.add(new JTable(model));
                }
            }

            frame.add(labPanel);
        }

        return frame;
    }

    public void showPrettyPanels() {
        JFrame frame = PrettyPanels();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

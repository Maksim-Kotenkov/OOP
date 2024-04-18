package ru.nsu.kotenkov.oopchecker.graphics;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ResultsTable {
    private final String[] columnNames = {"Name", "Age", "Student", "Checker results"};
    private DefaultTableModel model;

    public ResultsTable(HashMap<String, HashMap<Integer, HashMap<String, HashMap<String, String>>>> results) {
        this.model = new DefaultTableModel(columnNames, 0);

        for (String lab : results.keySet()) {
//            DefaultTableModel labTable = new DefaultTableModel(
//                    new Object[] {"Group", "Students"}, 0
//            );
            for (Integer group : results.get(lab).keySet()) {
                HashMap<String, HashMap<String, String>> groupMap = results.get(lab).get(group);
                for (String student : groupMap.keySet()) {
                    model.addRow(new Object[] {lab, group, student, groupMap.get(student)});
                }

//                model.addRow(new Object[] {lab, group, dataVector});
//                model.newRowsAdded(new TableModelEvent(model, 0, model.getRowCount() - 1,
//                        TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
            }
        }

//        this.data = new Object[][] {
//                {"Ken", 5, false},
//                {"Tom", 3, true},
//                {"Susam", 2, false},
//                {"Mark", 20, true},
//                {"Joe", 10, false}
//        };
//        this.data = results;
    }

    public void showWindow() {
        JFrame frame = new JFrame();

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);

        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

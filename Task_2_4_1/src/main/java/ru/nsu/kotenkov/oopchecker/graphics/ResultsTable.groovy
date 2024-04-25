package ru.nsu.kotenkov.oopchecker.graphics;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

width = 1920;
height = 1080;

columnNames = ["Name", "Age", "Student", "Checker results"]

// get results
GroovyShell shell = new GroovyShell();
source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
        "oopchecker/groovyscripts/checkGroupLab.groovy"));
HashMap results = (HashMap) shell.run(source, Collections.singletonList(""));

// creating panels
JFrame PrettyPanels(results) {
    JFrame frame = new JFrame();
    frame.setLayout(new GridLayout(results.size(),1));
    ArrayList<String> sorted = new ArrayList<String> (results.keySet());
    Collections.sort(sorted);
    for (String lab : sorted) {
        JPanel labPanel = new JPanel()
        labPanel.setBorder(BorderFactory.createLineBorder(Color.black))
        labPanel.add(new JLabel(lab))

        for (Integer group : results.get(lab).keySet()) {
            HashMap<String, HashMap<String, String>> groupMap = results.get(lab).get(group);

            JPanel groupPanel = new JPanel();
            groupPanel.setBorder(BorderFactory.createLineBorder(Color.black))
            groupPanel.setLayout(new GridLayout(groupMap.size() + 1, 1));
            groupPanel.add(new JLabel(group.toString()));
            labPanel.add(new ScrollPane().add(groupPanel));

            for (String student : groupMap.keySet()) {
                JPanel studentPanel = new JPanel();
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

                studentPanel.add(new JTable(model));
            }
        }

        frame.add labPanel
    }

    return frame
}


JFrame frame = PrettyPanels(results);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
frame.setSize(width, height)
frame.setVisible(true)


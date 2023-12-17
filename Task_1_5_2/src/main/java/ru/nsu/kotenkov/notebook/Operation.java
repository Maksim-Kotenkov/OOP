package ru.nsu.kotenkov.notebook;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Operation enum, that connects string form of a command with operation object.
 */
public enum Operation {
    /**
     * Operations for working with the notebook.
     */
    ADD(2) {
        /**
         * Add action.
         *SimpleDateFormat("dd.MM.yyyy HH.mm")
         * @param args List of args (number of args already correct)
         * @throws IOException if the notebook.json file doesn't exist
         */
        @Override
        void action(List<String> args) throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            Notion newNotion = new Notion();
            newNotion.setLabel(args.get(0));
            newNotion.setDescription(args.get(1));
            newNotion.setDate(format.format(new java.util.Date()));

            File json = Paths.get("notebook.json").toFile();
            ObjectMapper mapper = new ObjectMapper();
            List<Notion> listNotions = new ArrayList<>(Arrays.asList(mapper.readValue(json, Notion[].class)));

            listNotions.add(newNotion);

            mapper.writerWithDefaultPrettyPrinter().writeValue(json, listNotions);
        }
    },
    SHOW(2) {
        /**
         * Show actions within time bounds.
         *
         * @param args List of args (number of args already correct)
         * @throws IOException if the notebook.json file doesn't exist
         */
        @Override
        void action(List<String> args) throws IOException, ParseException {
            File json = Paths.get("notebook.json").toFile();
            ObjectMapper mapper = new ObjectMapper();
            List<Notion> listNotions = Arrays.asList(mapper.readValue(json, Notion[].class));
            List<Notion> outputNotions = new ArrayList<>();

            if (!args.isEmpty()) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date from = format.parse(args.get(0));
                Date to = format.parse(args.get(1));

                for (Notion notion: listNotions) {
                    Date date = format.parse(notion.getDate());
                    if (date.after(from) && to.after(date)) {
                        outputNotions.add(notion);
                    }
                }
            } else {
                outputNotions = listNotions;
            }

            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputNotions);
            System.out.println(result);
        }
    },
    RM(1) {
        /**
         * Deleting action.
         *
         * @param args List of args (number of args already correct)
         * @throws IOException if the notebook.json file doesn't exist
         */
        @Override
        void action(List<String> args) throws IOException {
            File json = Paths.get("notebook.json").toFile();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.readValue(json, ObjectNode.class);

            root.remove(args.get(0));
            mapper.writerWithDefaultPrettyPrinter().writeValue(json, root);
        }
    };

    /**
     * THe number of arguments for the operation.
     */
    private final int valence;

    /**
     * Constructor to store operations with their valences.
     *
     * @param i the valence that is assigned to the enum value
     */
    Operation(int i) {
        this.valence = i;
    }

    /**
     * Getter for valences of operations.
     *
     * @return int
     */
    public int getValence() {
        return valence;
    }

    /**
     * Abstract method to do actions according to the Operation type.
     *
     * @param args List of args (number of args already correct)
     */
    abstract void action(List<String> args) throws IOException, ParseException;
}

package ru.nsu.kotenkov.notebook;


import com.fasterxml.jackson.databind.ObjectMapper;
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
    ADD {
        /**
         * Add action.
         *
         * @param args List of args (number of args already correct)
         * @throws IOException if the notebook.json file doesn't exist
         */
        @Override
        void action(List<String> args) throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            Notion newNotion = new Notion();
            newNotion.setLabel(args.get(0));
            newNotion.setDescription(args.get(1));
            newNotion.setDate(format.format(new java.util.Date()));

            File json = Paths.get("notebook.json").toFile();
            ObjectMapper mapper = new ObjectMapper();
            List<Notion> listNotions = new ArrayList<>(Arrays.asList(
                    mapper.readValue(json, Notion[].class)));

            listNotions.add(newNotion);

            mapper.writerWithDefaultPrettyPrinter().writeValue(json, listNotions);
        }

        /**
         * To add anything we need to specify the label and the description.
         *
         * @param args List of args (number of args already correct)
         * @return boolean if args is correct
         */
        @Override
        boolean checkArgs(List<String> args) {
            return args.size() == 2;
        }
    },
    SHOW {
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

            if (args.size() >= 2) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Date from = format.parse(args.get(0));
                Date to = format.parse(args.get(1));

                for (Notion notion : listNotions) {
                    Date date = format.parse(notion.getDate());
                    if (date.after(from) && to.after(date)) {
                        outputNotions.add(notion);
                    }
                }

                if (args.size() > 2) {
                    for (String keyword : args.subList(2, args.size())) {
                        outputNotions = outputNotions.stream()
                                .filter(a -> a.getLabel().contains(keyword))
                                .toList();
                    }
                }
            } else {
                outputNotions = listNotions;
            }

            List<Notion> result = outputNotions
                    .stream()
                    .sorted(Notion::compareTo)
                    .toList();
            System.out.println("=== YOUR NOTEBOOK ===");
            for (Notion res : result) {
                System.out.println("---");
                System.out.println(res.getDate());
                System.out.println(res.getLabel() + ":");
                System.out.println("    " + res.getDescription());
                System.out.println("---");
            }
        }

        /**
         * Show command can be called without arguments, or with timestamp and keywords.
         *
         * @param args List of args (number of args already correct)
         * @return boolean if args is correct
         */
        @Override
        boolean checkArgs(List<String> args) {
            return args.isEmpty() || args.size() >= 2;
        }
    },
    RM {
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
            List<Notion> listNotions = Arrays.asList(mapper.readValue(json, Notion[].class));

            List<Notion> newListNotions = listNotions.stream()
                    .filter(a -> !a.getLabel().equals(args.get(0)))
                    .toList();
            mapper.writerWithDefaultPrettyPrinter().writeValue(json, newListNotions);
        }

        /**
         * Rm take only one argument: the label to be deleted.
         *
         * @param args List of args (number of args already correct)
         * @return boolean if args is correct
         */
        @Override
        boolean checkArgs(List<String> args) {
            return args.size() == 1;
        }
    };

    /**
     * Abstract method to do actions according to the Operation type.
     *
     * @param args List of args (number of args already correct)
     */
    abstract void action(List<String> args) throws IOException, ParseException;

    /**
     * Abstract method to check the number of arguments.
     *
     * @param args List of args (number of args already correct)
     */
    abstract boolean checkArgs(List<String> args);
}

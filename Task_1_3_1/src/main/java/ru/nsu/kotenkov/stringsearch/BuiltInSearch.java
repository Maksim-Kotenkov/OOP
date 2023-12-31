package ru.nsu.kotenkov.stringsearch;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A class for finding all indices of substring in a string.
 * This class uses built-in lastIndexOf method of java Strings +
 * can read huge files because of batch-reading implementation.
 * First 1mil results will be returned, other - stored in the out.txt file.
 */
public class BuiltInSearch {
    /**
     * target - the substring we try to search for.
     * filename - file name that contains the whole string in that we'll search.
     * batchSize - 20 by default, but if it's smaller than the target, we make it larger.
     */
    private final String filename;
    private int batchSize = 1000;
    private final int listLimit = 1000000;

    /**
     * A constructor to initialize filename and target string.
     *
     * @param filename name of the input file
     */
    public BuiltInSearch(String filename) {
        this.filename = filename;
    }

    /**
     * A method that finds all occurrences of a substring. It maintains reading batch-by-batch,
     * concatenating tail of previous batch to the current batch
     * (not to miss a substring that has a part in previous),
     * and it stores all indices we found with shifts we've got from batch-reading.
     * First 1mil indices are stored in the List we return,
     * others (if there are so many indices) are stored in out.txt
     *
     * @param target the substring we are trying to find
     * @return List of Integers
     */
    public List<Integer> find(String target) {
        if (this.batchSize < target.length()) {
            this.batchSize = target.length();
        }

        List<Integer> result = new ArrayList<>();
        int found;
        int prevConcatCache = 0;
        String prevStr = "";

        try (FileInputStream stream = new FileInputStream(filename)) {
            PrintWriter writer = null;
            File file = new File("out.txt");
            while (stream.available() > 0) {

                if (result.size() >= this.listLimit && writer == null) {
                    writer = new PrintWriter(file, StandardCharsets.UTF_8);
                }

                String content = "";
                int gotFromPrev = 0;

                // get a part from previous batch to check if a part of a target is in there
                int part = prevStr.length() - target.length() + 1;
                if (part > 0) {
                    content = content.concat(prevStr.substring(part));
                    gotFromPrev = target.length() - 1;
                }

                // reading a batch of text
                byte[] buffer = new byte[this.batchSize];
                StringBuilder sb = new StringBuilder();
                stream.read(buffer);
                sb.append(new String(buffer));
                content = content.concat(sb.toString());

                if (!content.isEmpty()) {
                    found = content.lastIndexOf(target);
                    if (result.size() >= this.listLimit) {
                        while (found != -1) {
                            writer.print(prevConcatCache + found - gotFromPrev);
                            writer.print(" ");
                            found = content.lastIndexOf(target, found - 1);
                        }
                    } else {
                        while (found != -1) {
                            result.add(prevConcatCache + found - gotFromPrev);
                            found = content.lastIndexOf(target, found - 1);
                        }
                    }
                }

                prevConcatCache += this.batchSize;
                prevStr = content;
            }

            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collections.sort(result);
        return result;
    }
}

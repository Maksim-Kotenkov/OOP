package ru.nsu.kotenkov.stringsearch;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A class for finding all indices of substring in a string.
 * This class uses built-in lastIndexOf method of java Strings +
 * can read huge files because of batch-reading implementation.
 */
public class BuiltInSearch {
    /**
     * target - the substring we try to search for.
     * filename - file name that contains the whole string in that we'll search.
     * batchSize - 20 by default, but if it's smaller than the target, we make it larger.
     */
    private final String target;
    private final String filename;
    private int batchSize = 1000;

    /**
     * A constructor to initialize filename and target string.
     *
     * @param filename name of the input file
     * @param target substring that we want to search for
     */
    public BuiltInSearch(String filename, String target) {
        this.target = target;
        this.filename = filename;
        if (this.batchSize < target.length()) {
            this.batchSize = target.length();
        }
    }

    /**
     * A method that finds all occurrences of a substring. It maintains reading batch-by-batch,
     * concatenating tail of previous batch to the current batch
     * (not to miss a substring that has a part in previous),
     * and it stores all indices we found with shifts we've got from batch-reading.
     *
     * @return List of Integers
     */
    public List<Integer> find() {
        List<Integer> result = new ArrayList<>();
        int found;
        int prevConcatCache = 0;
        String prevStr = "";

        try (FileInputStream stream = new FileInputStream(filename)) {

            System.out.println("Find run");

            System.out.println(stream.available() + " symbols in the file");
            while (stream.available() > 0) {
                String content = "";
                int gotFromPrev = 0;

                // get a part from previous batch to check if a part of a target is in there
                if (prevStr.length() - this.target.length() + 1 > 0) {
                    content = content.concat(prevStr.substring(
                            prevStr.length() - this.target.length() + 1));
                    gotFromPrev = this.target.length() - 1;
                }

                // reading a batch of text
                byte[] buffer = new byte[this.batchSize];
                StringBuilder sb = new StringBuilder();
                stream.read(buffer);
                sb.append(new String(buffer));
                content = content.concat(sb.toString());
                // System.out.println("Batch red: " + content);

                if (!content.isEmpty()) {
                    found = content.lastIndexOf(this.target);
                    while (found != -1) {
                        // System.out.println("Found " + (prevConcatCache + found - gotFromPrev));
                        result.add(prevConcatCache + found - gotFromPrev);
                        found = content.lastIndexOf(this.target, found - 1);
                    }
                }

                prevConcatCache += this.batchSize;
                prevStr = content;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collections.sort(result);
        return result;
    }
}

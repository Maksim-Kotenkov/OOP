package ru.nsu.kotenkov.stringsearch;


import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BuiltInSearch {
    private final String target;
    private final String path;
    private int batchSize = 20;

    public BuiltInSearch(String filename, String target) {
        this.target = target;
        this.path = filename;
        if (this.batchSize < target.length()) {
            this.batchSize = target.length();
        }
    }

    public List<Integer> find() {
        List<Integer> result = new ArrayList<>();
        int found;
        int concatenated = 0;
        int prevConcatCache = 0;
        String prevStr = "";

        try (InputStream stream = this.getClass().getResourceAsStream("/" + path)) {
            assert stream != null : "Stream init failure";

            System.out.println("Find run");

            System.out.println(stream.available() + " symbols in the file");
            while (stream.available() > 0) {
                String content = "";
                int gotFromPrev = 0;

                // get a part from previous batch to check if a part of a target is in there
                if (prevStr.length() - this.target.length() + 1 > 0) {
                    content = content.concat(prevStr.substring(prevStr.length() - this.target.length() + 1));
                    gotFromPrev = this.target.length() - 1;
                }

                // reading a batch of text
                byte[] buffer = new byte[1];
                StringBuilder sb = new StringBuilder();
                while (stream.available() != 0 && concatenated < batchSize) {
                    stream.read(buffer);
                    sb.append(new String(buffer));
                    buffer = new byte[1];
                    concatenated++;
                }
                content = content.concat(sb.toString());
//                System.out.println("Batch red: " + content);

                if (!content.isEmpty()) {
                    found = content.lastIndexOf(this.target);
                    while (found != -1) {
//                        System.out.println("Found " + (prevConcatCache + found - gotFromPrev));
                        result.add(prevConcatCache + found - gotFromPrev);
                        found = content.lastIndexOf(this.target, found - 1);
                    }
                }

                prevConcatCache += concatenated;
                concatenated = 0;
                prevStr = content;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Collections.sort(result);
        return result;
    }
}

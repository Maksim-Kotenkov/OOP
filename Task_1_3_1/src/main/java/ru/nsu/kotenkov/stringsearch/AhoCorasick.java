package ru.nsu.kotenkov.stringsearch;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AhoCorasick {

    private List<Entry> entryList;
    private int alphabetSize;
    private List<Vertex> vertexList;
    private int vertexCount;

    public AhoCorasick(List<Entry> entryList, int alphabetSize) {
        this.entryList = entryList;
        this.alphabetSize = alphabetSize;
    }

    // Строка набора
    public static class Entry {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    // Вершина бора (состояние конечного детерминированного автомата)
    public static class Vertex {
        private int next[];         // массив сыновей
        private boolean isLeaf;     // терминальная вершина
        private int parent;         // родительская вершина
        private int charIndexFromParent;// символ перехода от родителя
        private int suffixLink;     // суффиксная ссылка
        private int go[];           // массив переходов, используемый для вычисления суффиксных ссылок

        public Vertex(int alphabetSize) {
            this.next = newIntArray(alphabetSize);
            this.go = newIntArray(alphabetSize);
            this.parent = -1;
            this.suffixLink = -1;
        }

        private int[] newIntArray(int size) {
            int[] ints = new int[size];
            Arrays.fill(ints, -1);
            return ints;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "next=" + Arrays.toString(next) +
                    ", go=" + Arrays.toString(go) +
                    ", isLeaf=" + isLeaf +
                    '}';
        }
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    // добавить строку в набор
    public boolean addEntry(Entry entry) {
        return entryList.add(entry);
    }

    // поиск всех строк из заданного набора в тексте
    public List<Integer> solve(String text) {
        List<Integer> result = new ArrayList<>();
        vertexList = new ArrayList<>();
        vertexList.add(new Vertex(alphabetSize));
        vertexList.add(new Vertex(alphabetSize));
        vertexCount = 1;

        for (Entry entry : entryList) {
            addToTrie(entry);
        }

        int v = 0;
        for (char ch : text.toCharArray()) {
            v = go(v, charToIndex(ch));
            Vertex vertex = vertexList.get(v);
            if(vertex.isLeaf) { // посещение терминальной вершине - это нахождение строки из заданного набора
                result.add(v);
            }
        }

        return result;
    }

    // добавить в бор
    private void addToTrie(Entry entry) {
        int v = 0;
        for (char ch : entry.value.toCharArray()) {
            int sym = charToIndex(ch);
            if(vertexList.get(v).next[sym] == -1) {
                Vertex vertex = vertexList.get(vertexCount);
                vertex.suffixLink = -1;
                vertex.parent = v;
                vertex.charIndexFromParent = sym;

                vertexList.add(new Vertex(alphabetSize));
                vertexList.get(v).next[sym] = vertexCount++;
            }
            v = vertexList.get(v).next[sym];
        }
        vertexList.get(v).isLeaf = true;
    }

    private int charToIndex(char ch) {
        return ch - 'a';
    }

    // обычный переход
    private int go(int v, int sym) {
        Vertex vertex = vertexList.get(v);
        if(vertex.go[sym] == -1) {
            if(vertex.next[sym] != -1) {
                vertex.go[sym] = vertex.next[sym];
            } else {
                vertex.go[sym] = v == 0 ? 0 : go(goBySuffixLink(v), sym);
            }
        }
        return vertex.go[sym];
    }

    // переход по суффиксной ссылке (к вершине, в которой оканчивается наидлиннейший собственный суффикс строки)
    private int goBySuffixLink(int v) {
        Vertex vertex = vertexList.get(v);
        if(vertex.suffixLink == -1) {
            if(v == 0 || vertex.parent == 0) {
                vertex.suffixLink = 0;
            } else {
                vertex.suffixLink = go(goBySuffixLink(vertex.parent), vertex.charIndexFromParent);
            }
        }
        return vertex.suffixLink;
    }
}

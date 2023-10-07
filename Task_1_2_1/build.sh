javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.kotenkov.tree

# jar cf Heapsort.jar Heapsort.class
javac src/main/java/ru/nsu/kotenkov/tree/Tree.java -d ./build

java -cp ./build ru.nsu.kotenkov.tree.Tree

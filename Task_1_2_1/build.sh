javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.kotenkov.tree

javac src/main/java/ru/nsu/kotenkov/tree/*.java -d ./build

cd ./build
jar cfm Tree.jar ../src/main/java/ru/nsu/kotenkov/tree/ManifestAddition.mf ru/nsu/kotenkov/tree
#jar tf Tree.jar

java -jar Tree.jar

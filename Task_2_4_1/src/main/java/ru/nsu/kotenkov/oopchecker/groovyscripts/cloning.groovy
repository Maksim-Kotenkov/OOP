package ru.nsu.kotenkov.oopchecker.groovyscripts

def config = new GroovyClassLoader().parseClass("./src/main/java/ru/nsu/kotenkov/oopchecker/groovyscripts/config.groovy" as File)

def cloneRepos(LinkedHashMap groups) {
    println 'Groups list' + groups.keySet()
    for (groupName in groups.keySet()) {
        println 'Cloning ' + groupName
        for (person in groups.get(groupName)) {
            String cloneDirectoryPath = "repos/" + groupName + '/' + person.getKey();
            println 'Cloning ' + person.getValue().repo + ' into ' + "repos/" + groupName + '/' + person.getKey();
            def cloningProc = ('git clone ' + person.getValue().repo + ' ' + cloneDirectoryPath).execute()
            def b = new StringBuffer()
            cloningProc.consumeProcessErrorStream(b)

            println b.toString()
        }
    }
}

cloneRepos(config.groups)

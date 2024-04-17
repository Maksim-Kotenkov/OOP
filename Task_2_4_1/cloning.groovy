
def config = new GroovyClassLoader().parseClass("./config.groovy" as File)

def cloneRepos(LinkedHashMap groups) {
    println 'Groups list' + groups.keySet()
    for (Integer groupName : groups.keySet()) {
        println 'Cloning ' + groupName
        for (Map.Entry person : groups.get(groupName)) {
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

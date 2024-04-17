package ru.nsu.kotenkov.oopchecker

import org.gradle.tooling.BuildLauncher


//@GrabResolver('http://repo.gradle.org/gradle/libs/')
//@Grab('org.gradle:gradle-tooling-api:7.3')
//@Grab('org.apache.ivy:ivy:2.4.0')
import org.gradle.tooling.model.GradleProject
import org.gradle.tooling.GradleConnector
//import org.apache.ivy.core.module.descriptor.ModuleDescriptor  // screaming for help but is working
//import org.apache.ivy.util.MessageLogger  // not working


def config = new GroovyClassLoader().parseClass("./src/main/java/ru/nsu/kotenkov/oopchecker/config.groovy" as File)


def evaluate(Set groups, String lab) {
    for (groupDirectory in groups) {
        def connector = GradleConnector.newConnector()
        File groupPath = new File("./repos/" + groupDirectory);
        String[] studentsSubDirectories = groupPath.list(new FilenameFilter() {
            @Override
            boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        })

        println '----------'
        println 'Students in group ' + groupDirectory + ':'
        println studentsSubDirectories
        println 'Testing:'

        for (student in studentsSubDirectories) {
            def fullLabPath = './repos/' + groupDirectory + '/' + student + '/' + lab
            connector.forProjectDirectory(new File(fullLabPath))

            def connection = connector.connect()
            println 'testing ' + fullLabPath

            BuildLauncher build = connection.newBuild()

            if (build == null) {
                System.err.println("Building of " + fullLabPath + " failed")
                continue
            }

            build.forTasks("test")

            try {
                build.run()
            } catch (Exception e) {
                System.err.println("Execution of " + fullLabPath + " resulted in exception " as char, e)
                println 'Error'
            } finally {
                connection.close()
                println 'No errors'
            }
        }
    }

}


for (lab in config.tasks) {
    evaluate(config.groups.keySet(), lab)
}

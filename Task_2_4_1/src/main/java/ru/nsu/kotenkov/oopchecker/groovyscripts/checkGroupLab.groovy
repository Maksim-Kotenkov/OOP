package ru.nsu.kotenkov.oopchecker.groovyscripts

import org.gradle.internal.logging.events.ProgressEvent
import org.gradle.internal.logging.progress.ProgressListener
import org.gradle.tooling.BuildLauncher


//@GrabResolver('http://repo.gradle.org/gradle/libs/')
//@Grab('org.gradle:gradle-tooling-api:7.3')
//@Grab('org.apache.ivy:ivy:2.4.0')
import org.gradle.tooling.model.GradleProject
import org.gradle.tooling.GradleConnector

import java.util.stream.Stream

//import org.apache.ivy.core.module.descriptor.ModuleDescriptor  // screaming for help but is working
//import org.apache.ivy.util.MessageLogger  // not working


def config = new GroovyClassLoader().parseClass("./src/main/java/ru/nsu/kotenkov/oopchecker/groovyscripts/config.groovy" as File)


def evaluate(Set groups, String lab) {
    println '--------------------'
    println lab + ':'

    def results = new HashMap()
    for (groupDirectory in groups) {
        def groupResults = new HashMap()
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
        if (studentsSubDirectories == null) {
            println 'No repositories for this group'
            continue
        }
        println studentsSubDirectories
        println 'Testing:'

        for (student in studentsSubDirectories) {
            println student
            def studentResults = [
                    build: '-',
                    javadoc: '-',
                    test: '-'
            ]

            def fullLabPath = './repos/' + groupDirectory + '/' + student + '/' + lab
            connector.forProjectDirectory(new File(fullLabPath))

            def connection = connector.connect()

            BuildLauncher build = connection.newBuild()

            try {
                build.forTasks('build')
                        .addArguments('-x', 'test')
                        .run()
            } catch (Exception e) {
                println "Building " + fullLabPath + " failed " + e
                connection.close()
                if (groupDirectory in groupResults.keySet()) {
                    groupResults[student] add studentResults
                } else {
                    groupResults[student] = studentResults
                }
                continue
            }

            studentResults['build'] = '+'

            build = connection.newBuild()
            try {
                // stringbuilder here
                PrintStream output = new PrintStream(//string builder to here)

                build.forTasks('test')
                        .setStandardOutput(output)
//                        .addProgressListener(new org.gradle.tooling.ProgressListener() {
//                            @Override
//                            void statusChanged(org.gradle.tooling.ProgressEvent progressEvent) {
//                                println progressEvent.description
//                            }
//                        })
                        .run()
                System.out.println(output.toString())
            } catch (Exception e) {
                println "Execution of " + fullLabPath + " resulted in exception " + e
                println 'Error'
                connection.close()
                if (groupDirectory in groupResults.keySet()) {
                    groupResults[student] add studentResults
                } else {
                    groupResults[student] = studentResults
                }
                continue
            }

            studentResults['test'] = '+'
            connection.close()
            println studentResults
            if (groupDirectory in groupResults.keySet()) {
                groupResults[student] add studentResults
            } else {
                groupResults[student] = studentResults
            }

        }

        results[groupDirectory] = groupResults
        println groupResults
    }

//    println '----------'
//    println '----------'
//    println 'RESULTS:'
//    println results
    return results
}

def shell = new GroovyShell();
source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
        "oopchecker/groovyscripts/cloning.groovy"));
shell.run(source, Collections.singletonList(""));

def allLabResults = new HashMap()
for (lab in config.tasks) {
    def labResults = evaluate(config.groups.keySet(), lab)
    allLabResults[lab] = labResults
}

println '----------'
println '----------'
println allLabResults

return allLabResults

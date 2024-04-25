package ru.nsu.kotenkov.oopchecker.groovyscripts

import org.gradle.internal.logging.events.ProgressEvent
import org.gradle.internal.logging.progress.ProgressListener
import org.gradle.tooling.BuildLauncher


//@GrabResolver('http://repo.gradle.org/gradle/libs/')
//@Grab('org.gradle:gradle-tooling-api:7.3')
//@Grab('org.apache.ivy:ivy:2.4.0')
import org.gradle.tooling.model.GradleProject
import org.gradle.tooling.GradleConnector
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

import java.lang.annotation.ElementType
import java.util.stream.Stream

import org.jsoup.Jsoup;

//import org.apache.ivy.core.module.descriptor.ModuleDescriptor  // screaming for help but is working
//import org.apache.ivy.util.MessageLogger  // not working


def config = new GroovyClassLoader().parseClass("./src/main/java/ru/nsu/kotenkov/oopchecker/groovyscripts/config.groovy" as File).newInstance()


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
            studentResults['path'] = fullLabPath

            connector.forProjectDirectory(new File(fullLabPath))

            def connection = connector.connect()

            BuildLauncher build = connection.newBuild()

            // BUILD
            try {
                build.forTasks('build')
                        .addArguments('-x', 'test')
                        .run()
                studentResults['build'] = '+'
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

            build = connection.newBuild()
            // DOCS
            try {
                build.forTasks('javadoc')
                        .run()
                studentResults['javadoc'] = '+'
            } catch (Exception e) {
                println "Javadoc for " + fullLabPath + " failed " + e
            }

            build = connection.newBuild()
            // TESTS
            try {
                // stringbuilder here
                ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
                PrintStream output = new PrintStream(outBytes)

                build.forTasks('test')
                        .setStandardOutput(output)
                        .addArguments('-i')
                        .run()
                link = fullLabPath + '/build/reports/tests/test/index.html'
                File testSummary = new File(link)
                Document document = Jsoup.parse(testSummary)
//                String value = Jsoup.parse(new File(link), "UTF-8").select("div[name=something]").first().val();
                String value = document.getElementById("successRate").select("div.percent").first().text()
                System.out.println("TESTS COMPLETED " + value)

                studentResults['test'] = value
                studentResults['summaryHTML'] = document.getElementById("content").outerHtml()
                connection.close()
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


//            println studentResults
            if (groupDirectory in groupResults.keySet()) {
                groupResults[student] add studentResults
            } else {
                groupResults[student] = studentResults
            }

        }

        results[groupDirectory] = groupResults
//        println groupResults
    }

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
//println allLabResults

return allLabResults

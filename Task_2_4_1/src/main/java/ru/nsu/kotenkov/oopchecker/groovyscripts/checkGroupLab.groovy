package ru.nsu.kotenkov.oopchecker.groovyscripts


import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.GradleConnector
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


def config = new GroovyClassLoader().parseClass("./src/main/java/ru/nsu/kotenkov/oopchecker/groovyscripts/config.groovy" as File).newInstance()


def evaluate(Set groups, String lab) {
    println '--------------------'
    println lab + ':'

    def results = new HashMap()
    for (groupDirectory in groups) {
        def groupResults = new HashMap()
        def connector = GradleConnector.newConnector()

        File groupPath = new File("./repos/" + groupDirectory)
        String[] studentsSubDirectories = groupPath.list(new FilenameFilter() {
            @Override
            boolean accept(File current, String name) {
                return new File(current, name).isDirectory()
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

            // DOCS
            build = connection.newBuild()
            try {
                build.forTasks('javadoc')
                        .run()
                studentResults['javadoc'] = '+'

                link = fullLabPath + '/build/docs/javadoc/allpackages-index.html'
                File testSummary = new File(link)
                Document document = Jsoup.parse(testSummary)

//                studentResults['javadocHTML'] = document.select("div.summary-table two-column-summary").outerHtml()
                studentResults['javadocHTML'] = document.select("div.col-first").outerHtml()  // for index-all
                println 'javadocs stolen'
                println studentResults['javadocHTML']

            } catch (Exception e) {
                println "Javadoc for " + fullLabPath + " failed " + e
            }

            // TESTS
            build = connection.newBuild()
            try {

                build.forTasks('test')
                        .addArguments('-i')
                        .run()
                link = fullLabPath + '/build/reports/tests/test/index.html'
                File testSummary = new File(link)
                Document document = Jsoup.parse(testSummary)

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

            if (groupDirectory in groupResults.keySet()) {
                groupResults[student] add studentResults
            } else {
                groupResults[student] = studentResults
            }

        }

        results[groupDirectory] = groupResults
    }

    return results
}

def shell = new GroovyShell()
source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
        "oopchecker/groovyscripts/cloning.groovy"))
shell.run(source, Collections.singletonList(""))

def allLabResults = new HashMap()
for (lab in config.tasks) {
    def labResults = evaluate(config.groups.keySet(), lab)
    allLabResults[lab] = labResults
}

println '----------'
println '----------'
//println allLabResults

return allLabResults

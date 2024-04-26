package ru.nsu.kotenkov.oopchecker.graphics

import org.jsoup.Jsoup
import org.apache.commons.io.FileUtils

// get results
GroovyShell shell = new GroovyShell()
source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
        "oopchecker/groovyscripts/checkGroupLab.groovy"))
HashMap results = (HashMap) shell.run(source, Collections.singletonList(""))

// get template
File htmlTemplateFile = new File("./src/main/java/ru/nsu/kotenkov/oopchecker/html/template.html")
String htmlString = Jsoup.parse(htmlTemplateFile).toString()


ArrayList<String> sorted = new ArrayList<String> (results.keySet())
Collections.sort(sorted)

tabsCounter = 0
for (taskEntry in sorted) {
    htmlString += '<h1>\n'
    htmlString += taskEntry
    htmlString += '</h1>\n'
    htmlString += '<div id="content">'

    for (groupEntry in results[taskEntry].keySet()) {
        htmlString += '<h1>\n'
        htmlString += groupEntry
        htmlString += '</h1>\n'
        for (person in results[taskEntry][groupEntry].keySet()) {
            htmlString += '<div id="content">\n' + '<h1>' + person + '</h1>'

            if (results[taskEntry][groupEntry][person]['build'] == '+') {
                // BUILD
                htmlString += '<h2>Build SUCCESSFUL ✅</h2>'

                // TEST
                String personRes = results[taskEntry][groupEntry][person]['summaryHTML'].toString()

                // replacements
                personRes = personRes.replace('tabs', 'tabs' + person + taskEntry)  // unique id for elements
                personRes = personRes.replace('tab0', 'tab' + tabsCounter)
                tabsCounter += 1
                personRes = personRes.replace('tab1', 'tab' + tabsCounter)
                tabsCounter += 1

                testsReportPath = results[taskEntry][groupEntry][person]['path'] + '/build/reports/tests/test/'
                personRes = personRes.replace('classes', testsReportPath + 'classes')
                personRes = personRes.replace('packages', testsReportPath + 'packages')
                htmlString += personRes

            } else {
                htmlString += '<h2>Build FAILED ❌</h2>'
                htmlString += '<h2>Cannot run tests ❌</h2>'
            }

            // JAVADOC
            if (results[taskEntry][groupEntry][person].get('javadoc') == '+') {
                htmlString += '<h2>Javadocs ✅\n'

                String personDocs = results[taskEntry][groupEntry][person]['javadocHTML'].toString()
                docsPath = results[taskEntry][groupEntry][person]['path'] + '/build/docs/javadoc/'
                personDocs = personDocs.replace('href="', 'href="' + docsPath)
                htmlString += personDocs

                htmlString += '</h2>'
            } else {
                htmlString += '<h2>No Javadocs ❌</h2>'
            }

            htmlString += '</div>\n'
        }

    }
    htmlString += '</div>\n'
}
htmlString += '</body>\n' +
        '</html>'

File newHtmlFile = new File("./OopCheckerReport.html")
FileUtils.writeStringToFile(newHtmlFile, htmlString)

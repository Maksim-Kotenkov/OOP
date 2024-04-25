package ru.nsu.kotenkov.oopchecker.graphics

import org.jsoup.Jsoup
import org.apache.commons.io.FileUtils;

// get results
GroovyShell shell = new GroovyShell()
source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
        "oopchecker/groovyscripts/checkGroupLab.groovy"))
HashMap results = (HashMap) shell.run(source, Collections.singletonList(""))

// get template
File htmlTemplateFile = new File("./src/main/java/ru/nsu/kotenkov/oopchecker/html/template.html");
String htmlString = Jsoup.parse(htmlTemplateFile).toString()

String body = 'lol'


ArrayList<String> sorted = new ArrayList<String> (results.keySet());
Collections.sort(sorted);

for (taskEntry in sorted) {
    println taskEntry
//    File personTemplate = new File("./src/main/java/ru/nsu/kotenkov/oopchecker/html/personTestResults.html");
//    String personResults = Jsoup.parse(personTemplate).toString()
//    htmlString += personResults
    htmlString += '<h1>\n'
    htmlString += taskEntry
    htmlString += '</h1>\n'
    htmlString += '<div id="content">'

    for (groupEntry in results[taskEntry].keySet()) {
        println groupEntry
        htmlString += '<h1>\n'
        htmlString += groupEntry
        htmlString += '</h1>\n'
        for (person in results[taskEntry][groupEntry].keySet()) {
            htmlString += '<div id="content">\n' + '<h1>' + person + '</h1>'

            // Build
            if (results[taskEntry][groupEntry][person].get('build') == '+') {
                htmlString += '<h2>Build SUCCESSFUL ✅</h2>'

                // Test summary
                String personRes = results[taskEntry][groupEntry][person].get('summaryHTML').toString()

                // replacements
                personRes = personRes.replace('tabs', 'tabs' + person + taskEntry)
//                personRes = personRes.replace('tab0', 'tab0' + person + taskEntry)  // unique id for elements
//                personRes = personRes.replace('tab1', 'tab1' + person + taskEntry)
                testsReportPath = results[taskEntry][groupEntry][person].get('path') + '/build/reports/tests/test/'
                personRes = personRes.replace('classes', testsReportPath + 'classes')
                personRes = personRes.replace('packages', testsReportPath + 'packages')
//                htmlString += '<h2>Test Summary</h2>'
                htmlString += personRes
//            htmlString = htmlString.replace('Test Summary', person)
            } else {
                htmlString += '<h2>Build FAILED ❌</h2>'
            }

            htmlString += '</div>\n'
        }

    }
    htmlString += '</div>\n'
}
htmlString += '</body>\n' +
        '</html>'


//htmlString = htmlString.replace('$coverage', results.get('Task_1_1_1').get(22213).get('Maksim-Kotenkov').get('summaryHTML'));
//htmlString = htmlString.replace('$body', body);

File newHtmlFile = new File("./new.html");
FileUtils.writeStringToFile(newHtmlFile, htmlString);

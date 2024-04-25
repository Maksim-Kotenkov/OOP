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

htmlString += '<div id="content">'

ArrayList<String> sorted = new ArrayList<String> (results.keySet());
Collections.sort(sorted);

for (taskEntry in sorted) {
    println taskEntry
//    File personTemplate = new File("./src/main/java/ru/nsu/kotenkov/oopchecker/html/personTestResults.html");
//    String personResults = Jsoup.parse(personTemplate).toString()
//    htmlString += personResults
    htmlString += '<h1>'
    htmlString += taskEntry
    for (groupEntry in results[taskEntry].keySet()) {
        println groupEntry
    }
    String personRes = results.get('Task_1_1_1').get(22213).get('Maksim-Kotenkov').get('summaryHTML').toString()
    htmlString += personRes
    htmlString = htmlString.replace('Test Summary', 'Maksim-Kotenkov')
    htmlString += '</h1>'
}
htmlString += '</div>\n' +
        '</body>\n' +
        '</html>'


//htmlString = htmlString.replace('$coverage', results.get('Task_1_1_1').get(22213).get('Maksim-Kotenkov').get('summaryHTML'));
//htmlString = htmlString.replace('$body', body);

File newHtmlFile = new File("./new.html");
FileUtils.writeStringToFile(newHtmlFile, htmlString);

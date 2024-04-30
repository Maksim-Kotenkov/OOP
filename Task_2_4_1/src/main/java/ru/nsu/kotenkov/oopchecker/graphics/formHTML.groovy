package ru.nsu.kotenkov.oopchecker.graphics

//import java.net.HttpUrlConnection
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

            // ACTIVITY
            githubUrl = 'https://api.github.com/repos/' + person + '/' + 'OOP' + '/activity' + '?ref=' + taskEntry + '&activity_type=push'
            URL url = new URL(githubUrl);
            githubApi = (HttpURLConnection) url.openConnection();
            githubApi.setRequestMethod("GET")
            data = URLEncoder.encode("Accept", "UTF-8") + "=" + URLEncoder.encode("application/vnd.github+json", "UTF-8")
//            + URLEncoder.encode("Authorization: Bearer <YOUR-TOKEN>", "UTF-8")

//            DataOutputStream wr = new DataOutputStream (
//                    githubApi.getOutputStream())
//            wr.writeBytes(data)
//            wr.close();

            //Get Response
            StringBuilder response = new StringBuilder()
            activityCounter = 0
            try {
                InputStream is = githubApi.getInputStream()
                BufferedReader rd = new BufferedReader(new InputStreamReader(is))
                String line
                while ((line = rd.readLine()) != null) {
                    response.append(line)
                    response.append('\r')
                }
                rd.close()

                activityCounter = response.count('push')
            } catch (IOException e) {
                println "Response error: " + e
            }


            htmlString += '<h2>Activity\n'
            htmlString += activityCounter + ' commits'
            htmlString += '</h2>'

            htmlString += '</div>\n'
        }

    }
    htmlString += '</div>\n'
}
htmlString += '</body>\n' +
        '</html>'

File newHtmlFile = new File("./OopCheckerReport.html")
FileUtils.writeStringToFile(newHtmlFile, htmlString)

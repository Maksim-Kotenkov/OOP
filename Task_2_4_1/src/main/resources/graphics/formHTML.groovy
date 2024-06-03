package graphics


import org.jsoup.Jsoup
import org.apache.commons.io.FileUtils


// get .env token
def map = [:]
def envAsText = new File('.env').text

envAsText.eachLine {
    splt = it.split('=', 2)
    if (splt.size() == 2) {
        (key,value) = splt
        map[key] = value
    }
}
println map['GITHUB_TOKEN']

// get results
shell = new GroovyShell()
source = new GroovyCodeSource(this.class.getResource("/groovyscripts/checkGroupLab.groovy"))
results = shell.run(source, Collections.singletonList(""))

// get template
htmlTemplateFile = new File(this.class.getResource("/html/template.html").getFile())
htmlString = Jsoup.parse(htmlTemplateFile).toString()


ArrayList sorted = results.keySet()
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
                personRes = results[taskEntry][groupEntry][person]['summaryHTML'].toString()

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

                personDocs = results[taskEntry][groupEntry][person]['javadocHTML'].toString()
                docsPath = results[taskEntry][groupEntry][person]['path'] + '/build/docs/javadoc/'
                personDocs = personDocs.replace('href="', 'href="' + docsPath)
                htmlString += personDocs

                htmlString += '</h2>'
            } else {
                htmlString += '<h2>No Javadocs ❌</h2>'
            }

            // ACTIVITY
            githubUrl = 'https://api.github.com/repos/' + person + '/' + 'OOP' + '/activity' +
                    '?ref=' + taskEntry + '&activity_type=push'
            url = new URL(githubUrl);
            githubApi = (HttpURLConnection) url.openConnection();
            githubApi.setRequestProperty("Authorization","Bearer " + map['GITHUB_TOKEN']);
            githubApi.setRequestMethod("GET")
            data = URLEncoder.encode("Accept", "UTF-8") + "=" + URLEncoder.encode("application/vnd.github+json", "UTF-8")

            //Get Response
            response = new StringBuilder()
            activityCounter = 0
            try {
                is = githubApi.getInputStream()
                rd = new BufferedReader(new InputStreamReader(is))
                while ((line = rd.readLine()) != null) {
                    response.append(line)
                    response.append('\r')
                }
                rd.close()

                activityCounter = response.count('push')

                // if wrong ref=task format
                if (activityCounter == 0) {
                    // use format task-1-1-1 instead of Task_1_1_1
                    githubUrl = 'https://api.github.com/repos/' + person + '/' + 'OOP' + '/activity' +
                            '?ref=' + taskEntry.toLowerCase().replace('_', '-') + '&activity_type=push'
                    url = new URL(githubUrl);
                    githubApi = (HttpURLConnection) url.openConnection();
                    githubApi.setRequestProperty("Authorization","Bearer " + map['GITHUB_TOKEN']);
                    githubApi.setRequestMethod("GET")
                    data = URLEncoder.encode("Accept", "UTF-8") + "=" + URLEncoder.encode("application/vnd.github+json", "UTF-8")

                    //Get Response
                    response = new StringBuilder()

                    is = githubApi.getInputStream()
                    rd = new BufferedReader(new InputStreamReader(is))
                    while ((line = rd.readLine()) != null) {
                        response.append(line)
                        response.append('\r')
                    }
                    rd.close()

                    activityCounter = response.count('push')
                }
            } catch (Exception e) {
                println "Response error: " + e
            }

            htmlString += '<h2>Overall activity: '
            htmlString += activityCounter + ' commits'
            htmlString += '</h2>'

            htmlString += '</div>\n'
        }

    }
    htmlString += '</div>\n'
}
htmlString += '</body>\n' +
        '</html>'


newHtmlFile = new File("./OopCheckerReport.html")
FileUtils.writeStringToFile(newHtmlFile, htmlString)

source = new File(this.class.getResource("/html/css/").getFile())
dest = new File("./css/");
try {
    FileUtils.copyDirectory(source, dest);
} catch (IOException e) {
    e.printStackTrace();
}

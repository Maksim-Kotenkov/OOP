
@GrabResolver('http://repo.gradle.org/gradle/libs/')
@Grab('org.gradle:gradle-tooling-api:7.3')
@Grab('org.apache.ivy:ivy:2.4.0')
import org.gradle.tooling.model.GradleProject
import org.gradle.tooling.GradleConnector
import org.apache.ivy.core.module.descriptor.ModuleDescriptor  // screaming for help but is working
import org.apache.ivy.util.MessageLogger  // not working


def config = new GroovyClassLoader().parseClass("./config.groovy" as File)


def evaluate(Set directories, String lab) {
    println directories
    for (String directory : directories) {
        def connector = GradleConnector.newConnector()
        connector.forProjectDirectory(new File("./repos/" + directory + "/"  + lab))

        def connection = connector.connect();
        println 'connected'
    }

}

for (LinkedHashMap group : config.groups) {
    for (String lab : config.tasks) {
        evaluate(group.keySet(), lab)
    }
}

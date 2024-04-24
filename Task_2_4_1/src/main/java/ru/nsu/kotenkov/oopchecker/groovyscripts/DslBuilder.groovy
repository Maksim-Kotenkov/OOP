import groovy.transform.builder.Builder
import groovy.xml.Entity


@Builder
class DslBuilder {
    String field1
    String field2

    def asType( Class type ) {
        if (type == Entity) {
            return new Entity(field1, field2)
        }
        super.asType(type)
    }
}

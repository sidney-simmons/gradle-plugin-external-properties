# gradle-plugin-external-properties

gradle-plugin-external-properties is a custom gradle plugin for configuring external properties in your gradle build.  Allows for properties defined in source control and property overrides defined outside of source control.

Benefits over gradle's built-in functionality:
* Properties in multiple projects that have the same name/key can be overridden outside of source control independently from one another.
* Properties can be loaded from multiple files with different names and locations.
* Multi-project builds can take advantage of a hierarchy of property sources.

## Usage

``` gradle
task exampleProperties() {
    // Get a property - throws an exception if the property cannot be resolved
    println props.get("some.property")
    
    // Get a property - returns a default value if the property cannot be resolved
    println props.get("this.property.does.not.exist", "This is a default value!")
    
    // See if a property exists - returns a boolean
    println props.exists("another.property")
}
```

## Order and Hierarchy

By default this plugin will resolve properties from the following files. Each project, including sub projects, has its own collection of resolvers. These resolvers are traversed in the order below until the requested property is found. The order is essentially all of the resolvers in the project where the property is being requested, all of that project's parent project resolvers, that project's parent resolvers, and so on.

##### Single-project build
```
1) [USER HOME]/.gradle-plugin-external-properties/[PROJECT NAME]/build.properties
2) [PROJECT DIRECTORY]/build.properties
```

##### Multi-project build
```
// props.get("some.property") from the root project
1) [USER HOME]/.gradle-plugin-external-properties/[ROOT PROJECT NAME]/build.properties
2) [ROOT PROJECT DIRECTORY]/build.properties

// props.get("some.property") from the sub project
1) [USER HOME]/.gradle-plugin-external-properties/[ROOT PROJECT NAME]/[SUB PROJECT NAME]/build.properties
2) [SUB PROJECT DIRECTORY]/build.properties
3) [USER HOME]/.gradle-plugin-external-properties/[ROOT PROJECT NAME]/build.properties
4) [ROOT PROJECT DIRECTORY]/build.properties
```

## Configuration

You can define your own property resolvers for a project or sub project using the configuration below if the defaults aren't sufficient.  Similar to the default resolvers above resolvers toward the beginning of the list take precedence over resolvers toward the end of the list and resolvers in child projects take precedence over resolvers in parent projects.

Note that the project's default property resolvers will be ignored if you decide to supply your own via the configuration.

##### Supply your own "*.properties" file to the built in properties file resolver
``` gradle
externalProperties {
    propertiesFileResolver file("C:/Users/blahblahblah/icecream.properties")
    propertiesFileResolver file("C:/Users/blahblahblah/sandwich.properties")
}
```

##### Supply your own custom resolver that implements the `PropertyResolver` interface
``` gradle
import com.sidneysimmons.gradlepluginexternalproperties.resolver.PropertyResolver;
public class MyCustomResolver implements PropertyResolver {
    @Override
    public String resolve(String propertyName) {
        return "I'm a property!";
    }
}

externalProperties {
    resolver new MyCustomResolver()
}
```

## Logging

This plugin logs several pieces of troubleshooting information at the DEBUG level.  Enable logging through gradle with the "--debug" command (or any other similar approach) and look for logs coming from classes in the `com.sidneysimmons.gradlepluginexternalproperties` package.

## Compatibility

The latest release is unit tested against the following gradle versions (see [gradle-plugin-external-properties-tests](https://github.com/sidney-simmons/gradle-plugin-external-properties-tests)):

* 6.3
* 6.4
* 6.4.1
* 6.5
* 6.5.1
* 6.6
* 6.6.1
* 6.7
* 6.7.1
* 6.8

## License
[MIT](https://choosealicense.com/licenses/mit/)
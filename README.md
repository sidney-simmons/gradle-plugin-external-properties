# gradle-plugin-external-properties

gradle-plugin-external-properties is a custom gradle plugin for easily configuring external properties in your gradle build. The following external property files are loaded by default:

- `[USER HOME]/.overrides/[PROJECT NAME]/build.properties`
- `[PROJECT ROOT]/build.properties`

_Note that files toward the beginning of the list take precedence over files toward the end of the list._

## Gradle Tasks

```
External Properties tasks
-------------------------
showAllProperties - Show all of the configured external properties.
showAllResolvers - Show all of the configured external property resolvers.
```

## Usage

> build.gradle

```
tasks.register("printPropertyValue") {
    doLast {
        println "My property: ${props.getProperty('my.prop')}"
    }
}
```

> [USER HOME]/.overrides/[PROJECT NAME]/build.properties (loaded by default)

```
my.prop=Hello from user home!
```

> [PROJECT ROOT]/build.properties (loaded by default)

```
my.prop=Hello from project root!
```

#### Enable Via Gradle Plugin Portal

Use the published plugin by setting the following in your project's build file.  You can find the latest version [here](https://plugins.gradle.org/plugin/com.sidneysimmons.gradle-plugin-external-properties).

```
plugins {
    id 'com.sidneysimmons.gradle-plugin-external-properties' version '[LATEST VERSION]'
}
```

#### Enable Via Local Build
Clone the repository and execute `gradlew build`.  Then execute `gradlew publish`.  This will publish the plugin to a local maven repository directory `[PLUGIN PROJECT ROOT]/build/test-maven-repo`.  Now you can use the local plugin in your own project by setting the following in your project's build files.

> build.gradle

```
plugins {
    id 'com.sidneysimmons.gradle-plugin-external-properties' version '[PLUGIN VERSION]'
}
```

> settings.gradle

```
pluginManagement {
    repositories {
        maven {
            url '[PLUGIN PROJECT ROOT]/build/test-maven-repo'
        }
        gradlePluginPortal()
    }
}
```

## Configuration

```
externalProperties {
    // Rename the container name
    propertyContainerName = 'props'
    
    // Clear the default property files and provide your own
    propertyFiles = []
    propertyFiles.add(file('C:/Users/blahblahblah/icecream.properties'))
    propertyFiles.add(file('C:/Users/blahblahblah/sandwich.properties'))
}
```

| Property | Type | Description |
| --- | --- | --- |
| propertyContainerName | String | The name of the property container you'd use in the gradle build to get the properties. Optional. Defaults to 'props'. |
| propertyFiles | List&lt;File&gt; | The list of the configured external property files. Exposed so it can be manipulated if needed. Optional. |

## License
[MIT](https://choosealicense.com/licenses/mit/)
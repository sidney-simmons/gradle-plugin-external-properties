# gradle-plugin-external-properties

gradle-plugin-external-properties is a custom gradle plugin for easily configuring external properties in your gradle build.

## Usage

By default this plugin will load properties from the following files. Missing files will be ignored so don't feel like they must both exist.  Note that the default property files will be ignored if you decide to supply your own via the configuration (see below).  Also note that files toward the beginning of the list take precedence over files toward the end of the list.

**Default property files**

- `[USER HOME]/.overrides/[PROJECT NAME]/build.properties`
- `[PROJECT ROOT]/build.properties`

**Accessing the properties**

```
tasks.register("printPropertyValue") {
    println props.get('some.property')
    doLast {
        println "My property: ${props.get('other.prop')}"
    }
}
```

## Configuration

```
externalProperties {
    resolver file('C:/Users/blahblahblah/icecream.properties')
    resolver file('C:/Users/blahblahblah/sandwich.properties')
}
```

| Property | Type | Description |
| --- | --- | --- |
| resolver | File | An external property file to load. Optional. If not set the defaults above will be used. |

## Gradle Tasks

```
External Properties tasks
-------------------------
showAllProperties - Show all of the configured external properties.
showAllResolvers - Show all of the configured external property resolvers.
```

## Applying the Plugin

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

## License
[MIT](https://choosealicense.com/licenses/mit/)
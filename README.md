# gradle-plugin-external-properties

gradle-plugin-external-properties is a custom gradle plugin for configuring external properties in your gradle build.

## Usage

By default this plugin will load properties from the following files. Missing files will be ignored so don't feel like they must both exist.  Note that the default property files will be ignored if you decide to supply your own via the configuration (see below).  Also note that files toward the beginning of the list take precedence over files toward the end of the list and files in child projects take precedence over files in parent projects.

**Default property files**

- `[USER HOME]/.overrides/[PROJECT NAME]/build.properties`
- `[PROJECT ROOT]/build.properties`

**Accessing the properties**

``` gradle
tasks.register("printPropertyValue") {
    // Get a property - throws an exception if the property cannot be resolved
    println props.get('some.property')
    
    // Get a property - returns a default value if the property cannot be resolved
    println props.get('this.property.does.not.exist', 'This is a default value!')
    
    // See if a property exists - returns a boolean
    println props.exists('another.property')
}
```

## Configuration

``` gradle
externalProperties {
    resolver file('C:/Users/blahblahblah/icecream.properties')
    resolver file('C:/Users/blahblahblah/sandwich.properties')
}
```

| Property | Type | Description |
| --- | --- | --- |
| resolver | File | An external property file to load. Optional. If not set the defaults above will be used. |

## Applying the Plugin

#### Enable Via Gradle Plugin Portal

Use the published plugin by setting the following in your project's build file.  You can find the latest version [here](https://plugins.gradle.org/plugin/com.sidneysimmons.gradle-plugin-external-properties).

``` gradle
plugins {
    id 'com.sidneysimmons.gradle-plugin-external-properties' version '[LATEST VERSION]'
}
```

#### Enable Via Local Build
Clone the repository and execute `./gradlew build`.  Then execute `./gradlew publish`.  This will publish the plugin to a local maven repository directory `[PLUGIN PROJECT ROOT]/build/test-maven-repo`.  Now you can use the local plugin in your own project by setting the following in your project's build files.

> build.gradle

``` gradle
plugins {
    id 'com.sidneysimmons.gradle-plugin-external-properties' version '[PLUGIN VERSION]'
}
```

> settings.gradle

``` gradle
pluginManagement {
    repositories {
        maven {
            url '[PLUGIN PROJECT ROOT]/build/test-maven-repo'
        }
        gradlePluginPortal()
    }
}
```

## Publishing the Plugin

1. Increment the "version" within build.gradle.

2. Run the following to publish.

```
./gradlew -Dgradle.publish.key=${PUBLISH_KEY} -Dgradle.publish.secret=${PUBLISH_SECRET} publishPlugins
```

3. Add a new release to the GitHub releases.

## License
[MIT](https://choosealicense.com/licenses/mit/)
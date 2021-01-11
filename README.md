# gradle-plugin-external-properties

gradle-plugin-external-properties is a custom gradle plugin for configuring external properties in your gradle build.

## Usage

By default this plugin will resolve properties from the following files. Missing files will be ignored so it isn't required that they exist.  Resolvers toward the beginning of the list take precedence over resolvers toward the end of the list and resolvers in child projects take precedence over resolvers in parent projects.

**Default property resolvers**

- `[USER HOME]/.gradle-plugin-external-properties/[PROJECT NAME]/build.properties`
- `[USER HOME]/.overrides/[PROJECT NAME]/build.properties` <- DEPRECATED and will be removed!
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

**Multi-project example**

Let's say you have a gradle project called "beer" and it has a subproject called "ipa".  Here is the order the default property resolvers will be queried.

- `[USER HOME]/.gradle-plugin-external-properties/beer/ipa/build.properties`
- `[PROJECT ROOT]/ipa/build.properties`
- `[USER HOME]/.gradle-plugin-external-properties/beer/build.properties`
- `[PROJECT ROOT]/build.properties`

## Custom Configuration

You can define your own property resolvers using the configuration below if the defaults aren't sufficient for your project.  Similar to the default resolvers above resolvers toward the beginning of the list take precedence over resolvers toward the end of the list and resolvers in child projects take precedence over resolvers in parent projects.

Note that the default property resolvers will be ignored if you decide to supply your own via the configuration.

``` gradle
externalProperties {
    resolver file('C:/Users/blahblahblah/icecream.properties')
    resolver file('C:/Users/blahblahblah/sandwich.properties')
}
```

| Property | Type | Description |
| --- | --- | --- |
| resolver | File | An external property file to load. Optional. If none are set the defaults above will be used. |

## Applying the Plugin

Use the published plugin by setting the following in your project's build file.  You can find the latest version [here](https://plugins.gradle.org/plugin/com.sidneysimmons.gradle-plugin-external-properties).

``` gradle
plugins {
    id 'com.sidneysimmons.gradle-plugin-external-properties' version '[LATEST VERSION]'
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)
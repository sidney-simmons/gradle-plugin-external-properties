package com.sidneysimmons.gradlepluginexternalproperties;

import com.sidneysimmons.gradlepluginexternalproperties.exception.InvalidPropertyException;
import com.sidneysimmons.gradlepluginexternalproperties.exception.MissingPropertyException;
import com.sidneysimmons.gradlepluginexternalproperties.extension.ExternalPropertiesExtension;
import com.sidneysimmons.gradlepluginexternalproperties.resolver.FileResolver;
import com.sidneysimmons.gradlepluginexternalproperties.resolver.PropertyResolver;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.gradle.api.Project;

/**
 * A container for all the loaded external properties. This can be called from the gradle build script to resolve
 * properties.
 * 
 * @author Sidney Simmons
 */
public class ExternalPropertiesContainer {

    private Project project;
    private ExternalPropertiesExtension extension;
    private List<PropertyResolver> propertyResolvers = new ArrayList<>();

    /**
     * Constructor that initializes the property resolvers.
     * 
     * @param project the project
     * @param extension the extension
     */
    public ExternalPropertiesContainer(Project project, ExternalPropertiesExtension extension) {
        this.project = project;
        this.extension = extension;
        initializePropertyResolvers();
    }

    /**
     * Get the property with the given property name. Looks at each property resolver until one contains the property. A
     * runtime exception is thrown if the property cannot be resolved by any resolver.
     * 
     * @param propertyName the property name
     * @return the property
     */
    public String getProperty(String propertyName) {
        // Validate the property name
        propertyName = propertyName != null ? propertyName.trim() : null;
        if (propertyName == null || propertyName.isEmpty()) {
            throw new InvalidPropertyException(MessageFormat.format("External property name is invalid. propertyName = {0}", propertyName));
        }

        // Attempt to find the property
        String property = null;
        for (PropertyResolver propertyResolver : propertyResolvers) {
            property = propertyResolver.resolve(propertyName);
            if (property != null) {
                return property;
            }
        }

        // Missing property!
        throw new MissingPropertyException(MessageFormat.format("No external property found with name \"{0}\".", propertyName));
    }

    /**
     * Initialize the property resolvers using the property files defined in the plugin extension.
     */
    private void initializePropertyResolvers() {
        propertyResolvers.clear();
        for (File propertyFile : extension.getPropertyFiles().get()) {
            propertyResolvers.add(new FileResolver(project, propertyFile));
        }
    }

    public List<PropertyResolver> getPropertyResolvers() {
        return propertyResolvers;
    }

}

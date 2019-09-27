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
 * Container that allows the user access to the properties.
 * 
 * @author Sidney Simmons
 */
public class ExternalPropertiesContainer {

    public static final String NAME = "props";
    private Project project;
    private ExternalPropertiesExtension extension;
    private List<PropertyResolver> defaultResolvers;

    /**
     * Constructor.
     * 
     * @param project the project
     * @param extension the extension
     */
    public ExternalPropertiesContainer(Project project, ExternalPropertiesExtension extension) {
        this.project = project;
        this.extension = extension;
    }

    /**
     * Get the property value for the given property name. Throws a runtime exception if the property isn't found.
     * 
     * @param propertyName the property name
     * @return the property value
     */
    public String get(String propertyName) {
        // Validate the property name
        propertyName = validatePropertyName(propertyName);

        // Attempt to find the property
        String property = null;
        for (PropertyResolver resolver : getResolvers()) {
            property = resolver.resolve(propertyName);
            if (property != null) {
                return property;
            }
        }

        // Missing property!
        throw new MissingPropertyException(MessageFormat.format("External property not found. propertyName = {0}", propertyName));
    }

    /**
     * Get all the property resolvers. Uses defaults if the user doesn't specify any custom resolvers.
     * 
     * @return the list of property resolvers
     */
    public List<PropertyResolver> getResolvers() {
        List<PropertyResolver> resolvers = extension.getResolvers().get();
        if (!resolvers.isEmpty()) {
            return resolvers;
        } else {
            return getDefaultResolvers();
        }
    }

    /**
     * Get all the default property resolvers. The following files are defined as defaults:
     * 
     * <ol>
     * <li>{@code [USER HOME]/.overrides/[PROJECT NAME]/build.properties}</li>
     * <li>{@code [PROJECT ROOT]/build.properties}</li>
     * </ol>
     * 
     * @return the list of property resolvers
     */
    public List<PropertyResolver> getDefaultResolvers() {
        if (defaultResolvers == null) {
            defaultResolvers = new ArrayList<>();

            // 1) Build the user directory file resolver
            String userDirectoryResolverString = MessageFormat.format("{0}/.overrides/{1}/build.properties",
                    System.getProperty("user.home"), project.getName());
            PropertyResolver userDirectoryResolver = new FileResolver(project, new File(userDirectoryResolverString));
            defaultResolvers.add(userDirectoryResolver);

            // 2) Build the project directory file resolver
            String projectDirectoryResolverString = MessageFormat.format("{0}/build.properties", project.getProjectDir().getAbsolutePath());
            PropertyResolver projectDirectoryResolver = new FileResolver(project, new File(projectDirectoryResolverString));
            defaultResolvers.add(projectDirectoryResolver);
        }
        return defaultResolvers;
    }

    /**
     * Validate a property name. Throws a runtime exception if the name is invalid.
     * 
     * @param propertyName the property name
     * @return the property name
     */
    private String validatePropertyName(String propertyName) {
        propertyName = propertyName != null ? propertyName.trim() : null;
        if (propertyName == null || propertyName.isEmpty()) {
            throw new InvalidPropertyException(MessageFormat.format("External property name is invalid. propertyName = {0}", propertyName));
        }
        return propertyName;
    }

}

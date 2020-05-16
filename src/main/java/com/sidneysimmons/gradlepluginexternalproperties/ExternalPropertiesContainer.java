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
     * Get the property value for the given property name. Throws a runtime exception if the property
     * isn't found.
     * 
     * @param propertyName the property name
     * @return the property value
     */
    public String get(String propertyName) {
        // Find the property
        String propertyValue = findPropertyValue(propertyName);
        if (propertyValue != null) {
            return propertyValue;
        }

        // Missing property!
        throw new MissingPropertyException(
                MessageFormat.format("External property not found. propertyName = {0}", propertyName));
    }

    /**
     * Get the property value for the given property name. Returns the given default value if the
     * property isn't found.
     * 
     * @param propertyName the property name
     * @param defaultValue the default value
     * @return the property value
     */
    public String get(String propertyName, String defaultValue) {
        // Find the property
        String propertyValue = findPropertyValue(propertyName);
        if (propertyValue != null) {
            return propertyValue;
        }

        // Missing property!
        return defaultValue;
    }

    /**
     * Determine if a property value exists for the given property name.
     * 
     * @param propertyName the property name
     * @return true if the property exists, false otherwise
     */
    public Boolean exists(String propertyName) {
        // Find the property
        String propertyValue = findPropertyValue(propertyName);
        if (propertyValue != null) {
            return Boolean.TRUE;
        }

        // Missing property!
        return Boolean.FALSE;
    }

    /**
     * Find the property value for the given property name. First looks in this project's resolvers.
     * Moves to the parent project's resolvers if not found. Continues up through the root project.
     * 
     * @param propertyName the property name
     * @return the property value if found, null otherwise
     */
    private String findPropertyValue(String propertyName) {
        // Validate the property name
        propertyName = validatePropertyName(propertyName);

        // Attempt to find the property using this project's resolvers, the parent's resolvers, that
        // parent's resolvers, etc
        Project tempProject = project;
        while (tempProject != null) {
            ExternalPropertiesContainer tempContainer = (ExternalPropertiesContainer) tempProject.getExtensions()
                    .findByName(NAME);
            if (tempContainer != null) {
                String property = null;
                for (PropertyResolver resolver : tempContainer.getResolvers()) {
                    property = resolver.resolve(propertyName);
                    if (property != null) {
                        return property;
                    }
                }
            }

            // Move on to the parent project (should return null when we've already reached the root project)
            tempProject = project.getParent();
        }

        // Cannot find the property
        return null;
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
            throw new InvalidPropertyException(
                    MessageFormat.format("External property name is invalid. propertyName = {0}", propertyName));
        }
        return propertyName;
    }

    /**
     * Get all the property resolvers for this project. Does not include parent projects. Uses defaults
     * if the user doesn't specify any custom resolvers.
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
     * <li>{@code [USER HOME]/.gradle-plugin-external-properties/[PROJECT NAME]/[SUBPROJECT NAME]/build.properties}</li>
     * <li>{@code [USER HOME]/.overrides/[PROJECT NAME]/[SUBPROJECT NAME]/build.properties}</li>
     * <li>{@code [PROJECT ROOT]/build.properties}</li>
     * </ol>
     * 
     * @return the list of property resolvers
     */
    public List<PropertyResolver> getDefaultResolvers() {
        if (defaultResolvers == null) {
            defaultResolvers = new ArrayList<>();

            // 1) Build the user directory file resolver
            String userHomeDirectory = System.getProperty("user.home");
            String projectNameHierarchy = buildProjectNameHierarchy();
            String userDirectoryResolverString = MessageFormat.format(
                    "{0}/.gradle-plugin-external-properties/{1}/build.properties", userHomeDirectory,
                    projectNameHierarchy);
            PropertyResolver userDirectoryResolver = new FileResolver(project, new File(userDirectoryResolverString));
            defaultResolvers.add(userDirectoryResolver);

            // 2) Build the user directory file resolver (DEPRECATED)
            // TODO - remove this in the next release!
            String deprecatedDirectoryResolverString = MessageFormat.format("{0}/.overrides/{1}/build.properties",
                    userHomeDirectory, projectNameHierarchy);
            PropertyResolver deprecatedUserDirectoryResolver = new FileResolver(project,
                    new File(deprecatedDirectoryResolverString));
            defaultResolvers.add(deprecatedUserDirectoryResolver);

            // 3) Build the project directory file resolver
            String projectDirectoryResolverString = MessageFormat.format("{0}/build.properties",
                    project.getProjectDir().getAbsolutePath());
            PropertyResolver projectDirectoryResolver = new FileResolver(project,
                    new File(projectDirectoryResolverString));
            defaultResolvers.add(projectDirectoryResolver);
        }
        return defaultResolvers;
    }

    /**
     * Build a project name hierarchy for the current project and its parent projects. A root project
     * would be returned as "my-project" and a subproject of that would be returned as
     * "my-project/my-subproject".
     * 
     * @return the project name hierarchy
     */
    private String buildProjectNameHierarchy() {
        String projectNameHierarchy = "";
        Project tempProject = project;
        while (tempProject != null) {
            projectNameHierarchy = tempProject.getName() + "/" + projectNameHierarchy;
            tempProject = tempProject.getParent();
        }

        // Remove any trailing slash
        if (projectNameHierarchy.endsWith("/")) {
            projectNameHierarchy = projectNameHierarchy.substring(0, projectNameHierarchy.length() - 1);
        }

        return projectNameHierarchy;
    }

}

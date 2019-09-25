package com.sidneysimmons.gradlepluginexternalproperties.extension;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

/**
 * Plugin extension for external properties related configuration.
 * 
 * @author Sidney Simmons
 */
public class ExternalPropertiesExtension {

    public static final String NAME = "externalProperties";

    private Property<String> propertyContainerName;
    private ListProperty<File> propertyFiles;

    /**
     * Constructor that sets a default container name and list of property files.
     * 
     * @param project the project
     */
    public ExternalPropertiesExtension(Project project) {
        // Default container name
        propertyContainerName = project.getObjects().property(String.class);
        propertyContainerName.set("props");

        // Default list of property files
        propertyFiles = project.getObjects().listProperty(File.class);
        propertyFiles.set(initializeDefaultPropertyFiles(project));
    }

    /**
     * Initialize a list of default property files. Currently contains the following files.
     * 
     * <ol>
     * <li>[USER_HOME]/.overrides/[PROJECT_NAME]/build.properties
     * <li>[PROJECT_ROOT]/build.properties
     * </ol>
     * 
     * @param project the project
     * @return the list of default property files
     */
    private List<File> initializeDefaultPropertyFiles(Project project) {
        List<File> defaultFiles = new ArrayList<>();
        defaultFiles.add(
                new File(MessageFormat.format("{0}/.overrides/{1}/build.properties", System.getProperty("user.home"), project.getName())));
        defaultFiles.add(new File(MessageFormat.format("{0}/build.properties", project.getProjectDir().getAbsolutePath())));
        return defaultFiles;
    }

    public Property<String> getPropertyContainerName() {
        return propertyContainerName;
    }

    public ListProperty<File> getPropertyFiles() {
        return propertyFiles;
    }

}
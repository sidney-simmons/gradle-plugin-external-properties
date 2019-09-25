package com.sidneysimmons.gradlepluginexternalproperties.util;

import com.sidneysimmons.gradlepluginexternalproperties.ExternalPropertiesContainer;
import com.sidneysimmons.gradlepluginexternalproperties.extension.ExternalPropertiesExtension;
import java.text.MessageFormat;
import org.gradle.api.Project;

/**
 * Utility methods for the plugin.
 * 
 * @author Sidney Simmons
 */
public class PluginUtil {

    private PluginUtil() {
        // No need to instantiate this
    }

    /**
     * Get the external properties container from the given project.
     * 
     * @param project the project
     * @return the external properties container
     */
    public static ExternalPropertiesContainer getExternalPropertiesContainer(Project project) {
        ExternalPropertiesExtension extension = project.getExtensions().getByType(ExternalPropertiesExtension.class);
        String propertiesContainerName = extension.getPropertyContainerName().get();
        return (ExternalPropertiesContainer) project.getExtensions().getExtraProperties().get(propertiesContainerName);
    }

    /**
     * Set the external properties container on the given project.
     * 
     * @param project the project
     */
    public static void setExternalPropertiesContainer(Project project) {
        ExternalPropertiesExtension extension = project.getExtensions().getByType(ExternalPropertiesExtension.class);
        String propertiesContainerName = extension.getPropertyContainerName().get();
        ExternalPropertiesContainer propertyContainer = new ExternalPropertiesContainer(project, extension);
        project.getLogger().info(MessageFormat.format("Setting external properties container name to \"{0}\"", propertiesContainerName));
        project.getExtensions().getExtraProperties().set(propertiesContainerName, propertyContainer);
    }

}

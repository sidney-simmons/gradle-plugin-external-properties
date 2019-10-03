package com.sidneysimmons.gradlepluginexternalproperties;

import com.sidneysimmons.gradlepluginexternalproperties.extension.ExternalPropertiesExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Custom plugin for working with external properties.
 * 
 * @author Sidney Simmons
 */
public class ExternalPropertiesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // Create the plugin extension
        ExternalPropertiesExtension extension = project.getExtensions().create(ExternalPropertiesExtension.NAME,
                ExternalPropertiesExtension.class, project);

        // Create the properties container
        project.getExtensions().create(ExternalPropertiesContainer.NAME, ExternalPropertiesContainer.class, project,
                extension);
    }

}

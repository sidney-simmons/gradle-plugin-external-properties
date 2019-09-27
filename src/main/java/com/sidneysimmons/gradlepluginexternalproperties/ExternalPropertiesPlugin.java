package com.sidneysimmons.gradlepluginexternalproperties;

import com.sidneysimmons.gradlepluginexternalproperties.extension.ExternalPropertiesExtension;
import com.sidneysimmons.gradlepluginexternalproperties.task.ShowAllProperties;
import com.sidneysimmons.gradlepluginexternalproperties.task.ShowAllResolvers;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

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
        project.getExtensions().create(ExternalPropertiesContainer.NAME, ExternalPropertiesContainer.class, project, extension);

        // Register a task to display all the configured external property resolvers
        TaskContainer taskContainer = project.getTasks();
        taskContainer.register(ShowAllResolvers.NAME, ShowAllResolvers.class, task -> {

        });

        // Register a task to display all the configured external properties
        taskContainer.register(ShowAllProperties.NAME, ShowAllProperties.class, task -> {

        });
    }

}

package com.sidneysimmons.gradlepluginexternalproperties;

import com.sidneysimmons.gradlepluginexternalproperties.extension.ExternalPropertiesExtension;
import com.sidneysimmons.gradlepluginexternalproperties.task.ShowPropertyResolversTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * Custom plugin for working with external properties.
 */
public class ExternalPropertiesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // Create the plugin extension
        ExternalPropertiesExtension extension = project.getExtensions().create(ExternalPropertiesExtension.NAME, ExternalPropertiesExtension.class, project);

        // Create the properties container
        project.getExtensions().create(ExternalPropertiesContainer.NAME, ExternalPropertiesContainer.class, project, extension);

        // Create the task to print the property resolvers
        TaskContainer taskContainer = project.getTasks();
        taskContainer.register(ShowPropertyResolversTask.NAME, ShowPropertyResolversTask.class, task -> {
            // Nothing to configure at the moment
        });
    }

}

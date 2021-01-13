package com.sidneysimmons.gradlepluginexternalproperties.task;

import com.sidneysimmons.gradlepluginexternalproperties.ExternalPropertiesContainer;
import com.sidneysimmons.gradlepluginexternalproperties.resolver.PropertyResolver;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

/**
 * Task used to print all of this project's configured property resolvers.
 */
public class ShowPropertyResolversTask extends DefaultTask {

    public static final String GROUP = "External Properties";
    public static final String NAME = "showPropertyResolvers";
    public static final String DESCRIPTION = "Prints this project's configured property resolvers.";

    @TaskAction
    void action() {
        Integer resolverPosition = 1;
        Project tempProject = getProject();
        while (tempProject != null) {
            getLogger().lifecycle("Project: " + buildProjectHierarchyString(tempProject));
            ExternalPropertiesContainer tempContainer = (ExternalPropertiesContainer) tempProject.getExtensions().findByName(ExternalPropertiesContainer.NAME);
            if (tempContainer != null) {
                if (!tempContainer.getResolvers().isEmpty()) {
                    for (PropertyResolver resolver : tempContainer.getResolvers()) {
                        getLogger().lifecycle(resolverPosition++ + ") " + resolver.toString());
                    }
                } else {
                    getLogger().lifecycle("No resolvers are configured.");
                }
            } else {
                getLogger().lifecycle("External properties container not configured.");
            }

            // Move on to the parent project (should return null when we've already reached the root project)
            tempProject = tempProject.getParent();
            if (tempProject != null) {
                getLogger().lifecycle("");
            }
        }
    }

    @Override
    @Internal
    public String getGroup() {
        return GROUP;
    }

    @Override
    @Internal
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * Build a project hierarchy string from the given project. Should result in a string such as
     * "root-project -> sub-project-1 -> sub-project-2".
     * 
     * @param project the project
     * @return the project hierarchy string
     */
    private String buildProjectHierarchyString(Project project) {
        String projectHierarchyString = null;
        Project tempProject = project;
        while (tempProject != null) {
            if (projectHierarchyString != null) {
                projectHierarchyString = tempProject.getName() + " -> " + projectHierarchyString;
            } else {
                projectHierarchyString = tempProject.getName();
            }

            // Move on to the parent project (should return null when we've already reached the root project)
            tempProject = tempProject.getParent();
        }
        return projectHierarchyString;
    }

}
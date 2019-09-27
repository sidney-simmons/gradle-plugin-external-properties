package com.sidneysimmons.gradlepluginexternalproperties.task;

import com.sidneysimmons.gradlepluginexternalproperties.resolver.PropertyResolver;
import java.util.List;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

/**
 * Task used to show all of the configured external property resolvers.
 * 
 * @author Sidney Simmons
 */
public class ShowAllResolvers extends BaseTask {

    public static final String GROUP = "External Properties";
    public static final String NAME = "showAllResolvers";
    public static final String DESCRIPTION = "Show all of the configured external property resolvers.";

    @TaskAction
    private void action() {
        List<PropertyResolver> resolvers = propertiesContainer.getResolvers();
        if (!resolvers.isEmpty()) {
            getLogger().lifecycle("");
            for (PropertyResolver propertyResolver : resolvers) {
                getLogger().lifecycle(propertyResolver.toString());
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

}
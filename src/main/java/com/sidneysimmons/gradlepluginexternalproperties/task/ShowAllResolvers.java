package com.sidneysimmons.gradlepluginexternalproperties.task;

import com.sidneysimmons.gradlepluginexternalproperties.ExternalPropertiesContainer;
import com.sidneysimmons.gradlepluginexternalproperties.resolver.PropertyResolver;
import com.sidneysimmons.gradlepluginexternalproperties.util.PluginUtil;
import java.util.List;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * Task used to show all of the configured external property resolvers.
 * 
 * @author Sidney Simmons
 */
public class ShowAllResolvers extends DefaultTask {

    public static final String GROUP = "External Properties";
    public static final String NAME = "showAllResolvers";
    public static final String DESCRIPTION = "Show all of the configured external property resolvers.";

    @TaskAction
    private void action() {
        ExternalPropertiesContainer propertiesContainer = PluginUtil.getExternalPropertiesContainer(getProject());
        List<PropertyResolver> resolvers = propertiesContainer.getPropertyResolvers();
        if (!resolvers.isEmpty()) {
            getLogger().lifecycle("");
            for (PropertyResolver propertyResolver : resolvers) {
                getLogger().lifecycle(propertyResolver.toString());
            }
        }
    }

    @Override
    public String getGroup() {
        return GROUP;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
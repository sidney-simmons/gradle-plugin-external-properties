package com.sidneysimmons.gradlepluginexternalproperties.task;

import com.sidneysimmons.gradlepluginexternalproperties.ExternalPropertiesContainer;
import org.gradle.api.DefaultTask;

/**
 * Base task for the tasks within this plugin. Makes the properties container available to all tasks.
 * 
 * @author Sidney Simmons
 */
public class BaseTask extends DefaultTask {

    protected ExternalPropertiesContainer propertiesContainer = getProject().getExtensions().getByType(ExternalPropertiesContainer.class);

}

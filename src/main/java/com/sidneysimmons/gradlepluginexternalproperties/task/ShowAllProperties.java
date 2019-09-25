package com.sidneysimmons.gradlepluginexternalproperties.task;

import com.sidneysimmons.gradlepluginexternalproperties.ExternalPropertiesContainer;
import com.sidneysimmons.gradlepluginexternalproperties.resolver.PropertyResolver;
import com.sidneysimmons.gradlepluginexternalproperties.util.PluginUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

/**
 * Task used to show all of the configured external properties.
 * 
 * @author Sidney Simmons
 */
public class ShowAllProperties extends DefaultTask {

    public static final String GROUP = "External Properties";
    public static final String NAME = "showAllProperties";
    public static final String DESCRIPTION = "Show all of the configured external properties.";

    @TaskAction
    private void action() {
        // Get all the property resolvers
        ExternalPropertiesContainer propertiesContainer = PluginUtil.getExternalPropertiesContainer(getProject());
        List<PropertyResolver> propertyResolvers = propertiesContainer.getPropertyResolvers();

        // Reverse loop through the resolvers and put all the property details into a map
        SortedMap<String, PropertyDetails> propertyDetailsByProperty = new TreeMap<>();
        for (int i = propertyResolvers.size() - 1; i >= 0; i--) {
            PropertyResolver resolver = propertyResolvers.get(i);
            for (Entry<Object, Object> entry : resolver.getProperties().entrySet()) {
                PropertyDetails details = new PropertyDetails();
                details.propertyName = (String) entry.getKey();
                details.propertyValue = (String) entry.getValue();
                details.propertyResolver = resolver;
                propertyDetailsByProperty.put(details.propertyName, details);
            }
        }

        // Separate the properties by resolver
        Map<PropertyResolver, List<PropertyDetails>> propertyDetailsByResolver = new HashMap<>();
        for (Entry<String, PropertyDetails> entry : propertyDetailsByProperty.entrySet()) {
            PropertyDetails details = entry.getValue();
            if (!propertyDetailsByResolver.containsKey(details.propertyResolver)) {
                propertyDetailsByResolver.put(details.propertyResolver, new ArrayList<>());
            }
            propertyDetailsByResolver.get(details.propertyResolver).add(details);
        }

        // Log all the properties!
        for (PropertyResolver resolver : propertyResolvers) {
            String resolverString = resolver.toString();
            getLogger().lifecycle("\n" + resolverString);
            getLogger().lifecycle(String.join("", Collections.nCopies(resolverString.length(), "-")));

            List<PropertyDetails> detailsList = propertyDetailsByResolver.get(resolver);
            if (detailsList != null && !detailsList.isEmpty()) {
                for (PropertyDetails details : detailsList) {
                    getLogger().lifecycle(details.propertyName + "=" + details.propertyValue);
                }
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
     * Internal class used to group a property's name/value/resolver.
     * 
     * @author Sidney Simmons
     */
    private class PropertyDetails {
        protected String propertyName;
        protected String propertyValue;
        protected PropertyResolver propertyResolver;
    }

}
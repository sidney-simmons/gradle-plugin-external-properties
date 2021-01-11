package com.sidneysimmons.gradlepluginexternalproperties.resolver;

import java.util.Properties;

/**
 * Interface for all property resolvers.
 */
public interface PropertyResolver {

    /**
     * Resolve the property with the given property name.
     * 
     * @param propertyName the property name
     * @return the property if found, null otherwise
     */
    String resolve(String propertyName);

    /**
     * Get the loaded properties.
     * 
     * @return the properties
     */
    Properties getProperties();

}

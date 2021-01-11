package com.sidneysimmons.gradlepluginexternalproperties.extension;

import com.sidneysimmons.gradlepluginexternalproperties.resolver.FileResolver;
import com.sidneysimmons.gradlepluginexternalproperties.resolver.PropertyResolver;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;

/**
 * Plugin extension for configuring this plugin.
 */
public class ExternalPropertiesExtension {

    public static final String NAME = "externalProperties";
    private Project project;
    private ListProperty<PropertyResolver> resolvers;

    /**
     * Constructor.
     * 
     * @param project the project
     */
    public ExternalPropertiesExtension(Project project) {
        this.project = project;

        // Default the list of resolvers to an empty list
        resolvers = project.getObjects().listProperty(PropertyResolver.class);
        resolvers.set(new ArrayList<>());
    }

    /**
     * Allows the user to add a {@link File} based resolver.
     * 
     * @param file the file
     */
    public void resolver(File file) {
        appendResolver(new FileResolver(project, file));
    }

    /**
     * Get all the resolvers.
     * 
     * @return the list of resolvers
     */
    public ListProperty<PropertyResolver> getResolvers() {
        return resolvers;
    }

    /**
     * Appends the given resolver to the list of resolvers.
     * 
     * @param resolver the resolver to append
     */
    private void appendResolver(PropertyResolver resolver) {
        List<PropertyResolver> existingResolvers = resolvers.get();
        List<PropertyResolver> newResolvers = new ArrayList<>(existingResolvers);
        newResolvers.add(resolver);
        resolvers.set(newResolvers);
    }

}
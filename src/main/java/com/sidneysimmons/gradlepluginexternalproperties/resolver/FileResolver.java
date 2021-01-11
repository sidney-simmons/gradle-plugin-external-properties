package com.sidneysimmons.gradlepluginexternalproperties.resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolver for {@link File} objects.
 * 
 * @author Sidney Simmons
 */
public class FileResolver implements PropertyResolver {

    private static final Logger log = LoggerFactory.getLogger(FileResolver.class);

    private Project project;
    private File file;
    private Properties properties;

    /**
     * Constructor.
     * 
     * @param project the project
     * @param file the properties file
     */
    public FileResolver(Project project, File file) {
        this.project = project;
        this.file = file;
    }

    @Override
    public String resolve(String propertyName) {
        return getProperties().getProperty(propertyName);
    }

    @Override
    public Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream inputStream = new FileInputStream(file)) {
                properties.load(inputStream);
            } catch (IOException e) {
                // This is a valid scenario so log it and continue gracefully
                log.debug(MessageFormat.format("Cannot load external properties file. Continuing gracefully. file = {0}", file));
            }
        }
        return properties;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()));
        builder.append("(");
        builder.append("file=" + file);
        builder.append(")");
        return builder.toString();
    }

}

package com.sidneysimmons.gradlepluginexternalproperties.resolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolver for {@link File} objects formatted as ".properties" files.
 */
public class PropertiesFileResolver implements PropertyResolver {

    private static final Logger log = LoggerFactory.getLogger(PropertiesFileResolver.class);

    private File file;
    private Properties properties;

    /**
     * Constructor.
     * 
     * @param file the properties file
     */
    public PropertiesFileResolver(File file) {
        this.file = file;
    }

    @Override
    public String resolve(String propertyName) {
        return getProperties().getProperty(propertyName);
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

    /**
     * Get the properties for this properties file resolver. Loads the properties from the file and
     * keeps them in memory.
     * 
     * @return the properties from the given file
     */
    private Properties getProperties() {
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

}

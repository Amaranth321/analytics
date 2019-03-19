package com.kaisquare.vca.sdk;

import com.kaisquare.vca.exceptions.InvalidSettingsException;
import com.kaisquare.vca.shared.LocalizableText;
import com.kaisquare.vca.utils.JsonReader;

/**
 * @author Aye Maung
 * @since v4.5
 */
public interface Parameter
{
    /**
     * Argument of the parameter (e.g. --cntio).
     * Case-sensitive.
     * requires single or double dash prefix
     */
    String arg();

    /**
     * specify whether value is required for this parameter.
     * <p/>
     * e.g.
     * -R 5  => value required, -t    => value not required
     */
    boolean valueRequired();

    /**
     * Parameter description. This might be exposed on the configuration UI in the future.
     */
    LocalizableText description();

    /**
     * The return value of this call will be used as a command line parameter value, hence always a string
     * <p/>
     * Depending on the nature of this parameter, an implementation of this method may not be applicable.
     * For such cases, throw {@link UnsupportedOperationException}
     *
     * @param configuredSettingsReader settings map sent from platform UI
     * @param serverInfoProxy          proxy to access essential info on the server
     *
     * @return null if userConfiguredSettings doesn't contain this parameter
     */
    String parse(JsonReader configuredSettingsReader,
                 ServerInfoProxy serverInfoProxy) throws InvalidSettingsException;

    /**
     * If non-optional parameters are missing, vca will not be started.
     */
    boolean optional();

    /**
     * Default value for this parameter if user doesn't configure it.
     * <p/>
     * If a default value is not applicable (e.g. regions), throw {@link UnsupportedOperationException}
     *
     * @param serverInfoProxy proxy to access essential info on the server
     */
    String defaultValue(ServerInfoProxy serverInfoProxy);
}

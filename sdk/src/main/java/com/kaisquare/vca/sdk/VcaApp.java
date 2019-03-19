package com.kaisquare.vca.sdk;

import com.kaisquare.vca.exceptions.InvalidOutputException;
import com.kaisquare.vca.shared.ExtractedData;
import com.kaisquare.vca.shared.LocalizableText;
import com.kaisquare.vca.shared.StreamType;
import net.xeoh.plugins.base.Plugin;

import java.util.List;
import java.util.Map;

/**
 * Use this interface for adding new types of KAI_X2 VCA apps.
 *
 * @author Aye Maung
 * @since v4.5
 */
public interface VcaApp extends Plugin
{
    /**
     * unique id.
     * <p/>
     * Convention: use lowercase letters only (with underscores if necessary).
     */
    String appId();

    /**
     * Event Type output by raw vca data
     *
     * @return
     */
    String vcaOutputTypeName();
    /**
     * Name to be shown on UI
     */
    LocalizableText displayName();

    /**
     * Detailed description of what this app does. This will be exposed on the UI in the future.
     */
    LocalizableText description();

    /**
     * stream type for video feed
     */
    StreamType streamType();

    /**
     * Additional parameters required by this VCA.
     * Add them in the order to be appeared in the command line.
     */
    List<Parameter> parameters();

    /**
     * environment variables just for this VCA app.
     * <p/>
     * Return an empty map if this is not needed. DO NOT return null.
     */
    Map<String, String> environmentVariables();

    /**
     * @param outputData      data map of the json output from the vca process
     * @param serverInfoProxy proxy to access essential info on the server
     */
    List<ExtractedData> extract(Map outputData, ServerInfoProxy serverInfoProxy) throws InvalidOutputException;

    /**
     * This task will be run before the vca process is started.
     * <p/>
     * You may use this to copy out resources necessary to run the vca.
     *
     * @return null if not needed
     */
    Runnable preProcessTask();

    /**
     * This task will be run after the process has ended.
     * <p/>
     * You may use this to remove/cleanup anything created during the runtime
     *
     * @return null if not needed
     */
    Runnable postProcessTask();


}

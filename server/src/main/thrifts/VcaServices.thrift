/*
 * Copyright (C) KAI Square Pte Ltd
 *
 * This Thrift IDL file defines the vca services offered by KUP Analytics to the Platform.
 *
 */

namespace java com.kaisquare.vca.thrift
namespace cpp com.kaisquare.vca.thrift

/*****************************************************************************/
/* DATA STRUCTURES                                                           */
/*****************************************************************************/

struct TVcaInfo {
    1: string instanceId,
    2: string appId,
    3: string coreDeviceId,
    4: string channelId,
    5: string settings,
    6: string recurrenceRule,
    7: bool enabled
}

struct TVcaInstance {
    1: TVcaInfo vcaInfo,
    2: double releaseNumber,
    3: bool updateRequired,
    4: string vcaStatus
}

struct TVcaServerInfo {
    1: i64 serverStartTime,
    2: double releaseNumber,
    3: list<string> threads
}

struct TVcaAppInfo {
    1: string appId,
    2: string program,
    3: string version,
    4: map<string,string> displayName,
    5: map<string,string> description
}

service VcaServices {

    /**
     * Get the list of VCAs
     *
     * Returns the list of VCAs
     */
	list<TVcaInstance> getVcaList(),

    /**
     * Add a new vca instance
     *
     * (1) vcaInfo - thrift vca info object
     *
     * Returns the created instance
     */
	TVcaInstance addVca(1: TVcaInfo vcaInfo),

    /**
     * Updates VCA instance
     *
     * (1) instanceId - vca instance ID.
     * (2) settings - json string of vca settings
     * (3) recurrenceRule - json string of vca schedule
     *
     * Returns true if successful, false on failure.
     */
	bool updateVca(1: string instanceId
	               2: string settings
	               3: string recurrenceRule),

    /**
     * Removes a vca
     *
     * (1) instanceId - vca instance ID.
     *
     * Returns true if successful, false on failure.
     */
    bool removeVca(1: string instanceId),

    /**
     * Activates a vca
     *
     * (1) instanceId - vca instance ID.
     *
     * Returns true if successful, false on failure.
     */
    bool activateVca(1: string instanceId),

    /**
     * Deactivates a vca
     *
     * (1) instanceId - vca instance ID.
     *
     * Returns true if successful, false on failure.
     */
    bool deactivateVca(1: string instanceId),

    /**
     * Get executed vca command for a running instance
     *
     * (1) instanceId - vca instance ID.
     *
     */
    list<string> getVcaProcessCommands(1: string instanceId),

    /**
     * Get executed vca command for a running instance
     *
     * (1) instanceId - vca instance ID.
     *
     */
    TVcaServerInfo getServerInformation(),

    /**
     * Get information for all supported programs
     *
     */
    list<TVcaAppInfo> getSupportedApps(),

}
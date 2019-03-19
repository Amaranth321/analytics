/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.kaisquare.core.thrift;

import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import java.util.*;

/**
 * Stream File Details
 * (1) serverId - Stream file located server/group.
 * (2) from - Begin timestamp of this video/image (ddMMyyyyHHmmss format).
 * (3) to - End timestamp of this video/image (ddMMyyyyHHmmss format).
 * (4) fileSize - The size of this video/image. uint: B.
 * (5) url - The url/path of the video/image.
 * (6) createdTime - The time media file created, on KUP it is the upload time.
 * (7) progress - The video/image upload progress, units : percent, from "0" to "100".
 * (8) status - The upload status. Valid values are:
 *             "unrequested" means video/image has not been requested to upload by Platform.
 *             "requested" means Platform requested to upload video/image.
 *             "uploading" means video/image is being uploaded.
 *             "completed" means video/image upload completed.
 *             "retrying" means upload error, but Core is retrying again.
 *             "aborted" means upload error, and Core gives up the uploading.
 *             "stopped" means upload has been canceled by Platform, Core stops uploading.
 */
public class StreamFileDetails implements org.apache.thrift.TBase<StreamFileDetails, StreamFileDetails._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StreamFileDetails");

  private static final org.apache.thrift.protocol.TField SERVER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("serverId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField FROM_FIELD_DESC = new org.apache.thrift.protocol.TField("from", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TO_FIELD_DESC = new org.apache.thrift.protocol.TField("to", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FILE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("fileSize", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField URL_FIELD_DESC = new org.apache.thrift.protocol.TField("url", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CREATED_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createdTime", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField PROGRESS_FIELD_DESC = new org.apache.thrift.protocol.TField("progress", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StreamFileDetailsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StreamFileDetailsTupleSchemeFactory());
  }

  private String serverId; // required
  private String from; // required
  private String to; // required
  private String fileSize; // required
  private String url; // required
  private String createdTime; // required
  private String progress; // required
  private String status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SERVER_ID((short)1, "serverId"),
    FROM((short)2, "from"),
    TO((short)3, "to"),
    FILE_SIZE((short)4, "fileSize"),
    URL((short)5, "url"),
    CREATED_TIME((short)6, "createdTime"),
    PROGRESS((short)7, "progress"),
    STATUS((short)8, "status");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SERVER_ID
          return SERVER_ID;
        case 2: // FROM
          return FROM;
        case 3: // TO
          return TO;
        case 4: // FILE_SIZE
          return FILE_SIZE;
        case 5: // URL
          return URL;
        case 6: // CREATED_TIME
          return CREATED_TIME;
        case 7: // PROGRESS
          return PROGRESS;
        case 8: // STATUS
          return STATUS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SERVER_ID, new org.apache.thrift.meta_data.FieldMetaData("serverId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FROM, new org.apache.thrift.meta_data.FieldMetaData("from", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TO, new org.apache.thrift.meta_data.FieldMetaData("to", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("fileSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.URL, new org.apache.thrift.meta_data.FieldMetaData("url", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATED_TIME, new org.apache.thrift.meta_data.FieldMetaData("createdTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PROGRESS, new org.apache.thrift.meta_data.FieldMetaData("progress", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StreamFileDetails.class, metaDataMap);
  }

  public StreamFileDetails() {
  }

  public StreamFileDetails(
    String serverId,
    String from,
    String to,
    String fileSize,
    String url,
    String createdTime,
    String progress,
    String status)
  {
    this();
    this.serverId = serverId;
    this.from = from;
    this.to = to;
    this.fileSize = fileSize;
    this.url = url;
    this.createdTime = createdTime;
    this.progress = progress;
    this.status = status;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StreamFileDetails(StreamFileDetails other) {
    if (other.isSetServerId()) {
      this.serverId = other.serverId;
    }
    if (other.isSetFrom()) {
      this.from = other.from;
    }
    if (other.isSetTo()) {
      this.to = other.to;
    }
    if (other.isSetFileSize()) {
      this.fileSize = other.fileSize;
    }
    if (other.isSetUrl()) {
      this.url = other.url;
    }
    if (other.isSetCreatedTime()) {
      this.createdTime = other.createdTime;
    }
    if (other.isSetProgress()) {
      this.progress = other.progress;
    }
    if (other.isSetStatus()) {
      this.status = other.status;
    }
  }

  public StreamFileDetails deepCopy() {
    return new StreamFileDetails(this);
  }

  @Override
  public void clear() {
    this.serverId = null;
    this.from = null;
    this.to = null;
    this.fileSize = null;
    this.url = null;
    this.createdTime = null;
    this.progress = null;
    this.status = null;
  }

  public String getServerId() {
    return this.serverId;
  }

  public StreamFileDetails setServerId(String serverId) {
    this.serverId = serverId;
    return this;
  }

  public void unsetServerId() {
    this.serverId = null;
  }

  /** Returns true if field serverId is set (has been assigned a value) and false otherwise */
  public boolean isSetServerId() {
    return this.serverId != null;
  }

  public void setServerIdIsSet(boolean value) {
    if (!value) {
      this.serverId = null;
    }
  }

  public String getFrom() {
    return this.from;
  }

  public StreamFileDetails setFrom(String from) {
    this.from = from;
    return this;
  }

  public void unsetFrom() {
    this.from = null;
  }

  /** Returns true if field from is set (has been assigned a value) and false otherwise */
  public boolean isSetFrom() {
    return this.from != null;
  }

  public void setFromIsSet(boolean value) {
    if (!value) {
      this.from = null;
    }
  }

  public String getTo() {
    return this.to;
  }

  public StreamFileDetails setTo(String to) {
    this.to = to;
    return this;
  }

  public void unsetTo() {
    this.to = null;
  }

  /** Returns true if field to is set (has been assigned a value) and false otherwise */
  public boolean isSetTo() {
    return this.to != null;
  }

  public void setToIsSet(boolean value) {
    if (!value) {
      this.to = null;
    }
  }

  public String getFileSize() {
    return this.fileSize;
  }

  public StreamFileDetails setFileSize(String fileSize) {
    this.fileSize = fileSize;
    return this;
  }

  public void unsetFileSize() {
    this.fileSize = null;
  }

  /** Returns true if field fileSize is set (has been assigned a value) and false otherwise */
  public boolean isSetFileSize() {
    return this.fileSize != null;
  }

  public void setFileSizeIsSet(boolean value) {
    if (!value) {
      this.fileSize = null;
    }
  }

  public String getUrl() {
    return this.url;
  }

  public StreamFileDetails setUrl(String url) {
    this.url = url;
    return this;
  }

  public void unsetUrl() {
    this.url = null;
  }

  /** Returns true if field url is set (has been assigned a value) and false otherwise */
  public boolean isSetUrl() {
    return this.url != null;
  }

  public void setUrlIsSet(boolean value) {
    if (!value) {
      this.url = null;
    }
  }

  public String getCreatedTime() {
    return this.createdTime;
  }

  public StreamFileDetails setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  public void unsetCreatedTime() {
    this.createdTime = null;
  }

  /** Returns true if field createdTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreatedTime() {
    return this.createdTime != null;
  }

  public void setCreatedTimeIsSet(boolean value) {
    if (!value) {
      this.createdTime = null;
    }
  }

  public String getProgress() {
    return this.progress;
  }

  public StreamFileDetails setProgress(String progress) {
    this.progress = progress;
    return this;
  }

  public void unsetProgress() {
    this.progress = null;
  }

  /** Returns true if field progress is set (has been assigned a value) and false otherwise */
  public boolean isSetProgress() {
    return this.progress != null;
  }

  public void setProgressIsSet(boolean value) {
    if (!value) {
      this.progress = null;
    }
  }

  public String getStatus() {
    return this.status;
  }

  public StreamFileDetails setStatus(String status) {
    this.status = status;
    return this;
  }

  public void unsetStatus() {
    this.status = null;
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return this.status != null;
  }

  public void setStatusIsSet(boolean value) {
    if (!value) {
      this.status = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SERVER_ID:
      if (value == null) {
        unsetServerId();
      } else {
        setServerId((String)value);
      }
      break;

    case FROM:
      if (value == null) {
        unsetFrom();
      } else {
        setFrom((String)value);
      }
      break;

    case TO:
      if (value == null) {
        unsetTo();
      } else {
        setTo((String)value);
      }
      break;

    case FILE_SIZE:
      if (value == null) {
        unsetFileSize();
      } else {
        setFileSize((String)value);
      }
      break;

    case URL:
      if (value == null) {
        unsetUrl();
      } else {
        setUrl((String)value);
      }
      break;

    case CREATED_TIME:
      if (value == null) {
        unsetCreatedTime();
      } else {
        setCreatedTime((String)value);
      }
      break;

    case PROGRESS:
      if (value == null) {
        unsetProgress();
      } else {
        setProgress((String)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SERVER_ID:
      return getServerId();

    case FROM:
      return getFrom();

    case TO:
      return getTo();

    case FILE_SIZE:
      return getFileSize();

    case URL:
      return getUrl();

    case CREATED_TIME:
      return getCreatedTime();

    case PROGRESS:
      return getProgress();

    case STATUS:
      return getStatus();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SERVER_ID:
      return isSetServerId();
    case FROM:
      return isSetFrom();
    case TO:
      return isSetTo();
    case FILE_SIZE:
      return isSetFileSize();
    case URL:
      return isSetUrl();
    case CREATED_TIME:
      return isSetCreatedTime();
    case PROGRESS:
      return isSetProgress();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StreamFileDetails)
      return this.equals((StreamFileDetails)that);
    return false;
  }

  public boolean equals(StreamFileDetails that) {
    if (that == null)
      return false;

    boolean this_present_serverId = true && this.isSetServerId();
    boolean that_present_serverId = true && that.isSetServerId();
    if (this_present_serverId || that_present_serverId) {
      if (!(this_present_serverId && that_present_serverId))
        return false;
      if (!this.serverId.equals(that.serverId))
        return false;
    }

    boolean this_present_from = true && this.isSetFrom();
    boolean that_present_from = true && that.isSetFrom();
    if (this_present_from || that_present_from) {
      if (!(this_present_from && that_present_from))
        return false;
      if (!this.from.equals(that.from))
        return false;
    }

    boolean this_present_to = true && this.isSetTo();
    boolean that_present_to = true && that.isSetTo();
    if (this_present_to || that_present_to) {
      if (!(this_present_to && that_present_to))
        return false;
      if (!this.to.equals(that.to))
        return false;
    }

    boolean this_present_fileSize = true && this.isSetFileSize();
    boolean that_present_fileSize = true && that.isSetFileSize();
    if (this_present_fileSize || that_present_fileSize) {
      if (!(this_present_fileSize && that_present_fileSize))
        return false;
      if (!this.fileSize.equals(that.fileSize))
        return false;
    }

    boolean this_present_url = true && this.isSetUrl();
    boolean that_present_url = true && that.isSetUrl();
    if (this_present_url || that_present_url) {
      if (!(this_present_url && that_present_url))
        return false;
      if (!this.url.equals(that.url))
        return false;
    }

    boolean this_present_createdTime = true && this.isSetCreatedTime();
    boolean that_present_createdTime = true && that.isSetCreatedTime();
    if (this_present_createdTime || that_present_createdTime) {
      if (!(this_present_createdTime && that_present_createdTime))
        return false;
      if (!this.createdTime.equals(that.createdTime))
        return false;
    }

    boolean this_present_progress = true && this.isSetProgress();
    boolean that_present_progress = true && that.isSetProgress();
    if (this_present_progress || that_present_progress) {
      if (!(this_present_progress && that_present_progress))
        return false;
      if (!this.progress.equals(that.progress))
        return false;
    }

    boolean this_present_status = true && this.isSetStatus();
    boolean that_present_status = true && that.isSetStatus();
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (!this.status.equals(that.status))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(StreamFileDetails other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    StreamFileDetails typedOther = (StreamFileDetails)other;

    lastComparison = Boolean.valueOf(isSetServerId()).compareTo(typedOther.isSetServerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServerId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serverId, typedOther.serverId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFrom()).compareTo(typedOther.isSetFrom());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFrom()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.from, typedOther.from);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTo()).compareTo(typedOther.isSetTo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.to, typedOther.to);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFileSize()).compareTo(typedOther.isSetFileSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileSize, typedOther.fileSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUrl()).compareTo(typedOther.isSetUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.url, typedOther.url);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreatedTime()).compareTo(typedOther.isSetCreatedTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreatedTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createdTime, typedOther.createdTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetProgress()).compareTo(typedOther.isSetProgress());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetProgress()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.progress, typedOther.progress);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(typedOther.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, typedOther.status);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("StreamFileDetails(");
    boolean first = true;

    sb.append("serverId:");
    if (this.serverId == null) {
      sb.append("null");
    } else {
      sb.append(this.serverId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("from:");
    if (this.from == null) {
      sb.append("null");
    } else {
      sb.append(this.from);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("to:");
    if (this.to == null) {
      sb.append("null");
    } else {
      sb.append(this.to);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileSize:");
    if (this.fileSize == null) {
      sb.append("null");
    } else {
      sb.append(this.fileSize);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("url:");
    if (this.url == null) {
      sb.append("null");
    } else {
      sb.append(this.url);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createdTime:");
    if (this.createdTime == null) {
      sb.append("null");
    } else {
      sb.append(this.createdTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("progress:");
    if (this.progress == null) {
      sb.append("null");
    } else {
      sb.append(this.progress);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    if (this.status == null) {
      sb.append("null");
    } else {
      sb.append(this.status);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class StreamFileDetailsStandardSchemeFactory implements SchemeFactory
  {
    public StreamFileDetailsStandardScheme getScheme() {
      return new StreamFileDetailsStandardScheme();
    }
  }

  private static class StreamFileDetailsStandardScheme extends StandardScheme<StreamFileDetails>
  {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StreamFileDetails struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SERVER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.serverId = iprot.readString();
              struct.setServerIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FROM
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.from = iprot.readString();
              struct.setFromIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.to = iprot.readString();
              struct.setToIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FILE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileSize = iprot.readString();
              struct.setFileSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.url = iprot.readString();
              struct.setUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATED_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createdTime = iprot.readString();
              struct.setCreatedTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // PROGRESS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.progress = iprot.readString();
              struct.setProgressIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.status = iprot.readString();
              struct.setStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, StreamFileDetails struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.serverId != null) {
        oprot.writeFieldBegin(SERVER_ID_FIELD_DESC);
        oprot.writeString(struct.serverId);
        oprot.writeFieldEnd();
      }
      if (struct.from != null) {
        oprot.writeFieldBegin(FROM_FIELD_DESC);
        oprot.writeString(struct.from);
        oprot.writeFieldEnd();
      }
      if (struct.to != null) {
        oprot.writeFieldBegin(TO_FIELD_DESC);
        oprot.writeString(struct.to);
        oprot.writeFieldEnd();
      }
      if (struct.fileSize != null) {
        oprot.writeFieldBegin(FILE_SIZE_FIELD_DESC);
        oprot.writeString(struct.fileSize);
        oprot.writeFieldEnd();
      }
      if (struct.url != null) {
        oprot.writeFieldBegin(URL_FIELD_DESC);
        oprot.writeString(struct.url);
        oprot.writeFieldEnd();
      }
      if (struct.createdTime != null) {
        oprot.writeFieldBegin(CREATED_TIME_FIELD_DESC);
        oprot.writeString(struct.createdTime);
        oprot.writeFieldEnd();
      }
      if (struct.progress != null) {
        oprot.writeFieldBegin(PROGRESS_FIELD_DESC);
        oprot.writeString(struct.progress);
        oprot.writeFieldEnd();
      }
      if (struct.status != null) {
        oprot.writeFieldBegin(STATUS_FIELD_DESC);
        oprot.writeString(struct.status);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StreamFileDetailsTupleSchemeFactory implements SchemeFactory
  {
    public StreamFileDetailsTupleScheme getScheme() {
      return new StreamFileDetailsTupleScheme();
    }
  }

  private static class StreamFileDetailsTupleScheme extends TupleScheme<StreamFileDetails>
  {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StreamFileDetails struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetServerId()) {
        optionals.set(0);
      }
      if (struct.isSetFrom()) {
        optionals.set(1);
      }
      if (struct.isSetTo()) {
        optionals.set(2);
      }
      if (struct.isSetFileSize()) {
        optionals.set(3);
      }
      if (struct.isSetUrl()) {
        optionals.set(4);
      }
      if (struct.isSetCreatedTime()) {
        optionals.set(5);
      }
      if (struct.isSetProgress()) {
        optionals.set(6);
      }
      if (struct.isSetStatus()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetServerId()) {
        oprot.writeString(struct.serverId);
      }
      if (struct.isSetFrom()) {
        oprot.writeString(struct.from);
      }
      if (struct.isSetTo()) {
        oprot.writeString(struct.to);
      }
      if (struct.isSetFileSize()) {
        oprot.writeString(struct.fileSize);
      }
      if (struct.isSetUrl()) {
        oprot.writeString(struct.url);
      }
      if (struct.isSetCreatedTime()) {
        oprot.writeString(struct.createdTime);
      }
      if (struct.isSetProgress()) {
        oprot.writeString(struct.progress);
      }
      if (struct.isSetStatus()) {
        oprot.writeString(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StreamFileDetails struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.serverId = iprot.readString();
        struct.setServerIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.from = iprot.readString();
        struct.setFromIsSet(true);
      }
      if (incoming.get(2)) {
        struct.to = iprot.readString();
        struct.setToIsSet(true);
      }
      if (incoming.get(3)) {
        struct.fileSize = iprot.readString();
        struct.setFileSizeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.url = iprot.readString();
        struct.setUrlIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createdTime = iprot.readString();
        struct.setCreatedTimeIsSet(true);
      }
      if (incoming.get(6)) {
        struct.progress = iprot.readString();
        struct.setProgressIsSet(true);
      }
      if (incoming.get(7)) {
        struct.status = iprot.readString();
        struct.setStatusIsSet(true);
      }
    }
  }

}


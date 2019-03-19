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
 * Stream Information
 * (1) deviceId - The device ID
 * (2) channelId - The channel ID
 * (3) isLiveview - Indicates liveview or playback
 * (4) type - The stream type, e.g. "http/jpeg" or "rtmp/h264"
 * (5) url - The stream URL as visible to outside world
 * (6) startTime - The timestamp when stream was started (ddMMyyyyHHmmss format)
 * (7) clients - List of clients connected to this stream
 */
public class StreamInfo implements org.apache.thrift.TBase<StreamInfo, StreamInfo._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StreamInfo");

  private static final org.apache.thrift.protocol.TField DEVICE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("deviceId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField CHANNEL_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("channelId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField IS_LIVEVIEW_FIELD_DESC = new org.apache.thrift.protocol.TField("isLiveview", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField URL_FIELD_DESC = new org.apache.thrift.protocol.TField("url", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField START_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("startTime", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField CLIENTS_FIELD_DESC = new org.apache.thrift.protocol.TField("clients", org.apache.thrift.protocol.TType.LIST, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StreamInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StreamInfoTupleSchemeFactory());
  }

  private String deviceId; // required
  private String channelId; // required
  private boolean isLiveview; // required
  private String type; // required
  private String url; // required
  private String startTime; // required
  private List<StreamClientInfo> clients; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DEVICE_ID((short)1, "deviceId"),
    CHANNEL_ID((short)2, "channelId"),
    IS_LIVEVIEW((short)3, "isLiveview"),
    TYPE((short)4, "type"),
    URL((short)5, "url"),
    START_TIME((short)6, "startTime"),
    CLIENTS((short)7, "clients");

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
        case 1: // DEVICE_ID
          return DEVICE_ID;
        case 2: // CHANNEL_ID
          return CHANNEL_ID;
        case 3: // IS_LIVEVIEW
          return IS_LIVEVIEW;
        case 4: // TYPE
          return TYPE;
        case 5: // URL
          return URL;
        case 6: // START_TIME
          return START_TIME;
        case 7: // CLIENTS
          return CLIENTS;
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
  private static final int __ISLIVEVIEW_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DEVICE_ID, new org.apache.thrift.meta_data.FieldMetaData("deviceId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CHANNEL_ID, new org.apache.thrift.meta_data.FieldMetaData("channelId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IS_LIVEVIEW, new org.apache.thrift.meta_data.FieldMetaData("isLiveview", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.URL, new org.apache.thrift.meta_data.FieldMetaData("url", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.START_TIME, new org.apache.thrift.meta_data.FieldMetaData("startTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLIENTS, new org.apache.thrift.meta_data.FieldMetaData("clients", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, StreamClientInfo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StreamInfo.class, metaDataMap);
  }

  public StreamInfo() {
  }

  public StreamInfo(
    String deviceId,
    String channelId,
    boolean isLiveview,
    String type,
    String url,
    String startTime,
    List<StreamClientInfo> clients)
  {
    this();
    this.deviceId = deviceId;
    this.channelId = channelId;
    this.isLiveview = isLiveview;
    setIsLiveviewIsSet(true);
    this.type = type;
    this.url = url;
    this.startTime = startTime;
    this.clients = clients;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StreamInfo(StreamInfo other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetDeviceId()) {
      this.deviceId = other.deviceId;
    }
    if (other.isSetChannelId()) {
      this.channelId = other.channelId;
    }
    this.isLiveview = other.isLiveview;
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetUrl()) {
      this.url = other.url;
    }
    if (other.isSetStartTime()) {
      this.startTime = other.startTime;
    }
    if (other.isSetClients()) {
      List<StreamClientInfo> __this__clients = new ArrayList<StreamClientInfo>();
      for (StreamClientInfo other_element : other.clients) {
        __this__clients.add(new StreamClientInfo(other_element));
      }
      this.clients = __this__clients;
    }
  }

  public StreamInfo deepCopy() {
    return new StreamInfo(this);
  }

  @Override
  public void clear() {
    this.deviceId = null;
    this.channelId = null;
    setIsLiveviewIsSet(false);
    this.isLiveview = false;
    this.type = null;
    this.url = null;
    this.startTime = null;
    this.clients = null;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public StreamInfo setDeviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

  public void unsetDeviceId() {
    this.deviceId = null;
  }

  /** Returns true if field deviceId is set (has been assigned a value) and false otherwise */
  public boolean isSetDeviceId() {
    return this.deviceId != null;
  }

  public void setDeviceIdIsSet(boolean value) {
    if (!value) {
      this.deviceId = null;
    }
  }

  public String getChannelId() {
    return this.channelId;
  }

  public StreamInfo setChannelId(String channelId) {
    this.channelId = channelId;
    return this;
  }

  public void unsetChannelId() {
    this.channelId = null;
  }

  /** Returns true if field channelId is set (has been assigned a value) and false otherwise */
  public boolean isSetChannelId() {
    return this.channelId != null;
  }

  public void setChannelIdIsSet(boolean value) {
    if (!value) {
      this.channelId = null;
    }
  }

  public boolean isIsLiveview() {
    return this.isLiveview;
  }

  public StreamInfo setIsLiveview(boolean isLiveview) {
    this.isLiveview = isLiveview;
    setIsLiveviewIsSet(true);
    return this;
  }

  public void unsetIsLiveview() {
    __isset_bit_vector.clear(__ISLIVEVIEW_ISSET_ID);
  }

  /** Returns true if field isLiveview is set (has been assigned a value) and false otherwise */
  public boolean isSetIsLiveview() {
    return __isset_bit_vector.get(__ISLIVEVIEW_ISSET_ID);
  }

  public void setIsLiveviewIsSet(boolean value) {
    __isset_bit_vector.set(__ISLIVEVIEW_ISSET_ID, value);
  }

  public String getType() {
    return this.type;
  }

  public StreamInfo setType(String type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public String getUrl() {
    return this.url;
  }

  public StreamInfo setUrl(String url) {
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

  public String getStartTime() {
    return this.startTime;
  }

  public StreamInfo setStartTime(String startTime) {
    this.startTime = startTime;
    return this;
  }

  public void unsetStartTime() {
    this.startTime = null;
  }

  /** Returns true if field startTime is set (has been assigned a value) and false otherwise */
  public boolean isSetStartTime() {
    return this.startTime != null;
  }

  public void setStartTimeIsSet(boolean value) {
    if (!value) {
      this.startTime = null;
    }
  }

  public int getClientsSize() {
    return (this.clients == null) ? 0 : this.clients.size();
  }

  public java.util.Iterator<StreamClientInfo> getClientsIterator() {
    return (this.clients == null) ? null : this.clients.iterator();
  }

  public void addToClients(StreamClientInfo elem) {
    if (this.clients == null) {
      this.clients = new ArrayList<StreamClientInfo>();
    }
    this.clients.add(elem);
  }

  public List<StreamClientInfo> getClients() {
    return this.clients;
  }

  public StreamInfo setClients(List<StreamClientInfo> clients) {
    this.clients = clients;
    return this;
  }

  public void unsetClients() {
    this.clients = null;
  }

  /** Returns true if field clients is set (has been assigned a value) and false otherwise */
  public boolean isSetClients() {
    return this.clients != null;
  }

  public void setClientsIsSet(boolean value) {
    if (!value) {
      this.clients = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DEVICE_ID:
      if (value == null) {
        unsetDeviceId();
      } else {
        setDeviceId((String)value);
      }
      break;

    case CHANNEL_ID:
      if (value == null) {
        unsetChannelId();
      } else {
        setChannelId((String)value);
      }
      break;

    case IS_LIVEVIEW:
      if (value == null) {
        unsetIsLiveview();
      } else {
        setIsLiveview((Boolean)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((String)value);
      }
      break;

    case URL:
      if (value == null) {
        unsetUrl();
      } else {
        setUrl((String)value);
      }
      break;

    case START_TIME:
      if (value == null) {
        unsetStartTime();
      } else {
        setStartTime((String)value);
      }
      break;

    case CLIENTS:
      if (value == null) {
        unsetClients();
      } else {
        setClients((List<StreamClientInfo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DEVICE_ID:
      return getDeviceId();

    case CHANNEL_ID:
      return getChannelId();

    case IS_LIVEVIEW:
      return Boolean.valueOf(isIsLiveview());

    case TYPE:
      return getType();

    case URL:
      return getUrl();

    case START_TIME:
      return getStartTime();

    case CLIENTS:
      return getClients();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DEVICE_ID:
      return isSetDeviceId();
    case CHANNEL_ID:
      return isSetChannelId();
    case IS_LIVEVIEW:
      return isSetIsLiveview();
    case TYPE:
      return isSetType();
    case URL:
      return isSetUrl();
    case START_TIME:
      return isSetStartTime();
    case CLIENTS:
      return isSetClients();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StreamInfo)
      return this.equals((StreamInfo)that);
    return false;
  }

  public boolean equals(StreamInfo that) {
    if (that == null)
      return false;

    boolean this_present_deviceId = true && this.isSetDeviceId();
    boolean that_present_deviceId = true && that.isSetDeviceId();
    if (this_present_deviceId || that_present_deviceId) {
      if (!(this_present_deviceId && that_present_deviceId))
        return false;
      if (!this.deviceId.equals(that.deviceId))
        return false;
    }

    boolean this_present_channelId = true && this.isSetChannelId();
    boolean that_present_channelId = true && that.isSetChannelId();
    if (this_present_channelId || that_present_channelId) {
      if (!(this_present_channelId && that_present_channelId))
        return false;
      if (!this.channelId.equals(that.channelId))
        return false;
    }

    boolean this_present_isLiveview = true;
    boolean that_present_isLiveview = true;
    if (this_present_isLiveview || that_present_isLiveview) {
      if (!(this_present_isLiveview && that_present_isLiveview))
        return false;
      if (this.isLiveview != that.isLiveview)
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
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

    boolean this_present_startTime = true && this.isSetStartTime();
    boolean that_present_startTime = true && that.isSetStartTime();
    if (this_present_startTime || that_present_startTime) {
      if (!(this_present_startTime && that_present_startTime))
        return false;
      if (!this.startTime.equals(that.startTime))
        return false;
    }

    boolean this_present_clients = true && this.isSetClients();
    boolean that_present_clients = true && that.isSetClients();
    if (this_present_clients || that_present_clients) {
      if (!(this_present_clients && that_present_clients))
        return false;
      if (!this.clients.equals(that.clients))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(StreamInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    StreamInfo typedOther = (StreamInfo)other;

    lastComparison = Boolean.valueOf(isSetDeviceId()).compareTo(typedOther.isSetDeviceId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDeviceId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.deviceId, typedOther.deviceId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChannelId()).compareTo(typedOther.isSetChannelId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChannelId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.channelId, typedOther.channelId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsLiveview()).compareTo(typedOther.isSetIsLiveview());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsLiveview()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isLiveview, typedOther.isLiveview);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, typedOther.type);
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
    lastComparison = Boolean.valueOf(isSetStartTime()).compareTo(typedOther.isSetStartTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startTime, typedOther.startTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetClients()).compareTo(typedOther.isSetClients());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClients()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.clients, typedOther.clients);
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
    StringBuilder sb = new StringBuilder("StreamInfo(");
    boolean first = true;

    sb.append("deviceId:");
    if (this.deviceId == null) {
      sb.append("null");
    } else {
      sb.append(this.deviceId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("channelId:");
    if (this.channelId == null) {
      sb.append("null");
    } else {
      sb.append(this.channelId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("isLiveview:");
    sb.append(this.isLiveview);
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
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
    sb.append("startTime:");
    if (this.startTime == null) {
      sb.append("null");
    } else {
      sb.append(this.startTime);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("clients:");
    if (this.clients == null) {
      sb.append("null");
    } else {
      sb.append(this.clients);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class StreamInfoStandardSchemeFactory implements SchemeFactory
  {
    public StreamInfoStandardScheme getScheme() {
      return new StreamInfoStandardScheme();
    }
  }

  private static class StreamInfoStandardScheme extends StandardScheme<StreamInfo>
  {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StreamInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DEVICE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.deviceId = iprot.readString();
              struct.setDeviceIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CHANNEL_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.channelId = iprot.readString();
              struct.setChannelIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IS_LIVEVIEW
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isLiveview = iprot.readBool();
              struct.setIsLiveviewIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
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
          case 6: // START_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.startTime = iprot.readString();
              struct.setStartTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // CLIENTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.clients = new ArrayList<StreamClientInfo>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  StreamClientInfo _elem2; // required
                  _elem2 = new StreamClientInfo();
                  _elem2.read(iprot);
                  struct.clients.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setClientsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StreamInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.deviceId != null) {
        oprot.writeFieldBegin(DEVICE_ID_FIELD_DESC);
        oprot.writeString(struct.deviceId);
        oprot.writeFieldEnd();
      }
      if (struct.channelId != null) {
        oprot.writeFieldBegin(CHANNEL_ID_FIELD_DESC);
        oprot.writeString(struct.channelId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(IS_LIVEVIEW_FIELD_DESC);
      oprot.writeBool(struct.isLiveview);
      oprot.writeFieldEnd();
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeString(struct.type);
        oprot.writeFieldEnd();
      }
      if (struct.url != null) {
        oprot.writeFieldBegin(URL_FIELD_DESC);
        oprot.writeString(struct.url);
        oprot.writeFieldEnd();
      }
      if (struct.startTime != null) {
        oprot.writeFieldBegin(START_TIME_FIELD_DESC);
        oprot.writeString(struct.startTime);
        oprot.writeFieldEnd();
      }
      if (struct.clients != null) {
        oprot.writeFieldBegin(CLIENTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.clients.size()));
          for (StreamClientInfo _iter3 : struct.clients)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StreamInfoTupleSchemeFactory implements SchemeFactory
  {
    public StreamInfoTupleScheme getScheme() {
      return new StreamInfoTupleScheme();
    }
  }

  private static class StreamInfoTupleScheme extends TupleScheme<StreamInfo>
  {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StreamInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetDeviceId()) {
        optionals.set(0);
      }
      if (struct.isSetChannelId()) {
        optionals.set(1);
      }
      if (struct.isSetIsLiveview()) {
        optionals.set(2);
      }
      if (struct.isSetType()) {
        optionals.set(3);
      }
      if (struct.isSetUrl()) {
        optionals.set(4);
      }
      if (struct.isSetStartTime()) {
        optionals.set(5);
      }
      if (struct.isSetClients()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetDeviceId()) {
        oprot.writeString(struct.deviceId);
      }
      if (struct.isSetChannelId()) {
        oprot.writeString(struct.channelId);
      }
      if (struct.isSetIsLiveview()) {
        oprot.writeBool(struct.isLiveview);
      }
      if (struct.isSetType()) {
        oprot.writeString(struct.type);
      }
      if (struct.isSetUrl()) {
        oprot.writeString(struct.url);
      }
      if (struct.isSetStartTime()) {
        oprot.writeString(struct.startTime);
      }
      if (struct.isSetClients()) {
        {
          oprot.writeI32(struct.clients.size());
          for (StreamClientInfo _iter4 : struct.clients)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StreamInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.deviceId = iprot.readString();
        struct.setDeviceIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.channelId = iprot.readString();
        struct.setChannelIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.isLiveview = iprot.readBool();
        struct.setIsLiveviewIsSet(true);
      }
      if (incoming.get(3)) {
        struct.type = iprot.readString();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.url = iprot.readString();
        struct.setUrlIsSet(true);
      }
      if (incoming.get(5)) {
        struct.startTime = iprot.readString();
        struct.setStartTimeIsSet(true);
      }
      if (incoming.get(6)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.clients = new ArrayList<StreamClientInfo>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            StreamClientInfo _elem7; // required
            _elem7 = new StreamClientInfo();
            _elem7.read(iprot);
            struct.clients.add(_elem7);
          }
        }
        struct.setClientsIsSet(true);
      }
    }
  }

}


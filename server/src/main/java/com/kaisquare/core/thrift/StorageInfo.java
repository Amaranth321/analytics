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
 * Recording Server Storage Status
 * (1) serverId - The recording server ID
 * (2) serverHost - The recording server host
 * (3) streamCount - The number of streams that server is processing
 * (4) freeSpace - The available disk space in this server. uint: MB
 * (5) totalSpace - The total disk space in this server. uint: MB
 */
public class StorageInfo implements org.apache.thrift.TBase<StorageInfo, StorageInfo._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StorageInfo");

  private static final org.apache.thrift.protocol.TField SERVER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("serverId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SERVER_HOST_FIELD_DESC = new org.apache.thrift.protocol.TField("serverHost", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField STREAM_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("streamCount", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField FREE_SPACE_FIELD_DESC = new org.apache.thrift.protocol.TField("freeSpace", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField TOTAL_SPACE_FIELD_DESC = new org.apache.thrift.protocol.TField("totalSpace", org.apache.thrift.protocol.TType.I64, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StorageInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StorageInfoTupleSchemeFactory());
  }

  private String serverId; // required
  private String serverHost; // required
  private long streamCount; // required
  private long freeSpace; // required
  private long totalSpace; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SERVER_ID((short)1, "serverId"),
    SERVER_HOST((short)2, "serverHost"),
    STREAM_COUNT((short)3, "streamCount"),
    FREE_SPACE((short)4, "freeSpace"),
    TOTAL_SPACE((short)5, "totalSpace");

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
        case 2: // SERVER_HOST
          return SERVER_HOST;
        case 3: // STREAM_COUNT
          return STREAM_COUNT;
        case 4: // FREE_SPACE
          return FREE_SPACE;
        case 5: // TOTAL_SPACE
          return TOTAL_SPACE;
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
  private static final int __STREAMCOUNT_ISSET_ID = 0;
  private static final int __FREESPACE_ISSET_ID = 1;
  private static final int __TOTALSPACE_ISSET_ID = 2;
  private BitSet __isset_bit_vector = new BitSet(3);
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SERVER_ID, new org.apache.thrift.meta_data.FieldMetaData("serverId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SERVER_HOST, new org.apache.thrift.meta_data.FieldMetaData("serverHost", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.STREAM_COUNT, new org.apache.thrift.meta_data.FieldMetaData("streamCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.FREE_SPACE, new org.apache.thrift.meta_data.FieldMetaData("freeSpace", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TOTAL_SPACE, new org.apache.thrift.meta_data.FieldMetaData("totalSpace", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StorageInfo.class, metaDataMap);
  }

  public StorageInfo() {
  }

  public StorageInfo(
    String serverId,
    String serverHost,
    long streamCount,
    long freeSpace,
    long totalSpace)
  {
    this();
    this.serverId = serverId;
    this.serverHost = serverHost;
    this.streamCount = streamCount;
    setStreamCountIsSet(true);
    this.freeSpace = freeSpace;
    setFreeSpaceIsSet(true);
    this.totalSpace = totalSpace;
    setTotalSpaceIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StorageInfo(StorageInfo other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetServerId()) {
      this.serverId = other.serverId;
    }
    if (other.isSetServerHost()) {
      this.serverHost = other.serverHost;
    }
    this.streamCount = other.streamCount;
    this.freeSpace = other.freeSpace;
    this.totalSpace = other.totalSpace;
  }

  public StorageInfo deepCopy() {
    return new StorageInfo(this);
  }

  @Override
  public void clear() {
    this.serverId = null;
    this.serverHost = null;
    setStreamCountIsSet(false);
    this.streamCount = 0;
    setFreeSpaceIsSet(false);
    this.freeSpace = 0;
    setTotalSpaceIsSet(false);
    this.totalSpace = 0;
  }

  public String getServerId() {
    return this.serverId;
  }

  public StorageInfo setServerId(String serverId) {
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

  public String getServerHost() {
    return this.serverHost;
  }

  public StorageInfo setServerHost(String serverHost) {
    this.serverHost = serverHost;
    return this;
  }

  public void unsetServerHost() {
    this.serverHost = null;
  }

  /** Returns true if field serverHost is set (has been assigned a value) and false otherwise */
  public boolean isSetServerHost() {
    return this.serverHost != null;
  }

  public void setServerHostIsSet(boolean value) {
    if (!value) {
      this.serverHost = null;
    }
  }

  public long getStreamCount() {
    return this.streamCount;
  }

  public StorageInfo setStreamCount(long streamCount) {
    this.streamCount = streamCount;
    setStreamCountIsSet(true);
    return this;
  }

  public void unsetStreamCount() {
    __isset_bit_vector.clear(__STREAMCOUNT_ISSET_ID);
  }

  /** Returns true if field streamCount is set (has been assigned a value) and false otherwise */
  public boolean isSetStreamCount() {
    return __isset_bit_vector.get(__STREAMCOUNT_ISSET_ID);
  }

  public void setStreamCountIsSet(boolean value) {
    __isset_bit_vector.set(__STREAMCOUNT_ISSET_ID, value);
  }

  public long getFreeSpace() {
    return this.freeSpace;
  }

  public StorageInfo setFreeSpace(long freeSpace) {
    this.freeSpace = freeSpace;
    setFreeSpaceIsSet(true);
    return this;
  }

  public void unsetFreeSpace() {
    __isset_bit_vector.clear(__FREESPACE_ISSET_ID);
  }

  /** Returns true if field freeSpace is set (has been assigned a value) and false otherwise */
  public boolean isSetFreeSpace() {
    return __isset_bit_vector.get(__FREESPACE_ISSET_ID);
  }

  public void setFreeSpaceIsSet(boolean value) {
    __isset_bit_vector.set(__FREESPACE_ISSET_ID, value);
  }

  public long getTotalSpace() {
    return this.totalSpace;
  }

  public StorageInfo setTotalSpace(long totalSpace) {
    this.totalSpace = totalSpace;
    setTotalSpaceIsSet(true);
    return this;
  }

  public void unsetTotalSpace() {
    __isset_bit_vector.clear(__TOTALSPACE_ISSET_ID);
  }

  /** Returns true if field totalSpace is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalSpace() {
    return __isset_bit_vector.get(__TOTALSPACE_ISSET_ID);
  }

  public void setTotalSpaceIsSet(boolean value) {
    __isset_bit_vector.set(__TOTALSPACE_ISSET_ID, value);
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

    case SERVER_HOST:
      if (value == null) {
        unsetServerHost();
      } else {
        setServerHost((String)value);
      }
      break;

    case STREAM_COUNT:
      if (value == null) {
        unsetStreamCount();
      } else {
        setStreamCount((Long)value);
      }
      break;

    case FREE_SPACE:
      if (value == null) {
        unsetFreeSpace();
      } else {
        setFreeSpace((Long)value);
      }
      break;

    case TOTAL_SPACE:
      if (value == null) {
        unsetTotalSpace();
      } else {
        setTotalSpace((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SERVER_ID:
      return getServerId();

    case SERVER_HOST:
      return getServerHost();

    case STREAM_COUNT:
      return Long.valueOf(getStreamCount());

    case FREE_SPACE:
      return Long.valueOf(getFreeSpace());

    case TOTAL_SPACE:
      return Long.valueOf(getTotalSpace());

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
    case SERVER_HOST:
      return isSetServerHost();
    case STREAM_COUNT:
      return isSetStreamCount();
    case FREE_SPACE:
      return isSetFreeSpace();
    case TOTAL_SPACE:
      return isSetTotalSpace();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StorageInfo)
      return this.equals((StorageInfo)that);
    return false;
  }

  public boolean equals(StorageInfo that) {
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

    boolean this_present_serverHost = true && this.isSetServerHost();
    boolean that_present_serverHost = true && that.isSetServerHost();
    if (this_present_serverHost || that_present_serverHost) {
      if (!(this_present_serverHost && that_present_serverHost))
        return false;
      if (!this.serverHost.equals(that.serverHost))
        return false;
    }

    boolean this_present_streamCount = true;
    boolean that_present_streamCount = true;
    if (this_present_streamCount || that_present_streamCount) {
      if (!(this_present_streamCount && that_present_streamCount))
        return false;
      if (this.streamCount != that.streamCount)
        return false;
    }

    boolean this_present_freeSpace = true;
    boolean that_present_freeSpace = true;
    if (this_present_freeSpace || that_present_freeSpace) {
      if (!(this_present_freeSpace && that_present_freeSpace))
        return false;
      if (this.freeSpace != that.freeSpace)
        return false;
    }

    boolean this_present_totalSpace = true;
    boolean that_present_totalSpace = true;
    if (this_present_totalSpace || that_present_totalSpace) {
      if (!(this_present_totalSpace && that_present_totalSpace))
        return false;
      if (this.totalSpace != that.totalSpace)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(StorageInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    StorageInfo typedOther = (StorageInfo)other;

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
    lastComparison = Boolean.valueOf(isSetServerHost()).compareTo(typedOther.isSetServerHost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServerHost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serverHost, typedOther.serverHost);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStreamCount()).compareTo(typedOther.isSetStreamCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStreamCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.streamCount, typedOther.streamCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFreeSpace()).compareTo(typedOther.isSetFreeSpace());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFreeSpace()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.freeSpace, typedOther.freeSpace);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalSpace()).compareTo(typedOther.isSetTotalSpace());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalSpace()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalSpace, typedOther.totalSpace);
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
    StringBuilder sb = new StringBuilder("StorageInfo(");
    boolean first = true;

    sb.append("serverId:");
    if (this.serverId == null) {
      sb.append("null");
    } else {
      sb.append(this.serverId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("serverHost:");
    if (this.serverHost == null) {
      sb.append("null");
    } else {
      sb.append(this.serverHost);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("streamCount:");
    sb.append(this.streamCount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("freeSpace:");
    sb.append(this.freeSpace);
    first = false;
    if (!first) sb.append(", ");
    sb.append("totalSpace:");
    sb.append(this.totalSpace);
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

  private static class StorageInfoStandardSchemeFactory implements SchemeFactory
  {
    public StorageInfoStandardScheme getScheme() {
      return new StorageInfoStandardScheme();
    }
  }

  private static class StorageInfoStandardScheme extends StandardScheme<StorageInfo>
  {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StorageInfo struct) throws org.apache.thrift.TException {
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
          case 2: // SERVER_HOST
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.serverHost = iprot.readString();
              struct.setServerHostIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // STREAM_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.streamCount = iprot.readI64();
              struct.setStreamCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FREE_SPACE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.freeSpace = iprot.readI64();
              struct.setFreeSpaceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TOTAL_SPACE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.totalSpace = iprot.readI64();
              struct.setTotalSpaceIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StorageInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.serverId != null) {
        oprot.writeFieldBegin(SERVER_ID_FIELD_DESC);
        oprot.writeString(struct.serverId);
        oprot.writeFieldEnd();
      }
      if (struct.serverHost != null) {
        oprot.writeFieldBegin(SERVER_HOST_FIELD_DESC);
        oprot.writeString(struct.serverHost);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(STREAM_COUNT_FIELD_DESC);
      oprot.writeI64(struct.streamCount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FREE_SPACE_FIELD_DESC);
      oprot.writeI64(struct.freeSpace);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TOTAL_SPACE_FIELD_DESC);
      oprot.writeI64(struct.totalSpace);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StorageInfoTupleSchemeFactory implements SchemeFactory
  {
    public StorageInfoTupleScheme getScheme() {
      return new StorageInfoTupleScheme();
    }
  }

  private static class StorageInfoTupleScheme extends TupleScheme<StorageInfo>
  {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StorageInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetServerId()) {
        optionals.set(0);
      }
      if (struct.isSetServerHost()) {
        optionals.set(1);
      }
      if (struct.isSetStreamCount()) {
        optionals.set(2);
      }
      if (struct.isSetFreeSpace()) {
        optionals.set(3);
      }
      if (struct.isSetTotalSpace()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetServerId()) {
        oprot.writeString(struct.serverId);
      }
      if (struct.isSetServerHost()) {
        oprot.writeString(struct.serverHost);
      }
      if (struct.isSetStreamCount()) {
        oprot.writeI64(struct.streamCount);
      }
      if (struct.isSetFreeSpace()) {
        oprot.writeI64(struct.freeSpace);
      }
      if (struct.isSetTotalSpace()) {
        oprot.writeI64(struct.totalSpace);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StorageInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.serverId = iprot.readString();
        struct.setServerIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.serverHost = iprot.readString();
        struct.setServerHostIsSet(true);
      }
      if (incoming.get(2)) {
        struct.streamCount = iprot.readI64();
        struct.setStreamCountIsSet(true);
      }
      if (incoming.get(3)) {
        struct.freeSpace = iprot.readI64();
        struct.setFreeSpaceIsSet(true);
      }
      if (incoming.get(4)) {
        struct.totalSpace = iprot.readI64();
        struct.setTotalSpaceIsSet(true);
      }
    }
  }

}


// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ProtobufTest.proto

package com.dapp.lan.protobuf;

public final class ProtobufTestProto {
  private ProtobufTestProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ProtobufTestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.dapp.server.protobuf.ProtobufTest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 MESSAGE_HEADER_LENGTH = 1;</code>
     * @return The mESSAGEHEADERLENGTH.
     */
    int getMESSAGEHEADERLENGTH();

    /**
     * <code>int32 code = 2;</code>
     * @return The code.
     */
    int getCode();

    /**
     * <code>int32 tag = 3;</code>
     * @return The tag.
     */
    int getTag();

    /**
     * <code>bytes data = 4;</code>
     * @return The data.
     */
    com.google.protobuf.ByteString getData();
  }
  /**
   * Protobuf type {@code com.dapp.server.protobuf.ProtobufTest}
   */
  public static final class ProtobufTest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.dapp.server.protobuf.ProtobufTest)
      ProtobufTestOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ProtobufTest.newBuilder() to construct.
    private ProtobufTest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ProtobufTest() {
      data_ = com.google.protobuf.ByteString.EMPTY;
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new ProtobufTest();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ProtobufTest(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              mESSAGEHEADERLENGTH_ = input.readInt32();
              break;
            }
            case 16: {

              code_ = input.readInt32();
              break;
            }
            case 24: {

              tag_ = input.readInt32();
              break;
            }
            case 34: {

              data_ = input.readBytes();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return ProtobufTestProto.internal_static_com_dapp_server_protobuf_ProtobufTest_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return ProtobufTestProto.internal_static_com_dapp_server_protobuf_ProtobufTest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ProtobufTest.class, Builder.class);
    }

    public static final int MESSAGE_HEADER_LENGTH_FIELD_NUMBER = 1;
    private int mESSAGEHEADERLENGTH_;
    /**
     * <code>int32 MESSAGE_HEADER_LENGTH = 1;</code>
     * @return The mESSAGEHEADERLENGTH.
     */
    @Override
    public int getMESSAGEHEADERLENGTH() {
      return mESSAGEHEADERLENGTH_;
    }

    public static final int CODE_FIELD_NUMBER = 2;
    private int code_;
    /**
     * <code>int32 code = 2;</code>
     * @return The code.
     */
    @Override
    public int getCode() {
      return code_;
    }

    public static final int TAG_FIELD_NUMBER = 3;
    private int tag_;
    /**
     * <code>int32 tag = 3;</code>
     * @return The tag.
     */
    @Override
    public int getTag() {
      return tag_;
    }

    public static final int DATA_FIELD_NUMBER = 4;
    private com.google.protobuf.ByteString data_;
    /**
     * <code>bytes data = 4;</code>
     * @return The data.
     */
    @Override
    public com.google.protobuf.ByteString getData() {
      return data_;
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (mESSAGEHEADERLENGTH_ != 0) {
        output.writeInt32(1, mESSAGEHEADERLENGTH_);
      }
      if (code_ != 0) {
        output.writeInt32(2, code_);
      }
      if (tag_ != 0) {
        output.writeInt32(3, tag_);
      }
      if (!data_.isEmpty()) {
        output.writeBytes(4, data_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (mESSAGEHEADERLENGTH_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, mESSAGEHEADERLENGTH_);
      }
      if (code_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, code_);
      }
      if (tag_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, tag_);
      }
      if (!data_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(4, data_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ProtobufTest)) {
        return super.equals(obj);
      }
      ProtobufTest other = (ProtobufTest) obj;

      if (getMESSAGEHEADERLENGTH()
          != other.getMESSAGEHEADERLENGTH()) return false;
      if (getCode()
          != other.getCode()) return false;
      if (getTag()
          != other.getTag()) return false;
      if (!getData()
          .equals(other.getData())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + MESSAGE_HEADER_LENGTH_FIELD_NUMBER;
      hash = (53 * hash) + getMESSAGEHEADERLENGTH();
      hash = (37 * hash) + CODE_FIELD_NUMBER;
      hash = (53 * hash) + getCode();
      hash = (37 * hash) + TAG_FIELD_NUMBER;
      hash = (53 * hash) + getTag();
      hash = (37 * hash) + DATA_FIELD_NUMBER;
      hash = (53 * hash) + getData().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ProtobufTest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ProtobufTest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ProtobufTest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ProtobufTest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ProtobufTest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ProtobufTest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ProtobufTest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ProtobufTest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static ProtobufTest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static ProtobufTest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ProtobufTest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ProtobufTest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(ProtobufTest prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.dapp.server.protobuf.ProtobufTest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.dapp.server.protobuf.ProtobufTest)
        ProtobufTestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return ProtobufTestProto.internal_static_com_dapp_server_protobuf_ProtobufTest_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return ProtobufTestProto.internal_static_com_dapp_server_protobuf_ProtobufTest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ProtobufTest.class, Builder.class);
      }

      // Construct using com.dapp.server.protobuf.ProtobufTestProto.ProtobufTest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        mESSAGEHEADERLENGTH_ = 0;

        code_ = 0;

        tag_ = 0;

        data_ = com.google.protobuf.ByteString.EMPTY;

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return ProtobufTestProto.internal_static_com_dapp_server_protobuf_ProtobufTest_descriptor;
      }

      @Override
      public ProtobufTest getDefaultInstanceForType() {
        return ProtobufTest.getDefaultInstance();
      }

      @Override
      public ProtobufTest build() {
        ProtobufTest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public ProtobufTest buildPartial() {
        ProtobufTest result = new ProtobufTest(this);
        result.mESSAGEHEADERLENGTH_ = mESSAGEHEADERLENGTH_;
        result.code_ = code_;
        result.tag_ = tag_;
        result.data_ = data_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof ProtobufTest) {
          return mergeFrom((ProtobufTest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ProtobufTest other) {
        if (other == ProtobufTest.getDefaultInstance()) return this;
        if (other.getMESSAGEHEADERLENGTH() != 0) {
          setMESSAGEHEADERLENGTH(other.getMESSAGEHEADERLENGTH());
        }
        if (other.getCode() != 0) {
          setCode(other.getCode());
        }
        if (other.getTag() != 0) {
          setTag(other.getTag());
        }
        if (other.getData() != com.google.protobuf.ByteString.EMPTY) {
          setData(other.getData());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        ProtobufTest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (ProtobufTest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int mESSAGEHEADERLENGTH_ ;
      /**
       * <code>int32 MESSAGE_HEADER_LENGTH = 1;</code>
       * @return The mESSAGEHEADERLENGTH.
       */
      @Override
      public int getMESSAGEHEADERLENGTH() {
        return mESSAGEHEADERLENGTH_;
      }
      /**
       * <code>int32 MESSAGE_HEADER_LENGTH = 1;</code>
       * @param value The mESSAGEHEADERLENGTH to set.
       * @return This builder for chaining.
       */
      public Builder setMESSAGEHEADERLENGTH(int value) {
        
        mESSAGEHEADERLENGTH_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 MESSAGE_HEADER_LENGTH = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearMESSAGEHEADERLENGTH() {
        
        mESSAGEHEADERLENGTH_ = 0;
        onChanged();
        return this;
      }

      private int code_ ;
      /**
       * <code>int32 code = 2;</code>
       * @return The code.
       */
      @Override
      public int getCode() {
        return code_;
      }
      /**
       * <code>int32 code = 2;</code>
       * @param value The code to set.
       * @return This builder for chaining.
       */
      public Builder setCode(int value) {
        
        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 code = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearCode() {
        
        code_ = 0;
        onChanged();
        return this;
      }

      private int tag_ ;
      /**
       * <code>int32 tag = 3;</code>
       * @return The tag.
       */
      @Override
      public int getTag() {
        return tag_;
      }
      /**
       * <code>int32 tag = 3;</code>
       * @param value The tag to set.
       * @return This builder for chaining.
       */
      public Builder setTag(int value) {
        
        tag_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 tag = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearTag() {
        
        tag_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>bytes data = 4;</code>
       * @return The data.
       */
      @Override
      public com.google.protobuf.ByteString getData() {
        return data_;
      }
      /**
       * <code>bytes data = 4;</code>
       * @param value The data to set.
       * @return This builder for chaining.
       */
      public Builder setData(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>bytes data = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearData() {
        
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:com.dapp.server.protobuf.ProtobufTest)
    }

    // @@protoc_insertion_point(class_scope:com.dapp.server.protobuf.ProtobufTest)
    private static final ProtobufTest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ProtobufTest();
    }

    public static ProtobufTest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ProtobufTest>
        PARSER = new com.google.protobuf.AbstractParser<ProtobufTest>() {
      @Override
      public ProtobufTest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ProtobufTest(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ProtobufTest> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<ProtobufTest> getParserForType() {
      return PARSER;
    }

    @Override
    public ProtobufTest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_dapp_server_protobuf_ProtobufTest_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_dapp_server_protobuf_ProtobufTest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\022ProtobufTest.proto\022\030com.dapp.server.pr" +
      "otobuf\"V\n\014ProtobufTest\022\035\n\025MESSAGE_HEADER" +
      "_LENGTH\030\001 \001(\005\022\014\n\004code\030\002 \001(\005\022\013\n\003tag\030\003 \001(\005" +
      "\022\014\n\004data\030\004 \001(\014B-\n\030com.dapp.server.protob" +
      "ufB\021ProtobufTestProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_dapp_server_protobuf_ProtobufTest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_dapp_server_protobuf_ProtobufTest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_dapp_server_protobuf_ProtobufTest_descriptor,
        new String[] { "MESSAGEHEADERLENGTH", "Code", "Tag", "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

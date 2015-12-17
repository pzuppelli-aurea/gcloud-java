// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/pubsub/v1/pubsub.proto

package com.google.pubsub.v1;

public interface ListTopicSubscriptionsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.pubsub.v1.ListTopicSubscriptionsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional string topic = 1;</code>
   *
   * <pre>
   * The name of the topic that subscriptions are attached to.
   * </pre>
   */
  java.lang.String getTopic();
  /**
   * <code>optional string topic = 1;</code>
   *
   * <pre>
   * The name of the topic that subscriptions are attached to.
   * </pre>
   */
  com.google.protobuf.ByteString
      getTopicBytes();

  /**
   * <code>optional int32 page_size = 2;</code>
   *
   * <pre>
   * Maximum number of subscription names to return.
   * </pre>
   */
  int getPageSize();

  /**
   * <code>optional string page_token = 3;</code>
   *
   * <pre>
   * The value returned by the last ListTopicSubscriptionsResponse; indicates
   * that this is a continuation of a prior ListTopicSubscriptions call, and
   * that the system should return the next page of data.
   * </pre>
   */
  java.lang.String getPageToken();
  /**
   * <code>optional string page_token = 3;</code>
   *
   * <pre>
   * The value returned by the last ListTopicSubscriptionsResponse; indicates
   * that this is a continuation of a prior ListTopicSubscriptions call, and
   * that the system should return the next page of data.
   * </pre>
   */
  com.google.protobuf.ByteString
      getPageTokenBytes();
}
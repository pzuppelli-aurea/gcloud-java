// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/logging/v2/logging.proto

package com.google.logging.v2;

public interface ReadLogEntriesRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.logging.v2.ReadLogEntriesRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated string project_ids = 1;</code>
   *
   * <pre>
   * Required. A list of project ids from which to retrieve log entries.
   * Example: `"my-project-id"`.
   * </pre>
   */
  com.google.protobuf.ProtocolStringList
      getProjectIdsList();
  /**
   * <code>repeated string project_ids = 1;</code>
   *
   * <pre>
   * Required. A list of project ids from which to retrieve log entries.
   * Example: `"my-project-id"`.
   * </pre>
   */
  int getProjectIdsCount();
  /**
   * <code>repeated string project_ids = 1;</code>
   *
   * <pre>
   * Required. A list of project ids from which to retrieve log entries.
   * Example: `"my-project-id"`.
   * </pre>
   */
  java.lang.String getProjectIds(int index);
  /**
   * <code>repeated string project_ids = 1;</code>
   *
   * <pre>
   * Required. A list of project ids from which to retrieve log entries.
   * Example: `"my-project-id"`.
   * </pre>
   */
  com.google.protobuf.ByteString
      getProjectIdsBytes(int index);

  /**
   * <code>optional string filter = 2;</code>
   *
   * <pre>
   * Optional. An [advanced logs filter](/logging/docs/view/advanced_filters).
   * The response includes only entries that match the filter.
   * If `filter` is empty, then all entries in all logs are retrieved.
   * </pre>
   */
  java.lang.String getFilter();
  /**
   * <code>optional string filter = 2;</code>
   *
   * <pre>
   * Optional. An [advanced logs filter](/logging/docs/view/advanced_filters).
   * The response includes only entries that match the filter.
   * If `filter` is empty, then all entries in all logs are retrieved.
   * </pre>
   */
  com.google.protobuf.ByteString
      getFilterBytes();

  /**
   * <code>optional string order_by = 3;</code>
   *
   * <pre>
   * Optional. How the results should be sorted.  Presently, the only permitted
   * values are `"timestamp"` (default) and `"timestamp desc"`.  The first
   * option returns entries in order of increasing values of
   * `LogEntry.timestamp` (oldest first), and the second option returns entries
   * in order of decreasing timestamps (newest first).  Entries with equal
   * timestamps will be returned in order of `LogEntry.insertId`.
   * </pre>
   */
  java.lang.String getOrderBy();
  /**
   * <code>optional string order_by = 3;</code>
   *
   * <pre>
   * Optional. How the results should be sorted.  Presently, the only permitted
   * values are `"timestamp"` (default) and `"timestamp desc"`.  The first
   * option returns entries in order of increasing values of
   * `LogEntry.timestamp` (oldest first), and the second option returns entries
   * in order of decreasing timestamps (newest first).  Entries with equal
   * timestamps will be returned in order of `LogEntry.insertId`.
   * </pre>
   */
  com.google.protobuf.ByteString
      getOrderByBytes();

  /**
   * <code>optional string resume_token = 4;</code>
   *
   * <pre>
   * Optional. If the `resumeToken` request parameter is supplied, then the next
   * page of results in the set are retrieved.  The `resumeToken` request
   * parameter must be set with the value of the `resumeToken` result parameter
   * from the previous request.
   * </pre>
   */
  java.lang.String getResumeToken();
  /**
   * <code>optional string resume_token = 4;</code>
   *
   * <pre>
   * Optional. If the `resumeToken` request parameter is supplied, then the next
   * page of results in the set are retrieved.  The `resumeToken` request
   * parameter must be set with the value of the `resumeToken` result parameter
   * from the previous request.
   * </pre>
   */
  com.google.protobuf.ByteString
      getResumeTokenBytes();
}
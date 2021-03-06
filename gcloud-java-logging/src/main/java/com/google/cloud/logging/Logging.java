/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.logging;

import com.google.cloud.AsyncPage;
import com.google.cloud.MonitoredResource;
import com.google.cloud.MonitoredResourceDescriptor;
import com.google.cloud.Page;
import com.google.cloud.Service;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.concurrent.Future;

public interface Logging extends AutoCloseable, Service<LoggingOptions> {

  /**
   * Class for specifying options for listing sinks, monitored resources and monitored resource
   * descriptors.
   */
  final class ListOption extends Option {

    private static final long serialVersionUID = -6857294816115909271L;

    enum OptionType implements Option.OptionType {
      PAGE_SIZE, PAGE_TOKEN;

      @SuppressWarnings("unchecked")
      <T> T get(Map<Option.OptionType, ?> options) {
        return (T) options.get(this);
      }
    }

    private ListOption(OptionType option, Object value) {
      super(option, value);
    }

    /**
     * Returns an option to specify the maximum number of resources returned per page.
     */
    public static ListOption pageSize(int pageSize) {
      return new ListOption(OptionType.PAGE_SIZE, pageSize);
    }

    /**
     * Returns an option to specify the page token from which to start listing resources.
     */
    public static ListOption pageToken(String pageToken) {
      return new ListOption(OptionType.PAGE_TOKEN, pageToken);
    }
  }

  /**
   * Class for specifying options for writing log entries.
   */
  final class WriteOption extends Option {

    private static final long serialVersionUID = 715900132268584612L;

    enum OptionType implements Option.OptionType {
      LOG_NAME, RESOURCE, LABELS;

      @SuppressWarnings("unchecked")
      <T> T get(Map<Option.OptionType, ?> options) {
        return (T) options.get(this);
      }
    }

    private WriteOption(OptionType option, Object value) {
      super(option, value);
    }

    /**
     * Returns an option to specify a default log name (see {@link LogEntry#logName()}) for those
     * log entries that do not specify their own log name. Example: {@code syslog}.
     */
    public static WriteOption logName(String logName) {
      return new WriteOption(OptionType.LOG_NAME, logName);
    }

    /**
     * Returns an option to specify a default monitored resource (see {@link LogEntry#resource()})
     * for those log entries that do not specify their own resource.
     */
    public static WriteOption resource(MonitoredResource resource) {
      return new WriteOption(OptionType.RESOURCE, resource);
    }

    /**
     * Sets an option to specify (key, value) pairs that are added to the {@link LogEntry#labels()}
     * of each log entry written, except when a log entry already has a value associated to the
     * same key.
     */
    public static WriteOption labels(Map<String, String> labels) {
      return new WriteOption(OptionType.LABELS, ImmutableMap.copyOf(labels));
    }
  }

  /**
   * Fields according to which log entries can be sorted.
   */
  enum SortingField {
    TIMESTAMP;

    String selector() {
      return name().toLowerCase();
    }
  }

  /**
   * Sorting orders available when listing log entries.
   */
  enum SortingOrder {
    DESCENDING("desc"),
    ASCENDING("asc");

    private final String selector;

    SortingOrder(String selector) {
      this.selector = selector;
    }

    String selector() {
      return selector;
    }
  }

  /**
   * Class for specifying options for listing log entries.
   */
  final class EntryListOption extends Option {

    private static final long serialVersionUID = -1561159676386917050L;

    enum OptionType implements Option.OptionType {
      PAGE_SIZE, PAGE_TOKEN, ORDER_BY, FILTER;

      @SuppressWarnings("unchecked")
      <T> T get(Map<Option.OptionType, ?> options) {
        return (T) options.get(this);
      }
    }

    private EntryListOption(OptionType option, Object value) {
      super(option, value);
    }

    /**
     * Returns an option to specify the maximum number of log entries returned per page.
     */
    public static EntryListOption pageSize(int pageSize) {
      return new EntryListOption(OptionType.PAGE_SIZE, pageSize);
    }

    /**
     * Returns an option to specify the page token from which to start listing log entries.
     */
    public static EntryListOption pageToken(String pageToken) {
      return new EntryListOption(OptionType.PAGE_TOKEN, pageToken);
    }

    /**
     * Returns an option to sort log entries. If not specified, log entries are sorted in ascending
     * (most-recent last) order with respect to the {@link LogEntry#timestamp()} value.
     */
    public static EntryListOption sortOrder(SortingField field, SortingOrder order) {
      return new EntryListOption(OptionType.ORDER_BY, field.selector() + ' ' + order.selector());
    }

    /**
     * Returns an option to specify a filter to the log entries to be listed.
     *
     * @see <a href="https://cloud.google.com/logging/docs/view/advanced_filters">Advanced Logs
     *     Filters</a>
     */
    public static EntryListOption filter(String filter) {
      return new EntryListOption(OptionType.FILTER, filter);
    }
  }

  /**
   * Creates a new sink.
   *
   * @return the created sink
   * @throws LoggingException upon failure
   */
  Sink create(SinkInfo sink);

  /**
   * Sends a request for creating a sink. This method returns a {@code Future} object to consume the
   * result. {@link Future#get()} returns the created sink.
   */
  Future<Sink> createAsync(SinkInfo sink);

  /**
   * Updates a sink or creates one if it does not exist.
   *
   * @return the created sink
   * @throws LoggingException upon failure
   */
  Sink update(SinkInfo sink);

  /**
   * Sends a request for updating a sink (or creating it, if it does not exist). This method returns
   * a {@code Future} object to consume the result. {@link Future#get()} returns the
   * updated/created sink or {@code null} if not found.
   */
  Future<Sink> updateAsync(SinkInfo sink);

  /**
   * Returns the requested sink or {@code null} if not found.
   *
   * @throws LoggingException upon failure
   */
  Sink getSink(String sink);

  /**
   * Sends a request for getting a sink. This method returns a {@code Future} object to consume the
   * result. {@link Future#get()} returns the requested sink or {@code null} if not found.
   *
   * @throws LoggingException upon failure
   */
  Future<Sink> getSinkAsync(String sink);

  /**
   * Lists the sinks. This method returns a {@link Page} object that can be used to consume
   * paginated results. Use {@link ListOption} to specify the page size or the page token from which
   * to start listing sinks.
   *
   * @throws LoggingException upon failure
   */
  Page<Sink> listSinks(ListOption... options);

  /**
   * Sends a request for listing sinks. This method returns a {@code Future} object to consume
   * the result. {@link Future#get()} returns an {@link AsyncPage} object that can be used to
   * asynchronously handle paginated results. Use {@link ListOption} to specify the page size or the
   * page token from which to start listing sinks.
   */
  Future<AsyncPage<Sink>> listSinksAsync(ListOption... options);

  /**
   * Deletes the requested sink.
   *
   * @return {@code true} if the sink was deleted, {@code false} if it was not found
   */
  boolean deleteSink(String sink);

  /**
   * Sends a request for deleting a sink. This method returns a {@code Future} object to consume the
   * result. {@link Future#get()} returns {@code true} if the sink was deleted, {@code false} if it
   * was not found.
   */
  Future<Boolean> deleteSinkAsync(String sink);

  /**
   * Deletes a log and all its log entries. The log will reappear if new entries are written to it.
   *
   * @return {@code true} if the log was deleted, {@code false} if it was not found
   */
  boolean deleteLog(String log);

  /**
   * Sends a request for deleting a log and all its log entries. This method returns a
   * {@code Future} object to consume the result. {@link Future#get()} returns {@code true} if the
   * log was deleted, {@code false} if it was not found.
   */
  Future<Boolean> deleteLogAsync(String log);

  /**
   * Lists the monitored resource descriptors used by Stackdriver Logging. This method returns a
   * {@link Page} object that can be used to consume paginated results. Use {@link ListOption} to
   * specify the page size or the page token from which to start listing resource descriptors.
   *
   * @throws LoggingException upon failure
   */
  Page<MonitoredResourceDescriptor> listMonitoredResourceDescriptors(ListOption... options);

  /**
   * Sends a request for listing monitored resource descriptors used by Stackdriver Logging. This
   * method returns a {@code Future} object to consume the result. {@link Future#get()} returns an
   * {@link AsyncPage} object that can be used to asynchronously handle paginated results. Use
   * {@link ListOption} to specify the page size or the page token from which to start listing
   * resource descriptors.
   */
  Future<AsyncPage<MonitoredResourceDescriptor>> listMonitoredResourceDescriptorsAsync(
      ListOption... options);

  /**
   * Creates a new metric.
   *
   * @return the created metric
   * @throws LoggingException upon failure
   */
  Metric create(MetricInfo metric);

  /**
   * Sends a request for creating a metric. This method returns a {@code Future} object to consume
   * the result. {@link Future#get()} returns the created metric.
   */
  Future<Metric> createAsync(MetricInfo metric);

  /**
   * Updates a metric or creates one if it does not exist.
   *
   * @return the created metric
   * @throws LoggingException upon failure
   */
  Metric update(MetricInfo metric);

  /**
   * Sends a request for updating a metric (or creating it, if it does not exist). This method
   * returns a {@code Future} object to consume the result. {@link Future#get()} returns the
   * updated/created metric or {@code null} if not found.
   */
  Future<Metric> updateAsync(MetricInfo metric);

  /**
   * Returns the requested metric or {@code null} if not found.
   *
   * @throws LoggingException upon failure
   */
  Metric getMetric(String metric);

  /**
   * Sends a request for getting a metric. This method returns a {@code Future} object to consume
   * the result. {@link Future#get()} returns the requested metric or {@code null} if not found.
   *
   * @throws LoggingException upon failure
   */
  Future<Metric> getMetricAsync(String metric);

  /**
   * Lists the metrics. This method returns a {@link Page} object that can be used to consume
   * paginated results. Use {@link ListOption} to specify the page size or the page token from which
   * to start listing metrics.
   *
   * @throws LoggingException upon failure
   */
  Page<Metric> listMetrics(ListOption... options);

  /**
   * Sends a request for listing metrics. This method returns a {@code Future} object to consume
   * the result. {@link Future#get()} returns an {@link AsyncPage} object that can be used to
   * asynchronously handle paginated results. Use {@link ListOption} to specify the page size or the
   * page token from which to start listing metrics.
   */
  Future<AsyncPage<Metric>> listMetricsAsync(ListOption... options);

  /**
   * Deletes the requested metric.
   *
   * @return {@code true} if the metric was deleted, {@code false} if it was not found
   */
  boolean deleteMetric(String metric);

  /**
   * Sends a request for deleting a metric. This method returns a {@code Future} object to consume
   * the result. {@link Future#get()} returns {@code true} if the metric was deleted, {@code false}
   * if it was not found.
   */
  Future<Boolean> deleteMetricAsync(String metric);

  /**
   * Writes log entries to Stackdriver Logging. Use {@link WriteOption#logName(String)} to provide a
   * log name for those entries that do not specify one. Use
   * {@link WriteOption#resource(MonitoredResource)} to provide a monitored resource for those
   * entries that do not specify one. Use {@link WriteOption#labels(Map)} to provide some labels
   * to be added to every entry in {@code logEntries}.
   */
  void write(Iterable<LogEntry> logEntries, WriteOption... options);

  /**
   * Sends a request to log entries to Stackdriver Logging. Use {@link WriteOption#logName(String)}
   * to provide a log name for those entries that do not specify one. Use
   * {@link WriteOption#resource(MonitoredResource)} to provide a monitored resource for those
   * entries that do not specify one. Use {@link WriteOption#labels(Map)} to provide some labels
   * to be added to every entry in {@code logEntries}. The method returns a {@code Future} object
   * that can be used to wait for the write operation to be completed.
   */
  Future<Void> writeAsync(Iterable<LogEntry> logEntries, WriteOption... options);

  /**
   * Lists log entries. This method returns a {@link Page} object that can be used to consume
   * paginated results. Use {@link EntryListOption#pageSize(int)} to specify the page size. Use
   * {@link EntryListOption#pageToken(String)} to specify the page token from which to start listing
   * entries. Use {@link EntryListOption#sortOrder(SortingField, SortingOrder)} to sort log entries
   * according to your preferred order (default is most-recent last). Use
   * {@link EntryListOption#filter(String)} to filter listed log entries.
   *
   * @throws LoggingException upon failure
   */
  Page<LogEntry> listLogEntries(EntryListOption... options);

  /**
   * Sends a request for listing log entries. This method returns a {@code Future} object to consume
   * the result. {@link Future#get()} returns an {@link AsyncPage} object that can be used to
   * asynchronously handle paginated results. Use {@link EntryListOption#pageSize(int)} to specify
   * the page size. Use {@link EntryListOption#pageToken(String)} to specify the page token from
   * which to start listing entries. Use
   * {@link EntryListOption#sortOrder(SortingField, SortingOrder)} to sort log entries according to
   * your preferred order (default is most-recent last). Use {@link EntryListOption#filter(String)}
   * to filter listed log entries.
   *
   * @throws LoggingException upon failure
   */
  Future<AsyncPage<LogEntry>> listLogEntriesAsync(EntryListOption... options);
}

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

package com.google.cloud.compute;

import com.google.cloud.BaseServiceException;
import com.google.cloud.RetryHelper.RetryHelperException;
import com.google.cloud.RetryHelper.RetryInterruptedException;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.util.Set;

/**
 * Compute Engine service exception.
 */
public class ComputeException extends BaseServiceException {

  private static final Set<Error> RETRYABLE_ERRORS = ImmutableSet.of(new Error(500, null));
  private static final long serialVersionUID = -8039359778707845810L;

  ComputeException(int code, String message) {
    super(code, message, null, true, null);
  }

  ComputeException(int code, String message, Throwable cause) {
    super(code, message, null, true, cause);
  }

  public ComputeException(IOException exception) {
    super(exception, true);
  }

  @Override
  protected Set<Error> retryableErrors() {
    return RETRYABLE_ERRORS;
  }

  /**
   * Translate RetryHelperException to the ComputeException that caused the error. This method will
   * always throw an exception.
   *
   * @throws ComputeException when {@code ex} was caused by a {@code ComputeException}
   * @throws RetryInterruptedException when {@code ex} is a {@code RetryInterruptedException}
   */
  static BaseServiceException translateAndThrow(RetryHelperException ex) {
    BaseServiceException.translateAndPropagateIfPossible(ex);
    throw new ComputeException(UNKNOWN_CODE, ex.getMessage(), ex.getCause());
  }
}

/*
 * Copyright 2012 LinkedIn, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.linkedin.parseq;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.slf4j.ILoggerFactory;


/**
 * @author Chris Pettitt (cpettitt@linkedin.com)
 */
public class ListLoggerFactory implements ILoggerFactory {
  private final ConcurrentMap<String, ListLogger> _loggerMap = new ConcurrentHashMap<String, ListLogger>();

  public void reset() {
    for (ListLogger logger : _loggerMap.values()) {
      logger.reset();
    }
  }

  @Override
  public ListLogger getLogger(final String loggerName) {
    // We could use the memoizer pattern here, but given that this is for
    // test only, we'll keep this simple.
    ListLogger logger = _loggerMap.get(loggerName);
    if (logger != null) {
      return logger;
    }

    final ListLogger newLogger = new ListLogger(loggerName);
    logger = _loggerMap.putIfAbsent(loggerName, newLogger);
    return logger == null ? newLogger : logger;
  }
}

/*
 *    Copyright 2010-2026 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.domain;

// R5: Replace Type Code with Enum
// An order's status used to be a bare string like "P" with no explanation
// Each status now has a proper name, and its short code is what gets saved in the database.
public enum OrderStatus {

  // A new order that is waiting to be processed
  PENDING("P");

  // The short code stored in the ORDERSTATUS table (the column holds at most 2 characters)
  private final String code;

  OrderStatus(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  // Find the status that matches a code read from the database
  // Returns null for a code this enum does not know, so unexpected old data does not crash the app
  public static OrderStatus fromCode(String code) {
    for (OrderStatus status : values()) {
      if (status.code.equals(code)) {
        return status;
      }
    }
    return null;
  }
}
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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AddressTest {

  // R6: all 8 fields are copied from the customer's account
  @Test
  void fromAccountCopiesAllFields() {
    Account account = new Account();
    account.setFirstName("My");
    account.setLastName("Batis");
    account.setAddress1("Address 1");
    account.setAddress2("Address 2");
    account.setCity("City");
    account.setState("ST");
    account.setZip("99001");
    account.setCountry("JPN");

    Address address = Address.fromAccount(account);

    assertThat(address.getFirstName()).isEqualTo("My");
    assertThat(address.getLastName()).isEqualTo("Batis");
    assertThat(address.getAddress1()).isEqualTo("Address 1");
    assertThat(address.getAddress2()).isEqualTo("Address 2");
    assertThat(address.getCity()).isEqualTo("City");
    assertThat(address.getState()).isEqualTo("ST");
    assertThat(address.getZip()).isEqualTo("99001");
    assertThat(address.getCountry()).isEqualTo("JPN");
  }

  // R6: a copy is a separate envelope, so changing it leaves the original alone
  @Test
  void copyIsIndependent() {
    Address original = new Address();
    original.setCity("City");

    Address copy = original.copy();
    copy.setCity("Other City");

    assertThat(original.getCity()).isEqualTo("City");
    assertThat(copy.getCity()).isEqualTo("Other City");
  }

}
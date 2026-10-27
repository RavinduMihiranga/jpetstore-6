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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * The Class OrderTest.
 */
class OrderTest {

  /**
   * Init order.
   */
  @Test
  void initOrder() {
    // given
    Account account = new Account();
    account.setUsername("mybatis");
    account.setEmail("mybatis@example.com");
    account.setFirstName("My");
    account.setLastName("Batis");
    account.setStatus("NG");
    account.setAddress1("Address 1");
    account.setAddress2("Address 2");
    account.setCity("City");
    account.setState("ST");
    account.setZip("99001");
    account.setCountry("JPN");
    account.setPhone("09012345678");

    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));
    cart.addItem(item, true);
    cart.addItem(item, true);

    Order order = new Order();

    // when
    order.initOrder(account, cart);

    // then
    assertThat(order.getUsername()).isSameAs(account.getUsername());
    assertThat(order.getOrderDate()).isBeforeOrEqualTo(LocalDateTime.now());
    assertThat(order.getShipAddress1()).isEqualTo(account.getAddress1());
    assertThat(order.getShipAddress2()).isEqualTo(account.getAddress2());
    assertThat(order.getShipCity()).isEqualTo(account.getCity());
    assertThat(order.getShipState()).isEqualTo(account.getState());
    assertThat(order.getShipCountry()).isEqualTo(account.getCountry());
    assertThat(order.getShipZip()).isEqualTo(account.getZip());
    assertThat(order.getBillAddress1()).isEqualTo(account.getAddress1());
    assertThat(order.getBillAddress2()).isEqualTo(account.getAddress2());
    assertThat(order.getBillCity()).isEqualTo(account.getCity());
    assertThat(order.getBillState()).isEqualTo(account.getState());
    assertThat(order.getBillCountry()).isEqualTo(account.getCountry());
    assertThat(order.getBillZip()).isEqualTo(account.getZip());
    assertThat(order.getTotalPrice()).isEqualTo(new BigDecimal("4.10"));
    assertThat(order.getCreditCard()).isEqualTo("999 9999 9999 9999");
    assertThat(order.getCardType()).isEqualTo("Visa");
    assertThat(order.getExpiryDate()).isEqualTo("12/03");
    assertThat(order.getCourier()).isEqualTo("UPS");
    assertThat(order.getLocale()).isEqualTo("CA");
    assertThat(order.getStatus()).isEqualTo("P");
    assertThat(order.getLineItems()).hasSize(1);
    assertThat(order.getLineItems().get(0).getItem()).isSameAs(item);
    assertThat(order.getLineItems().get(0).getLineNumber()).isEqualTo(1);
    assertThat(order.getLineItems().get(0).getItemId()).isEqualTo("I01");
    assertThat(order.getLineItems().get(0).getUnitPrice()).isEqualTo(new BigDecimal("2.05"));
    assertThat(order.getLineItems().get(0).getQuantity()).isEqualTo(2);
    assertThat(order.getLineItems().get(0).getTotal()).isEqualTo(new BigDecimal("4.10"));
  }

  /**
   * Gets the formatted order date.
   */
  @Test
  void getFormattedOrderDate() {
    // given
    Order order = new Order();
    order.setOrderDate(LocalDateTime.of(2026, Month.AUGUST, 15, 13, 45, 30));

    // when
    String formattedOrderDate = order.getFormattedOrderDate();

    // then
    assertThat(formattedOrderDate).isEqualTo("2026/08/15 13:45:30");
  }

  // R4: the customer's name is copied to both shipping and billing
  // (the original test checked the address fields but never the names)
  @Test
  void initOrderCopiesNameToShippingAndBilling() {
    Account account = new Account();
    account.setUsername("mybatis");
    account.setFirstName("My");
    account.setLastName("Batis");

    Order order = new Order();
    order.initOrder(account, new Cart());

    assertThat(order.getShipToFirstName()).isEqualTo("My");
    assertThat(order.getShipToLastName()).isEqualTo("Batis");
    assertThat(order.getBillToFirstName()).isEqualTo("My");
    assertThat(order.getBillToLastName()).isEqualTo("Batis");
  }

  // R4: each cart item becomes an order line, numbered in the order it was added
  @Test
  void initOrderNumbersLineItemsInCartOrder() {
    Cart cart = new Cart();

    Item first = new Item();
    first.setItemId("I01");
    first.setListPrice(new BigDecimal("1.00"));

    Item second = new Item();
    second.setItemId("I02");
    second.setListPrice(new BigDecimal("2.00"));

    cart.addItem(first, true);
    cart.addItem(second, true);

    Order order = new Order();
    order.initOrder(new Account(), cart);

    assertThat(order.getLineItems()).hasSize(2);
    assertThat(order.getLineItems().get(0).getItemId()).isEqualTo("I01");
    assertThat(order.getLineItems().get(0).getLineNumber()).isEqualTo(1);
    assertThat(order.getLineItems().get(1).getItemId()).isEqualTo("I02");
    assertThat(order.getLineItems().get(1).getLineNumber()).isEqualTo(2);
  }

  // R5: a new order starts as PENDING, and the database still gets the old "P" code
  @Test
  void newOrderIsPending() {
    Order order = new Order();
    order.initOrder(new Account(), new Cart());

    assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
    assertThat(order.getStatus()).isEqualTo("P");
  }

  // R5: setting the status with the enum stores its short code
  @Test
  void setOrderStatusStoresItsCode() {
    Order order = new Order();

    order.setOrderStatus(OrderStatus.PENDING);

    assertThat(order.getStatus()).isEqualTo("P");
  }

  // R5: a code the app does not know (like "OK" used in OrderMapperTest) does not crash
  @Test
  void unknownStatusCodeGivesNull() {
    Order order = new Order();

    order.setStatus("OK");

    assertThat(order.getOrderStatus()).isNull();
  }

  // R6: shipping and billing start the same but can be changed separately
  // (if both pointed at one Address, changing the ship city would change the bill city too)
  @Test
  void shippingAndBillingAddressesAreIndependent() {
    Account account = new Account();
    account.setCity("City");

    Order order = new Order();
    order.initOrder(account, new Cart());

    order.setShipCity("Other City");

    assertThat(order.getShipCity()).isEqualTo("Other City");
    assertThat(order.getBillCity()).isEqualTo("City");
  }

}

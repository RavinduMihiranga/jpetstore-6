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
import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * The Class CartTest.
 */
class CartTest {

  /**
   * Add item when is in stock is true.
   */
  @Test
  void addItemWhenIsInStockIsTrue() {
    // given
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));

    // when
    cart.addItem(item, true);
    cart.addItem(item, true);

    // then
    assertThat(cart.getCartItemList().get(0).getItem()).isSameAs(item);
    assertThat(cart.getCartItemList().get(0).isInStock()).isTrue();
    assertThat(cart.getCartItemList().get(0).getQuantity()).isEqualTo(2);
    assertThat(cart.getCartItemList().get(0).getTotal()).isEqualTo(new BigDecimal("4.10"));
    assertThat(cart.containsItemId("I01")).isTrue();
    assertThat(cart.getNumberOfItems()).isEqualTo(1);
    {
      Iterator<CartItem> cartItems = cart.getCartItems();
      assertThat(cartItems.next()).isNotNull();
      assertThat(cartItems.hasNext()).isFalse();
    }
    {
      Iterator<CartItem> cartItems = cart.getAllCartItems();
      assertThat(cartItems.next()).isNotNull();
      assertThat(cartItems.hasNext()).isFalse();
    }
  }

  /**
   * Add item when is in stock is false.
   */
  @Test
  void addItemWhenIsInStockIsFalse() {
    // given
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));

    // when
    cart.addItem(item, false);

    // then
    assertThat(cart.getCartItemList().get(0).getItem()).isSameAs(item);
    assertThat(cart.getCartItemList().get(0).isInStock()).isFalse();
    assertThat(cart.getCartItemList().get(0).getQuantity()).isEqualTo(1);
    assertThat(cart.getCartItemList().get(0).getTotal()).isEqualTo(new BigDecimal("2.05"));
  }

  /**
   * Remove item by id when item not found.
   */
  @Test
  void removeItemByIdWhenItemNotFound() {
    // given
    Cart cart = new Cart();

    // when
    Item item = cart.removeItemById("I01");

    // then
    assertThat(item).isNull();
    assertThat(cart.containsItemId("I01")).isFalse();
    assertThat(cart.getNumberOfItems()).isZero();
    assertThat(cart.getCartItems().hasNext()).isFalse();
    assertThat(cart.getAllCartItems().hasNext()).isFalse();
  }

  /**
   * Remove item by id when item found.
   */
  @Test
  void removeItemByIdWhenItemFound() {
    // given
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));
    cart.addItem(item, true);

    // when
    Item removedItem = cart.removeItemById("I01");

    // then
    assertThat(removedItem).isSameAs(item);
    assertThat(cart.getCartItemList()).isEmpty();
  }

  /**
   * Increment quantity by item id.
   */
  @Test
  void incrementQuantityByItemId() {
    // given
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));
    cart.addItem(item, true);

    // when
    cart.incrementQuantityByItemId("I01");
    cart.incrementQuantityByItemId("I01");

    // then
    assertThat(cart.getCartItemList().get(0).getItem()).isSameAs(item);
    assertThat(cart.getCartItemList().get(0).isInStock()).isTrue();
    assertThat(cart.getCartItemList().get(0).getQuantity()).isEqualTo(3);
    assertThat(cart.getCartItemList().get(0).getTotal()).isEqualTo(new BigDecimal("6.15"));
  }

  /**
   * Set quantity by item id.
   */
  @Test
  void setQuantityByItemId() {
    // given
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));
    cart.addItem(item, true);

    // when
    cart.setQuantityByItemId("I01", 10);

    // then
    assertThat(cart.getCartItemList().get(0).getItem()).isSameAs(item);
    assertThat(cart.getCartItemList().get(0).isInStock()).isTrue();
    assertThat(cart.getCartItemList().get(0).getQuantity()).isEqualTo(10);
    assertThat(cart.getCartItemList().get(0).getTotal()).isEqualTo(new BigDecimal("20.50"));
  }

  /**
   * Gets the sub total when item is empty.
   */
  @Test
  void getSubTotalWhenItemIsEmpty() {
    // given
    Cart cart = new Cart();

    // when
    BigDecimal subTotal = cart.getSubTotal();

    // then
    assertThat(subTotal).isEqualTo(BigDecimal.ZERO);

  }

  /**
   * Gets the sub total when item is exist.
   */
  @Test
  void getSubTotalWhenItemIsExist() {
    // given
    Cart cart = new Cart();
    {
      Item item = new Item();
      item.setItemId("I01");
      item.setListPrice(new BigDecimal("2.05"));
      cart.addItem(item, true);
      cart.setQuantityByItemId("I01", 5);
    }
    {
      Item item = new Item();
      item.setItemId("I02");
      item.setListPrice(new BigDecimal("3.06"));
      cart.addItem(item, true);
      cart.setQuantityByItemId("I02", 6);
    }

    // when
    BigDecimal subTotal = cart.getSubTotal();

    // then
    assertThat(subTotal).isEqualTo(new BigDecimal("28.61"));
  }

  // R1: setting the quantity to 0 removes the item from the cart completely
  @Test
  void updateQuantityToZeroRemovesItem() {
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));
    cart.addItem(item, true);

    cart.updateQuantity("I01", 0);

    assertThat(cart.getNumberOfItems()).isZero();
    assertThat(cart.containsItemId("I01")).isFalse();
  }

  // R1: a positive quantity replaces the old one and the subtotal follows
  @Test
  void updateQuantitySetsNewQuantity() {
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));
    cart.addItem(item, true);

    cart.updateQuantity("I01", 5);

    // 5 x 2.05 = 10.25
    assertThat(cart.getCartItemList().get(0).getQuantity()).isEqualTo(5);
    assertThat(cart.getSubTotal()).isEqualTo(new BigDecimal("10.25"));
  }

  // R2: items come back in the order they were added
  // (the old ConcurrentHashMap could not do this, which is why a second list existed)
  @Test
  void cartKeepsInsertionOrder() {
    Cart cart = new Cart();

    Item first = new Item();
    first.setItemId("I01");
    first.setListPrice(new BigDecimal("1.00"));

    Item second = new Item();
    second.setItemId("I02");
    second.setListPrice(new BigDecimal("2.00"));

    Item third = new Item();
    third.setItemId("I03");
    third.setListPrice(new BigDecimal("3.00"));

    cart.addItem(first, true);
    cart.addItem(second, true);
    cart.addItem(third, true);

    assertThat(cart.getCartItemList().get(0).getItem()).isSameAs(first);
    assertThat(cart.getCartItemList().get(1).getItem()).isSameAs(second);
    assertThat(cart.getCartItemList().get(2).getItem()).isSameAs(third);
  }

  // R2: changing the returned list must not change the cart
  // (before R2 this list WAS the cart's own list, which is how the R1 bug could happen)
  @Test
  void changingReturnedListDoesNotChangeCart() {
    Cart cart = new Cart();
    Item item = new Item();
    item.setItemId("I01");
    item.setListPrice(new BigDecimal("2.05"));
    cart.addItem(item, true);

    cart.getCartItemList().clear();

    assertThat(cart.getNumberOfItems()).isEqualTo(1);
    assertThat(cart.containsItemId("I01")).isTrue();
  }

}

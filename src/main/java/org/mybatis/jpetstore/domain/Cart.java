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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// A shopping cart for one user's session.
// R2: items are kept in ONE LinkedHashMap instead of a map plus a list.
// The LinkedHashMap gives fast lookup by item ID and also remembers the order items were added,
// so there is no second collection that can get out of sync.
public class Cart implements Serializable {

  private static final long serialVersionUID = 8329559983943337176L;

  // item ID -> cart item, kept in the order the items were added
  private final Map<String, CartItem> items = new LinkedHashMap<>();

  // Loop over the items in the order they were added
  public Iterator<CartItem> getCartItems() {
    return getCartItemList().iterator();
  }

  // The items as a list, used by the cart and checkout pages (cart.cartItemList)
  // A copy is returned so callers cannot change the cart behind its back
  public List<CartItem> getCartItemList() {
    return new ArrayList<>(items.values());
  }

  // How many different items are in the cart
  public int getNumberOfItems() {
    return items.size();
  }

  // Old name for getCartItems(), kept so existing code still works
  @Deprecated
  public Iterator<CartItem> getAllCartItems() {
    return getCartItems();
  }

  // Is this item already in the cart?
  public boolean containsItemId(String itemId) {
    return items.containsKey(itemId);
  }

  // Add one of an item. If it is new it starts at 0 and then goes up to 1.
  public void addItem(Item item, boolean isInStock) {
    CartItem cartItem = items.get(item.getItemId());

    if (cartItem == null) {
      cartItem = new CartItem();
      cartItem.setItem(item);
      cartItem.setQuantity(0);
      cartItem.setInStock(isInStock);
      items.put(item.getItemId(), cartItem);
    }

    cartItem.incrementQuantity();
  }

  // Remove an item completely
  // Returns the removed item, or null if it was not in the cart
  public Item removeItemById(String itemId) {
    CartItem cartItem = items.remove(itemId);

    if (cartItem == null) {
      return null;
    }

    return cartItem.getItem();
  }

  // Add one more of an item that is already in the cart
  public void incrementQuantityByItemId(String itemId) {
    CartItem cartItem = items.get(itemId);
    cartItem.incrementQuantity();
  }

  // Set the quantity of an item that is already in the cart
  public void setQuantityByItemId(String itemId, int quantity) {
    CartItem cartItem = items.get(itemId);
    cartItem.setQuantity(quantity);
  }

  // Update how many of an item are in the cart (R1: Move Method)
  // A quantity below 1 means the user wants the item gone, so it is removed completely.
  public void updateQuantity(String itemId, int quantity) {
    if (itemId == null || itemId.isBlank()) {
      throw new IllegalArgumentException("Item ID cannot be blank.");
    }

    if (quantity < 1) {
      removeItemById(itemId);
    } else {
      setQuantityByItemId(itemId, quantity);
    }
  }

  // Total price of everything in the cart: list price x quantity for each item
  public BigDecimal getSubTotal() {
    BigDecimal subTotal = BigDecimal.ZERO;

    for (CartItem cartItem : items.values()) {
      BigDecimal price = cartItem.getItem().getListPrice();
      BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
      subTotal = subTotal.add(price.multiply(quantity));
    }

    return subTotal;
  }

}
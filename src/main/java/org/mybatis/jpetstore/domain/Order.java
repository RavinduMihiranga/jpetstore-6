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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Order.
 */
public class Order implements Serializable {

  /** The serial version uid. */
  private static final long serialVersionUID = 6321792448424424931L;

  // R4: Replace Literal with Constant
  // Placeholder values pre-filled in the order form (NewOrderForm.jsp tells users to use a fake card number)
  // The user can change the card details on the form before placing the order
  private static final String DEFAULT_CREDIT_CARD = "999 9999 9999 9999";
  private static final String DEFAULT_EXPIRY_DATE = "12/03";
  private static final String DEFAULT_CARD_TYPE = "Visa";
  private static final String DEFAULT_COURIER = "UPS";
  private static final String DEFAULT_LOCALE = "CA";

  /** The order id. */
  private int orderId;
  /** The username. */
  private String username;
  /** The order date. */
  private LocalDateTime orderDate;

  // R6: Extract Class
  // These two objects replace 16 separate fields (shipToFirstName ... shipCountry and billToFirstName ... billCountry).
  // They start empty so MyBatis and the order form can fill them in through the setters below.
  private Address shippingAddress = new Address();
  private Address billingAddress = new Address();
  /** The courier. */
  private String courier;
  /** The total price. */
  private BigDecimal totalPrice;
  /** The bill to first name. */
  private String billToFirstName;
  /** The bill to last name. */
  private String billToLastName;
  /** The ship to first name. */
  private String shipToFirstName;
  /** The ship to last name. */
  private String shipToLastName;
  /** The credit card. */
  private String creditCard;
  /** The expiry date. */
  private String expiryDate;
  /** The card type. */
  private String cardType;
  /** The locale. */
  private String locale;
  /** The status. */
  private String status;
  /** The line items. */
  private List<LineItem> lineItems = new ArrayList<>();

  /**
   * Gets the order id.
   *
   * @return the order id
   */
  public int getOrderId() {
    return orderId;
  }

  /**
   * Sets the order id.
   *
   * @param orderId
   *          the order id
   */
  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   *
   * @param username
   *          the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the order date.
   *
   * @return the order date
   */
  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  /**
   * Sets the order date.
   *
   * @param orderDate
   *          the order date
   */
  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  // R6: the whole shipping address as one object
  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress) {
    if (shippingAddress == null) {
      throw new IllegalArgumentException("Shipping address cannot be null.");
    }

    this.shippingAddress = shippingAddress;
  }

  // R6: the whole billing address as one object
  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    if (billingAddress == null) {
      throw new IllegalArgumentException("Billing address cannot be null.");
    }

    this.billingAddress = billingAddress;
  }

  // ---------------------------------------------------------------------------
  // Old shipping getters and setters, kept so nothing outside Order has to change
  // Each one now simply reads or writes the shippingAddress object
  // ---------------------------------------------------------------------------

  public String getShipAddress1() {
    return shippingAddress.getAddress1();
  }

  public void setShipAddress1(String shipAddress1) {
    shippingAddress.setAddress1(shipAddress1);
  }

  public String getShipAddress2() {
    return shippingAddress.getAddress2();
  }

  public void setShipAddress2(String shipAddress2) {
    shippingAddress.setAddress2(shipAddress2);
  }

  public String getShipCity() {
    return shippingAddress.getCity();
  }

  public void setShipCity(String shipCity) {
    shippingAddress.setCity(shipCity);
  }

  public String getShipState() {
    return shippingAddress.getState();
  }

  public void setShipState(String shipState) {
    shippingAddress.setState(shipState);
  }

  public String getShipZip() {
    return shippingAddress.getZip();
  }

  public void setShipZip(String shipZip) {
    shippingAddress.setZip(shipZip);
  }

  public String getShipCountry() {
    return shippingAddress.getCountry();
  }

  public void setShipCountry(String shipCountry) {
    shippingAddress.setCountry(shipCountry);
  }

  // ---------------------------------------------------------------------------
  // Old billing getters and setters, kept for the same reason.
  // Each one now simply reads or writes the billingAddress object.
  // ---------------------------------------------------------------------------

  public String getBillAddress1() {
    return billingAddress.getAddress1();
  }

  public void setBillAddress1(String billAddress1) {
    billingAddress.setAddress1(billAddress1);
  }

  public String getBillAddress2() {
    return billingAddress.getAddress2();
  }

  public void setBillAddress2(String billAddress2) {
    billingAddress.setAddress2(billAddress2);
  }

  public String getBillCity() {
    return billingAddress.getCity();
  }

  public void setBillCity(String billCity) {
    billingAddress.setCity(billCity);
  }

  public String getBillState() {
    return billingAddress.getState();
  }

  public void setBillState(String billState) {
    billingAddress.setState(billState);
  }

  public String getBillZip() {
    return billingAddress.getZip();
  }

  public void setBillZip(String billZip) {
    billingAddress.setZip(billZip);
  }

  public String getBillCountry() {
    return billingAddress.getCountry();
  }

  public void setBillCountry(String billCountry) {
    billingAddress.setCountry(billCountry);
  }

  /**
   * Gets the courier.
   *
   * @return the courier
   */
  public String getCourier() {
    return courier;
  }

  /**
   * Sets the courier.
   *
   * @param courier
   *          the courier
   */
  public void setCourier(String courier) {
    this.courier = courier;
  }

  /**
   * Gets the total price.
   *
   * @return the total price
   */
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  /**
   * Sets the total price.
   *
   * @param totalPrice
   *          the total price
   */
  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  // ---------------------------------------------------------------------------
  // Old name getters and setters, kept for the same reason.
  // ---------------------------------------------------------------------------

  public String getBillToFirstName() {
    return billingAddress.getFirstName();
  }

  public void setBillToFirstName(String billToFirstName) {
    billingAddress.setFirstName(billToFirstName);
  }

  public String getBillToLastName() {
    return billingAddress.getLastName();
  }

  public void setBillToLastName(String billToLastName) {
    billingAddress.setLastName(billToLastName);
  }

  public String getShipToFirstName() {
    return shippingAddress.getFirstName();
  }

  public void setShipToFirstName(String shipToFirstName) {
    shippingAddress.setFirstName(shipToFirstName);
  }

  public String getShipToLastName() {
    return shippingAddress.getLastName();
  }

  public void setShipToLastName(String shipToLastName) {
    shippingAddress.setLastName(shipToLastName);
  }

  /**
   * Gets the credit card.
   *
   * @return the credit card
   */
  public String getCreditCard() {
    return creditCard;
  }

  /**
   * Sets the credit card.
   *
   * @param creditCard
   *          the credit card
   */
  public void setCreditCard(String creditCard) {
    this.creditCard = creditCard;
  }

  /**
   * Gets the expiry date.
   *
   * @return the expiry date
   */
  public String getExpiryDate() {
    return expiryDate;
  }

  /**
   * Sets the expiry date.
   *
   * @param expiryDate
   *          the expiry date
   */
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  /**
   * Gets the card type.
   *
   * @return the card type
   */
  public String getCardType() {
    return cardType;
  }

  /**
   * Sets the card type.
   *
   * @param cardType
   *          the card type
   */
  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  /**
   * Gets the locale.
   *
   * @return the locale
   */
  public String getLocale() {
    return locale;
  }

  /**
   * Sets the locale.
   *
   * @param locale
   *          the locale
   */
  public void setLocale(String locale) {
    this.locale = locale;
  }

  /**
   * Gets the status.
   *
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the status.
   *
   * @param status
   *          the status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  // R5: the status as an enum
  // Returns null if the stored code is not a known status
  public OrderStatus getOrderStatus() {
    return OrderStatus.fromCode(status);
  }

  // R5: set the status using the enum
  public void setOrderStatus(OrderStatus orderStatus) {
    if (orderStatus == null) {
      throw new IllegalArgumentException("Order status cannot be null.");
    }

    this.status = orderStatus.getCode();
  }

  /**
   * Sets the line items.
   *
   * @param lineItems
   *          the line items
   */
  public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  /**
   * Gets the line items.
   *
   * @return the line items
   */
  public List<LineItem> getLineItems() {
    return lineItems;
  }

  /**
   * Inits the order.
   *
   * @param account
   *          the account
   * @param cart
   *          the cart
   */
  // Fill in a new order from the customer's account and their cart
  // R4: Extract Method. Each step now lives in its own small method,
  // so this method reads like a list of steps instead of 39 lines of assignments.
  public void initOrder(Account account, Cart cart) {
    username = account.getUsername();
    orderDate = LocalDateTime.now(ZoneId.systemDefault());

    // R6: build the customer's address once, then use it for shipping and billing
    // Billing gets its own copy, so changing the shipping address later does not change the billing address
    shippingAddress = Address.fromAccount(account);
    billingAddress = shippingAddress.copy();

    totalPrice = cart.getSubTotal();
    applyDefaultOrderDetails();

    addLineItemsFrom(cart);
  }

  // Pre-fill the payment and delivery details shown on the order form
  private void applyDefaultOrderDetails() {
    creditCard = DEFAULT_CREDIT_CARD;
    expiryDate = DEFAULT_EXPIRY_DATE;
    cardType = DEFAULT_CARD_TYPE;
    courier = DEFAULT_COURIER;
    locale = DEFAULT_LOCALE;
    // Every new order starts as pending
    status = OrderStatus.PENDING.getCode();
  }

  // Turn every item in the cart into a numbered line on the order
  private void addLineItemsFrom(Cart cart) {
    for (CartItem cartItem : cart.getCartItemList()) {
      addLineItem(cartItem);
    }
  }

  /**
   * Add line item.
   *
   * @param cartItem
   *          the cart item
   */
  public void addLineItem(CartItem cartItem) {
    LineItem lineItem = new LineItem(lineItems.size() + 1, cartItem);
    addLineItem(lineItem);
  }

  /**
   * Add line item.
   *
   * @param lineItem
   *          the line item
   */
  public void addLineItem(LineItem lineItem) {
    lineItems.add(lineItem);
  }

  /**
   * Gets the formatted order date.
   *
   * @return the formatted order date
   */
  public String getFormattedOrderDate() {
    return orderDate.format(ORDER_DATE_FORMATTER);
  }

  /** The Constant ORDER_DATE_FORMATTER. */
  private static final DateTimeFormatter ORDER_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

}

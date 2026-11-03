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

// R6: Extract Class
// A person's name and postal address, used for both shipping and billing on an order.
// Before this, Order had 16 separate fields (8 "ship..." + 8 "bill...") that held the same kind of data.
public class Address implements Serializable {

  private static final long serialVersionUID = 1L;

  private String firstName;
  private String lastName;
  private String address1;
  private String address2;
  private String city;
  private String state;
  private String zip;
  private String country;

  // Build an address from a customer's account details
  public static Address fromAccount(Account account) {
    if (account == null) {
      throw new IllegalArgumentException("Account cannot be null.");
    }

    Address address = new Address();
    address.firstName = account.getFirstName();
    address.lastName = account.getLastName();
    address.address1 = account.getAddress1();
    address.address2 = account.getAddress2();
    address.city = account.getCity();
    address.state = account.getState();
    address.zip = account.getZip();
    address.country = account.getCountry();

    return address;
  }

  // Make a separate copy, so changing one address never changes the other
  public Address copy() {
    Address copy = new Address();
    copy.firstName = firstName;
    copy.lastName = lastName;
    copy.address1 = address1;
    copy.address2 = address2;
    copy.city = city;
    copy.state = state;
    copy.zip = zip;
    copy.country = country;

    return copy;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

}
package com.rlx.command.center.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Person{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  private String phone;

  public String getPhone(){
    return phone;
  }

  public void  setPhone(String phone){
    this.phone = phone;
  }

  public String getFirstName(){
    return firstName;
  }

  public void setFirstName(String firstName){
      this.firstName = firstName;
  }

  public String getLastName(){
    return lastName;
  }

  public void setLastName(String lastName){
    this.lastName = lastName;
  }

}

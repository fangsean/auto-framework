package com.auto.entity;

import lombok.Data;

/**
 * @author auto.yin<auto.yin @ gmail.com>
 * 2018-10-18
 * @Description: <p></p>
 */

@Data
public class Person {

   private  String name;
   private  Integer num;

   public Person(String name, Integer num) {
      this.name = name;
      this.num = num;
   }
}

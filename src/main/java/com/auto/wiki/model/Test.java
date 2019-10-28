package com.auto.wiki.model;

import java.util.Optional;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-18
 * @Description: <p></p>
 */
public class Test {

    public static void main(String[] args) {

//        Optional<Car> car = Optional.empty();

        Car car = new Car();
        Optional<Car> optCar = Optional.of(car);

        Car car1 = optCar.get();
        System.out.println(car1);
        Person p = new Person();
        Optional<Person> person = Optional.of(p);
        System.out.println(person);
        String unknown = person
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
        System.out.println(unknown);


    }

}

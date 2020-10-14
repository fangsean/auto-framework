package com.auto.zproblem;

public class Resource implements AutoCloseable {

    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Resource is closed");
    }

    public static void main(String[] args) throws Exception {
        try (Resource resource = new Resource()) {
            resource.sayHello();
        }
    }

}

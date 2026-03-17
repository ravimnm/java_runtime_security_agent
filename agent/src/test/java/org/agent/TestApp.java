package org.agent;

import java.io.*;

public class TestApp {

    public static void main(String[] args) throws Exception {

        System.out.println("Application running");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);

        out.writeObject("hello");  // String is Serializable
        out.flush();

        byte[] bytes = bos.toByteArray();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);

        Object obj = in.readObject();

        System.out.println("Deserialized: " + obj);
    }
}
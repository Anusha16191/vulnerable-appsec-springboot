package com.anusha.vulnerableappsec;

import java.io.*;
import java.util.Base64;
import com.anusha.vulnerableappsec.model.Profile;

public class SerializeProfile {

    public static void main(String[] args) throws Exception {

        Profile p = new Profile();
        p.setName("Test User");
        p.setEmail("test@mail.com");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(p);
        oos.close();

        String base64 = Base64.getEncoder().encodeToString(bos.toByteArray());
        System.out.println(base64);
    }
}

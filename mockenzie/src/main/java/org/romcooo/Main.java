package org.romcooo;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "John");
        jsonObject.put("lastName", "Smith");
        jsonObject.put("age", 25);
        JSONObject address = new JSONObject();
        address.put("streetAddress", "21 2nd Street");
        address.put("city", "New York");
        address.put("state", "NY");
        address.put("postalCode", "10021");
        jsonObject.put("address", address);
        JSONObject phone1 = new JSONObject();
        phone1.put("type", "home");
        phone1.put("number", "212 555-1234");
        JSONObject phone2 = new JSONObject();
        phone2.put("type", "fax");
        phone2.put("number", "646 555-4567");
        JSONArray phones = new JSONArray();
        phones.add(phone1);
        phones.add(phone2);
        jsonObject.put("phone", phones);
        System.out.println(jsonObject.toJSONString());
        
        for (Object o : phones) {
            if (o instanceof JSONObject) {
                JSONObject o2 = (JSONObject) o;
                if (o2.get("type") == "fax") {
                    System.out.println("there is a fax: " + o2.get("number"));
                }
                
            }
        }
    }
}

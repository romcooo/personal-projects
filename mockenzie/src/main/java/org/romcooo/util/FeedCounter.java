package org.romcooo.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static org.romcooo.util.Const.csvSeparator;

public class FeedCounter {
    
    public static void main(String[] args) throws FileNotFoundException, ParseException {
//        File file = new File("C:\\Users\\roman.stubna\\Desktop\\exports\\output_java_w_address_extended_backup.txt");
        File file = new File("C:\\Users\\roman.stubna\\Desktop\\exports\\output_java_in_2.txt");
        Scanner reader = new Scanner(file);
    
//        PrintWriter writer = new PrintWriter("C:\\Users\\roman.stubna\\Desktop\\output_producer_distrib.txt");
    
        List<Map<String, String>> requests = new ArrayList<>();
        Map<String, String> currentRequest = new HashMap<>();
    
        StringBuilder sb = new StringBuilder();
        
        while (reader.hasNextLine()) {
            
            String line = reader.nextLine();
            if (line.contains("========")) {
                currentRequest = new HashMap<>();
                requests.add(currentRequest);
                continue;
            }
            if (line.contains(" = ")) {
                String key = line.substring(0, line.indexOf(" = "));
                String value = line.substring(line.indexOf(" = ") + 3);
                currentRequest.put(key, value);
            }
            
        }
        
//        List<Map<String, Integer>> distinctCountList = new ArrayList<>();
//        for (Map<String, String> request : requests) {
//            for (String key : request.keySet()) {
//
//            }
//        }
        
        Map<String, Integer> producerCount = new HashMap<>();
        Map<String, Map<String, Integer>> producerToModelCountMap = new HashMap<>();
    
        Map<String, Map<String, String>> modelAttributeMap = new HashMap<>();
        
        List<Map<String, String>> salesroomList = new ArrayList<>();
        
        for (Map<String, String> request : requests) {
            // producer to model count map
            String producer = request.get("commodity.producer");
            if (producerCount.containsKey(producer)) {
                producerCount.put(producer, producerCount.get(producer) + 1);
            } else {
                producerCount.put(producer, 1);
            }
            
            String modelNumber = request.get("commodity.modelNumber");
            Map<String, Integer> modelCountForProducer = new HashMap<>();
            
            if (producerToModelCountMap.containsKey(producer)) {
                modelCountForProducer = producerToModelCountMap.get(producer);
                if (modelCountForProducer.containsKey(modelNumber)) {
                    modelCountForProducer.put(modelNumber, modelCountForProducer.get(modelNumber) + 1);
                } else {
                    modelCountForProducer.put(modelNumber, 1);
                    producerToModelCountMap.put(producer, modelCountForProducer);
                }
            } else {
                modelCountForProducer.put(modelNumber, 1);
                producerToModelCountMap.put(producer, modelCountForProducer);
            }
    
            // model attributes
            Map<String, String> modelAttributes = new HashMap<>();
            modelAttributes.put("producer", producer);
            modelAttributes.put("price", request.get("commodity.price"));
            modelAttributes.put("typeCode", request.get("commodity.typeCode"));
            modelAttributes.put("serialNumber", request.get("commodity.serialNumber"));
            modelAttributes.put("engineNumber", request.get("commodity.engineNumber"));
            modelAttributes.put("licencePlateNumber", request.get("commodity.licencePlateNumber"));

            modelAttributeMap.put(modelNumber, modelAttributes);
            
            // salesrooms
            Map<String, String> salesroomInfo = new HashMap<>();
            salesroomInfo.put("code", request.get("salesroom.code"));
            salesroomInfo.put("name", request.get("salesroom.name"));
            salesroomInfo.put("gpsLatitude", request.get("salesroom.gpsLatitude"));
            salesroomInfo.put("gpsLongtitude", request.get("salesroom.gpsLongtitude"));
            salesroomInfo.put("partnerCode", request.get("salesroom.partnerCode"));
            salesroomInfo.put("partnerName", request.get("salesroom.partnerName"));
    
            if (request.get("salesroom.addressInfo") != null) {JSONParser jsonParser = new JSONParser();
                JSONObject
                jsonObject = (JSONObject) jsonParser.parse(request.get("salesroom.addressInfo"));
                salesroomInfo.put("countryCode", (String) jsonObject.get("countryCode"));
                salesroomInfo.put("districtCode", (String) jsonObject.get("districtCode"));
                salesroomInfo.put("houseNumber", (String) jsonObject.get("houseNumber"));
                salesroomInfo.put("regionCode", (String) jsonObject.get("regionCode"));
                salesroomInfo.put("addressType", (String) jsonObject.get("addressType"));
                salesroomInfo.put("street", (String) jsonObject.get("street"));
                salesroomInfo.put("town", (String) jsonObject.get("town"));
            }
    
            salesroomList.add(salesroomInfo);
        }
    
    
        sb.append("## PRODUCERS:\n");
        for (String producers : producerCount.keySet()) {
            sb.append(producers)
              .append(csvSeparator)
              .append(producerCount.get(producers))
              .append("\n");
        }
        
        sb.append("## MODELS:\n");
        
        for (String producer : producerToModelCountMap.keySet()) {
//            sb.append("\n==")
//              .append(producer)
//              .append(":\n");
            Map<String, Integer> modelCount = producerToModelCountMap.get(producer);
            for (String model : modelCount.keySet()) {
                sb.append(model)
                  .append(csvSeparator)
                  .append(producer)
                  .append(csvSeparator)
                  .append(modelCount.get(model))
                  .append("\n");
            }
        }
    
        sb.append("## MODEL ATTRIBUTES (modelname and: ")
          .append(modelAttributeMap.values()
                                   .stream()
                                   .findFirst()
                                   .orElse(Collections.emptyMap())
                                   .keySet())
          .append(")\n");
        
        for (String modelName : modelAttributeMap.keySet()) {
            Map<String, String> modelAttributes = modelAttributeMap.get(modelName);
            sb.append(modelName)
              .append(csvSeparator);
            for (String attributeName : modelAttributes.keySet()) {
                sb.append(modelAttributes.get(attributeName))
                  .append(csvSeparator);
            }
            if (sb.lastIndexOf(csvSeparator) > 0) {
                sb.delete(sb.lastIndexOf(csvSeparator), sb.lastIndexOf(csvSeparator)+1);
            }
            sb.append("\n");
        }
        
        sb.append("\n## SALESROOMS\n");
        
        Map<String, String> first = salesroomList.get(0);
        for (String key : first.keySet()) {
            sb.append(key)
              .append(csvSeparator);
        }
        if (sb.lastIndexOf(csvSeparator) > 0) {
            sb.delete(sb.lastIndexOf(csvSeparator), sb.lastIndexOf(csvSeparator)+1);
        }
        sb.append("\n");
    
    
        int counter = 0;
        for (Map<String, String> entry : salesroomList) {
            for (String key : entry.keySet()) {
                sb.append(entry.get(key))
                  .append(csvSeparator);
                  
            }
            if (sb.lastIndexOf(csvSeparator) > 0) {
                sb.delete(sb.lastIndexOf(csvSeparator), sb.lastIndexOf(csvSeparator)+1);
            }
            sb.append("\n");
            counter++;
            if (counter >= 60) {
                break;
            }
        }
        
        System.out.println(sb.toString());
        
    }
}

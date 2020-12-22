package org.romco;

public class Main {
    
    public static void main(String[] args) {
        try {
            SplunkLogExtractor.extract();
            GatlingSourceDataTransformer.transformData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

package org.romcooo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SalesroomInfoGatherer {
    
    public static final String SALESROOM_ENDPOINT = "https://homesis.in00c1.in.infra/homesis/restful/salesrooms/";
    
    
    public static void main(String[] args) throws FileNotFoundException {
    
        File file = new File("C:\\Users\\roman.stubna\\Desktop\\exports\\export_in_2_older.txt");
        Scanner reader = new Scanner(file);
    
        PrintWriter writer = new PrintWriter("C:\\Users\\roman.stubna\\Desktop\\exports\\output_java_in_2_2.txt");
    
        int counter = 0;
        int maxRows = 6000;
    
        while (reader.hasNextLine() && (counter < maxRows || maxRows == -1)) {
        
        }
    }
    
}

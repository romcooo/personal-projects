package org.romco.localwiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;

import java.util.Scanner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockRunner {
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
                                                                   .port(9999)
                                                                   .usingFilesUnderDirectory("src/test/resources/wiremock-resources")
                                                                   .notifier(new ConsoleNotifier(true))
                                                                   .extensions(new ResponseTemplateTransformer(true)));
        
        wireMockServer.start();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("q to quit");
        while (scanner.hasNext()) {
            String s = scanner.next();
            if (s.equals("q")) {
                break;
            }
            scanner.nextLine();
        }
        System.out.println("quitting");
        wireMockServer.stop();
    }
}

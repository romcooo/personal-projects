package org.romcooo.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SplunkLogExtractor {
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, InterruptedException, ParseException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // write your code here
        File file = new File("C:\\Users\\roman.stubna\\Desktop\\vn prod 4h only v6.txt");
        Scanner reader = new Scanner(file);
        
        PrintWriter writer = new PrintWriter("C:\\Users\\roman.stubna\\Desktop\\output_java.txt");
        
        int counter = 0;
        int maxRows = 5;
        
        while (reader.hasNextLine() && counter < maxRows) {
            counter++;
            
            StringBuilder sb = new StringBuilder();
            sb.append("=================\n");
            System.out.println("=================");
            String line = reader.nextLine();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(line)));
            Element root = document.getDocumentElement();
            
            Map<String, String> vals = new HashMap<String, String>();
            
            XPath xPath = XPathFactory.newInstance().newXPath();
            
            String totalMonthlyPayment = (String) xPath.evaluate("/ContractFullInfoRequest/data/contractParameterCEL/totalMonthlyPayment", document, XPathConstants.STRING);
            String term = (String) xPath.evaluate("/ContractFullInfoRequest/data/contractParameterCEL/terms", document, XPathConstants.STRING);
            
            String loanAmount = (String) xPath.evaluate("/ContractFullInfoRequest/data/contractParameterCEL/providedCreditAmount", document, XPathConstants.STRING);
            if (loanAmount == null) {
                loanAmount = (String) xPath.evaluate("/ContractFullInfoRequest/data/contractParameterREL/providedCreditAmount", document, XPathConstants.STRING);
            }
            
            String downPayment = (String) xPath.evaluate("/ContractFullInfoRequest/data/contractParameterCEL/netCashPayment", document, XPathConstants.STRING);
            if (downPayment == null) {
                downPayment = (String) xPath.evaluate("/ContractFullInfoRequest/data/contractParameterREL/netCashPayment", document, XPathConstants.STRING);
            }
            
            String salesroomCode = (String) xPath.evaluate("/ContractFullInfoRequest/data/salesroomCode", document, XPathConstants.STRING);
            
            String commodityTypeCode = (String) xPath.evaluate("/ContractFullInfoRequest/data/commodity/commodityTypeCode", document, XPathConstants.STRING);
            String commodityProducer = (String) xPath.evaluate("/ContractFullInfoRequest/data/commodity/producer", document, XPathConstants.STRING);
            String commoditySerialNumber = (String) xPath.evaluate("/ContractFullInfoRequest/data/commodity/serialNumber", document, XPathConstants.STRING);
            String commodityModelNumber = (String) xPath.evaluate("/ContractFullInfoRequest/data/commodity/modelNumber", document, XPathConstants.STRING);
            String commodityEngineNumber = (String) xPath.evaluate("/ContractFullInfoRequest/data/commodity/engineNumber", document, XPathConstants.STRING);
            String commodityLicencePlateNumber = (String) xPath.evaluate("/ContractFullInfoRequest/data/commodity/licencePlateNumber", document, XPathConstants.STRING);
            String commodityPrice = (String) xPath.evaluate("/ContractFullInfoRequest/data/commodity/price", document, XPathConstants.STRING);
            
            
            // TODO finish parsing for each commodity then uncomment the call to commodities with coma separated exchange ids below
//            NodeList commodityExchangeIdNodeList = (NodeList) xPath.evaluate("/ContractFullInfoRequest/data/commodity", document, XPathConstants.NODESET);
//            List<String> commodityExchangeIds = new ArrayList<>();
//            for (int i = 0; i < commodityExchangeIdNodeList.getLength(); i++) {
//                Node node = commodityExchangeIdNodeList.item(i);
//                NodeList commodityChildren = node.getChildNodes();
//                for (int cn = 0; cn < commodityChildren.getLength(); cn++) {
////                    if (commodityChildren.item(cn).getNodeValue()
//                }
//                String content = commodityExchangeIdNodeList.item(i).getChildNodes().item();
////                String content = node.getTextContent();
//                commodityExchangeIds.add(content);
//                System.out.println(content);
//            }
            
            vals.put("financialParameters.totalMonthlyPayment", totalMonthlyPayment);
            vals.put("financialParameters.term", term);
            vals.put("financialParameters.loanAmount", loanAmount);
            vals.put("financialParameters.downPayment", downPayment);
            vals.put("salesroom.code", salesroomCode);
            vals.put("commodity.typeCode", commodityTypeCode);
            vals.put("commodity.producer", commodityProducer);
            vals.put("commodity.serialNumber", commoditySerialNumber);
            vals.put("commodity.modelNumber", commodityModelNumber);
            vals.put("commodity.engineNumber", commodityEngineNumber);
            vals.put("commodity.licencePlateNumber", commodityLicencePlateNumber);
            vals.put("commodity.price", commodityPrice);
            
            
            String salesroomDetailsUri = "https://homesis.vn00c1.vn.infra/homesis/restful/salesrooms/" + salesroomCode;
            CloseableHttpClient httpClient = createTrustAllHttpClientBuilder().build();
            String encoding = Base64.getEncoder().encodeToString(("homerselect:homerselect").getBytes());
            
            HttpUriRequest httpUriRequest = new HttpGet(URI.create(salesroomDetailsUri));
            httpUriRequest.setHeader("User-Agent", "Java 11 HttpClient Bot");
            httpUriRequest.setHeader("Authorization", "Basic " + encoding);
            httpUriRequest.setHeader("Accept", "*/*");
            
            CloseableHttpResponse srDetailResponse = httpClient.execute(httpUriRequest);
            
            if (srDetailResponse.getStatusLine().getStatusCode() == 200) {
                String respString = EntityUtils.toString(srDetailResponse.getEntity());
                System.out.println(respString);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(respString);
                String salesroomName = (String) jsonObject.get("name");
                String salesroomGpsLatitude = (String) jsonObject.get("gpsLatitude");
                String salesroomGpsLongitude = (String) jsonObject.get("gpsLongtitude");
                vals.put("salesroom.name", salesroomName);
                vals.put("salesroom.gpsLatitude", salesroomGpsLatitude);
                vals.put("salesroom.gpsLongtitude", salesroomGpsLongitude);
            }
            
            
            String salesroomAddressUri = "https://homesis.vn00c1.vn.infra/homesis/restful/salesrooms/" + salesroomCode + "/addresses/SR_BUS";
            
            HttpUriRequest httpUriRequestAddress = new HttpGet(URI.create(salesroomAddressUri));
            httpUriRequest.setHeader("User-Agent", "Java 11 HttpClient Bot");
            httpUriRequest.setHeader("Authorization", "Basic " + encoding);
            httpUriRequest.setHeader("Accept", "*/*");
            
            
            CloseableHttpResponse addressResponse = httpClient.execute(httpUriRequestAddress);
            
            if (addressResponse.getStatusLine().getStatusCode() == 200) {
                String respString = EntityUtils.toString(addressResponse.getEntity());
                System.out.println(respString);
                vals.put("salesroom.addressInfo", respString);
                
            }
            
            ////////////////

//            StringBuilder exchangeIds = new StringBuilder();
//            commodityExchangeIds.forEach(it -> exchangeIds.append(it).append(","));
//            if (!commodityExchangeIds.isEmpty()) {
//                commodityExchangeIds.remove(commodityExchangeIds.size()-1);
//            }
//
//            String prodEncoding = Base64.getEncoder().encodeToString(("osbuser:6xKcZIH7PLE4").getBytes());
//            String commodityUri = "https://commoditywl.pdcvn1.vn.prod/commodity/openapi/v1/commodities/" + exchangeIds.toString();
//            System.out.println("calling " + commodityUri);
//
//            HttpUriRequest httpUriRequestCommodity = new HttpGet(URI.create(commodityUri));
//            httpUriRequest.setHeader("User-Agent", "appsupp_healthcheck");
//            httpUriRequest.setHeader("Authorization", "Basic " + prodEncoding);
//            httpUriRequest.setHeader("Accept", "*/*");
//
//
//            CloseableHttpResponse commodityResponse = httpClient.execute(httpUriRequestCommodity);
//
//            if (addressResponse.getStatusLine().getStatusCode() == 200) {
//                String respString = EntityUtils.toString(commodityResponse.getEntity());
//                System.out.println(respString);
//                vals.put("commodity.details", respString);
//
//            }
//
            //////////////
            
            
            if (!getString("initTransactionType", root).equals("NDF")) {
                for (String key : vals.keySet()) {
                    sb.append(key)
                      .append(" = ")
                      .append(vals.get(key))
                      .append("\n" );
                    
                    System.out.println(key + " : " + vals.get(key));
                }
                writer.println(sb.toString());
            }
            
        }
        
        
        writer.close();
        
    }
    
    protected static String getString(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();
            
            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        
        return null;
    }
    
    public static HttpClientBuilder createTrustAllHttpClientBuilder() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(null, (chain, authType) -> true);
        SSLConnectionSocketFactory sslsf = new
                SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
        return HttpClients.custom().setSSLSocketFactory(sslsf);
    }
    
}

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.util.*;

public class SplunkLogExtractor {
    
    // source data exported from https://splunk-pdc.cz.prod/en-GB/account/login?return_to=%2Fen-GB%2Fapp%2Fsearch%2Fsearch%3Fq%3Dsearch%2520country%253D%2522vn%2522%2520index%253D%2522hcg_bsl_prod%2522%2520ContractFullInfoRequest%2520%2522ContractSignSE%2522%26display.page.search.mode%3Dsmart%26dispatch.sample_ratio%3D1%26earliest%3D-30d%2540d%26latest%3Dnow%26display.page.search.tab%3Devents%26sid%3D1585837234.15944_3C280DFD-CE09-4D68-858E-F772545BBA91
    // but using country="in"
    
    public static final String REQUEST_START = "<ContractFullInfoRequest xmlns=\"http://homecredit.net/homerselect/contract/v6\" xmlns:ns2=\"http://homecredit.net/homerselect/common/v1\" xmlns:ns3=\"http://homecredit.net/homerselect/contractbulk/v6\">";
    public static final String REQUEST_END = "</ContractFullInfoRequest>";
    
    public static final String SALESROOM_ENDPOINT = "https://homesis.in00c1.in.infra/homesis/restful/salesrooms/";
    public static final String PARTNERS_ENDPOINT = "https://homesis.in00c1.in.infra/homesis/restful/partners/";
    public static final String COMMODITY_TYPES_ENDPOINT = "https://commoditywl.in00c1.in.infra/commodity/openapi/v1/commodity-types/";
    public static final String COMMODITY_CATEGORIES_ENDPOINT = "https://commoditywl.in00c1.in.infra/commodity/openapi/v1/commodity-categories/";
    
    public static final String FILE_IN_PATH = "C:\\Users\\roman.stubna\\Desktop\\exports\\export_in_2_older.txt";
    public static final String FILE_OUT_PATH = "C:\\Users\\roman.stubna\\Desktop\\exports\\output_java_in_2_3.txt";
    
    public static final int MAX_ROWS = 6000;
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, InterruptedException, ParseException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        File file = new File(FILE_IN_PATH);
        Scanner reader = new Scanner(file);
        
        PrintWriter writer = new PrintWriter(FILE_OUT_PATH);
        
        int counter = 0;
        
        Map<String, String> commodityTypeToCategoryNameMap = new HashMap<>();
        
        while (reader.hasNextLine() && (counter < MAX_ROWS || MAX_ROWS == -1)) {
    
            String line = reader.nextLine();
            if (!line.contains(REQUEST_START) || !line.contains(REQUEST_END)) {
                continue;
            }
    
            StringBuilder sb = new StringBuilder();
            sb.append("=================\n");
    
            counter++;
            
            // only print out the first one to check that it's parsing right
            boolean initPrintOut = (counter == 1);
            
            if (initPrintOut) {
                System.out.println("=================");
            }
            
            
            int start = line.indexOf(REQUEST_START);
            int end = line.indexOf(REQUEST_END) + REQUEST_END.length();
            
            String request = line.substring(start, end);
    
            if (initPrintOut) {
                System.out.println(request);
            }
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(request)));
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
    
            // retrieve commodity category by commodity type code and add it to map so it doesn't have to call again next time
            if (!commodityTypeToCategoryNameMap.containsKey(commodityTypeCode)) {
                String commodityTypeDetailUri = COMMODITY_TYPES_ENDPOINT + commodityTypeCode;
                CloseableHttpClient httpClient = createTrustAllHttpClientBuilder().build();
                String encoding = Base64.getEncoder()
                                        .encodeToString(("homerselect:homerselect").getBytes());
    
                HttpUriRequest commodityTypeRequest = new HttpGet(URI.create(commodityTypeDetailUri));
                commodityTypeRequest.setHeader("User-Agent", "Java 11 HttpClient Bot");
                commodityTypeRequest.setHeader("Authorization", "Basic " + encoding);
                commodityTypeRequest.setHeader("Accept", "*/*");
                
                CloseableHttpResponse commodityTypeResponse = httpClient.execute(commodityTypeRequest);
                
                if (commodityTypeResponse.getStatusLine().getStatusCode() == 200) {
                    String commodityTypeResponseString = EntityUtils.toString(commodityTypeResponse.getEntity());
                    if (initPrintOut) {
                        System.out.println("Commodity response:" + commodityTypeResponseString);
                    }
                    JSONParser jsonParser = new JSONParser();
                    JSONArray jsonArray = (JSONArray) jsonParser.parse(commodityTypeResponseString);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    String commodityCategoryCode = (String) jsonObject.get("categoryCode");
                    
                    if (!commodityCategoryCode.isBlank()) {
                        String commodityCategoryDetailUri = COMMODITY_CATEGORIES_ENDPOINT + commodityCategoryCode;
                        HttpUriRequest commodityCategoryRequest = new HttpGet(URI.create(commodityCategoryDetailUri));
    
                        commodityCategoryRequest.setHeader("User-Agent", "Java 11 HttpClient Bot");
                        commodityCategoryRequest.setHeader("Authorization", "Basic " + encoding);
                        commodityCategoryRequest.setHeader("Accept", "*/*");
    
                        CloseableHttpResponse commodityCategoryResponse = httpClient.execute(commodityCategoryRequest);
                        
                        if (commodityCategoryResponse.getStatusLine().getStatusCode() == 200) {
                            String commodityCategoryResponseString = EntityUtils.toString(commodityCategoryResponse.getEntity());
                            if (initPrintOut) {
                                System.out.println("Commodity category response:" + commodityCategoryResponseString);
                            }
                            jsonArray = (JSONArray) jsonParser.parse(commodityCategoryResponseString);
                            jsonObject = (JSONObject) jsonArray.get(0);
                            JSONObject jsonObject1 = (JSONObject) jsonObject.get("name");
                            String commodityCategoryName = (String) jsonObject1.getOrDefault("EN", "");
                            
                            // could be empty, that's ok we still want to put it in the map to not repeat the call
                            commodityTypeToCategoryNameMap.put(commodityTypeCode, commodityCategoryName);
                            
                        }
                    }
                }
                
            }
    
            String commodityCategoryName = commodityTypeToCategoryNameMap.get(commodityTypeCode);
            
            
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
            vals.put("commodity.category", commodityCategoryName);
            
            
            String salesroomDetailsUri = SALESROOM_ENDPOINT + salesroomCode;
            CloseableHttpClient httpClient = createTrustAllHttpClientBuilder().build();
            String encoding = Base64.getEncoder().encodeToString(("homerselect:homerselect").getBytes());
            
            HttpUriRequest httpUriRequest = new HttpGet(URI.create(salesroomDetailsUri));
            httpUriRequest.setHeader("User-Agent", "Java 11 HttpClient Bot");
            httpUriRequest.setHeader("Authorization", "Basic " + encoding);
            httpUriRequest.setHeader("Accept", "*/*");
            
            CloseableHttpResponse srDetailResponse = httpClient.execute(httpUriRequest);
            
            if (srDetailResponse.getStatusLine().getStatusCode() == 200) {
                String respString = EntityUtils.toString(srDetailResponse.getEntity());
                if (initPrintOut) {
                    System.out.println("Salesroom response: " + respString);
                }
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(respString);
                String salesroomName = (String) jsonObject.get("name");
                String partnerCode = (String) jsonObject.get("partnerCode");
                String salesroomGpsLatitude = (String) jsonObject.get("gpsLatitude").toString();
                String salesroomGpsLongitude = (String) jsonObject.get("gpsLongtitude").toString();
                vals.put("salesroom.name", salesroomName);
                vals.put("salesroom.partnerCode", partnerCode);
                vals.put("salesroom.gpsLatitude", salesroomGpsLatitude);
                vals.put("salesroom.gpsLongtitude", salesroomGpsLongitude);
            }
            
            
            String salesroomAddressUri = SALESROOM_ENDPOINT + salesroomCode + "/addresses/SR_BUS";
            
            HttpUriRequest httpUriRequestAddress = new HttpGet(URI.create(salesroomAddressUri));
            httpUriRequest.setHeader("User-Agent", "Java 11 HttpClient Bot");
            httpUriRequest.setHeader("Authorization", "Basic " + encoding);
            httpUriRequest.setHeader("Accept", "*/*");
            
            
            CloseableHttpResponse addressResponse = httpClient.execute(httpUriRequestAddress);
            
            if (addressResponse.getStatusLine().getStatusCode() == 200) {
                String respString = EntityUtils.toString(addressResponse.getEntity());
                if (initPrintOut) {
                    System.out.println("Address response: " + respString);
                }
                vals.put("salesroom.addressInfo", respString);
                
            }
            
            String partnerCode = vals.get("salesroom.partnerCode");
            if (partnerCode != null && !partnerCode.equals("")) {
                String partnerUri = PARTNERS_ENDPOINT + partnerCode;
                
                HttpUriRequest httpUriRequestPartner = new HttpGet(URI.create(partnerUri));
                httpUriRequestPartner.setHeader("User-Agent", "Java 11 HttpClient Bot");
                httpUriRequestPartner.setHeader("Authorization", "Basic " + encoding);
                httpUriRequestPartner.setHeader("Accept", "*/*");
                
                CloseableHttpResponse partnerResponse = httpClient.execute(httpUriRequestPartner);
                
                if (partnerResponse.getStatusLine().getStatusCode() == 200) {
                    String respString = EntityUtils.toString(partnerResponse.getEntity());
                    if (initPrintOut) {
                        System.out.println("Partner response: " + respString);
                    }
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(respString);
                    vals.put("salesroom.partnerName", (String) jsonObject.get("name"));
                }
            }
            
            
            if (!getString("initTransactionType", root).equals("NDF")) {
                for (String key : vals.keySet()) {
                    sb.append(key)
                      .append(" = ")
                      .append(vals.get(key))
                      .append("\n" );
    
                    if (initPrintOut) {
                        System.out.println(key + " : " + vals.get(key));
                    }
                }
                writer.println(sb.toString());
            }
            
            if (counter % 10 == 0) {
                System.out.println("at: " + counter);
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

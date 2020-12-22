package org.romco;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.*;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.romco.Const.customEntrySeparator;

public class SplunkLogExtractor {
    
    // source data exported from https://splunk-pdc.cz.prod/en-GB/account/login?return_to=%2Fen-GB%2Fapp%2Fsearch%2Fsearch%3Fq%3Dsearch%2520country%253D%2522vn%2522%2520index%253D%2522hcg_bsl_prod%2522%2520ContractFullInfoRequest%2520%2522ContractSignSE%2522%26display.page.search.mode%3Dsmart%26dispatch.sample_ratio%3D1%26earliest%3D-30d%2540d%26latest%3Dnow%26display.page.search.tab%3Devents%26sid%3D1585837234.15944_3C280DFD-CE09-4D68-858E-F772545BBA91
    // but using country="in"
    
    private static final Logger log = LoggerFactory.getLogger(SplunkLogExtractor.class.getSimpleName());
    
    public static final String REQUEST_START = "<ContractFullInfoRequest xmlns=\"http://homecredit.net/homerselect/contract/v6\" xmlns:ns2=\"http://homecredit.net/homerselect/common/v1\" xmlns:ns3=\"http://homecredit.net/homerselect/contractbulk/v6\">";
    public static final String REQUEST_END = "</ContractFullInfoRequest>";
    
    public static final String SALESROOM_ENDPOINT = "https://homesis.in00c1.in.infra/homesis/restful/salesrooms/";
    public static final String PARTNERS_ENDPOINT = "https://homesis.in00c1.in.infra/homesis/restful/partners/";
    public static final String COMMODITY_TYPES_ENDPOINT = "https://commoditywl.in00c1.in.infra/commodity/openapi/v1/commodity-types/";
    public static final String COMMODITY_CATEGORIES_ENDPOINT = "https://commoditywl.in00c1.in.infra/commodity/openapi/v1/commodity-categories/";
    
    public static final String FILE_IN_PATH = "src\\main\\resources\\splunk-export\\contract-signed-export.txt";
    public static final String FILE_OUT_PATH = "src\\main\\resources\\generated\\output-splunk-extract.txt";
    
    // if you enter 100 or more, it's going to run for more then a minute
    public static final int MAX_ROWS = 2000;
    
    private static boolean initPrintOut = true;
    
    
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, ParseException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        extract();
    }
    
    public static void extract() throws ParserConfigurationException, XPathExpressionException, IOException, SAXException, NoSuchAlgorithmException, KeyStoreException, ParseException, KeyManagementException {
        File file = new File(FILE_IN_PATH);
        Scanner reader;
        PrintWriter writer;
        
        reader = new Scanner(file);
        writer = new PrintWriter(FILE_OUT_PATH);
        
        int counter = 0;
    
        Map<String, String> commodityTypeToCategoryNameMap = new HashMap<>();
    
        while (reader.hasNextLine() && (counter < MAX_ROWS || MAX_ROWS == -1)) {
        
            String line = reader.nextLine();
            if (!line.contains(REQUEST_START) || !line.contains(REQUEST_END)) {
                continue;
            }
        
            StringBuilder sb = new StringBuilder();
            sb.append(customEntrySeparator + "\n");
        
            counter++;
        
            // only print out the first one to check that it's parsing right
            if (counter > 1) initPrintOut = false;
        
            if (initPrintOut) {
                log.debug(customEntrySeparator);
            }
        
        
            int start = line.indexOf(REQUEST_START);
            int end = line.indexOf(REQUEST_END) + REQUEST_END.length();
        
            String request = line.substring(start, end);
        
            if (initPrintOut) {
                log.debug(request);
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
        
            // retrieve commodity category by commodity type code and add it to map so it doesn't have to call again next time
            if (!commodityTypeToCategoryNameMap.containsKey(commodityTypeCode)) {
                getCommodityDetails(commodityTypeCode, commodityTypeToCategoryNameMap);
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

            // get salesroom detail
            getSalesroomDetail(salesroomCode, vals);

            // get salesroom address details
            getSalesroomAddressDetail(salesroomCode, vals);

            // get partner details
            getPartnerDetails(vals);
        
            // filter out NDF transaction types
            if (!getString("initTransactionType", root).equals("NDF")) {
                for (String key : vals.keySet()) {
                    sb.append(key)
                      .append(" = ")
                      .append(vals.get(key))
                      .append("\n" );
                
                    if (initPrintOut) {
                        log.debug("{} : {}", key, vals.get(key));
                    }
                }
                writer.println(sb.toString());
            }
        
            // print out progress on every 10th processed entity
            if (counter % 10 == 0) {
                log.debug("at: {}", counter);
            }
        
        }
        
        reader.close();
        writer.close();
    }
    
    private static void getCommodityDetails(String commodityTypeCode,
                                              Map<String, String> commodityTypeToCategoryNameMap)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, ParseException  {
        
        String commodityTypeDetailUri = COMMODITY_TYPES_ENDPOINT + commodityTypeCode;
        
        // ideally, one instance of httpClient accross the class would be preferable, but when I tried implementing it, it kept freezing, probably due to too many requests.
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
            commodityTypeResponse.close();
            if (initPrintOut) {
                log.debug("Commodity response: {}", commodityTypeResponseString);
            }
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(commodityTypeResponseString);
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            String commodityCategoryCode = (String) jsonObject.get("categoryCode");
        
            if (!commodityCategoryCode.isBlank()) {
                String commodityCategoryDetailUri = COMMODITY_CATEGORIES_ENDPOINT + commodityCategoryCode;
                HttpUriRequest httpUriCommodityCategoryRequest = new HttpGet(URI.create(commodityCategoryDetailUri));
                setCustomRequestHeaders(httpUriCommodityCategoryRequest, encoding);
            
                CloseableHttpResponse commodityCategoryResponse = httpClient.execute(httpUriCommodityCategoryRequest);
            
                if (commodityCategoryResponse.getStatusLine().getStatusCode() == 200) {
                    String commodityCategoryResponseString = EntityUtils.toString(commodityCategoryResponse.getEntity());
                    if (initPrintOut) {
                        log.debug("Commodity category response: {}", commodityCategoryResponseString);
                    }
                    jsonArray = (JSONArray) jsonParser.parse(commodityCategoryResponseString);
                    jsonObject = (JSONObject) jsonArray.get(0);
                    JSONObject jsonObject1 = (JSONObject) jsonObject.get("name");
                    String commodityCategoryName = (String) jsonObject1.getOrDefault("EN", "");
                
                    // could be empty, that's ok we still want to put it in the map to not repeat the call
                    commodityTypeToCategoryNameMap.put(commodityTypeCode, commodityCategoryName);
                
                }
            }
        } else {
            log.info("Commodity request failed for commodityTypeCode {} with status code {}. This might be OK (404 is generally OK).", commodityTypeCode, commodityTypeResponse.getStatusLine().getStatusCode());
        }
        httpClient.close();
    }
    
    private static void getSalesroomDetail(String salesroomCode,
                                             Map<String, String> vals)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, ParseException {

        String salesroomDetailsUri = SALESROOM_ENDPOINT + salesroomCode;
        CloseableHttpClient httpClient = createTrustAllHttpClientBuilder().build();
        String encoding = Base64.getEncoder().encodeToString(("homerselect:homerselect").getBytes());

        HttpUriRequest httpUriSalesroomRequest = new HttpGet(URI.create(salesroomDetailsUri));
        setCustomRequestHeaders(httpUriSalesroomRequest, encoding);
    
        CloseableHttpResponse srDetailResponse = httpClient.execute(httpUriSalesroomRequest);
        
        if (srDetailResponse.getStatusLine().getStatusCode() == 200) {
            String respString = EntityUtils.toString(srDetailResponse.getEntity());
            srDetailResponse.close();
            if (initPrintOut) {
                log.debug("Salesroom response: {}", respString);
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
        } else {
            log.info("Salesroom request failed for salesroomCode {} with status code {}. This might be OK (404 is generally OK).", salesroomCode, srDetailResponse.getStatusLine().getStatusCode());
        }
        httpClient.close();

    }
    
    private static void getSalesroomAddressDetail(String salesroomCode, Map<String, String> vals)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, ParseException {
        String salesroomAddressUri = SALESROOM_ENDPOINT + salesroomCode + "/addresses/SR_BUS";
        String encoding = Base64.getEncoder().encodeToString(("homerselect:homerselect").getBytes());
        
        HttpUriRequest httpUriRequestAddress = new HttpGet(URI.create(salesroomAddressUri));
        setCustomRequestHeaders(httpUriRequestAddress, encoding);
        
        CloseableHttpClient httpClient = createTrustAllHttpClientBuilder().build();
        CloseableHttpResponse addressResponse = httpClient.execute(httpUriRequestAddress);
        
        if (addressResponse.getStatusLine().getStatusCode() == 200) {
            String respString = EntityUtils.toString(addressResponse.getEntity());
            addressResponse.close();
            if (initPrintOut) {
                log.debug("Address response: {}", respString);
            }
            vals.put("salesroom.addressInfo", respString);
        } else {
            log.info("Salesroom address request failed for salesroomCode {} with status code {}. This might be OK (404 is generally OK).", salesroomCode, addressResponse.getStatusLine().getStatusCode());
        }
        httpClient.close();
    }
    
    private static void getPartnerDetails(Map<String, String> vals)
            throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, ParseException {
    
        String partnerCode = vals.get("salesroom.partnerCode");
        if (partnerCode != null && !partnerCode.equals("")) {
            String partnerUri = PARTNERS_ENDPOINT + partnerCode;
            String encoding = Base64.getEncoder().encodeToString(("homerselect:homerselect").getBytes());
        
            HttpUriRequest httpUriRequestPartner = new HttpGet(URI.create(partnerUri));
            setCustomRequestHeaders(httpUriRequestPartner, encoding);
    
            CloseableHttpClient httpClient = createTrustAllHttpClientBuilder().build();
            CloseableHttpResponse partnerResponse = httpClient.execute(httpUriRequestPartner);
            
            if (partnerResponse.getStatusLine().getStatusCode() == 200) {
                String respString = EntityUtils.toString(partnerResponse.getEntity());
                partnerResponse.close();
                if (initPrintOut) {
                    log.debug("Partner response: {}", respString);
                }
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(respString);
                vals.put("salesroom.partnerName", (String) jsonObject.get("name"));
            } else {
                log.info("Partner request failed for partnerCode {} with status code {}. This might be OK (404 is generally OK).", partnerCode, partnerResponse.getStatusLine().getStatusCode());
            }
            httpClient.close();
        }
    }
    
    private static String getString(String tagName, Element element) {
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
    
    private static void setCustomRequestHeaders(HttpUriRequest request, String encoding) {
        request.setHeader("User-Agent", "Java 11 HttpClient Bot");
        request.setHeader("Authorization", "Basic " + encoding);
        request.setHeader("Accept", "*/*");
    }
}

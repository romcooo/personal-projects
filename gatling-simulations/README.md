# Gatling Simulation 
This project aims at providing foundations for load tests.

Test scenarios are scripted via Scala classes which gives the authors huge flexibility.

# Getting Started
First of all, get your IntelliJ Idea IDE and download Scala plugin. It will be followed by IDE restart.

Once done, you need to install Scala SDK. These are the steps describing how to do that:
1. Go to the *Project Structure* 
2. Navigate to *Platform Settings > Global Libraries*
3. Add new one and select *Scala SDK* 
4. Choose version *scala-sdk-2.12.10* (version 2.13.x did not work)
5. Select *src>test>scala* and mark directory as *Test Sources Root*
6. Rebuild the project

Optionally, you can start mock server (e.g. Wiremock) with given request mappings and mapped responses.
Should you decide to use Wiremock, you can find Wiremock resources in *src>test>resources>wiremock-resources*.
It is fine to copy them to the folder where your Wiremock jar exists.

You can start it easily from CLI.

**Example**
```$bash
java -jar wiremock-standalone-2.26.3.jar --port 9999
```
# Build and Test
## Running in IDE
When having all steps from the above completed, rebuild the project.

To start the scenarios/simulations in IDE, run *src>scala>Engine* object. It will ask you which simulation you wish to launch in console.
You will pick the desired one by providing its number. 

## Running from Command Line Using Maven
You are free to use Maven instead. 

The simulation can be launched like this

**Example**
```$bash
mvn gatling:test -Dgatling.simulationClass=net.homecredit.koyal.salesfeed.SalesFeedSimulation 
```

It will launch the test with default CLI arguments:
- *numberOfUsers*
    - 10
- *durationInSeconds*
    - 10
- *targetUrl*
    - http://localhost:9999

If you need to override it, provide required values as CLI arguments:

**Example**
```$bash
mvn gatling:test -Dgatling.simulationClass=net.homecredit.koyal.salesfeed.SalesFeedSimulation -DnumberOfUsers=5 -DdurationInSeconds=30 -DtargetUrl=some_url
```

## Running from Command Line Using Gatling Standalone Bundle
Download Gatling [binary](https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/3.3.1/gatling-charts-highcharts-bundle-3.3.1-bundle.zip) and extract it locally. 

More details regarding configuration can be found [here](https://gatling.io/docs/current/general/configuration).

## Contribute
When you need to add more scenarios/simulations, take a look at *net.homecredit.koyal.salesfeed.SalesFeedSimulation* as example. 

It defines various Gatling simulation resources in structured way instead of single file.

Please note that the given structure will help to write cleaner simulations.
 

# Links
- [Gatling](https://gatling.io/)
- [Pebble Templating Engine](https://pebbletemplates.io/)
- [Wiremock](http://http://wiremock.org/)

# Input data
- input data to be used in tests is located in src/test/resources/data, generally in csv format
- the format of the csvs is as follows:
    - first line always contains the name of the attribute in the given column
    - the remaining lines represent individual "records"
    - lines starting with "##" are considered comments and are ignored during parsing

## Sourcing the input data
Current process involves the following steps (feel free to do it your way):
1. Gathering the request data from splunk using the following filter and exporting it (set your desired time period):  
    ```
   country="in" index="hcg_bsl_prod" ContractFullInfoRequest "ContractSignSE"    
   ```
    https://splunk-pdc.cz.prod/en-GB/app/search/search?q=search%20country%3D%22vn%22%20index%3D%22hcg_bsl_prod%22%20ContractFullInfoRequest%20%22ContractSignSE%22&display.page.search.mode=smart&dispatch.sample_ratio=1&earliest=-30d%40d&latest=now&display.page.search.tab=events&sid=1606996893.7859_F5E1DD07-A071-4C8E-9542-329BB2319BEE
    
2. Gathering additional data regarding the following:
      - salesroom details at "https://homesis.in00c1.in.infra/homesis/restful/salesrooms/";
      - partner details at "https://homesis.in00c1.in.infra/homesis/restful/partners/";
      - commodity details at "https://commoditywl.in00c1.in.infra/commodity/openapi/v1/commodity-types/";
      and "https://commoditywl.in00c1.in.infra/commodity/openapi/v1/commodity-categories/";
      
3. Formatting the data to the expected gatling input format (csv files)

# Recorded Video Overview
Here's a 20 minute overview (in Slovak) of the application:
https://web.microsoftstream.com/video/1325f3ec-f9a6-4990-8094-2fb6b7be4504
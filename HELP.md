# Simplewarehouse

Simplewarehouse is build with technology
 - Spring Boot Rest Webservice
 - H2 in memory database
 - Spring JPA Query
 
It can be launched from main method SimplewarehouseApplication.
 
Simplewarehouse can download excell file from here:
http://adverity-challenge.s3-website-eu-west-1.amazonaws.com/PIxSyyrIKFORrCXfMYqZBI.csv
parse it and initialize its own in memory database.
To achieve that PUT request is needed - http://localhost:8080/simplewarehouse/init

1. http://localhost:8080/simplewarehouse/datasourceList
List all datasources

2. Total Clicks for a given Datasource for a given Date range 
http://localhost:8080/simplewarehouse/totalclicks

params (all optional):
datasource:<datasourcename>,
campaign=<campaignname>,
startDate:<startdate>, - ISO Format
endDate:<enddate> - ISO Format

examples:
http://localhost:8080/simplewarehouse/totalclicks?datasource=Facebook Ads&from=2020-02-02&to=2021-02-02
http://localhost:8080/simplewarehouse/totalclicks?campaign=Adventmarkt%20Touristik
http://localhost:8080/simplewarehouse/totalclicks?from=2010-02-02&to=2021-02-02

3. Click-Through Rate (CTR) per Datasource and Campaign 

params (all optional):
datasource:<datasourcename>,
campaign=<campaignname>,
startDate:<startdate>, - ISO Format
endDate:<enddate> - ISO Format

example:
http://localhost:8080/simplewarehouse/ctr?from=2010-02-02&to=2021-02-02

4. Impressions

params (all optional):
datasource:<datasourcename>,
campaign=<campaignname>,
startDate:<startdate>, - ISO Format
endDate:<enddate> - ISO Format

example:
http://localhost:8080/simplewarehouse/impressions?campaign=SN_KochAbo&from=2010-02-02&to=2021-02-02






# EPGP BACKEND SERVER

What this does:
- provides a java backend for https://github.com/Purple-bloom/EPGPHelperFrontend
- offers launch options for database connection, port and username/password for admin access
- offers (together with the fronted) a possibility to maintain players, reward them with EP, add GP for them, fix values by hand, detailed logging of all changes and table views of everything.


# Setup:
- install a database, preferably a postgresql instance and set it up. Initiate it by running AddRaids.sql and AddTables.sql in /src/main/resources/SQL/EPGP
- clone this repository to your homeserver / local machine
- set up a script to start the backend server like follows:
- ./gradlew bootRun --args='--spring.datasource.url=jdbc:postgresql://YOURDATABASEADDRESS:DATABASEPORT/DATABASENAME --spring.datasource.username=dbUsername --spring.datasource.password=dbPassword --server.port=port --username=admin --password=123456'
- explanation of the individual args:
- spring.datasource.url is your database url, please follow the schema provided
- spring.datasource.username is your database username
- spring.datasource.password is your database password
- server.port is the port for this backend server to run on - this will determine what you have to configure for the frontend server!
- username & password are the credentials to access the "admin" functions such as adding and modifying players, actually handing out points etc.

Your application.properties in src/main/resources also needs to be configured!
by default it lists a bunch of http://localhost:1234 addresses - this needs to be changed depending on how/where you host your frontend. If you intend to open this to the internet / others you have to configure your public IP / DNS including port, for example https://test.ddns.net:12345



An example linux bash script to start this server could look like this:
#!/bin/bash
cd /pathToGetHere/EPGPHelper
./gradlew bootRun --args='--spring.datasource.url=jdbc:postgresql://192.168.178.101:10025/epgpDatabase --spring.datasource.username=admin --spring.datasource.password=123456 --server.port=12345'


Link to frontend: https://github.com/Purple-bloom/EPGPHelperFrontend
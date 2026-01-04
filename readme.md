start with ./gradlew bootRun  
start frontend with npm start  
set up a postgresql database, initiate it with the addTables.sql and AddRaids.sql in /src/main/resources/sql/EPGP  

Your application.properties in src/main/resources should look like this:  
spring.application.name=GuildHelper

Start the app like so:
#!/bin/bash

cd /pathToGetHere/EPGPHelper

./gradlew bootRun --args='--spring.datasource.url=jdbc:postgresql://yourDBIP:port/dbName --spring.datasource.username=dbUsername --spring.datasource.password=dbPassword --server.port=port'

Then set up the frontend: https://github.com/Purple-bloom/EPGPHelperFrontend

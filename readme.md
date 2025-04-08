start with ./gradlew bootRun
start frontend with npm start
set up a postgresql database, initiate it with the addTables.sql and AddRaidsAndBosses.sql in /src/main/resources/sql/EPGP

Your application.properties in src/main/resources should look like this:
spring.application.name=GuildHelper
spring.datasource.url=jdbc:postgresql://IP:Port/DBName
spring.datasource.username=username
spring.datasource.password=password
server.port=port
# EPGP BACKEND SERVER

What this does:
- provides a java backend for https://github.com/Purple-bloom/EPGPHelperFrontend
- offers launch options for database connection, port and username/password for admin access
- offers (together with the fronted) a possibility to maintain players, reward them with EP, add GP for them, fix values by hand, detailed logging of all changes and table views of everything.


# Setup:
- clone this repository to your homeserver / local machine
- set up a script to start the backend server like follows (this script needs to target gradlew in the install folder, so maybe just put your script there):
- ./gradlew bootRun --args='--spring.datasource.url=jdbc:sqlite:GuildEpgp.db --server.port=port --username=admin --password=123456'
- explanation of the individual args:
- spring.datasource.url is your database url, you can replace "GuildEpgp.db" with the name of your guild if you wish. If you run multiple guilds, you can just run seperate instances with different db names!
    - example script guild 1: ./gradlew bootRun --args='--spring.datasource.url=jdbc:sqlite:Guild1.db --server.port=port --username=admin --password=123456'
    - example script guild 2: ./gradlew bootRun --args='--spring.datasource.url=jdbc:sqlite:Guild2.db --server.port=port --username=admin --password=123456'
- server.port is the port for this backend server to run on - this will determine what you have to configure for the frontend server!
- username & password are the credentials to access the "admin" functions such as adding and modifying players, actually handing out points etc.

Your application.properties in src/main/resources also needs to be configured!
by default it lists a bunch of http://localhost:1234 addresses - this needs to be changed depending on how/where you host your frontend. If you intend to open this to the internet / others you have to configure your public IP / DNS including port, for example https://test.ddns.net:12345



An example linux bash script to start this server could look like this:<br>
#!/bin/bash<br>
cd /pathToGetHere/EPGPHelper<br>
./gradlew bootRun --args='--spring.datasource.url=jdbc:sqlite:Guild1.db --server.port=12345 --username=admin --password=123456'<br>


Link to frontend: https://github.com/Purple-bloom/EPGPHelperFrontend
Link to compatible addon: https://github.com/Purple-bloom/EpgpWhisperer
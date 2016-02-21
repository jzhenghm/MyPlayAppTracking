# MyPlay Application traking #

This is a web-based application to tracing applications deployed in the serveral environment: QA, stage, Labs, Production. 
The application is developed in Playframework. H2 in-memory database is used as backend database. I use Play evolution to run DDL to define data base tables populate sample data when the application is starting

## Buid Project ##
The project is buit using sbt
To package the application run dist in play2 console

## Run Project ##
After the project is successfully built and packaged a deployable package is created in <project root>/target/universal/myplay-1.0-SNAPSHOT.zip

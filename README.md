# App deployment traking #

## Overview ##
This is a web-based application to tracing applications deployed in the serval environment: QA, stage, Labs, Production.
The application is developed in Scala Play framework.

* Th project is implemented in Play framework MVC pattern. The class packages are view, controller, model and data access layer. REST service endpoint is configured in the file `config\routes`
* Relational database is the data storage of the application. In the data access layer tables are mapped to Scala collection using Play Slick API.
* For the demo purpose, I set H2 in-memory database as default configuration. when the application is started up database is initialized and sample data is inserted with Play Evolution. (see the scripts in `config\envolutions\default`)


## Build Project ##
The project is built using Typesafe activator. To start activator console, you can open a terminal on the project root and enter `activator`. it starts a play2 console. Then you can enter following command to build and run the application:

`[myplay] $ ~ ;clean;compile;run`

To package the application executable, run command in play2 console:

`[myplay] $ dist`

It generates a package `/target/universal/myplay-1.0-SNAPSHOT.zip`.

## Run Application ##
The Play2 project includes an embed webserver - Nettyserver. So we don't need to use any other http server to run this application. The steps to start and test the application are following:
1. Unzip the the zip file `myplay-1.0-SNAPSHOT.zip`
2. the run the shell script `./myplay-1.0-SNAPSHOT/bin/myplay`(run `myplay.bat` in windows environment)
* Open a web browser enter `http://localhost:9000/` launches the index page of the app.
* Select show all history and click search button all the records of deployment history are display on the main frame.
* To check what version the e-commerce website is in production: enter
  `app:e-commerce web,  env:prod, show current:true` .
* To check history of production deployment: select `Show all history` and `env:prod`.
* To find deployment details of a particular version of app in production, you can enter the app name, version and select env than click search.


## Integrate with version registration ##
The application tracking application provides a REST endpoint `http://localhost:9000/journal/add` for adding a new deployment entry. This REST api is a post method to send a Json object.


To update database automatically the endpoint has to be integrated with deployment/delivery tool. For example to Integrate this tracking application with Chef, we can include an http request callback recipe includes in the recipes in the cookbook of application deployment activities. The recipe for the http request call is the following:

```ruby
http_request 'add_deploy' do
action :post
url 'http://localhost:9000/journal/add'
message ({'app' => :app, 'env' => :env, 'version' => :version, 'date' => :date, 'username'=> :uid }.to_json end
```

I don't have project setup in Chef server to test deployment update. but We can do it with `curl` command on terminal:

`curl -X POST -H "Content-Type: application/json"
-d '{"app":"e-commerce web","version":"0.1.2","env":"prod","username":"mack","date":"2016-02-01"}' http://localhost:9000/journal/add`

To check the tracking application updated with the new insertion, we can search current deployment on web interface and we will see the latest deployment we just added is at the top of search results.

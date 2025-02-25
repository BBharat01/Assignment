# Assignment
Take home test

#### This is a springboot application , you need to have java/maven setup to run this app. Maven setup : https://maven.apache.org/install.html

#### How to run this application

* Build it once for sanity , run ->  `mvn clean install` ( Terminal commands )
* run -> `mvn spring-boot:run` to start the application and tomcat server
* You should see following logs after succesfull start-up
  * INFO 99122 --- [ledger] [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:ledgerdb'
  * INFO 99122 --- [ledger] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
  * INFO 99122 --- [ledger] [           main] c.t.assignment.ledger.LedgerApplication  : Started LedgerApplication in 1.57 seconds (process running for 1.702)

* This application is swagger enabled , so the APIs can be accessed through swagger-ui , Go to: `http://localhost:8080/swagger-ui.html` for localhost run.
* For Curl refer following
  * curl -X POST "http://localhost:8080/ledger/deposit?amount=200"
  * curl -X POST "http://localhost:8080/ledger/withdraw?amount=50"
  * curl -X GET "http://localhost:8080/ledger/balance"
  * curl -X GET "http://localhost:8080/ledger/history"
* It uses H2 database to store the trabsactions
 


  

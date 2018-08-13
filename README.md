# com.mycompany.notifier_webapp
Application to send notifications

To build and run

1. mvn clean package
2. docker build -t mycompany.notifier_webapp .
3. docker run -p 8080:8080 --link mycompany.db:mycompany.db --link mycompany.redis:mycompany.redis --name mycompany.notifier_webapp mycompany.notifier_webapp

To build and push

1. docker build -t mycompany.notifier_webapp .
2. docker tag mycompany.notifier_webapp a142857/mycompany.notifier_webapp:1.0-SNAPSHOT
3. docker push a142857/mycompany.notifier_webapp

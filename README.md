# helidon-simple-server-example
## Build and run
With JDK21
```bash
mvn package
java -jar target/myproject.jar
```

## Exercise the application
Simple chat functionality: 
Each connection needs to identify themselves first using a simple json having an id.
After id has been authorised, you can send messages as per the following format:
        {"recipients":[x,y],"sender":"x","message":"sample message"}
We cant send messages unless identification has been done.
Client 1:
```cmd
wscat -c ws://localhost:8080/message
> {"id":"1"}
< hello 1
> {"recipients":[1,2], "sender":"1", message:"hi how are you"}
< 1 -> hi how are you
< 2 -> im good
```

Client 2:
```cmd
wscat -c ws://localhost:8080/message
> {"id":"2"}
< hello 2
< 1 -> hi how are you
> {"recipients":[1,2], "sender":"2", message:"im good"}
< 2 -> im good 
```
Basic:
```cmd
curl -X GET http://localhost:8080/simple-greet
Hello World!
```


JSON:
```cmd
curl -X GET http://localhost:8080/greet
{"message":"Hello World!"}

curl -X GET http://localhost:8080/greet/Joe
{"message":"Hello Joe!"}

curl -X PUT -H "Content-Type: application/json" -d '{"greeting" : "Hola"}' http://localhost:8080/greet/greeting

curl -X GET http://localhost:8080/greet/Jose
{"message":"Hola Jose!"}
```




                                

# WEB DEMO

This is a demonstration of a simple web interface using Thymeleaf that accesses data over an http api call to
the project api-demo

## Compile

This is intended to be compiled using java 17 at a minimum

```
$ mvn clean install
```

## Run

In the target directory

```
$ java -jar web-demo*.jar
```
## Configuration

Spring boot by default takes it's configuration from the file  __application.properties__ which,
as in this case, can be on the classpath (taken from src/main/resources) or in the same directory
the exeutable jar.

Configuration can be extended by marking a class with the __@Configuration__ annotation
There is one Configuration class in this application __Configurator__, note that this also has
the annotation __@PropertySource__ which allows configuration to be obtained from another file,
in this case __extra.properties__ on the class path.

Note that __application.properties__ is still read first, if there is a clash, that is both files
contain the same property the one loaded latest will take presidence.

In __application.properties__

__server.port: 8002__ sets the server to listen on port 8082.

This file also configures logging, see the __sectionLogging__.

In __extra.properties__ the property 
__api-conf-base: http://localhost:8081/api__ is used to define where the API
is, this is read in __Configurator__ with the lines

__@Value("${api-conf-base}")
private String base;__

Which says automatically set the value oif String __base__ to the property value of __api-conf-base__
which as stated above is in the file __extra.properties__ though it could have been in __application.properties__.

The __@Bean__ annotation on the method __getRestTmplate__ indicates to spring that 
this should be the source of a RestTemplate bean (instantiated class) should it need to inject
such into another bean it creates.

## Logging
Logging uses __Slf4J__ which is a very commonly used logging system.
System.out is very rarely used for logging and is considered bad form.

The various logging systems that exist have the adventage that they can be switched on and off via a
configuration file and similarly various levels selected.

So you can pick the single class you are worried about and display debug logging for that class but only log errors for the rest.

It is configured in __application.properties__ using the lines
```
logging.level.root=info
logging.level.uk.co.rpl=debug
logging.pattern.console=%d{dd-MM-yyyy HH:mm:ss.SSS} %magenta([%thread]) %highlight(%-5level) %logger.%M - %msg%n
```

There are multiple logging __"Levels"__ __ERROR INFO WARN DEBUG TRACE__, this is an ordered list from most 
catastrofic to least, if you pick a level then that level and anything more important is logged.

In the configuration above, 
```
logging.level.root=info
```
means only log __INFO__ and __ERROR__, but the line  
```
logging.level.uk.co.rpl=debug
```
Means that the package __uk.co.rpl__ should be logged for __ERROR INFO WARN__ and  __DEBUG__.

So any classes in or below the package __uk.co.rpl__ will be logged for  __ERROR INFO WARN__ and  __DEBUG__
and any other classes (notably those in Spring itself in this case will only be logged for __ERROR__ and __INFO__.

Normally a class will contain a variable called log or similar with the line

```
private static final LOG = LoggingFactory.getLogger(ClassName.class);
```

This logger (LOG) can then be used to output messages

```
LOG.debug("The value of fred is {}", fred);
```

and similar.

In this case the annotation __@Slf4j__ automatically generates the log creating line producing a variable called __log__.

So we get:
```
log.debug("The value of fred is {}", fred);
```
This will send the message to the logging system replacing the {} with the value of fred generally by calling it;'s toString() method.

## Controllers

There is only 1 controller which is an end point for __/__, the method signature is:
```
public String start(Model model)
```
The String returned by this method is the name of an HTML template stored under
__src/main/resources/tamplates__ with the file extension __.html__.

Model is effectively a map of names to data, this is used to populate data onto the 
HTML which is rendered by Thymeleaf.

So this method calls the __DemoAccessService__ which obtains data via an api call, 
the data returned is stored in __Model__.

This method stores in a __name__, __lastName__ and __allUsers__ values in the __Model__
and then returns the string __"home"__.

The Spring mvc system will then lookup the file __src/main/resources/templates/_home_.html__
and passes it to Thymeleaf for rendering.

Thymeleaf modifies the html through attributes starting__th:__ added to elements.

Elements of note within the __home.html__ page are:

```
<td th:text="${lastName}">ssss</td>
```
ssss is replaced with the value lastName from __Model__, not that __ssss__ is special,
it is just that the value within the td elemnt is replaced by the value specified
in the __th:text__ attribute.

allUsers in the Model is a list, the html elemnts:
```
        <tr th:each="entry: ${allUsers}" style="">
            <td th:text="${entry.forename}"></td>
            <td th:text="${entry.surname}"></td>
        </tr>
```
are repeated once for each element in the __allUsers__ list from __Model__, each time setting the variable
__entry__ to be the next value from the list, these are rendered in the td elements producing an output like:

```
       <tr style="">
            <td>John</td>
            <td>Smith</td>
        </tr>
        <tr style="">
            <td>Mary</td>
            <td>Jones</td>
        </tr>
        <tr style="">
            <td>Jennifer</td>
            <td>Eccles</td>
        </tr>
        <tr style="">
            <td>Jack</td>
            <td>Spratt</td>
        </tr>
 
```

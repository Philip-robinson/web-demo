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

Configuration is in the internal __application.properties__ file

server.port is set to 8082

It is assumed that api-demo will be available on http://localhost:8081

The class __Configurator__ reads the value __api-conf-base__ from application.properties
this is the base address of the api that this application uses to get it's needed data.

It also generates an instance of the Template class (A spring utility to make restfull calls)
which is used to access the API to get the required data.

## Controllers

There is only 1 controller which is an end point for __/__, th method signature is:
```
public String start(Model model)
```
The String returned by this method is the name of an HTML template stored under
__src/main/resources/tamplates__ with the file extension __.html__.

Model is effectively a map of names to data, this is used to populate data onto the 
HTML which is rendered by Thymeleaf.

So tis metod calls te __DemoAccessService__ which obtains data via an api call, 
the data returned is stored in __Model__.

This method stores in a __name__, __lastName__ and __allUsers__ values in the __Model__
and then return the string __"home"__.

The Spring mvc system will then lookup the file src/main/resources/templates/__home__.html
and passes it to Thymeleaf for rendering.

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

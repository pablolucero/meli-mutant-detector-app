# Ejercicio de programación para Mercado Libre

# Mutant detector API
### Objetivo
Detectar si un ADN es mutante o no usando como input una secuencia de 6 cadenas de ADN. Esto se da cuando se encuentra
*mas de una* secuencia de 4 caracteres iguales, ya sea de manera horizontal asi como vertical o diagonalmente. 

Los caracteres validos son A, T, G y C, los cuales representan cada base nitrogenada del ADN.

### Stack utilizado
- Java 8
- H2
- MySQL 8 o MariaDB 10
- Tomcat server 8.5
- Apache Maven 3.5
- Spring Boot 2.0
- Jacoco Junit libs
### Deploy en la nube
- Heroku cloud engine alojado en [https://meli-mutant-detector-app.herokuapp.com/](https://meli-mutant-detector-app.herokuapp.com/)
- MySQL deployado utilizando el plugin de ClearDB para Heroku 

Al estar utilizando el plan gratuito de Heroku, la primera vez que se acceda al servicio el mismo tardara unos
segundos en iniciar ya que después un tiempo sin uso el mismo se apaga. Una vez hecha la primer llamada,
las subsiguientes ya se realizan en el tiempo esperable.

### Instrucciones para instalación local 
- Descargar e instalar [Java SDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html?ssSourceSiteId=otnes)
- Descargar e instalar [Apache Maven](https://maven.apache.org/download.cgi). Si tiene algún problema o no desea 
instalarlo puede utilizar el Maven Wrapper que viene dentro del repositorio reemplazando el comando ```mvn``` por ```./mvnw```. 
- Por defecto el proyecto utiliza una base de datos en memoria (H2) que puede accederse desde 
[http://localhost:8080/h2-console](http://localhost:8080/h2-console) y no necesita mayor configuración.
En caso de querer utilizar la consola de H2 recuerde revisar que la ```JDBC URL``` a la que se conecta sea 
```jdbc:h2:mem:mutantdb```. El user es ```sa``` y el password queda vacío.
- Si desea que los datos se persistan una vez apagado el proyecto debe descargar e instalar MySql o MariaDB 
(la opción mas fácil es via [XAMPP](https://www.apachefriends.org/es/index.html)) y comentar en el 
[application.properties](https://github.com/pablolucero/meli-mutant-detector-app/blob/master/src/main/resources/application.properties)
 las configuraciones correspondientes a H2 y descomentar las de MySQL. También debe crear a mano una base de datos con
 el nombre ```mutantdb```.

- Clonar [este repositorio](https://github.com/pablolucero/meli-mutant-detector-app)
- Ubicarse en el directorio raíz del proyecto y correr el comando 
 ```mvn clean package```
- Ejecutar ```mvn spring-boot:run``` o ubicarse en la carpeta target del proyecto y ejecutar 
```java -jar mutant-detector-app-1.0.jar```

### Uso
Una vez que la aplicación este levantada los endpoints de la API pueden ser testeados de manera local usando un cliente 
REST como [Postman](https://www.getpostman.com/) o desde la consola si se tiene cURL instalado.

API Mutant
-----
#### Caso 1: ADN mutante
A continuación se muestran ejemplos concretos de request y response con la API. La url corresponde a un
deploy local en ```localhost:8080```. Si se quiere utilizar la version ya deployada en la nube, reemplazar 
```localhost:8080``` por la url ```meli-mutant-detector-app.herokuapp.com``` como se muestra en los ejemplos con cURL.  
```
POST localhost:8080/mutant
{
    "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```
o
```
curl -v -X POST https://meli-mutant-detector-app.herokuapp.com/mutant -H 'Content-Type: application/json' -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'
```
```
Response: 200 - OK
```
#### Caso 2: ADN *no* mutante
```
POST localhost:8080/mutant
{
	"dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGACGG", "GACCTA", "TCACTG"]
}
```
o
```
curl -v -X POST https://meli-mutant-detector-app.herokuapp.com/mutant -H 'Content-Type: application/json' -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGACGG","GACCTA","TCACTG"]}'
```
```
Response: 403 - Forbidden
```
API Stats
-----
```
GET localhost:8080/stats
``` 
o 
```
curl -v https://meli-mutant-detector-app.herokuapp.com/stats -H 'Content-Type: application/json'
```
```
Response 200 - OK 
{
    "count_mutant_dna": 2,
    "count_human_dna": 2,
    "ratio": 1
}
```
----
## Tests
### Unit Test / Code coverage

Para correr el reporte de code coverage cubierto por los tests unitarios debe posicionarse con la consola 
en el directorio raiz del proyecto y ejecutar ```mvn test```.
Al terminar el comando el reporte estará ubicado dentro de la carpeta 
\meli-mutant-detector-app\target\jacoco-ut\index.html
 
#### Porcentaje de code coverage: 85%

### Test de integración
Para correr los test de integración ejecutar el comando ```mvn verify```. Esto correrá primero los 
tests unitarios y, en caso de no fallar, continua con los de test de integración.


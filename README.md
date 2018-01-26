# EJERCICIO JAVA -> MAVEN -> java.util.concurrent

Reto JAVA

## MODELO

![alt text](https://raw.githubusercontent.com/docampog30/CallCenter/master/callcenter/Diagrama%20Clases.png)


## EXPLICACIÓN

Para la solución de este ejercicio se optó por utilizar Java Executor Service que pertenece al API de Java7 y es una de las clases que nos permite gestionar la programación concurrente de una forma más sencilla y optima.

En esta solución tenemos una clase Dispatcher y Queue manager que son las encargadas de asignar y procesar las llamadas que vienen a traves de los objetos ConsumerCall y Producer Call.

Ademas para la asignación de mensajes se utilizo el paquete java.util.concurrent el cual nos permite manejar de forma segura las peticiones concurrentes con objetos como ConcurrentLinkedQueue (utilizado para la cola de pendientes), LinkedBlockingQueue (utilizado para los roles empleado, supervisor y director) y finalmente ConcurrentSkipListMap para permitir una facil gestión y asignación de los mensajes a partir de las reglas de negocio dadas en el ejercicio.

Para una evolución de la evolución se podría plantear el tema de colas con JEE (JMS), para obtener un producto mas robusto y que se gestione automaticamente desde un provider especialmente enfocado para esto (activemq rabbitmq, hornetq).

Se generan test a las clases/metodos que se consideran mas importantes dentro de la aplicación y test solicitado en los requerimientos,

## Extras/Plus
Dar alguna solución sobre qué pasa con una llamada cuando no hay ningún empleado libre
Dar alguna solución sobre qué pasa con una llamada cuando entran más de 10 llamadas concurrentes.
	
Existe una cola sin limite que maneja estos mensajes los cuales se vuelven a reintentar asignar a algun empleado cuando esté libre. 
	


## RUN

    mvn clean install

Los resultados del comportamiento de la aplicación se pueden observar por consola.
 
## DEMO

Run> App.java, en esta clase existe un caso de ejemplo
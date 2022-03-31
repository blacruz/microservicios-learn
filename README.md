# microservicios-learn
Repositorio donde colocaré los proyectos que voy haciendo siguiendo un curso de microservicios


## config-service
Primero comencé con este proyecto, lo generé usando el Spring Initializr, con las dependencias de cloud config server
En el archivo de propiedades le indiqué en que puerto va a escuchar: 8888
Adicionalmente le indiqué que trabajase con fiches de configuración, que están alojados en la carpeta _cloud-config del mismo repositorio

## eureka-service
En segundo lugar generé este proyecto para el descubrimiento de servicios Netflix Eureka, con esto me aseguro que los microservicios que voy a generar tengan un mecanismo de descubrimiento de servicios, a partir de acá, como usamos Spring Cloud config, para la configuración  se debe usar el archivo bootstrap.properties e indicarle la url donde se encuentra el servicio config-service.

## Dockerfile
Para no estar iniciando estos proyectos desde mi maquina y ahorrar recursos, generé el archivo Dockerfile para contenerizar estos 2 proyectos (config-service y eureka-service) los pasos del contenedor son basicamente contenerizar un maven, hacer el build del maven y usar el openjdk-11 para ejecutar ambos servicios.

## gateway-service
Este servicio, lo generé también con el spring initializr, pero con la dependencia gateway, esto lo que hará es registrarse en eureka y el resto de servicios que vienen (security, inventory, etc.) se registren en eureka. además de proporcionar una puerta unica de entrada de requests y multiples instancias de los microservicios.
La idea es que este gateway se comunique con el security-service y gestionar la seguridad a través de JWT.

## security-service
Este servicio tendrá como finalidad estudiar el principio TDD, además de gestionar toda la seguridad del ecosistema.

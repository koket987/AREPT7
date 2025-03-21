# Micro Servicios

En este taller vamos a diseñar un API y crear un monolito Spring que permita a los usuarios hacer posts de 140 caracteres e ir registrandolos en un stream único de posts (a la Twitter). Piense en tres entidades Usuario, hilo(stream), posts, ademas vamos a crear un aplicación JS para usar el servicio. Depliegue la aplicación en S3. Asegúrese que esté disponible sobre internet.


## 📌 Características

Este proyecto experimental consiste en la implementación de una API y una aplicación web que permite a los usuarios realizar publicaciones de hasta 140 caracteres, similar a Twitter. Se parte de un monolito en Spring Boot y se evoluciona hacia una arquitectura basada en microservicios desplegados en AWS Lambda.

# Funcionalidades Principales

En este taller, diseñaremos y desplegaremos una aplicación segura y escalable utilizando infraestructura de AWS, con un enfoque en las mejores prácticas de seguridad. Las funcionalidades principales de la aplicación incluyen:

- Registro y autenticación de usuarios con JWT (Cognito u otra tecnología).
- Creación y visualización de publicaciones en un stream único.
- Interfaz web en JavaScript para interactuar con el servicio.
- Despliegue en AWS con separación de microservicios.


## 🛠️ Requisitos
- Java 11 o superior
- Git
- Maven
- AWS EC2
- Spring Framework


## 🚀 Instalación y Ejecución
### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/Koket987/AREP_T7
cd AREP_T7
```

### 2️⃣ Compilar el proyecto con Maven
```bash
mvn clean install
```

![image](https://github.com/user-attachments/assets/97fbe2ec-8b49-41a1-8107-b421fde0c432)



### 3️⃣ Ejecutar el servidor 

```bash
mvn spring-boot:run
```

![image](https://github.com/user-attachments/assets/8793f127-5163-4014-a435-d9c0076cf549)



Probamos en el navegador lo siguiente:

```bash
http://localhost:8080/index.html
```


![image](https://github.com/user-attachments/assets/d92215ec-dbee-4b37-910a-f20f93d48deb)


## 🔍 Sistema Arquitectónico

Clases Principales

> [!IMPORTANT]
> ### 🔹 Monolito Inicial
> - Una sola aplicación Spring Boot que gestiona usuarios, posts y el stream de publicaciones.
> - Autenticación con JWT.
> - Base de datos PostgreSQL o DynamoDB.


> [!IMPORTANT]
> ### 🔹 Separación en Microservicios
> - **Servicio de Usuarios**: Registro y autenticación de usuarios.
> - **Servicio de Posts**: Creación y consulta de publicaciones.
> - **Servicio de Hilos (Stream)**: Administración del flujo de publicaciones.




![image](https://github.com/user-attachments/assets/66248244-b248-47c4-a528-030e2d68cf3c)


## 🚀 Despliegue AWS

Creamos la instancia en AWS EC2.

![image](https://github.com/user-attachments/assets/235034e2-76fb-4234-aa50-fb34a714b1f4)


Ahora configuramos el grupo de seguridad de la instancia EC2 para permitir el tráfico entrante en el puerto 50000 (o el puerto que hayas configurado en tu aplicación Spring Boot).


![image](https://github.com/user-attachments/assets/520a5553-1dc2-4a30-9930-c02f078a3a10)


Desplegamos nuestro codigo en AWS

1. Subimos el proyecto a AWS

```bash
scp -i key.pem target/app.jar ec2-user@IP_EC2:/home/ec2-user/
```

![image](https://github.com/user-attachments/assets/1fcd2f17-f047-4315-81a2-94ea82c06c18)


2. Conectamos a nuestra instancia EC2

```bash
ssh -i key.pem ec2-user@IP_EC2
```

3. Ejecutamos la aplicacion 

```bash
java -jar /home/ec2-user/app.jar
```

4. Y para finalizar accedemos a la aplicacion

```bash
http://EC2_PUBLIC_IP:5000/
```

Que se veria de la siguiente manera

![image](https://github.com/user-attachments/assets/a438e938-a59a-4055-9781-2b6102e5eeae)


## 🔍 Pruebas de Estilo de Codificacion ##

Con el siguiente comando realizamos las pruebas de estilo de codificación son aquellas que verifican que el código sigue las convenciones y buenas prácticas del equipo o la comunidad

```bash
mvn checkstyle:check
```

![Image](https://github.com/user-attachments/assets/6c5a4c16-9c71-463d-9629-59f5c976213a)


## :office: Desplieqgue ##

Vamos a ejecutar el servidor como un proceso en segundo plano o configurar un servicio systemd, de la siguiente manera:

```bash
mvn spring-boot:run
```

## :cd: Construido con ## 

 - Java - Lenguaje principal utilizado
 - Maven - Para la gestión de dependencias y automatización
 - Docker - Plataforma de código abierto que permite crear, ejecutar, administrar y actualizar contenedores


## :busts_in_silhouette: Contribuciones ##

Lea [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) para obtener detalles sobre nuestro código de conducta y el proceso para enviarnos solicitudes de extracción.

## :school_satchel: Control de Versiones ##

Usamos [SemVer](http://semver.org/) para controlar las versiones.

## :bust_in_silhouette: Autor ##

* **Juan Sebastian Sanchez** - *Trabajo Inicial* - [Juanse2347](https://github.com/Juanse2347)


## 📄 Licencia
Este proyecto está bajo la licencia [LICENSE](LICENSE). ¡Siéntete libre de contribuir! 😊


## :wave: Expresiones de Gratitud ##

- Inspiracion
- Compromiso


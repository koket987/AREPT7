# Micro Servicios

In this workshop, we will design and deploy a secure, scalable application using AWS infrastructure with a focus on best practices for security. Our architecture will feature two primary components:

- **Server 1**: Apache Server
  The Apache server will be responsible for serving an asynchronous HTML+JavaScript client over a secure connection using TLS. Client-side code will be delivered through encrypted channels, ensuring data integrity and confidentiality during download.
- **Server 2**: Spring Framework
  The Spring server will handle backend services, offering RESTful API endpoints. These services will also be protected using TLS, ensuring secure communication between the client and the backend.


## 📌 Características


# Funcionalidades Principales

En este taller, diseñaremos y desplegaremos una aplicación segura y escalable utilizando infraestructura de AWS, con un enfoque en las mejores prácticas de seguridad. Las funcionalidades principales de la aplicación incluyen:


- **TLS Encryption**: Secure transmission of data using TLS certificates generated through Let’s Encrypt, ensuring confidentiality and integrity.
- **Asynchronous Client**: Our HTML+JavaScript client will leverage async techniques to optimize performance while maintaining secure communication.
- **Login Security**: We will implement login authentication, with passwords securely stored as hashes.
- **AWS Deployment**: All services will be deployed and managed on AWS, leveraging its secure, reliable infrastructure.

Despliegue de los servicios de backend y base de datos en servidores separados dentro de de AWS
  
```bash
Despliegue en AWS
```

Se ejecuta localmente en contenedores Docker y también puede ser desplegada en una máquina virtual en AWS.


## 🛠️ Requisitos
- Java 11 o superior
- Git
- Maven
- AWS EC2
- Spring Framework



## 🚀 Instalación y Ejecución
### 1️⃣ Clonar el repositorio
```bash
git clone https://github.com/Juanse2347/AREP_T6
cd AREP_T6
```

### 2️⃣ Compilar el proyecto con Maven
```bash
mvn clean install
```

![image](https://github.com/user-attachments/assets/2755aea0-a8ef-488d-9d1a-54eb748878a1)



### 3️⃣ Ejecutar el servidor 

```bash
mvn spring-boot:run
```

![image](https://github.com/user-attachments/assets/6c7fafa7-4cb3-4ff1-b947-8fc43a8faa7d)


Probamos en el navegador lo siguiente:

```bash
https://localhost:5000
```


![image](https://github.com/user-attachments/assets/d2d9c003-4126-4db8-987b-f9b9d96cb7ea)


## 🔍 Diseño de Clases 

Clases Principales

> [!IMPORTANT]
>  - **HelloController**: Representa la entidad de propiedad con los atributos id, address, price, size, y description.
>  - **SecureURLReader**: Contiene la lógica del negocio para gestionar las operaciones CRUD de las propiedades.
>  - **Secureweb**: Proporciona los endpoints RESTful para interactuar con el frontend.


![image](https://github.com/user-attachments/assets/17c1bbfb-18e4-4341-a457-6738dcf3472f)


# 🔍 Sistema Arquitectónico

El sistema consta de tres componentes principales:


### 1. **Servidor Apache**

- **Rol:** Servir contenido estático (HTML+JavaScript) de manera segura.
- **Tecnología:** Apache HTTP Server.
- **Seguridad:** Implementación de TLS para asegurar la transmisión de contenido estático. El servidor es configurado para entregar contenido a través de conexiones cifradas (HTTPS).
- **Certificados SSL:** Se utilizarán certificados generados por Let's Encrypt para asegurar las conexiones.
  
### 2. **Servidor Spring Framework**

- **Rol:** Proveer los servicios backend mediante APIs RESTful.
- **Tecnología:** Spring Framework.
- **Seguridad:** Implementación de TLS para asegurar que las comunicaciones entre el cliente y la API estén cifradas.
- **Certificados SSL:** Certificados generados por Let's Encrypt para asegurar las conexiones HTTPS.

### 3. **Cliente HTML+JavaScript (Asíncrono)**

- **Rol:** Interfaz de usuario que interactúa con el servidor backend utilizando AJAX y técnicas asincrónicas.
- **Tecnología:** HTML, JavaScript, y AJAX.
- **Seguridad:** Las solicitudes AJAX se realizan de manera segura a través de HTTPS, garantizando la protección de los datos en tránsito.

### 4. **Seguridad del Login**

- **Autenticación:** El sistema de login estará protegido con autenticación de usuario. Las contraseñas se almacenarán de forma segura utilizando hashing (por ejemplo, bcrypt) en lugar de almacenamiento en texto plano.
  
### 5. **Despliegue en AWS**

El despliegue de la aplicación se gestionará usando AWS. Utilizaremos servicios como EC2 para alojar los servidores Apache y Spring, y S3 o RDS para almacenamiento adicional según sea necesario.

- **AWS EC2:** Instancias que alojarán el servidor Apache y el servidor Spring.
- **AWS ELB (Elastic Load Balancer):** Para distribuir el tráfico entre las instancias si se requiere escalabilidad.
- **AWS S3/RDS:** Opcional, dependiendo de los requisitos de almacenamiento.


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


## <p align="center"> BASE API SPRING </p>
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)<br>
API Rest desarrollada en Java con Spring Boot para la gestión de usuarios(login y registro), role y CRUD de usuarios.


## Índice

1. [Funcionalidades](#Funcionalidades)
2. [Requerimientos previos](#requerimientos-previos)
3. [Configuración](#configuración)
4. [Swagger](#swagger)
5. [Tecnologías utilizadas](#tecnologías-utilizadas)
6. [Estructura del proyecto](#estructura-del-proyecto)
7. [Modelo entidad-relación](#modelo-entidad-relación)
8. [Licencia](#licencia)


## Funcionalidades



<details>
<summary>🔐 Autenticación</summary>

| Método | Endpoint | Reglas de negocio |
|--------|----------|-------------------|
| POST   | `/login` | Inicia sesión y obtiene un Token JWT. |

</details>



<details>
<summary>👤 Usuarios</summary>

| Método | Endpoint          | Reglas de negocio |
|--------|-------------------|-------------------|
| POST   | `/users/register` | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- La API no debe permitir el registro de usuarios duplicados (con el mismo correo) y debe tener al menos un número y una letra mayúscula.<br>- Asignar el rol USER por defecto.<br>- La API debe retornar la información del nuevo usuario y el token. <br>- Si elige el rol MENTOR, la propiedad mentor es necesaria y de forma similar para el rol MENTEE con la propiedad mentee. <br>- Si el correo ya existe retornar un código HTTP 409. <br>- Si la contraseña tiene menos de 8 o más de 15 caracteres retornar un 400.<br>- Si la contraseña no tiene al menos un letra mayúscula y un número retornar un 400.|
| GET    | `/users`          | - Retornar los primeros 10 resultados ordenados por id.<br>- Devolver todos los atributos menos la contraseña.<br>- Obtener la respuesta con paginación para controlar el volumen de los datos.<br>- Solo el rol ADMIN puede obtener todos los usuarios. |
| GET    | `/users/{id}`     | - Retornar el usuario que coincida con el id y que además se encuentre habilitado.<br>- Si no encuentra el usuario retornar un 404.<br>- Solo el rol ADMIN puede buscar usuarios. |
| UPDATE | `/users/{id}`     | - Si no se completan los campos obligatorios retorna un 400.<br>- Si no encuentra el usuario retornar un 404.<br>- Solo el rol ADMIN puede actualizar usuarios. <br>- Si elige el rol MENTOR, la propiedad mentor es necesaria y de forma similar para el rol MENTEE con la propiedad mentee. <br>- Si el correo ya existe retornar un código HTTP 409. <br>- Si la contraseña tiene menos de 8 o más de 15 caracteres retornar un 400.<br>- Si la contraseña no tiene al menos un letra mayúscula y un número retornar un 400.|
| DELETE  | `/users/{id}`     | - Si la eliminación es exitosa retornar un 204.<br>- Si no encuentra el usuario retornar un 404.<br>- Solo el rol ADMIN puede eliminar usuarios. |

</details>


## Requerimientos previos

- **JDK: Java 21 o superior**
- **Gestor de dependencias: Maven 4.0.0**
- **Spring Boot 3.3.5**
- **Base de datos MySQL o PostgreSQL (cambiar la configuración de application.properties)**

## Configuración 

  1. Clona el repositorio
     
     ```bash
     git clone https://github.com/Luiggi-piero/base-api-spring.git
     cd base-api-spring
  2. Configura las variables de entorno para la conexión a la base de datos desde `application-prod.yml`

     ```yaml
     spring:
      datasource:
        url: ${DB_URL:jdbc:postgresql://localhost:5432/baseapispring_db}
        username: ${DB_USER}
        password: ${DB_PASS}
        driver-class-name: org.postgresql.Driver
      jpa:
    
        hibernate:
          ddl-auto: update
        show-sql: true
    
      server:
        port: 8080
    
      konecta:
        cors:
          allowed-origins: "*"
          allowed-methods: "GET,POST,PUT,DELETE,OPTIONS"
          allowed-headers: "*"
    
      security:
        secret: ${JWT_SECRET}  # Get from env vars
        expiration-ms: ${JWT_EXPIRATION_MS:86400000}

  3. Crea un base de datos vacía con el nombre konecta
  
  4. Ejecuta el proyecto

  5. La aplicación estará disponible en: http://localhost:8080

## Swagger
Swagger está configurado para generar documentación de la API automáticamente. Puedes acceder a la interfaz de Swagger en la siguiente URL cuando el servidor esté en funcionamiento:
```
http://localhost:8080/swagger-ui/index.html
```
![image](https://github.com/user-attachments/assets/9d909024-f60d-442a-a63a-d02528599d6c)



## Tecnologías utilizadas

- **Spring Boot**: Desarrollo rápido y robusto de aplicaciones.
- **Spring Security y JWT**: Autenticación segura.
- **MySQL y postgreSQL**: Sistema de gestión de bases de datos relacional.          


## Estructura del proyecto

Arquitectura basada en paquetes funcionales, se organizan  las carpetas de acuerdo con las características o módulos de la aplicación (por ejemplo, auth, category, challenge), es un diseño entre aspectos funcionales y principios de Clean Architecture y este tipo de arquitectura agrupa cada módulo con sus propios componentes como controladores, servicios, repositorios y modelos.

      src
      └── main
          ├── java/com/example/skilllinkbackend
          │   ├── config       
          │   |   ├── exceptions       -> Exception handling.
          |   |   ├── responses        -> Response format.
          |   |   ├── security         -> Security settings.
          |   |   └── springdoc        -> Spring doc configuration.
          │   ├── features
          │   |   ├── auth             -> Authentication.
          |   |   ├── mentor 
          |   |   ├── role   
          |   |   └── usuario 
          |   └── shared                     
          │      ├── roledeletionhandler
          |      ├── roleregistrationhandler        
          |      └── util             -> Reusable items.
          └── resources
              └── application.properties -> Configuration app.
        

## Modelo Entidad Relación
<img width="751" height="361" alt="Image" src="https://github.com/user-attachments/assets/5f71b75a-7b1e-4157-afbe-348186eaad4b" />

</br>

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.
</br></br>

> [!IMPORTANT]
> * Con sql crea los roles: ADMIN, USER, MENTEE y MENTOR en la tabla roles
> * Cambia a enabled 1 todos los roles
> * Registra un usuario
> * Modifica la tabla users_roles: agrega un registro para asignar al usuario creado el rol ADMIN
> * Agrega la configuración de la bd en `application-prod.yml`
> * Para aquellos que no tienen la zona horaria GMT-5 modificar el archivo ...TokenService (para indicar la expiración del token)
         

</br>
<p align="center">
  <img src="https://img.shields.io/badge/java-white?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=black">
  <img src="https://img.shields.io/badge/SPRINGBOOT-white?style=for-the-badge&logo=spring&logoColor=white&labelColor=%236DB33F">
  <img src="https://img.shields.io/badge/mysql-white?style=for-the-badge&logo=mysql&logoColor=white&labelColor=4169E1">
  <img src="https://img.shields.io/badge/postgresql-white?style=for-the-badge&logo=postgresql&logoColor=white&labelColor=4169E1">
</p>

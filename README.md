## <p align="center"> SHEPARD API </p>
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)<br>
API Rest que ofrece servicios de reservación de habitaciones y departamentos desarrollada en Java con Spring Boot, Spring Security, Spring Doc, PostgreSQL, entre otros para la gestión de usuarios(login y registro).


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
| POST   | `/api/v1/login` | Inicia sesión y obtiene un Token JWT. |

</details>



<details>
<summary>👤 Usuarios</summary>

| Método | Endpoint          | Reglas de negocio |
|--------|-------------------|-------------------|
| POST   | `/api/v1/users/register` | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- La API no debe permitir el registro de usuarios duplicados (con el mismo correo) y debe tener al menos un número y una letra mayúscula.<br>- Asignar el rol USER por defecto.<br>- La API debe retornar la información del nuevo usuario y el token. <br>- Si elige el rol RECEPTION, la propiedad receptionist es necesaria y de forma similar para el rol CLEANING con la propiedad cleaningStaff. <br>- Si el correo ya existe retornar un código HTTP 409. <br>- Si la contraseña tiene menos de 8 o más de 15 caracteres retornar un 400.<br>- Si la contraseña no tiene al menos un letra mayúscula y un número retornar un 400.|
| GET    | `/users`          | - Retornar los primeros 10 resultados ordenados por id.<br>- Devolver todos los atributos menos la contraseña.<br>- Obtener la respuesta con paginación para controlar el volumen de los datos.<br>- Solo el rol ADMIN puede obtener todos los usuarios. |
| GET    | `/api/v1/users/{id}`     | - Retornar el usuario que coincida con el id y que además se encuentre habilitado.<br>- Si no encuentra el usuario retornar un 404.<br>- Solo el rol ADMIN puede buscar usuarios. |
| UPDATE | `/api/v1/users/{id}`     | - Si no se completan los campos obligatorios retorna un 400.<br>- Si no encuentra el usuario retornar un 404.<br>- Solo el rol ADMIN puede actualizar usuarios. <br>- Si elige el rol RECEPTION, la propiedad receptionist es necesaria y de forma similar para el rol CLEANING con la propiedad cleaningStaff. <br>- Si el correo ya existe retornar un código HTTP 409. <br>- Si la contraseña tiene menos de 8 o más de 15 caracteres retornar un 400.<br>- Si la contraseña no tiene al menos un letra mayúscula y un número retornar un 400.|
| DELETE  | `/api/v1/users/{id}`     | - Si la eliminación es exitosa retornar un 204.<br>- Si no encuentra el usuario retornar un 404.<br>- Solo el rol ADMIN puede eliminar usuarios. |

</details>

<details>
<summary>🏢 Departamentos</summary>

| Método | Endpoint          | Reglas de negocio |
|--------|-------------------|-------------------|
| POST   | `/api/v1/departments` | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN tienen acceso <br>- La API debe retornar la información del nuevo departamento. <br>- Si la creación es exitosa retorna el código HTTP 201.<br>- Si los datos son inválidos retornar el código HTTP 400.
| GET    | `/api/v1/departments`          | - Retornar los primeros 10 resultados ordenados por code.<br>- Obtener la respuesta con paginación para controlar el volumen de los datos.<br>- Solo el rol ADMIN y RECEPTION pueden obtener todos los usuarios. <br>- El tamaño por defecto de la página será de 10|
| GET    | `/api/v1/departments/{id}`     | - Retornar el departamento que coincida con el id y que además se encuentre habilitado.<br>- Si no encuentra el departamento retornar un 404.<br>- Solo el rol ADMIN y RECEPTION puede buscar usuarios.|
| UPDATE | `/api/v1/departments/{id}`     | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN tienen acceso <br>- La API debe retornar la información del departamento actualizado. <br>- Si la edición es exitosa retorna el código HTTP 200.<br>- Si los datos son inválidos retornar el código HTTP 400. <br> - Si el departamento no se encuentra retornar el código HTTP 404.|
| DELETE  | `/api/v1/departments/{id}`     | - Si la eliminación es exitosa retornar un 204.<br>- Si no encuentra el departamento retornar un 404.<br>- Solo el rol ADMIN puede eliminar departamentos. |

</details>

<details>
<summary>🚪 Habitaciones</summary>

| Método | Endpoint          | Reglas de negocio |
|--------|-------------------|-------------------|
| POST   | `/api/v1/rooms` | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN tienen acceso <br>- La API debe retornar la información de la nueva habitación. <br>- Si la creación es exitosa retorna el código HTTP 201.<br>- Si los datos son inválidos retornar el código HTTP 400. <br> - Si no se encuentra el tipo de habitación retornar un 404.
| GET    | `/api/v1/rooms`          | - Retornar los primeros 10 resultados ordenados por number.<br>- Obtener la respuesta con paginación para controlar el volumen de los datos.<br>- Solo el rol ADMIN y RECEPTION pueden obtener todas las habitaciones. <br>- El tamaño por defecto de la página será de 10.|
| GET    | `/api/v1/rooms/{id}`     | - Retornar la habitación que coincida con el id y que además se encuentre habilitado.<br>- Si no encuentra el departamento retornar un 404.<br>- Solo el rol ADMIN y RECEPTION puede buscar habitaciones.|
| UPDATE | `/api/v1/rooms/{id}`     | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN tienen acceso <br>- La API debe retornar la información de la habitación actualizada. <br>- Si la edición es exitosa retorna el código HTTP 200.<br>- Si los datos son inválidos retornar el código HTTP 400. <br> - Si no se encuentra la habitación o el tipo de habitación retornar un 404.|
| DELETE  | `/api/v1/rooms/{id}`     | - Si la eliminación es exitosa retornar un 204.<br>- Si no encuentra la habitación retornar un 404.<br>- Solo el rol ADMIN puede eliminar habitaciones. |

</details>


<details>
<summary>📅 Reservas</summary>

| Método | Endpoint          | Reglas de negocio |
|--------|-------------------|-------------------|
| POST   | `/api/v1/bookings` | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN o RECEPTION tienen acceso a este endpoint. <br>- La API debe retornar la información de la nueva reserva. <br>- Si la creación es exitosa retorna el código HTTP 201.<br>- Si los datos son inválidos retornar el código HTTP 400. <br> - Si no se encuentra huésped, recepcionista o el alojamiento retornar un 404. <br> - Verifica que no exista algún conflicto de tiempos entre la nueva reserva y las existentes.
| GET    | `/api/v1/bookings`          | - Retornar los primeros 10 resultados ordenados por fecha de creación.<br>- Obtener la respuesta con paginación para controlar el volumen de los datos.<br>- Solo el rol ADMIN y RECEPTION pueden obtener todas las reservas. <br>- El tamaño por defecto de la página será de 10. <br> - Es posible realizar búsquedas con filtros como: ID del huésped, ID del recepcionista, estado de la reserva, nombre del huésped y fechas|
| GET    | `/api/v1/bookings/{id}`     | - Retornar la reserva que coincida con el id y que además se encuentre habilitado.<br>- Si no encuentra la reserva retornar un 404.<br>- Solo el rol ADMIN y RECEPTION puede buscar habitaciones.|
| UPDATE | `/api/v1/bookings/{id}`     | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN o RECEPTION tienen acceso a este endpoint. <br>- La API debe retornar la información de la reserva actualizada. <br>- Si la edición es exitosa retorna el código HTTP 200.<br>- Si los datos son inválidos retornar el código HTTP 400. <br> - Si no se encuentra huésped, recepcionista o el alojamiento retornar un 404. <br> - Verificar que no exista algún conflicto de tiempos entre la nueva reserva y las existentes. <br> - Si no encuentra la reserva retornar el código HTTP 404.|
| DELETE  | `/api/v1/bookings/{id}`     | - Si la eliminación es exitosa retornar un 204.<br>- Si no encuentra la reserva retornar un 404.<br>- Solo el rol ADMIN y RECEPTION puede eliminar reservas. |

</details>

<details>
<summary>🏨 Tipos de habitaciones</summary>

| Método | Endpoint          | Reglas de negocio |
|--------|-------------------|-------------------|
| POST   | `/api/v1/room-types` | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN tienen acceso a este endpoint. <br>- La API debe retornar la información del nuevo tipo de habitación. <br>- Si la creación es exitosa retorna el código HTTP 201.<br>- Si los datos son inválidos retornar el código HTTP 400.
| GET    | `/api/v1/room-types`          | - Retornar los primeros 10 resultados ordenados por el nombre.<br>- Obtener la respuesta con paginación para controlar el volumen de los datos.<br>- Solo el rol ADMIN y RECEPTION pueden obtener todos los tipos de habitaciones. <br>- El tamaño por defecto de la página será de 10.|
| GET    | `/api/v1/room-types/{id}`     | - Retornar el tipo de habitación que coincida con el id y que además se encuentre habilitado.<br>- Si no lo encuentra retornar un 404.<br>- Solo el rol ADMIN y RECEPTION puede buscar los tipos de habitaciones.|
| UPDATE | `/api/v1/room-types/{id}`     | - Verificar si todos los campos obligatorios se están ingresando correctamente.<br>- Solo usuarios con rol ADMIN tienen acceso a este endpoint. <br>- La API debe retornar la información actualizada. <br>- Si la actualización es exitosa retorna el código HTTP 200.<br>- Si los datos son inválidos retornar el código HTTP 400. <br> - Si no se encuentra el recurso retornar el código HTTP 404.|
| DELETE  | `/api/v1/room-types/{id}`     | - Si la eliminación es exitosa retornar un 204.<br>- Si no encuentra el recurso retornar un 404.<br>- Solo el rol ADMIN puede eliminar. |

</details>

## Requerimientos previos

- **JDK: Java 21 o superior**
- **Gestor de dependencias: Maven 4.0.0**
- **Spring Boot 3.3.5**
- **Base de datos PostgreSQL (cambiar la configuración de application.properties)**

## Configuración 

  1. Clona el repositorio
     
     ```bash
     git clone https://github.com/Luiggi-piero/shepard-backend.git
     cd shepard-backend
  2. Configura las variables de entorno para la conexión a la base de datos desde `application-prod.yml`

     ```yaml
     spring:
      datasource:
        url: ${DB_URL:jdbc:postgresql://localhost:5432/shepard_db}
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

  3. Crea un base de datos vacía con el nombre shepard_db
  
  4. Ejecuta el proyecto

  5. La aplicación estará disponible en: http://localhost:8080

## Swagger
Swagger está configurado para generar documentación de la API automáticamente. Puedes acceder a la interfaz de Swagger en la siguiente URL cuando el servidor esté en funcionamiento:
```
http://localhost:8080/swagger-ui/index.html
```
<img width="1897" height="904" alt="image" src="https://github.com/user-attachments/assets/e49827bd-6ca4-4c58-a1af-01fcf49f4974" />



## Tecnologías utilizadas

- **Spring Boot**: Desarrollo rápido y robusto de aplicaciones.
- **Spring Security y JWT**: Autenticación segura.
- **PostgreSQL**: Sistema de gestión de bases de datos relacional.          


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
          |   |   ├── accommodation
          |   |   ├── booking
          |   |   ├── bookingitem
          |   |   ├── cleaningstaff
          |   |   ├── department
          |   |   ├── receptionist
          |   |   ├── room
          |   |   ├── roomtype
          |   |   ├── securitystaff
          |   |   ├── role   
          |   |   └── usuario 
          |   └── shared                     
          │      ├── enums
          │      ├── roledeletionhandler
          |      ├── roleregistrationhandler        
          |      └── util             -> Reusable items.
          └── resources
              └── application.properties -> Configuration app.
        

## Modelo Entidad Relación
<img width="2091" height="676" alt="Image" src="https://github.com/user-attachments/assets/662c86aa-7744-4b40-833a-842643040558" />

</br>

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.
</br></br>

> [!IMPORTANT]
> * Con sql crea los roles: USER, ADMIN, GUEST, CLEANING, RECEPTION y SECURITY  en la tabla roles
> * Cambia a enabled 1 todos los roles
> * Registra un usuario con los roles necesarios
> * Agrega la configuración de la bd en `application-prod.yml`
> * Para aquellos que no tienen la zona horaria GMT-5 modificar el archivo ...TokenService (para indicar la expiración del token)
         

</br>
<p align="center">
  <img src="https://img.shields.io/badge/java-white?style=for-the-badge&logo=openjdk&logoColor=white&labelColor=black">
  <img src="https://img.shields.io/badge/SPRINGBOOT-white?style=for-the-badge&logo=spring&logoColor=white&labelColor=%236DB33F">
  <img src="https://img.shields.io/badge/mysql-white?style=for-the-badge&logo=mysql&logoColor=white&labelColor=4169E1">
  <img src="https://img.shields.io/badge/postgresql-white?style=for-the-badge&logo=postgresql&logoColor=white&labelColor=4169E1">
</p>

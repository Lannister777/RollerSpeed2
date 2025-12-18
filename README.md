# Roller Speed – Sistema de Gestión para Escuela de Patinaje

Aplicación web monolítica desarrollada con **Spring Boot 3** para la escuela de patinaje **Roller Speed**.  
Permite gestionar aspirantes, alumnos, clases, pagos y asistencia, con diferentes roles de usuario (Administrador, Instructor y Alumno), vistas HTML con Thymeleaf y APIs REST documentadas con OpenAPI/Swagger.

---

## 1. Tecnologías y stack

- **Lenguaje**: Java 17  
- **Framework**: Spring Boot 3  
- **Build**: Maven  
- **Base de datos**: MySQL  
- **Dependencias principales**:
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Thymeleaf
  - Validation (Bean Validation)
  - Lombok
  - MySQL Driver
  - springdoc-openapi (Swagger UI)

---

## 2. Arquitectura

La aplicación sigue una arquitectura **en capas (MVC)**:

- `domain/entity`  
  Entidades JPA que representan el modelo de datos:
  - `Usuario`, `Alumno`, `Instructor`, `Aspirante`
  - `Clase`, `Pago`, `Asistencia`
  - `ContenidoInstitucional`, `Noticia`, `Evento`, etc.

- `repository`  
  Repositorios Spring Data JPA (`JpaRepository`) para acceso a la base de datos:
  - `AlumnoRepository`, `InstructorRepository`, `PagoRepository`, `AspiranteRepository`, …

- `service` y `service.impl`  
  Servicios con la lógica de negocio:
  - `AlumnoService`, `PagoService`, `ClaseService`, `AsistenciaService`, etc.
  - Implementaciones como `PagoServiceImpl`.

- `controller.api`  
  Controladores REST para el **CRUD vía JSON**, documentados con OpenAPI:
  - `PagoRestController`, `AlumnoRestController`, etc.

- `controller.web`  
  Controladores que devuelven vistas Thymeleaf (HTML):
  - `AdminAspiranteController`, `AdminAlumnoController`, `AdminPagoController`
  - `AlumnoController`, `InstructorController`, `InstitutionalController`, etc.

- `resources/templates`  
  Vistas Thymeleaf (HTML) para:
  - Login, index, páginas públicas (institucional, noticias, eventos).
  - Paneles: admin, instructor, alumno.
  - CRUD HTML (aspirantes, alumnos, pagos pendientes).

- `resources/static/css/rollerspeed.css`  
  Hoja de estilos propia para complementar Bootstrap y dar una identidad visual a la app.

---

## 3. Seguridad y roles

### Roles definidos

- `ROLE_ADMIN`
- `ROLE_INSTRUCTOR`
- `ROLE_ALUMNO`

### Configuración

- Implementada con **Spring Security** en `SecurityConfig`.
- Para entorno de demostración se usa `NoOpPasswordEncoder`  
  (contraseñas en texto plano para simplificar las pruebas).
- `CustomAuthenticationSuccessHandler` redirige según el rol al iniciar sesión:
  - ADMIN → `/admin`
  - INSTRUCTOR → `/instructor`
  - ALUMNO → `/alumno`

### Usuarios demo

Para probar rápidamente el sistema:

- **Administrador**
  - Usuario: `admin`
  - Contraseña: `admin123`
- **Instructor**
  - Usuario: `instructor1`
  - Contraseña: `instr123`
- **Alumno**
  - Usuario: `alumno1`
  - Contraseña: `alum123`

Estos usuarios se muestran también en la propia página de login.

---

## 4. Módulos funcionales

### 4.1 Aspirantes

- Registro público de aspirantes desde `/registro-aspirante`.
- Gestión completa desde el panel admin:
  - Lista de aspirantes: `/admin/aspirantes`
    - Tabla con nombre, correo, teléfono, estado y fecha de registro.
    - Badges de color según estado (`PENDIENTE`, `APROBADO`, etc.).
  - Formulario de aspirantes:
    - Crear y editar aspirantes con datos básicos y método de pago preferido.

### 4.2 Alumnos

- Entidad `Alumno` asociada a `Usuario`.
- CRUD HTML para admin:
  - Lista: `/admin/alumnos`
  - Formulario: crear/editar alumnos (`nombreCompleto`, `fechaNacimiento`, `genero`, `telefono`, `nivelPatinaje`, `fechaIngreso`).

### 4.3 Clases

- Entidad `Clase` con relación a instructores y alumnos.
- Panel del instructor `/instructor` muestra:
  - Sus clases asignadas.
  - Asistencias recientes de los alumnos.

### 4.4 Pagos

- Entidad `Pago` con:
  - `monto`, `fechaPago`, `periodo`, `metodoPago`, `estado`, `observaciones`.
- Servicio `PagoService` y `PagoServiceImpl`:
  - `listarTodos`, `buscarPorId`, `actualizar`, `eliminar`.
  - `pagosPorAlumno(alumnoId)`.
  - `pagosPendientes()` (estado `"PENDIENTE"`).

- Controlador REST `PagoRestController`:
  - `/api/pagos` → CRUD REST.
  - `/api/pagos/pendientes` → lista de pagos pendientes.
  - `/api/pagos/alumno/{alumnoId}` → pagos por alumno.

- Panel admin de pagos:
  - Tarjeta “Pagos pendientes (7)” en `/admin` enlaza a `/admin/pagos`.
  - Vista `admin/pagos/lista.html` muestra los pagos pendientes en tabla.

### 4.5 Asistencias

- Entidad `Asistencia`:
  - Relaciona alumno, clase y fecha.
  - Indica si el alumno estuvo **presente** o **ausente**.

- Panel del Alumno `/alumno`:
  - Sección “Mis asistencias” con tabla de historial.

---

## 5. Páginas públicas e institucionales

- **Inicio** `/`  
  Página principal con imagen hero de patinaje, botones de acceso y registro.

- **Registro de aspirante** `/registro-aspirante`  
  Formularios públicos para que los interesados se inscriban.

- **Institucional** `/institucional`  
  Muestra misión, visión y servicios usando la entidad `ContenidoInstitucional`.

- **Noticias** `/noticias`  
  Lista de noticias en formato de tarjetas con imagen, título, fecha y resumen, usando la entidad `Noticia`.

- **Eventos** `/eventos`  
  Lista de eventos con imagen, fecha, lugar y descripción, usando la entidad `Evento`.

---

## 6. Paneles por rol

### 6.1 Panel Administrador (`/admin`)

- Banner con imagen de fondo e introducción.
- Tarjetas resumen:
  - Aspirantes activos → `/admin/aspirantes`.
  - Alumnos matriculados → `/admin/alumnos`.
  - Pagos pendientes → `/admin/pagos`.
  - Clases esta semana → `/instructor`.

- Secciones:
  - Aspirantes recientes.
  - Pagos recientes.
  - Alumnos por nivel.
  - Próximas clases.

### 6.2 Panel Instructor (`/instructor`)

- Muestra:
  - Clases asignadas al instructor autenticado.
  - Asistencias recientes de sus alumnos.

### 6.3 Panel Alumno (`/alumno`)

- Banner con saludo personalizado.
- Tarjetas de resumen:
  - Clases inscritas.
  - Pagos registrados.
  - Asistencias registradas.
- Secciones:
  - Mis clases.
  - Mis pagos.
  - Mis asistencias.

---

## 7. Documentación de APIs (Swagger / OpenAPI)

La aplicación incluye **springdoc-openapi** para documentar las APIs REST.

- URL de Swagger UI:  
  `http://localhost:8080/swagger-ui.html`

Desde ahí se pueden probar los endpoints para:
- Aspirantes
- Alumnos
- Pagos
- Clases
- Asistencias  
 etc., según los controladores REST disponibles.

---

## 8. Configuración y ejecución

### 8.1 Requisitos previos

- Java 17 instalado (`java -version`)
- Maven (o usar el wrapper `mvnw`)
- MySQL en ejecución:
  - Base de datos, por ejemplo: `rollerspeed`
  - Usuario/contraseña configurados en `application.properties` o `application.yml`

### 8.2 Configurar base de datos

En `src/main/resources/application.properties` (o `.yml`), configurar:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rollerspeed?useSSL=false&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> Nota: si usas `schema.sql` y `data.sql`, se ejecutarán para crear tablas y datos iniciales.

### 8.3 Ejecutar la aplicación

Desde la raíz del proyecto:

```bash
# Windows (PowerShell / CMD)
.\mvnw spring-boot:run

# o si tienes Maven instalado
mvn spring-boot:run
```

La app quedará disponible en:

- `http://localhost:8080/`

Rutas de interés:

- Login: `/login`
- Panel admin: `/admin`
- Panel instructor: `/instructor`
- Panel alumno: `/alumno`
- Páginas públicas: `/`, `/institucional`, `/noticias`, `/eventos`
- Swagger UI: `/swagger-ui.html`

---

## 9. Próximos pasos (posibles mejoras)

- Completar CRUD HTML para:
  - Clases
  - Asistencias
  - Pagos (crear/editar/estado) desde el panel admin.
- Conectar más métricas del dashboard admin directamente a datos reales.
- Migrar a un `PasswordEncoder` seguro (por ejemplo, BCrypt) en un entorno de producción.
- Internacionalización (i18n) y mensajes de error más detallados.
- Tests automáticos (unitarios y de integración) más completos.

---

## 10. Autor y propósito

Este proyecto fue desarrollado como trabajo académico/práctico para demostrar:

- Uso de Spring Boot y arquitectura en capas.
- Diseño de APIs REST y documentación con OpenAPI.
- Integración de Spring Security con roles y vistas específicas.
- Construcción de una interfaz web agradable con Thymeleaf, Bootstrap y CSS personalizado.

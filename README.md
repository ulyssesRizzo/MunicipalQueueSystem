### Municipal Queue System

Sistema de gestión de tickets para municipalidades. Desarrollado en Java, CSS y JavaScript con backend y frontend integrados.

## 📌 Tabla de Contenidos

- [Requisitos Técnicos](#-requisitos-técnicos)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Configuración Inicial](#-configuración-inicial)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Despliegue](#-despliegue)
- [Autor](#-autor)


## ⚙️ Requisitos Técnicos

| Componente | Versión
|-----|-----
| Base de Datos | MySQL 8.0
| Servidor | Apache Tomcat 9.0
| JDK | 24
| IDE | NetBeans 25
| Conector MySQL | mysql-connector-j-9.3.0


> **📌 Nota:**

- El nombre de la base de datos debe ser: `municipal_queue_system`.
- Asegúrese de tener configuradas las variables de entorno de Java y Tomcat.




## 📂 Estructura del Proyecto

```plaintext
MunicipalQueueSystem-1.0-SNAPSHOT/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── municipal/
│   │   │           ├── controller/
│   │   │           │   ├── AuthController.java
│   │   │           │   ├── TicketController.java
│   │   │           │   └── TicketJsonController.java
│   │   │           ├── dao/
│   │   │           │   ├── TicketDAO.java
│   │   │           │   └── UserDAO.java
│   │   │           ├── model/
│   │   │           │   ├── Ticket.java
│   │   │           │   └── User.java
│   │   │           └── util/
│   │   │
│   │   └── resources/
│   │       ├── META-INF/
│   │       │   └── persistence.xml
│   │       └── webapp/
│   │           ├── META-INF/
│   │           │   └── context.xml
│   │           ├── WEB-INF/
│   │           │   ├── beans.xml
│   │           │   └── web.xml
│   │           ├── js/
│   │           │   └── usuario.js
│   │           ├── admin.jsp
│   │           ├── estado.jsp
│   │           ├── estilos.css
│   │           ├── login.jsp
│   │           ├── operador.jsp
│   │           └── usuario.jsp
│   │
│   └── test/
│       └── [carpetas de pruebas]/
│
├── target/
├── nb-configuration.xml
└── pom.xml
```

## 🔧 Configuración Inicial

### Base de Datos

```sql
CREATE DATABASE municipal_queue_system;
USE municipal_queue_system;
```

Importar el esquema SQL si existe (`schema.sql`).



## 🚀 Tecnologías Utilizadas

| Rol | Tecnologías
|-----|-----
| Backend | Java (JDK 24), JPA/Hibernate, Servlets
| Frontend | JSP, CSS3, JavaScript
| Base de Datos | MySQL 8.0
| Build Tool | Maven


## 📄 Descripción de Componentes

### Controladores

- **AuthController.java**: Gestiona la autenticación y autorización de usuarios.
- **TicketController.java**: Maneja las operaciones CRUD para tickets.
- **TicketJsonController.java**: API REST para operaciones con tickets en formato JSON.


### Modelos

- **Ticket.java**: Entidad JPA que representa un ticket en el sistema.
- **User.java**: Entidad JPA que representa un usuario del sistema.


### Vistas

- **login.jsp**: Página de inicio de sesión.
- **admin.jsp**: Panel de administración.
- **operador.jsp**: Interfaz para operadores que atienden tickets.
- **usuario.jsp**: Interfaz para usuarios que solicitan tickets.
- **estado.jsp**: Muestra el estado actual de los tickets.


## ⚡ Despliegue

1. Clonar repositorio:


```shellscript
git clone https://github.com/ulyssesRizzo/MunicipalQueueSystem.git
```

2. Importar en NetBeans como proyecto Maven.
3. Configurar Tomcat 9.0 en el IDE.
4. Ejecutar el proyecto desde NetBeans o desplegar el archivo WAR generado en Tomcat.


## 👨‍💻 Autor

Ulysses Torres (https://github.com/ulyssesRizzo)


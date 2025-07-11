# 🏕️ Aike: Sistema de Gestión de Complejos de Cabañas

<div align="center">

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Latest-blue)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**Versión Actual:** 1.1.3

</div>

---

## 📋 Tabla de Contenidos

- [Descripción del Proyecto](#-descripción-del-proyecto)
- [Características Principales](#-características-principales)
- [Tecnologías Utilizadas](#️-tecnologías-utilizadas)
- [Arquitectura del Sistema](#-arquitectura-del-sistema)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Uso de la Aplicación](#-uso-de-la-aplicación)
- [Documentación de la API](#-documentación-de-la-api)
- [Contribuir al Proyecto](#-contribuir-al-proyecto)
- [Licencia](#-licencia)
- [Autores y Contacto](#-autores-y-contacto)

---

## 🏕️ **Descripción del Proyecto**

**Aike** es un sistema integral diseñado para modernizar y optimizar la gestión de complejos de cabañas. Esta solución tecnológica proporciona una plataforma robusta, escalable y fácil de usar que facilita la administración completa de reservas, usuarios, pagos y servicios adicionales.

### 🎯 **Objetivo Principal**

Digitalizar y automatizar los procesos de gestión de complejos turísticos, ofreciendo una experiencia fluida tanto para administradores como para huéspedes, mientras se integran tecnologías avanzadas como inteligencia artificial para recomendaciones personalizadas.

### 🌍 **Alcance**

El sistema está diseñado para adaptarse a complejos de cabañas de diferentes tamaños, desde pequeños emprendimientos familiares hasta grandes complejos turísticos, proporcionando funcionalidades escalables según las necesidades específicas de cada negocio.

---

## 🌟 **Características Principales**

### 👥 **Gestión de Usuarios**
- **Autenticación Multi-Modal**: Login tradicional, OAuth2 con Google y JWT
- **Sistema de Roles**: Administradores, empleados y clientes con permisos diferenciados
- **Gestión Completa**: Alta, baja, modificación y consulta de usuarios (ABM)

### 🏠 **Gestión de Cabañas**
- **Inventario Digital**: Control completo del estado y disponibilidad de cabañas
- **Mantenimiento**: Seguimiento de tareas de limpieza y mantenimiento
- **Categorización**: Clasificación por tipo, capacidad y servicios incluidos

### 📅 **Sistema de Reservas**
- **Reservas en Tiempo Real**: Disponibilidad actualizada instantáneamente
- **Notificaciones Automáticas**: Confirmaciones y recordatorios por email/SMS
- **Gestión de Conflictos**: Resolución automática de solapamientos

### 🔐 **Control de Acceso Inteligente**
- **Códigos QR**: Generación automática para acceso sin llaves físicas
- **Integración RFID**: Soporte para llaves electrónicas
- **Logs de Acceso**: Registro completo de entradas y salidas

### 🤖 **Inteligencia Artificial**
- **Recomendaciones Turísticas**: Sugerencias personalizadas mediante OpenAI
- **Análisis Predictivo**: Optimización de tarifas y ocupación
- **Chatbot Integrado**: Asistencia automatizada para huéspedes

### 💰 **Gestión Financiera**
- **Pagos Online**: Integración completa con Mercado Pago
- **Facturación Automática**: Generación de facturas y recibos
- **Reportes Financieros**: Análisis de ingresos y rentabilidad

### 📊 **Panel de Control y Reportes**
- **Dashboard Ejecutivo**: Métricas en tiempo real
- **Reportes Customizables**: Análisis de ocupación, ingresos y satisfacción
- **Alertas Inteligentes**: Notificaciones proactivas de situaciones críticas

---

## 🛠️ **Tecnologías Utilizadas**

### **Backend - API REST**
- **Lenguaje**: Java 17 (LTS)
- **Framework**: Spring Boot 3.3.5
- **Seguridad**: Spring Security + JWT + OAuth2
- **Base de Datos**: PostgreSQL (Producción) / H2 (Testing)
- **ORM**: Hibernate/JPA
- **Mapeo de Objetos**: MapStruct 1.6.3
- **Utilidades**: Lombok

### **Integraciones Externas**
- **Inteligencia Artificial**: OpenAI GPT-4 (Spring AI)
- **Pagos**: Mercado Pago SDK 2.2.0
- **Autenticación Social**: Google OAuth2
- **Códigos QR**: ZXing (Google)

### **DevOps y Deployment**
- **Contenedores**: Docker
- **Build Tool**: Maven 3.9+
- **Cloud**: AWS RDS (Base de Datos)
- **Monitoreo**: Spring Boot Actuator

### **Testing y Calidad**
- **Testing**: Spring Boot Test + JUnit
- **Documentación**: Swagger/OpenAPI (próximamente)

---

## 🏗️ **Arquitectura del Sistema**

### **Estructura del Proyecto**
```plaintext
src/
├── main/
│   ├── java/com/tesis/aike/
│   │   ├── controller/         # Controladores REST API
│   │   ├── service/           # Lógica de negocio
│   │   ├── repository/        # Acceso a datos (JPA)
│   │   ├── model/
│   │   │   ├── entity/        # Entidades JPA
│   │   │   └── dto/           # Data Transfer Objects
│   │   ├── security/          # Configuración de seguridad
│   │   ├── configuration/     # Configuraciones Spring
│   │   ├── helper/           # Clases utilitarias
│   │   └── utils/            # Utilidades generales
│   └── resources/
│       └── application.properties  # Configuración de la aplicación
└── test/                           # Pruebas unitarias e integración

target/                             # Archivos compilados
├── classes/                        # Clases compiladas
└── aike-1.1.3.jar               # JAR ejecutable
```

### **Principales Endpoints**
- `/api/auth/**` - Autenticación y autorización
- `/api/users/**` - Gestión de usuarios
- `/api/cabins/**` - Gestión de cabañas
- `/api/reservations/**` - Sistema de reservas
- `/api/payments/**` - Procesamiento de pagos
- `/api/ai/**` - Servicios de inteligencia artificial
- `/api/access/**` - Control de acceso
- `/api/qr/**` - Generación de códigos QR

---

## 🚀 **Instalación y Configuración**

### **Prerrequisitos**
- Java 17 o superior
- Maven 3.6+
- PostgreSQL 12+
- Docker (opcional)
- Git

### **1. Clonar el Repositorio**
```bash
git clone https://github.com/otondonicolasDaVinci/aike-api.git
cd aike-api
```

### **2. Configurar Variables de Entorno**
Crear un archivo `.env` en la raíz del proyecto:
```env
# Base de Datos
DB_URL=jdbc:postgresql://localhost:5432/aike_db
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña

# JWT
JWT_SECRET_KEY=tu_clave_secreta_jwt_muy_larga_y_segura

# Mercado Pago
MP_ACCESS_TOKEN=tu_access_token_de_mercadopago
MP_PUBLIC_KEY=tu_public_key_de_mercadopago
MP_WEBHOOK_SECRET=tu_webhook_secret

# Google OAuth
GOOGLE_APP_CLIENT_ID=tu_client_id_de_google

# OpenAI
OPENAI_API_KEY=tu_api_key_de_openai
```

### **3. Configurar Base de Datos**
```sql
-- Crear base de datos
CREATE DATABASE aike_db;

-- Crear usuario (opcional)
CREATE USER aike_user WITH PASSWORD 'tu_contraseña';
GRANT ALL PRIVILEGES ON DATABASE aike_db TO aike_user;
```

### **4. Compilar y Ejecutar**

#### **Opción A: Ejecución Local**
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
```

#### **Opción B: Crear JAR ejecutable**
```bash
# Generar JAR
mvn clean package -DskipTests

# Ejecutar JAR
java -jar target/aike-1.1.3.jar
```

#### **Opción C: Docker**
```bash
# Construir imagen
docker build -t aike-api .

# Ejecutar contenedor
docker run -p 8080:8080 --env-file .env aike-api
```

### **5. Verificar Instalación**
La aplicación estará disponible en: `http://localhost:8080`

---

## 📖 **Uso de la Aplicación**

### **Inicio Rápido**

1. **Registrar Administrador**:
   ```bash
   curl -X POST http://localhost:8080/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","email":"admin@aike.com","password":"admin123","role":"ADMIN"}'
   ```

2. **Iniciar Sesión**:
   ```bash
   curl -X POST http://localhost:8080/api/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"admin123"}'
   ```

3. **Obtener Token y Usar API**:
   ```bash
   # Usar el token JWT en headers posteriores
   curl -X GET http://localhost:8080/api/cabins \
     -H "Authorization: Bearer YOUR_JWT_TOKEN"
   ```

### **Flujo Típico de Uso**

1. **Configuración Inicial**: Registrar cabañas, configurar precios y disponibilidad
2. **Gestión de Reservas**: Los clientes realizan reservas a través de la API
3. **Procesamiento de Pagos**: Integración automática con Mercado Pago
4. **Control de Acceso**: Generación de códigos QR para acceso sin llaves
5. **Recomendaciones**: La IA sugiere actividades basadas en preferencias

---

## 📚 **Documentación de la API**

### **Autenticación**
Todas las rutas protegidas requieren un token JWT en el header:
```
Authorization: Bearer <token_jwt>
```

### **Principales Endpoints**

#### **Autenticación**
- `POST /api/auth/register` - Registrar nuevo usuario
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/logout` - Cerrar sesión
- `GET /api/auth/profile` - Obtener perfil del usuario

#### **Gestión de Cabañas**
- `GET /api/cabins` - Listar todas las cabañas
- `GET /api/cabins/{id}` - Obtener cabaña específica
- `POST /api/cabins` - Crear nueva cabaña (Admin)
- `PUT /api/cabins/{id}` - Actualizar cabaña (Admin)
- `DELETE /api/cabins/{id}` - Eliminar cabaña (Admin)

#### **Reservas**
- `GET /api/reservations` - Listar reservas
- `POST /api/reservations` - Crear nueva reserva
- `GET /api/reservations/{id}` - Obtener reserva específica
- `PUT /api/reservations/{id}` - Modificar reserva
- `DELETE /api/reservations/{id}` - Cancelar reserva

#### **Pagos**
- `POST /api/payments/create` - Crear preferencia de pago
- `POST /api/payments/webhook` - Webhook de Mercado Pago
- `GET /api/payments/{id}` - Estado del pago

#### **Inteligencia Artificial**
- `POST /api/ai/recommendations` - Obtener recomendaciones turísticas
- `GET /api/ai/chat` - Interactuar con chatbot

### **Códigos de Respuesta**
- `200` - Éxito
- `201` - Creado exitosamente
- `400` - Error en la petición
- `401` - No autorizado
- `403` - Acceso denegado
- `404` - Recurso no encontrado
- `500` - Error interno del servidor

---

## 🤝 **Contribuir al Proyecto**

¡Las contribuciones son bienvenidas! Por favor, sigue estos pasos:

### **1. Fork del Repositorio**
```bash
git clone https://github.com/tu-usuario/aike-api.git
cd aike-api
```

### **2. Crear Rama de Feature**
```bash
git checkout -b feature/nueva-funcionalidad
```

### **3. Realizar Cambios**
- Mantén el código limpio y bien documentado
- Sigue las convenciones de Spring Boot
- Agrega tests para nuevas funcionalidades
- Actualiza la documentación si es necesario

### **4. Testing**
```bash
mvn test
mvn spring-boot:run  # Verificar que la app inicia correctamente
```

### **5. Commit y Push**
```bash
git add .
git commit -m "feat: descripción de la nueva funcionalidad"
git push origin feature/nueva-funcionalidad
```

### **6. Crear Pull Request**
- Describe claramente los cambios realizados
- Incluye screenshots si hay cambios en la UI
- Referencia issues relacionados

### **Estándares de Código**
- Utilizar Java 17+ features
- Seguir convenciones de Spring Boot
- Documentar métodos públicos con JavaDoc
- Mantener cobertura de tests > 80%
- Usar Lombok para reducir boilerplate code

---

## 📄 **Licencia**

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

```
MIT License

Copyright (c) 2024 Mario Exequiel Acosta, Nicolas Otondo, Lucas Gomez, Florencia Verratti

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👥 **Autores y Contacto**

### **Equipo de Desarrollo**

<table>
<tr>
<td align="center">
<img src="https://github.com/otondonicolasDaVinci.png" width="100px;" alt="Nicolas Otondo"/><br>
<b>Nicolas Otondo</b><br>
<i>Full Stack Developer</i><br>
<a href="https://github.com/otondonicolasDaVinci">GitHub</a> | 
<a href="mailto:nicolas.otondo@example.com">Email</a>
</td>
<td align="center">
<b>Mario Exequiel Acosta</b><br>
<i>Backend Developer</i><br>
<a href="mailto:mario.acosta@example.com">Email</a>
</td>
<td align="center">
<b>Lucas Gomez</b><br>
<i>Frontend Developer</i><br>
<a href="mailto:lucas.gomez@example.com">Email</a>
</td>
<td align="center">
<b>Florencia Verratti</b><br>
<i>UX/UI Designer</i><br>
<a href="mailto:florencia.verratti@example.com">Email</a>
</td>
</tr>
</table>

### **Información de Contacto**
- **Email del Proyecto**: aike-support@example.com
- **Website**: [https://aike-cabanas.com](https://aike-cabanas.com)
- **Documentación**: [https://docs.aike-cabanas.com](https://docs.aike-cabanas.com)

### **Soporte y Comunidad**
- **Issues**: [GitHub Issues](https://github.com/otondonicolasDaVinci/aike-api/issues)
- **Discusiones**: [GitHub Discussions](https://github.com/otondonicolasDaVinci/aike-api/discussions)
- **Wiki**: [GitHub Wiki](https://github.com/otondonicolasDaVinci/aike-api/wiki)

---

<div align="center">

**¿Te gusta Aike?** ⭐ ¡Dale una estrella al repositorio!

**Desarrollado con ❤️ por el equipo de Aike**

---

*Última actualización: Julio 2024*

</div>

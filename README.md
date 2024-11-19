# Aike: Sistema de Gestión de Complejos de Cabañas

**Versión Actual:** 0.1 
**Autor:** Mario Exequiel Acosta, Nicolas Otondo, Lucas Gomez, Florencia Verratti.

---

## 🏕️ **Descripción del Proyecto**

Aike es un sistema integral diseñado para modernizar la gestión de complejos de cabañas. Proporciona una solución personalizada y escalable que facilita la administración de reservas, usuarios, y pagos, además de ofrecer funcionalidades avanzadas como recomendaciones turísticas generadas por inteligencia artificial.

Este proyecto está desarrollado con tecnologías modernas y es accesible tanto desde aplicaciones web, móviles, como de escritorio, adaptándose a las necesidades de administradores y huéspedes.

---

## 🌟 **Características principales**
- **Gestión de Usuarios (ABM):** Alta, baja, modificación y autenticación de usuarios con diferentes roles (administradores y clientes).
- **Sistema de Reservas:** Gestión de reservas en tiempo real, con notificaciones automáticas.
- **Control de Stock:** Seguimiento de inventario de cabañas e insumos.
- **Seguridad:** Gestión de accesos con códigos QR o llaves RFID.
- **Recomendaciones Turísticas:** Generación de recorridos personalizados con inteligencia artificial.
- **Gestión de Pagos:** Integración con la API de Mercado Pago para procesar transacciones de forma segura.
- **Escalabilidad:** El sistema es capaz de crecer según las necesidades del complejo.

---

## 🛠️ **Tecnologías utilizadas**

### **Backend**
- Lenguaje: **Java**
- Framework: **Spring Boot**
- Base de datos: **Microsoft SQL Server**
- Autenticación: **JWT**
- ORM: **Hibernate**

### **Frontend**
- Web: **Java/React**.
- Mobile: **Java/Kotlin**.

### **Integraciones**
- **Mercado Pago API:** Para gestión de cobros.
- **ChatGPT API:** Para recomendaciones turísticas.

---

## 🚀 **Estructura del Proyecto**

### Backend (Spring Boot)
```plaintext
src/
├── main/
│   ├── java/com/aike/
│   │   ├── controller/    # Controladores REST
│   │   ├── service/       # Lógica de negocio
│   │   ├── repository/    # Acceso a datos (JPA)
│   │   ├── model/         # Entidades de la base de datos
│   │   └── dto/           # Objetos de transferencia de datos
│   ├── resources/
│       ├── application.properties  # Configuración de Spring Boot
│       └── schema.sql              # Script de inicialización de la base de datos
└── test/                           # Pruebas unitarias e integración

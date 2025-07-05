# App-de-Citas-M-dicas
**Medicit** es una aplicación Android desarrollada como proyecto académico para la gestión de citas médicas. Fue creada en el contexto de una asignatura universitaria y **no está lista para ser usada en entornos productivos o reales**, pero representa una sólida base funcional y didáctica.

---

## 📚 Sobre el proyecto

> 📌 Este proyecto fue creado con fines **educativos**. No pretende ser una solución lista para la industria médica, pero explora conceptos clave de desarrollo móvil como:

- Arquitectura de apps con múltiples actividades
- Uso de base de datos local (Room)
- Manejo de `RecyclerView` para listas dinámicas
- Gestión de permisos (ubicación)
- Integración básica con Google Maps
- Modularidad con `Adapter`, `DAO`, `Entities`, y `Coroutines`

## 📲 Funcionalidades principales

- ✅ Registro y login de usuarios
- ✅ Agendar nuevas citas médicas
- ✅ Selección dinámica de:
  - Especialidad
  - Doctor
  - Consultorio
  - Horario
- ✅ Visualización de citas agendadas (con opción de editar o eliminar)
- ✅ Edición de una cita existente manteniendo sus datos cargados
- ✅ Eliminación de citas desde la misma interfaz
- ✅ Uso de Google Maps para ubicación (permiso de ubicación incluido)
- ✅ Validaciones básicas para asegurar una buena experiencia de usuario

---
## 🧩 Estructura del proyecto

La aplicación está dividida en varias actividades para mantener un orden claro:
- `inicio.kt`: Pantalla inicial / login-registro
- `login.kt`: Logica del login
- `registro.kt`: Logica del registro
- `Home.kt`: Pantalla con agendar cita - ver citas - hospitales cercanos
- `agendar_citaActivity.kt`: Lógica para agendar nuevas citas
- `ver_citasActivity.kt`: Muestra las citas en un RecyclerView con botón de editar y eliminar
- `editar_citaActivity.kt`: Permite editar una cita existente
- `MapsActivity.kt`: Muestra un mapa e incluye permisos de ubicación

Además, incluye:

- `Room Database`: DAO, entidades y base de datos (`AppDatabase.kt`)
- `RecyclerView Adapter`: `CitaAdapter.kt` para mostrar citas

---

## 🧠 Tecnologías utilizadas

- 💻 **Kotlin**
- 🗂️ **Room Database (SQLite)**
- 📦 **RecyclerView**
- 🧵 **Coroutines**
- 🌍 **Google Maps API**
- 🧪 **View Binding / findViewById**
- 💡 **Material Design**
- 🔐 **Permissions & Activity Result API**

---

## 👨‍💻 Autor
Desarrollado por Natacha Caceres
Estudiante de Ingeniería en Informática

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

## 📲 Funcionalidades implementadas

- 🧑‍⚕️ **Login simulado de usuario**
- 📅 **Agendar citas médicas**
- 🔁 **Actualizar y eliminar citas**
- 🧠 **Selección dependiente de especialidad, doctor, horario y consultorio**
- 🗺️ **Mapa con permiso de ubicación**
- ✔️ Validaciones básicas al ingresar datos
- 🧱 Uso de `Room` con múltiples entidades

---
## ⚠️ Importante

> **Este proyecto NO está diseñado para uso real en clínicas o centros médicos.**

Aspectos **pendientes o no implementados** que serían necesarios para un uso industrial:

- ❌ Validaciones robustas y reglas de negocio (ej: evitar duplicidad de citas)
- ❌ Seguridad en login / autenticación real
- ❌ Sincronización con servidores / APIs reales
- ❌ Manejo de errores avanzado
- ❌ Accesibilidad
- ❌ Diseño UI/UX profesional

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

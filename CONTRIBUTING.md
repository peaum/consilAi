# Guía de Contribución – App de Quedadas Sociales

Este proyecto es privado y está gestionado de forma colaborativa entre Benjamin, Peace y Fran. Si formas parte del equipo y vas a contribuir, sigue estas indicaciones para mantener la organización, calidad y coherencia del desarrollo.

## Roles del Equipo

- **Benjamin** – Idea original, validación del concepto y estrategia de marketing.
- **Peace** – Desarrollo frontend, estructura del repositorio, documentación y diseño técnico.
- **Fran** – Desarrollo backend, arquitectura y soporte técnico.

## Requisitos Previos

- Tener acceso autorizado al repositorio privado.
- Utilizar Git con ramas organizadas por funcionalidad.
- Mantener el diseño y esquema de datos actualizado en la carpeta `/documentacion/`.

## Estructura de Ramas

- `main` – Rama estable y desplegable.
- `dev` – Rama de integración continua (merge desde branches de funcionalidad).
- `feature/nombre` – Funcionalidades nuevas.
- `bugfix/nombre` – Corrección de errores.
- `docs/nombre` – Actualización de documentación.

## Flujo de Trabajo

1. Crea una nueva rama desde `dev` con un nombre claro.
2. Realiza tus cambios localmente.
3. Asegúrate de que el proyecto compila correctamente (frontend/backend según tu rol).
4. Si cambias la lógica o el flujo, actualiza los archivos relevantes en `/documentacion/`.
5. Haz commit con mensajes descriptivos (en castellano).
6. Haz push a GitHub y abre un Pull Request hacia `dev`.
7. Avísalo por el grupo de WhatsApp o el canal de comunicación que estéis usando.

## Buenas Prácticas

- Comenta tu código si implementas lógica compleja.
- Usa nombres de variables y funciones significativos.
- Respeta la separación de responsabilidades (no mezcles frontend/backend en un mismo PR).
- No subas archivos temporales o sensibles (asegúrate de que `.gitignore` está bien configurado).

## Documentación

Todos los cambios estructurales, nuevas funcionalidades o decisiones técnicas deben reflejarse en:

- `/documentacion/concepto-inicial.md`
- `/documentacion/flujos-de-usuario.md`
- `/documentacion/esquema-datos.json`

---

Peace Umolu  
Mayo 2025

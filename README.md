---

# Inventory Pilot

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.10-blue.svg)](http://kotlinlang.org)

## Descripción

**Inventory Pilot** es una aplicación móvil desarrollada en Kotlin para la plataforma Android, diseñada para llevar el control de inventario de una agropecuaria. Utiliza Firebase como base de datos en tiempo real para asegurar que la información esté siempre actualizada y sincronizada.

## Características

- **Gestión de Productos:** Registro, edición y eliminación de productos con detalles como nombre, descripción, cantidad y precio.
- **Control de Stock:** Monitoreo en tiempo real del inventario, con alertas para niveles bajos de stock.
- **Historial de Movimientos:** Registro de todas las transacciones de entrada y salida de inventario.
- **Notificaciones:** Alertas instantáneas para eventos críticos como el agotamiento de stock.
- **Interfaz Intuitiva:** Diseño amigable y fácil de usar, optimizado para dispositivos móviles.

## Tecnologías Utilizadas

- **Lenguaje:** Kotlin
- **Plataforma:** Android
- **Base de Datos:** Firebase Realtime Database
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Dependencias:** 
  - [Koin](https://insert-koin.io/) para la inyección de dependencias.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) y [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) para la gestión de datos y UI.
  - [Firebase SDK](https://firebase.google.com/docs/android/setup) para la integración con Firebase.

## Capturas de Pantalla

![Pantalla Principal](screenshots/main_screen.png)
![Detalle del Producto](screenshots/product_detail.png)
![Historial de Movimientos](screenshots/movement_history.png)

## Instalación

Para clonar y ejecutar esta aplicación, sigue estos pasos:

1. Clona el repositorio:
    ```bash
    git clone https://github.com/tu_usuario/inventory-pilot.git
    ```
2. Abre el proyecto en Android Studio.
3. Configura Firebase:
    - Ve a [Firebase Console](https://console.firebase.google.com/).
    - Crea un nuevo proyecto o usa uno existente.
    - Añade tu aplicación Android a Firebase y sigue las instrucciones para descargar el archivo `google-services.json`.
    - Coloca el archivo `google-services.json` en la carpeta `app` de tu proyecto.
4. Compila y ejecuta la aplicación en un dispositivo o emulador.

## Uso

1. **Añadir Producto:** Navega a la sección de productos y pulsa el botón "Añadir" para registrar un nuevo producto.
2. **Editar Producto:** Selecciona un producto de la lista y edítalo según sea necesario.
3. **Ver Historial:** Accede a la sección de historial para ver todas las transacciones de inventario.

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas colaborar con el proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit (`git commit -am 'Añadir nueva funcionalidad'`).
4. Envía tus cambios a tu repositorio fork (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request en este repositorio.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

Desarrollado por [Tu Nombre](https://www.linkedin.com/in/tu_perfil) - [tu.email@example.com](mailto:tu.email@example.com)

---

Este README proporciona una descripción completa y profesional de tu proyecto "Inventory Pilot", resaltando sus características, tecnologías utilizadas, y guías para instalación y contribución.

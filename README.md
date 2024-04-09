# Holafly Marvel Technical Test

El objetivo de esta prueba es aplicar los conocimientos técnicos para un proyecto de desarrollo móvil en Android para la empresa Holafly. En este proyecto fue desarrollado en Android Studio utilizando Kotlin como lenguaje base de programación.

## Arquitectura: Clean architecture + MVVM en 4 módulos
- Core (Archivos y recursos compartidos por los demás módulos)
- Data (para base de datos, API's, repositorios y manejadores de errores)
- Domain (para la lógica de negocio, interfaces, modelos y casos de uso)
- Presenation (para la vistas, view models y compose)

## Tests
- [Mockk](https://mockk.io/) library 
- Unit tests
- UI tests (con [Compose Testing](https://developer.android.com/jetpack/compose/testing))
    
## Librerías y estructuración del proyecto
- Manejador de versiones (con [Version catalog](https://developer.android.com/build/migrate-to-catalogs))
- Inyección de dependencias (con [Hilt](http://google.github.io/hilt/))
- Llamados a servicios (con [Retrofit](https://square.github.io/retrofit/))
- Programación reactiva (con [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html))
- Componentes de UI (con [Jetpack Compose](https://developer.android.com/jetpack/compose))
  - Navegación en compose (con [Hilt Support](https://developer.android.com/jetpack/compose/libraries#hilt-navigation))

# Guía de inicio

1. Descargar el reposiorio y abrir el proyecto con Android Studio
2. Algunas veces es necesario cambiar el path de Java SDK. Android Studio lo actualiza automáticamente
3. Si presenta problemas el compilar el proyecto se debe cambiar el nombre del rootProject.name = "holaflymarvel" en el archivo settigs.gradle.kts
4. Las keys para consumir el servicio no están incluidas en el proyecto, una vez adquiridas se deben reemplazar en el archivo core/commons/Constants


# Git
- Este proyecto contiene `.github/workflows` para validar lint y correr los test unitarios de manera automática al crear PR's hacia la rama main.
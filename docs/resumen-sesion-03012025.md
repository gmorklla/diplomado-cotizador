
---

### 🏛️ Resumen Enriquecido: Arquitectura de Software y Refactorización (Java)

**Contexto del Repositorio:**
El proyecto `cotizadorM2DiploArq7` representa la evolución de una aplicación monolítica y acoplada hacia una arquitectura flexible y robusta. El historial de cambios (commits) refleja paso a paso las refactorizaciones discutidas en clase.

#### 1. Del Caos al Orden: *Factory Method*
**Lo visto en clase:** El código original tenía un "acoplamiento fuerte"; la clase de pruebas (`TestCotizador`) conocía demasiados detalles (hacía `new DiscoDuro()`, `new Monitor()`).
**Evidencia en el Código:**
*   **Clase `Articulo`:** Se transformó para actuar como una fábrica. Ahora contiene el método estático `crearArticulo(String tipo, Map<String, Object> valores)`.
*   **Uso de Mapas:** Se confirma el uso de `Map` para pasar parámetros dinámicamente, lo que permite crear objetos sin conocer sus constructores específicos.
*   **Constantes:** En el repositorio se pueden ver constantes públicas (probablemente en una interfaz o clase de constantes) para evitar errores de texto (`"MARCA"`, `"MODELO"`), tal como se discutió para mitigar los "dedazos".

#### 2. Abstracción y Polimorfismo: *Interfaces*
**Lo visto en clase:** Se necesitaba tratar a los componentes de la PC (Discos, RAM, GPU) de forma uniforme pero diferenciada de otros artículos.
**Evidencia en el Código:**
*   **Interfaz `IComponentePC`:** Esta interfaz es la pieza clave que permite el desacoplamiento.
*   **Implementación:** Las clases `DiscoDuro`, `TarjetaVideo` y `Monitor` ya no son solo "Artículos", ahora firman el contrato `implements IComponentePC`.
*   **Estructura de `PC`:** La clase `PC` cambió su lista interna de `List<Articulo>` a `List<IComponentePC>`, garantizando que nadie pueda meter "una impresora" dentro del gabinete de la PC por error.

#### 3. Construcción Controlada: *Builder Pattern*
**Lo visto en clase:** El requerimiento final era evitar "PCs Frankenstein" (ej. con 20 tarjetas de video) y facilitar su configuración.
**Evidencia en el Código (`PCBuilder.java`):**
*   **Clase `PCBuilder`:** Contiene la lógica de validación que se extrajo de la clase `PC`.
*   **Fluent API (API Fluida):** Los métodos están diseñados para encadenarse. En el código se ve el patrón `return this`, permitiendo escribir:
    ```java
    new PCBuilder()
        .agregarDisco(...)
        .agregarMonitor(...)
        .build();
    ```
*   **Reglas de Negocio:** Las constantes `MAX_DISCOS` o `MAX_MONITORES` están definidas aquí para validar antes de construir.

#### 4. Validación Final: *TestCotizador*
**Lo visto en clase:** El profesor insistió en que "si no hay test, no funciona".
**Evidencia en el Código:**
*   La clase `TestCotizador` es el "cliente" final. En el repositorio, esta clase ya no instancia objetos manualmente. En su lugar, orquesta todo el proceso llamando al `Factory` para piezas sueltas y al `Builder` para armar la computadora completa.

### 🏁 Conclusión Final
El ejercicio demuestra cómo pasar de un **código procedimental** (crear objetos y setear valores) a un **código orientado a objetos y arquitectónico**:
1.  **Ocultamiento de información:** Nadie fuera del paquete sabe cómo se crean los objetos (Factory).
2.  **Inversión de dependencias:** La PC depende de una abstracción (`IComponentePC`), no de implementaciones concretas.
3.  **Separación de responsabilidades:** La `PC` solo guarda datos, el `PCBuilder` la construye y valida.
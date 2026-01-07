**Laboratorio de refactorización**: Sobre cómo pasar de un diseño rígido a uno flexible.

---

### 🏗️ Arquitectura del Proyecto: `Cotizador de Hardware`

El proyecto implementa un sistema para armar y cotizar PCs. La arquitectura no es convencional (Modelo-Vista-Controlador), sino que está centrada en **Patrones de Diseño Creacionales** y **Principios SOLID**.

#### 1. El Núcleo: Abstracción y Polimorfismo
Todo el sistema gira en torno a no depender de cosas concretas ("un disco duro marca Seagate") sino de abstracciones ("un componente compatible con PC").

*   **`Articulo` (Clase Base):**
    *   Es la clase padre de todo (`DiscoDuro`, `Monitor`, `PC`, `TarjetaVideo`, `Impresora`).
    *   **Decisión de Diseño:** Contiene lógica común (marca, modelo, precio) para evitar repetir código (Principio DRY - Don't Repeat Yourself).
*   **`IComponentePC` (La Interfaz Clave):**
    *   **Función:** Actúa como un "contrato". Cualquier cosa que quiera entrar dentro del gabinete de una `PC` debe firmar este contrato (implementar esta interfaz).
    *   **Por qué existe:** Para que la clase `PC` no tenga que saber si le están metiendo un disco o una memoria. Solo sabe que recibe "algo compatible". Esto cumple el **Principio de Inversión de Dependencias (DIP)**: *Depender de abstracciones, no de concreciones*.

#### 2. La Capa de Creación (Factory Method)
En los audios, el profesor enfatizó "ocultar los constructores".

*   **El Problema:** Si `TestCotizador` hacía `new DiscoDuro("Seagate", ...)`, quedaba atado a ese constructor. Si mañana el disco duro requería un tercer parámetro (ej. RPM), el test se rompía.
*   **La Solución en el Código (`Articulo.crearArticulo`):**
    *   Se implementó un **Método Fábrica Estático** dentro de la clase `Articulo`.
    *   **Dinallismo con Mapas:** En lugar de argumentos fijos `(String, double, int)`, recibe un `Map<String, Object>`.
    *   **Ventaja:** Si mañana el disco duro requiere un nuevo dato, solo agregas una llave al mapa. No cambias la firma del método ni rompes el código de quien lo llama.
    *   **Detalle Técnico:** Usa `switch` o `if-else` basado en un `String tipo` ("RAM", "DISCO") para decidir qué subclase instanciar.

#### 3. La Estructura Compuesta (Composite / Wrapper)
*   **Clase `PC`:**
    *   Esta clase es especial. Es un `Articulo` (tiene precio y marca), pero *contiene* otros artículos.
    *   **Atributo Clave:** `List<IComponentePC> componentes`.
    *   **Lógica:** Al pedirle el precio a la PC, esta recorre su lista y suma los precios de sus hijos. Esto permite tratar a una PC completa casi igual que a un disco duro individual (ambos tienen `getPrecio`).

#### 4. El Constructor Inteligente (Builder Pattern)
Esta es la parte más avanzada del ejercicio y responde al requerimiento de "evitar PCs ilógicas" (ej. una PC con 0 componentes o 10 monitores).

*   **Clase `PCBuilder`:**
    *   **Separación de Responsabilidades:** Se le quitó a la clase `PC` la responsabilidad de validarse a sí misma mientras se arma. Ahora `PC` es solo un contenedor de datos (POJO) y `PCBuilder` es el "ingeniero" que la arma.
    *   **Fluent API (API Fluida):** En el código verás métodos que retornan `PCBuilder` (`return this;`). Esto permite encadenar:
        ```java
        builder.conDisco(...).conRAM(...).build();
    ```
    *   **Validación Diferida:** En lugar de lanzar error apenas agregas un 5º disco, el Builder guarda todo temporalmente. Solo cuando llamas al método `.build()`, verifica las reglas de negocio (constantes `MAX_DISCOS`, etc.) y, si todo está bien, "nace" el objeto `PC`.

---

### 🔍 Análisis de Flujo de Datos (Paso a Paso)

Cuando ejecutes el `TestCotizador`, esto es lo que ocurre tras bambalinas:

1.  **Configuración:** El Test crea un mapa de valores (HashMap) y lo llena con constantes (`Articulo.MARCA`, `Articulo.PRECIO`).
2.  **Fábrica:** El Test llama a `Articulo.crearArticulo("DISCO", mapa)`.
3.  **Instanciación Oculta:** La fábrica lee el string "DISCO", busca en el mapa los datos, hace el `new DiscoDuro()` internamente (nadie fuera lo ve) y devuelve un objeto genérico.
4.  **Construcción:** El Test obtiene una instancia de `PCBuilder`.
5.  **Ensamblaje:** Le pasa las piezas creadas al Builder (`agregarComponente`).
6.  **Validación y Entrega:** Llama a `.build()`. El Builder revisa que no haya violaciones de reglas y devuelve la `PC` final.
7.  **Cotización:** El Test llama a `pc.getPrecio()`, que internamente suma recursivamente los costos.

### 💡 Puntos Fuertes y Áreas de Mejora del Ejercicio

**✅ Puntos Fuertes (Buenas Prácticas):**
*   **Desacoplamiento:** Si mañana cambias cómo funciona el `DiscoDuro` por dentro, el `TestCotizador` ni se entera.
*   **Extensibilidad:** Agregar una "Fuente de Poder" es fácil: creas la clase, implementas la interfaz y la agregas al Factory. No rompes lo existente (Principio Open/Closed).
*   **Legibilidad:** El uso del Builder hace que el código de prueba se lea casi como lenguaje natural.

**⚠️ Áreas de "Riesgo" (Discutidas en clase):**
*   **Type Safety (Seguridad de Tipos):** Al usar `Map<String, Object>`, pierdes la ayuda del compilador. Si escribes `"precio"` (minúscula) y el sistema espera `"PRECIO"` (mayúscula), el error salta hasta que corres el programa (Runtime), no cuando compilas. *Solución implementada:* Uso de constantes estáticas.
*   **Casting:** El Factory devuelve `Articulo`, pero el Builder espera `IComponentePC`. El código obliga a hacer un *cast* `(IComponentePC) objeto`, lo cual es un poco "sucio" pero necesario en este diseño específico.

En resumen, este repositorio es un **ejemplo académico de cómo aplicar ingeniería de software** para proteger al código contra cambios futuros, sacrificando un poco de simplicidad inicial a cambio de robustez a largo plazo.
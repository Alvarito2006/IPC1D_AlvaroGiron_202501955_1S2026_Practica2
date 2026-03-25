# Manual de Usuario — Visualizador de Algoritmos de Ordenamiento

**Curso:** Introducción a la Programación y Computación 1  
**Universidad:** San Carlos de Guatemala — Facultad de Ingeniería  
**Versión:** 1.0 | Primer Semestre 2026

---

## ¿Qué hace este programa?

El Visualizador de Algoritmos de Ordenamiento es una aplicación de escritorio que te permite ver paso a paso cómo funcionan tres algoritmos de ordenamiento: **Bubble Sort**, **Shell Sort** y **Quick Sort**. Cada elemento del arreglo se representa como una barra de colores que cambia en tiempo real.

---

## Requisitos para ejecutar

- Java JDK 11 o superior instalado
- NetBeans 17+ (o IntelliJ IDEA)
- Sistema operativo: Windows, Linux o macOS

---

## Cómo abrir el proyecto en NetBeans

1. Abre **NetBeans**
2. Ve a **File → Open Project**
3. Navega hasta la carpeta `SortVisualizer` y selecciónala
4. Haz clic en **Open Project**
5. Presiona **F6** o el botón verde ▶ para ejecutar

---

## Pantalla principal

Al iniciar verás tres pestañas en la parte superior:

| Pestaña | Descripción |
|---|---|
| 🔢 Visualizador | Panel principal donde se ejecuta el ordenamiento |
| 📋 Reportes | Historial de todas las ejecuciones de la sesión |
| ℹ Ayuda | Guía rápida dentro de la aplicación |

---

## Cómo usar el Visualizador

### Paso 1 — Ingresar datos

Tienes tres opciones:

**Opción A — Escribir números manualmente:**
1. Haz clic en el campo de texto que dice *"Datos de entrada (separados por coma)"*
2. Escribe los números que quieras ordenar, separados por coma
   - Ejemplo: `64, 34, 25, 12, 22, 11, 90`
3. Presiona el botón **Cargar**

**Opción B — Generar aleatorio:**
1. Ajusta el número de elementos con el spinner (rango: 5 a 30)
2. Presiona el botón **⚡ Aleatorio**
3. Se generará automáticamente un arreglo de números entre 1 y 100

**Opción C — Cargar desde archivo .txt:**
1. Presiona **📂 Cargar desde .txt**
2. Selecciona un archivo de texto que contenga números separados por comas o saltos de línea
3. El arreglo se cargará automáticamente

---

### Paso 2 — Configurar el algoritmo

Usa el menú desplegable **Algoritmo** para elegir:

| Algoritmo | Tipo | Descripción |
|---|---|---|
| Bubble Sort | Iterativo | Compara pares adyacentes en múltiples pasadas |
| Shell Sort | Iterativo | Ordena subconjuntos a distancias decrecientes |
| Quick Sort | **Recursivo** | Divide el arreglo alrededor de un pivote |

---

### Paso 3 — Configurar el orden

Usa el menú **Orden** para elegir:
- **Ascendente** — de menor a mayor (ej: 1, 5, 12, 30)
- **Descendente** — de mayor a menor (ej: 30, 12, 5, 1)

---

### Paso 4 — Configurar la velocidad

| Opción | Retardo | Cuándo usar |
|---|---|---|
| Lento (500ms) | 0.5 seg por paso | Para estudiar cada comparación con detalle |
| Medio (100ms) | 0.1 seg por paso | Para seguir el proceso a ritmo normal |
| Rápido (20ms) | 0.02 seg por paso | Para ver el resultado rápidamente |

---

### Paso 5 — Iniciar y controlar

- Presiona **▶ Iniciar** para comenzar el ordenamiento
- Presiona **⏹ Detener** para interrumpirlo en cualquier momento
- Mientras corre, los controles se bloquean automáticamente

---

## Qué ver durante la ejecución

### Gráfica de barras (panel derecho, arriba)

Cada barra representa un elemento del arreglo. El color indica su estado actual:

| Color | Estado | Significado |
|---|---|---|
| 🔵 Azul | Normal | El elemento está en reposo |
| 🟡 Amarillo | Comparando | Este par está siendo evaluado |
| 🔴 Rojo | Intercambiando | Estos elementos están siendo permutados |
| 🟢 Verde | Ordenado | El elemento ya está en su posición final |

La leyenda de colores también aparece en el panel izquierdo, debajo de las estadísticas.

---

### Estadísticas (panel izquierdo, medio)

Se actualizan en tiempo real durante el ordenamiento:

| Estadística | Qué cuenta |
|---|---|
| **Comparaciones** | Cuántas veces se compararon dos elementos |
| **Intercambios** | Cuántas veces se permutaron dos posiciones |
| **Iteraciones** | Cuántas pasadas o llamadas recursivas ocurrieron |

Se reinician a cero cada vez que presionas **Iniciar**.

---

### Log de Operaciones (panel derecho, abajo)

Muestra una bitácora de texto con cada paso relevante:
- Qué elemento se comparó y con cuál
- Qué intercambios ocurrieron
- Cuándo comenzó y terminó cada iteración
- Tiempo total al finalizar

El log tiene scroll automático hacia abajo.

---

## Reportes

Al terminar cada ejecución, el programa genera automáticamente un archivo **HTML** con:
- El algoritmo usado y la dirección del ordenamiento
- El arreglo antes y después de ordenar
- Las estadísticas completas (comparaciones, intercambios, iteraciones, tiempo)

Para abrirlo:
1. Ve a la pestaña **📋 Reportes**
2. Haz clic en **Abrir Ventana de Reportes**
3. Presiona **🌐 Abrir último reporte HTML** — se abrirá en tu navegador

El historial muestra todas las ejecuciones realizadas desde que iniciaste la aplicación. Al cerrar el programa, el historial se borra.

---

## Ejemplo de uso completo

1. Escribe `45, 12, 78, 3, 56, 23` en el campo de texto → presiona **Cargar**
2. Selecciona **Quick Sort** en el menú Algoritmo
3. Deja el orden en **Ascendente**
4. Elige velocidad **Medio (100ms)**
5. Presiona **▶ Iniciar**
6. Observa cómo las barras se reorganizan con colores
7. Al finalizar, ve a **Reportes** y abre el HTML generado

---

## Posibles errores y soluciones

| Problema | Solución |
|---|---|
| "Por favor ingresa algunos números" | El campo de texto está vacío. Escribe números o usa Aleatorio |
| "Solo se permiten números enteros" | Verifica que no haya letras o caracteres especiales |
| El reporte no abre en el navegador | Verifica que tengas un navegador predeterminado configurado |
| El programa corre muy rápido | Cambia la velocidad a **Lento** |

---

*Visualizador de Algoritmos de Ordenamiento v1.0 — IPC1 USAC 2026*

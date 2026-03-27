# PRÁCTICA 2

**Curso:** Laboratorio de IPC1  
**Estudiante:** ALVARO MOISÉS GIRÓN MORALES  
**Carné:** 202501955  

---

# Manual Técnico

## 1. Introducción

El presente manual técnico describe la estructura, diseño y funcionamiento interno del **Visualizador de Algoritmos de Ordenamiento**, desarrollado como parte de la Práctica 2 del curso Laboratorio de Introducción a la Programación 1.

La aplicación permite cargar conjuntos de datos numéricos, ejecutar tres algoritmos de ordenamiento (**Bubble Sort, Shell Sort y Quick Sort**) con visualización gráfica en tiempo real, estadísticas de operaciones y generación de reportes en formato HTML.

El desarrollo se ha realizado en **Java**, utilizando **Swing/AWT** para la interfaz gráfica, **JFreeChart** para la visualización de barras, e hilos para el control de velocidad. Todo el código sigue estrictamente las restricciones técnicas impuestas: uso exclusivo de arreglos primitivos `int[]`, implementación manual de algoritmos de ordenamiento y recursión real para Quick Sort.

---

## 2. Requisitos del Sistema

- **Java Development Kit (JDK):** 11 o superior
- **Biblioteca externa:** JFreeChart (versión 1.5.3 utilizada)
- **Entorno de desarrollo:** NetBeans, IntelliJ IDEA o cualquier IDE compatible con Java
- **Sistema operativo:** Multiplataforma (Windows, Linux, macOS)

---

## 3. Estructura del Proyecto

```text
Practica2/
├── src/
│   ├── main/
│   │   ├── Main.java                       # Punto de entrada de la aplicación
│   │   ├── ui/
│   │   │   ├── MainMenu.java              # Ventana principal con menú
│   │   │   ├── SortingVisualizer.java     # Panel principal de visualización
│   │   │   ├── ControlPanel.java          # Panel de control (algoritmo, orden, velocidad)
│   │   │   ├── StatsPanel.java            # Panel de estadísticas
│   │   │   ├── LogPanel.java              # Panel de bitácora de operaciones
│   │   │   └── ReportsPanel.java          # Panel de reportes (historial)
│   │   ├── algorithms/
│   │   │   ├── SortingAlgorithm.java      # Clase abstracta base
│   │   │   ├── BubbleSort.java            # Implementación de Bubble Sort
│   │   │   ├── ShellSort.java             # Implementación de Shell Sort
│   │   │   └── QuickSort.java             # Implementación recursiva de Quick Sort
│   │   ├── model/
│   │   │   ├── ArrayManager.java          # Manejo del arreglo interno y carga de datos
│   │   │   ├── ExecutionRecord.java       # Registro de una ejecución (para reportes)
│   │   │   └── HistoryManager.java        # Gestión del historial de ejecuciones
│   │   └── utils/
│   │       ├── ChartUpdater.java          # Actualización del gráfico con JFreeChart
│   │       └── HtmlReportGenerator.java   # Generación de reportes HTML
├── lib/                                   # Librerías externas (JFreeChart, etc.)
├── docs/                                  # Documentación (manuales, diagramas)
└── README.md
```

---

## 4. Descripción de Clases Principales

### 4.1 `Main.java`

- Contiene el método `main`, que lanza la aplicación creando una instancia de `MainMenu`.
- Configura el *look and feel* por defecto de Swing.

### 4.2 `MainMenu.java`

- Extiende `JFrame` y muestra las opciones principales:
  - **Iniciar Visualizador** → abre `SortingVisualizer`
  - **Ver Reportes** → abre `ReportsPanel`
  - **Ayuda** → muestra información del programa
- Utiliza `CardLayout` para cambiar entre paneles.

### 4.3 `SortingVisualizer.java`

Ventana central donde se realiza el ordenamiento.

Contiene:

- `ControlPanel` con selección de algoritmo, orden, velocidad y botones de carga/inicio
- `StatsPanel` para mostrar comparaciones, intercambios e iteraciones
- `LogPanel` para mostrar la bitácora de operaciones
- `ChartUpdater` que gestiona el gráfico de barras

Además:

- Gestiona el hilo de ordenamiento
- Coordina la comunicación entre componentes mediante eventos

### 4.4 `SortingAlgorithm` (clase abstracta)

Define el contrato para todos los algoritmos:

```java
public abstract void sort(int[] arr, int order, SortingCallback callback, int delayMs);
```

#### Parámetros

- `order`: `1` para ascendente, `-1` para descendente
- `callback`: interfaz que notifica comparaciones, intercambios e iteraciones
- `delayMs`: retardo entre pasos para la visualización

### 4.5 Implementaciones concretas

#### `BubbleSort.java`

- Algoritmo iterativo con dos bucles anidados
- En cada pasada, compara elementos adyacentes y realiza intercambios si no están en el orden deseado
- Actualiza el callback después de cada comparación y cada intercambio

#### `ShellSort.java`

- Implementación iterativa con secuencia de gaps: `n/2`, `n/4`, ..., `1`
- Para cada gap, realiza un *insertion sort* en los subarreglos
- Notifica comparaciones e intercambios mediante el callback

#### `QuickSort.java`

Implementación recursiva obligatoria.

##### Métodos

- `sort()`: inicia la recursión llamando a `quickSort(arr, 0, arr.length - 1, order, callback, delayMs)`
- `quickSort()`: caso base `low >= high`; selecciona pivote (último elemento) y llama a `partition`
- `partition()`: reorganiza el arreglo alrededor del pivote y retorna la posición final del pivote

Las llamadas recursivas se ejecutan en el mismo hilo, respetando el retardo mediante `Thread.sleep(delayMs)` después de cada partición.

### 4.6 `ArrayManager.java`

Mantiene el arreglo `int[]` actual.

#### Métodos

- `loadFromFile(File file)`: lee números separados por comas o saltos de línea
- `loadFromText(String text)`: parsea texto con comas
- `generateRandom(int size, int min, int max)`: genera arreglo aleatorio
- `getArrayCopy()`: retorna una copia para evitar modificaciones externas

### 4.7 `HistoryManager.java`

Almacena en memoria una lista de ejecuciones de la sesión.

Cada `ExecutionRecord` contiene:

- número de ejecución
- algoritmo
- orden
- tamaño
- comparaciones
- intercambios
- iteraciones
- tiempo total

#### Métodos

- `addRecord(ExecutionRecord record)`
- `getRecords()`
- `clearHistory()` *(si se desea reiniciar)*

### 4.8 `HtmlReportGenerator.java`

Genera un archivo HTML con el resumen de una ejecución.

- Recibe los datos y escribe el archivo en la carpeta `reports/`
- Utiliza nombres con formato `report_YYYYMMDD_HHMMSS.html`
- Emplea tabla y estilos CSS embebidos

### 4.9 `ChartUpdater.java`

Encargado de actualizar el gráfico de barras mediante JFreeChart.

- Contiene un `CategoryDataset` y un `JFreeChart` con un `BarRenderer`
- El método `updateChart(int[] data)` actualiza los valores del dataset y refresca el panel

### 4.10 `SortingCallback` (interfaz)

Declara métodos que el algoritmo invoca para notificar eventos:

```java
onCompare(int index1, int index2, int value1, int value2)

onSwap(int index1, int index2, int value1, int value2)

onIteration(int iterationNumber)

onPartition(int pivotIndex, int[] partitionInfo) // solo para Quick Sort
```

La implementación en `SortingVisualizer` actualiza:

- las estadísticas
- el log
- el gráfico

---

## 5. Lógica de Concurrencia y Actualización de UI

- El ordenamiento se ejecuta en un hilo separado (`Thread` o `Runnable`)
- Para controlar la velocidad, se utiliza `Thread.sleep(delayMs)` dentro del algoritmo
- Todas las actualizaciones de componentes Swing (estadísticas, log, gráfico) se realizan mediante `SwingUtilities.invokeLater()` para garantizar que se ejecuten en el **Event Dispatch Thread (EDT)** y evitar bloqueos

### Ejemplo

```java
SwingUtilities.invokeLater(() -> {
    statsPanel.setComparisons(comparisons);
    logPanel.append("[Paso] Comparación realizada...");
    chartUpdater.updateChart(array);
});
```

El hilo de ordenamiento se detiene si el usuario inicia una nueva ejecución, interrumpiendo el hilo anterior.

---

## 6. Gestión de Datos

### 6.1 Carga de datos

#### Archivo `.txt`
- Se usa `JFileChooser` para seleccionar el archivo
- El contenido se parsea dividiendo por comas o saltos de línea

#### Área de texto
- Un `JTextField` acepta números separados por comas  
  Ejemplo: `"5, 2, 8, 1"`

#### Validación
- Se verifica que todos los tokens sean enteros válidos
- En caso contrario, se muestra un mensaje de error

### 6.2 Estructura de datos

- El arreglo se almacena como `int[]` en `ArrayManager`
- No se utilizan colecciones de Java, cumpliendo la restricción

---

## 7. Visualización con JFreeChart

- Se crea un `JFreeChart` de tipo `CategoryPlot` con un `BarRenderer`
- El dataset se implementa con `DefaultCategoryDataset`, donde cada elemento tiene:
  - una categoría (su índice)
  - un valor
- El gráfico se incrusta en un `ChartPanel` dentro de la ventana
- Cada vez que el arreglo cambia, se actualiza el dataset con los nuevos valores

---

## 8. Generación de Reportes

### 8.1 Resumen HTML

Al finalizar cada ejecución, se llama a `HtmlReportGenerator.generateReport(...)`.

El archivo HTML incluye:

- algoritmo
- orden
- velocidad
- arreglo original
- arreglo ordenado
- estadísticas:
  - comparaciones
  - intercambios
  - iteraciones
  - tiempo

Se guarda en la carpeta `reports/` con un nombre único.

### 8.2 Historial de la sesión

- La clase `HistoryManager` acumula registros en memoria
- `ReportsPanel` muestra una `JTable` con los datos de cada ejecución
- Los registros se pierden al cerrar la aplicación

---

## 9. Restricciones Técnicas Cumplidas

| Restricción | Cumplimiento |
|---|---|
| No uso de colecciones (`ArrayList`, etc.) | Todos los arreglos son `int[]` |
| No uso de `Arrays.sort()` ni similares | Algoritmos implementados manualmente |
| Quick Sort recursivo | Implementado con recursión real, no pila manual |
| Tipos primitivos | Se utiliza `int[]`, nunca `Integer[]` |
| Biblioteca de gráficos | JFreeChart 1.5.3, declarada en la documentación |

---

## 10. Métodos Importantes

### `BubbleSort.sort(...)`

- Bucles:
  - `for (i = 0; i < n - 1; i++)`
  - `for (j = 0; j < n - i - 1; j++)`
- Comparación:

```java
if ((order * arr[j]) > (order * arr[j + 1]))
```

### `ShellSort.sort(...)`

- Gap inicial: `gap = n / 2`
- Para cada gap, realiza *insertion sort*:

```java
for (i = gap; i < n; i++)
```

### `QuickSort.quickSort(...)`

```java
if (low < high) {
    int pi = partition(arr, low, high, order);
    quickSort(arr, low, pi - 1, order, callback, delayMs);
    quickSort(arr, pi + 1, high, order, callback, delayMs);
}
```

### `ArrayManager.loadFromFile(...)`

- Lee el archivo con `BufferedReader`
- Filtra tokens usando expresiones regulares:

```java
[,\\s]+
```

### `ChartUpdater.updateChart(...)`

- Itera sobre el arreglo y llama a:

```java
dataset.setValue(value, "Valor", String.valueOf(i));
```

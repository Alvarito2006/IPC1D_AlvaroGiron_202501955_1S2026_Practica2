# Manual Técnico — Visualizador de Algoritmos de Ordenamiento

**Curso:** Introducción a la Programación y Computación 1  
**Universidad:** San Carlos de Guatemala — Facultad de Ingeniería  
**Versión:** 1.0 | Primer Semestre 2026

---

## 1. Estructura del Proyecto

```
SortVisualizer/
├── src/
│   └── sortvisualizer/
│       ├── Main.java                         # Punto de entrada
│       ├── algorithms/
│       │   ├── SortCallback.java             # Interfaz de callbacks
│       │   ├── BubbleSort.java               # Algoritmo Bubble Sort (iterativo)
│       │   ├── ShellSort.java                # Algoritmo Shell Sort (iterativo)
│       │   └── QuickSort.java                # Algoritmo Quick Sort (recursivo)
│       ├── model/
│       │   ├── ExecutionStats.java           # Estadísticas de una ejecución
│       │   └── SessionHistory.java           # Historial de la sesión (singleton)
│       ├── reports/
│       │   └── HtmlReportGenerator.java      # Generador de reportes HTML
│       └── ui/
│           ├── MainMenuFrame.java            # Ventana principal con pestañas
│           ├── VisualizerPanel.java          # Panel central (control + log)
│           ├── BarChartPanel.java            # Gráfica de barras personalizada
│           ├── StatsPanel.java               # Panel de estadísticas
│           └── ReportsFrame.java             # Ventana de historial/reportes
├── nbproject/
│   ├── project.xml
│   └── project.properties
└── manifest.mf
```

---

## 2. Tecnologías y Librerías

| Componente | Tecnología | Versión |
|---|---|---|
| Lenguaje | Java | JDK 11+ |
| Interfaz gráfica | Java Swing / AWT | Incluida en JDK |
| Gráfica de barras | `Graphics2D` personalizado | Incluida en JDK |
| Concurrencia | `Thread` + `SwingUtilities.invokeLater()` | Incluida en JDK |
| Aleatorios | `java.util.Random` | Incluida en JDK |
| Reportes | HTML generado con `PrintWriter` | Incluida en JDK |
| IDE recomendado | Apache NetBeans | 17+ |

> **Nota sobre gráficos:** Se optó por pintura personalizada con `Graphics2D` en lugar de JFreeChart para eliminar dependencias externas. El resultado es una gráfica de barras con gradiente y colores de estado integrada directamente en Swing.

---

## 3. Descripción de Clases Principales

### `Main.java`
Punto de entrada. Lanza `MainMenuFrame` en el EDT usando `SwingUtilities.invokeLater()`.

### `SortCallback.java` (interfaz)
Define los métodos que cada algoritmo llama para notificar pasos a la UI:
- `onCompare(i, j, arr)` — par siendo comparado
- `onSwap(i, j, arr)` — par siendo intercambiado
- `onIteration(n, arr)` — fin de una iteración/pasada
- `onElementSorted(i, arr)` — elemento en posición final
- `onPartition(low, high, arr)` — paso de partición (Quick Sort)
- `getDelay()` — retarda en ms configurado por el usuario

### `BubbleSort.java`
Implementación iterativa clásica con dos bucles anidados. Optimizado con flag `swapped` para cortar temprano si el arreglo ya está ordenado.

### `ShellSort.java`
Implementación iterativa usando la secuencia de gaps de Knuth: `gap = gap * 3 + 1`. Reduce el gap progresivamente hasta 1. No usa recursión.

### `QuickSort.java`
Implementación **recursiva obligatoria**. Usa el **último elemento como pivote**.

```
quickSort(arr, low, high)
  └─ caso base: low >= high → return
  └─ pi = partition(arr, low, high)  ← coloca el pivote en su posición
  └─ quickSort(arr, low, pi-1)       ← llamada recursiva izquierda
  └─ quickSort(arr, pi+1, high)      ← llamada recursiva derecha
```

No simula recursión con pila manual. Es recursión real de Java.

### `ExecutionStats.java`
Acumula contadores durante una ejecución: comparaciones, intercambios, iteraciones. Incluye timer con `System.currentTimeMillis()`. También guarda los arreglos original y ordenado para el reporte.

### `SessionHistory.java`
Singleton. Almacena hasta 100 ejecuciones en un arreglo primitivo `ExecutionStats[]`. No usa colecciones del JCF.

### `BarChartPanel.java`
Extiende `JPanel` y sobreescribe `paintComponent()`. Dibuja barras con gradiente usando `Graphics2D`. Cada barra tiene un estado: `STATE_NORMAL`, `STATE_COMPARING`, `STATE_SWAPPING`, `STATE_SORTED`.

### `VisualizerPanel.java`
Panel principal. Implementa `SortCallback` inline. Ejecuta el algoritmo en un `Thread` separado. Todas las actualizaciones de UI usan `SwingUtilities.invokeLater()` para no bloquear el EDT.

### `HtmlReportGenerator.java`
Genera un archivo `.html` con estilo CSS embebido al finalizar cada ejecución. Incluye resumen de la ejecución e historial de la sesión en tabla.

---

## 4. Flujo General de Ejecución

```
Usuario presiona "Iniciar"
    │
    ├─► VisualizerPanel.startSorting()
    │       ├─ Copia el arreglo (para no modificar el original)
    │       ├─ Configura ExecutionStats
    │       └─ Crea Thread con el algoritmo seleccionado
    │
    ├─► Thread del algoritmo ejecuta (ej: BubbleSort.sort())
    │       ├─ Cada comparación → callback.onCompare() → SwingUtilities.invokeLater()
    │       ├─ Cada intercambio  → callback.onSwap()    → SwingUtilities.invokeLater()
    │       └─ Thread.sleep(delay) entre pasos
    │
    └─► Al terminar → onSortingFinished()
            ├─ SessionHistory.addExecution(stats)
            └─ HtmlReportGenerator.generate(stats, path)
```

---

## 5. Restricciones cumplidas

| Restricción | Cumplimiento |
|---|---|
| Solo `int[]` primitivos | ✅ Todos los arreglos son `int[]` |
| Sin Collections (ArrayList, etc.) | ✅ No se usa ninguna clase del JCF |
| Sin `Arrays.sort()` o similar | ✅ Todos los algoritmos implementados manualmente |
| Quick Sort recursivo real | ✅ Usa la pila de llamadas de Java, no Stack manual |
| Hilos con `Thread.sleep()` | ✅ Implementado en cada algoritmo |
| UI con `SwingUtilities.invokeLater()` | ✅ Todas las actualizaciones pasan por el EDT |

---

## 6. Compilación y Ejecución en NetBeans

1. Abrir NetBeans → *File → Open Project* → seleccionar la carpeta `SortVisualizer`
2. Verificar que la clase principal sea `sortvisualizer.Main` (*Project Properties → Run → Main Class*)
3. Presionar `F6` o el botón *Run Project*

Para generar el JAR ejecutable: *Clean and Build* (`Shift+F11`) → el JAR queda en `dist/SortVisualizer.jar`

---

*Visualizador de Algoritmos de Ordenamiento v1.0 — IPC1 USAC 2026*

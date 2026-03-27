# PRÁCTICA 2

**Curso:** Laboratorio de IPC1  
**Estudiante:** ALVARO MOISÉS GIRÓN MORALES  
**Carné:** 202501955  

---

# Manual Técnico  
## Visualizador de Algoritmos de Ordenamiento

---

## 1. Introducción

El presente documento describe los aspectos técnicos de la aplicación **Visualizador de Algoritmos de Ordenamiento**, desarrollada en **Java** como solución para la **Práctica 2 del Laboratorio de IPC1**.

El sistema fue construido como una aplicación de escritorio con interfaz gráfica, cuyo propósito es permitir la carga de datos, la ejecución visual de algoritmos de ordenamiento y la generación de reportes en formato HTML. El proyecto implementa de forma manual los algoritmos **Bubble Sort**, **Shell Sort** y **Quick Sort**, permitiendo orden ascendente o descendente, control de velocidad, visualización gráfica en tiempo real, estadísticas y bitácora de operaciones.

Este manual técnico documenta la arquitectura del proyecto, la organización del código, los componentes principales, la lógica de funcionamiento, las tecnologías utilizadas y los métodos más importantes implementados.

---

## 2. Objetivo del sistema

Desarrollar una aplicación gráfica en Java que permita:

- Cargar un conjunto de números enteros.
- Visualizar paso a paso el comportamiento de distintos algoritmos de ordenamiento.
- Mostrar estadísticas de ejecución en tiempo real.
- Registrar una bitácora de operaciones.
- Generar un reporte HTML al finalizar cada ejecución.
- Mantener un historial de ejecuciones durante la sesión actual.

---

## 3. Tecnologías utilizadas

| Componente | Tecnología utilizada | Observación |
|---|---|---|
| Lenguaje principal | Java | JDK 11 |
| Interfaz gráfica | Swing / AWT | Componentes estándar de Java |
| Visualización gráfica | `Graphics2D` | Implementación personalizada de gráfica de barras |
| Manejo de concurrencia | `Thread` | Ejecución del ordenamiento en segundo plano |
| Actualización de UI | `SwingUtilities.invokeLater()` | Para actualizar la interfaz sin bloquear el EDT |
| Aleatoriedad | `java.util.Random` | Generación de arreglos aleatorios |
| Reportes | HTML + `PrintWriter` / `FileWriter` | Generación automática de archivos `.html` |
| IDE de desarrollo | Apache NetBeans | Proyecto configurado como proyecto Java estándar |

### Nota sobre la visualización gráfica
Aunque en el enunciado se recomienda JFreeChart o una biblioteca equivalente, en este proyecto se optó por una implementación propia utilizando **`Graphics2D`**. Esta decisión elimina dependencias externas y permite controlar directamente el dibujo de barras, colores, gradientes, índices y estados visuales de cada elemento.

---

## 4. Restricciones técnicas cumplidas

El proyecto fue desarrollado respetando las restricciones establecidas en la práctica:

- Se utilizan únicamente arreglos primitivos de tipo `int[]`.
- No se utiliza `ArrayList`, `LinkedList`, `Vector`, `Stack` ni ninguna colección del Java Collections Framework.
- No se emplea `Arrays.sort()`, `Collections.sort()` ni ningún método de ordenamiento predefinido.
- Quick Sort fue implementado con **recursión real y explícita**.
- La interfaz fue desarrollada usando componentes estándar de Swing/AWT.
- La velocidad de visualización se controla con `Thread.sleep()`.
- Las actualizaciones visuales se realizan mediante `SwingUtilities.invokeLater()`.

---

## 5. Estructura general del proyecto

La estructura real del proyecto está organizada de la siguiente manera:

```bash
NB/
├── build.xml
├── DiagramaFlujo.md
├── manifest.mf
├── ManualTecnico.md
├── ManualUsuario.md
├── nbproject/
│   ├── build-impl.xml
│   ├── genfiles.properties
│   ├── project.properties
│   ├── project.xml
│   └── private/
│       └── private.xml
└── src/
    └── sortvisualizer/
        ├── Main.java
        ├── algorithms/
        │   ├── BubbleSort.java
        │   ├── QuickSort.java
        │   ├── ShellSort.java
        │   └── SortCallback.java
        ├── model/
        │   ├── ExecutionStats.java
        │   └── SessionHistory.java
        ├── reports/
        │   └── HtmlReportGenerator.java
        └── ui/
            ├── BarChartPanel.java
            ├── MainMenuFrame.java
            ├── ReportsFrame.java
            ├── StatsPanel.java
            └── VisualizerPanel.java

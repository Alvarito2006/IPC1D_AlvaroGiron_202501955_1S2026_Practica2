# PRÁCTICA 2

**Curso:** Laboratorio de IPC1  
**Estudiante:** ALVARO MOISÉS GIRÓN MORALES  
**Carné:** 202501955  

---

# Visualizador de Algoritmos de Ordenamiento

## Descripción
Este proyecto consiste en una aplicación de escritorio desarrollada en **Java** que permite visualizar el funcionamiento de distintos algoritmos de ordenamiento de manera interactiva. La aplicación fue creada como solución a la **Práctica 2 del Laboratorio de IPC1**, cuyo objetivo es reforzar conceptos de programación orientada a objetos, estructuras básicas, recursividad, hilos e interfaces gráficas.

El sistema permite cargar datos manualmente o desde un archivo `.txt`, seleccionar un algoritmo de ordenamiento, definir el tipo de orden (ascendente o descendente), ajustar la velocidad de ejecución y observar en tiempo real el proceso de ordenamiento mediante una gráfica de barras, estadísticas y un log de operaciones.

---

## Objetivos del proyecto
- Implementar una aplicación gráfica en Java.
- Visualizar paso a paso el comportamiento de algoritmos de ordenamiento.
- Aplicar conceptos de:
  - Programación Orientada a Objetos.
  - Recursividad.
  - Hilos.
  - Manejo de interfaces gráficas con Swing/AWT.
- Generar reportes automáticos de cada ejecución.

---

## Funcionalidades principales

### 1. Carga de datos
La aplicación permite ingresar los datos de dos formas:
- **Desde archivo `.txt`**
- **Desde un área de texto**

Los valores deben estar separados por comas o saltos de línea.

### 2. Generación de arreglo aleatorio
Se puede generar automáticamente un arreglo de números enteros aleatorios con un tamaño configurable.

### 3. Selección de algoritmo
El sistema incluye los siguientes algoritmos:
- **Bubble Sort**
- **Shell Sort**
- **Quick Sort** (implementado de forma recursiva)

### 4. Selección de orden
Se puede ordenar en:
- **Ascendente**
- **Descendente**

### 5. Control de velocidad
La visualización puede ejecutarse con distintas velocidades:
- **Lento**
- **Medio**
- **Rápido**

### 6. Visualización gráfica
Se representa el arreglo en una gráfica de barras que cambia dinámicamente durante el proceso de ordenamiento.

### 7. Estadísticas en tiempo real
Durante cada ejecución se muestran:
- Comparaciones
- Intercambios
- Iteraciones

### 8. Log de operaciones
Se registra paso a paso lo que hace el algoritmo:
- Comparaciones
- Intercambios
- Particiones en Quick Sort
- Inicio y fin del proceso

### 9. Reportes
Al finalizar cada ejecución, el sistema genera un **reporte en HTML** con:
- Algoritmo utilizado
- Tipo de orden
- Arreglo original
- Arreglo ordenado
- Comparaciones
- Intercambios
- Iteraciones
- Tiempo total
- Velocidad seleccionada

Además, se mantiene un historial de ejecuciones realizadas durante la sesión activa.

---

## Restricciones de la práctica
Para cumplir con los lineamientos establecidos, el proyecto respeta las siguientes restricciones:

- Se utilizan únicamente arreglos primitivos `int[]`.
- No se utiliza `ArrayList`, `LinkedList`, `Vector`, `Stack` ni ninguna colección del Java Collections Framework.
- No se utiliza `Arrays.sort()`, `Collections.sort()` ni métodos predefinidos de ordenamiento.
- **Quick Sort** está implementado con recursión real y explícita.
- La interfaz gráfica está desarrollada con componentes estándar de Swing/AWT.
- La velocidad de ejecución se controla mediante hilos y pausas con `Thread.sleep()`.

---

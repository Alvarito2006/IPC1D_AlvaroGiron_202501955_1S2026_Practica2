# Diagrama de Flujo — Visualizador de Algoritmos de Ordenamiento

## Flujo General del Sistema

```mermaid
flowchart TD
    A([🟢 Inicio]) --> B[Lanzar MainMenuFrame]
    B --> C{Usuario elige pestaña}

    C -->|Visualizador| D[Panel de Control]
    C -->|Reportes| R[Ver historial de sesión]
    C -->|Ayuda| H[Ver documentación]

    D --> E{¿Cómo ingresar datos?}
    E -->|Escribir manualmente| F[JTextArea + botón Cargar]
    E -->|Generar aleatorio| G[Math.random / java.util.Random]
    E -->|Archivo .txt| I[JFileChooser → leer líneas]

    F --> J[Parsear números a int[]]
    G --> J
    I --> J

    J --> K{¿Datos válidos?}
    K -->|No| L[Mostrar error]
    L --> D
    K -->|Sí| M[Mostrar arreglo en BarChartPanel]

    M --> N[Configurar algoritmo, orden y velocidad]
    N --> O[Presionar ▶ Iniciar]

    O --> P[Crear ExecutionStats - reiniciar contadores]
    P --> Q[Crear Thread separado con el algoritmo]
    Q --> S{¿Algoritmo seleccionado?}

    S -->|Bubble Sort| BS[BubbleSort.sort]
    S -->|Shell Sort| SS[ShellSort.sort]
    S -->|Quick Sort| QS[QuickSort.sort - RECURSIVO]

    BS --> CB[SortCallback.onCompare / onSwap / onIteration]
    SS --> CB
    QS --> CB

    CB --> TS[Thread.sleep - delay configurado]
    TS --> EDT[SwingUtilities.invokeLater]
    EDT --> UP[Actualizar BarChartPanel + StatsPanel + Log]
    UP --> FIN{¿Ordenamiento terminado?}

    FIN -->|No - continuar| CB
    FIN -->|Sí| DONE[Marcar todos los elementos en verde]

    DONE --> SAV[SessionHistory.addExecution]
    SAV --> REP[HtmlReportGenerator.generate → archivo .html]
    REP --> END([🔴 Ejecución completada])
```

---

## Flujo de Quick Sort Recursivo

```mermaid
flowchart TD
    QS["quickSort(arr, low, high)"]
    BASE{low >= high?}
    BASE -->|Sí - Caso base| RET([return])
    BASE -->|No| PART["partition(arr, low, high)"]

    PART --> PV[Pivote = arr high]
    PV --> LOOP["Recorrer j = low .. high-1"]
    LOOP --> CMP{arr j <= pivote?}
    CMP -->|Sí| SWAP[Intercambiar arr i++ y arr j]
    CMP -->|No| LOOP
    SWAP --> LOOP
    LOOP --> FP[Colocar pivote en posición i+1]
    FP --> PI[Retornar índice pi]

    PI --> REC1["quickSort(arr, low, pi-1)"]
    PI --> REC2["quickSort(arr, pi+1, high)"]

    REC1 --> QS
    REC2 --> QS

    QS --> BASE
```

---

## Flujo de Actualización de la UI

```mermaid
sequenceDiagram
    participant T as Thread Algoritmo
    participant CB as SortCallback
    participant EDT as EDT Swing
    participant BAR as BarChartPanel
    participant LOG as LogArea
    participant STA as StatsPanel

    T->>CB: onCompare(i, j, arr)
    CB->>EDT: SwingUtilities.invokeLater()
    EDT->>BAR: updateData(arr, estados) → repaint()
    EDT->>LOG: append("[Paso N] Comparando...")
    EDT->>STA: update(comparaciones, intercambios, iteraciones)

    T->>T: Thread.sleep(delay)

    T->>CB: onSwap(i, j, arr)
    CB->>EDT: SwingUtilities.invokeLater()
    EDT->>BAR: updateData(arr, estados ROJO) → repaint()
    EDT->>LOG: append("[Paso N] Intercambiando...")

    T->>CB: onElementSorted(index, arr)
    CB->>EDT: SwingUtilities.invokeLater()
    EDT->>BAR: estado[index] = VERDE → repaint()
```

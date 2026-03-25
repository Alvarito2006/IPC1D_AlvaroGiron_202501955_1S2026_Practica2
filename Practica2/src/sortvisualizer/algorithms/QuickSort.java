package sortvisualizer.algorithms;

import sortvisualizer.model.ExecutionStats;

/**
 * Implementación RECURSIVA de Quick Sort (obligatorio según la práctica).
 *
 * Estructura recursiva explícita:
 *   - quickSort(arr, low, high) → caso base: low >= high
 *   - partition(arr, low, high) → selecciona pivote (último elemento) y reordena
 *   - Llamadas recursivas: quickSort(arr, low, pi-1) y quickSort(arr, pi+1, high)
 *
 * NO se simula con pila manual (Stack). Es recursión real de Java.
 */
public class QuickSort {

    // Referencias compartidas entre todas las llamadas recursivas
    private static ExecutionStats sharedStats;
    private static SortCallback sharedCallback;
    private static boolean ascending;
    private static int iterationCount;

    /**
     * Punto de entrada público para iniciar Quick Sort.
     *
     * @param arr        Arreglo de enteros a ordenar (int[] primitivo).
     * @param ascendente true = ascendente, false = descendente.
     * @param stats      Objeto donde se acumulan las estadísticas.
     * @param callback   Interfaz para notificar pasos a la UI.
     */
    public static void sort(int[] arr, boolean ascendente, ExecutionStats stats, SortCallback callback) {
        // Guardar referencias estáticas para uso en llamadas recursivas
        sharedStats = stats;
        sharedCallback = callback;
        ascending = ascendente;
        iterationCount = 0;

        // Llamada inicial que desencadena la recursión
        quickSort(arr, 0, arr.length - 1);

        // Marcar todos los elementos como ordenados al finalizar
        for (int i = 0; i < arr.length; i++) {
            sharedCallback.onElementSorted(i, arr);
        }
    }

    /**
     * Método recursivo principal de Quick Sort.
     * CASO BASE: low >= high → no hay nada que ordenar.
     *
     * @param arr  Arreglo de enteros.
     * @param low  Índice inferior de la subpartición actual.
     * @param high Índice superior de la subpartición actual.
     */
    private static void quickSort(int[] arr, int low, int high) {
        // CASO BASE: si el subarreglo tiene 0 o 1 elementos, ya está ordenado
        if (low >= high) return;

        iterationCount++;
        sharedStats.incrementIterations();

        // Notificar la partición actual a la UI
        sharedCallback.onPartition(low, high, arr);

        // Obtener el índice del pivote después de particionar
        int pi = partition(arr, low, high);

        // Notificar fin de iteración
        sharedCallback.onIteration(iterationCount, arr);

        // Pausa para visualización
        try {
            Thread.sleep(sharedCallback.getDelay());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        // LLAMADAS RECURSIVAS obligatorias sobre cada mitad
        quickSort(arr, low, pi - 1);   // Subpartición izquierda
        quickSort(arr, pi + 1, high);  // Subpartición derecha
    }

    /**
     * Particiona el subarreglo arr[low..high] usando el ÚLTIMO elemento como pivote.
     * Reordena los elementos de forma que todos los menores al pivote queden a su
     * izquierda y todos los mayores queden a su derecha.
     *
     * @param arr  Arreglo de enteros.
     * @param low  Índice inferior.
     * @param high Índice superior (pivote = arr[high]).
     * @return     Índice final del pivote tras la partición.
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Selección del pivote: último elemento
        int i = low - 1;       // Índice del elemento más pequeño

        for (int j = low; j < high; j++) {
            // Notificar comparación con el pivote
            sharedCallback.onCompare(j, high, arr);
            sharedStats.incrementComparisons();

            // Determinar si arr[j] debe ir antes del pivote
            boolean shouldSwap = ascending
                    ? arr[j] <= pivot
                    : arr[j] >= pivot;

            if (shouldSwap) {
                i++;
                // Intercambiar arr[i] y arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                sharedStats.incrementSwaps();
                sharedCallback.onSwap(i, j, arr);

                // Pausa para visualización
                try {
                    Thread.sleep(sharedCallback.getDelay());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return i + 1;
                }
            }
        }

        // Colocar el pivote en su posición correcta
        int pivotPos = i + 1;
        int temp = arr[pivotPos];
        arr[pivotPos] = arr[high];
        arr[high] = temp;

        sharedStats.incrementSwaps();
        sharedCallback.onSwap(pivotPos, high, arr);

        // Marcar el pivote como ordenado
        sharedCallback.onElementSorted(pivotPos, arr);

        return pivotPos;
    }
}

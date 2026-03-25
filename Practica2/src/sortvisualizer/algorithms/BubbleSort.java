package sortvisualizer.algorithms;

import sortvisualizer.model.ExecutionStats;

/**
 * Implementación de Bubble Sort.
 * Algoritmo iterativo con dos bucles anidados.
 * Realiza pases completos reduciendo el rango en cada iteración.
 */
public class BubbleSort {

    /**
     * Ejecuta Bubble Sort sobre el arreglo dado.
     *
     * @param arr       Arreglo de enteros a ordenar (int[] primitivo).
     * @param ascending true = ascendente, false = descendente.
     * @param stats     Objeto donde se acumulan las estadísticas.
     * @param callback  Interfaz para notificar pasos a la UI.
     */
    public static void sort(int[] arr, boolean ascending, ExecutionStats stats, SortCallback callback) {
        int n = arr.length;
        int iterCount = 0;

        // Bucle externo: controla cuántos pases se hacen
        for (int i = 0; i < n - 1; i++) {
            iterCount++;
            stats.incrementIterations();

            boolean swapped = false;

            // Bucle interno: compara pares adyacentes
            for (int j = 0; j < n - i - 1; j++) {

                // Notificar comparación a la UI
                callback.onCompare(j, j + 1, arr);
                stats.incrementComparisons();

                // Determinar si se debe intercambiar según el orden
                boolean shouldSwap = ascending
                        ? arr[j] > arr[j + 1]
                        : arr[j] < arr[j + 1];

                if (shouldSwap) {
                    // Realizar el intercambio
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    stats.incrementSwaps();
                    swapped = true;

                    // Notificar intercambio a la UI
                    callback.onSwap(j, j + 1, arr);
                }

                // Pausa para visualización
                try {
                    Thread.sleep(callback.getDelay());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; // Salir si el hilo fue interrumpido (botón detener)
                }
            }

            // El elemento en posición n-i-1 ya está ordenado
            callback.onElementSorted(n - i - 1, arr);

            // Notificar fin de iteración
            callback.onIteration(iterCount, arr);

            // Optimización: si no hubo intercambios, el arreglo ya está ordenado
            if (!swapped) break;
        }

        // Marcar el primer elemento como ordenado (queda por defecto)
        callback.onElementSorted(0, arr);
    }
}

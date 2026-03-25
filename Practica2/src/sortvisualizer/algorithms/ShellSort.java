package sortvisualizer.algorithms;

import sortvisualizer.model.ExecutionStats;

/**
 * Implementación de Shell Sort.
 * Algoritmo iterativo con secuencia de gaps decrecientes.
 * Ordena subconjuntos del arreglo a distancias (gaps) cada vez menores.
 * No se aplica reformulación recursiva; es puramente iterativo.
 */
public class ShellSort {

    /**
     * Ejecuta Shell Sort sobre el arreglo dado.
     *
     * @param arr       Arreglo de enteros a ordenar (int[] primitivo).
     * @param ascending true = ascendente, false = descendente.
     * @param stats     Objeto donde se acumulan las estadísticas.
     * @param callback  Interfaz para notificar pasos a la UI.
     */
    public static void sort(int[] arr, boolean ascending, ExecutionStats stats, SortCallback callback) {
        int n = arr.length;

        // Calcular el gap inicial usando la secuencia de Knuth (3^k - 1) / 2
        int gap = 1;
        while (gap < n / 3) {
            gap = gap * 3 + 1;
        }

        // Reducir el gap progresivamente
        while (gap >= 1) {
            stats.incrementIterations();
            int iterCount = stats.getIterations();

            // Ordenar por inserción con el gap actual
            for (int i = gap; i < n; i++) {
                int temp = arr[i]; // Elemento a insertar
                int j = i;

                // Mover elementos que son mayores/menores que temp
                while (j >= gap) {
                    // Notificar comparación
                    callback.onCompare(j - gap, j, arr);
                    stats.incrementComparisons();

                    boolean shouldMove = ascending
                            ? arr[j - gap] > temp
                            : arr[j - gap] < temp;

                    if (!shouldMove) break;

                    // Desplazar el elemento
                    arr[j] = arr[j - gap];
                    stats.incrementSwaps();

                    // Notificar el desplazamiento como intercambio
                    callback.onSwap(j - gap, j, arr);

                    j -= gap;

                    // Pausa para visualización
                    try {
                        Thread.sleep(callback.getDelay());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                arr[j] = temp;
            }

            // Notificar fin de iteración con este gap
            callback.onIteration(iterCount, arr);

            // Reducir el gap según la secuencia de Knuth
            gap = (gap - 1) / 3;
        }

        // Marcar todos los elementos como ordenados al final
        for (int i = 0; i < n; i++) {
            callback.onElementSorted(i, arr);
        }
    }
}

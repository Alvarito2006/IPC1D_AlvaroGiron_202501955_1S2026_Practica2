package sortvisualizer.algorithms;

/**
 * Interfaz de callback que los algoritmos usan para notificar
 * cada paso a la interfaz gráfica.
 * Permite actualizar la visualización y el log en tiempo real.
 */
public interface SortCallback {

    /**
     * Notifica que se está comparando un par de elementos.
     * @param i Índice del primer elemento.
     * @param j Índice del segundo elemento.
     * @param arr Estado actual del arreglo.
     */
    void onCompare(int i, int j, int[] arr);

    /**
     * Notifica que se está realizando un intercambio.
     * @param i Índice del primer elemento.
     * @param j Índice del segundo elemento.
     * @param arr Estado actual del arreglo.
     */
    void onSwap(int i, int j, int[] arr);

    /**
     * Notifica que una iteración (pasada) ha completado.
     * @param iterNum Número de iteración actual.
     * @param arr Estado actual del arreglo.
     */
    void onIteration(int iterNum, int[] arr);

    /**
     * Notifica que un elemento ya quedó en su posición final.
     * @param index Índice del elemento ordenado.
     * @param arr Estado actual del arreglo.
     */
    void onElementSorted(int index, int[] arr);

    /**
     * Notifica un paso de partición de Quick Sort.
     * @param low  Límite inferior de la partición.
     * @param high Límite superior (pivote).
     * @param arr  Estado actual del arreglo.
     */
    void onPartition(int low, int high, int[] arr);

    /**
     * Retorna el retardo en milisegundos configurado por el usuario.
     * Usado por Thread.sleep() en cada paso del algoritmo.
     */
    int getDelay();
}

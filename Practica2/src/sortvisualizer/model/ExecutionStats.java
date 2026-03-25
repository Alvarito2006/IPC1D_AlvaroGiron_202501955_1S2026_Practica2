package sortvisualizer.model;

/**
 * Modelo que almacena las estadísticas de una ejecución de ordenamiento.
 * Registra comparaciones, intercambios, iteraciones y tiempo.
 */
public class ExecutionStats {

    private int comparisons;    // Número de comparaciones realizadas
    private int swaps;          // Número de intercambios realizados
    private int iterations;     // Número de iteraciones/pasadas realizadas
    private long startTime;     // Tiempo de inicio en ms
    private long endTime;       // Tiempo de fin en ms

    // Datos de la ejecución
    private String algorithm;
    private String order;
    private String speed;
    private int[] originalArray;
    private int[] sortedArray;
    private int arraySize;

    // Número de ejecución en la sesión actual
    private int executionNumber;

    public ExecutionStats() {
        reset();
    }

    /** Reinicia todos los contadores a cero. */
    public void reset() {
        comparisons = 0;
        swaps = 0;
        iterations = 0;
        startTime = 0;
        endTime = 0;
    }

    /** Incrementa el contador de comparaciones. */
    public void incrementComparisons() {
        comparisons++;
    }

    /** Incrementa el contador de intercambios. */
    public void incrementSwaps() {
        swaps++;
    }

    /** Incrementa el contador de iteraciones. */
    public void incrementIterations() {
        iterations++;
    }

    /** Registra el tiempo de inicio. */
    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    /** Registra el tiempo de fin. */
    public void stopTimer() {
        endTime = System.currentTimeMillis();
    }

    /** Retorna el tiempo total de ejecución en milisegundos. */
    public long getElapsedTime() {
        if (endTime == 0) {
            return System.currentTimeMillis() - startTime;
        }
        return endTime - startTime;
    }

    // ---- Getters y Setters ----

    public int getComparisons() { return comparisons; }
    public int getSwaps() { return swaps; }
    public int getIterations() { return iterations; }
    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public String getOrder() { return order; }
    public void setOrder(String order) { this.order = order; }
    public String getSpeed() { return speed; }
    public void setSpeed(String speed) { this.speed = speed; }
    public int[] getOriginalArray() { return originalArray; }
    public void setOriginalArray(int[] arr) {
        originalArray = new int[arr.length];
        System.arraycopy(arr, 0, originalArray, 0, arr.length);
    }
    public int[] getSortedArray() { return sortedArray; }
    public void setSortedArray(int[] arr) {
        sortedArray = new int[arr.length];
        System.arraycopy(arr, 0, sortedArray, 0, arr.length);
    }
    public int getArraySize() { return arraySize; }
    public void setArraySize(int arraySize) { this.arraySize = arraySize; }
    public int getExecutionNumber() { return executionNumber; }
    public void setExecutionNumber(int executionNumber) { this.executionNumber = executionNumber; }

    /** Convierte un arreglo a String separado por comas. */
    public static String arrayToString(int[] arr) {
        if (arr == null || arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

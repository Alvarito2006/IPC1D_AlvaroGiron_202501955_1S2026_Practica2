package sortvisualizer.model;

/**
 * Almacena el historial de ejecuciones de la sesión actual.
 * No persiste entre sesiones (se reinicia al cerrar la app).
 * Usa un arreglo de tamaño fijo para no usar Collections.
 */
public class SessionHistory {

    private static final int MAX_EXECUTIONS = 100;
    private ExecutionStats[] history;
    private int count;

    // Instancia única (singleton)
    private static SessionHistory instance;

    private SessionHistory() {
        history = new ExecutionStats[MAX_EXECUTIONS];
        count = 0;
    }

    /** Retorna la instancia única del historial de sesión. */
    public static SessionHistory getInstance() {
        if (instance == null) {
            instance = new SessionHistory();
        }
        return instance;
    }

    /**
     * Agrega una ejecución al historial.
     * @param stats Estadísticas de la ejecución completada.
     */
    public void addExecution(ExecutionStats stats) {
        if (count < MAX_EXECUTIONS) {
            stats.setExecutionNumber(count + 1);
            history[count] = stats;
            count++;
        }
    }

    /**
     * Retorna todas las ejecuciones registradas.
     * @return Arreglo de ExecutionStats con las ejecuciones.
     */
    public ExecutionStats[] getHistory() {
        ExecutionStats[] result = new ExecutionStats[count];
        for (int i = 0; i < count; i++) {
            result[i] = history[i];
        }
        return result;
    }

    /** Retorna el número total de ejecuciones registradas. */
    public int getCount() {
        return count;
    }
}

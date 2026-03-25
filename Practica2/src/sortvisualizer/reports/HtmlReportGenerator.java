package sortvisualizer.reports;

import sortvisualizer.model.ExecutionStats;
import sortvisualizer.model.SessionHistory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Generador de reportes HTML.
 * Crea un resumen detallado de cada ejecución y el historial de la sesión.
 */
public class HtmlReportGenerator {

    /**
     * Genera un archivo HTML con el resumen de la ejecución más reciente
     * y el historial completo de la sesión.
     *
     * @param stats     Estadísticas de la ejecución que acaba de finalizar.
     * @param outputPath Ruta donde se guardará el archivo .html
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    public static void generate(ExecutionStats stats, String outputPath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println(buildHtml(stats));
        }
    }

    /** Construye el contenido HTML completo. */
    private static String buildHtml(ExecutionStats stats) {
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        ExecutionStats[] history = SessionHistory.getInstance().getHistory();

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n<html lang='es'>\n<head>\n");
        sb.append("<meta charset='UTF-8'>\n");
        sb.append("<title>Reporte de Ordenamiento - IPC1</title>\n");
        sb.append("<style>\n");
        sb.append("  body { font-family: 'Segoe UI', Arial, sans-serif; background: #1a1a2e; color: #e0e0e0; margin: 0; padding: 20px; }\n");
        sb.append("  h1 { color: #4285f4; text-align: center; border-bottom: 2px solid #4285f4; padding-bottom: 10px; }\n");
        sb.append("  h2 { color: #34a853; margin-top: 30px; }\n");
        sb.append("  .card { background: #16213e; border-radius: 10px; padding: 20px; margin: 15px 0; border: 1px solid #2a2a4a; }\n");
        sb.append("  .stats-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin: 15px 0; }\n");
        sb.append("  .stat-box { background: #0f3460; border-radius: 8px; padding: 15px; text-align: center; }\n");
        sb.append("  .stat-number { font-size: 2em; font-weight: bold; }\n");
        sb.append("  .stat-label { font-size: 0.85em; color: #aaa; margin-top: 5px; }\n");
        sb.append("  .comparisons { color: #4285f4; }\n");
        sb.append("  .swaps { color: #fbbc04; }\n");
        sb.append("  .iterations { color: #34a853; }\n");
        sb.append("  .array-box { background: #0f3460; border-radius: 6px; padding: 10px 15px; font-family: monospace; font-size: 0.95em; word-break: break-all; }\n");
        sb.append("  table { width: 100%; border-collapse: collapse; margin-top: 10px; }\n");
        sb.append("  th { background: #4285f4; color: white; padding: 10px; text-align: center; }\n");
        sb.append("  td { padding: 8px 10px; text-align: center; border-bottom: 1px solid #2a2a4a; }\n");
        sb.append("  tr:nth-child(even) { background: #1e2d4a; }\n");
        sb.append("  tr:last-child td { font-weight: bold; background: #0d2137; }\n");
        sb.append("  .footer { text-align: center; color: #666; margin-top: 30px; font-size: 0.85em; }\n");
        sb.append("  .badge { display: inline-block; padding: 3px 10px; border-radius: 12px; font-size: 0.85em; }\n");
        sb.append("  .asc { background: #34a853; color: white; }\n");
        sb.append("  .desc { background: #ea4335; color: white; }\n");
        sb.append("</style>\n</head>\n<body>\n");

        sb.append("<h1>📊 Reporte de Ordenamiento - IPC1</h1>\n");
        sb.append("<p style='text-align:center; color:#888;'>Generado: ").append(timestamp).append("</p>\n");

        // --- Resumen de la ejecución actual ---
        sb.append("<h2>✅ Resumen de la Ejecución #").append(stats.getExecutionNumber()).append("</h2>\n");
        sb.append("<div class='card'>\n");

        sb.append("<p><strong>Algoritmo:</strong> ").append(stats.getAlgorithm()).append("</p>\n");
        sb.append("<p><strong>Orden:</strong> <span class='badge ")
          .append(stats.getOrder().equals("Ascendente") ? "asc" : "desc")
          .append("'>").append(stats.getOrder()).append("</span></p>\n");
        sb.append("<p><strong>Velocidad:</strong> ").append(stats.getSpeed()).append("</p>\n");
        sb.append("<p><strong>Tamaño del arreglo:</strong> ").append(stats.getArraySize()).append(" elementos</p>\n");
        sb.append("<p><strong>Tiempo de ejecución:</strong> ").append(stats.getElapsedTime()).append(" ms</p>\n");

        sb.append("<p><strong>Arreglo original:</strong></p>\n");
        sb.append("<div class='array-box'>").append(ExecutionStats.arrayToString(stats.getOriginalArray())).append("</div>\n");
        sb.append("<p><strong>Arreglo ordenado:</strong></p>\n");
        sb.append("<div class='array-box'>").append(ExecutionStats.arrayToString(stats.getSortedArray())).append("</div>\n");

        sb.append("<div class='stats-grid'>\n");
        sb.append("  <div class='stat-box'><div class='stat-number comparisons'>").append(stats.getComparisons())
          .append("</div><div class='stat-label'>Comparaciones</div></div>\n");
        sb.append("  <div class='stat-box'><div class='stat-number swaps'>").append(stats.getSwaps())
          .append("</div><div class='stat-label'>Intercambios</div></div>\n");
        sb.append("  <div class='stat-box'><div class='stat-number iterations'>").append(stats.getIterations())
          .append("</div><div class='stat-label'>Iteraciones</div></div>\n");
        sb.append("</div>\n");
        sb.append("</div>\n");

        // --- Historial de la sesión ---
        sb.append("<h2>📋 Historial de Ejecuciones de la Sesión</h2>\n");
        sb.append("<div class='card'>\n");
        sb.append("<table>\n<thead><tr>\n");
        sb.append("<th>#</th><th>Algoritmo</th><th>Orden</th><th>n</th>");
        sb.append("<th>Comparaciones</th><th>Intercambios</th><th>Iteraciones</th><th>Tiempo (ms)</th>\n");
        sb.append("</tr></thead>\n<tbody>\n");

        for (ExecutionStats e : history) {
            sb.append("<tr>");
            sb.append("<td>").append(e.getExecutionNumber()).append("</td>");
            sb.append("<td>").append(e.getAlgorithm()).append("</td>");
            sb.append("<td>").append(e.getOrder()).append("</td>");
            sb.append("<td>").append(e.getArraySize()).append("</td>");
            sb.append("<td>").append(e.getComparisons()).append("</td>");
            sb.append("<td>").append(e.getSwaps()).append("</td>");
            sb.append("<td>").append(e.getIterations()).append("</td>");
            sb.append("<td>").append(e.getElapsedTime()).append("</td>");
            sb.append("</tr>\n");
        }

        sb.append("</tbody>\n</table>\n</div>\n");

        sb.append("<div class='footer'>\n");
        sb.append("<p>Visualizador de Algoritmos de Ordenamiento v1.0 | IPC1 - USAC 2026</p>\n");
        sb.append("</div>\n</body>\n</html>");

        return sb.toString();
    }
}

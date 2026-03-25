package sortvisualizer.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Panel de visualización de barras para el ordenamiento.
 * Dibuja el arreglo como un gráfico de barras coloreadas
 * según el estado de cada elemento (normal, comparando, intercambiando, ordenado).
 *
 * Reemplaza JFreeChart usando pintura personalizada con Graphics2D,
 * lo cual es más ligero y no requiere librería externa.
 */
public class BarChartPanel extends JPanel {

    // ---- Constantes de colores por estado ----
    private static final Color COLOR_NORMAL      = new Color(66, 133, 244);   // Azul claro
    private static final Color COLOR_COMPARING   = new Color(251, 188, 4);    // Amarillo/Naranja
    private static final Color COLOR_SWAPPING    = new Color(234, 67, 53);    // Rojo/Coral
    private static final Color COLOR_SORTED      = new Color(52, 168, 83);    // Verde
    private static final Color COLOR_BACKGROUND  = new Color(30, 30, 40);     // Fondo oscuro
    private static final Color COLOR_TEXT        = new Color(220, 220, 220);  // Texto claro
    private static final Color COLOR_GRID        = new Color(60, 60, 70);     // Líneas de cuadrícula

    // Estados posibles de cada barra
    public static final int STATE_NORMAL      = 0;
    public static final int STATE_COMPARING   = 1;
    public static final int STATE_SWAPPING    = 2;
    public static final int STATE_SORTED      = 3;

    // Datos del arreglo y estados
    private int[] data;
    private int[] states;
    private int maxValue;

    public BarChartPanel() {
        setBackground(COLOR_BACKGROUND);
        setPreferredSize(new Dimension(600, 350));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 100)),
                "Visualización",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                new Color(180, 180, 200)
        ));
    }

    /**
     * Actualiza el arreglo y los estados de las barras.
     * Debe llamarse desde el EDT (SwingUtilities.invokeLater).
     *
     * @param arr    Arreglo actual de valores.
     * @param states Estado de cada elemento (normal/comparando/intercambiando/ordenado).
     */
    public void updateData(int[] arr, int[] states) {
        if (arr == null) return;

        this.data = new int[arr.length];
        System.arraycopy(arr, 0, this.data, 0, arr.length);

        this.states = new int[states.length];
        System.arraycopy(states, 0, this.states, 0, states.length);

        // Calcular el valor máximo para escalar las barras
        maxValue = 0;
        for (int val : arr) {
            if (val > maxValue) maxValue = val;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (data == null || data.length == 0) {
            // Mensaje cuando no hay datos
            g.setColor(new Color(120, 120, 140));
            g.setFont(new Font("SansSerif", Font.ITALIC, 14));
            g.drawString("Carga datos y presiona Iniciar para visualizar...",
                    getWidth() / 2 - 180, getHeight() / 2);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth  = getWidth();
        int panelHeight = getHeight();

        // Márgenes
        int marginLeft   = 30;
        int marginRight  = 15;
        int marginTop    = 30;
        int marginBottom = 40;

        int chartWidth  = panelWidth  - marginLeft - marginRight;
        int chartHeight = panelHeight - marginTop  - marginBottom;

        // Dibujar cuadrícula horizontal
        g2d.setColor(COLOR_GRID);
        g2d.setStroke(new BasicStroke(0.5f));
        int gridLines = 5;
        for (int i = 0; i <= gridLines; i++) {
            int y = marginTop + (chartHeight * i / gridLines);
            g2d.drawLine(marginLeft, y, marginLeft + chartWidth, y);

            // Etiquetas del eje Y
            int value = maxValue - (maxValue * i / gridLines);
            g2d.setColor(COLOR_TEXT);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
            g2d.drawString(String.valueOf(value), 2, y + 4);
            g2d.setColor(COLOR_GRID);
        }

        // Calcular dimensiones de cada barra
        int n        = data.length;
        int barWidth = Math.max(2, (chartWidth - n) / n);
        int gap      = Math.max(1, (chartWidth - n * barWidth) / (n + 1));

        // Dibujar cada barra
        for (int i = 0; i < n; i++) {
            // Altura proporcional al valor
            int barHeight = maxValue > 0
                    ? (int) ((double) data[i] / maxValue * chartHeight)
                    : 0;

            int x = marginLeft + gap + i * (barWidth + gap);
            int y = marginTop + chartHeight - barHeight;

            // Seleccionar color según el estado
            Color barColor;
            switch (states[i]) {
                case STATE_COMPARING: barColor = COLOR_COMPARING; break;
                case STATE_SWAPPING:  barColor = COLOR_SWAPPING;  break;
                case STATE_SORTED:    barColor = COLOR_SORTED;    break;
                default:              barColor = COLOR_NORMAL;    break;
            }

            // Dibujar la barra con gradiente
            GradientPaint gradient = new GradientPaint(
                    x, y, barColor.brighter(),
                    x, y + barHeight, barColor.darker()
            );
            g2d.setPaint(gradient);
            g2d.fillRoundRect(x, y, barWidth, barHeight, 3, 3);

            // Borde de la barra
            g2d.setColor(barColor.darker());
            g2d.setStroke(new BasicStroke(0.8f));
            g2d.drawRoundRect(x, y, barWidth, barHeight, 3, 3);

            // Mostrar el valor encima de la barra (solo si hay espacio)
            if (barWidth >= 14) {
                g2d.setColor(COLOR_TEXT);
                g2d.setFont(new Font("SansSerif", Font.BOLD, Math.min(10, barWidth - 2)));
                String val = String.valueOf(data[i]);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (barWidth - fm.stringWidth(val)) / 2;
                g2d.drawString(val, textX, y - 2);
            }

            // Índice debajo de la barra
            if (barWidth >= 10) {
                g2d.setColor(new Color(150, 150, 170));
                g2d.setFont(new Font("SansSerif", Font.PLAIN, 9));
                String idx = String.valueOf(i);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (barWidth - fm.stringWidth(idx)) / 2;
                g2d.drawString(idx, textX, panelHeight - marginBottom + 14);
            }
        }

        // Línea base del eje X
        g2d.setColor(new Color(100, 100, 120));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(marginLeft, marginTop + chartHeight,
                     marginLeft + chartWidth, marginTop + chartHeight);
    }
}

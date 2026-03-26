package sortvisualizer.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Panel de estadísticas que muestra en tiempo real:
 * - Comparaciones
 * - Intercambios
 * - Iteraciones
 *
 * Se actualiza con cada paso del algoritmo.
 */
public class StatsPanel extends JPanel {

    // Colores unificados con el tema oscuro
    private static final Color BG_PANEL     = new Color(35, 35, 45);
    private static final Color BORDER_COLOR = new Color(60, 60, 80);
    private static final Color TEXT_MUTED   = new Color(160, 160, 180);
    private static final Color ACCENT_BLUE  = new Color(76, 154, 255);
    private static final Color ACCENT_AMBER = new Color(255, 193, 7);
    private static final Color ACCENT_GREEN = new Color(76, 175, 80);

    private JLabel lblComparisons;
    private JLabel lblSwaps;
    private JLabel lblIterations;

    public StatsPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(2, 3, 15, 8));
        setBackground(BG_PANEL);
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                "Estadísticas",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                TEXT_MUTED
        ));

        // Crear los labels grandes de estadísticas
        lblComparisons = createStatLabel("0", ACCENT_BLUE);
        lblSwaps       = createStatLabel("0", ACCENT_AMBER);
        lblIterations  = createStatLabel("0", ACCENT_GREEN);

        // Etiquetas descriptivas
        JLabel descComp = createDescLabel("Comparaciones");
        JLabel descSwap = createDescLabel("Intercambios");
        JLabel descIter = createDescLabel("Iteraciones");

        // Añadir en el orden correcto (fila1: números, fila2: descripciones)
        add(lblComparisons);
        add(lblSwaps);
        add(lblIterations);
        add(descComp);
        add(descSwap);
        add(descIter);
    }

    /** Crea el label grande con el número. */
    private JLabel createStatLabel(String text, Color color) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 32));
        lbl.setForeground(color);
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        return lbl;
    }

    /** Crea el label pequeño descriptivo. */
    private JLabel createDescLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(TEXT_MUTED);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return lbl;
    }

    /**
     * Actualiza los valores mostrados en tiempo real.
     * Debe llamarse desde el EDT.
     *
     * @param comparisons Número de comparaciones.
     * @param swaps       Número de intercambios.
     * @param iterations  Número de iteraciones.
     */
    public void update(int comparisons, int swaps, int iterations) {
        lblComparisons.setText(String.valueOf(comparisons));
        lblSwaps.setText(String.valueOf(swaps));
        lblIterations.setText(String.valueOf(iterations));
    }

    /** Reinicia todos los contadores a cero. */
    public void reset() {
        update(0, 0, 0);
    }
}
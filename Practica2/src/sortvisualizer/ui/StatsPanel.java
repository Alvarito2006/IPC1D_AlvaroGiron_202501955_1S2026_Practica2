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

    private JLabel lblComparisons;
    private JLabel lblSwaps;
    private JLabel lblIterations;
    private JLabel lblTime;

    public StatsPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(2, 3, 10, 5));
        setBackground(new Color(35, 35, 50));
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                "Estadísticas",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                new Color(180, 180, 220)
        ));

        // Crear los labels grandes de estadísticas
        lblComparisons = createStatLabel("0", new Color(66, 133, 244));
        lblSwaps       = createStatLabel("0", new Color(251, 188, 4));
        lblIterations  = createStatLabel("0", new Color(52, 168, 83));

        // Etiquetas descriptivas
        JLabel descComp = createDescLabel("Comparaciones");
        JLabel descSwap = createDescLabel("Intercambios");
        JLabel descIter = createDescLabel("Iteraciones");

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
        lbl.setFont(new Font("SansSerif", Font.BOLD, 28));
        lbl.setForeground(color);
        return lbl;
    }

    /** Crea el label pequeño descriptivo. */
    private JLabel createDescLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(160, 160, 180));
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

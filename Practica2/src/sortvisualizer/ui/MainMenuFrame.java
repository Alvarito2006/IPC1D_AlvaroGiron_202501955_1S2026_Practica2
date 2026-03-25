package sortvisualizer.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Ventana del Menú Principal.
 * Es la primera pantalla que se muestra al iniciar la aplicación.
 * Permite acceder al visualizador, reportes y sección de ayuda.
 */
public class MainMenuFrame extends JFrame {

    private static final String APP_NAME    = "Visualizador de Algoritmos de Ordenamiento";
    private static final String APP_VERSION = "v1.0 - IPC1 USAC 2026";

    private ReportsFrame reportsFrame;

    public MainMenuFrame() {
        super(APP_NAME);
        reportsFrame = new ReportsFrame(); // Crear ventana de reportes (compartida)
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null); // Centrar en pantalla
        getContentPane().setBackground(new Color(20, 20, 35));
        setLayout(new BorderLayout());

        // ---- Panel de encabezado ----
        add(buildHeader(), BorderLayout.NORTH);

        // ---- Panel principal con pestañas ----
        JTabbedPane tabs = buildTabbedPane();
        tabs.setBackground(new Color(25, 25, 40));
        tabs.setForeground(Color.WHITE);
        tabs.setFont(new Font("SansSerif", Font.BOLD, 13));
        add(tabs, BorderLayout.CENTER);

        // ---- Barra de estado inferior ----
        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    /** Encabezado con nombre y subtítulo de la app. */
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 15, 28));
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel(APP_NAME, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(66, 133, 244));

        JLabel subtitle = new JLabel("Bubble Sort, Shell Sort y Quick Sort", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 13));
        subtitle.setForeground(new Color(130, 130, 180));

        header.add(title, BorderLayout.CENTER);
        header.add(subtitle, BorderLayout.SOUTH);

        return header;
    }

    /** Crea el panel de pestañas principal. */
    private JTabbedPane buildTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(25, 25, 40));

        // Pestaña 1: Visualizador (principal)
        VisualizerPanel visualizer = new VisualizerPanel(reportsFrame);
        tabs.addTab("🔢 Visualizador", visualizer);

        // Pestaña 2: Reportes
        JPanel reportTab = buildReportTab();
        tabs.addTab("📋 Reportes", reportTab);

        // Pestaña 3: Ayuda / Información
        JPanel helpTab = buildHelpTab();
        tabs.addTab("ℹ Ayuda", helpTab);

        return tabs;
    }

    /** Panel de la pestaña de Reportes. */
    private JPanel buildReportTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(25, 25, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel info = new JLabel(
                "<html><div style='text-align:center;color:#aaa;'>"
                + "El historial de ejecuciones se acumula mientras la aplicación esté abierta.<br>"
                + "Al cerrar la aplicación, el historial se pierde (no persiste entre sesiones).</div></html>",
                SwingConstants.CENTER);
        info.setFont(new Font("SansSerif", Font.ITALIC, 13));
        panel.add(info, BorderLayout.NORTH);

        JButton btnOpenReports = new JButton("📊 Abrir Ventana de Reportes");
        btnOpenReports.setBackground(new Color(52, 168, 83));
        btnOpenReports.setForeground(Color.WHITE);
        btnOpenReports.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnOpenReports.setFocusPainted(false);
        btnOpenReports.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnOpenReports.setPreferredSize(new Dimension(300, 60));
        btnOpenReports.addActionListener((ActionEvent e) -> {
            reportsFrame.loadHistory();
            reportsFrame.setVisible(true);
        });

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(25, 25, 40));
        centerPanel.add(btnOpenReports);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    /** Panel de la pestaña de Ayuda. */
    private JPanel buildHelpTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(25, 25, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        String helpText =
            "<html><body style='font-family:SansSerif; color:#ddd; background:#19192a;'>"
            + "<h2 style='color:#4285f4;'>Cómo usar el Visualizador</h2>"
            + "<ol>"
            + "<li><b>Cargar datos:</b> Escribe números separados por coma en el campo de entrada "
            + "y presiona <i>Cargar</i>, o usa <i>Aleatorio</i> para generar datos automáticamente.</li><br>"
            + "<li><b>Seleccionar algoritmo:</b> Elige entre <b>Bubble Sort</b> (iterativo), "
            + "<b>Shell Sort</b> (iterativo con gaps) o <b>Quick Sort</b> (recursivo).</li><br>"
            + "<li><b>Configurar el orden:</b> Ascendente (menor a mayor) o Descendente.</li><br>"
            + "<li><b>Configurar la velocidad:</b> Lento (500ms), Medio (100ms) o Rápido (20ms) por paso.</li><br>"
            + "<li><b>Iniciar:</b> Presiona el botón <i>▶ Iniciar</i> para comenzar la visualización.</li><br>"
            + "<li><b>Detener:</b> Puedes interrumpir el ordenamiento con <i>⏹ Detener</i> en cualquier momento.</li><br>"
            + "<li><b>Reportes:</b> Al finalizar cada ejecución se genera automáticamente un reporte HTML. "
            + "Puedes abrirlo desde la pestaña <i>Reportes</i>.</li>"
            + "</ol>"
            + "<h2 style='color:#4285f4;'>Colores de las barras</h2>"
            + "<ul>"
            + "<li><b style='color:#4285f4;'>Azul:</b> Estado normal (reposo)</li>"
            + "<li><b style='color:#fbbc04;'>Amarillo:</b> Comparando dos elementos</li>"
            + "<li><b style='color:#ea4335;'>Rojo:</b> Intercambiando elementos</li>"
            + "<li><b style='color:#34a853;'>Verde:</b> Elemento en posición final ordenada</li>"
            + "</ul>"
            + "<h2 style='color:#4285f4;'>Sobre los algoritmos</h2>"
            + "<p><b>Bubble Sort:</b> O(n²) en el peor caso. Compara pares adyacentes y realiza "
            + "intercambios hasta que el arreglo está ordenado.</p>"
            + "<p><b>Shell Sort:</b> Mejora sobre Insertion Sort. Usa gaps decrecientes para "
            + "ordenar subconjuntos del arreglo antes del ordenamiento final.</p>"
            + "<p><b>Quick Sort:</b> O(n log n) en el caso promedio. Divide el arreglo "
            + "recursivamente alrededor de un pivote. Implementación recursiva real.</p>"
            + "<br><p style='color:#666;'>Versión: " + APP_VERSION + " | ECYS - FIUSAC</p>"
            + "</body></html>";

        JEditorPane editor = new JEditorPane("text/html", helpText);
        editor.setEditable(false);
        editor.setBackground(new Color(25, 25, 40));
        editor.setBorder(null);

        JScrollPane scroll = new JScrollPane(editor);
        scroll.getViewport().setBackground(new Color(25, 25, 40));
        scroll.setBorder(null);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    /** Barra de estado en la parte inferior. */
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bar.setBackground(new Color(15, 15, 25));
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(50, 50, 80)));

        JLabel status = new JLabel("✓ Sistema listo  |  " + APP_VERSION + "  |  Java " + System.getProperty("java.version"));
        status.setFont(new Font("SansSerif", Font.PLAIN, 11));
        status.setForeground(new Color(100, 100, 140));
        bar.add(status);

        return bar;
    }
}

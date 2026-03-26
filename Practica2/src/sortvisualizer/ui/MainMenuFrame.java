package sortvisualizer.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
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

    // Colores unificados para tema oscuro
    private static final Color BG_DARK      = new Color(28, 28, 35);
    private static final Color BG_PANEL     = new Color(35, 35, 45);
    private static final Color BG_HEADER    = new Color(20, 20, 28);
    private static final Color TEXT_LIGHT   = new Color(224, 224, 224);
    private static final Color TEXT_MUTED   = new Color(160, 160, 180);
    private static final Color ACCENT_BLUE  = new Color(76, 154, 255);
    private static final Color ACCENT_GREEN = new Color(76, 175, 80);
    private static final Color BORDER_COLOR = new Color(60, 60, 80);

    public MainMenuFrame() {
        super(APP_NAME);
        reportsFrame = new ReportsFrame();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout());

        // ---- Panel de encabezado ----
        add(buildHeader(), BorderLayout.NORTH);

        // ---- Panel principal con pestañas ----
        JTabbedPane tabs = buildTabbedPane();
        add(tabs, BorderLayout.CENTER);

        // ---- Barra de estado inferior ----
        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    /** Encabezado con nombre y subtítulo de la app. */
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_HEADER);
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel(APP_NAME, SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(ACCENT_BLUE);

        JLabel subtitle = new JLabel("Bubble Sort, Shell Sort y Quick Sort", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 13));
        subtitle.setForeground(TEXT_MUTED);

        header.add(title, BorderLayout.CENTER);
        header.add(subtitle, BorderLayout.SOUTH);

        return header;
    }

    /** Crea el panel de pestañas principal con estilo personalizado. */
    private JTabbedPane buildTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_DARK);
        tabs.setForeground(TEXT_LIGHT);
        tabs.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabs.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                // Cambiar colores por defecto para un look más limpio
                tabAreaInsets = new Insets(5, 5, 0, 5);
                contentBorderInsets = new Insets(2, 0, 0, 0);
                selectedTabPadInsets = new Insets(2, 2, 2, 2);
            }

            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
                // No pintar borde por defecto
            }

            @Override
            protected void paintFocusIndicator(Graphics g, int tabPlacement,
                                               Rectangle[] rects, int tabIndex,
                                               Rectangle iconRect, Rectangle textRect,
                                               boolean isSelected) {
                // No pintar indicador de foco
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                              int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isSelected) {
                    g2d.setColor(BG_PANEL);
                    g2d.fillRoundRect(x, y, w, h, 8, 8);
                } else {
                    g2d.setColor(BG_HEADER);
                    g2d.fillRoundRect(x, y, w, h, 8, 8);
                }
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font,
                                     FontMetrics metrics, int tabIndex, String title,
                                     Rectangle textRect, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setFont(font);
                if (isSelected) {
                    g2d.setColor(ACCENT_BLUE);
                } else {
                    g2d.setColor(TEXT_MUTED);
                }
                g2d.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            }
        });

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
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel info = new JLabel(
                "<html><div style='text-align:center; color:" + toHex(TEXT_MUTED) + ";'>"
                + "El historial de ejecuciones se acumula mientras la aplicación esté abierta.<br>"
                + "Al cerrar la aplicación, el historial se pierde (no persiste entre sesiones).</div></html>",
                SwingConstants.CENTER);
        info.setFont(new Font("SansSerif", Font.ITALIC, 13));
        panel.add(info, BorderLayout.NORTH);

        JButton btnOpenReports = new JButton("📊 Abrir Ventana de Reportes");
        btnOpenReports.setBackground(ACCENT_GREEN);
        btnOpenReports.setForeground(Color.BLACK);
        btnOpenReports.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnOpenReports.setFocusPainted(false);
        btnOpenReports.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnOpenReports.setPreferredSize(new Dimension(280, 55));
        btnOpenReports.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_GREEN.darker(), 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        btnOpenReports.addActionListener((ActionEvent e) -> {
            reportsFrame.loadHistory();
            reportsFrame.setVisible(true);
        });
        // Efecto hover sutil
        btnOpenReports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOpenReports.setBackground(ACCENT_GREEN.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOpenReports.setBackground(ACCENT_GREEN);
            }
        });

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BG_PANEL);
        centerPanel.add(btnOpenReports);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    /** Panel de la pestaña de Ayuda. */
    private JPanel buildHelpTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        String helpText = 
            "<html><body style='font-family:SansSerif; background-color:" + toHex(BG_PANEL) + "; color:" + toHex(TEXT_LIGHT) + ";'>"
            + "<h2 style='color:" + toHex(ACCENT_BLUE) + ";'>Cómo usar el Visualizador</h2>"
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
            + "<h2 style='color:" + toHex(ACCENT_BLUE) + ";'>Colores de las barras</h2>"
            + "<ul>"
            + "<li><b style='color:" + toHex(new Color(76, 154, 255)) + ";'>Azul:</b> Estado normal (reposo)</li>"
            + "<li><b style='color:" + toHex(new Color(255, 193, 7)) + ";'>Amarillo:</b> Comparando dos elementos</li>"
            + "<li><b style='color:" + toHex(new Color(244, 67, 54)) + ";'>Rojo:</b> Intercambiando elementos</li>"
            + "<li><b style='color:" + toHex(new Color(76, 175, 80)) + ";'>Verde:</b> Elemento en posición final ordenada</li>"
            + "</ul>"
            + "<h2 style='color:" + toHex(ACCENT_BLUE) + ";'>Sobre los algoritmos</h2>"
            + "<p><b>Bubble Sort:</b> O(n²) en el peor caso. Compara pares adyacentes y realiza "
            + "intercambios hasta que el arreglo está ordenado.</p>"
            + "<p><b>Shell Sort:</b> Mejora sobre Insertion Sort. Usa gaps decrecientes para "
            + "ordenar subconjuntos del arreglo antes del ordenamiento final.</p>"
            + "<p><b>Quick Sort:</b> O(n log n) en el caso promedio. Divide el arreglo "
            + "recursivamente alrededor de un pivote. Implementación recursiva real.</p>"
            + "<br><p style='color:" + toHex(TEXT_MUTED) + ";'>Versión: " + APP_VERSION + " | ECYS - FIUSAC</p>"
            + "</body></html>";

        JEditorPane editor = new JEditorPane("text/html", helpText);
        editor.setEditable(false);
        editor.setBackground(BG_PANEL);
        editor.setBorder(null);

        JScrollPane scroll = new JScrollPane(editor);
        scroll.getViewport().setBackground(BG_PANEL);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    /** Barra de estado en la parte inferior. */
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        bar.setBackground(BG_HEADER);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        JLabel status = new JLabel("✓ Sistema listo  |  " + APP_VERSION + "  |  Java " + System.getProperty("java.version"));
        status.setFont(new Font("SansSerif", Font.PLAIN, 11));
        status.setForeground(TEXT_MUTED);
        bar.add(status);

        return bar;
    }

    /** Convierte un Color a su representación hexadecimal. */
    private String toHex(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
}
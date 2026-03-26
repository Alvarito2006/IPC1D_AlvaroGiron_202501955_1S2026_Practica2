package sortvisualizer.ui;

import sortvisualizer.model.ExecutionStats;
import sortvisualizer.model.SessionHistory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.Desktop;
import java.io.File;

/**
 * Ventana de reportes que muestra el historial de ejecuciones de la sesión.
 * Permite abrir el último reporte HTML generado en el navegador.
 */
public class ReportsFrame extends JFrame {

    // Colores unificados con el resto de la UI
    private static final Color BG_DARK      = new Color(28, 28, 35);
    private static final Color BG_PANEL     = new Color(35, 35, 45);
    private static final Color BG_HEADER    = new Color(20, 20, 28);
    private static final Color TEXT_LIGHT   = new Color(0, 0, 0);
    private static final Color TEXT_MUTED   = new Color(0, 0, 0);
    private static final Color ACCENT_BLUE  = new Color(76, 154, 255);
    private static final Color ACCENT_GREEN = new Color(76, 175, 80);
    private static final Color ACCENT_RED   = new Color(239, 83, 80);
    private static final Color BORDER_COLOR = new Color(60, 60, 80);

    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JButton btnOpenHtml;
    private String lastReportPath;

    public ReportsFrame() {
        super("Reportes - Visualizador de Ordenamiento");
        initComponents();
        loadHistory();
    }

    private void initComponents() {
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel title = new JLabel("📋 Historial de Ejecuciones de la Sesión", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(ACCENT_BLUE);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Tabla del historial
        String[] columns = {"#", "Algoritmo", "Orden", "n", "Comparaciones", "Intercambios", "Iteraciones", "Tiempo (ms)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        historyTable = new JTable(tableModel);
        historyTable.setBackground(BG_HEADER);
        historyTable.setForeground(TEXT_LIGHT);
        historyTable.setGridColor(BORDER_COLOR);
        historyTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        historyTable.setRowHeight(28);
        historyTable.setSelectionBackground(ACCENT_BLUE.darker());
        historyTable.setSelectionForeground(Color.WHITE);
        historyTable.setShowVerticalLines(false);
        historyTable.setIntercellSpacing(new Dimension(0, 1));

        // Estilo del encabezado
        JTableHeader header = historyTable.getTableHeader();
        header.setBackground(BG_PANEL);
        header.setForeground(TEXT_LIGHT);
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getViewport().setBackground(BG_HEADER);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getViewport().setOpaque(true);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(BG_DARK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        btnOpenHtml = createStyledButton("🌐 Abrir último reporte HTML", ACCENT_GREEN);
        btnOpenHtml.setEnabled(false);
        btnOpenHtml.addActionListener(e -> openLastReport());

        JButton btnClose = createStyledButton("✖ Cerrar", ACCENT_RED);
        btnClose.addActionListener(e -> dispose());

        buttonPanel.add(btnOpenHtml);
        buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /** Crea un botón con estilo redondeado y efecto hover. */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    /** Carga el historial de la sesión en la tabla. */
    public void loadHistory() {
        tableModel.setRowCount(0);
        ExecutionStats[] history = SessionHistory.getInstance().getHistory();
        for (ExecutionStats e : history) {
            tableModel.addRow(new Object[]{
                e.getExecutionNumber(),
                e.getAlgorithm(),
                e.getOrder(),
                e.getArraySize(),
                e.getComparisons(),
                e.getSwaps(),
                e.getIterations(),
                e.getElapsedTime() + " ms"
            });
        }
    }

    /** Establece la ruta del último reporte HTML generado. */
    public void setLastReportPath(String path) {
        this.lastReportPath = path;
        btnOpenHtml.setEnabled(path != null);
    }

    /** Abre el último reporte HTML en el navegador predeterminado. */
    private void openLastReport() {
        if (lastReportPath == null) return;
        try {
            File file = new File(lastReportPath);
            if (file.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(file.toURI());
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo abrir el reporte. Ruta: " + lastReportPath,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir el navegador: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
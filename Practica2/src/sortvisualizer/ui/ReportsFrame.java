package sortvisualizer.ui;

import sortvisualizer.model.ExecutionStats;
import sortvisualizer.model.SessionHistory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Desktop;
import java.io.File;

/**
 * Ventana de reportes que muestra el historial de ejecuciones de la sesión.
 * Permite abrir el último reporte HTML generado en el navegador.
 */
public class ReportsFrame extends JFrame {

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
        getContentPane().setBackground(new Color(30, 30, 45));
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel title = new JLabel("📋 Historial de Ejecuciones de la Sesión", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(66, 133, 244));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Tabla del historial
        String[] columns = {"#", "Algoritmo", "Orden", "n", "Comparaciones", "Intercambios", "Iteraciones", "Tiempo (ms)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        historyTable = new JTable(tableModel);
        historyTable.setBackground(new Color(20, 20, 35));
        historyTable.setForeground(new Color(220, 220, 220));
        historyTable.setGridColor(new Color(60, 60, 90));
        historyTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        historyTable.getTableHeader().setBackground(new Color(66, 133, 244));
        historyTable.getTableHeader().setForeground(Color.WHITE);
        historyTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        historyTable.setRowHeight(28);
        historyTable.setSelectionBackground(new Color(40, 80, 140));

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getViewport().setBackground(new Color(20, 20, 35));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(30, 30, 45));

        btnOpenHtml = new JButton("🌐 Abrir último reporte HTML");
        styleButton(btnOpenHtml, new Color(52, 168, 83));
        btnOpenHtml.setEnabled(false);
        btnOpenHtml.addActionListener(e -> openLastReport());

        JButton btnClose = new JButton("✖ Cerrar");
        styleButton(btnClose, new Color(234, 67, 53));
        btnClose.addActionListener(e -> dispose());

        buttonPanel.add(btnOpenHtml);
        buttonPanel.add(btnClose);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

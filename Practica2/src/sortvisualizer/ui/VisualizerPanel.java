package sortvisualizer.ui;

import sortvisualizer.algorithms.*;
import sortvisualizer.model.ExecutionStats;
import sortvisualizer.model.SessionHistory;
import sortvisualizer.reports.HtmlReportGenerator;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

/**
 * Panel principal del visualizador.
 * Contiene: panel de control, estadísticas, visualización de barras y log de operaciones.
 */
public class VisualizerPanel extends JPanel {

    // ---- Referencias a componentes ----
    private JTextArea txtInput;
    private JComboBox<String> cmbAlgorithm;
    private JComboBox<String> cmbOrder;
    private JComboBox<String> cmbSpeed;
    private JSpinner spinnerSize;
    private JButton btnLoad;
    private JButton btnRandom;
    private JButton btnStart;
    private JButton btnStop;
    private JTextArea logArea;
    private BarChartPanel chartPanel;
    private StatsPanel statsPanel;

    // ---- Estado del programa ----
    private int[] currentArray;             // Arreglo actual cargado
    private int[] elementStates;            // Estado de cada barra (normal/comparando/etc.)
    private Thread sortThread;              // Hilo del algoritmo en ejecución
    private ExecutionStats currentStats;    // Estadísticas de la ejecución actual
    private boolean isSorting = false;      // Indica si hay un ordenamiento en curso
    private int stepCount = 0;              // Contador de pasos para el log

    // ---- Referencia a la ventana de reportes ----
    private ReportsFrame reportsFrame;

    // ---- Velocidades disponibles (ms por paso) ----
    private static final int SPEED_SLOW   = 500;
    private static final int SPEED_MEDIUM = 100;
    private static final int SPEED_FAST   = 20;

    public VisualizerPanel(ReportsFrame reportsFrame) {
        this.reportsFrame = reportsFrame;
        currentStats = new ExecutionStats();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(25, 25, 38));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel izquierdo: control + estadísticas + leyenda
        JPanel leftPanel = buildLeftPanel();
        leftPanel.setPreferredSize(new Dimension(260, 0));

        // Panel derecho: gráfica + log
        JPanel rightPanel = buildRightPanel();

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    // ============================
    //  PANEL IZQUIERDO (Control)
    // ============================

    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(25, 25, 38));

        // Panel de control
        panel.add(buildControlPanel());
        panel.add(Box.createVerticalStrut(10));

        // Panel de estadísticas
        statsPanel = new StatsPanel();
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        panel.add(statsPanel);
        panel.add(Box.createVerticalStrut(10));

        // Leyenda de colores
        panel.add(buildLegendPanel());

        return panel;
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 45));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                "Panel de Control",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                new Color(180, 180, 220)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // ---- Ingreso de datos ----
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(createLabel("Datos de entrada (separados por coma):"), gbc);
        row++;

        txtInput = new JTextArea(2, 20);
        txtInput.setBackground(new Color(40, 40, 60));
        txtInput.setForeground(new Color(220, 220, 220));
        txtInput.setCaretColor(Color.WHITE);
        txtInput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtInput.setLineWrap(true);
        txtInput.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120)));
        JScrollPane inputScroll = new JScrollPane(txtInput);
        inputScroll.setPreferredSize(new Dimension(230, 45));

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(inputScroll, gbc);
        row++;

        // ---- Botones cargar y aleatorio ----
        btnLoad = createButton("Cargar", new Color(66, 133, 244));
        btnLoad.addActionListener(e -> loadDataFromText());

        btnRandom = createButton("⚡ Aleatorio", new Color(103, 58, 183));
        btnRandom.addActionListener(e -> generateRandom());

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(btnLoad, gbc);
        gbc.gridx = 1;
        panel.add(btnRandom, gbc);
        row++;

        // ---- Botón cargar desde archivo ----
        JButton btnFile = createButton("📂 Cargar desde .txt", new Color(80, 100, 160));
        btnFile.addActionListener(e -> loadFromFile());
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(btnFile, gbc);
        row++;

        // ---- Tamaño del arreglo aleatorio ----
        panel.add(createLabel("Tamaño arreglo aleatorio (5-30):"), gbc);
        row++;

        spinnerSize = new JSpinner(new SpinnerNumberModel(10, 5, 30, 1));
        spinnerSize.setBackground(new Color(40, 40, 60));
        ((JSpinner.DefaultEditor) spinnerSize.getEditor()).getTextField()
                .setBackground(new Color(40, 40, 60));
        ((JSpinner.DefaultEditor) spinnerSize.getEditor()).getTextField()
                .setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(spinnerSize, gbc);
        row++;

        // ---- Algoritmo ----
        panel.add(createLabel("Algoritmo:"), gbc);
        row++;

        cmbAlgorithm = new JComboBox<>(new String[]{"Bubble Sort", "Shell Sort", "Quick Sort"});
        styleComboBox(cmbAlgorithm);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(cmbAlgorithm, gbc);
        row++;

        // ---- Orden ----
        panel.add(createLabel("Orden:"), gbc);
        row++;

        cmbOrder = new JComboBox<>(new String[]{"Ascendente", "Descendente"});
        styleComboBox(cmbOrder);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(cmbOrder, gbc);
        row++;

        // ---- Velocidad ----
        panel.add(createLabel("Velocidad:"), gbc);
        row++;

        cmbSpeed = new JComboBox<>(new String[]{"Lento (500ms)", "Medio (100ms)", "Rápido (20ms)"});
        cmbSpeed.setSelectedIndex(1);
        styleComboBox(cmbSpeed);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(cmbSpeed, gbc);
        row++;

        // ---- Botones Iniciar / Detener ----
        btnStart = createButton("▶ Iniciar", new Color(52, 168, 83));
        btnStart.addActionListener(e -> startSorting());

        btnStop = createButton("⏹ Detener", new Color(234, 67, 53));
        btnStop.setEnabled(false);
        btnStop.addActionListener(e -> stopSorting());

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(btnStart, gbc);
        gbc.gridx = 1;
        panel.add(btnStop, gbc);

        return panel;
    }

    private JPanel buildLegendPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBackground(new Color(30, 30, 45));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                "Leyenda",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 11),
                new Color(180, 180, 220)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        panel.add(createLegendItem("Normal", new Color(66, 133, 244)));
        panel.add(createLegendItem("Comparando", new Color(251, 188, 4)));
        panel.add(createLegendItem("Intercambiando", new Color(234, 67, 53)));
        panel.add(createLegendItem("Ordenado", new Color(52, 168, 83)));

        return panel;
    }

    private JLabel createLegendItem(String text, Color color) {
        JLabel lbl = new JLabel("■ " + text);
        lbl.setForeground(color);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        return lbl;
    }

    // ============================
    //  PANEL DERECHO (Visualización + Log)
    // ============================

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(25, 25, 38));

        // Gráfica de barras
        chartPanel = new BarChartPanel();
        panel.add(chartPanel, BorderLayout.CENTER);

        // Log de operaciones
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBackground(new Color(25, 25, 38));
        logPanel.setPreferredSize(new Dimension(0, 160));
        logPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)),
                "Log de Operaciones",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 12),
                new Color(180, 180, 220)));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(15, 15, 25));
        logArea.setForeground(new Color(180, 220, 180));
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        logArea.setCaretColor(Color.WHITE);

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.getViewport().setBackground(new Color(15, 15, 25));
        logPanel.add(logScroll, BorderLayout.CENTER);

        panel.add(logPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ============================
    //  ACCIONES DEL PANEL DE CONTROL
    // ============================

    /** Carga los datos ingresados en el campo de texto. */
    private void loadDataFromText() {
        String text = txtInput.getText().trim();
        if (text.isEmpty()) {
            showError("Por favor ingresa algunos números separados por coma.");
            return;
        }
        try {
            currentArray = parseNumbers(text);
            elementStates = new int[currentArray.length];
            updateChart(currentArray, elementStates);
            logMessage("Arreglo cargado: " + ExecutionStats.arrayToString(currentArray));
            statsPanel.reset();
        } catch (NumberFormatException e) {
            showError("Error: solo se permiten números enteros separados por coma.");
        }
    }

    /** Genera un arreglo aleatorio de tamaño configurable. */
    private void generateRandom() {
        int n = (int) spinnerSize.getValue();
        currentArray = new int[n];
        elementStates = new int[n];
        java.util.Random rand = new java.util.Random();

        for (int i = 0; i < n; i++) {
            currentArray[i] = rand.nextInt(100) + 1; // Valores entre 1 y 100
        }

        txtInput.setText(arrayToCSV(currentArray));
        updateChart(currentArray, elementStates);
        logMessage("Arreglo aleatorio generado (" + n + " elementos): "
                + ExecutionStats.arrayToString(currentArray));
        statsPanel.reset();
    }

    /** Carga datos desde un archivo .txt usando JFileChooser. */
    private void loadFromFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));
        chooser.setDialogTitle("Seleccionar archivo de datos");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    // Reemplazar saltos de línea por comas para unificar el formato
                    sb.append(line.trim()).append(",");
                }
                String content = sb.toString().replaceAll(",+", ",").replaceAll(",$", "");
                txtInput.setText(content);
                loadDataFromText();
                logMessage("Archivo cargado: " + file.getName());
            } catch (IOException ex) {
                showError("Error al leer el archivo: " + ex.getMessage());
            }
        }
    }

    /** Inicia el ordenamiento en un hilo separado. */
    private void startSorting() {
        if (currentArray == null || currentArray.length == 0) {
            showError("Primero carga un arreglo de datos.");
            return;
        }
        if (isSorting) return;

        // Preparar una copia del arreglo original para ordenar
        int[] arrToSort = new int[currentArray.length];
        System.arraycopy(currentArray, 0, arrToSort, 0, currentArray.length);

        // Configurar estadísticas
        currentStats = new ExecutionStats();
        currentStats.setAlgorithm((String) cmbAlgorithm.getSelectedItem());
        currentStats.setOrder((String) cmbOrder.getSelectedItem());
        currentStats.setSpeed((String) cmbSpeed.getSelectedItem());
        currentStats.setOriginalArray(currentArray);
        currentStats.setArraySize(currentArray.length);

        boolean ascending = cmbOrder.getSelectedIndex() == 0;
        String algorithm  = (String) cmbAlgorithm.getSelectedItem();

        // Reiniciar UI
        elementStates = new int[arrToSort.length];
        stepCount = 0;
        statsPanel.reset();
        logArea.setText("");
        logMessage("Iniciando " + algorithm + " (" + cmbOrder.getSelectedItem() + ")...");

        // Bloquear controles durante el ordenamiento
        setControlsEnabled(false);
        isSorting = true;

        // Crear el callback que conecta el algoritmo con la UI
        SortCallback callback = buildCallback(arrToSort);

        currentStats.startTimer();

        // Ejecutar el algoritmo en un hilo separado (nunca en el EDT)
        sortThread = new Thread(() -> {
            try {
                switch (algorithm) {
                    case "Bubble Sort":
                        BubbleSort.sort(arrToSort, ascending, currentStats, callback);
                        break;
                    case "Shell Sort":
                        ShellSort.sort(arrToSort, ascending, currentStats, callback);
                        break;
                    case "Quick Sort":
                        QuickSort.sort(arrToSort, ascending, currentStats, callback);
                        break;
                }

                currentStats.stopTimer();

                // Actualizar UI al finalizar (desde el EDT)
                SwingUtilities.invokeLater(() -> {
                    onSortingFinished(arrToSort);
                });

            } catch (Exception e) {
                currentStats.stopTimer();
                SwingUtilities.invokeLater(() -> {
                    logMessage("⚠ Ordenamiento interrumpido.");
                    setControlsEnabled(true);
                    isSorting = false;
                });
            }
        });

        sortThread.setDaemon(true);
        sortThread.start();
    }

    /** Detiene el hilo de ordenamiento. */
    private void stopSorting() {
        if (sortThread != null && sortThread.isAlive()) {
            sortThread.interrupt();
        }
        isSorting = false;
        setControlsEnabled(true);
        logMessage("⏹ Ordenamiento detenido por el usuario.");
    }

    /** Acciones al finalizar el ordenamiento correctamente. */
    private void onSortingFinished(int[] sortedArr) {
        currentStats.setSortedArray(sortedArr);

        // Actualizar arreglo actual con el resultado
        currentArray = new int[sortedArr.length];
        System.arraycopy(sortedArr, 0, currentArray, 0, sortedArr.length);

        long elapsed = currentStats.getElapsedTime();
        logMessage("✅ Ordenamiento completado en " + elapsed + " ms");
        logMessage("Comparaciones: " + currentStats.getComparisons()
                + " | Intercambios: " + currentStats.getSwaps()
                + " | Iteraciones: " + currentStats.getIterations());

        // Guardar en historial de sesión
        SessionHistory.getInstance().addExecution(currentStats);

        // Generar reporte HTML
        generateHtmlReport();

        setControlsEnabled(true);
        isSorting = false;
    }

    /**
     * Construye el callback que conecta el algoritmo con la interfaz gráfica.
     * Todas las actualizaciones de UI se hacen mediante SwingUtilities.invokeLater().
     */
    private SortCallback buildCallback(int[] arr) {
        return new SortCallback() {
            @Override
            public void onCompare(int i, int j, int[] currentArr) {
                stepCount++;
                int s = stepCount;
                SwingUtilities.invokeLater(() -> {
                    elementStates[i] = BarChartPanel.STATE_COMPARING;
                    elementStates[j] = BarChartPanel.STATE_COMPARING;
                    chartPanel.updateData(currentArr, elementStates);
                    statsPanel.update(currentStats.getComparisons(),
                            currentStats.getSwaps(), currentStats.getIterations());
                    // Log detallado (solo cada 10 pasos para no saturar)
                    if (s % 10 == 0) {
                        logMessage("[Paso " + s + "] Comparando arr[" + i + "]="
                                + currentArr[i] + " con arr[" + j + "]=" + currentArr[j]);
                    }
                });
            }

            @Override
            public void onSwap(int i, int j, int[] currentArr) {
                int s = stepCount;
                SwingUtilities.invokeLater(() -> {
                    elementStates[i] = BarChartPanel.STATE_SWAPPING;
                    elementStates[j] = BarChartPanel.STATE_SWAPPING;
                    chartPanel.updateData(currentArr, elementStates);
                    logMessage("[Paso " + s + "] Intercambiando arr[" + i + "]="
                            + currentArr[i] + " ↔ arr[" + j + "]=" + currentArr[j]);
                    // Restaurar a normal después del intercambio
                    if (elementStates[i] != BarChartPanel.STATE_SORTED)
                        elementStates[i] = BarChartPanel.STATE_NORMAL;
                    if (elementStates[j] != BarChartPanel.STATE_SORTED)
                        elementStates[j] = BarChartPanel.STATE_NORMAL;
                });
            }

            @Override
            public void onIteration(int iterNum, int[] currentArr) {
                SwingUtilities.invokeLater(() -> {
                    statsPanel.update(currentStats.getComparisons(),
                            currentStats.getSwaps(), currentStats.getIterations());
                    logMessage("--- Iteración " + iterNum + " ---");
                    chartPanel.updateData(currentArr, elementStates);
                });
            }

            @Override
            public void onElementSorted(int index, int[] currentArr) {
                SwingUtilities.invokeLater(() -> {
                    if (index >= 0 && index < elementStates.length) {
                        elementStates[index] = BarChartPanel.STATE_SORTED;
                        chartPanel.updateData(currentArr, elementStates);
                    }
                });
            }

            @Override
            public void onPartition(int low, int high, int[] currentArr) {
                SwingUtilities.invokeLater(() -> {
                    logMessage("[QuickSort] Particionando [" + low + " .. " + high
                            + "] | Pivote: arr[" + high + "]=" + currentArr[high]);
                });
            }

            @Override
            public int getDelay() {
                switch (cmbSpeed.getSelectedIndex()) {
                    case 0: return SPEED_SLOW;
                    case 2: return SPEED_FAST;
                    default: return SPEED_MEDIUM;
                }
            }
        };
    }

    /** Genera el reporte HTML y lo guarda en el directorio de trabajo. */
    private void generateHtmlReport() {
        try {
            String fileName = "reporte_" + System.currentTimeMillis() + ".html";
            String path = System.getProperty("user.home") + File.separator + fileName;
            HtmlReportGenerator.generate(currentStats, path);
            logMessage("📄 Reporte HTML generado: " + path);

            if (reportsFrame != null) {
                reportsFrame.setLastReportPath(path);
                reportsFrame.loadHistory();
            }
        } catch (IOException ex) {
            logMessage("⚠ No se pudo generar el reporte: " + ex.getMessage());
        }
    }

    // ============================
    //  UTILIDADES
    // ============================

    /** Agrega un mensaje al log de operaciones con scroll automático. */
    private void logMessage(String msg) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(msg + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    /** Actualiza la gráfica de barras desde el EDT. */
    private void updateChart(int[] arr, int[] states) {
        SwingUtilities.invokeLater(() -> chartPanel.updateData(arr, states));
    }

    /** Habilita o deshabilita los controles durante el ordenamiento. */
    private void setControlsEnabled(boolean enabled) {
        btnLoad.setEnabled(enabled);
        btnRandom.setEnabled(enabled);
        btnStart.setEnabled(enabled);
        btnStop.setEnabled(!enabled);
        cmbAlgorithm.setEnabled(enabled);
        cmbOrder.setEnabled(enabled);
        txtInput.setEnabled(enabled);
        spinnerSize.setEnabled(enabled);
    }

    /** Parsea una cadena de texto con números separados por coma. */
    private int[] parseNumbers(String text) {
        String[] parts = text.split("[,\\s]+");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
        }
        return result;
    }

    /** Convierte un arreglo a String CSV. */
    private String arrayToCSV(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        return sb.toString();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(new Color(160, 160, 200));
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        return lbl;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        return btn;
    }

    private void styleComboBox(JComboBox<String> cmb) {
        cmb.setBackground(new Color(40, 40, 65));
        cmb.setForeground(Color.WHITE);
        cmb.setFont(new Font("SansSerif", Font.PLAIN, 12));
        cmb.setFocusable(false);
    }
}

package sortvisualizer;

import sortvisualizer.ui.MainMenuFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada principal de la aplicación.
 * Visualizador de Algoritmos de Ordenamiento - IPC1
 */
public class Main {

    public static void main(String[] args) {
        // Ejecutar la interfaz en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                // Aplicar look and feel del sistema operativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Si falla, se usa el look and feel por defecto
            }
            // Mostrar el menú principal
            new MainMenuFrame().setVisible(true);
        });
    }
}

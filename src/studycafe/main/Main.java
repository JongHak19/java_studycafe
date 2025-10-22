package studycafe.main;

import studycafe.ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        // Swing UI in EDT (Event Dispatch Thread)
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Application startup error: " + e.getMessage());
            }
        });
    }
}

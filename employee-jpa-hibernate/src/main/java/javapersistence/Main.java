package javapersistence;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Jalankan Form GUI di thread yang benar (Swing)
        SwingUtilities.invokeLater(() -> {
            FormKaryawan form = new FormKaryawan();
            form.setVisible(true);
        });
    }
}
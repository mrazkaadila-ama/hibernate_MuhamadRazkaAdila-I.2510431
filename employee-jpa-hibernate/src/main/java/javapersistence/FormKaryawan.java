package javapersistence;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class FormKaryawan extends JFrame {

    // Komponen GUI
    private JTextField txtNip, txtNama, txtTempLhr, txtTgl, txtBln, txtThn, txtJabatan;
    private JButton btnInsert, btnUpdate, btnDelete, btnRefresh, btnClear, btnClose;
    private JTable TblKar;
    private DefaultTableModel defTab;
    private JLabel lblStatus;

    private ControllerKaryawan ck = new ControllerKaryawan();
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color DANGER_COLOR = new Color(192, 57, 43);
    private static final Color LABEL_COLOR = new Color(44, 62, 80);

    public FormKaryawan() {
        setTitle("📋 Manajemen Data Karyawan");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setFont(new Font("Segoe UI", Font.PLAIN, 11));
        initComponents();
        loadAllData();
    }

    private void initComponents() {
        // Main Panel dengan dua bagian: Form dan Tabel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // ==================== FORM PANEL ====================
        JPanel formPanel = createFormPanel();
        
        // ==================== TABEL PANEL ====================
        JPanel tablePanel = createTablePanel();

        // Split pane untuk separasi form dan tabel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel);
        splitPane.setDividerLocation(260);
        splitPane.setResizeWeight(0.35);
        
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        // ==================== STATUS BAR ====================
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        statusPanel.setBackground(new Color(236, 240, 241));
        lblStatus = new JLabel("Status: Siap");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblStatus.setBorder(new EmptyBorder(5, 10, 5, 10));
        statusPanel.add(lblStatus, BorderLayout.WEST);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(new LineBorder(PRIMARY_COLOR, 2), 
                "📝 FORM INPUT DATA KARYAWAN", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // NIP
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2;
        panel.add(createStyledLabel("NIP *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        txtNip = createStyledTextField(10);
        panel.add(txtNip, gbc);

        // Nama
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(createStyledLabel("Nama Lengkap *"), gbc);
        gbc.gridx = 1;
        txtNama = createStyledTextField(20);
        panel.add(txtNama, gbc);

        // Tempat Lahir
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(createStyledLabel("Tempat Lahir *"), gbc);
        gbc.gridx = 1;
        txtTempLhr = createStyledTextField(20);
        panel.add(txtTempLhr, gbc);

        // Tanggal Lahir
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(createStyledLabel("Tanggal Lahir *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.5;
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setBackground(Color.WHITE);
        txtTgl = createStyledTextField(3);
        txtBln = createStyledTextField(3);
        txtThn = createStyledTextField(5);
        datePanel.add(new JLabel("Tgl:"));
        datePanel.add(txtTgl);
        datePanel.add(new JLabel("Bln:"));
        datePanel.add(txtBln);
        datePanel.add(new JLabel("Thn:"));
        datePanel.add(txtThn);
        panel.add(datePanel, gbc);

        // Jabatan
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(createStyledLabel("Jabatan *"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        txtJabatan = createStyledTextField(20);
        panel.add(txtJabatan, gbc);

        // Button Panel
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weightx = 1.0;
        JPanel btnPanel = createButtonPanel();
        panel.add(btnPanel, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(new LineBorder(PRIMARY_COLOR, 2), 
                "📊 DAFTAR DATA KARYAWAN", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), PRIMARY_COLOR));

        TblKar = new JTable();
        TblKar.setRowHeight(25);
        TblKar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        TblKar.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        TblKar.getTableHeader().setBackground(SECONDARY_COLOR);
        TblKar.getTableHeader().setForeground(Color.WHITE);
        TblKar.setSelectionBackground(SECONDARY_COLOR);
        TblKar.setSelectionForeground(Color.WHITE);
        TblKar.setGridColor(new Color(189, 195, 199));

        JScrollPane scroll = new JScrollPane(TblKar);
        scroll.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        panel.add(scroll, BorderLayout.CENTER);

        // Double click table untuk load data
        TblKar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadFromTable();
                    updateStatus("Data dipilih dari tabel");
                }
            }
        });

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.WHITE);

        btnInsert = createStyledButton("➕ INSERT", SUCCESS_COLOR);
        btnUpdate = createStyledButton("✏️ UPDATE", SECONDARY_COLOR);
        btnDelete = createStyledButton("🗑️ DELETE", DANGER_COLOR);
        btnRefresh = createStyledButton("🔄 REFRESH", PRIMARY_COLOR);
        btnClear = createStyledButton("❌ CLEAR", new Color(149, 165, 166));
        btnClose = createStyledButton("❌ CLOSE", new Color(108, 117, 125));

        panel.add(btnInsert);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnRefresh);
        panel.add(btnClear);
        panel.add(btnClose);

        // Event Listener
        btnInsert.addActionListener(e -> insertAction());
        btnUpdate.addActionListener(e -> updateAction());
        btnDelete.addActionListener(e -> deleteAction());
        btnRefresh.addActionListener(e -> {
            clearForm();
            loadAllData();
            updateStatus("Data di-refresh");
        });
        btnClear.addActionListener(e -> {
            clearForm();
            updateStatus("Form dibersihkan");
        });
        btnClose.addActionListener(e -> dispose());

        // Search by NIP dengan Enter key
        txtNip.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchByNip();
                }
            }
        });

        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(LABEL_COLOR);
        return label;
    }

    private JTextField createStyledTextField(int cols) {
        JTextField txt = new JTextField(cols);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txt.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        txt.setMargin(new Insets(5, 5, 5, 5));
        return txt;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(new RoundedBorder(5));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));

        // Hover effect
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    private void loadAllData() {
        String[] colHeader = {"NIP", "Nama", "Tempat Lahir", "Tanggal Lahir", "Jabatan"};
        defTab = new DefaultTableModel(null, colHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel tidak bisa diedit langsung
            }
        };

        try {
            Collection<Karyawan> list = ck.getAllKaryawan();
            if (list == null || list.isEmpty()) {
                TblKar.setModel(defTab);
                updateStatus("Tidak ada data");
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            
            for (Karyawan k : list) {
                String tgl = (k.getTglLhr() != null) ? sdf.format(k.getTglLhr()) : "-";
                Object[] row = {k.getNip(), k.getNmKar(), k.getTemLhr(), tgl, k.getJabatan()};
                defTab.addRow(row);
            }
            
            TblKar.setModel(defTab);
            updateStatus("Total data: " + list.size());
        } catch (Exception ex) {
            showError("Error load data: " + ex.getMessage());
            updateStatus("Error load data");
        }
    }

    private void fillTable(boolean filter) {
        String[] colHeader = {"NIP", "Nama", "Tempat Lahir", "Tanggal Lahir", "Jabatan"};
        defTab = new DefaultTableModel(null, colHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Collection<Karyawan> list;
        try {
            if (filter) {
                String nip = txtNip.getText().trim();
                if (nip.isEmpty()) {
                    showError("NIP tidak boleh kosong!");
                    return;
                }
                list = ck.getFilteredKaryawan("SELECT k FROM Karyawan k WHERE k.nip = '" + nip + "'");
            } else {
                list = ck.getAllKaryawan();
            }

            if (list == null || list.isEmpty()) {
                TblKar.setModel(defTab);
                updateStatus("Tidak ada data");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            for (Karyawan k : list) {
                String tgl = (k.getTglLhr() != null) ? sdf.format(k.getTglLhr()) : "-";
                Object[] row = {k.getNip(), k.getNmKar(), k.getTemLhr(), tgl, k.getJabatan()};
                defTab.addRow(row);
            }
            TblKar.setModel(defTab);
            updateStatus("Data di-filter: " + list.size() + " record(s)");
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
            updateStatus("Filter ERROR");
        }
    }

    private boolean validateForm() {
        if (txtNip.getText().trim().isEmpty()) {
            showError("NIP tidak boleh kosong!");
            txtNip.requestFocus();
            return false;
        }
        if (txtNama.getText().trim().isEmpty()) {
            showError("Nama tidak boleh kosong!");
            txtNama.requestFocus();
            return false;
        }
        if (txtTempLhr.getText().trim().isEmpty()) {
            showError("Tempat lahir tidak boleh kosong!");
            txtTempLhr.requestFocus();
            return false;
        }
        if (txtTgl.getText().trim().isEmpty() || txtBln.getText().trim().isEmpty() || txtThn.getText().trim().isEmpty()) {
            showError("Tanggal lahir tidak lengkap!");
            return false;
        }
        if (txtJabatan.getText().trim().isEmpty()) {
            showError("Jabatan tidak boleh kosong!");
            txtJabatan.requestFocus();
            return false;
        }
        
        // Validasi format tanggal
        try {
            int tgl = Integer.parseInt(txtTgl.getText().trim());
            int bln = Integer.parseInt(txtBln.getText().trim());
            int thn = Integer.parseInt(txtThn.getText().trim());
            
            if (tgl < 1 || tgl > 31 || bln < 1 || bln > 12 || thn < 1900 || thn > 2100) {
                showError("Format tanggal tidak valid!");
                return false;
            }
        } catch (NumberFormatException ex) {
            showError("Tanggal harus berupa angka!");
            return false;
        }
        
        return true;
    }

    private void insertAction() {
        if (!validateForm()) return;

        Karyawan k = new Karyawan();
        k.setNip(txtNip.getText().trim());
        k.setNmKar(txtNama.getText().trim());
        k.setTemLhr(txtTempLhr.getText().trim());
        k.setJabatan(txtJabatan.getText().trim());

        try {
            String tglStr = txtThn.getText() + "-" + 
                          String.format("%02d", Integer.parseInt(txtBln.getText())) + "-" + 
                          String.format("%02d", Integer.parseInt(txtTgl.getText()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            k.setTglLhr(sdf.parse(tglStr));

            if (ck.insert(k)) {
                showSuccess("✅ Data berhasil disimpan!");
                updateStatus("Data INSERT: " + k.getNip());
                clearForm();
                loadAllData();
            } else {
                showError("Gagal menyimpan data!");
                updateStatus("INSERT FAILED");
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
            updateStatus("INSERT ERROR");
        }
    }

    private void updateAction() {
        if (!validateForm()) return;

        Karyawan k = new Karyawan();
        k.setNip(txtNip.getText().trim());
        k.setNmKar(txtNama.getText().trim());
        k.setTemLhr(txtTempLhr.getText().trim());
        k.setJabatan(txtJabatan.getText().trim());

        try {
            String tglStr = txtThn.getText() + "-" + 
                          String.format("%02d", Integer.parseInt(txtBln.getText())) + "-" + 
                          String.format("%02d", Integer.parseInt(txtTgl.getText()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            k.setTglLhr(sdf.parse(tglStr));

            if (ck.update(k)) {
                showSuccess("✅ Data berhasil diupdate!");
                updateStatus("Data UPDATE: " + k.getNip());
                clearForm();
                loadAllData();
            } else {
                showError("Gagal update data!");
                updateStatus("UPDATE FAILED");
            }
        } catch (Exception ex) {
            showError("Error: " + ex.getMessage());
            updateStatus("UPDATE ERROR");
        }
    }

    private void deleteAction() {
        if (txtNip.getText().trim().isEmpty()) {
            showError("Pilih data yang akan dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
                "Yakin ingin menghapus data NIP: " + txtNip.getText() + "?", 
                "Konfirmasi Penghapusan", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String nip = txtNip.getText().trim();
                if (ck.delete(nip)) {
                    showSuccess("✅ Data berhasil dihapus!");
                    updateStatus("Data DELETE: " + nip);
                    clearForm();
                    loadAllData();
                } else {
                    showError("Gagal menghapus data!");
                    updateStatus("DELETE FAILED");
                }
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
                updateStatus("DELETE ERROR");
            }
        }
    }

    private void searchByNip() {
        String nip = txtNip.getText().trim();
        if (nip.isEmpty()) {
            showError("Masukkan NIP untuk pencarian!");
            return;
        }

        try {
            Karyawan k = ck.getKaryawan(nip);
            if (k != null) {
                txtNama.setText(k.getNmKar());
                txtTempLhr.setText(k.getTemLhr());
                txtJabatan.setText(k.getJabatan());

                if (k.getTglLhr() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String tgl = sdf.format(k.getTglLhr());
                    txtThn.setText(tgl.substring(0, 4));
                    txtBln.setText(tgl.substring(5, 7));
                    txtTgl.setText(tgl.substring(8, 10));
                }
                fillTable(true);
                updateStatus("Pencarian: " + nip + " ditemukan");
            } else {
                showError("Data NIP: " + nip + " tidak ditemukan!");
                updateStatus("Pencarian: " + nip + " tidak ditemukan");
            }
        } catch (Exception ex) {
            showError("Error pencarian: " + ex.getMessage());
            updateStatus("Search ERROR");
        }
    }

    private void loadFromTable() {
        int row = TblKar.getSelectedRow();
        if (row < 0) {
            showError("Pilih baris tabel terlebih dahulu!");
            return;
        }

        txtNip.setText((String) TblKar.getValueAt(row, 0));
        txtNama.setText((String) TblKar.getValueAt(row, 1));
        txtTempLhr.setText((String) TblKar.getValueAt(row, 2));
        String tgl = (String) TblKar.getValueAt(row, 3);
        
        if (tgl != null && !tgl.equals("-") && tgl.length() == 10) {
            // Format dd-MM-yyyy
            txtTgl.setText(tgl.substring(0, 2));
            txtBln.setText(tgl.substring(3, 5));
            txtThn.setText(tgl.substring(6, 10));
        }
        
        txtJabatan.setText((String) TblKar.getValueAt(row, 4));
    }

    private void clearForm() {
        txtNip.setText("");
        txtNama.setText("");
        txtTempLhr.setText("");
        txtTgl.setText("");
        txtBln.setText("");
        txtThn.setText("");
        txtJabatan.setText("");
        txtNip.requestFocus();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "❌ ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "✅ SUKSES", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStatus(String message) {
        lblStatus.setText("Status: " + message);
    }

    public static void showForm() {
        new FormKaryawan().setVisible(true);
    }
}

// Custom Border untuk rounded corners
class RoundedBorder extends AbstractBorder {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(189, 195, 199));
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
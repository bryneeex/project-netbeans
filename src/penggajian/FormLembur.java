package penggajian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
// import com.toedter.calendar.JDateChooser;

public class FormLembur extends JFrame {
    private JTextField txtIdLembur, txtJumlah;
    private JComboBox<String> cbIdKaryawan;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTanggalLemburPlaceholder; 
    // private JDateChooser dateLembur;

    public FormLembur() {
        setTitle("Data Lembur");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(52, 152, 219));
        
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlForm.setOpaque(false);
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pnlForm.add(createLabel("ID Lembur:"));
        txtIdLembur = new JTextField(); pnlForm.add(txtIdLembur);
        
        pnlForm.add(createLabel("ID Karyawan:"));
        cbIdKaryawan = new JComboBox<>(new String[]{"K001", "K002"}); // Dummy data
        pnlForm.add(cbIdKaryawan);
        
        pnlForm.add(createLabel("Tanggal Lembur:"));
        txtTanggalLemburPlaceholder = new JTextField("YYYY-MM-DD");
        pnlForm.add(txtTanggalLemburPlaceholder);
        // dateLembur = new JDateChooser(); pnlForm.add(dateLembur);
        
        pnlForm.add(createLabel("Jumlah Jam:"));
        txtJumlah = new JTextField(); pnlForm.add(txtJumlah);
        
        // Panel Buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.setOpaque(false);
        JButton btnSave = new JButton("Save");
        JButton btnReset = new JButton("Reset");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnExit = new JButton("Exit");
        
        pnlButtons.add(btnSave); pnlButtons.add(btnReset);
        pnlButtons.add(btnUpdate); pnlButtons.add(btnDelete); pnlButtons.add(btnExit);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(false);
        pnlTop.add(pnlForm, BorderLayout.CENTER);
        pnlTop.add(pnlButtons, BorderLayout.SOUTH);
        
        add(pnlTop, BorderLayout.NORTH);
        
        // Panel Table
        String[] cols = {"ID Lembur", "ID Karyawan", "Tgl Lembur", "Jumlah Jam"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        btnExit.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> simpanData());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
        btnReset.addActionListener(e -> resetForm());
        
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKlik();
            }
        });
        
        loadKaryawan();
        loadData();
    }
    
    private void loadKaryawan() {
        cbIdKaryawan.removeAllItems();
        try {
            java.sql.ResultSet rs = DatabaseHelper.executeQuery("SELECT id_karyawan FROM tb_karyawan");
            while (rs != null && rs.next()) {
                cbIdKaryawan.addItem(rs.getString("id_karyawan"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        try {
            java.sql.ResultSet rs = DatabaseHelper.executeQuery("SELECT * FROM tb_lembur");
            while (rs != null && rs.next()) {
                String tglMySQL = rs.getString("tanggal_lembur");
                String tglDisplay = tglMySQL;
                try {
                    java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tglMySQL);
                    tglDisplay = new java.text.SimpleDateFormat("dd-MM-yyyy").format(date);
                } catch (Exception ignored) {}
                
                tableModel.addRow(new Object[]{
                    rs.getString("id_lembur"), rs.getString("id_karyawan"), 
                    tglDisplay, rs.getInt("jumlah_jam")
                });
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void tabelKlik() {
        int row = table.getSelectedRow();
        if(row >= 0) {
            txtIdLembur.setText(tableModel.getValueAt(row, 0).toString());
            cbIdKaryawan.setSelectedItem(tableModel.getValueAt(row, 1).toString());
            txtTanggalLemburPlaceholder.setText(tableModel.getValueAt(row, 2).toString());
            txtJumlah.setText(tableModel.getValueAt(row, 3).toString());
        }
    }
    
    private void resetForm() {
        txtIdLembur.setText("");
        if(cbIdKaryawan.getItemCount() > 0) cbIdKaryawan.setSelectedIndex(0);
        txtTanggalLemburPlaceholder.setText("YYYY-MM-DD");
        txtJumlah.setText("");
    }
    
    private void simpanData() {
        try {
            String id = txtIdLembur.getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID Lembur tidak boleh kosong!");
                return;
            }
            if (cbIdKaryawan.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Data Karyawan kosong! Harap input Karyawan dulu.");
                return;
            }
            String idKaryawan = cbIdKaryawan.getSelectedItem().toString();
            String tglInput = txtTanggalLemburPlaceholder.getText();
            int jam = Integer.parseInt(txtJumlah.getText());
            
            // Konversi format tanggal
            String tglMySQL = tglInput;
            try {
                java.text.SimpleDateFormat formatInput = new java.text.SimpleDateFormat("dd-MM-yyyy");
                java.text.SimpleDateFormat formatDB = new java.text.SimpleDateFormat("yyyy-MM-dd");
                tglMySQL = formatDB.format(formatInput.parse(tglInput));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan format DD-MM-YYYY", "Error Tanggal", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String sql = "INSERT INTO tb_lembur VALUES (?, ?, ?, ?)";
            if (DatabaseHelper.executeUpdate(sql, id, idKaryawan, tglMySQL, jam)) {
                JOptionPane.showMessageDialog(this, "Data Lembur berhasil disimpan!");
                loadData();
                resetForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah Jam harus berupa angka!", "Error Validasi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateData() {
        try {
            String id = txtIdLembur.getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap pilih data dari tabel terlebih dahulu!");
                return;
            }
            String idKaryawan = cbIdKaryawan.getSelectedItem().toString();
            String tglInput = txtTanggalLemburPlaceholder.getText();
            int jam = Integer.parseInt(txtJumlah.getText());
            
            String tglMySQL = tglInput;
            try {
                tglMySQL = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.text.SimpleDateFormat("dd-MM-yyyy").parse(tglInput));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan DD-MM-YYYY", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String sql = "UPDATE tb_lembur SET id_karyawan=?, tanggal_lembur=?, jumlah_jam=? WHERE id_lembur=?";
            if (DatabaseHelper.executeUpdate(sql, idKaryawan, tglMySQL, jam, id)) {
                JOptionPane.showMessageDialog(this, "Data Lembur berhasil diupdate!");
                loadData();
                resetForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah Jam harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteData() {
        String id = txtIdLembur.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus dari tabel!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int konfirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data Lembur " + id + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tb_lembur WHERE id_lembur=?";
            if (DatabaseHelper.executeUpdate(sql, id)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                loadData();
                resetForm();
            }
        }
    }
    
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
}

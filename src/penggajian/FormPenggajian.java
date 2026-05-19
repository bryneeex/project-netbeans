package penggajian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormPenggajian extends JFrame {
    private JTextField txtIdGaji, txtNamaKaryawan, txtGolongan;
    private JTextField txtJumlahGaji, txtJumlahLembur, txtPotongan, txtTotalGaji;
    private JComboBox<String> cbIdKaryawan;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private JTextField txtTanggalGajiPlaceholder;
    // private JDateChooser dateGaji;

    public FormPenggajian() {
        setTitle("Daftar Gaji Karyawan");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(52, 152, 219));
        
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form
        JPanel pnlForm = new JPanel(new GridLayout(9, 2, 5, 5));
        pnlForm.setOpaque(false);
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pnlForm.add(createLabel("ID Gaji:"));
        txtIdGaji = new JTextField(); pnlForm.add(txtIdGaji);
        
        pnlForm.add(createLabel("Tanggal Gaji:"));
        txtTanggalGajiPlaceholder = new JTextField("YYYY-MM-DD"); pnlForm.add(txtTanggalGajiPlaceholder);
        // dateGaji = new JDateChooser(); pnlForm.add(dateGaji);
        
        pnlForm.add(createLabel("ID Karyawan:"));
        cbIdKaryawan = new JComboBox<>(new String[]{"K001", "K002"}); pnlForm.add(cbIdKaryawan);
        
        pnlForm.add(createLabel("Nama Karyawan:"));
        txtNamaKaryawan = new JTextField(); txtNamaKaryawan.setEditable(false); pnlForm.add(txtNamaKaryawan);
        
        pnlForm.add(createLabel("Golongan:"));
        txtGolongan = new JTextField(); txtGolongan.setEditable(false); pnlForm.add(txtGolongan);
        
        pnlForm.add(createLabel("Jumlah Gaji:"));
        txtJumlahGaji = new JTextField(); txtJumlahGaji.setEditable(false); pnlForm.add(txtJumlahGaji);
        
        pnlForm.add(createLabel("Jumlah Lembur (Rp):"));
        txtJumlahLembur = new JTextField(); txtJumlahLembur.setEditable(false); pnlForm.add(txtJumlahLembur);
        
        pnlForm.add(createLabel("Potongan:"));
        txtPotongan = new JTextField("0"); pnlForm.add(txtPotongan);
        
        pnlForm.add(createLabel("Total Gaji Bersih:"));
        txtTotalGaji = new JTextField(); txtTotalGaji.setEditable(false); pnlForm.add(txtTotalGaji);
        
        add(pnlForm, BorderLayout.NORTH);
        
        // Panel Buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.setOpaque(false);
        JButton btnHitung = new JButton("Hitung Total");
        JButton btnSave = new JButton("Save");
        JButton btnReset = new JButton("Reset");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnExit = new JButton("Exit");
        
        pnlButtons.add(btnHitung);
        pnlButtons.add(btnSave); pnlButtons.add(btnReset);
        pnlButtons.add(btnUpdate); pnlButtons.add(btnDelete); pnlButtons.add(btnExit);
        add(pnlButtons, BorderLayout.CENTER);
        
        // Panel Table
        String[] cols = {"ID Gaji", "Tgl", "ID Kary", "Nama", "Golongan", "Gaji", "Lembur", "Potongan", "Total"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);
        
        btnExit.addActionListener(e -> dispose());
        btnHitung.addActionListener(e -> hitungGaji());
        btnSave.addActionListener(e -> simpanData());
    }
    
    private void simpanData() {
        try {
            String id = txtIdGaji.getText();
            String tgl = txtTanggalGajiPlaceholder.getText();
            String idKaryawan = cbIdKaryawan.getSelectedItem().toString();
            String nama = txtNamaKaryawan.getText();
            String golongan = txtGolongan.getText();
            
            // Hitung dulu jika belum dihitung
            if (txtTotalGaji.getText().isEmpty()) {
                hitungGaji();
            }
            
            double gapok = Double.parseDouble(txtJumlahGaji.getText().isEmpty() ? "0" : txtJumlahGaji.getText());
            double lembur = Double.parseDouble(txtJumlahLembur.getText().isEmpty() ? "0" : txtJumlahLembur.getText());
            double potongan = Double.parseDouble(txtPotongan.getText().isEmpty() ? "0" : txtPotongan.getText());
            double total = Double.parseDouble(txtTotalGaji.getText());
            
            String sql = "INSERT INTO tb_penggajian VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            if (DatabaseHelper.executeUpdate(sql, id, tgl, idKaryawan, nama, golongan, gapok, lembur, potongan, total)) {
                JOptionPane.showMessageDialog(this, "Data Penggajian berhasil disimpan!");
                tableModel.addRow(new Object[]{id, tgl, idKaryawan, nama, golongan, gapok, lembur, potongan, total});
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Pastikan format angka benar sebelum menyimpan!", "Error Validasi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hitungGaji() {
        try {
            double gaji = txtJumlahGaji.getText().isEmpty() ? 0 : Double.parseDouble(txtJumlahGaji.getText());
            double lembur = txtJumlahLembur.getText().isEmpty() ? 0 : Double.parseDouble(txtJumlahLembur.getText());
            double potongan = txtPotongan.getText().isEmpty() ? 0 : Double.parseDouble(txtPotongan.getText());
            
            double total = gaji + lembur - potongan;
            txtTotalGaji.setText(String.valueOf(total));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Format angka salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
}

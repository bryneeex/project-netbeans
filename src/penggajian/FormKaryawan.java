package penggajian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
// Uncomment the line below once jcalendar.jar is added to the project libraries!
// import com.toedter.calendar.JDateChooser;

public class FormKaryawan extends JFrame {
    private JTextField txtIdKaryawan, txtNama, txtTempat;
    private JComboBox<String> cbIdGolongan;
    private JRadioButton rbLaki, rbPerempuan, rbMenikah, rbBelum;
    private JTextArea txtAlamat;
    private JTable table;
    private DefaultTableModel tableModel;
    
    // Use JTextField as placeholder until JCalendar is imported
    private JTextField txtTanggalLahirPlaceholder; 
    // private JDateChooser dateLahir;

    public FormKaryawan() {
        setTitle("Data Karyawan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(52, 152, 219));
        
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form
        JPanel pnlForm = new JPanel(new GridLayout(8, 2, 5, 5));
        pnlForm.setOpaque(false);
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pnlForm.add(createLabel("ID Karyawan:"));
        txtIdKaryawan = new JTextField(); pnlForm.add(txtIdKaryawan);
        
        pnlForm.add(createLabel("Nama:"));
        txtNama = new JTextField(); pnlForm.add(txtNama);
        
        pnlForm.add(createLabel("ID Golongan:"));
        cbIdGolongan = new JComboBox<>(new String[]{"G001", "G002", "G003", "G004"});
        pnlForm.add(cbIdGolongan);
        
        pnlForm.add(createLabel("Jenis Kelamin:"));
        JPanel pnlJK = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlJK.setOpaque(false);
        rbLaki = new JRadioButton("Laki-laki"); rbLaki.setOpaque(false); rbLaki.setForeground(Color.WHITE);
        rbPerempuan = new JRadioButton("Perempuan"); rbPerempuan.setOpaque(false); rbPerempuan.setForeground(Color.WHITE);
        ButtonGroup bgJK = new ButtonGroup(); bgJK.add(rbLaki); bgJK.add(rbPerempuan);
        pnlJK.add(rbLaki); pnlJK.add(rbPerempuan);
        pnlForm.add(pnlJK);
        
        pnlForm.add(createLabel("Tempat Lahir:"));
        txtTempat = new JTextField(); pnlForm.add(txtTempat);
        
        pnlForm.add(createLabel("Tanggal Lahir:"));
        txtTanggalLahirPlaceholder = new JTextField("YYYY-MM-DD");
        pnlForm.add(txtTanggalLahirPlaceholder); 
        // dateLahir = new JDateChooser(); pnlForm.add(dateLahir);
        
        pnlForm.add(createLabel("Status:"));
        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlStatus.setOpaque(false);
        rbMenikah = new JRadioButton("Menikah"); rbMenikah.setOpaque(false); rbMenikah.setForeground(Color.WHITE);
        rbBelum = new JRadioButton("Belum Menikah"); rbBelum.setOpaque(false); rbBelum.setForeground(Color.WHITE);
        ButtonGroup bgStatus = new ButtonGroup(); bgStatus.add(rbMenikah); bgStatus.add(rbBelum);
        pnlStatus.add(rbMenikah); pnlStatus.add(rbBelum);
        pnlForm.add(pnlStatus);
        
        pnlForm.add(createLabel("Alamat:"));
        txtAlamat = new JTextArea(3, 20);
        pnlForm.add(new JScrollPane(txtAlamat));
        
        add(pnlForm, BorderLayout.NORTH);
        
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
        add(pnlButtons, BorderLayout.CENTER);
        
        // Panel Table
        String[] cols = {"ID", "Nama", "Golongan", "L/P", "Tempat", "Tgl Lahir", "Status", "Alamat"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);
        
        btnExit.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> simpanData());
    }
    
    private void simpanData() {
        String id = txtIdKaryawan.getText();
        String nama = txtNama.getText();
        String idGol = cbIdGolongan.getSelectedItem().toString();
        String jk = rbLaki.isSelected() ? "Laki-laki" : (rbPerempuan.isSelected() ? "Perempuan" : "");
        String tempat = txtTempat.getText();
        String tglInput = txtTanggalLahirPlaceholder.getText(); // Input dari user (DD-MM-YYYY)
        String status = rbMenikah.isSelected() ? "Menikah" : (rbBelum.isSelected() ? "Belum Menikah" : "");
        String alamat = txtAlamat.getText();

        if (id.isEmpty() || nama.isEmpty() || jk.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap lengkapi semua data wajib!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Konversi DD-MM-YYYY menjadi YYYY-MM-DD untuk MySQL
        String tglMySQL = tglInput;
        try {
            java.text.SimpleDateFormat formatInput = new java.text.SimpleDateFormat("dd-MM-yyyy");
            java.text.SimpleDateFormat formatDB = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = formatInput.parse(tglInput);
            tglMySQL = formatDB.format(date);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Format tanggal salah! Gunakan format DD-MM-YYYY (Contoh: 17-03-2009)", "Error Tanggal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO tb_karyawan (id_karyawan, nama, id_golongan, jenis_kelamin, tempat_lahir, tanggal_lahir, status, alamat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        if (DatabaseHelper.executeUpdate(sql, id, nama, idGol, jk, tempat, tglMySQL, status, alamat)) {
            JOptionPane.showMessageDialog(this, "Data Karyawan berhasil disimpan!");
            tableModel.addRow(new Object[]{id, nama, idGol, jk, tempat, tglInput, status, alamat});
        }
    }
    
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
}

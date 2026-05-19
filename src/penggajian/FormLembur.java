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
        String[] cols = {"ID Lembur", "ID Karyawan", "Tgl Lembur", "Jumlah Jam"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);
        
        btnExit.addActionListener(e -> dispose());
    }
    
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
}

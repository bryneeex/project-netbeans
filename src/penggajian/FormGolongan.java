package penggajian;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormGolongan extends JFrame {
    private JTextField txtIdGolongan, txtNamaGolongan, txtGajiPokok, txtTunjanganIstri;
    private JTextField txtJumlahAnak, txtTunjanganAnak, txtTransport, txtUangMakan;
    private JTable table;
    private DefaultTableModel tableModel;

    public FormGolongan() {
        setTitle("Data Golongan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(52, 152, 219));
        
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form
        JPanel pnlForm = new JPanel(new GridLayout(8, 2, 5, 5));
        pnlForm.setOpaque(false);
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pnlForm.add(createLabel("ID Golongan:"));
        txtIdGolongan = new JTextField(); pnlForm.add(txtIdGolongan);
        
        pnlForm.add(createLabel("Nama Golongan:"));
        txtNamaGolongan = new JTextField(); pnlForm.add(txtNamaGolongan);
        
        pnlForm.add(createLabel("Gaji Pokok:"));
        txtGajiPokok = new JTextField(); pnlForm.add(txtGajiPokok);
        
        pnlForm.add(createLabel("Tunjangan Istri:"));
        txtTunjanganIstri = new JTextField(); pnlForm.add(txtTunjanganIstri);
        
        pnlForm.add(createLabel("Jumlah Anak:"));
        txtJumlahAnak = new JTextField(); pnlForm.add(txtJumlahAnak);
        
        pnlForm.add(createLabel("Tunjangan Anak:"));
        txtTunjanganAnak = new JTextField(); pnlForm.add(txtTunjanganAnak);
        
        pnlForm.add(createLabel("Transport:"));
        txtTransport = new JTextField(); pnlForm.add(txtTransport);
        
        pnlForm.add(createLabel("Uang Makan:"));
        txtUangMakan = new JTextField(); pnlForm.add(txtUangMakan);
        
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
        String[] cols = {"ID", "Nama", "Gaji Pokok", "Tj. Istri", "Jml Anak", "Tj. Anak", "Transport", "Makan"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.SOUTH);
        
        // Events
        btnExit.addActionListener(e -> dispose());
        // TODO: Impelementasi Event CRUD ke DatabaseHelper
    }
    
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
}

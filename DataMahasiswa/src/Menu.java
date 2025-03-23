import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(480, 560);

        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);

        // isi window
        window.setContentPane(window.mainPanel);

        // ubah warna background
        window.getContentPane().setBackground(Color.white);

        // tampilkan window
        window.setVisible(true);

        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> jenisKelaminComboBox;
    private JButton deleteButton;
    private JSlider usiaSlider;
    private JRadioButton kelasC1Button;
    private JRadioButton kelasC2Button;
    private ButtonGroup kelasButtonGroup;
    private JLabel titleLabel = new JLabel("Data Mahasiswa", SwingConstants.CENTER);
    private JLabel nimLabel = new JLabel("NIM:");
    private JLabel namaLabel = new JLabel("Nama:");
    private JLabel jenisKelaminLabel = new JLabel("Jenis Kelamin:");
    private JLabel usiaLabel = new JLabel("Usia:");
    private JLabel kelasLabel = new JLabel("Kelas:");

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // isi listMahasiswa
        populateList();

        // isi tabel mahasiswa
        mahasiswaTable = new JTable();

        // initialize mainPanel and set layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(11, 2, 10, 10));

        // add components to mainPanel
        mainPanel.add(nimLabel);
        mainPanel.add(nimField = new JTextField());
        mainPanel.add(namaLabel);
        mainPanel.add(namaField = new JTextField());
        mainPanel.add(jenisKelaminLabel);
        mainPanel.add(jenisKelaminComboBox = new JComboBox<>());
        mainPanel.add(usiaLabel);
        mainPanel.add(usiaSlider = new JSlider(18, 30, 18));
        mainPanel.add(kelasLabel);
        JPanel kelasPanel = new JPanel(new FlowLayout());
        kelasC1Button = new JRadioButton("C1");
        kelasC2Button = new JRadioButton("C2");
        kelasButtonGroup = new ButtonGroup();
        kelasButtonGroup.add(kelasC1Button);
        kelasButtonGroup.add(kelasC2Button);
        kelasPanel.add(kelasC1Button);
        kelasPanel.add(kelasC2Button);
        mainPanel.add(kelasPanel);
        mainPanel.add(addUpdateButton = new JButton("Add"));
        mainPanel.add(cancelButton = new JButton("Cancel"));
        mainPanel.add(deleteButton = new JButton("Delete"));
        mainPanel.add(new JScrollPane(mahasiswaTable));
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] jenisKelaminData = { "Laki-laki", "Perempuan" };
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<String>(jenisKelaminData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex != -1) {
                    deleteData();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield, combo box, slider, dan radio button
                String selectedNim = mahasiswaTable.getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getValueAt(selectedIndex, 3).toString();
                int selectedUsia = Integer.parseInt(mahasiswaTable.getValueAt(selectedIndex, 4).toString());
                String selectedKelas = mahasiswaTable.getValueAt(selectedIndex, 5).toString();

                // ubah isi textfield, combo box, slider, dan radio button
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                usiaSlider.setValue(selectedUsia);
                if (selectedKelas.equals("C1")) {
                    kelasC1Button.setSelected(true);
                } else {
                    kelasC2Button.setSelected(true);
                }

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public void insertData() {
        // ambil value dari textfield, combobox, slider, dan radio button
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int usia = usiaSlider.getValue();
        String kelas = kelasC1Button.isSelected() ? "C1" : "C2";

        // tambahkan data ke dalam list
        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin, usia, kelas));

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        int usia = usiaSlider.getValue();
        String kelas = kelasC1Button.isSelected() ? "C1" : "C2";

        // ubah data mahasiswa di list
        Mahasiswa mahasiswa = listMahasiswa.get(selectedIndex);
        mahasiswa.setNim(nim);
        mahasiswa.setNama(nama);
        mahasiswa.setJenisKelamin(jenisKelamin);
        mahasiswa.setUsia(usia);
        mahasiswa.setKelas(kelas);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    }

    public void deleteData() {
        // hapus data dari list
        listMahasiswa.remove(selectedIndex);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = { "No", "NIM", "Nama", "Jenis Kelamin", "Usia", "Kelas" };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(column, 0);

        // isi tabel dengan listMahasiswa
        for (int i = 0; i < listMahasiswa.size(); i++) {
            Mahasiswa mahasiswa = listMahasiswa.get(i);
            Object[] data = {
                    i + 1,
                    mahasiswa.getNim(),
                    mahasiswa.getNama(),
                    mahasiswa.getJenisKelamin(),
                    mahasiswa.getUsia(),
                    mahasiswa.getKelas()
            };
            temp.addRow(data);
        }

        return temp;
    }

    public void clearForm() {
        // kosongkan semua texfield, combo box, slider, dan radio button
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        usiaSlider.setValue(18); // default usia
        kelasButtonGroup.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", 21, "C1"));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", 21, "C1"));
    }
}

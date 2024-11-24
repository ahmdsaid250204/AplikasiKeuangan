
# Aplikasi Keuangan

# Deskripsi
    "Aplikasi Keuangan". 

Judul Halaman: "Halaman Utama", menandakan bahwa ini adalah tampilan awal aplikasi.

Tombol-tombol:

Tombol "Pemasukan": Untuk mencatat atau mengelola pemasukan keuangan.
Tombol "Pengeluaran": Untuk mencatat atau mengelola pengeluaran keuangan.
Tombol "Dompet Saya": Kemungkinan berfungsi untuk menampilkan informasi atau saldo dari akun keuangan pengguna.



Judul Halaman: "Pemasukan" tertulis di bagian atas dengan gaya tebal, menunjukkan bahwa halaman ini digunakan untuk mengelola data pemasukan.

Form Input:

Keterangan: Sebuah kolom input untuk menambahkan deskripsi pemasukan (misalnya, "Gaji Bulanan").
Jumlah: Kolom input untuk mencatat jumlah uang yang diterima.
Tanggal: Kolom input untuk memasukkan tanggal pemasukan, dengan tombol kecil bergambar kalender di sebelahnya.
Kategori: Dropdown (menu pilihan) untuk memilih kategori pemasukan, seperti "Gaji", "Hadiah", dll.
Tombol Aksi:

Tambah: Untuk menambahkan data baru ke daftar pemasukan.
Edit: Untuk mengubah data yang sudah ada.
Hapus: Untuk menghapus data pemasukan yang dipilih.
Kembali: Untuk kembali ke halaman sebelumnya.
Tabel Data Pemasukan:

Berisi daftar pemasukan yang sudah dimasukkan oleh pengguna. Kolom dalam tabel mencakup:
ID: Nomor unik untuk setiap data pemasukan.
Keterangan: Deskripsi pemasukan (contoh: "Gaji Bulanan", "Tunjangan").
Jumlah: Nilai uang yang diterima.
Tanggal: Tanggal pemasukan dicatat.
Kategori: Kategori dari pemasukan tersebut (misalnya "Gaji" atau "Hadiah").



Judul: Tertulis "Pengeluaran" di bagian atas sebagai judul utama aplikasi.

Form Input:
Keterangan: Terdapat kotak teks untuk mengisi deskripsi pengeluaran.
Jumlah: Kotak teks untuk memasukkan nominal atau jumlah pengeluaran.
Tanggal: Kotak teks untuk tanggal dengan ikon kalender di sebelahnya untuk memilih tanggal.
Kategori: Dropdown menu dengan opsi seperti "Belanja", yang digunakan untuk mengkategorikan pengeluaran.
Tombol Aksi:
Tambah: Untuk menambahkan data baru ke daftar pengeluaran.
Edit: Untuk mengubah data yang sudah ada.
Hapus: Untuk menghapus data pengeluaran yang dipilih.
Kembali: Untuk kembali ke menu sebelumnya atau keluar dari halaman ini.
Tabel Data:
Menampilkan data pengeluaran yang sudah dicatat, dengan kolom: ID, Keterangan, Jumlah, Tanggal, dan Kategori.
Dua contoh data ditampilkan:
"Makan KFC" dengan jumlah 200,000, tanggal 22 November 2024, kategori "Makanan".
"Kebutuhan ..." dengan jumlah 300,000, tanggal 21 November 2024, kategori "Lainnya".


Judul:

Terletak di bagian atas, dengan tulisan "Dompet Saya" sebagai nama aplikasi.
Saldo Dompet:

Menampilkan total saldo dompet dengan format mata uang. Dalam contoh ini, saldonya adalah Rp 875993000000.00.
Tabel Transaksi:

Menyajikan daftar transaksi keuangan yang meliputi:

ID: Nomor urut transaksi.
Jenis Transaksi: Pemasukan atau Pengeluaran.
Keterangan: Penjelasan singkat transaksi, misalnya "Gaji Bulanan", "Makan KFC", dll.
Jumlah: Nominal uang dalam transaksi.
Tanggal: Tanggal transaksi terjadi.
Kategori: Klasifikasi transaksi, seperti Gaji, Hadiah, Makanan, Lainnya.
Data contoh yang ditampilkan:

ID 1: Pemasukan, Gaji Bulanan, Rp 10.000.000, Tanggal 22 November 2024, Kategori Gaji.
ID 3: Pemasukan, Tunjangan, Rp 2.000.000, Tanggal 22 November 2024, Kategori Hadiah.
ID 1: Pengeluaran, Makan KFC, Rp 200.000, Tanggal 22 November 2024, Kategori Makanan.
ID 2: Pengeluaran, Kebutuhan..., Rp 300.000, Tanggal 21 November 2024, Kategori Lainnya.
ID 5: Pemasukan, Gaji Harian, Rp 600.000, Tanggal 14 November 2024, Kategori Gaji.
Tombol Aksi:

Refresh: Untuk memuat ulang data.
Kembali: Untuk kembali ke menu utama atau sebelumnya.
Cetak Data: Untuk mencetak laporan transaksi.
Dropdown menu di bagian bawah: Kemungkinan untuk memfilter data berdasarkan kategori atau jenis transaksi.










# coding

## coding Java Aplikasi Keuangan
```java 

- koding FormDompet

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class FormDompet extends javax.swing.JFrame {

    /**
     * Creates new form FormDompet
     */
    public FormDompet() {
        initComponents();
        tampilkanSaldoDompet();
        tampilkanRiwayatDompet();
        filterData();

        buttonKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKembaliActionPerformed(evt);
            }
        });

        buttonCetakPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakPDF();
            }
        });

        comboBoxFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterData();
            }
        });

    }

    private void tampilkanSaldoDompet() {
        String query = "SELECT saldo FROM dompet WHERE id = 1"; // Ambil saldo dari tabel dompet
        try (Connection conn = DatabaseConnection.connect();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                String saldo = rs.getString("saldo");
                labelSaldoDompet.setText("Saldo Dompet: Rp " + saldo); // Tampilkan saldo
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menampilkan saldo!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilkanRiwayatDompet() {
        String[] kolom = {"ID", "Jenis Transaksi", "Keterangan", "Jumlah", "Tanggal", "Kategori"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);

        String query = "SELECT id, 'Pemasukan' AS jenis_transaksi, keterangan, jumlah, tanggal, kategori FROM pemasukan "
                + "UNION ALL "
                + "SELECT id, 'Pengeluaran' AS jenis_transaksi, keterangan, jumlah, tanggal, kategori FROM pengeluaran "
                + "ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.connect();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String jenisTransaksi = rs.getString("jenis_transaksi");
                String keterangan = rs.getString("keterangan");
                String jumlah = rs.getString("jumlah");
                String tanggal = rs.getString("tanggal");
                String kategori = rs.getString("kategori");

                String[] data = {id, jenisTransaksi, keterangan, jumlah, tanggal, kategori};
                model.addRow(data);
            }

            tableDompet.setModel(model); // Set data ke JTable
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menampilkan riwayat transaksi!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buttonKembaliActionPerformed(java.awt.event.ActionEvent evt) {
        // Tampilkan MainView
        MainView mainView = new MainView();
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null); // Tampilkan di tengah layar

        // Tutup form saat ini
        this.dispose();
    }

    private void cetakPDF() {
        String filePath = "Laporan_Dompet.pdf"; // Lokasi file output

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Judul Dokumen
            document.add(new Paragraph("Laporan Dompet"));
            document.add(new Paragraph(" ")); // Spasi kosong

            // Tabel PDF
            PdfPTable pdfTable = new PdfPTable(tableDompet.getColumnCount());
            pdfTable.setWidthPercentage(100);
            pdfTable.setSpacingBefore(10f);

            // Header Tabel
            for (int i = 0; i < tableDompet.getColumnCount(); i++) {
                PdfPCell cell = new PdfPCell(new Phrase(tableDompet.getColumnName(i)));
                pdfTable.addCell(cell);
            }

            // Data Tabel
            for (int rows = 0; rows < tableDompet.getRowCount(); rows++) {
                for (int cols = 0; cols < tableDompet.getColumnCount(); cols++) {
                    String cellValue = tableDompet.getValueAt(rows, cols).toString();
                    pdfTable.addCell(cellValue);
                }
            }

            document.add(pdfTable); // Tambahkan tabel ke dokumen
            document.close();

            JOptionPane.showMessageDialog(this, "Laporan berhasil dicetak ke file: " + filePath);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencetak PDF!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterData() {
        String selectedFilter = comboBoxFilter.getSelectedItem().toString();
        String query;

        // Tentukan query berdasarkan filter
        if (selectedFilter.equals("Pemasukan")) {
            query = "SELECT id, 'Pemasukan' AS jenis_transaksi, keterangan, jumlah, tanggal, kategori FROM pemasukan ORDER BY tanggal DESC";
        } else if (selectedFilter.equals("Pengeluaran")) {
            query = "SELECT id, 'Pengeluaran' AS jenis_transaksi, keterangan, jumlah, tanggal, kategori FROM pengeluaran ORDER BY tanggal DESC";
        } else {
            query = "SELECT id, 'Pemasukan' AS jenis_transaksi, keterangan, jumlah, tanggal, kategori FROM pemasukan "
                    + "UNION ALL "
                    + "SELECT id, 'Pengeluaran' AS jenis_transaksi, keterangan, jumlah, tanggal, kategori FROM pengeluaran "
                    + "ORDER BY tanggal DESC";
        }

        // Tampilkan data ke JTable
        tampilkanDataDenganQuery(query);
    }

    private void tampilkanDataDenganQuery(String query) {
        String[] kolom = {"ID", "Jenis Transaksi", "Keterangan", "Jumlah", "Tanggal", "Kategori"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);

        try (Connection conn = DatabaseConnection.connect();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String jenisTransaksi = rs.getString("jenis_transaksi");
                String keterangan = rs.getString("keterangan");
                String jumlah = rs.getString("jumlah");
                String tanggal = rs.getString("tanggal");
                String kategori = rs.getString("kategori");

                String[] data = {id, jenisTransaksi, keterangan, jumlah, tanggal, kategori};
                model.addRow(data);
            }

            tableDompet.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memfilter data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelSaldoDompet = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDompet = new javax.swing.JTable();
        buttonRefresh = new javax.swing.JButton();
        buttonKembali = new javax.swing.JButton();
        buttonCetakPDF = new javax.swing.JButton();
        comboBoxFilter = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dompet Saya", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24))); // NOI18N

        labelSaldoDompet.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelSaldoDompet.setText("Saldo Dompet");

        tableDompet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableDompet);

        buttonRefresh.setText("Refresh");
        buttonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRefreshActionPerformed(evt);
            }
        });

        buttonKembali.setText("Kembali");

        buttonCetakPDF.setText("Cetak  Data");

        comboBoxFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semua", "Pemasukan", "Pengeluaran" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelSaldoDompet)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonRefresh)
                                    .addComponent(buttonKembali))
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(buttonCetakPDF)
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(comboBoxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(labelSaldoDompet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonKembali)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCetakPDF)
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void buttonRefreshActionPerformed(java.awt.event.ActionEvent evt) {                                              
        tampilkanSaldoDompet();
        tampilkanRiwayatDompet();
    }                                             

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDompet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDompet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDompet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDompet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDompet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonCetakPDF;
    private javax.swing.JButton buttonKembali;
    private javax.swing.JButton buttonRefresh;
    private javax.swing.JComboBox<String> comboBoxFilter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelSaldoDompet;
    private javax.swing.JTable tableDompet;
    // End of variables declaration                   
}




- koding FormPemasukan


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.math.BigDecimal;

public class FormPemasukan extends javax.swing.JFrame {

    /**
     * Creates new form FormPemasukan
     */
    public FormPemasukan() {
        initComponents();
        isiComboBoxKategori();
        tampilkanDataPemasukan();

        tablePemasukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePemasukanMouseClicked(evt);
            }
        });

        buttonKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKembaliActionPerformed(evt);
            }
        });

    }

    private void tambahPemasukan() {
        String query = "INSERT INTO pemasukan (keterangan, jumlah, tanggal, kategori) VALUES (?, ?, ?, ?)";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = null;
        if (dateChooserTanggal.getDate() != null) {
            tanggal = dateFormat.format(dateChooserTanggal.getDate());
        } else {
            JOptionPane.showMessageDialog(this, "Tanggal tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement ps = conn.prepareStatement(query)) {

            String keterangan = textFieldKeterangan.getText().trim();
            if (keterangan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keterangan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ps.setString(1, keterangan);

            try {
                String jumlahText = textFieldJumlah.getText().trim();
                if (jumlahText.isEmpty()) {
                    throw new NumberFormatException("Jumlah tidak boleh kosong");
                }
                BigDecimal jumlah = new BigDecimal(jumlahText);
                ps.setBigDecimal(2, jumlah);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ps.setString(3, tanggal);
            String kategori = comboBoxKategori.getSelectedItem() != null
                    ? comboBoxKategori.getSelectedItem().toString()
                    : null;
            if (kategori == null || kategori.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih kategori!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ps.setString(4, kategori);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Pemasukan berhasil ditambahkan!");

            tampilkanDataPemasukan(); // Refresh tabel
            clearFields(); // Kosongkan field input setelah berhasil
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menambah data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilkanDataPemasukan() {
        String[] kolom = {"ID", "Keterangan", "Jumlah", "Tanggal", "Kategori"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);

        String query = "SELECT * FROM pemasukan";

        try (Connection conn = DatabaseConnection.connect();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String keterangan = rs.getString("keterangan");
                String jumlah = rs.getString("jumlah");
                String tanggal = rs.getString("tanggal");
                String kategori = rs.getString("kategori");

                String[] data = {id, keterangan, jumlah, tanggal, kategori};
                model.addRow(data);
            }

            tablePemasukan.setModel(model); // Set data ke JTable
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editPemasukan() {
        int row = tablePemasukan.getSelectedRow();
        if (row >= 0) {
            String id = tablePemasukan.getValueAt(row, 0).toString();
            String query = "UPDATE pemasukan SET keterangan = ?, jumlah = ?, tanggal = ?, kategori = ? WHERE id = ?";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = dateFormat.format(dateChooserTanggal.getDate());

            try (Connection conn = DatabaseConnection.connect();
                    PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, textFieldKeterangan.getText());
                ps.setBigDecimal(2, new BigDecimal(textFieldJumlah.getText()));
                ps.setString(3, tanggal);
                ps.setString(4, comboBoxKategori.getSelectedItem().toString());
                ps.setString(5, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
                tampilkanDataPemasukan(); // Refresh tabel
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengubah data!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah!");
        }
    }

    private void hapusPemasukan() {
        int row = tablePemasukan.getSelectedRow();
        if (row >= 0) {
            String id = tablePemasukan.getValueAt(row, 0).toString();
            String query = "DELETE FROM pemasukan WHERE id = ?";

            try (Connection conn = DatabaseConnection.connect();
                    PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                tampilkanDataPemasukan(); // Refresh tabel
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
        }
    }

    private void isiComboBoxKategori() {
        comboBoxKategori.addItem("Gaji");
        comboBoxKategori.addItem("Hadiah");
        comboBoxKategori.addItem("Lainnya");
        comboBoxKategori.addItem("Belanja");
        comboBoxKategori.addItem("Transportasi");
    }

    private void clearFields() {
        textFieldKeterangan.setText(""); // Kosongkan field keterangan
        textFieldJumlah.setText("");     // Kosongkan field jumlah
        dateChooserTanggal.setDate(null); // Kosongkan JDateChooser
        comboBoxKategori.setSelectedIndex(0); // Reset kategori ke pilihan pertama
    }

    private void tablePemasukanMouseClicked(java.awt.event.MouseEvent evt) {
        // Ambil baris yang diklik
        int row = tablePemasukan.getSelectedRow();

        // Ambil data dari JTable dan set ke field input
        textFieldKeterangan.setText(tablePemasukan.getValueAt(row, 1).toString());
        textFieldJumlah.setText(tablePemasukan.getValueAt(row, 2).toString());

        // Parsing tanggal dari tabel ke JDateChooser
        try {
            java.util.Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(tablePemasukan.getValueAt(row, 3).toString());
            dateChooserTanggal.setDate(tanggal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pilih kategori di ComboBox
        String kategori = tablePemasukan.getValueAt(row, 4).toString();
        comboBoxKategori.setSelectedItem(kategori);
    }

    private void buttonKembaliActionPerformed(java.awt.event.ActionEvent evt) {
        // Tampilkan MainView
        MainView mainView = new MainView();
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null); // Tampilkan di tengah layar

        // Tutup form saat ini
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textFieldKeterangan = new javax.swing.JTextField();
        textFieldJumlah = new javax.swing.JTextField();
        comboBoxKategori = new javax.swing.JComboBox<>();
        dateChooserTanggal = new com.toedter.calendar.JDateChooser();
        buttonEdit = new javax.swing.JButton();
        buttonTambah = new javax.swing.JButton();
        buttonHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePemasukan = new javax.swing.JTable();
        buttonKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pemasukan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24))); // NOI18N

        jLabel1.setText("Keterangan:");

        jLabel2.setText("Jumlah:");

        jLabel3.setText("Tanggal:");

        jLabel4.setText("Kategori:");

        buttonEdit.setText("Edit");
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });

        buttonTambah.setText("Tambah");
        buttonTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahActionPerformed(evt);
            }
        });

        buttonHapus.setText("Hapus");
        buttonHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapusActionPerformed(evt);
            }
        });

        tablePemasukan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablePemasukan);

        buttonKembali.setText("Kembali");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2))
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFieldKeterangan)
                            .addComponent(textFieldJumlah)
                            .addComponent(dateChooserTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(comboBoxKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonEdit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonKembali)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(textFieldKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textFieldJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(dateChooserTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboBoxKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonTambah)
                            .addComponent(buttonEdit))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonHapus)
                            .addComponent(buttonKembali)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void buttonTambahActionPerformed(java.awt.event.ActionEvent evt) {                                             
        tambahPemasukan();
    }                                            

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {                                           
        editPemasukan();
    }                                          

    private void buttonHapusActionPerformed(java.awt.event.ActionEvent evt) {                                            
        hapusPemasukan();
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPemasukan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPemasukan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonHapus;
    private javax.swing.JButton buttonKembali;
    private javax.swing.JButton buttonTambah;
    private javax.swing.JComboBox<String> comboBoxKategori;
    private com.toedter.calendar.JDateChooser dateChooserTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablePemasukan;
    private javax.swing.JTextField textFieldJumlah;
    private javax.swing.JTextField textFieldKeterangan;
    // End of variables declaration                   
}





- koding FormPengeluaran


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class FormPengeluaran extends javax.swing.JFrame {

    /**
     * Creates new form FormPengeluaran
     */
    public FormPengeluaran() {
        initComponents();
        isiComboBoxKategori();
        tampilkanDataPengeluaran();

        tablePengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePengeluaranMouseClicked(evt);
            }
        });

        buttonKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonKembaliActionPerformed(evt);
            }
        });

    }

    private void tambahPengeluaran() {
        String query = "INSERT INTO pengeluaran (keterangan, jumlah, tanggal, kategori) VALUES (?, ?, ?, ?)";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = null;
        if (dateChooserTanggal.getDate() != null) {
            tanggal = dateFormat.format(dateChooserTanggal.getDate());
        } else {
            JOptionPane.showMessageDialog(this, "Tanggal tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.connect();
                PreparedStatement ps = conn.prepareStatement(query)) {

            String keterangan = textFieldKeterangan.getText().trim();
            if (keterangan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keterangan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ps.setString(1, keterangan);

            try {
                String jumlahText = textFieldJumlah.getText().trim();
                if (jumlahText.isEmpty()) {
                    throw new NumberFormatException("Jumlah tidak boleh kosong");
                }
                BigDecimal jumlah = new BigDecimal(jumlahText);
                ps.setBigDecimal(2, jumlah);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ps.setString(3, tanggal);
            String kategori = comboBoxKategori.getSelectedItem() != null
                    ? comboBoxKategori.getSelectedItem().toString()
                    : null;
            if (kategori == null || kategori.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pilih kategori!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ps.setString(4, kategori);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Pengeluaran berhasil ditambahkan!");

            tampilkanDataPengeluaran(); // Refresh tabel
            clearFields(); // Kosongkan field input setelah berhasil
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menambah data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusPengeluaran() {
        int row = tablePengeluaran.getSelectedRow();
        if (row >= 0) {
            String id = tablePengeluaran.getValueAt(row, 0).toString();
            String query = "DELETE FROM pengeluaran WHERE id = ?";

            try (Connection conn = DatabaseConnection.connect();
                    PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, id);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                tampilkanDataPengeluaran();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editPengeluaran() {
        int row = tablePengeluaran.getSelectedRow();
        if (row >= 0) {
            String id = tablePengeluaran.getValueAt(row, 0).toString();
            String query = "UPDATE pengeluaran SET keterangan = ?, jumlah = ?, tanggal = ?, kategori = ? WHERE id = ?";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal = dateFormat.format(dateChooserTanggal.getDate());

            try (Connection conn = DatabaseConnection.connect();
                    PreparedStatement ps = conn.prepareStatement(query)) {

                String keterangan = textFieldKeterangan.getText().trim();
                if (keterangan.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Keterangan tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ps.setString(1, keterangan);

                try {
                    BigDecimal jumlah = new BigDecimal(textFieldJumlah.getText().trim());
                    ps.setBigDecimal(2, jumlah);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ps.setString(3, tanggal);
                ps.setString(4, comboBoxKategori.getSelectedItem().toString());
                ps.setString(5, id);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
                tampilkanDataPengeluaran();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengubah data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void tampilkanDataPengeluaran() {
        String[] kolom = {"ID", "Keterangan", "Jumlah", "Tanggal", "Kategori"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);

        String query = "SELECT * FROM pengeluaran";

        try (Connection conn = DatabaseConnection.connect();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String keterangan = rs.getString("keterangan");
                String jumlah = rs.getString("jumlah");
                String tanggal = rs.getString("tanggal");
                String kategori = rs.getString("kategori");

                String[] data = {id, keterangan, jumlah, tanggal, kategori};
                model.addRow(data);
            }

            tablePengeluaran.setModel(model); // Set data ke JTable
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void isiComboBoxKategori() {
        comboBoxKategori.addItem("Belanja");
        comboBoxKategori.addItem("Transportasi");
        comboBoxKategori.addItem("Makanan");
        comboBoxKategori.addItem("Hiburan");
        comboBoxKategori.addItem("Lainnya");
    }

    private void clearFields() {
        textFieldKeterangan.setText(""); // Kosongkan field keterangan
        textFieldJumlah.setText("");     // Kosongkan field jumlah
        dateChooserTanggal.setDate(null); // Kosongkan JDateChooser
        comboBoxKategori.setSelectedIndex(0); // Reset kategori ke pilihan pertama
    }

    private void tablePengeluaranMouseClicked(java.awt.event.MouseEvent evt) {
        // Ambil baris yang diklik
        int row = tablePengeluaran.getSelectedRow();

        // Ambil data dari JTable dan set ke field input
        textFieldKeterangan.setText(tablePengeluaran.getValueAt(row, 1).toString());
        textFieldJumlah.setText(tablePengeluaran.getValueAt(row, 2).toString());

        // Parsing tanggal dari tabel ke JDateChooser
        try {
            java.util.Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(tablePengeluaran.getValueAt(row, 3).toString());
            dateChooserTanggal.setDate(tanggal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pilih kategori di ComboBox
        String kategori = tablePengeluaran.getValueAt(row, 4).toString();
        comboBoxKategori.setSelectedItem(kategori);
    }

    private void buttonKembaliActionPerformed(java.awt.event.ActionEvent evt) {
        // Tampilkan MainView
        MainView mainView = new MainView();
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null); // Tampilkan di tengah layar

        // Tutup form saat ini
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textFieldKeterangan = new javax.swing.JTextField();
        textFieldJumlah = new javax.swing.JTextField();
        comboBoxKategori = new javax.swing.JComboBox<>();
        dateChooserTanggal = new com.toedter.calendar.JDateChooser();
        buttonEdit = new javax.swing.JButton();
        buttonTambah = new javax.swing.JButton();
        buttonHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePengeluaran = new javax.swing.JTable();
        buttonKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pengeluaran", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24))); // NOI18N

        jLabel1.setText("Keterangan:");

        jLabel2.setText("Jumlah:");

        jLabel3.setText("Tanggal:");

        jLabel4.setText("Kategori:");

        buttonEdit.setText("Edit");
        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditActionPerformed(evt);
            }
        });

        buttonTambah.setText("Tambah");
        buttonTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTambahActionPerformed(evt);
            }
        });

        buttonHapus.setText("Hapus");
        buttonHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHapusActionPerformed(evt);
            }
        });

        tablePengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablePengeluaran);

        buttonKembali.setText("Kembali");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2))
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFieldKeterangan)
                            .addComponent(textFieldJumlah)
                            .addComponent(dateChooserTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(comboBoxKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonEdit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonKembali)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(textFieldKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textFieldJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(dateChooserTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboBoxKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonTambah)
                            .addComponent(buttonEdit))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonHapus)
                            .addComponent(buttonKembali)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(151, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 744, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(53, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>                        

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {                                           
        editPengeluaran();
    }                                          

    private void buttonHapusActionPerformed(java.awt.event.ActionEvent evt) {                                            
        hapusPengeluaran();
    }                                           

    private void buttonTambahActionPerformed(java.awt.event.ActionEvent evt) {                                             
        tambahPengeluaran();
    }                                            

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPengeluaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPengeluaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonEdit;
    private javax.swing.JButton buttonHapus;
    private javax.swing.JButton buttonKembali;
    private javax.swing.JButton buttonTambah;
    private javax.swing.JComboBox<String> comboBoxKategori;
    private com.toedter.calendar.JDateChooser dateChooserTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablePengeluaran;
    private javax.swing.JTextField textFieldJumlah;
    private javax.swing.JTextField textFieldKeterangan;
    // End of variables declaration                   
}



- koding MainView

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author muham
 */
public class MainView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
        setTitle("Aplikasi Keuangan"); // Set judul form
        setLocationRelativeTo(null); // Tampilkan di tengah layar

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        buttonDompet = new javax.swing.JButton();
        buttonPemasukan = new javax.swing.JButton();
        buttonPengeluaran = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Halaman Utama", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 36))); // NOI18N

        buttonDompet.setText("Dompet Saya");
        buttonDompet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDompetActionPerformed(evt);
            }
        });

        buttonPemasukan.setText("Pemasukan");
        buttonPemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPemasukanActionPerformed(evt);
            }
        });

        buttonPengeluaran.setText("Pengeluaran");
        buttonPengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPengeluaranActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonPemasukan)
                    .addComponent(buttonPengeluaran)
                    .addComponent(buttonDompet))
                .addGap(0, 255, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(buttonPemasukan)
                .addGap(51, 51, 51)
                .addComponent(buttonPengeluaran)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(buttonDompet)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void buttonPemasukanActionPerformed(java.awt.event.ActionEvent evt) {                                                
        FormPemasukan formPemasukan = new FormPemasukan();
        formPemasukan.setVisible(true);
        formPemasukan.setLocationRelativeTo(null);
    }                                               

    private void buttonPengeluaranActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        FormPengeluaran formPengeluaran = new FormPengeluaran();
        formPengeluaran.setVisible(true);
        formPengeluaran.setLocationRelativeTo(null);
    }                                                 

    private void buttonDompetActionPerformed(java.awt.event.ActionEvent evt) {                                             
        FormDompet formDompet = new FormDompet();
        formDompet.setVisible(true);
        formDompet.setLocationRelativeTo(null);
    }                                            

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton buttonDompet;
    private javax.swing.JButton buttonPemasukan;
    private javax.swing.JButton buttonPengeluaran;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration                   
}



```
## PEMBUAT
Ahmad Said 
2210010264 5B



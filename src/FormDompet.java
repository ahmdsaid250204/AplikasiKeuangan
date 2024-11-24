
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRefreshActionPerformed
        tampilkanSaldoDompet();
        tampilkanRiwayatDompet();
    }//GEN-LAST:event_buttonRefreshActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCetakPDF;
    private javax.swing.JButton buttonKembali;
    private javax.swing.JButton buttonRefresh;
    private javax.swing.JComboBox<String> comboBoxFilter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelSaldoDompet;
    private javax.swing.JTable tableDompet;
    // End of variables declaration//GEN-END:variables
}

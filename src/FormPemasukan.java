
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
    }// </editor-fold>//GEN-END:initComponents

    private void buttonTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTambahActionPerformed
        tambahPemasukan();
    }//GEN-LAST:event_buttonTambahActionPerformed

    private void buttonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditActionPerformed
        editPemasukan();
    }//GEN-LAST:event_buttonEditActionPerformed

    private void buttonHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHapusActionPerformed
        hapusPemasukan();
    }//GEN-LAST:event_buttonHapusActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables
}

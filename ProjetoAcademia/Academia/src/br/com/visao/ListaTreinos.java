package br.com.visao;

import br.com.controle.TreinoController;
import br.com.modelo.Treino;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class ListaTreinos extends javax.swing.JFrame {

    private final TreinoController treinoController;

    public ListaTreinos() {
        initComponents();
        treinoController = new TreinoController();
        carregarTabela();
    }

    private void carregarTabela() {
        List<Treino> listaTreinos = treinoController.listarTreinos();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Treino treino : listaTreinos) {
            Object[] row = {
                treino.getId(),
                treino.getTipo(),
                treino.getDuracao(),
                treino.getNivelDificuldade(),
                treino.getDescricao(),
                treino.getObjetivo(),
                treino.getEquipamentos(),
                treino.getDiasSemana()
            };
            model.addRow(row);
        }
    }

    // Método para excluir treino
    private void excluirTreino() {
        int linha = jTable1.getSelectedRow();
        if (linha > -1) {
            Treino treino = treinoController.listarTreinos().get(linha);
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o treino ID " + treino.getId() + "?",
                    "Confirme a exclusão", JOptionPane.YES_NO_OPTION);

            if (opcao == JOptionPane.YES_OPTION) {
                treinoController.excluirTreino(treino.getId());
                JOptionPane.showMessageDialog(this, "Treino excluído com sucesso!");
                carregarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um treino para excluir!");
        }
    }

// Método para atualizar treino
    private void atualizarTreino() {
        int linha = jTable1.getSelectedRow();
        if (linha > -1) {
            Treino treino = treinoController.listarTreinos().get(linha);

            // Solicitar novos valores ao usuário
            String novoTipo = JOptionPane.showInputDialog(this, "Novo tipo:", treino.getTipo());
            int novaDuracao = Integer.parseInt(JOptionPane.showInputDialog(this, "Nova duração (min):", String.valueOf(treino.getDuracao())));
            int novoNivelDificuldade = Integer.parseInt(JOptionPane.showInputDialog(this, "Novo nível de dificuldade:", String.valueOf(treino.getNivelDificuldade())));
            String novaDescricao = JOptionPane.showInputDialog(this, "Nova descrição:", treino.getDescricao());
            String novoObjetivo = JOptionPane.showInputDialog(this, "Novo objetivo:", treino.getObjetivo());
            String novosEquipamentos = JOptionPane.showInputDialog(this, "Novos equipamentos:", treino.getEquipamentos());
            String novosDiasTreino = JOptionPane.showInputDialog(this, "Novos dias de treino:", treino.getDiasSemana());

            // Atualizar os dados do treino
            treino.setTipo(novoTipo);
            treino.setDuracao(String.valueOf(novaDuracao));
            treino.setNivelDificuldade(String.valueOf(novoNivelDificuldade));
            treino.setDescricao(novaDescricao);
            treino.setObjetivo(novoObjetivo);
            treino.setEquipamentos(novosEquipamentos);
            treino.setDiasSemana(novosDiasTreino);

            // Chamar o método para atualizar no banco de dados
            treinoController.atualizarTreino(treino);

            JOptionPane.showMessageDialog(this, "Treino atualizado com sucesso!");
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um treino para editar!");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(121, 165, 95));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tipo", "Duração (min)", "Lv Dificuldade", "Discrição", "Objetivo", "Equipamentos", "Dias de treino"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Deletar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Lista de Treinos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(30, 30, 30)
                        .addComponent(jButton2)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(271, 271, 271))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(16, 16, 16))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        excluirTreino();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        atualizarTreino();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ListaTreinos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaTreinos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaTreinos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaTreinos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaTreinos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

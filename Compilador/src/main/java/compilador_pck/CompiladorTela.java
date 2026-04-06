/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package compilador_pck;

import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Lorenzo
 */
public class CompiladorTela extends javax.swing.JFrame {

    File arquivoAtual = null;

    /**
     * Creates new form NewJFrame
     */
    public CompiladorTela() {
        initComponents();
        editorArea.setBorder(new NumberedBorder());
        msgArea.setEditable(false);

        // atalho Ctrl + N
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(ctrlN, "novo");

        getRootPane().getActionMap()
                .put("novo", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        acaoNovo();
                    }
                });

        // atalho Ctrl + O
        KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(ctrlO, "abrir");

        getRootPane().getActionMap()
                .put("abrir", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        acaoAbrir();
                    }
                });

        // atalho Ctrl + S
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(ctrlS, "salvar");

        getRootPane().getActionMap()
                .put("salvar", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        acaoSalvar();
                    }
                });

        // atalho Ctrl + C
        KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(ctrlC, "copiar");

        getRootPane().getActionMap()
                .put("copiar", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        acaoCopiar();
                    }
                });

        // atalho Ctrl + V
        KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(ctrlV, "colar");

        getRootPane().getActionMap()
                .put("colar", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        acaoColar();
                    }
                });

        //atalho Ctrl + X
        KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(ctrlX, "recortar");

        getRootPane().getActionMap()
                .put("recortar", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        acaoRecortar();
                    }
                });

        // atalho F7
        KeyStroke f7 = KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(f7, "compilar");

        getRootPane().getActionMap()
                .put("compilar", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        acaoCompilar();
                    }
                });

        // atalho F1
        KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(f1, "equipe");

        getRootPane().getActionMap()
                .put("equipe", new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        acaoEquipe();
                    }
                });
    }

    private void acaoNovo() {
        editorArea.setText("");
        msgArea.setText("");
        statusLabel.setText("Nenhum arquivo aberto");

        arquivoAtual = null;
    }

    private void acaoAbrir() {
        JFileChooser chooser = new JFileChooser();

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {

            File arquivoSelecionado = chooser.getSelectedFile();

            try {
                String conteudo = new String(
                        Files.readAllBytes(arquivoSelecionado.toPath())
                );

                editorArea.setText(conteudo);

                msgArea.setText("");

                arquivoAtual = arquivoSelecionado;

                statusLabel.setText(
                        arquivoAtual.getParent() + " / " + arquivoAtual.getName()
                );

            } catch (Exception e) {
                msgArea.setText("Erro ao abrir arquivo");
            }
        }
    }

    private void acaoSalvar() {

        try {

            if (arquivoAtual == null) {

                JFileChooser chooser = new JFileChooser();
                int resultado = chooser.showSaveDialog(this);

                if (resultado == JFileChooser.APPROVE_OPTION) {

                    File arquivoSelecionado = chooser.getSelectedFile();

                    if (!arquivoSelecionado.getName().endsWith(".txt")) {
                        arquivoSelecionado = new File(arquivoSelecionado.getAbsolutePath() + ".txt");
                    }

                    FileWriter writer = new FileWriter(arquivoSelecionado);
                    writer.write(editorArea.getText());
                    writer.close();

                    arquivoAtual = arquivoSelecionado;

                    msgArea.setText("");

                    statusLabel.setText(
                            arquivoAtual.getParent() + " / " + arquivoAtual.getName()
                    );
                }

            } else {

                FileWriter writer = new FileWriter(arquivoAtual);
                writer.write(editorArea.getText());
                writer.close();

                msgArea.setText("");
            }

        } catch (IOException e) {
            msgArea.setText("Erro ao salvar arquivo");
        }
    }

    private void acaoCopiar() {
        editorArea.copy();
    }

    private void acaoColar() {
        editorArea.paste();
    }

    private void acaoRecortar() {
        editorArea.cut();
    }

    private void acaoCompilar() {
        msgArea.setText("compilação de programas ainda não foi implementada");
    }

    private void acaoEquipe() {
        msgArea.setText(
                "Equipe de desenvolvimento:\n"
                + "Henrique Horn Lenzi\n"
                + "João Carlos Krapp\n"
                + "Lorenzo Zoboli"
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        novoBtn = new javax.swing.JButton();
        abrirBtn = new javax.swing.JButton();
        salvarBtn = new javax.swing.JButton();
        copiarBtn = new javax.swing.JButton();
        colarBtn = new javax.swing.JButton();
        recortarBtn = new javax.swing.JButton();
        compilarBtn = new javax.swing.JButton();
        equipeBtn = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        editorScroll = new javax.swing.JScrollPane();
        editorArea = new javax.swing.JTextArea();
        msgScroll = new javax.swing.JScrollPane();
        msgArea = new javax.swing.JTextArea();
        statusBar = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(null);
        setResizable(false);
        setSize(new java.awt.Dimension(1500, 800));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);

        novoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/papel-em-branco.png"))); // NOI18N
        novoBtn.setText("Novo [Ctrl-N]");
        novoBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        novoBtn.setFocusable(false);
        novoBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        novoBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        novoBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        novoBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        novoBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        novoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novoBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(novoBtn);

        abrirBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pasta-aberta.png"))); // NOI18N
        abrirBtn.setText("Abrir [Ctrl-O]");
        abrirBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        abrirBtn.setFocusable(false);
        abrirBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        abrirBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        abrirBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        abrirBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        abrirBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        abrirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(abrirBtn);

        salvarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disquete.png"))); // NOI18N
        salvarBtn.setText("Salvar [Ctrl-S]");
        salvarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        salvarBtn.setFocusable(false);
        salvarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        salvarBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        salvarBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        salvarBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        salvarBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        salvarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(salvarBtn);

        copiarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/copiar-documentos.png"))); // NOI18N
        copiarBtn.setText("Copiar [Ctrl-C]");
        copiarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        copiarBtn.setFocusable(false);
        copiarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        copiarBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        copiarBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        copiarBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        copiarBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        copiarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(copiarBtn);

        colarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prancheta.png"))); // NOI18N
        colarBtn.setText("Colar [Ctrl-V]");
        colarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        colarBtn.setFocusable(false);
        colarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        colarBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        colarBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        colarBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        colarBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        colarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colarBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(colarBtn);

        recortarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tesoura.png"))); // NOI18N
        recortarBtn.setText("Recortar [Ctrl-X]");
        recortarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recortarBtn.setFocusable(false);
        recortarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recortarBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        recortarBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        recortarBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        recortarBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recortarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recortarBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(recortarBtn);

        compilarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/engrenagem.png"))); // NOI18N
        compilarBtn.setText("Compilar [F7]");
        compilarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        compilarBtn.setFocusable(false);
        compilarBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        compilarBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        compilarBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        compilarBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        compilarBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        compilarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(compilarBtn);

        equipeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simbolo-de-informacao.png"))); // NOI18N
        equipeBtn.setText("Equipe [F1]");
        equipeBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        equipeBtn.setFocusable(false);
        equipeBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        equipeBtn.setMaximumSize(new java.awt.Dimension(120, 60));
        equipeBtn.setMinimumSize(new java.awt.Dimension(120, 60));
        equipeBtn.setPreferredSize(new java.awt.Dimension(120, 60));
        equipeBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        equipeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equipeBtnActionPerformed(evt);
            }
        });
        jToolBar1.add(equipeBtn);

        jSplitPane1.setDividerLocation(543);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        editorScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        editorScroll.setToolTipText("");
        editorScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        editorArea.setColumns(20);
        editorArea.setRows(5);
        editorArea.setMaximumSize(null);
        editorArea.setMinimumSize(null);
        editorArea.setPreferredSize(null);
        editorScroll.setViewportView(editorArea);

        jSplitPane1.setTopComponent(editorScroll);

        msgScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        msgScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        msgArea.setColumns(20);
        msgArea.setRows(5);
        msgArea.setMaximumSize(new java.awt.Dimension(1500, 25));
        msgArea.setMinimumSize(new java.awt.Dimension(1500, 25));
        msgArea.setPreferredSize(null);
        msgScroll.setViewportView(msgArea);

        jSplitPane1.setRightComponent(msgScroll);

        statusBar.setBackground(new java.awt.Color(255, 255, 255));

        statusLabel.setText("Nenhum arquivo aberto");

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
            .addComponent(jSplitPane1)
            .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void novoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novoBtnActionPerformed
        acaoNovo();
    }//GEN-LAST:event_novoBtnActionPerformed

    private void abrirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirBtnActionPerformed
        acaoAbrir();
    }//GEN-LAST:event_abrirBtnActionPerformed

    private void salvarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarBtnActionPerformed
        acaoSalvar();
    }//GEN-LAST:event_salvarBtnActionPerformed

    private void copiarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarBtnActionPerformed
        acaoCopiar();
    }//GEN-LAST:event_copiarBtnActionPerformed

    private void colarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colarBtnActionPerformed
        acaoColar();
    }//GEN-LAST:event_colarBtnActionPerformed

    private void recortarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recortarBtnActionPerformed
        acaoRecortar();
    }//GEN-LAST:event_recortarBtnActionPerformed

    private void compilarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarBtnActionPerformed
        acaoCompilar();
    }//GEN-LAST:event_compilarBtnActionPerformed

    private void equipeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equipeBtnActionPerformed
        acaoEquipe();
    }//GEN-LAST:event_equipeBtnActionPerformed

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
            java.util.logging.Logger.getLogger(CompiladorTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompiladorTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompiladorTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompiladorTela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompiladorTela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrirBtn;
    private javax.swing.JButton colarBtn;
    private javax.swing.JButton compilarBtn;
    private javax.swing.JButton copiarBtn;
    private javax.swing.JTextArea editorArea;
    private javax.swing.JScrollPane editorScroll;
    private javax.swing.JButton equipeBtn;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea msgArea;
    private javax.swing.JScrollPane msgScroll;
    private javax.swing.JButton novoBtn;
    private javax.swing.JButton recortarBtn;
    private javax.swing.JButton salvarBtn;
    private javax.swing.JPanel statusBar;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
}

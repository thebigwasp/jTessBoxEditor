/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TiffBoxDialog.java
 *
 * Created on Sep 13, 2011, 8:14:15 PM
 */
package net.sourceforge.tessboxeditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import net.sourceforge.vietpad.components.FontDialog;

public class TiffBoxDialog extends javax.swing.JDialog {

    /** Creates new form TiffBoxDialog */
    public TiffBoxDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Font font = this.jTextArea1.getFont();
        this.jButtonFont.setText(fontDesc(font));

        setLocationRelativeTo(getOwner());

        //  Handle escape key to hide the dialog
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction =
                new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                    }
                };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonFile = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jButtonFont = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabelW = new javax.swing.JLabel();
        jSpinnerW = new javax.swing.JSpinner();
        jLabelH = new javax.swing.JLabel();
        jSpinnerH = new javax.swing.JSpinner();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButtonGenerate = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setTitle("Generate TIFF/Box");
        setMinimumSize(new java.awt.Dimension(550, 400));
        setResizable(false);

        jToolBar1.setRollover(true);

        jButtonFile.setText("File");
        jButtonFile.setToolTipText("Open Text File");
        jButtonFile.setFocusable(false);
        jButtonFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFileActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonFile);
        jToolBar1.add(filler3);

        jButtonFont.setText("Font");
        jButtonFont.setToolTipText("Set Font");
        jButtonFont.setFocusable(false);
        jButtonFont.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonFont.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFontActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonFont);
        jToolBar1.add(filler5);

        jLabelW.setText("Width");
        jLabelW.setToolTipText("Image Width");
        jToolBar1.add(jLabelW);

        jSpinnerW.setPreferredSize(new java.awt.Dimension(55, 20));
        jToolBar1.add(jSpinnerW);

        jLabelH.setText("Height");
        jLabelH.setToolTipText("Image Height");
        jToolBar1.add(jLabelH);

        jSpinnerH.setPreferredSize(new java.awt.Dimension(55, 20));
        jToolBar1.add(jSpinnerH);
        jToolBar1.add(filler4);

        jButtonGenerate.setText("Generate");
        jButtonGenerate.setToolTipText("Generate TIFF/Box");
        jButtonGenerate.setFocusable(false);
        jButtonGenerate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonGenerate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonGenerate);
        jToolBar1.add(filler6);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFileActionPerformed
        if (jFileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        }
    }//GEN-LAST:event_jButtonFileActionPerformed

    private void jButtonFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFontActionPerformed
        FontDialog dlg = new FontDialog((JFrame) this.getParent());
        Font font = this.jTextArea1.getFont();
        dlg.setAttributes(font);
        dlg.setVisible(true);

        if (dlg.succeeded()) {
            font = dlg.getFont();
            this.jTextArea1.setFont(font);
            this.jTextArea1.validate();
            this.jButtonFont.setText(fontDesc(font));
        }
    }//GEN-LAST:event_jButtonFontActionPerformed

    private String fontDesc(Font font) {
        return font.getName() + (font.isBold() ? " Bold" : "") + (font.isItalic() ? " Italic" : "") + " " + font.getSize() + "pt";
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
            java.util.logging.Logger.getLogger(TiffBoxDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TiffBoxDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TiffBoxDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TiffBoxDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                TiffBoxDialog dialog = new TiffBoxDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JButton jButtonFile;
    private javax.swing.JButton jButtonFont;
    private javax.swing.JButton jButtonGenerate;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabelH;
    private javax.swing.JLabel jLabelW;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerH;
    private javax.swing.JSpinner jSpinnerW;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
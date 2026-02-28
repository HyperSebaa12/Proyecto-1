/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaz;

/**
 *
 * @author Hyper
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    Logica.Grafo miGrafo = new Logica.Grafo();
    Logica.ManejadorArchivos manejador = new Logica.ManejadorArchivos();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());

    public VentanaPrincipal() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Cargar Archivo");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Eliminar Proteina");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Calcular Ruta");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane2.setViewportView(jScrollPane1);

        jButton4.setText("Mostrar Red Grafica");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jButton5.setText("Guardar Cambios");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)))
                        .addGap(33, 33, 33)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(324, 324, 324)
                        .addComponent(jButton5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton4)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if (miGrafo.getInicio() != null) {
        int resp = javax.swing.JOptionPane.showConfirmDialog(this, "¿Desea guardar antes de cargar?");
        if (resp == 0) manejador.guardarArchivo(miGrafo);
    }
    manejador.cargarArchivo(miGrafo);
    jTextArea1.setText("Red cargada.\nProteínas: " + miGrafo.getTamaño());
    jTextArea1.append("\nHub Principal: " + miGrafo.obtenerHubPrincipal());
    int complejos = miGrafo.contarComplejosProteicos();
    String hub = miGrafo.obtenerHubPrincipal();
    jTextArea1.append("\n--- ANÁLISIS DE RED ---");
    jTextArea1.append("\nComplejos Proteicos detectados: " + complejos);
    jTextArea1.append("\nDiana Terapéutica Sugerida (Hub): " + hub);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    String nombre = javax.swing.JOptionPane.showInputDialog(this, "Nombre de proteína:");
    if (nombre != null) {
        miGrafo.eliminarProteina(nombre);
        jTextArea1.setText("Proteína eliminada. Total: " + miGrafo.getTamaño());
    }        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    String origen = javax.swing.JOptionPane.showInputDialog(this, "Origen:");
    String destino = javax.swing.JOptionPane.showInputDialog(this, "Destino:");
    if (origen != null && destino != null) {
        String resultado = miGrafo.calcularDijkstra(origen, destino);
        jTextArea1.setText(resultado);

    }//GEN-LAST:event_jButton3ActionPerformed
} 
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        miGrafo.graficarRed();       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        manejador.guardarArchivo(miGrafo);       
    }//GEN-LAST:event_jButton5ActionPerformed


    public static void main(String args[]) {


        java.awt.EventQueue.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}

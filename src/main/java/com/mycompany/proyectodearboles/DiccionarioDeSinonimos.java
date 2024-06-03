/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectodearboles;

import Excepciones.OrdenInvalidoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author EQUIPO
 */

public class DiccionarioDeSinonimos extends javax.swing.JFrame {
    private IArbolBusqueda<String,String> ia;
    
    /**
     * Creates new form DiccionarioDeSinonimos
     * @throws Excepciones.OrdenInvalidoException
     */
    public DiccionarioDeSinonimos() throws OrdenInvalidoException {
        initComponents();
        inicializarArbol();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jPalabraBuscada = new javax.swing.JTextField();
        jTituloBuscar = new javax.swing.JLabel();
        jMenuDeOperacion = new javax.swing.JMenuBar();
        jArchivo = new javax.swing.JMenu();
        jCambiarArbol = new javax.swing.JMenuItem();
        jInsertar = new javax.swing.JMenuItem();
        jBuscarPalabra = new javax.swing.JMenuItem();
        jGuardarCambios = new javax.swing.JMenuItem();
        jCantidadDePalabras = new javax.swing.JMenuItem();
        jAlturaDelArbol = new javax.swing.JMenuItem();
        jNivelDelArbol = new javax.swing.JMenuItem();
        jEliminarPalabra = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jRecorriendoPorNiveles = new javax.swing.JMenuItem();
        recorriendoPorPreOrden = new javax.swing.JMenuItem();
        jRecorriendoPorInOrden = new javax.swing.JMenuItem();
        jRecorridoPostOrden = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));

        jTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jTitulo.setText("Diccionario De Sinónimos");

        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        jPalabraBuscada.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPalabraBuscada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPalabraBuscadaActionPerformed(evt);
            }
        });

        jTituloBuscar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTituloBuscar.setText("Buscar Palabras: ");

        jArchivo.setText("Archivo");

        jCambiarArbol.setText("Cambiar de arbol");
        jCambiarArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCambiarArbolActionPerformed(evt);
            }
        });
        jArchivo.add(jCambiarArbol);

        jInsertar.setText("Insertar una nueva palabra");
        jInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInsertarActionPerformed(evt);
            }
        });
        jArchivo.add(jInsertar);

        jBuscarPalabra.setText("Buscar una palabra");
        jBuscarPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuscarPalabraActionPerformed(evt);
            }
        });
        jArchivo.add(jBuscarPalabra);

        jGuardarCambios.setText("Guardar cambios");
        jGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGuardarCambiosActionPerformed(evt);
            }
        });
        jArchivo.add(jGuardarCambios);

        jCantidadDePalabras.setText("size del arbol");
        jCantidadDePalabras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCantidadDePalabrasActionPerformed(evt);
            }
        });
        jArchivo.add(jCantidadDePalabras);

        jAlturaDelArbol.setText("Altura del arbol");
        jAlturaDelArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAlturaDelArbolActionPerformed(evt);
            }
        });
        jArchivo.add(jAlturaDelArbol);

        jNivelDelArbol.setText("Nivel del arbol");
        jNivelDelArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNivelDelArbolActionPerformed(evt);
            }
        });
        jArchivo.add(jNivelDelArbol);

        jEliminarPalabra.setText("Borrar palabra");
        jEliminarPalabra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEliminarPalabraActionPerformed(evt);
            }
        });
        jArchivo.add(jEliminarPalabra);

        jMenu1.setText("Lista de las palabras del diccionario");

        jRecorriendoPorNiveles.setText("Recorriendo por niveles");
        jRecorriendoPorNiveles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRecorriendoPorNivelesActionPerformed(evt);
            }
        });
        jMenu1.add(jRecorriendoPorNiveles);

        recorriendoPorPreOrden.setText("Recorriendo por PreOrden");
        recorriendoPorPreOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recorriendoPorPreOrdenActionPerformed(evt);
            }
        });
        jMenu1.add(recorriendoPorPreOrden);

        jRecorriendoPorInOrden.setText("Recorriendo por InOrden");
        jRecorriendoPorInOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRecorriendoPorInOrdenActionPerformed(evt);
            }
        });
        jMenu1.add(jRecorriendoPorInOrden);

        jRecorridoPostOrden.setText("Recorriendo por PostOrden");
        jRecorridoPostOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRecorridoPostOrdenActionPerformed(evt);
            }
        });
        jMenu1.add(jRecorridoPostOrden);

        jArchivo.add(jMenu1);

        jMenuItem8.setText("Salir");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jArchivo.add(jMenuItem8);

        jMenuDeOperacion.add(jArchivo);

        setJMenuBar(jMenuDeOperacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTituloBuscar)
                            .addComponent(jPalabraBuscada, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 109, Short.MAX_VALUE)
                .addComponent(jTitulo)
                .addGap(134, 134, 134))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jTitulo)
                .addGap(28, 28, 28)
                .addComponent(jTituloBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPalabraBuscada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCambiarArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCambiarArbolActionPerformed
        try {
            this.inicializarArbol();
        } catch (OrdenInvalidoException ex) {
         JOptionPane.showMessageDialog(null,"orden invalido intente otra vez");  
        }
       // TODO add your handling code here:
    }//GEN-LAST:event_jCambiarArbolActionPerformed

    private void jInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInsertarActionPerformed
        try {
            int cantidadAInsertar=Integer.parseInt(JOptionPane.showInputDialog(
                    "Ingrese la cantidad de palabras que desea insertar:"));
            for (int i = 0; i < cantidadAInsertar; i++) {
                try {
                    String palabraAInsertar = JOptionPane.showInputDialog("Ingrese la  palabra" + 
                            " que desea insertar:");
                    String significado = JOptionPane.showInputDialog("Ingrese el signiicado de" + 
                            " la palabra que desea insertar:");
                     ia.insertar(palabraAInsertar,significado);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Error. No se puede insertar datos " + 
                            "vacíos al arbol.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Error. Número incorrecto.");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jInsertarActionPerformed

    private void recorriendoPorPreOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recorriendoPorPreOrdenActionPerformed
        jTextArea.append("Lista de palabras en PreOrden:\n");
        jTextArea.append(ia.recorridoEnPreOrden()+"\n");
        // TODO add your handling code here:
    }//GEN-LAST:event_recorriendoPorPreOrdenActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jRecorridoPostOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRecorridoPostOrdenActionPerformed
        jTextArea.append("Lista de palabras en PostOrden:\n");
        jTextArea.append(ia.recorridoEnPostOrden()+"\n");
        // TODO add your handling code here:
    }//GEN-LAST:event_jRecorridoPostOrdenActionPerformed

    private void jRecorriendoPorInOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRecorriendoPorInOrdenActionPerformed
        jTextArea.append("Lista de palabras en InOrden:\n");
        jTextArea.append(ia.recorridoEnInOrden()+"\n");
        // TODO add your handling code here:
    }//GEN-LAST:event_jRecorriendoPorInOrdenActionPerformed

    private void jRecorriendoPorNivelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRecorriendoPorNivelesActionPerformed
        jTextArea.append("Lista de palabras por Niveles:\n");
        jTextArea.append(ia.recorridoPorNiveles()+"\n");
        // TODO add your handling code here:
    }//GEN-LAST:event_jRecorriendoPorNivelesActionPerformed

    private void jEliminarPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEliminarPalabraActionPerformed
      try{
        int cantidadAEliminar=Integer.parseInt(JOptionPane.showInputDialog(
                    "Ingrese la cantidad de palabras que desea eliminar:"));
            for (int i = 0; i < cantidadAEliminar; i++) {
             try {
              String palabraAEliminar =
              JOptionPane.showInputDialog(null, "Ingrese la palabra que desea borrar:");
              String significado = ia.eliminar(palabraAEliminar);
              if(significado == null) {
                JOptionPane.showMessageDialog(null,"Error: No se puede eliminar " + 
                        "una palabra que no hay en el diccionario.");
              } else {
                JOptionPane.showMessageDialog(null, "Se eliminó la palabra: " +
                        palabraAEliminar + " y su significado: " + significado);
               }
             } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "ERROR: No se acpetan datos nulos.");  
             }
            }
    } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Error. Número incorrecto.");
        }        
        // TODO add your handling code here:
    }//GEN-LAST:event_jEliminarPalabraActionPerformed

    private void jNivelDelArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNivelDelArbolActionPerformed
        JOptionPane.showMessageDialog(null, "El nivel que tiene el árbol es de: " + ia.nivel());
        // TODO add your handling code here:
    }//GEN-LAST:event_jNivelDelArbolActionPerformed

    private void jAlturaDelArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAlturaDelArbolActionPerformed
        JOptionPane.showMessageDialog(null, "La altura que tiene el árbol es de: " + ia.altura());
        // TODO add your handling code here:
    }//GEN-LAST:event_jAlturaDelArbolActionPerformed

    private void jCantidadDePalabrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCantidadDePalabrasActionPerformed
        JOptionPane.showMessageDialog(null, "La cantidad de palabras " + 
            "que tiene el diccionario es de:" + ia.size());
        // TODO add your handling code here:
    }//GEN-LAST:event_jCantidadDePalabrasActionPerformed

    private void jBuscarPalabraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuscarPalabraActionPerformed
        try {
            String palabraABuscar = JOptionPane.showInputDialog("Ingrese la palabra " + 
                    "que desea buscar");
            String significado = ia.buscar(palabraABuscar);
            if (significado == null) {
                JOptionPane.showMessageDialog(null,"Error no se puede buscar palabras"
                      + "que no esten en el diccionario");
            } else {
                jPalabraBuscada.setText(palabraABuscar);
                jTextArea.removeAll();
                jTextArea.append(significado);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Error. No se admite datos nulos " + 
                    "en el diccionario.");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jBuscarPalabraActionPerformed

    private void jPalabraBuscadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPalabraBuscadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPalabraBuscadaActionPerformed

    private void jGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGuardarCambiosActionPerformed
     List<String>palabras=ia.recorridoEnPreOrden();
     List<String>significado=ia.recorridoDeValoresEnPreOrden();
     Archivo file=new Archivo("hola.txt");
     file.reescribir(palabras,significado);
     JOptionPane.showMessageDialog(null,"se guardo con exito");
        // TODO add your handling code here:
    }//GEN-LAST:event_jGuardarCambiosActionPerformed
    
    private void inicializarArbol() throws OrdenInvalidoException {
        String arbol =JOptionPane.showInputDialog("Ingrese el árbol que desea usar:\n"
        + "ABB : Árbol Binario de Búsqueda\n"+ "AVL : Árbol AVL\n"
        + "AMB : Árbol MVías de Búsqueda\n"+ "AB : Árbol B\n").toUpperCase();
        switch (arbol) {
            case "ABB":
                ia = new ArbolBinarioBusqueda<>();
                this.cargar();
                break;
            case "AVL":
                ia = new AVL();
                this.cargar();
                break;
            case "AMB":
                int orden=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el orden para el árbol mvías"));
                ia = new ArbolMViasBusqueda<>(orden);
                this.cargar();
                break;
            case "AB":
                int ordenB=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el orden para el árbol B"));
                ia = new ArbolB<>(ordenB);
                this.cargar();
                break;
            default:
                ia = new ArbolBinarioBusqueda<>();
                this.cargar();
        }
    }
    
    private void cargar(){
     List<String> palabras= new ArrayList<>();
     List<String> significado =new ArrayList<>();
     Archivo file= new Archivo("hola.txt");
     file.cargarDesdeUnArchivo(palabras,significado);
     for(int i=0;i<palabras.size();i++){
      ia.insertar(palabras.get(i),significado.get(i));
     }
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
            java.util.logging.Logger.getLogger(DiccionarioDeSinonimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiccionarioDeSinonimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiccionarioDeSinonimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiccionarioDeSinonimos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DiccionarioDeSinonimos().setVisible(true);
                } catch (OrdenInvalidoException ex) {
          JOptionPane.showMessageDialog(null,"orden invalido intente otra vez");  
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jAlturaDelArbol;
    private javax.swing.JMenu jArchivo;
    private javax.swing.JMenuItem jBuscarPalabra;
    private javax.swing.JMenuItem jCambiarArbol;
    private javax.swing.JMenuItem jCantidadDePalabras;
    private javax.swing.JMenuItem jEliminarPalabra;
    private javax.swing.JMenuItem jGuardarCambios;
    private javax.swing.JMenuItem jInsertar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuDeOperacion;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jNivelDelArbol;
    private javax.swing.JTextField jPalabraBuscada;
    private javax.swing.JMenuItem jRecorridoPostOrden;
    private javax.swing.JMenuItem jRecorriendoPorInOrden;
    private javax.swing.JMenuItem jRecorriendoPorNiveles;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea;
    private javax.swing.JLabel jTitulo;
    private javax.swing.JLabel jTituloBuscar;
    private javax.swing.JMenuItem recorriendoPorPreOrden;
    // End of variables declaration//GEN-END:variables
}

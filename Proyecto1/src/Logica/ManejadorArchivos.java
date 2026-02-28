/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ManejadorArchivos {


    public void cargarArchivo(Grafo grafo) {
        JFileChooser selector = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV", "csv");
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                int contador = 0;
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue; 
                    
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        String origen = partes[0].trim();
                        String destino = partes[1].trim();
                        try {
                            double peso = Double.parseDouble(partes[2].trim());
                            grafo.agregarInteraccion(origen, destino, peso);
                            contador++;
                        } catch (NumberFormatException e) {
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Grafo cargado exitosamente.\nInteracciones procesadas: " + contador);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void guardarArchivo(Grafo grafo) {
        if (grafo.getInicio() == null) {
            JOptionPane.showMessageDialog(null, "El grafo está vacío, no hay nada que guardar.");
            return;
        }

        JFileChooser selector = new JFileChooser();
        int resultado = selector.showSaveDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".csv")) {
                archivo = new File(archivo.getAbsolutePath() + ".csv");
            }

            try (PrintWriter pw = new PrintWriter(archivo)) {
                Vertice tempV = grafo.getInicio();
                while (tempV != null) {
                    Arista tempA = tempV.listaAdyacencia;
                    while (tempA != null) {
                        
                        pw.println(tempV.nombre + "," + tempA.destino + "," + tempA.peso);
                        tempA = tempA.siguiente;
                    }
                    tempV = tempV.siguiente;
                }
                JOptionPane.showMessageDialog(null, "Repositorio actualizado correctamente en: " + archivo.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
            }
        }
    }
}
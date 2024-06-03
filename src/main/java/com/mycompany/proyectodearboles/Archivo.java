/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectodearboles;
/**
 *
 * @author EQUIPO
*/
import java.io.*;
import java.util.List;

public class Archivo {
    private String nombreArchivo;

    public Archivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    // esto carga lo de los archivos en unas listas instanciadas
    public void cargarDesdeUnArchivo(List<String> palabras, List<String> significado) {
        try {
            // si e el archivo estuviera asi: palabra: significado1, significado2... hasta N
            FileReader lector = new FileReader(this.nombreArchivo);
            BufferedReader lectura = new BufferedReader(lector);
            String datosDelDiccionario;
            datosDelDiccionario = lectura.readLine();
            while(datosDelDiccionario != null) {
                int separador = datosDelDiccionario.indexOf(':');
                palabras.add(datosDelDiccionario.substring(0, separador));
                significado.add(datosDelDiccionario.substring(separador + 1, datosDelDiccionario.length()));
                datosDelDiccionario = lectura.readLine();
            }
        } catch(IOException e) {
            e.printStackTrace(System.out);
        } 
    }

    // esto rescribe si le dan al boton de guardar
    public void reescribir(List<String> palabra, List<String> significado) {
        String palabraAGuardar;
        try {
            palabraAGuardar = palabra.get(0) + ":" + significado.get(0);
            this.escribir(palabraAGuardar);
            FileWriter escritor = new FileWriter(this.nombreArchivo, true);
            for (int i = 1; i < palabra.size(); i++) {
                palabraAGuardar = palabra.get(i) + ":" + significado.get(i);
                escritor.write(palabraAGuardar + "\n");
            }
            escritor.close();
        } catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }

    // esto escribe de nuevo
    public void escribir(String palabra) {
        try {
            FileWriter escritor = new FileWriter(this.nombreArchivo);
            escritor.write(palabra + "\n");
            escritor.close();
        } catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *

 */


public class Vertice {
    public String nombre;
    public Arista listaAdyacencia;
    public Vertice siguiente;

    public Vertice(String nombre) {
        this.nombre = nombre;
        this.listaAdyacencia = null;
        this.siguiente = null;
    }

    public void agregarArista(String destino, double peso) {
        Arista nueva = new Arista(destino, peso);
        nueva.siguiente = listaAdyacencia;
        listaAdyacencia = nueva;
    }


    public int getGrado() {
        int grado = 0;
        Arista temp = listaAdyacencia;
        while (temp != null) {
            grado++;
            temp = temp.siguiente;
        }
        return grado;
    }
    
    public void eliminarArista(String destino) {
        if (listaAdyacencia == null) return;
        if (listaAdyacencia.destino.equals(destino)) {
            listaAdyacencia = listaAdyacencia.siguiente;
        } else {
            Arista actual = listaAdyacencia;
            while (actual.siguiente != null) {
                if (actual.siguiente.destino.equals(destino)) {
                    actual.siguiente = actual.siguiente.siguiente;
                    break;
                }
                actual = actual.siguiente;
            }
        }
    }
}


class Arista {
    public String destino;
    public double peso;
    public Arista siguiente;

    public Arista(String destino, double peso) {
        this.destino = destino;
        this.peso = peso;
        this.siguiente = null;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectodearboles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JTextArea;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */

public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {
    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {
    
    }
    
    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<K> clavesNoInOrden, 
            List<V> valoresInOrden, List<V> valoresNoInOrden, boolean conPostOrden) {
       
        if (clavesInOrden.isEmpty() || clavesNoInOrden.isEmpty() ||
            valoresInOrden.isEmpty()|| valoresNoInOrden.isEmpty()) {
            throw new IllegalArgumentException("ERROR: Las listas no pueden estar vacías.");  
        }
        if (this.contieneClavesNulas(clavesInOrden) ||
                this.contieneClavesNulas(clavesNoInOrden) ||
                this.contieneValoresNulos(valoresInOrden) ||
                this.contieneValoresNulos(valoresNoInOrden)) {
            throw new IllegalArgumentException("ERROR: Las listas no pueden tener valores nulos.");   
        }
        if (clavesInOrden.size() != clavesNoInOrden.size() ||
                clavesInOrden.size() != valoresNoInOrden.size() ||
                clavesNoInOrden.size() != valoresNoInOrden.size()) {
            throw new IllegalArgumentException("ERROR: Las listas deben ser del mismo tamaño.");
        }
        if (conPostOrden) {
            this.raiz = this.construirConPostOrden(
                clavesInOrden, clavesNoInOrden, valoresInOrden, 
            0, clavesInOrden.size() - 1, 0, clavesInOrden.size() - 1);  
        } else {
            this.raiz = this.construirConPreOrden(
                clavesInOrden, clavesNoInOrden, valoresInOrden, 
            0, clavesInOrden.size() - 1, 0, clavesInOrden.size() - 1);  
        }
    }
    
    private boolean contieneClavesNulas(List<K> listaDeClaves) {
        for (K claveDeLaLista: listaDeClaves) {
            if (claveDeLaLista == null) {
                return true;
            }
        }
        return false;
    }
    
    private boolean contieneValoresNulos(List<V> listaDeValores) {
        for (V valorDeLaLista: listaDeValores) {
            if (valorDeLaLista == null){
                return true;  
            }
        }
        return false;
    }
    
    private NodoBinario<K, V> construirConPostOrden(List<K> clavesInOrden, List<K> clavesPostOrden, 
        List<V> valoresPostOrden, int inicioInOrden, int finInOrden, int inicioPostOrden, int finPostOrden) {
        if (inicioInOrden > finInOrden || inicioPostOrden > finPostOrden) {
            return null;
        }
        K claveRaiz = clavesPostOrden.get(finPostOrden);
        V valorRaiz = valoresPostOrden.get(finPostOrden);
        NodoBinario<K, V> nodo = new NodoBinario<>(claveRaiz, valorRaiz);
        int indexRaiz = clavesInOrden.indexOf(claveRaiz);
        int nodosRI = indexRaiz - inicioInOrden;
        nodo.setHijoIzquierdo(construirConPostOrden(clavesInOrden, clavesPostOrden, valoresPostOrden, 
            inicioInOrden, indexRaiz - 1, inicioPostOrden, inicioPostOrden + nodosRI - 1));
        nodo.setHijoDerecho(construirConPostOrden(clavesInOrden, clavesPostOrden, valoresPostOrden, 
            indexRaiz + 1, finInOrden, inicioPostOrden + nodosRI, finPostOrden - 1));
        return nodo;
    }
  
    private NodoBinario<K, V> construirConPreOrden(List<K> clavesInOrden, List<K> clavesPreOrden, 
            List<V> valoresPreOrden, int inicioInOrden, int finInOrden, int inicioPreOrden, int finPreOrden) {
        if (inicioInOrden > finInOrden || inicioPreOrden > finPreOrden) {
            return null;
        }
        K claveRaiz = clavesPreOrden.get(inicioPreOrden);
        V valorRaiz = valoresPreOrden.get(inicioPreOrden);
        NodoBinario<K, V> nodo = new NodoBinario<>(claveRaiz, valorRaiz);
        int indexRaiz = clavesInOrden.indexOf(claveRaiz);
        int nodosRI = indexRaiz - inicioInOrden;
        nodo.setHijoIzquierdo(construirConPreOrden(clavesInOrden, clavesPreOrden, valoresPreOrden, 
                inicioInOrden, indexRaiz - 1, inicioPreOrden + 1, inicioPreOrden + nodosRI));
        nodo.setHijoDerecho(construirConPreOrden(clavesInOrden, clavesPreOrden, valoresPreOrden, 
                indexRaiz + 1, finInOrden, inicioPreOrden + nodosRI + 1, finPreOrden));
        return nodo;
    }
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("La clave no puede ser vacía.");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("El valor no puede ser vacío.");
        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar, valorAInsertar);
            return;
        }
        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        do {
            K claveNodoActual = nodoActual.getClave();
            int comparador = claveAInsertar.compareTo(claveNodoActual);
            nodoAnterior = nodoActual;
            if (comparador < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if (comparador > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valorAInsertar);
                return;
            }
        } while (!NodoBinario.esNodoVacio(nodoActual));
        NodoBinario<K, V> nodoNuevo = new NodoBinario<>(claveAInsertar, valorAInsertar);
        if (claveAInsertar.compareTo(nodoAnterior.getClave()) < 0) {
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        } else {
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }
    }

    @Override
    public V eliminar(K claveAEliminar) {
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            return null;
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }

    protected NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {
        K claveNodoActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveNodoActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzq = eliminar(
                    nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoActual;
        } else if (claveAEliminar.compareTo(nodoActual.getClave()) > 0) {
            NodoBinario<K, V> nuevoHijoDerecho = eliminar(
                nodoActual.getHijoDerecho(), claveAEliminar);
                nodoActual.setHijoDerecho(nuevoHijoDerecho);
            return nodoActual;
        }
        // caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        // caso 2.1
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        // caso 2.2
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        // caso 3
        NodoBinario<K, V> nodoDelSucesor = this.getSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijoDerecho = eliminar(
                    nodoActual.getHijoDerecho(), nodoDelSucesor.getClave());

        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }
 
    protected NodoBinario<K,V> getSucesor(NodoBinario<K,V> nodoActual) {   
        while (!NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())) {
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual;
    }
    
    @Override
    public V buscar(K claveABuscar) {
        if (claveABuscar == null) {
            throw new IllegalArgumentException("La clave no puede ser nula.");
        }
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K, V> nodo = this.raiz;
        do {
            if (claveABuscar.compareTo(nodo.getClave()) == 0) {
                return nodo.getValor();
            } else if (claveABuscar.compareTo(nodo.getClave()) < 0) {
                nodo = nodo.getHijoIzquierdo();
            } else {
                nodo = nodo.getHijoDerecho();
            }
        } while (!NodoBinario.esNodoVacio(nodo));
        return null;
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != null;
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int size = 0;
        Queue<NodoBinario<K, V>> cola = new LinkedList<>();
        cola.offer(this.raiz);
        do {
            NodoBinario<K, V> nodo = cola.poll();
            size++;
            if (!nodo.esVacioHijoIzquierdo()) {
                cola.offer(nodo.getHijoIzquierdo());
            }
            if (!nodo.esVacioHijoDerecho()) {
                cola.offer(nodo.getHijoDerecho());
            }
        } while (!cola.isEmpty());
        return size;
    }

    @Override
    public int altura() {   
        return altura(this.raiz); 
    }
    
    protected int altura(NodoBinario<K,V> nodo){
        if (NodoBinario.esNodoVacio(nodo)) {
            return 0;  
        }   
        int ai = altura(nodo.getHijoIzquierdo());
        int ad = altura(nodo.getHijoDerecho());
        return (ai > ad)? ai + 1 : ad + 1;
    }

    @Override
    public void vaciar(){
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {    
        return nivel(this.raiz);
    }

    protected int nivel(NodoBinario<K,V> nodo) {   
        if (NodoBinario.esNodoVacio(nodo)) {
            return -1;
        }
        int ai = nivel(nodo.getHijoIzquierdo());
        int ad = nivel(nodo.getHijoDerecho());
        return (ai > ad)? ai + 1 : ad + 1;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pila = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        do {
            while (!NodoBinario.esNodoVacio(nodoActual)) {
                pila.push(nodoActual);
                nodoActual = nodoActual.getHijoIzquierdo();
            }
            nodoActual = pila.pop();
            recorrido.add(nodoActual.getClave());
            nodoActual = nodoActual.getHijoDerecho();
        } while (!NodoBinario.esNodoVacio(nodoActual) || !pila.empty());
        return recorrido;
    }
    
    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pila = new Stack<>();
        pila.push(this.raiz);
        do {
            NodoBinario<K, V> nodo = pila.pop();
            recorrido.add(nodo.getClave());
            if (!nodo.esVacioHijoDerecho()) {
                pila.push(nodo.getHijoDerecho());
            }
            if (!nodo.esVacioHijoIzquierdo()) {
                pila.push(nodo.getHijoIzquierdo());
            }
        } while (!pila.empty());
        return recorrido;
    }
    
    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaAux = new Stack<>();
        Stack<NodoBinario<K, V>> pilaRecorrido = new Stack<>();
        pilaAux.push(this.raiz);
        do {
            NodoBinario<K, V> nodo = pilaAux.pop();
            pilaRecorrido.push(nodo);
            if (!nodo.esVacioHijoIzquierdo()) {
                pilaAux.push(nodo.getHijoIzquierdo());
            }
            if (!nodo.esVacioHijoDerecho()) {
                pilaAux.push(nodo.getHijoDerecho());
            }
        } while (!pilaAux.empty());
        do {
            recorrido.add(pilaRecorrido.pop().getClave());
        } while (!pilaRecorrido.empty());
        return recorrido;
    }
    
    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido= new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        do {
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        } while(!colaDeNodos.isEmpty());
        return recorrido;
    }

    //este metodo imprime de manera vertial el arbol  
    public void  imprimir(JTextArea pizarra) {
        // llamamos a un metodo privado  
        imprimir(this.raiz, pizarra);   
    }
    
    // este metodo imprime de manera vertical un nodo
    private void imprimir(NodoBinario<K,V> nodoActual, JTextArea pizarra) {
        //verificamos si el nodoActual esta vacio
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return; //salimos del metodo   
        }
        // verificar si el nodoActual es una hoja
        if (nodoActual.esHoja()) {
            pizarra.append("("+nodoActual.getClave().toString()+")");
            return;
        }
        //ponemos la clave del nodoActual en la pizarra
        pizarra.append("("+(nodoActual.getClave().toString())+")");
        //verificamos si tiene hijo izquierdo
        if (!nodoActual.esVacioHijoIzquierdo()) {
            pizarra.append("       \n |--(I) ");
            //hacemos la llamada recursiva
            this.imprimir(nodoActual.getHijoIzquierdo(), pizarra);
        }
        //verificamos si tiene hijo derecho
        if (!nodoActual.esVacioHijoDerecho()) {
            pizarra.append("      \n |--(D) ");
            // hacemos la llamada recursiva
            this.imprimir(nodoActual.getHijoDerecho(), pizarra);
        }
    }

    @Override
    public List<V> recorridoDeValoresEnPreOrden() {
       List<V> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pila = new Stack<>();
        pila.push(this.raiz);
        do {
            NodoBinario<K, V> nodo = pila.pop();
            recorrido.add(nodo.getValor());
            if (!nodo.esVacioHijoDerecho()) {
                pila.push(nodo.getHijoDerecho());
            }
            if (!nodo.esVacioHijoIzquierdo()) {
                pila.push(nodo.getHijoIzquierdo());
            }
        } while (!pila.empty());
        return recorrido;
    }  
}
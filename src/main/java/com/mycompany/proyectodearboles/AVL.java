/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectodearboles;

import java.util.List;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */

public class AVL <K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K, V> {
    public static final byte RANGO_SUPERIOR = 1;
    public static final byte RANGO_INFERIOR = -1;
   
    //constructor
    public AVL() {
    
    }
    //constructor que se reconstruye con 4 listas 
    public AVL(List<K> listaDeClavesInOrden,List<K> 
                    listaDeClavesNoInOrden,List<V> listaDeValoresInOrden,List<V> 
                    listaDeValoresNoInOrden, boolean conPostOrden){
     super(listaDeClavesInOrden,listaDeClavesNoInOrden,listaDeValoresInOrden, 
                    listaDeValoresNoInOrden,conPostOrden);
    }
    
    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("La clave no puede ser nula.");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("El valor no puede ser nulo.");
        }
        this.raiz = insertar(super.raiz, claveAInsertar, valorAsociado);
    }
    
    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAsociado) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return new NodoBinario<>(claveAInsertar, valorAsociado);
        }
        if (claveAInsertar.compareTo(nodoActual.getClave()) < 0) {
            NodoBinario<K, V> supuestoHI = insertar(nodoActual.getHijoIzquierdo(), 
                    claveAInsertar, valorAsociado);
            nodoActual.setHijoIzquierdo(supuestoHI);
            return balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(nodoActual.getClave()) > 0) {
            NodoBinario<K, V> supuestoHD = insertar(nodoActual.getHijoDerecho(), 
                    claveAInsertar, valorAsociado);
            nodoActual.setHijoDerecho(supuestoHD);
            return balancear(nodoActual);
        }
        nodoActual.setValor(valorAsociado);
        return nodoActual;
    }
    
    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodo) {
        int ai = super.altura(nodo.getHijoIzquierdo());
        int ad = super.altura(nodo.getHijoDerecho());
        if ((ai - ad) > AVL.RANGO_SUPERIOR) {
            NodoBinario<K, V> hijoIzqDelNodoActual = nodo.getHijoIzquierdo();
            ai = super.altura(hijoIzqDelNodoActual.getHijoIzquierdo());
            ad = super.altura(hijoIzqDelNodoActual.getHijoDerecho());
            if (ad > ai) {
                return rotacionDoblePorDerecha(nodo);
            }
            return rotacionSimplePorDerecha(nodo);
        } else if ((ai - ad) < AVL.RANGO_INFERIOR) {
            NodoBinario<K, V> hijoDerDelNodoActual = nodo.getHijoDerecho();
            ai = super.altura(hijoDerDelNodoActual.getHijoIzquierdo());
            ad = super.altura(hijoDerDelNodoActual.getHijoDerecho());
            if (ad < ai) {
                return rotacionDoblePorIzquierda(nodo);
            }
            return rotacionSimplePorIzquierda(nodo);
        }
        return nodo;
    }
    
    private NodoBinario<K, V> rotacionSimplePorDerecha(NodoBinario<K, V> nodo) {
        NodoBinario<K, V> nodoRetornar = nodo.getHijoIzquierdo();
        nodo.setHijoIzquierdo(nodoRetornar.getHijoDerecho());
        nodoRetornar.setHijoDerecho(nodo);
        return nodoRetornar;
    }
    
    private NodoBinario<K, V> rotacionSimplePorIzquierda(NodoBinario<K, V> nodo) {
        NodoBinario<K, V> nodoRetornar = nodo.getHijoDerecho();
        nodo.setHijoDerecho(nodoRetornar.getHijoIzquierdo());
        nodoRetornar.setHijoIzquierdo(nodo);
        return nodoRetornar;
    }
    
    private NodoBinario<K, V> rotacionDoblePorDerecha(NodoBinario<K, V > nodo) {
        nodo.setHijoIzquierdo(rotacionSimplePorIzquierda(nodo.getHijoIzquierdo()));
        return rotacionSimplePorDerecha(nodo);
    }
    
    private NodoBinario<K, V> rotacionDoblePorIzquierda(NodoBinario<K, V> nodo) {
        nodo.setHijoDerecho(rotacionSimplePorDerecha(nodo.getHijoDerecho()));
        return rotacionSimplePorIzquierda(nodo);
    }
  
    @Override
    public V eliminar(K claveAEliminar) {  
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("No se permite clave nulas.");  
        }
        V valorARetornar = this.buscar(claveAEliminar);
        if (valorARetornar == null) {
            throw new IllegalArgumentException("La clave no existe en el arbol.");  
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorARetornar;
    }

    @Override
    protected NodoBinario<K,V> eliminar(NodoBinario<K, V> nodoActual, K claveAEliminar) {    
        K claveDelNodoActual=nodoActual.getClave();
        if (claveAEliminar.compareTo(claveDelNodoActual) > 0) {
            NodoBinario<K,V> supuestoNuevoHijoDerecho = 
                    this.eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveDelNodoActual) < 0) {
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo = 
                    this.eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (nodoActual.esHoja()){
            return NodoBinario.nodoVacio();
        }
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoIzquierdo();
        }
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoDerecho();
        }
        NodoBinario<K,V> nodoSucesor = super.getSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> supuestoNuevoHijoDerecho = 
                this.eliminar(nodoActual.getHijoDerecho(),nodoSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setClave(nodoSucesor.getClave());
        nodoActual.setValor(nodoSucesor.getValor());
        return nodoActual;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyectodearboles;

import Excepciones.OrdenInvalidoException;
import java.util.Stack;

/**
 *
 * @author EQUIPO
 * @param <K>
 * @param <V>
 */

public class ArbolB<K extends Comparable<K>, V> extends ArbolMViasBusqueda<K, V> {
    private final int nroMaxDeClaves;
    private final int nroMinDeClaves;
    private final int nroMinDeHijos;
 
    public ArbolB() {
        super();
        this.nroMaxDeClaves = 2;
        this.nroMinDeClaves = 1;
        this.nroMinDeHijos = 2;
    }

    public ArbolB(int orden) throws OrdenInvalidoException {
        super(orden);
        this.nroMaxDeClaves = orden - 1;
        this.nroMinDeClaves = this.nroMaxDeClaves / 2;
        this.nroMinDeHijos = this.nroMinDeClaves + 1;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Excepcion: no se puede insertar claves nulas.");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("Excepcion: no puede insertar valores nulos.");
        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias(this.orden + 1, claveAInsertar, valorAInsertar);
            return;
        }
        // creamos una pila para guardar los ancestros hasta llegar a la hoja
        Stack<NodoMVias<K, V>> pilaDeAncestros = new Stack<>();
        NodoMVias<K,V> nodoActual=super.raiz;// es para recorrer el arbol
        do {
            // buscamos la posicion de la claveAInsertar si esta o no en el arbol
            int posicionDeLaClaveAInsertar = super.buscarPosicionDeClave(claveAInsertar, nodoActual);
            // verificamos si la claveAInsertar esta o no en el arbol
            if (posicionDeLaClaveAInsertar != ArbolMViasBusqueda.POSICION_INVALIDA) {
                // actualizamos el valor en la posicion que encontramos
                nodoActual = super.buscarNodoParaActualizar(nodoActual, claveAInsertar);
                nodoActual.setValor(posicionDeLaClaveAInsertar, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();
            } else if (nodoActual.esHoja()) {
                // caso contrario la claveAInsertar no esta en el arbol y verificamos que
                // el nodoActual sea una hoja y lo insertamos ordenadamente
                super.insertarClaveYValorOrdenado(nodoActual, claveAInsertar, valorAInsertar);
                // verificamos despues de la incersion si hay execeso de claves
                if (nodoActual.nroDeClavesNoVacias() > this.nroMaxDeClaves) {
                    // si hay llamamos aun metodo auxiliar para dividir
                    this.dividirElNodo(nodoActual,pilaDeAncestros);
                }
            nodoActual = NodoMVias.nodoVacio();
            } else {
                // el nodoActual no es una hoja y ya sabemos que la clave no existe
                // buscamos la posicion por donde bajar
                int posicionPorDondeBajar = super.buscarPosicionDondeBajar(nodoActual, claveAInsertar);
                // y guardamos en la pila el nodoActual antes de cambiar de referencia
                pilaDeAncestros.push(nodoActual);
                // cambamos de referencia´por la del hijo
                nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
            }
        } while (!NodoMVias.esNodoVacio(nodoActual));
        // itera hasta que el nodoActual sea vacio
    }

    // Este es un metodo protegido que la division del nodo que rompe el nro
    // maximo de claves en 2 y sube una clave a su padre
    protected void dividirElNodo(NodoMVias<K, V> nodoActual, Stack<NodoMVias<K, V>> pilaDeAncestros) {
        // aqui hacemos el proceso de partir el nodo
        K claveASubir = nodoActual.getClave(this.nroMinDeClaves);
        V valorASubir = nodoActual.getValor(this.nroMinDeClaves);
        // aqui guardamos en un nodo los datos antes de la clave que subira
        NodoMVias<K, V> nodoAntesDeLaClaveASubir = this.crearNuevoNodo(0, nodoActual, this.nroMinDeClaves);
        // si hubiera un hijo no vacio en la posicion de la clave a subir
        // lo ponemos en en el nodo que tenia datos antes de la clave a subir
        if (!nodoActual.esHijoVacio(this.nroMinDeClaves)) {
            nodoAntesDeLaClaveASubir.setHijo(
                nodoAntesDeLaClaveASubir.nroDeClavesNoVacias(), nodoActual.getHijo(this.nroMinDeClaves));
        }
        // el mismo proceso pero hacemos desde la posicion a subir+1 hasta el
        // nro de claves no vacias
        NodoMVias<K, V> nodoDespuesDeLaClaveASubir = this.crearNuevoNodo(
            this.nroMinDeClaves + 1, nodoActual, nodoActual.nroDeClavesNoVacias());
        // hacemos la misma pregunta del hijo vacio si hubiera en la parte del exceso
        // y lo ponemos en el nodo despues de la clave a subir
        if (!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())) {
            nodoDespuesDeLaClaveASubir.setHijo(
                nodoDespuesDeLaClaveASubir.nroDeClavesNoVacias(), nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
        }
        // revisamos la pila de ancestros si estuviera vacia asumimos que la raiz
        // era la que esta rompiendo la regla y creamos una nueva raiz
        if (pilaDeAncestros.isEmpty()) {
            NodoMVias<K, V> nuevaRaiz= new NodoMVias(this.orden + 1, claveASubir, valorASubir);
            nuevaRaiz.setHijo(0, nodoAntesDeLaClaveASubir);
            nuevaRaiz.setHijo(1, nodoDespuesDeLaClaveASubir);
            super.raiz = nuevaRaiz;
            return;
        }
        // sacamos el nodo de la pila de ancestros y ponemos los nuevos nodos 
        // nodoAntesDeLaClave y nodoDespuesDeLaClave
        NodoMVias<K, V> nodoAncestro = pilaDeAncestros.pop();
        // insertamos de manera ordenada las claves y valores al nodoAnccestros
        super.insertarClaveYValorOrdenado(nodoAncestro, claveASubir, valorASubir);
        K claveDelNodoAntesDeLaClaveASubir = nodoAntesDeLaClaveASubir.getClave(0);
        // una vez que sacamos la clave del nodoAntesDeLaClaveASubir
        // vemos por donde bajar del  nodoAncestro
        int posicionParaInsertarElNodo = super.buscarPosicionDondeBajar(
            nodoAncestro, claveDelNodoAntesDeLaClaveASubir);
        // actualizamos el hijo que era donde habia roto la regla del nro de claves
        nodoAncestro.setHijo(posicionParaInsertarElNodo, nodoAntesDeLaClaveASubir);
        // hacemos lo mismo pero con el otro nodo nodoDespuesDeLaClaveASubir
        K claveDelNodoDespuesDeLaClaveASubir = nodoDespuesDeLaClaveASubir.getClave(0);
        // buscamos la posicion para insertarlo en el nodoAncestro
        posicionParaInsertarElNodo = super.buscarPosicionDondeBajar(
            nodoAncestro, claveDelNodoDespuesDeLaClaveASubir);
        // preguntamos si existe un nodo en esa posicion
        if (!nodoAncestro.esHijoVacio(posicionParaInsertarElNodo)) {
            // llamamos aun metodo privado que recorra todos
            // los hijos desde una posicion y inserte el nodoDespuesDeLaClaveASubir
            this.insertarHijoOrdenado(
                nodoAncestro, posicionParaInsertarElNodo, nodoDespuesDeLaClaveASubir);
        } else {
            // si no hubiera hijos en esa posicion lo insertamos tranquilamente
            nodoAncestro.setHijo(posicionParaInsertarElNodo, nodoDespuesDeLaClaveASubir);
        }
        // verificamos si el nodoAncestro esta rompiendo la regla de nro maximo de claves
        if (nodoAncestro.nroDeClavesNoVacias() > this.nroMaxDeClaves) {
            // hacemos una llamada recursiva
            this.dividirElNodo(nodoAncestro, pilaDeAncestros);
        }
    }

    // este metodo privado crea un nuevo nodo apartir de un nodo y
    // desde que poscion hasta que posicion
    private NodoMVias<K, V> crearNuevoNodo(int posicionInicial, NodoMVias<K,V> nodoActual, int posicionFinal) {
        // crea un nuevo nodo
        NodoMVias<K, V> nodoARetornar = new NodoMVias(this.orden + 1);
        // hacemos un for desde la posicion inicial hasta la posicion final
        for (int i = posicionInicial; i < posicionFinal; i++) {
            // sacamos las claves y valores que esten en esa posicion
            K claveDelNodoActual = nodoActual.getClave(i);
            V valorDeLaClaveActual = nodoActual.getValor(i);
            // y la vamos nsertando ordenamente en el nuevo nodo
            super.insertarClaveYValorOrdenado(nodoARetornar, claveDelNodoActual, valorDeLaClaveActual);
            // preguntamos si tiene hijos diferentes de vacio y los copia(inserta) al nuevo nodo
            if (!nodoActual.esHijoVacio(i)) {
                NodoMVias<K, V> hijoDelNodoActual = nodoActual.getHijo(i);
                K claveDelHijo = hijoDelNodoActual.getClave(0);
                int posicionParaInsertarHijo = super.buscarPosicionDondeBajar(nodoARetornar, claveDelHijo);
                nodoARetornar.setHijo(posicionParaInsertarHijo, hijoDelNodoActual);
            }
        }
        return nodoARetornar;
    }

    // este metodo privado inserta(los mueve de posicion)los hijos para 
    // insertar u nuevo hijo en una posicion especifica
    private void insertarHijoOrdenado(NodoMVias<K, V> nodoActual, int posicionAInsertar, NodoMVias<K, V> nodoAInsertar) {
        // hacemos un for desde el nro de claves no vacias del nodoActual hasta que
        // deje de ser mayor a la posicionAInsertar
        for (int i = nodoActual.nroDeClavesNoVacias(); i > posicionAInsertar; i--) {
            // guardamos en una variable el hijo de la posicion i-1
            NodoMVias<K, V> hijoDelNodoActual = nodoActual.getHijo(i - 1);
            if (!NodoMVias.esNodoVacio(hijoDelNodoActual)) {
                K claveDelHijoDelNodoActual = hijoDelNodoActual.getClave(
                    hijoDelNodoActual.nroDeClavesNoVacias() - 1);
                // sacamos la clave del hijo que guardamos para buscarPosicionDondeBajar
                int posicionParaInsertar = super.buscarPosicionDondeBajar(nodoActual, claveDelHijoDelNodoActual);
                // y ponemos el hijo guardado en la nueva posicion
                nodoActual.setHijo(posicionParaInsertar, hijoDelNodoActual);
            }
        } // fin del for
        // una vez terminado el for podemos insertar el nodo en la posicion especifica
        nodoActual.setHijo(posicionAInsertar, nodoAInsertar);
    }
  
    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("Excepcion: no puede insertar claves nulas.");
        }
        Stack<NodoMVias<K, V>> pilaDeAncestros = new Stack<>();
        NodoMVias<K, V> nodoDeLaClaveAEliminar = this.buscarNodoDeLaClave(claveAEliminar, pilaDeAncestros);
        if (NodoMVias.esNodoVacio(nodoDeLaClaveAEliminar)) {
            return null;  
        }
        int posicionDeClaveAEliminar = super.buscarPosicionDeClave(claveAEliminar, nodoDeLaClaveAEliminar);
        V valorARetornar = nodoDeLaClaveAEliminar.getValor(posicionDeClaveAEliminar);
        if (nodoDeLaClaveAEliminar.esHoja()) {
            super.eliminarClaveDeNodoDePosicion(nodoDeLaClaveAEliminar, posicionDeClaveAEliminar);
            if (nodoDeLaClaveAEliminar.nroDeClavesNoVacias() < this.nroMinDeClaves) {
                if (pilaDeAncestros.isEmpty()) {
                    if (nodoDeLaClaveAEliminar.nroDeClavesNoVacias() == 0) {
                        super.vaciar();
                    }  
                } else {
                    this.prestarOFusionar(nodoDeLaClaveAEliminar, pilaDeAncestros);
                }  
            }  
        } else {
            pilaDeAncestros.push(nodoDeLaClaveAEliminar);
            NodoMVias<K, V> nodoDelPredecesor = this.obtenerNodoDePredecesor(
                pilaDeAncestros, nodoDeLaClaveAEliminar.getHijo(posicionDeClaveAEliminar));
            K claveDelPredecesor = nodoDelPredecesor.getClave(nodoDelPredecesor.nroDeClavesNoVacias() - 1);
            V valorDelPredecesor = nodoDelPredecesor.getValor(nodoDelPredecesor.nroDeClavesNoVacias() - 1);
            super.eliminarClaveDeNodoDePosicion(nodoDelPredecesor, nodoDelPredecesor.nroDeClavesNoVacias() - 1);
            nodoDeLaClaveAEliminar.setClave(posicionDeClaveAEliminar, claveDelPredecesor);
            nodoDeLaClaveAEliminar.setValor(posicionDeClaveAEliminar, valorDelPredecesor);
            if (nodoDelPredecesor.nroDeClavesNoVacias() < this.nroMinDeClaves) {
                this.prestarOFusionar(nodoDelPredecesor, pilaDeAncestros);
            }
        }
        return valorARetornar;
    }

    // este metodo privado busca el nodo donde esta la claveAEliminar y en trayecto 
    // la pila se ira llenando hasta que lo encuentre
    private NodoMVias<K, V> buscarNodoDeLaClave(K claveAEliminar, Stack<NodoMVias<K, V>> pilaDeAncestros) {
    // usamos el contiene para verificar si esta la clave antes de buscar
        if (super.contiene(claveAEliminar)) {
            NodoMVias<K, V> nodoActual = super.raiz;
            // aplicamos la misma logica del buscar simplemente añadiendo la pila
            do {
                boolean cambiarNodo = false; // esto es para saber si estamos cambiando nodo
                // hacemos un for de todas las claves que tiene el nodoActual 
                // y si no cambio de nodo 
                for (int i = 0; i < nodoActual.nroDeClavesNoVacias() && !cambiarNodo; i++) {
                    K claveDelNodoActual=nodoActual.getClave(i);
                    // comparamos la claveABuscar con la claveDelNodoActual son iguales
                    if (claveAEliminar.compareTo(claveDelNodoActual) == 0) {
                        // retornamos el nodo que tiene la dicha clave
                        return nodoActual;
                    }
                    // si la claveAEliminar es menor a la claveDelNodoActual
                    if (claveAEliminar.compareTo(claveDelNodoActual) < 0) {
                        // y decimos que cambiamos de nodos y metemos a la pila antes de cambiar
                        cambiarNodo = true;
                        pilaDeAncestros.push(nodoActual);
                        nodoActual = nodoActual.getHijo(i);
                    }
                } // necesiita de este bucle para procesar un nodo
                // saliendo del for revisamos si no cambio de nodo
                if (!cambiarNodo) {
                    pilaDeAncestros.push(nodoActual);
                    // nos movemos al hijo en la posicion del nroDeClavesNoVacias
                    nodoActual=nodoActual.getHijo(nodoActual.nroDeClavesNoVacias());
                }
                // repetir hasta que encuentre el valor o  el nodoActual sea nodoVacio
            } while (!NodoMVias.esNodoVacio(nodoActual));
        }
        return NodoMVias.nodoVacio();
    }

    // este metodo privado busca el predecesor inorden mientras lo busca va
    // poniendo los nodos en la pila de ancestros
    private NodoMVias<K, V> obtenerNodoDePredecesor(Stack<NodoMVias<K, V>> pilaDeAncestros, NodoMVias<K, V> nodoActual) {
        NodoMVias<K, V> hijoDelNodoActual = nodoActual;
        // iteramos hasta que el hijoDelNodoActual sea nodo vacio
        while (!NodoMVias.esNodoVacio(hijoDelNodoActual)) {
            // en el proceso vamos metiendo en la pila el hijoDelNodoActual
            pilaDeAncestros.push(hijoDelNodoActual);
            // actualizamos el hijoDelNodoActual
            hijoDelNodoActual = hijoDelNodoActual.getHijo(hijoDelNodoActual.nroDeClavesNoVacias());
        }
        return pilaDeAncestros.pop();
    }

    // este metodo privado ve que operacion debe realizare si prestamos o fusion
    private void prestarOFusionar(NodoMVias<K, V> nodoDeLaClaveAEliminar, Stack<NodoMVias<K, V>> pilaDeAncestros) {
        if (!pilaDeAncestros.isEmpty()) {
            NodoMVias<K, V> nodoPadre = pilaDeAncestros.pop();
            // buscamos la posicion de su hijo que es el que esta rompiendo las reglas
            int posicionDelNodoDeLaClaveAEliminar = this.buscarPosicionDelHijo(nodoPadre,nodoDeLaClaveAEliminar);
            // 1) si el hijo que rompe las reglas es el del inicio
            // 2) si el hijo que rompe las reglas es el del medio
            // 3) si el hijo que rompe las reglas es el ultimo que tiene el padre
     
            // verificamos si es el primer caso 
            if (super.hayHijosMasAdelanteDeLaPosicion(nodoPadre, posicionDelNodoDeLaClaveAEliminar) && 
                    posicionDelNodoDeLaClaveAEliminar == 0) {
                // calculamos la posicion de su hermano y lo obtenemos en una variable 
                int posicionDelHijoQuePrestar = posicionDelNodoDeLaClaveAEliminar + 1;
                NodoMVias<K, V> nodoQuePresta = nodoPadre.getHijo(posicionDelHijoQuePrestar);
                // verificamos si le quitamos una clave no rompe las reglas del nro de claves
                if (nodoQuePresta.nroDeClavesNoVacias() - 1 >= this.nroMinDeClaves) {
                    // llamamos a prestar
                    boolean hermanoQuePresta = true;
                    this.prestar(nodoPadre, nodoDeLaClaveAEliminar, nodoQuePresta, 
                        posicionDelNodoDeLaClaveAEliminar, posicionDelHijoQuePrestar, hermanoQuePresta);
                } else {
                    // llamamos a fusion
                    this.fusionarse(nodoPadre,nodoDeLaClaveAEliminar, posicionDelNodoDeLaClaveAEliminar, 
                        posicionDelHijoQuePrestar, nodoQuePresta);
                    // despues de la fusion preguntamos al padre si rompio las reglas 
                    if (nodoPadre.nroDeClavesNoVacias() < this.nroMinDeClaves) {
                       //volvemos a llamar al metodo  
                       this.prestarOFusionar(nodoPadre, pilaDeAncestros);
                    } 
                }
            // si no fue el primer caso verificamos si es el segundo caso
            } else if (super.hayHijosMasAdelanteDeLaPosicion(nodoPadre, posicionDelNodoDeLaClaveAEliminar) && 
                    !nodoPadre.esHijoVacio(posicionDelNodoDeLaClaveAEliminar - 1)) {
                // calculamos la posicion de su hermano de adelante y lo obtenemos en una variable
                int posicionDelHermanoDeAdelante = posicionDelNodoDeLaClaveAEliminar + 1;
                NodoMVias<K, V> hermanoDeAdealante = nodoPadre.getHijo(posicionDelHermanoDeAdelante);
                // verificamos si le quitamos una clave no rompe las reglas del nro de claves
                if (hermanoDeAdealante.nroDeClavesNoVacias() - 1 >= this.nroMinDeClaves) {
                    boolean hermanoQuePresta = true;
                    this.prestar(nodoPadre, nodoDeLaClaveAEliminar, hermanoDeAdealante,
                             posicionDelNodoDeLaClaveAEliminar, posicionDelHermanoDeAdelante, hermanoQuePresta);
                } else {
                    // hacemos las mismas operaciones pero ahora con el hermano de atras
                    int posicionDelHermanoDeAtras = posicionDelNodoDeLaClaveAEliminar - 1;
                    NodoMVias<K, V> hermanoDeAtras = nodoPadre.getHijo(posicionDelHermanoDeAtras);
                    if (hermanoDeAtras.nroDeClavesNoVacias() - 1 >= this.nroMinDeClaves) {   
                        // llamamos a prestar
                        boolean hermanoQuePresta = false;
                        this.prestar(nodoPadre, nodoDeLaClaveAEliminar, hermanoDeAtras,
                            posicionDelNodoDeLaClaveAEliminar, posicionDelHermanoDeAtras, hermanoQuePresta);     
                    } else {
                        // caso que no se pudo prestar de ninguno llamamos a fusion y lo hace
                        // con el hermano de adelante
                        this.fusionarse(nodoPadre, nodoDeLaClaveAEliminar, posicionDelNodoDeLaClaveAEliminar, 
                            posicionDelHermanoDeAdelante, hermanoDeAdealante);
                        // verificamos si el padre no rompe la regla del nro de claves
                        if (nodoPadre.nroDeClavesNoVacias() < this.nroMinDeClaves) {
                            //volvems a llamar al metodo
                            this.prestarOFusionar(nodoPadre,pilaDeAncestros);
                        }
                    }
                }
            // si no fue ni el primer caso ni el segundo caso entonces es el ultimo
            } else if (!super.hayHijosMasAdelanteDeLaPosicion(nodoPadre, posicionDelNodoDeLaClaveAEliminar) && 
                        !nodoPadre.esHijoVacio(posicionDelNodoDeLaClaveAEliminar - 1)) {
                // calculamos la posicion de su hermano de atras y lo obtenemos en una variable
                int posicionDelHijoQuePrestar = posicionDelNodoDeLaClaveAEliminar - 1;
                NodoMVias<K, V> nodoQuePresta = nodoPadre.getHijo(posicionDelHijoQuePrestar);
                // verificamos si le quitamos una clave no rompe las reglas del nro de claves
                if (nodoQuePresta.nroDeClavesNoVacias() - 1>= this.nroMinDeClaves) {
                // llamamos a prestar
                boolean hermanoQuePresta = false;
                this.prestar(nodoPadre, nodoDeLaClaveAEliminar, nodoQuePresta, posicionDelNodoDeLaClaveAEliminar,
                    posicionDelHijoQuePrestar, hermanoQuePresta);
                } else {
                    /*llamamos a fusion*/
                    this.fusionarse(nodoPadre, nodoDeLaClaveAEliminar, posicionDelNodoDeLaClaveAEliminar,
                        posicionDelHijoQuePrestar, nodoQuePresta);
                    // verificamos si el padre despues de la fusion no rompe la regla del nromindeclaves
                    if (nodoPadre.nroDeClavesNoVacias() < this.nroMinDeClaves) {
                        // en caso si llama al metodo prestarOFusionar
                        this.prestarOFusionar(nodoPadre, pilaDeAncestros);
                    }
                }
            }
        // en el caso que la pila este vacia significa que llegamos a la raiz 
        // pero verificamos si la raiz tiene 0 claves
        } else if (super.raiz.nroDeClavesNoVacias() < 1) {
            // si es menor a uno entonces su hijo pasa a ser la nueva raiz 
            NodoMVias<K, V> nuevaRaiz = super.raiz.getHijo(super.raiz.nroDeClavesNoVacias());
            super.raiz = nuevaRaiz;
        }
        //si tiene uno no pasa nada ya que la raiz puede romper las reglas del minimo de claves pero debe tener dos hijos 
    }

    // este metodo privado busca la posicion del hijo de un nodo
    private int buscarPosicionDelHijo(NodoMVias<K, V> nodoPadre, NodoMVias<K, V> nodoHijo) {
        for (int i = 0; i < nodoPadre.nroDeClavesNoVacias(); i++) {
            NodoMVias<K, V> hijoDelPadre = nodoPadre.getHijo(i);
            if (hijoDelPadre.nroDeClavesNoVacias() == nodoHijo.nroDeClavesNoVacias()) {
                return i;
            }
        }
        return nodoPadre.nroDeClavesNoVacias();
    }

    // este metodo privado hace el prestar que requiere el eliminar 
    private void prestar(NodoMVias<K, V> nodoPadre, NodoMVias<K, V> nodoQueDebePrestarse, NodoMVias<K, V> nodoQuePresta, 
        int posicionDeNodoQueDebePrestarse,int posicionDeNodoQuePresta, boolean prestarse) {
        // vemos si tiene que prestarse del hermano de adelante o de hermano de atras
        if (prestarse) {
            // en claso que fuera el hermano de adelante sacamos el primer dato del
            // hermano y lo borramos del nodoHermano y lo insertamos al padre
            K claveDelNodoQuePresta = nodoQuePresta.getClave(0);
            V valorDelNodoQuePresta = nodoQuePresta.getValor(0);
            super.eliminarClaveDeNodoDePosicion(nodoQuePresta, 0);
            // luego lo insertamos en el nodoQueDebePrestarse
            super.insertarClaveYValorOrdenado(nodoPadre, claveDelNodoQuePresta, valorDelNodoQuePresta);
            // sacamos el dato del padre en la posicion del nodoQueDebePresta
            // lo borramos y lo insertamos en el nodoQueDebePrestarse
            K claveDelPadre = nodoPadre.getClave(posicionDeNodoQueDebePrestarse);
            V valorDelPadre = nodoPadre.getValor(posicionDeNodoQueDebePrestarse);
            super.eliminarClaveDeNodoDePosicion(nodoPadre, posicionDeNodoQueDebePrestarse);
            super.insertarClaveYValorOrdenado(nodoQueDebePrestarse, claveDelPadre, valorDelPadre);
            // en el caso que no fuera una hoja el nodo que presta este le da tambien su hijo al que presta
            if (!nodoQuePresta.esHoja()) {
                // obtenemos el hijo que sera el de la posicion 0   
                NodoMVias<K, V> hijoDelNodoQuePresta = nodoQuePresta.getHijo(0);
                //obtenemos su clave del hijo
                K claveDelHijoDelNodoQuePresta = hijoDelNodoQuePresta.getClave(0);
                // y vemos en que posicion lo va a insertar ese nuevo hijo
                int posicionAInsertar=super.buscarPosicionDondeBajar(nodoQueDebePrestarse, claveDelHijoDelNodoQuePresta);
                // llamamos aun metodo que inserta ordenado un hijo que le demos desde una posicion 
                this.insertarHijoOrdenado(nodoQueDebePrestarse, posicionAInsertar, hijoDelNodoQuePresta);
                //mientras en el nodo que presto su clave debe mover sus hijos a sus nuevas posiciones
                if (super.hayHijosMasAdelanteDeLaPosicion(nodoQuePresta, 0)) {
                    // hacemos un ciclo para ir moviendo a los hijos
                    for (int i = 0; i < nodoQuePresta.nroDeClavesNoVacias() + 1; i++) {
                        NodoMVias<K, V> hijosMasAdelante =nodoQuePresta.getHijo(i + 1);
                        this.insertarHijoOrdenado(nodoQuePresta, i, hijosMasAdelante);
                    }
                    //al final el ultimo hijo lo ponemos vacio ya que fue copiado una posicion
                    nodoQuePresta.setHijo(nodoQuePresta.nroDeClavesNoVacias() + 1, NodoMVias.nodoVacio());
                }
            }
        } else {
            // en claso que no fuera el hermano de adelante sacamos el ultimo dato del 
            // hermano y lo borramos del nodoHermano y lo insertamos al padre
            K claveDelNodoQuePresta = nodoQuePresta.getClave(nodoQuePresta.nroDeClavesNoVacias() - 1);
            V valorDelNodoQuePresta = nodoQuePresta.getValor(nodoQuePresta.nroDeClavesNoVacias() - 1);
            super.eliminarClaveDeNodoDePosicion(nodoQuePresta, nodoQuePresta.nroDeClavesNoVacias() - 1);
            super.insertarClaveYValorOrdenado(nodoPadre, claveDelNodoQuePresta, valorDelNodoQuePresta); 
            // sacamos el dato del padre en la posicion del nodoQueDebePrestarse 
            // lo borramos y lo insertamos en el nodoQueDebePrestarse
            K claveDelPadre = nodoPadre.getClave(posicionDeNodoQueDebePrestarse);
            V valorDelPadre = nodoPadre.getValor(posicionDeNodoQueDebePrestarse);
            super.eliminarClaveDeNodoDePosicion(nodoPadre,posicionDeNodoQueDebePrestarse);
            super.insertarClaveYValorOrdenado(nodoQueDebePrestarse, claveDelPadre,valorDelPadre);
            // en el caso que no fuera una hoja el nodo que presta este le da tambien su hijo al que presta
            if (!nodoQuePresta.esHoja()) {
                //en el caso del hermano de atras seria su ultimo hijo
                NodoMVias<K, V> hijoDelNodoQuePresta= nodoQuePresta.getHijo(nodoQuePresta.nroDeClavesNoVacias() + 1);
                //aqui obtenemos su clave pued ser la ultima como la primera van a obtener la misma posicion
                K claveDelHijoDelNodoQuePresta=hijoDelNodoQuePresta.getClave(0);
                //obtenermos la posicion donde seran movidos
                int posicionAInsertar = super.buscarPosicionDondeBajar(nodoQueDebePrestarse, claveDelHijoDelNodoQuePresta);
                //llamamos aun metodo que inserta ordenado aun hijo desde una posicion
                this.insertarHijoOrdenado(nodoQueDebePrestarse, posicionAInsertar, hijoDelNodoQuePresta);
                //y al ser ultimo hijo solo ponemos vacio en esa posicion en el nodoQuePresta
                nodoQuePresta.setHijo(nodoQuePresta.nroDeClavesNoVacias()+1,NodoMVias.nodoVacio());
            }
        }
    }

    // este metodo privado hace la fusion para el eliminar
    private void fusionarse(NodoMVias<K, V> nodoPadre, NodoMVias<K, V> nodoQueDebeFusionarse, 
            int posicionDeNodoQueDebeFusionarse, int posicionDeNodoHermano,NodoMVias<K,V>nodoHermano) {
        // hacemos un for para copiar los datos del hermano al nodoQueDebeFusionarse
        for (int i = 0; i < nodoHermano.nroDeClavesNoVacias(); i++) {
            K claveDelHermano=nodoHermano.getClave(i);
            V valorDelHermano=nodoHermano.getValor(i);
            super.insertarClaveYValorOrdenado(nodoQueDebeFusionarse, claveDelHermano, valorDelHermano);
        }
        // ahora verificamos si hay clave en la posicionDeNodoQueDebeFusionarse y copiamos los datos del padre
        if (nodoPadre.getClave(posicionDeNodoQueDebeFusionarse) != (K) NodoMVias.datoVacio()) {
            K claveDelPadre = nodoPadre.getClave(posicionDeNodoQueDebeFusionarse);
            V valorDelPadre = nodoPadre.getValor(posicionDeNodoQueDebeFusionarse);
            super.insertarClaveYValorOrdenado(nodoQueDebeFusionarse, claveDelPadre, valorDelPadre);
            // una vez copiado los datos los borramos del nodoPadre
            super.eliminarClaveDeNodoDePosicion(nodoPadre, posicionDeNodoQueDebeFusionarse);
        } else {
            // si no le restamos 1 a la posicion para tener sus datos
            K claveDelPadre = nodoPadre.getClave(posicionDeNodoQueDebeFusionarse - 1);
            V valorDelPadre = nodoPadre.getValor(posicionDeNodoQueDebeFusionarse - 1);
            super.insertarClaveYValorOrdenado(nodoQueDebeFusionarse, claveDelPadre, valorDelPadre);
            // una vez copiado los datos los borramos del nodoPadre
            super.eliminarClaveDeNodoDePosicion(nodoPadre, posicionDeNodoQueDebeFusionarse - 1);  
        }
        // vemos si el nodoHermano no es una hoja
        if (!nodoHermano.esHoja()) {
            // si no es hacemos un for y vamos viendo sus hijos
            for (int i = 0; i <= nodoHermano.nroDeClavesNoVacias(); i++) {
                // verificamos si el hijo del nodoHermano no esta vacio
                if (!nodoHermano.esHijoVacio(i)) {
                    // sacamos su hijo y vemos en donde inserarlo en el nodoQueDebeFusionarse
                    NodoMVias<K, V> hijoDelNodoHermano = nodoHermano.getHijo(i);
                    K claveDelHijoDelNodoHermano = hijoDelNodoHermano.getClave(0);
                    int posicionAInsertarNuevoHijo = super.buscarPosicionDondeBajar(
                            nodoQueDebeFusionarse, claveDelHijoDelNodoHermano);
                    if (nodoQueDebeFusionarse.esHijoVacio(posicionAInsertarNuevoHijo)) {
                        nodoQueDebeFusionarse.setHijo(posicionAInsertarNuevoHijo, hijoDelNodoHermano);
                    } else {
                        this.insertarHijoOrdenado(nodoQueDebeFusionarse, posicionAInsertarNuevoHijo, hijoDelNodoHermano);
                    }
                }
            }
        }
        /*verificamo si mi hermano es de la anterior posicion o siguiente*/
        if (posicionDeNodoHermano < posicionDeNodoQueDebeFusionarse) {
            // en caso de que su hermano sea de la posicion anterior el nodoquefusiona
            // lo movemos donde su hermano y en su antigua posicion ponemos un nodVacio
            nodoPadre.setHijo(posicionDeNodoHermano, nodoQueDebeFusionarse);
            nodoPadre.setHijo(posicionDeNodoQueDebeFusionarse, NodoMVias.nodoVacio());
        } else {
            // caso contrario el hermano era de la posicion siguiente y vemos si hay hermaos
            if (super.hayHijosMasAdelanteDeLaPosicion(nodoPadre, posicionDeNodoHermano)) {
                for (int i = posicionDeNodoHermano; i < nodoPadre.nroDeClavesNoVacias() + 1; i++) {
                    NodoMVias<K, V> hijosMasAdelante = nodoPadre.getHijo(i + 1);
                    this.insertarHijoOrdenado(nodoPadre, i, hijosMasAdelante);
                }
                nodoPadre.setHijo(nodoPadre.nroDeClavesNoVacias() + 1, NodoMVias.nodoVacio());
            } else {
                nodoPadre.setHijo(posicionDeNodoHermano, NodoMVias.nodoVacio());
            }
        }
    }
}
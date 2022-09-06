/*@author Juan Mesquida Arenas y Joan Martorell Ferriol*/
package practicafinal;

import java.io.Serializable;

/**
 *
 * AQUÍ INTRODUCIREMOS LOS GETTERS Y SETTERS DE NUESTRAS VARIABLES PRIVADAS
 *
 */
public class Casilla implements Serializable {

    private int ancho = 9;                    //numero casillas del tablero de ancho
    private int alto = 9;                     //numero casillas del tablero de alto
    private int numeroBombas = 10;            //numero de bombas

//************************************************************************************************************************************************
    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getNumeroBombas() {
        return numeroBombas;
    }

    public void setNumeroBombas(int numeroBombas) {
        this.numeroBombas = numeroBombas;
    }

}

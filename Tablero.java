/*@author Juan Mesquida Arenas y Joan Martorell Ferriol*/
package practicafinal;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Random;
import javax.swing.*;

/*
 * 
 * AQUÍ INTRODUCIREMOS CLASE TABLERO, MÉTODO PINTAR Y OTROS METODOS Y FUNCIONES DEL JUEGO
 * 
 */
public class Tablero extends JPanel implements Serializable {       //es serializable para que podamos guardar la partida en un fichero de objeto

    private final Casilla casilla = new Casilla();
    private final JButton Boton[][] = new JButton[casilla.getAncho()][casilla.getAlto()];
//************************************************************************************************************************************************ 
//-2 es casilla vacia,-1 es bomba,0 es casilla tapada , y del 1 al 8 las bombas que hay alrededor
//-3 es la casilla donde ha explotado la bomba
//************************************************************************************************************************************************    
    private final int[][] etiquetas = new int[casilla.getAncho()][casilla.getAlto()];

    //utilizamos labels para imprimir imagen ya que con boton se difumina al desactivarlo
    private final JLabel[][] label = new JLabel[casilla.getAncho()][casilla.getAlto()];
//************************************************************************************************************************************************

    public void crearTablero() {

        for (int X = 0; X < casilla.getAncho(); X++) {               //X es la posicion horizontal

            for (int Y = 0; Y < casilla.getAlto(); Y++) {            //Y es la posicion horizontal

                etiquetas[X][Y] = 0;                                 //recordamos que 0 era el estado de botontapado 

                Boton[X][Y] = new JButton();
                label[X][Y] = new JLabel();

                pintar(X, Y);                                        //pintamos boton tapado
                etiquetas[X][Y] = -2;                                //despues de pintarlo le introducimos estado -2 (casilla vacia)
                Boton[X][Y].add(label[X][Y]);                        //introducimos label al boton
                this.add(Boton[X][Y]);                               //añadimos boton al panel
                Boton[X][Y].setMargin(new Insets(0, 0, 0, 0));       //margen de los botones
                Boton[X][Y].setBounds(X * 60, Y * 60, 60, 60);       //anchura,altura y separacion de los botones

                //añadimos action listener a cada boton para que detecte los clicks
                Boton[X][Y].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent evento) {
                        for (int X = 0; X < casilla.getAncho(); X++) {
                            for (int Y = 0; Y < casilla.getAlto(); Y++) {
                                if (evento.getSource() == Boton[X][Y]) {
                                    DestaparBotonPulsado(X, Y);      //ese boton llamara al metodo destaparBotonPulsado

                                }
                            }
                        }

                    }

                });                                                  //aqui acaba metodo actionlistener         

            }
        }
    }                                                                //aqui acaba metodo tablero
//************************************************************************************************************************************************

    public void PonerBombas() {                                      //metodo que coloca el estado -1 en las zonas donde hay bomba

        Random random = new Random();
        int X;
        int Y;
        int indice = 0;
        Boolean BombaUsada[][] = new Boolean[casilla.getAncho()][casilla.getAlto()];//si es true es que hay una bomba 

        for (int x = 0; x < casilla.getAncho(); x++) {
            for (int y = 0; y < casilla.getAlto(); y++) {
                BombaUsada[x][y] = false;                             //metemos false en todas las bombas porque ninguna esta ocupada
            }
        }

        while (indice != casilla.getNumeroBombas()) {

            X = random.nextInt(casilla.getAncho());                   //metemos un numero entre 0 y 8 en X e Y
            Y = random.nextInt(casilla.getAlto());

            while (BombaUsada[X][Y]) {                                //si esta usado ese espacio volvemos a buscar otro sitio
                X = random.nextInt(casilla.getAncho());
                Y = random.nextInt(casilla.getAlto());
            }
            BombaUsada[X][Y] = true;                                  //ese lugar esta ocupado

            etiquetas[X][Y] = -1;                                     //añadimos estado -1

            indice++;                                                 //cuando llegue al numero de bombas saldra del bucle

        }

    }
//************************************************************************************************************************************************

    public void PonerNumeros() {                                      //pone numero dependiendo de las bombas que hay
        for (int X = 0; X < casilla.getAncho(); X++) {
            for (int Y = 0; Y < casilla.getAlto(); Y++) {
                int IndiceCasilla = 0;                                //el numero que voy a asignar al boton
                if (etiquetas[X][Y] != -1) {                          //vamos mirando las bombas de casillas de alrededor en sentido del reloj

                    if (Y != 0) {                                     //casilla norte
                        if (etiquetas[X][Y - 1] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if ((Y != 0) && (X != casilla.getAncho() - 1)) {  //casilla noreste
                        if (etiquetas[X + 1][Y - 1] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if (X != casilla.getAncho() - 1) {                //casilla este
                        if (etiquetas[X + 1][Y] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if ((X != casilla.getAncho() - 1) && (Y != casilla.getAlto() - 1)) {//casilla sureste
                        if (etiquetas[X + 1][Y + 1] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if (Y != casilla.getAlto() - 1) {                 //casilla sur
                        if (etiquetas[X][Y + 1] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if ((X != 0) && (Y != casilla.getAlto() - 1)) {   //casilla suroeste
                        if (etiquetas[X - 1][Y + 1] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if (X != 0) {                                     //casilla oeste
                        if (etiquetas[X - 1][Y] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if ((Y != 0) && (X != 0)) {                       //casilla noroeste
                        if (etiquetas[X - 1][Y - 1] == -1) {
                            IndiceCasilla++;
                        }
                    }

                    if (IndiceCasilla != 0) {
                        etiquetas[X][Y] = IndiceCasilla;              //añadimos el numero a la posicion correspondiente

                    }

                }
            }
        }

    }
//************************************************************************************************************************************************

    public void DestaparBotonPulsado(int X, int Y) {                  //muestra el numero encima de los botones

        Boton[X][Y].setEnabled(false);                                //destapamos boton pulsado

        pintar(X, Y);                                                 //pintamos ese boton
        if (etiquetas[X][Y] == -1) {
            etiquetas[X][Y] = -3;                                     //esa bomba obtiene el estado de -3 para introducir la imagen de explosion
            DestaparTodo();                                           //cuando hay bomba se destapa todo y se pierde la partida
        }
        Victoria();                                                   //comprobara si ha ganado la partida
        
    }
//************************************************************************************************************************************************

    private void DestaparTodo() {                                                    //metodo al perder                                                     
        for (int x = 0; x < casilla.getAncho(); x++) {
            for (int y = 0; y < casilla.getAlto(); y++) {

                Boton[x][y].setEnabled(false);                                       //todos los botones se destapan

                pintar(x, y);                                                        //y se pintan

            }
        }
        JOptionPane.showMessageDialog(null, "Has perdido!!!");                       //aparece mensaje de que has perdido
    }
//************************************************************************************************************************************************

    public void pintar(int X, int Y) {                                               //Sirve para pintar en tablero
        ImageIcon nombreimagen;

        switch (etiquetas[X][Y]) {                                                   //cada casilla se pintara segun su estado                                                                       

            case -3:                                                                 //casilla donde ha explotado la bomba

                nombreimagen = new ImageIcon("IMAGENES/explosion.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));
                break;
            case -2:                                                                 //casilla destapada vacia

                nombreimagen = new ImageIcon("IMAGENES/destapado.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case -1:                                                                 //casilla con bomba
                nombreimagen = new ImageIcon("IMAGENES/bomba.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));
                break;
            case 0:                                                                  //casilla tapada

                nombreimagen = new ImageIcon("IMAGENES/tapado.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));
                break;

            case 1:                                                                  //el resto numero correspondiente
                nombreimagen = new ImageIcon("IMAGENES/uno.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case 2:
                nombreimagen = new ImageIcon("IMAGENES/dos.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case 3:
                nombreimagen = new ImageIcon("IMAGENES/tres.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case 4:
                nombreimagen = new ImageIcon("IMAGENES/cuatro.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case 5:
                nombreimagen = new ImageIcon("IMAGENES/cinco.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case 6:
                nombreimagen = new ImageIcon("IMAGENES/seis.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case 7:
                nombreimagen = new ImageIcon("IMAGENES/siete.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;
            case 8:
                nombreimagen = new ImageIcon("IMAGENES/ocho.png");
                label[X][Y].setIcon(new ImageIcon(nombreimagen.getImage().getScaledInstance(55, 50, Image.SCALE_SMOOTH)));

                break;

        }

    }
//************************************************************************************************************************************************

    private void Victoria() {                                     //metodo para ganar
        int contadorVictoria = 0;
        for (int x = 0; x < casilla.getAncho(); x++) {
            for (int y = 0; y < casilla.getAlto(); y++) {
                if (Boton[x][y].isEnabled() == false) {

                    contadorVictoria++;                            //contador que ira sumando cada boton destapado                                  
                }
            }
        }
        //si ese sumador es igual a numero de casillas de alto por casillas de ancho menos 
        //las bombas es que has ganado
        if (contadorVictoria == (casilla.getAncho() * casilla.getAlto()) - casilla.getNumeroBombas()) {

            for (int x = 0; x < casilla.getAncho(); x++) {
                for (int y = 0; y < casilla.getAlto(); y++) {
                    Boton[x][y].setEnabled(false);                 //destapamos todos los botones
                }
            }
            JOptionPane.showMessageDialog(null, "Has ganado!!!");  //aparece mensaje de que has perdido
        }

    }
//************************************************************************************************************************************************

    public void Reinicio() {                                      //metodo para reiniciar
        for (int x = 0; x < casilla.getAncho(); x++) {
            for (int y = 0; y < casilla.getAlto(); y++) {

                this.remove(Boton[x][y]);                         //quitamos todos los botones

            }
        }
        crearTablero();                                          //volvemos a crear tablero,poner bombas y poner numeros
        PonerBombas();
        PonerNumeros();

    }
//************************************************************************************************************************************************

    public void Abrirfichero(Tablero t) {                       //metodo para abrir fichero objeto guardado
        int guardarEstado;                                      //registro temporal

        for (int x = 0; x < casilla.getAncho(); x++) {
            for (int y = 0; y < casilla.getAlto(); y++) {
                etiquetas[x][y] = t.etiquetas[x][y];            //guardamos el estado de cada boton de la partida guardada
                //para saber que habia en cada boton

            }
        }
        for (int x = 0; x < casilla.getAncho(); x++) {
            for (int y = 0; y < casilla.getAlto(); y++) {
                if (t.Boton[x][y].isEnabled()) {                  // si el boton estaba encedido usamos guardarEstado como registro temporal
                    // para guardar el estado de la casilla de la partida anterior ya que tendremos que
                    guardarEstado = etiquetas[x][y];                  // introducir el estado 0 para que se tapen las casillas que no habiamos pulsado.
                    etiquetas[x][y] = 0;
                    pintar(x, y);
                    etiquetas[x][y] = guardarEstado;
                } else {                                         //si el boton de la partida anterior no estaba pulsado entonces simplemente lo pintamos

                    Boton[x][y].setEnabled(false);
                    pintar(x, y);

                }
            }
        }
    }
}

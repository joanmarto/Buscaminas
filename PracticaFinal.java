/*@author Juan Mesquida Arenas y Joan Martorell Ferriol
 *
 * Video: https://youtu.be/vgPNp2Z4MxQ
 *
 */
package practicafinal;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * AQUÍ INTRODUCIREMOS LA CLASE MAIN, LA CREACION DEL JFRAME Y LA BARRA DE MENU
 *
 */
public class PracticaFinal extends JFrame {

    private Tablero tablero;
    private JMenuBar menubar;
    private JMenu menu;
    private JMenuItem Abrir, Guardar, Reiniciar, Salir;

    public PracticaFinal() {
        super("BUSCAMINAS ** PRACTICA FINAL PROGRAMACION 19/20");
        this.setLayout(null);                               //eliminar diseño automático
        setSize(545, 592);                                  //tamaño ventana        
        setResizable(false);                                //no redimensionable      
        setLocationRelativeTo(null);                        //aparece en el centro de la pantalla     
        setDefaultCloseOperation(EXIT_ON_CLOSE);            //se cierra al seleccionar la cruz

        iniciarcomponentes();//inicializamos los componentes
    }
//******************************************************************************************************

    private void iniciarcomponentes() {

        //creamos barra JMenuBar con Jmenu y sus 4 botones
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        menu = new JMenu("Opciones");
        menubar.add(menu);                                  //añadimos menu a la jmenubar

        Abrir = new JMenuItem("Abrir");
        Abrir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evento) {//llama a metodo abrir
                Abrir(evento);
            }
        });

        Guardar = new JMenuItem("Guardar");
        Guardar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evento) {//llama a metodo guardar
                Guardar(evento);
            }
        });

        Reiniciar = new JMenuItem("Reiniciar");
        Reiniciar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evento) {//llama a metodo reiniciar
                Reiniciar(evento);
            }
        });

        Salir = new JMenuItem("Salir");
        Salir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evento) {//llama a metodo salir
                Salir(evento);
            }
        });
        //añadimos los 4 botones al menu
        menu.add(Abrir);
        menu.add(Guardar);
        menu.add(Reiniciar);
        menu.add(Salir);

        //metodo para crear y añadir el panel donde introduciremos el juego
        tablero = new Tablero();
        tablero.setBounds(0, 0, 540, 540);
        tablero.setBackground(Color.BLACK);
        tablero.setLayout(null);
        this.getContentPane().add(tablero);

        tablero.crearTablero();                             //metodo crear tablero
        tablero.PonerBombas();                              //metodo para introducir bombas en el tablero
        tablero.PonerNumeros();                             //metodo para poner numeros en el tablero dependiendo de las bombas

    }
//************************************************************************************************************************************************
    //Utilizando el metodoJFileChooser abriremos un archivo y lo guardaremos en un File. Mediante un FileInputStream y ObjectInputStream
    //abriremos el fichero de objeto guardado en t y llamaremos al metodo Abrirfichero .

    private void Abrir(ActionEvent evt) {
        JFileChooser jfilechooser = new JFileChooser();
        int elegir = jfilechooser.showOpenDialog(this);
        if (elegir == JFileChooser.APPROVE_OPTION) {
            try {
                File abrirarchivo = jfilechooser.getSelectedFile();
                FileInputStream ficheroentrada = new FileInputStream(abrirarchivo);
                ObjectInputStream objetoentrada = new ObjectInputStream(ficheroentrada);
                Tablero t = (Tablero) objetoentrada.readObject();
                tablero.Reinicio();                         //reiniciamos para que este el tablero intacto al abrir partida guardada
                tablero.Abrirfichero(t);

            } catch (Exception e) {
                System.out.println("ha ocurrido un error");
            }

        }
    }
//************************************************************************************************************************************************
    //Utilizando el metodoJFileChooser abriremos un archivo y lo guardaremos en un File. Mediante un FileOutputStream y ObjectOutputStream
    //guardaremos un fichero de objeto de nuestra clase tablero con el nombre que queramos.

    private void Guardar(ActionEvent evt) {
        JFileChooser jfilechooser = new JFileChooser();
        jfilechooser.showSaveDialog(null);
        jfilechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        File guardararchivo = jfilechooser.getSelectedFile();
        try {
            FileOutputStream ficherosalida = new FileOutputStream(guardararchivo);
            ObjectOutputStream objetosalida = new ObjectOutputStream(ficherosalida);
            objetosalida.writeObject(tablero);
            objetosalida.close();
            ficherosalida.close();
        } catch (Exception e) {
            System.out.println("ha ocurrido un error");
        }
    }
//************************************************************************************************************************************************

    private void Reiniciar(ActionEvent evt) {               //llama a metodo reiniciar
        tablero.Reinicio();
    }
//************************************************************************************************************************************************

    private void Salir(ActionEvent evt) {                   //sale directamente
        System.exit(0);
    }
//************************************************************************************************************************************************

    public static void main(String[] args) {
        PracticaFinal empezar = new PracticaFinal();        //llama constructor de Practica Final

        empezar.setVisible(true);                           //hace visible la ventana

    }

}

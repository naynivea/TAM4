package TAM4.TAM4;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import java.util.ArrayList;
import java.util.Random;

public class TableroPartida extends JFrame {

    private static final long serialVersionUID = 1L;
    
    //Representa el panel principal de la ventana.

    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField textField;
    private JTextField textField_1;
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnCpu;
	private ButtonGroup groupPlayer1;
    private JRadioButton rdbtnCpu_1;
    private JRadioButton rdbtnNewRadioButton_1;
	private ButtonGroup groupPlayer2;
	private JButton btnNewButton;
	
	//Botones que representan el tablero del juego

    private JButton[] buttons;
    
    //Instancia de la clase Tablero, que maneja la lógica del juego

    private Tablero tablero = new Tablero();
    
    //Almacena el nombre del jugador actual

    private String currentPlayer = "";
    
    //Un indicador para saber si es el turno del jugador 1

	private boolean turno_jugador_1 = true;
	
	//Boolean que indica si la partida está en curso
    private boolean partidaEnCurso = false;
    
    /* Variables: player1 y player2
     * Almacenan los nombres de los jugadores
     */
    private String player1 = "";
    private String player2 = "";
    
    //Almacena la ficha actual del jugador ('X' o 'O')

    private String ficha_actual = "X";

    //Guardan las posiciones x e y ocupadas por los jugadores

	private ArrayList<ArrayList<Integer>> posiciones_jugador_1 = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> posiciones_jugador_2 = new ArrayList<ArrayList<Integer>>();
	
	// Guarda los botones ocupados por las fichas de los jugadores
	
	private ArrayList<JButton> botones_jugador_1 = new ArrayList<JButton>();
	private ArrayList<JButton> botones_jugador_2 = new ArrayList<JButton>();

	//El número máximo de fichas que un jugador puede tener en el tablero (En este caso, 3)

	private final int FICHAS_MAXIMAS = 3;

	/* Constructor de la clase TableroPartida, donde se inicializa la ventana 
	 * y se configuran los botones, campos de texto, etiquetas y grupos de botones de radio
	 */
    public TableroPartida() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 762, 491);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // x y y controlan las coordenadas X e Y de la posición de los botones en la ventana

        buttons = new JButton[9];
        int x = 50;
        int y = 50;
        
        /*Crea una cuadrícula de 9 botones en la interfaz gráfica, que representa el tablero del juego Tres en Raya,
         *y configura un ActionListener para cada botón que permitirá gestionar las acciones del jugador cuando haga
         *clic en una celda del tablero
         */
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setBounds(x, y, 100, 100);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 40));
            buttons[i].setFocusable(false);
            contentPane.add(buttons[i]);
            
            /*Se agrega un ActionListener a cada botón.
             * Cuando se hace clic en uno de los botones, se activará el método
             * onButtonClick(e) que manejará el evento del clic
             */
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onButtonClick(e);
                }
            });
            /*
             * El código verifica si x supera un valor de 290, lo que significa que se ha llegado al final
             * de una fila de botones en la cuadrícula. Si es así, x se reinicia a 50, y se incrementa en 120
             * para moverse a la siguiente fila de botones en la cuadrícula
             */
            x += 120;
            if (x > 290) {
                x = 50;
                y += 120;
            }
        }

        lblNewLabel = new JLabel("Insertad los nombres y tipos de jugadores");
        lblNewLabel.setBounds(450, 112, 284, 14);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(530, 179, 166, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(530, 292, 166, 20);
        contentPane.add(textField_1);

        rdbtnNewRadioButton = new JRadioButton("Humano");
        rdbtnNewRadioButton.setBounds(470, 209, 109, 23);
        rdbtnNewRadioButton.setFocusable(false);
        contentPane.add(rdbtnNewRadioButton);

        rdbtnCpu = new JRadioButton("CPU");
        rdbtnCpu.setBounds(590, 209, 109, 23);
        rdbtnCpu.setFocusable(false);
        contentPane.add(rdbtnCpu);
        
        groupPlayer1 = new ButtonGroup();
        groupPlayer1.add(rdbtnNewRadioButton);
        groupPlayer1.add(rdbtnCpu);

        rdbtnNewRadioButton_1 = new JRadioButton("Humano");
        rdbtnNewRadioButton_1.setBounds(470, 322, 109, 23);
        rdbtnNewRadioButton_1.setFocusable(false);
        contentPane.add(rdbtnNewRadioButton_1);

        rdbtnCpu_1 = new JRadioButton("CPU");
        rdbtnCpu_1.setBounds(590, 322, 109, 23);
        rdbtnCpu_1.setFocusable(false);
        contentPane.add(rdbtnCpu_1);
        
        groupPlayer2 = new ButtonGroup();
        groupPlayer2.add(rdbtnNewRadioButton_1);
        groupPlayer2.add(rdbtnCpu_1);
        
        btnNewButton = new JButton("Nueva Partida");
        btnNewButton.setBounds(530, 57, 120, 26);
		btnNewButton.addActionListener(nuevaPartida);
		btnNewButton.setFocusable(false);
        contentPane.add(btnNewButton);
        
        JLabel lblJguador = new JLabel("Jugador 1:");
        lblJguador.setBounds(450, 182, 284, 14);
        contentPane.add(lblJguador);
        
        JLabel lblJugador = new JLabel("Jugador 2:");
        lblJugador.setBounds(450, 295, 284, 14);
        contentPane.add(lblJugador);
        
        setLocationRelativeTo(null);
    }

    /*
     * Maneja el evento de hacer clic en un botón del tablero.
     * Realiza movimientos de los jugadores y verifica si hay un ganador
     */
    private void onButtonClick(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        
        if (button.getText().isEmpty() && partidaEnCurso) {
            int index = -1;
            for (int i = 0; i < 9; i++) {
                if (button == buttons[i]) {
                    index = i;
                    break;
                }
            }
            int row = index / 3;
            int col = index % 3;
            insertar(row, col, button);
            
            if (tablero.comprobarTablero()) {
                JOptionPane.showMessageDialog(this, "¡Jugador " + currentPlayer + " gana!");
                resetGame();
            } else {
            	ficha_actual = ficha_actual.equals("X") ? "O" : "X";
            	currentPlayer = currentPlayer.equals(player1) ? player2 : player1; 
                lblNewLabel.setText("Turno de " + currentPlayer);
                if (rdbtnCpu.isSelected() && ficha_actual.equals("X") || rdbtnCpu_1.isSelected() && ficha_actual.equals("O")) {
                    // Turno de la IA
                    realizarMovimientoIA();
                }
            }
        }
    }

    /*Maneja el evento de hacer clic en el botón "Nueva Partida".
     *  Verifica las selecciones de jugadores y comienza una nueva partida si se cumplen las condiciones
     */
    ActionListener nuevaPartida = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(comprobarInicioPartida() && !partidaEnCurso) {
				player1 = textField.getText();
				currentPlayer = player1;
				player2 = textField_1.getText();
				partidaEnCurso = true;
				lblNewLabel.setText("Turno de "+ currentPlayer);
				if (rdbtnCpu.isSelected() && ficha_actual.equals("X")) {
                    realizarMovimientoIA();
				}
			}
		}
	};
    
	//Reinicia el juego, limpiando el tablero y restableciendo las variables de juego
    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
        }
        tablero.reiniciarTablero();
        reiniciarJugadores();
        currentPlayer = player1;
        ficha_actual ="X";
        lblNewLabel.setText("Turno de " + currentPlayer);
		if (rdbtnCpu.isSelected() && ficha_actual.equals("X")) {
            realizarMovimientoIA();
		}
    }

    //Simula el movimiento de la CPU eligiendo una casilla aleatoria vacía en el tablero
    private void realizarMovimientoIA() {
        Random random = new Random();
        int index;
        do {
            index = random.nextInt(9);
        } while (!buttons[index].getText().isEmpty());

        int row = index / 3;
        int col = index % 3;
        insertar(row, col, buttons[index]);
        
        if (tablero.comprobarTablero()) {
            JOptionPane.showMessageDialog(this, "La IA gana!");
            resetGame();
        } else {
        	ficha_actual = ficha_actual.equals("X") ? "O" : "X";
        	currentPlayer = currentPlayer.equals(player1) ? player2 : player1; 
            lblNewLabel.setText("Turno de " + currentPlayer);
        }
    }
    
    /*
     * Inserta una ficha en el tablero si la casilla está disponible
     */
    private void insertar(int posx, int posy, JButton boton) {
		if(tablero.comprobarCasilla(posx,posy)) {
			if(turno_jugador_1) {
				addFichaJugador(posiciones_jugador_1, botones_jugador_1, boton, posx, posy);
			}
			else {
				addFichaJugador(posiciones_jugador_2, botones_jugador_2, boton, posx, posy);
			}
			turno_jugador_1 = !turno_jugador_1;
		}
	}
    
    /*Agrega una ficha a la lista de posiciones del jugador y
     * actualiza el tablero y el botón con la ficha correspondiente
     */
    private void addFichaJugador(ArrayList<ArrayList<Integer>> posiciones_jugador, ArrayList<JButton> botones_jugador, JButton boton, int posx, int posy) {
		ArrayList<Integer> posicion = new ArrayList<Integer>();
		posicion.add(posx);
		posicion.add(posy);
    	if(posiciones_jugador.size()>=FICHAS_MAXIMAS) {
			tablero.vaciarCasilla(posiciones_jugador.get(0).get(0), posiciones_jugador.get(0).get(1));
			posiciones_jugador.remove(0);
			botones_jugador.get(0).setText("");
			botones_jugador.remove(0);
		}
    	posiciones_jugador.add(posicion);
		
		tablero.insertarMovimiento(posx, posy, ficha_actual);
		boton.setText(ficha_actual);
		botones_jugador.add(boton);
    }
    
    //Limpia las listas de posiciones y botones de los jugadores

    private void reiniciarJugadores() {
    	posiciones_jugador_1.clear();
    	botones_jugador_1.clear();
    	posiciones_jugador_2.clear();
    	botones_jugador_2.clear();
    }
    
    /*Verifica que se hayan ingresado los nombres de los jugadores 
     * y se haya seleccionado el tipo de jugador (humano o CPU) para ambos jugadores con, como máximo, 1 jugador CPU.
     * Muestra mensajes en la etiqueta lblNewLabel si hay algún error
     */
    private boolean comprobarInicioPartida() {
    	
		if(!textField.getText().equals("") && !textField_1.getText().equals("")) {
			
			if((groupPlayer1.getSelection() != null)&&(groupPlayer2.getSelection() != null)) {
				
				if(rdbtnCpu.isSelected() && rdbtnCpu_1.isSelected()) {
					lblNewLabel.setText("Ambos jugadores no pueden ser del tipo CPU");
					return false;
				}else {
					return true;
				}
			}else {
				lblNewLabel.setText("Introduce primero el tipo de cada jugador");
				return false;
			}
		}else {
			lblNewLabel.setText("Introduce primero los nombres de los jugadores");
			return false;
		}
    }
}


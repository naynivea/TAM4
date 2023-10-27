package TAM4.TAM4;

import java.awt.EventQueue;
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

    private JButton[] buttons;
    
    JRadioButton[] rdnButtonsPlayer1;
    JRadioButton[] rdnButtonsPlayer2;    
    private Tablero tablero = new Tablero();
    private String currentPlayer = ""; // Jugador 1 inicia
	private boolean turno_jugador_1 = true;

    private boolean partidaEnCurso = false;
    private String player1 = "";
    private String player2 = "";

	private ArrayList<ArrayList<Integer>> posiciones_jugador_1 = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Integer>> posiciones_jugador_2 = new ArrayList<ArrayList<Integer>>();
	private ArrayList<JButton> botones_jugador_1 = new ArrayList<JButton>();
	private ArrayList<JButton> botones_jugador_2 = new ArrayList<JButton>();

	private final int FICHAS_MAXIMAS = 3;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TableroPartida frame = new TableroPartida();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TableroPartida() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttons = new JButton[9];
        int x = 50;
        int y = 50;
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setBounds(x, y, 100, 100);
            contentPane.add(buttons[i]);
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onButtonClick(e);
                }
            });
            x += 120;
            if (x > 290) {
                x = 50;
                y += 120;
            }
        }

        lblNewLabel = new JLabel("Turno de " + currentPlayer);
        lblNewLabel.setBounds(450, 50, 200, 14);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(530, 150, 166, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(530, 320, 166, 20);
        contentPane.add(textField_1);

        rdbtnNewRadioButton = new JRadioButton("Humano");
        rdbtnNewRadioButton.setBounds(470, 180, 109, 23);
        contentPane.add(rdbtnNewRadioButton);

        rdbtnCpu = new JRadioButton("CPU");
        rdbtnCpu.setBounds(590, 180, 109, 23);
        contentPane.add(rdbtnCpu);
        
        groupPlayer1 = new ButtonGroup();
        groupPlayer1.add(rdbtnNewRadioButton);
        groupPlayer1.add(rdbtnCpu);

        rdbtnNewRadioButton_1 = new JRadioButton("Humano");
        rdbtnNewRadioButton_1.setBounds(470, 350, 109, 23);
        contentPane.add(rdbtnNewRadioButton_1);

        rdbtnCpu_1 = new JRadioButton("CPU");
        rdbtnCpu_1.setBounds(590, 350, 109, 23);
        contentPane.add(rdbtnCpu_1);
        
        groupPlayer2 = new ButtonGroup();
        groupPlayer2.add(rdbtnNewRadioButton_1);
        groupPlayer2.add(rdbtnCpu_1);
        
        btnNewButton = new JButton("Nueva Partida");
        btnNewButton.setBounds(530, 25, 120, 26);
		btnNewButton.addActionListener(nuevaPartida);
        contentPane.add(btnNewButton);
    }

    private void onButtonClick(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        
        if (button.getText().isEmpty() && partidaEnCurso) {
        	String sign = (currentPlayer.equals(player1)) ? "X" : "O";
            button.setText(sign);
            int index = -1;
            for (int i = 0; i < 9; i++) {
                if (button == buttons[i]) {
                    index = i;
                    break;
                }
            }
            int row = index / 3;
            int col = index % 3;
            tablero.insertarMovimiento(row, col, sign);
            
            if (tablero.comprobarTablero()) {
                JOptionPane.showMessageDialog(this, "¡Jugador " + currentPlayer + " gana!");
                resetGame();
            } else if (tablero.tableroCompleto()) {
                JOptionPane.showMessageDialog(this, "¡Empate!");
                resetGame();
            } else {
                //currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
                //JLabel lblNewLabel = (JLabel) contentPane.getComponent(8);
                lblNewLabel.setText("Turno de " + currentPlayer);
                if (rdbtnCpu.isSelected() && currentPlayer.equals("O")) {
                    // Turno de la IA
                    realizarMovimientoIA();
                }
            }
        }
    }

    ActionListener nuevaPartida = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(comprobarInicioPartida() && !partidaEnCurso) {
				player1 = textField.getText();
				currentPlayer = player1;
				player2 = textField_1.getText();
				partidaEnCurso = true;
			}
		}
	};
    
    
    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
        }
        tablero.reiniciarTablero();
        currentPlayer = "";
        //JLabel lblNewLabel = (JLabel) contentPane.getComponent(8);
        lblNewLabel.setText("Turno de " + currentPlayer);
    }

    private void realizarMovimientoIA() {
        Random random = new Random();
        int index;
        do {
            index = random.nextInt(9);
        } while (!buttons[index].getText().isEmpty());
        //buttons[index].setText("O");

        int row = index / 3;
        int col = index % 3;
        //tablero.insertarMovimiento(row, col, "O");
        insertar(row, col, buttons[index]);
        
        if (tablero.comprobarTablero()) {
            JOptionPane.showMessageDialog(this, "La IA gana!");
            resetGame();
        } else if (tablero.tableroCompleto()) {
            JOptionPane.showMessageDialog(this, "Empate!");
            resetGame();
        } else {
            //currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
            lblNewLabel.setText("Turno de " + currentPlayer);
        }
    }
    
    private void insertar(int posx, int posy, JButton button) {
		if(tablero.comprobarCasilla(posx,posy)) {
			ArrayList<Integer> posicion = new ArrayList<Integer>();
			posicion.add(posx);
			posicion.add(posy);
			if(turno_jugador_1) {
				if(posiciones_jugador_1.size()>=FICHAS_MAXIMAS) {
					tablero.vaciarCasilla(posiciones_jugador_1.get(0).get(0), posiciones_jugador_1.get(0).get(1));
					posiciones_jugador_1.remove(0);
					botones_jugador_1.get(0).setText("");
					botones_jugador_1.remove(0);
				}
				posiciones_jugador_1.add(posicion);
				
				tablero.insertarMovimiento(posx, posy, "X");
				button.setText("X");
				botones_jugador_1.add(button);
			}
			else {
				if(posiciones_jugador_2.size()>=FICHAS_MAXIMAS) {
					tablero.vaciarCasilla(posiciones_jugador_2.get(0).get(0), posiciones_jugador_2.get(0).get(1));
					posiciones_jugador_2.remove(0);
					botones_jugador_2.get(0).setText("");
					botones_jugador_2.remove(0);
				}
				posiciones_jugador_2.add(posicion);
				
				tablero.insertarMovimiento(posx, posy, "O");
				button.setText("O");
				botones_jugador_2.add(button);
			}
			turno_jugador_1 = !turno_jugador_1;
			//System.out.println(tablero.comprobarTablero());
		}
	}
    
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


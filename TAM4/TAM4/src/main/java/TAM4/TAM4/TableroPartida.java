package TAM4.TAM4;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import java.util.Random;

public class TableroPartida extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnCpu;
    private JRadioButton rdbtnCpu_1;
    private JRadioButton rdbtnNewRadioButton_1;

    private JButton[] buttons;
    private Tablero tablero = new Tablero();
    private String currentPlayer = "X"; // Jugador 1 inicia

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

        JLabel lblNewLabel = new JLabel("Turno de " + currentPlayer);
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

        rdbtnNewRadioButton_1 = new JRadioButton("Humano");
        rdbtnNewRadioButton_1.setBounds(470, 350, 109, 23);
        contentPane.add(rdbtnNewRadioButton_1);

        rdbtnCpu_1 = new JRadioButton("CPU");
        rdbtnCpu_1.setBounds(590, 350, 109, 23);
        contentPane.add(rdbtnCpu_1);
    }

    private void onButtonClick(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().isEmpty()) {
            button.setText(currentPlayer);
            int index = -1;
            for (int i = 0; i < 9; i++) {
                if (button == buttons[i]) {
                    index = i;
                    break;
                }
            }
            int row = index / 3;
            int col = index % 3;
            tablero.insertarMovimiento(row, col, currentPlayer);

            if (tablero.comprobarTablero()) {
                JOptionPane.showMessageDialog(this, "¡Jugador " + currentPlayer + " gana!");
                resetGame();
            } else if (tablero.tableroCompleto()) {
                JOptionPane.showMessageDialog(this, "¡Empate!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
                JLabel lblNewLabel = (JLabel) contentPane.getComponent(8);
                lblNewLabel.setText("Turno de " + currentPlayer);
                if (rdbtnCpu.isSelected() && currentPlayer.equals("O")) {
                    // Turno de la IA
                    realizarMovimientoIA();
                }
            }
        }
    }

    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
        }
        tablero.reiniciarTablero();
        currentPlayer = "X";
        JLabel lblNewLabel = (JLabel) contentPane.getComponent(8);
        lblNewLabel.setText("Turno de " + currentPlayer);
    }

    private void realizarMovimientoIA() {
        Random random = new Random();
        int index;
        do {
            index = random.nextInt(9);
        } while (!buttons[index].getText().isEmpty());
        buttons[index].setText("O");

        int row = index / 3;
        int col = index % 3;
        tablero.insertarMovimiento(row, col, "O");

        if (tablero.comprobarTablero()) {
            JOptionPane.showMessageDialog(this, "La IA gana!");
            resetGame();
        } else if (tablero.tableroCompleto()) {
            JOptionPane.showMessageDialog(this, "Empate!");
            resetGame();
        } else {
            currentPlayer = "X";
            JLabel lblNewLabel = (JLabel) contentPane.getComponent(8);
            lblNewLabel.setText("Turno de " + currentPlayer);
        }
    }
}

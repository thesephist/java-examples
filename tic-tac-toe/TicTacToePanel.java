import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Panel that represents an instance of a todo app
 */
class TicTacToePanel extends JFrame {

    /**
     * Constuctor for the game window
     */
    public TicTacToePanel() {
        super("Tic Tac Toe");

        // finish the program when window closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(500, 500);
    }

}


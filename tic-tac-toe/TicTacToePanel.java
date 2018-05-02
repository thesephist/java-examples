import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Panel that represents an instance of a todo app
 */
class TicTacToePanel extends JFrame {

    enum Mark {
        X, O;
    }
    
    private final int[][] WIN_CASES = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},

        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},

        {0, 4, 8},
        {2, 4, 6}
    };

    private Mark turn = Mark.X;
    private ArrayList<TicTacToeButton> history = new ArrayList<TicTacToeButton>();
    private TicTacToeButton[] boxes = new TicTacToeButton[9];

    private void log(String msg) {
        System.out.println(msg);
    }

    /**
     * Constuctor for the game window
     */
    public TicTacToePanel() {
        super("Tic Tac Toe");

        // finish the program when window closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < 9; i ++) {
            boxes[i] = new TicTacToeButton();
        }

        // Make panels (rows)
        JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new BoxLayout(gameBoardPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 3; i ++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            
            for (int j = 0; j < 3; j ++) {
                rowPanel.add(boxes[j * 3 + j]);
            }

            gameBoardPanel.add(rowPanel);
        }

        // Make buttons and button panel
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        JButton startOverButton = new JButton("Start over");
        startOverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startOver();
            }
        });
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });
        controlsPanel.add(startOverButton);
        controlsPanel.add(undoButton);

        // Add the two main panels to the main window
        add(gameBoardPanel, BorderLayout.CENTER);
        add(controlsPanel, BorderLayout.SOUTH);

        setSize(500, 500);
    }

    private void switchTurn() {
        turn = (turn == Mark.X ? Mark.O : Mark.X);
    }

    private void startOver() {
        while (history.size() > 0) {
            undo();
        }
        turn = Mark.X;
    }

    private boolean checkForTie() {
        if (history.size() == 9 && !checkForWin()) {
            alert("X and O tied!");
            return true;
        }
        return false;
    }

    private boolean checkForWin() {
        for (int[] row: WIN_CASES) {
            String line = "";
            for (int index: row) {
                line += boxes[index].getMark();
            }

            if (line.equals("XXX")) {
                alert("X won!");
                return true;
            } else if (line.equals("OOO")) {
                alert("O won!");
                return true;
            }
        }
        return false;
    }

    private void newMark() {
        switchTurn();
        if (checkForWin() || checkForTie()) {
            startOver();
        }
    }

    private void undo() {
        TicTacToeButton lastMarked = history.get(history.size() - 1);
        history.remove(lastMarked);
        lastMarked.clear();
        switchTurn();
    }

    private void alert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private class TicTacToeButton extends JButton {

        public TicTacToeButton() {
            super();
            addActionListener(new ClickListener());
        }

        public void mark(Mark mark) {
            if (mark == Mark.X) {
                setText("X");
            } else {
                setText("O");
            }

            history.add(this);
        }

        public String getMark() {
            return getText();
        }

        private boolean isMarked() {
            return !(getText().equals(""));
        }

        public void clear() {
            setText("");
        }

        private class ClickListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                if (!isMarked()) {
                    mark(turn);
                    newMark();
                }
            }
        }
    }

}


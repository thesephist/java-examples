import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Panel that represents an instance of the tic tac toe game window
 */
class TicTacToePanel extends JFrame {

    /**
     * Marks represent the two different players
     */
    enum Mark {
        X, O;
    }

    /**
     * In a 3x3 grid, it's way more efficient to enumerate all possible
     *  winning cases rather than attempt to compute them on the spot.
     *
     * So this list of winning cases is run against the board state
     *  after each move to see if either player has won.
     */
    private final int[][] WIN_CASES = {
        // horizontal lines
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},

        // vertical lines
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},

        // diagonal lines
        {0, 4, 8},
        {2, 4, 6}
    };

    /**
     * We always begin as the X's turn
     */
    private Mark turn = Mark.X;

    /**
     * A running history of moves through a full game allows the _undo_
     *  functionality and allows for some nifty shortcuts in other places.
     *
     * `history` is an ordered list of the boxes / spaces which have been played.
     */
    private ArrayList<TicTacToeButton> history = new ArrayList<TicTacToeButton>();

    /**
     * Rather than keep a 2D array, because we only have 9 boxes, we just
     *  refer to the boxes as a flat 9 element array. This allows for
     *  more concise checks and code later on.
     */
    private TicTacToeButton[] boxes = new TicTacToeButton[9];

    /**
     * Constuctor for the game window
     */
    public TicTacToePanel() {
        super("Tic Tac Toe");

        // finish the program when window closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // build up the list of 9 boxes on the game board
        for (int i = 0; i < 9; i ++) {
            boxes[i] = new TicTacToeButton();
        }

        // Make the game board area, divided up into 3 panels
        //  which correspond to the 3 rows of boxes, each with 3 spaces.
        JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new BoxLayout(gameBoardPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 3; i ++) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            gameBoardPanel.add(rowPanel);

            for (int j = 0; j < 3; j ++) {
                rowPanel.add(boxes[i * 3 + j]);
            }
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

    /**
     * Switch the next turn's player Mark. It is always either X's or O's turn.
     */
    private void switchTurn() {
        turn = (turn == Mark.X ? Mark.O : Mark.X);
    }

    /**
     * Clear the entire game board, wipe history, and set the starting player to X
     *  again for a fresh start.
     */
    private void startOver() {
        // this is a nifty way of clearing all necessary spaces while also clearing history
        while (history.size() > 0) {
            undo();
        }
        turn = Mark.X;
    }

    /**
     * Check if the game board is held up in a tie. We use two conditions to check quickly for this:
     *  1. The game has no winner yet
     *  2. All nine moves have been played
     *
     * If there is indeed a tie, we pop up an alert.
     */
    private boolean checkForTie() {
        if (history.size() == 9 && !checkForWin()) {
            alert("X and O tied!");
            return true;
        }
        return false;
    }

    /**
     * Check if the game has a clear winner, using the predefined list of win states from the class.
     *
     * If there is a clear winner, we pop up an alert.
     */
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

    /**
     * This hook runs after every new move has been played.
     *
     * Here, we switch to the next player's turn, and check for any game-ending states
     *  like wins or ties.
     *
     * If the game ends, `we startOver();`
     */
    private void newMark() {
        switchTurn();
        if (checkForWin() || checkForTie()) {
            startOver();
        }
    }

    /**
     * Undo by knocking off the last box added to history
     */
    private void undo() {
        if (history.size() > 0) {
            TicTacToeButton lastMarked = history.get(history.size() - 1);
            history.remove(lastMarked);
            lastMarked.clear();
            switchTurn();
        }
    }

    /**
     * A shortcut to pop up a swing-style alert box
     *
     * @param message - the message to display to the user in the pop-up
     */
    private void alert(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Represents a single, markable space on the tic tac toe board.
     *  There are nine of these in a standard game.
     *
     * At one point these just extended `JButton`, but was moved to a
     *  JPanel to be more flexible with size and layout, so that it
     *  fills the available space in the UI instead of being sized as
     *  a literal, text button.
     */
    private class TicTacToeButton extends JPanel {

        /**
         * Even though this is a panel, the essence of the component is still this button.
         */
        private JButton button = new JButton();

        /**
         * Constructor. Sets the style and the click listener
         */
        public TicTacToeButton() {
            super(new BorderLayout());

            button.setFont(new Font("Arial", Font.BOLD, 100));
            button.addActionListener(new ClickListener());
            add(button, BorderLayout.CENTER);
        }

        /**
         * The literal number values returned here are not relevant,
         *  as long as it is reasonably bigger than the font size,
         *  since its container uses BoxLayout to stretch out the buttons
         *  anyways. What's important here is that a constant width
         *  and height are returned regardless of the content of the button.
         *
         * We need this to make sure the grid of 3x3 spaces is regular
         *  at all times.
         */
        public Dimension getPreferredSize() {
            return new Dimension(150, 150);
        }

        /**
         * Make a move onto this space with the current turn's player mark,
         *  and pop that move into the game history.
         */
        public void mark() {
            if (turn == Mark.X) {
                button.setText("X");
            } else {
                button.setText("O");
            }

            // I debated whether this should be in `mark()` here or in the `newMark()` hook,
            //  but ultimately decided here, because it needs a reference to `this`, which `newMark()`
            //  does not and should not get.
            history.add(this);
        }

        /**
         * Get the mark that currently occupies this space, if any.
         */
        public String getMark() {
            return button.getText();
        }

        /**
         * Return whether or not a move has been made onto this space
         */
        private boolean isMarked() {
            return !(button.getText().equals(""));
        }

        /**
         * Clear this space of any marks / moves
         */
        public void clear() {
            button.setText("");
        }

        /**
         * Listener for when the user clicks on the space to make
         *  a move. It allows a move if the space is empty, and calls
         *  the after-each-move hook on the parent game board
         */
        private class ClickListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                if (!isMarked()) {
                    mark();
                    newMark();
                }
            }
        }
    }

}


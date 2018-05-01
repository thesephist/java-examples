class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startApp();
            }
        });
    }

    // Method run by the AWT UI thread to start the app
    private static void startApp() {
        TicTacToePanel tttp = new TicTacToePanel();
        tttp.setVisible(true);
    }

}


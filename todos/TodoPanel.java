import java.util.*; // for ArrayList
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Panel that represents an instance of a todo app
 */
class TodoPanel extends JFrame {

    private SaveManager saveManager;

    private JTextField inputField;
    private JList newList;
    private DefaultListModel newListModel;
    private JList doneList;
    private DefaultListModel doneListModel;

    private JButton addButton;
    private JButton doneButton;
    private JButton undoButton;
    private JButton removeNewButton;
    private JButton removeDoneButton;

    /**
     * Constuctor for the todo app window.
     *
     * This initializes all UI components inside it,
     *  and also restores / saves the todo list to the savefile.
     *
     * @param saveFileName - name of file used to store the todo list
     */
    public TodoPanel(String saveFileName) {
        super("Java Todos");

        // finish the program when window closes
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up a save-on-close action
        saveManager = new SaveManager(saveFileName);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ArrayList<String> newTodos = new ArrayList<String>();
                ArrayList<String> doneTodos = new ArrayList<String>();

                ArrayList<ArrayList<String>> todos = new ArrayList<ArrayList<String>>();
                todos.add(newTodos);
                todos.add(doneTodos);

                // this is a bit of a pain in the A
                //  because ListModel does not implement the Collection interface (yet)
                //  so we can't just use the for-each loop
                for (int i = 0; i < newListModel.getSize(); i ++) {
                    newTodos.add(newListModel.get(i).toString());
                }
                for (int i = 0; i < doneListModel.getSize(); i ++) {
                    doneTodos.add(doneListModel.get(i).toString());
                }

                saveManager.save(todos);
            }
        });

        // initialize list models and add saved
        //  todo items to the list
        newListModel = new DefaultListModel();
        doneListModel = new DefaultListModel();
        ArrayList<ArrayList<String>> todos = saveManager.read();
        for (String todo: todos.get(0)) {
            newListModel.addElement(todo);
        }
        for (String todo: todos.get(1)) {
            doneListModel.addElement(todo);
        }

        // initialize UI components
        inputField = new JTextField();
        newList = new JList(newListModel);
        doneList = new JList(doneListModel);
        addButton = new JButton("Add Todo");
        doneButton = new JButton("Mark as Done");
        undoButton = new JButton("Mark as Not Done");
        removeNewButton = new JButton("Remove from New");
        removeDoneButton = new JButton("Remove from Done");

        // list configurations
        newList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doneList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane newScrollPane = new JScrollPane(newList);
        JScrollPane doneScrollPane = new JScrollPane(doneList);

        // button listeners
        addButton.addActionListener(new AddListener());
        doneButton.addActionListener(new DoneListener());
        undoButton.addActionListener(new UndoListener());
        removeNewButton.addActionListener(new RemoveListener(newList, newListModel));
        removeDoneButton.addActionListener(new RemoveListener(doneList, doneListModel));

        // pane for inputs
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BorderLayout());
        inputPane.add(inputField, BorderLayout.CENTER);
        inputPane.add(addButton, BorderLayout.EAST);

        // pane for the lists
        JPanel listsPane = new JPanel();
        listsPane.setLayout(new BoxLayout(listsPane, BoxLayout.LINE_AXIS));
        listsPane.add(newScrollPane);
        listsPane.add(doneScrollPane);
        
        // pane for the buttons
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.LINE_AXIS));
        buttonsPane.add(doneButton);
        buttonsPane.add(undoButton);
        buttonsPane.add(removeNewButton);
        buttonsPane.add(removeDoneButton);

        // add components in the right position
        add(inputPane, BorderLayout.NORTH);
        add(listsPane, BorderLayout.CENTER);
        add(buttonsPane, BorderLayout.SOUTH);

        setSize(500, 500);
    }

    /**
     * This determines what happens when the add button is clicked
     */
    class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String todoName = inputField.getText();
            inputField.setText("");
            if (todoName.length() > 0) {
                newListModel.addElement(todoName);
            }
        }
    }

    /**
     * This determines what happens when the done button is clicked
     */
    class DoneListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int index = newList.getSelectedIndex();
            String todoName = newListModel.getElementAt(index).toString();
            if (index > -1) {
                newListModel.remove(index);
                doneListModel.addElement(todoName);

                int size = newListModel.getSize();
                if (size > 0) {
                    newList.setSelectedIndex(size - 1);
                }
            }
        }
    }

    /**
     * This determines what happens when the undo button is clicked
     */
    class UndoListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int index = doneList.getSelectedIndex();
            String todoName = doneListModel.getElementAt(index).toString();
            if (index > -1) {
                doneListModel.remove(index);
                newListModel.addElement(todoName);

                int size = doneListModel.getSize();
                if (size > 0) {
                    doneList.setSelectedIndex(size - 1);
                }
            }
        }
    }

    /**
     * This removes an item from a given list when button is clicked
     */
    class RemoveListener implements ActionListener {
        private JList list;
        private DefaultListModel listModel;

        public RemoveListener(JList list, DefaultListModel listModel) {
            this.list = list;
            this.listModel = listModel;
        }

        public void actionPerformed(ActionEvent event) {
            int index = list.getSelectedIndex();
            if (index > -1) {
                listModel.remove(index);

                int size = listModel.getSize();
                if (size > 0) {
                    list.setSelectedIndex(size - 1);
                }
            }
        }
    }

}


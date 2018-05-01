import java.util.*;
import java.io.*;

// TODO: a possible extension may be to take the
//  ArrayList<ArrayList<String>> type for the serialized
//  todo list and make it a class.

/**
 * SaveManager has the ability to take a todo list
 *  as a List and save it to the indicated file, or
 *  to read from a saved todo list file back into a
 *  List of todos.
 *
 * It is used to save todo items between sessions.
 */
class SaveManager {

    /**
     * We need something never actually used
     *  as a todo item. Since we block empty
     *  todo items in the app, this seems like
     *  a good simple choice.
     */
    public final String SEPARATOR = "";

    private String saveFileName;
    private File file;

    /**
     * Create the savefile if it does not already exist.
     *
     * @param saveFileName - name of file used to store the todo list
     */
    public SaveManager(String saveFileName) {
        this.saveFileName = saveFileName;

        try {
            file = new File(saveFileName);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Couldn't open the file to save!");
        }
    }

    /**
     * @return name of the savefile name given to the constructor
     */
    public String getSaveFileName() {
        return saveFileName;
    }

    /**
     * Serialize the saved todo list into two lists -- of new and
     *  finished todo items.
     */
    public ArrayList<ArrayList<String>> read() {
        ArrayList<ArrayList<String>> todos = new ArrayList<ArrayList<String>>();
        ArrayList<String> newTodos = new ArrayList<String>();
        ArrayList<String> doneTodos = new ArrayList<String>();

        todos.add(newTodos);
        todos.add(doneTodos);

        String line;
        boolean afterSeparator = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                if (line.equals(SEPARATOR)) {
                    afterSeparator = true;
                } else if (afterSeparator) {
                    doneTodos.add(line);
                } else {
                    newTodos.add(line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't open the file to save!");
        }

        return todos;
    }

    /**
     * Deserialize lists of new and done todos and save it in the
     *  right format to the save file.
     *
     * @param todos - list of todos in a list-of-list form of newTodos, doneTodos
     */
    public void save(ArrayList<ArrayList<String>> todos) {
        ArrayList<String> newTodos = todos.get(0);
        ArrayList<String> doneTodos = todos.get(1);

        String fileContent = "";
        for (String newTodo: newTodos) {
            fileContent += newTodo + "\n";
        }
        fileContent += SEPARATOR + "\n";
        for (String doneTodo: doneTodos) {
            fileContent += doneTodo + "\n";
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(fileContent);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: wasn't able to save the todos");
        }
    }

}

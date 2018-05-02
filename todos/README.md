# todos

Basic Java / Swing GUI based to-do list app ✅

## Synopsis

**todos** is a minimal todo application, allowing you to add and remove items to to the list, and mark items as complete. It is built on Java's Swing framework, using buttons and action listeners for interactivity and a `DefaultListModel` as the backing data model. We also optionally cover persistence of data to the disk here.

## Relevant concepts

This is a nice introduction to the basic capabilities of Java's Swing framework, the model-view architecture, and how the UI may interact with the main thread and other operations like filesystem calls.

### Language features

- Extending built-in components (`JFrame`)
- Generics (`ArrayList<String>`, etc.)
- Interfaces (`ActionListener`)
- Anonymous classes (`new WindowAdapter()`)
- Inner classes
- `try`/`catch` and basic filesystem IO for model data persistence

### Libraries

- Java Swing UI (`J*`) components and the interactivity model based on event listeners
- `ArrayList`
- Filesystem operations with `File[Reader|Writer]`

### Patterns

- Model-view architecture
- Action (event) listeners for interactivity
- Swing's panel-based layout (`BoxLayout`, `BorderLayout`)
- Separating out the UI thread from `main`

## Room for expansion

Because this is a pretty rudimentary application, there's lots of room for experimentation here, from adding buttons that do different things to adding keyboard input (hitting "ENTER" to add items).

There are also some edge cases that currently generate uncaught exception (attempting to mark task as complete with no task selected).


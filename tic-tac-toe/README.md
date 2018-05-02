# tic-tac-toe

Minimal tic-tac-toe game GUI with Swing (using Swing instead of a dedicated grphics library for simplicity / learnability / conceptual overlap with **todos**).

## Synopsis

**tic-tac-toe** is a simple Swing application that runs a tic-tac-toe game. It explores many of the same concepts as **todos**, but in a different setting of a game. 

This means we get to write lots of static-feeling methods that manipulate the game state. We can also discuss game loops, though in this case a "loop" is really a move made by either of the players. We also explore Java enums, and make deeper use of Swing's simple BoxLayout.

### Language features

- Extending built-in components (`JFrame, JPanel`)
- Generics (`ArrayList<TicTacToeButton>`, etc.)
- Interfaces (`ActionListener`)
- Arrays vs. ArrayLists
- Java Enums (`Mark {X, O}`)

### Libraries

- Java Swing UI (`J*`) components and the interactivity model based on event listeners
- `ArrayList`

### Patterns

- Swing's panel-based layout (`BoxLayout`, `BorderLayout`)
- Action (event) listeners for interactivity
- Separating out the UI thread from `main`
- Extending built-in components for specialization (`TicTacToeButton` from `JPanel`)
- Inner classes (`TicTacToeButton` and `ClickListener`)

## Room for expansion

A straightforward way to expand upon this would be to:

1. Add more players
2. Make this occur in a board bigger than a 3x3 grid
3. Keeping track of scores across games (3:1 in the last 4 games this session, etc.)


# Three Trios
Three Trios is a java implementation of a variation of the game Triple Triad.
Three Trios is played on a rectangular grid of cells and holes between two players.

## Quickstart
To initialize a cs3500.threetrios.provider.model and start a game with two human players, run the following through
a command prompt:
```
java -jar threetrios.jar human human
```
For other command-line arguments or further clarification on the main method,
please see the ThreeTrios class in the cs3500.threetrios package.

To view a cs3500.threetrios.provider.model through the console, run:
```
TripleTriadTextView view = new TripleTriadTextView(cs3500.threetrios.provider.model);
System.out.println(view.renderModel());
```
To view a cs3500.threetrios.provider.model through a graphical interface, run:
```
TripleTriadGraphicalView view = new TripleTriadGraphicalView(cs3500.threetrios.provider.model);
view.setVisible(true);
```

## Config
The game can be set up from config files using the static methods in the FileReader class.
<br />A board config file must be set up in the following format: <br />
```
NUM_ROWS NUM_COLS
ROW_0
ROW_1 
ROW_2 
... 
```
where each row is a NUM_COLS long sequence of X (representing holes) and C (representing cells).
<br /> A card deck config file must be set up in the following format: <br />
```
CARD_NAME NORTH SOUTH EAST WEST
CARD_NAME NORTH SOUTH EAST WEST
CARD_NAME NORTH SOUTH EAST WEST
...
```

## Structure
The project is broken up into a cs3500.threetrios.provider.model, view, and controller. <br />
The cs3500.threetrios.provider.model package contains all the relevant components for representing the game logic.
This includes the TripleTriadModel class, which enforces the rules and allows other classes to
"peek" at the ongoing game, as well as the TriadTile class, which represents the individual cells
of the game board, and the TripleTriadCard class, which represents the cards that the game is played
with. <br />
The view package contains all the relevant components for the players to view the game.
It contains implementations for both a textual view and a graphical view.
<br />
The controller package contains all relevant components for the controller to talk to the view and
cs3500.threetrios.provider.model, ensuring that a player's inputs are passed along to the cs3500.threetrios.provider.model properly, and that the view
remains up-to-date with the cs3500.threetrios.provider.model.
<br />
In addition to the cs3500.threetrios.provider.model, view, and controller, there is an additional section for strategies. The
Strategies are not necessary for the game to be played by humans. They are used to determine the
next move to be played by an AI player, and as such are passed as arguments for a
MachinePlayer class.

## Changes for Part 2:
Added the following functions to the observational cs3500.threetrios.provider.model:

getBoardWidth() - fairly self-explanatory, just returns the width of the board.

getBoardHeight() - returns the height of the board.

getTileAt(int row, int col) - returns a copy of the tile at that position

getPlayerAt(int row, int col) - returns the player that owns the card on that space

getPossibleCardsFlipped(Card card, int row, int col) - returns the number of cards that would be 
flipped, if the given card was played to the given row and column.

simulateMove(Card card, int row, int col) - returns another cs3500.threetrios.provider.model with the supplied move having
been played.

getPlayerScore(PlayerColor player) - returns the number of cards that player owns.

checkValidMove(int row, int col, int handIdx, PlayerColor player) - returns whether that play
would be valid.

checkValidSpot(int row, int col) - returns whether coordinates are in range of the cs3500.threetrios.provider.model.

The only functions above with more-than-trivial implementations are getCardsFlipped and simulateMove.

For getCardsFlipped we actually place down the card on the board, run a battle tick to 
determine how many cards would be flipped, then reset the board to its original state before 
the card was placed.

For simulateMove we create a new cs3500.threetrios.provider.model in the middle of a game through a private constructor,
then return that cs3500.threetrios.provider.model

## Part 2 Extra Credit:
We implemented both extra credit strategies -- both can be found in the
cs3500.threetrios.strategy package.

The DefaultStrategy returns a list of all possible moves to the board,
sorted by their position and handIndex.

All other strategies extend ComplexStrategy, allowing them to be combined in the constructor 
through the specification of a parent strategy. If no parent is specified, the DefaultStrategy
is used.
The order of the strategies matters when combined, as parent strategies are run first, so child
strategies will not consider any moves not returned by the parent.

MostCardsFlippedStrategy determines which of the moves from the parent strategy flips the most cards
on this turn.

CornerStrategy determines which of the moves from the parent strategy is in a corner,
then considers how many of the opposing player's cards can flip the card from each move.
If a neighboring tile is already filled or is a hole, the card is considered to be able to defend
all possible opposing cards from that direction.

CornerStrategy takes all of the moves from the parent strategy,
then considers how many of the opposing player's cards can flip the card from each move.
If a neighboring tile is already filled or is a hole, the card is considered to be able to
defend all possible opposing cards from that direction.

MinimaxStrategy takes all of the moves from the parent strategy, and for each move, simulates a new
cs3500.threetrios.provider.model after the moves execution. It then assumes that the opponent is using the
MostCardsFlippedStrategy (as it has the most direct benefit on a player's score), and determines the
best possible response of the opponent. It then filters the moves to only contain the ones that
resulted in the worst-case best-possible response from the opponent.

As previously stated, implementations are in the cs3500.threetrios.strategy package.
Tests can be found in the GeneralTests file in the /test folder, beginning at line 369.

## Changes for Part 3:

getHandIndexOfCard and addFeatures were both functions added to the ObservationalTriadModel interface.
getHandIndexOfCard returns the hand index of the card given.
addFeatures is used to add a listener to the cs3500.threetrios.provider.model, which receives callbacks when a turn ends or the
game is over.

As part of the command-callback nature of the assignment, the cs3500.threetrios.provider.model now calls functions on any
subscribed ModelFeatures objects when the turn has changed or when the game is over.

Most changes occurred in the view package.
We realized that we should probably have an interface for the view, so now we have that.
TripleTriadGraphicalView now extends a GraphicalView interface which describes what features a graphical
view needs to have for it to work with all the other parts of the game.

The view also now contains a field for a listener, to which it will send callbacks whenever the
user clicks a card in the hand or a space on the board. Finally, the view now is able to render
a "game over" screen with correct information.

Main now takes in two command-line arguments corresponding to which player implementations
should control RED and BLUE respectively.

## Short overview of Part 3 classes:

ViewFeatures and ModelFeatures are both interfaces that describe callbacks that the view and cs3500.threetrios.provider.model
can call respectively. The Controller concrete class implements both of these interfaces, and
subscribes itself both to the cs3500.threetrios.provider.model and its view. Because of this, the controller receives callbacks
from both of these objects, allowing it to asynchronously act on player input from the view as well
as keep its view up-to-date with any changes from the cs3500.threetrios.provider.model.

HumanPlayer and MachinePlayer are both implementations of the Player interface, which really just
contain a synchronous strategy and a color. In the human player's case, the controller should be
relying on the *asynchronous* callbacks, so the getNextMove() function returns an empty optional,
signaling the controller to wait. MachinePlayers just call their strategies.

# Tic Tac Toe Game Design 

## How I started to the project:
First, I needed to decide on my design, so I opened the activity_main.xml to start designing my app. 

I have decided to use constraint layout since constraint layout prevent layout rendering issues for nested layouts. 
For the XOX part, I first decided to use buttons since they are clickable and I thought I could change the text’s visibility when user click on it, but the text visibility was not great. So, I ask Chat if I could use Text View instead and it told me I can, so I decided to use text view instead since text view has clickable attribute too.

I have also used Grid Layout to make my text views more organized since otherwise I need to connect them all together and work so hard to make them organized so instead, I decided to use Grid layout. (I have also get the idea from Chat after struggling with organizing my text views precisely and realized it take a lot of time and effort)
After determining the app’s design, the next step was adding functionality to all these views on my **MainActivity.kt**.

<img width="148" alt="image" src="https://github.com/user-attachments/assets/6e1f3170-76ca-4243-b8da-b24f808c6ca6" />

# Libraries I have used:

- I have used **android.graphics.Color** library since I have decided to change the background color of the cell when a player wins.
- I have used **android.widget.Button** and **android.widget.TextView** since I have been using Button and TextViews for my tic tac toe games and I will need it when I am trying to connect to them in my mainActivity kotlin file

# How I Handled Game Logic:

After deciding on design, I decided to use two separate classes outside of main which one of them was to control the game and the other for keeping player’s score and their symbol which they used to play.
I started with player since it was easier. Player class was as follow:

## Player Class

```
// Player Class
class Player(val value:String, var score:Int = 0){
}
```

Which as you could see, keeps the player’s symbol of X or O as value and score of the player.

## Game Controller class

```
class GameController(val cells: Array<TextView>) {

    // Function for making moves:
    fun makeMove(cell:TextView, player:Player):Boolean {
        // if cell is already filled
        if (cell.text.isNotEmpty()) {
            return false; // then do not make move
        }
        else { // is it is not
            cell.text = player.value; // then put the player's value as text to the cell
            return true; // and make the move!
        }
    }

    // Function for checking if the user has win or not:
    fun checkWin():List<TextView>? {
        // These are the winning position
        val winningPositions = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        );
        // for each position in the list
        for ((a,b,c) in winningPositions) {
            // check each position's text
            val first = cells[a].text;
            val second = cells[b].text;
            val third = cells[c].text;
            // look if it is not empty and equal to each other
            if (first.isNotEmpty() && first == second && second == third) {
                return listOf(cells[a], cells[b], cells[c]) // if it is, then return the cells
            }
        }
        return null; // else return nothing
    }
    // Checking if the board is all full or not
    fun isBoardFull(): Boolean {
        // if all cells are full then return true else return false
        return cells.all { it.text.isNotEmpty();}
    }
    // When one of the user wins or it is draw then when user clicked on restart empty the board
    fun resetBoard() {
        for (cell in cells) {
            cell.text = "";
        }
    }

}
```

**GameController** class had taken cells as parameter. Cells are the array of text views on my android app that I used for user to be able to click on to put X or O respectively.
 Let's now dive into the functions inside the **GameController** Class:

### makeMove:

 ```
    // Function for making moves:
    fun makeMove(cell:TextView, player:Player):Boolean {
        // if cell is already filled
        if (cell.text.isNotEmpty()) {
            return false; // then do not make move
        }
        else { // is it is not
            cell.text = player.value; // then put the player's value as text to the cell
            return true; // and make the move!
        }
    }
 ```

Make move function in GameController class, checks if the cell the user wants to make move is empty or not. If it is empty, then it makes the move and if it is not then does not make the move.

### checkWin:
 
 ```
    // Function for checking if the user has win or not:
    fun checkWin():List<TextView>? {
        // These are the winning position
        val winningPositions = listOf(
            listOf(0, 1, 2),
            listOf(3, 4, 5),
            listOf(6, 7, 8),
            listOf(0, 3, 6),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(0, 4, 8),
            listOf(2, 4, 6)
        );
        // for each position in the list
        for ((a,b,c) in winningPositions) {
            // check each position's text
            val first = cells[a].text;
            val second = cells[b].text;
            val third = cells[c].text;
            // look if it is not empty and equal to each other
            if (first.isNotEmpty() && first == second && second == third) {
                return listOf(cells[a], cells[b], cells[c]) // if it is, then return the cells
            }
        }
        return null; // else return nothing
    }

 ```
The most important functionality of this app is checking if the user has wined or not since it is the most complicated part. This function first initialized the winning positions as list of triple lists which are the position a player must put their symbol in order to win the round.

After initializing the winning positions, the function looks in the cells if there is any X or O in the winning position. If there is then it returns the winning position so after that I could highlight the winning positions using another function.

### isBoardFull:
 ```
     // Checking if the board is all full or not
    fun isBoardFull(): Boolean {
        // if all cells are full then return true else return false
        return cells.all { it.text.isNotEmpty();}
    }
 ```

It checks for all the cells if their text is empty or not. If it is not empty for all of them then the board is not full and the function returns false but if it is full then the function returns true.

### resetBoard:

```
    // When one of the user wins or it is draw then when user clicked on restart empty the board
    fun resetBoard() {
        for (cell in cells) {
            cell.text = "";
        }
    }
```

Since if the board is full or player clicks on play again button, the game should clean the board, so we need reset board function to make every cell in cells’ text to be cleaned out.

## MainActivity Class:

In the main activity class, first thing I did was initializing the values and variables respectively considering their usage:

```
        // initilized the cells
        val cells:Array<TextView> = arrayOf(
            findViewById(R.id.cell1),
            findViewById(R.id.cell2),
            findViewById(R.id.cell3),
            findViewById(R.id.cell4),
            findViewById(R.id.cell5),
            findViewById(R.id.cell6),
            findViewById(R.id.cell7),
            findViewById(R.id.cell8),
            findViewById(R.id.cell9)
        );

        // initialized the game controller
        val gameController = GameController(cells);

        // initilized the others
        val startButton= findViewById<Button>(R.id.startButton);
        val xScore = findViewById<TextView>(R.id.xScore);
        val oScore = findViewById<TextView>(R.id.oScore);
        val drawScore = findViewById<TextView>(R.id.drawScore);
        val turns = findViewById<TextView>(R.id.turns);
        var drawCount = 0;
        var gameOver = false;

        lateinit var winningCells:List<TextView>;
```

I have declared currentPlayer, drawCount, gameOver and winningCells as variable since they are going to change throughout the game but for others, even though their score and some property is going to change, what they reference is not going to change so I could use val instead of var.

### switchTurns:

```
        // used for switching the turns of the player:
        fun switchTurns() {
            if(currentPlayer == firstPlayer) {
                currentPlayer = secondPlayer;
                turns.text = "O Player's turn";

            }
            else {
                currentPlayer = firstPlayer;
                turns.text = "X Player's turn";
            }
        }
```

Since throughout the game, I need to switch between X and O, I have decided to use an function in the Main for switching between players and to do that I have used currentPlayer temporary variable which holds the player who need to make the move next.

### highlightWinningCells:

```
        // highlighting the winning cells
        fun highlightWinningCells(cells:List<TextView>) {
            for (cell in cells) {
                cell.setBackgroundColor(Color.parseColor("#FF0B55"));
            }
        }
```

Since when a player wins, I need to highlight the winning cells so I used the above function which takes the winning cells and change its background color. (This is why I turn the winning cells from the checkWin function in GameController class)

### resetHighlight:

```
        // reseting the highlight
        fun resetHighlight(cells:List<TextView>) {
            for(cell in cells) {
                cell.setBackgroundColor(Color.parseColor("#C68EFD"));
            }
        }
```

After it highlighting the winning cells, it needs to set it back to its original color for game to be continued  so I have added a function which takes the winning cells as parameter and sets is background back to its original color.

### updateScores

```
        // Updating Scores
        fun updateScores(){
            if (currentPlayer.value == "X") {
                xScore.text = currentPlayer.score.toString();
            }
            else {
                oScore.text = currentPlayer.score.toString();
            }
        }
```

When a player wins, it needs to change its score on the TextView as well so this function basically checks if the current player who wined is X player or O player then changes its score to the current score hold in the player’s itself.

### resetGame:

```
        // Reset Game
        fun resetGame() {
            gameController.resetBoard();
            currentPlayer = firstPlayer;
            if (winningCells != null) {
                resetHighlight(winningCells)
            };
            turns.text = "X Player's turn"
            gameOver = false;
        }
```

The reset game function first reset the board using the function declared in GameController class then changes the currentPlayer back to the firstPlayer since X player always needs to start first. After that, it looks for winning cells and reseting their highlight back to normal and changes the text back to X Player’s turn. After that I have reinitialized the gameOver value back to true for game to be continue.

### resetScore:

```
        // Reset Score
        fun resetScore() {
            firstPlayer.score = 0;
            secondPlayer.score = 0;
            drawCount = 0;
            xScore.text = firstPlayer.score.toString();
            oScore.text = secondPlayer.score.toString();
            drawScore.text = drawCount.toString();
        }
```

I have used reset score function to reset all the scores back to 0 so the game could start from the beginning which is going to be important when the player clicks on play again button to start the game from beginning.

### onClickedCell:

```
           fun onClickedCell(cell: TextView) {
            if (gameOver) {
                return;
            }
            // if the cell the user clicked is not empty then
            if (!gameController.makeMove(cell, currentPlayer)) {
                // notify user that the cell is occupied
                turns.text = "The current cell is not empty!!";
                return; // and do nothing!! (no changing the turns!)
            }
            val result = gameController.checkWin();
            // if one of the user wins
            if (result != null) {
                winningCells = result;
                // make notification that says the current player has winned
                turns.text = "Player ${currentPlayer.value} has winned!";
                currentPlayer.score++; // increase the current player's score
                highlightWinningCells(winningCells);
                updateScores();
                gameOver = true;
                cell.postDelayed({
                    resetGame()
                }, 2000)
            }
            else if (gameController.isBoardFull()) {
                turns.text = "The board is full. Draw!";
                drawCount++;
                drawScore.text = drawCount.toString();
                gameOver = true;
                cell.postDelayed({
                    resetGame()
                }, 1000)
            }
            else {
                switchTurns();
            }
        }
```

On clicked function is the most important function in the Main class since it controls each situation respectively. When Game is over it does not do anything (It is important since otherwise the was never over). 
If the cell the user tries the make move is already occupied, the function does return nothing so it will not switch turns.

Since after each move the result is going to change for the winning cell, I need to look for it on each click and if it is. Not null then I should  initialized the winning cell to the value from the result (which is why I have used lateinit), Announce the player who winned, increase its score, highlight the cells and update each score on the board above. I have also used delay too since for the player it was not possible to see the highlight since after highlighting the cells, I need to reset the game for player to continue playing it. I have used Chat for initializing the delay since I had no idea how to do it but knew I should.

The last situation was draw. So when the board is full, it should increase the draw count then reset the game after some delay.
If there is no winner or the board is not full then the app should switch the players. This is all needed for the game to function properly.

# How Game State and UI State Communicate With Each Other:
 
 ```
        for (cell in cells) {
            cell.setOnClickListener { onClickedCell(cell) }
        }

        startButton.setOnClickListener {
            resetGame();
            resetScore();
        }
 ```

So for all cell, I have called setOnClickListener to listen when the cell is clicked and calls the onClickedCell function for the respective cell that has been clicked on.
Same goes for the startButton as well. When someone clicked on startButton, the resetGame and resetScore functions called to reset the whole game and the players start their game from the beginning.


        


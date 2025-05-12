package com.example.tic_tac_toe_game
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // initialized the two players
        val firstPlayer = Player("X");
        val secondPlayer= Player("O");
        var currentPlayer = firstPlayer;

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

        // highlighting the winning cells
        fun highlightWinningCells(cells:List<TextView>) {
            for (cell in cells) {
                cell.setBackgroundColor(Color.parseColor("#FF0B55"));
            }
        }

        // reseting the highlight
        fun resetHighlight(cells:List<TextView>) {
            for(cell in cells) {
                cell.setBackgroundColor(Color.parseColor("#C68EFD"));
            }
        }

        // Updating Scores
        fun updateScores(){
            if (currentPlayer.value == "X") {
                xScore.text = currentPlayer.score.toString();
            }
            else {
                oScore.text = currentPlayer.score.toString();
            }
        }
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
        // Reset Score
        fun resetScore() {
            firstPlayer.score = 0;
            secondPlayer.score = 0;
            drawCount = 0;
            xScore.text = firstPlayer.score.toString();
            oScore.text = secondPlayer.score.toString();
            drawScore.text = drawCount.toString();
        }

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

        for (cell in cells) {
            cell.setOnClickListener { onClickedCell(cell) }
        }

        startButton.setOnClickListener {
            resetGame();
            resetScore();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}

// Player Class
class Player(val value:String, var score:Int = 0){
}

// Game Controller Class
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
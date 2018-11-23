package andrew.opl.ramapo.edu.casino;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Round implements Serializable {
    private Player playerList[];
    private Board board;
    private Deck deck;
    private String capturedLast;
    private String CurrentPlayer;

    /**
     * Purpose : constructor for the round class with no save file
     * @param _playerList the playersList from the tournament class
     * @param _capturedlast the string of the person who captured last so they become the currentPlayer
     * Help Received : none
     */

    Round(Player _playerList[], String _capturedlast){
        playerList = _playerList;
        CurrentPlayer = _capturedlast;
        board = new Board();
        deck = new Deck();
        capturedLast = "None";
        SetupRound();
    }

    /**
     * Purpose : constructor for the round class with a seeded deck
     * @param _playerList the playersList from the tournament class
     * @param _capturedlast the string of the person who captured last so they become the currentPlayer
     * @param _deck the saved deck that was loaded it in
     * Help Received : none
     */

    Round(Player _playerList[], String _capturedlast, Deck _deck){
        playerList = _playerList;
        CurrentPlayer = _capturedlast;
        board = new Board();
        deck = _deck;
        capturedLast = "None";
        SetupRound();
    }

    /**
     * Purpose : constructor for the round class with a full save file
     * @param _playerList the playersList from the tournament class
     * @param _currentPlayer the string of the person who captured last so they become the currentPlayer
     * @param _board the board from the serialization file
     * @param _deck the deck from the serialization file
     * @param _capturedLast the capturedLast String from the serialization file
     * Help Received : none
     */
    Round(Player _playerList[], String _currentPlayer, Board _board, Deck _deck, String _capturedLast){
        playerList = _playerList;
        CurrentPlayer = _currentPlayer;
        board = _board;
        deck = _deck;
        capturedLast = _capturedLast;
    }

    /**
     * Purpose : to set of the hands and board for the round to start
     * Help Received : none
     */
    public void SetupRound() {
        playerList[0].SetHand(deck.DealCards());
        playerList[1].SetHand(deck.DealCards());
        board.setBoard(deck.DealCards());
    }

    /**
     * Purpose : to check to see if the move being made is by the player
     * @param _move the move object being passed from the StartActivity
     * @return the result object returned frim the play call or result with false if the move was invalid
     */
    public Result MakeMove(Move _move){
        if(CurrentPlayer.equals("Human") && !_move.GetMoveKeyword().equals("Computer")){
            Result result = playerList[0].play(board, _move, capturedLast);
            if (result.GetSuccess()) {
                this.CurrentPlayer = "Computer";
                this.capturedLast = result.GetCapturedLast();
                RefreshHands();
                return result;
            }
            else{
                return result;
            }
        }
        else if(CurrentPlayer.equals("Computer") && _move.GetMoveKeyword().equals("Computer")){
           Result result = playerList[1].play(board, _move, capturedLast);
           this.CurrentPlayer = "Human";
           this.capturedLast = result.GetCapturedLast();
           RefreshHands();
           return result;
        }

        return new Result(false, "Invaild Move Here at the Player class", capturedLast);
    }

    /**
     * Purpose : to refresh the hands if they are both empty and the deck is not, or end the round
     * Help Received : none
     */
    public void RefreshHands(){
        if(playerList[0].hand.isEmpty() && playerList[1].hand.isEmpty() && !deck.EmptyDeck()){
            playerList[0].SetHand(deck.DealCards());
            playerList[1].SetHand(deck.DealCards());
        }
        else if(!playerList[0].hand.isEmpty() || !playerList[1].hand.isEmpty()){
           return;
        }
        else{
            EndRound();
        }
    }

    /**
     * Purpose : to give the table cards to the person who captured last and clear the board
     * Help Received : none
     */

    public void EndRound(){
        int pos = 0;
        if(capturedLast.equals("Computer")){
            pos = 1;
        }
        ArrayList<Card> temp = playerList[pos].GetPile();
        temp.addAll(board.GetBoard());
        playerList[pos].SetPile(temp);
        board.ClearBoard();
    }

    /**
     * Purpose : to save the board state
     * @param context the context of the activity
     * @param roundCount the round count from the tournament class
     * Help Received : Stack OverFlow for the file appending
     */

    public void saveGame(Context context, int roundCount) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "serialization");
            File saveFile = new File(root, "SaveFile.txt");
            FileWriter writer = new FileWriter(saveFile);
            writer.append("Round: ".concat(Integer.toString(roundCount)).concat("\n\n"));

            writer.append("Computer:\n");
            String computerScore = "    Score: ".concat(Integer.toString(playerList[1].GetScore())+"\n");
            writer.append(computerScore);
            String computerHand = "    Hand: ";
            for(Card card: playerList[1].GetHand()){
                computerHand = computerHand.concat(card.GetName() + " ");
            }
            writer.append(computerHand.concat("\n"));
            String computerPile = "    Pile: ";
            for(Card card: playerList[1].GetPile()){
                computerPile = computerPile.concat(card.GetName() + " ");
            }
            writer.append(computerPile.concat("\n\n"));

            writer.append("Human:\n");
            String humanScore = "    Score: ".concat(Integer.toString(playerList[0].GetScore())+"\n");
            writer.append(humanScore);
            String humanHand = "    Hand: ";
            for(Card card: playerList[0].GetHand()){
                humanHand = humanHand.concat(card.GetName() + " ");
            }
            writer.append(humanHand.concat("\n"));
            String humanPile = "    Pile: ";
            for(Card card: playerList[0].GetPile()){
                humanPile = humanPile.concat(card.GetName() + " ");
            }
            writer.append(humanPile.concat("\n\n"));

            String tableCards = "Table: ";
            for(Card card : board.GetBoard()){
                tableCards = tableCards.concat(card.GetName() + " ");
            }
            writer.append(tableCards.concat("\n\n"));

            for(Card card: board.GetBoard()){
                if(!card.isBuild()){
                    continue;
                }
                String build = "Build Owner: ";
                build = build.concat(card.GetName() + " " + card.getOwner() + "\n\n");
                writer.append(build);
            }

            String lastCapturer = "Last Capturer: ".concat(capturedLast + "\n\n");
            writer.append(lastCapturer);

            String _deck = "Deck: ";
            for(Card card: deck.GetDeck()){
                _deck = _deck.concat(card.GetName() + " ");
            }
            _deck = _deck.concat("\n\n");
            writer.append(_deck);

            String nextPlayer = "Next Player: ".concat(CurrentPlayer);
            writer.append(nextPlayer);

            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public Board GetBoard(){
        return this.board;
    }

    public Deck GetDeck(){
        return this.deck;
    }

    public Player[] GetPlayerList() {
        return this.playerList;
    }

    public String getCurrentPlayer(){
        return this.CurrentPlayer;
    }
}


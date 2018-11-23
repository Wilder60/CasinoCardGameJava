package andrew.opl.ramapo.edu.casino;

import android.content.Context;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Tournament implements Serializable {
    private int RoundCount = 1;
    private Player[] playerList = new Player[2];
    private String capturedLast = "";
    private Board board;
    private Deck deck;
    private String currentPlayer = "";

    private Round round;
    private ArrayList<String> Movelist;
    private Serialization serialization = new Serialization();

    private boolean loadFlag = false;

    /**
     * Purpose : The default constructor for the tournament class
     * Help Received : none
     */
    public Tournament(){
        Human human = new Human();
        Computer computer = new Computer();
        playerList[0] = human;
        playerList[1] = computer;
        Movelist = new ArrayList<>();
        board = new Board();
        deck = new Deck();
    }

    /**
     * Purpose : to load in a deck from a file and start a new round
     * Help Received : none
     */
    public void loadSeededDeck(){
        round = new Round(playerList, capturedLast, deck);
    }

    /**
     * Purpose : to load in a game without anything for a save file
     * Help Received : none
     */
    public void loadNewGame(){
        round = new Round(playerList, capturedLast);
    }

    /**
     * Purpose : to send the Move from the StartActivity to the Round class
     * @param move the move from the StartActivity class
     * @return result from the round.MakeMove
     * Help Received : none
     */
    public Result MakeMove(Move move){
        Result result = round.MakeMove(move);
        if(result.GetSuccess()){
            addMove(result.GetMoveReason());
        }
        if(round.GetDeck().EmptyDeck() && playerList[0].hand.isEmpty() && playerList[1].hand.isEmpty()){
            result.SetendOfRound(true);
        }
        return result;
    }

    /**
     * Purpose : to simulate a coin flipping to decide if the player goes first or not
     * @param _flip true if the head button was click, false if the tails button was clicked
     * @return ture if _flip is true and num == 0 or _flip is false and num == 1, elae false
     * Help Received : none
     */
    public boolean coinFlip(boolean _flip){
        Random rand = new Random();
        int num = rand.nextInt() % 2;

        if(_flip && num == 0){
            capturedLast = "Human";
            return true;
        }
        else if(!_flip && num == 1){
            capturedLast = "Human";
            return true;
        }
        else{
            capturedLast = "Computer";
            return false;
        }
    }

    /**
     * Purpose : to load the information from the serialization object to the tournament object
     * Help Received : none
     */
    public void populateTournament(){
        RoundCount = serialization.getRoundNumber();
        playerList[1].SetScore(serialization.getComputerScore());
        playerList[1].SetHand(serialization.getComputerHand());
        playerList[1].SetPile(serialization.getComputerPile());
        playerList[0].SetScore(serialization.getHumanScore());
        playerList[0].SetHand(serialization.getHumanHand());
        playerList[0].SetPile(serialization.getHumanPile());
        board.setBoard(serialization.getTableCards());
        capturedLast = serialization.getLastCaptuerer();
        deck.SetDeck(serialization.getDeck());
        currentPlayer = serialization.getCurrentPlayer();

        round = new Round(playerList, currentPlayer, board, deck, capturedLast);
    }

    public void saveGame(Context context){
        round.saveGame(context, RoundCount);
    }

    public void setDeck(ArrayList<Card> _deck){
        deck.SetDeck(_deck);
    }

    public void setLoadFlag(boolean flag){
        loadFlag = flag;
    }

    public void IncreaseRoundCount(){
        RoundCount++;
    }

    public void addMove(String _move){
        Movelist.add(_move);
    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean isLoadFlag(){
        return loadFlag;
    }

    public int GetRoundCount(){
        return RoundCount;
    }

    public Player[] GetPlayers(){
        return this.playerList;
    }

    public Player GetHuman(){
        return playerList[0];
    }

    public Player GetComputer(){
        return playerList[1];
    }

    public Round GetRound(){
        return this.round;
    }

    public ArrayList<String> getMovelist() {
        return this.Movelist;
    }

    /**
     * Purpose : to return the serialization file and set the loadflag to true so it can load the save file
     * @return the serialization file
     */
    public Serialization getSerialization(){
        loadFlag = true;
        return this.serialization;
    }

}
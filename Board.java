package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    private ArrayList<Card> board;

    /**
     * Purpose : The default constructor for the Board class
     * Help Received : none
     */
    Board(){
        board = new ArrayList<>();
    }

    public void DeleteCards(ArrayList<Card> _cardsToBeRemoved){
        board.removeAll(_cardsToBeRemoved);
    }

    public void ClearBoard(){
        board.clear();
    }

    public void SetCard(Card _card){
        board.add(_card);
    }

    public void setBoard(ArrayList<Card> _board){
        board = new ArrayList<>(_board);
    }

    public ArrayList<Card> GetBoard(){
        return board;
    }

}

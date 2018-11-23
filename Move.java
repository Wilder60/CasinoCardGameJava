package andrew.opl.ramapo.edu.casino;

import java.util.ArrayList;

public class Move {
    private String movekeyword;
    private ArrayList<Card> tablecards;
    private ArrayList<Card> handcards;

    /**
     * Purpose : the default constructor for the move class, this is never used
     * Help Received : none
     */
    public Move(){
        movekeyword = "";
        tablecards = new ArrayList<>();
        handcards = new ArrayList<>();
    }

    /**
     * Purpose : the constructor for the move class passing in values
     * @param _move the name of the move to be made
     * @param _tablecards the cards from the tables that the user selected
     * @param _handcards the cards from the hand that the users selected
     * Help Received : none
     */
    public Move(String _move, ArrayList<Card> _tablecards, ArrayList<Card> _handcards){
        SetMoveKeyword(_move);
        SetTableCards(_tablecards);
        SetHandCards(_handcards);
    }

    public String GetMoveKeyword(){
        return this.movekeyword;
    }

    public ArrayList<Card> GetTableCards(){
        return this.tablecards;
    }

    public ArrayList<Card> GetHandCards(){
        return this.handcards;
    }

    public void SetMoveKeyword(String _move){
        this.movekeyword = _move;
    }

    public void SetTableCards(ArrayList<Card> _tablecards){
        this.tablecards = new ArrayList<>(_tablecards);
    }

    public void SetHandCards(ArrayList<Card> _handcards){
        this.handcards = new ArrayList<>(_handcards);
    }

}

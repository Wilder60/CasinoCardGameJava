package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;
import java.util.ArrayList;


public class Human extends Player implements Serializable {

    /**
     * The default constructor for the Human class
     */
    Human(){
        this.name = "Human";
        this.score = 0;
    }

    /**
     * Purpose : The play function for the human to call the appropriate functions
     * @param _board the Board object being passed in
     * @param move the move object that was passed in from the StartActivity containing
     *             what move the user wants to make and what cards
     * @param _captuedLast The string containing the name of the user who captures last
     * @return returns the reuslt of the function it was called or returns false
     * Help Received : none
     */
    @Override
    public Result play(Board _board, Move move, String _captuedLast) {
        if(move.GetMoveKeyword().equals("Build")) {
            return buildCard(_board, move.GetTableCards(), move.GetHandCards(), _captuedLast);
        }
        else if(move.GetMoveKeyword().equals("Capture")) {
            return captureCards(_board, move.GetTableCards(), move.GetHandCards(), _captuedLast);
        }
        else if(move.GetMoveKeyword().equals("Trail")){
            return trailcard(_board, move.GetTableCards(), move.GetHandCards(), _captuedLast);
        }
        else if(move.GetMoveKeyword().equals("Help")){
            return getHelp(_board, _captuedLast);
        }

        return new Result(false, "Invalid Move Keyword", _captuedLast);
    }

    /**
     * Purpose : the function to create a valid build
     * @param _board the Board object containing board ArrayList
     * @param _tablecards the cards that were selected by the user from the board
     * @param _handcards the cards that were selected by user from the hand
     * @param _captuedLast the string containing that name of the person that captured last
     * @return Result with true if the build was successful else Result with false and error message
     * Help Received : none
     */
    public Result buildCard(Board _board, ArrayList<Card> _tablecards, ArrayList<Card> _handcards, String _captuedLast){
        if(_handcards.size() != 2){
            return new Result(false, "You need to select two cards",_captuedLast);
        }
        Card minCard, maxCard;

        if(_handcards.get(0).GetValue() > _handcards.get(1).GetValue()){
            maxCard = _handcards.get(0);
            minCard = _handcards.get(1);
        }
        else{
            maxCard = _handcards.get(1);
            minCard = _handcards.get(0);
        }

        int total = 0;
        for (Card card : _tablecards){
            total += card.GetValue();
        }

        if(maxCard.GetValue() - minCard.GetValue() != total){
            if(minCard.GetValue() != 1){
                return new Result(false, "Cards do not match", _captuedLast);
            }
            Card temp = minCard;
            minCard = maxCard;
            maxCard = temp;
            if((maxCard.GetValue() + 13) - minCard.GetValue() != total){
                return new Result(false, "Cards do not match", _captuedLast);
            }
        }
        RemoveFromHand(minCard);
        _board.DeleteCards(_tablecards);
        _tablecards.add(minCard);
        Build build = new Build(_tablecards, GetName());

        if(createMultibuild(_board, build)){
            return new Result(true, "You created a multibuild", _captuedLast);
        }
        _board.SetCard(build);
        return new Result(true, "You built with ".concat(minCard.GetName()).concat(" towards ").concat(maxCard.GetName()), _captuedLast);
    }

    /**
     * Purpose : the function used to create a MultiBuild if successful
     * @param _board The board object containing the board ArrayList
     * @param _build the build trying to be converted into a multibuild
     * @return true if multibuild was made, else false
     * Help Received : None
     */
    public boolean createMultibuild(Board _board, Build _build){
        ArrayList<Card> tempBoard = new ArrayList<Card>(_board.GetBoard());

        for(Card card : tempBoard){
            if(card.isBuild() && card.GetValue() == _build.GetValue() && card.getOwner().equals(GetName())){
                Build build = (Build) card;
                ArrayList<Build> buildList = new ArrayList<>();
                buildList.add(build);
                buildList.add(_build);
                Multibuild multibuild = new Multibuild(buildList);
                tempBoard.remove(card);
                tempBoard.add(multibuild);
                _board.setBoard(tempBoard);
                return true;
            }
        }
        return false;
    }

    /**
     * Purpose : to captured the cards that the user selected to capture
     * @param _board the board object that contains the board ArrayList
     * @param _tablecards the cards that the user selected on the table
     * @param _handcards the cards that user selected from the hand
     * @param _captuedLast the string containing the name of the person that captured last
     * @return Result ture if the caputre what valid, false if not valid
     * Help Received : none
     */
    public Result captureCards(Board _board, ArrayList<Card> _tablecards, ArrayList<Card> _handcards, String _captuedLast){
        Result result = isValidCapture(_board, _tablecards, _handcards, _captuedLast);
        if(!result.GetSuccess()){
            return result;
        }
        Card targetCard = _handcards.get(0);
        ArrayList<Card> tempBoard = _board.GetBoard();
        for(Card card : _tablecards){
            if(card.isBuild()){
                Build build = (Build) card;
                pile.addAll(build.getBuildCards());
            }
            else{
                pile.add(card);
            }
        }
        tempBoard.removeAll(_tablecards);
        pile.add(targetCard);
        hand.remove(targetCard);
        _board.setBoard(tempBoard);
        return new Result(true, "You captured with ".concat(_handcards.get(0).GetName()), GetName());
    }

    /**
     * Purpose : To check to see if the capture the user is trying to do is valid or not
     * @param _board the board object that contains the board ArrayList
     * @param _tableCards the cards that the user selected on the table
     * @param _handCards the cards that user selected from the hand
     * @param _captuedLast the string containing the name of the person that captured last
     * @return Result with true if valid capture else Result with false and error message
     */
    public Result isValidCapture(Board _board, ArrayList<Card> _tableCards, ArrayList<Card> _handCards, String _captuedLast){
        if(_handCards.size() != 1){
            return new Result(false, "You need to selected one card", _captuedLast);
        }
        Card targetCard = _handCards.get(0);
        int numberOfAces = 0;
        int cardvalue;
        int temp = 0;
        for(Card card : _tableCards) {
            if (card.isBuild() && card.GetValue() != targetCard.GetValue()) {
                return new Result(false, "You cannot capture with a build with a different value.", _captuedLast);
            } else {
                temp += card.GetValue();
                if (card.GetName().charAt(1) == 'a') {
                    numberOfAces++;
                }
            }
        }
        if(targetCard.GetName().charAt(1) == 'a'){
            cardvalue = 14;
        }
        else{
            cardvalue = targetCard.GetValue();
        }
        while(numberOfAces > 0){
            if(cardvalue % temp + (13 * numberOfAces) == 0){
                break;
            }
            numberOfAces--;
        }
        if(numberOfAces < 0){
            return new Result(false, "Invalid card sets", _captuedLast);
        }
        ArrayList<Card> tempBoard = new ArrayList<>(_board.GetBoard());
        tempBoard.removeAll(_tableCards);
        for(Card card : tempBoard){
            if(card.getOwner().equals(GetName()) && targetCard.GetValue() == card.GetValue()){
                return new Result(false, "Cards on table needs to be captured", _captuedLast);
            }
            else if(card.getOwner().equals("NONE") && targetCard.GetValue() == card.GetValue()){
                return new Result(false, "Cards on table needs to be captured", _captuedLast);
            }
        }
        return new Result(true, "This doesn't matter", _captuedLast);
    }

    /**
     * Purpose : to trail a card from the users hand
     * @param _board the board object that contains the board ArrayList
     * @param _tableCards the cards that the user selected on the table
     * @param _handCards the cards that user selected from the hand
     * @param _captuedLast the string containing the name of the person that captured last
     * @return Result with true if the isValidTrail returns success or return the Result of isValidTrail
     */
    public Result trailcard(Board _board, ArrayList<Card> _tableCards, ArrayList<Card> _handCards, String _captuedLast){
        Result result = isValidTrail(_board, _tableCards, _handCards, _captuedLast);
        if(!result.GetSuccess()){
            return result;
        }

        Card _trailCard = _handCards.get(0);
        ArrayList<Card> temp = _board.GetBoard();
        temp.add(_trailCard);
        _board.setBoard(temp);
        RemoveFromHand(_trailCard);

        return new Result(true, "You trailed with ".concat(_trailCard.GetName()), _captuedLast);
    }

    /**
     * Purpose : to check to see if the trail is valid or not
     * @param _board the board object that contains the board ArrayList
     * @param _tableCards the cards that the user selected on the table
     * @param _handCards the cards that user selected from the hand
     * @param _captuedLast the string containing the name of the person that captured last
     * @return Result with true if it was a valid trail, else Result with false and the reason why
     */

    public Result isValidTrail(Board _board, ArrayList<Card> _tableCards, ArrayList<Card> _handCards, String _captuedLast){
        if(_handCards.size() != 1){
            return new Result(false, "You need to select one card", _captuedLast);

        }
        Card trailCard = _handCards.get(0);

        for( Card card: _board.GetBoard() ){
            if( card.getOwner().equals(GetName()) ){
                return new Result(false, "You can not trail if you own a build", _captuedLast);
            }
            if( card.GetValue() == trailCard.GetValue() ){
                return new Result(false, "You can not trail this card with loose cards of the same value!", _captuedLast);
            }
        }
        return new Result(true, "This doesn't matter", _captuedLast);
    }

    /**
     * Purpose : to get help from the AI generate move
     * @param _board The board object that contains that board ArrayList
     * @param _captuedLast the string containing the name of the person that captured last
     * @return Result with false and the Ai recommend move
     */

    public Result getHelp(Board _board, String _captuedLast){
        Aimove aimove = generateMove(_board.GetBoard(), GetHand());

        return new Result(false, aimove.getRecommendMove().concat(". ").concat(aimove.getReasonWhy()), _captuedLast);
    }

}

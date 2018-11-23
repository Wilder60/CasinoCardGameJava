package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Player implements Serializable {

    protected String name;
    protected ArrayList<Card> hand = new ArrayList<>();
    protected ArrayList<Card> pile = new ArrayList<>();
    protected int score;
    protected String moveReason;

    /**
     * Purpose : The default constructor for the player class
     * Help Received : none
     */

    Player(){
        this.name = "NULL";
        this.score = 0;
    }

    /**
     * Purpose : The fucntion that gets overloaded in the player and the computer class
     * @param _board the Board object being passed in
     * @param move the move object that was passed in from the StartActivity containing
     *             what move the user wants to make and what cards
     * @param _captureLast the string of the players name that captured last, if the move is capture
     *                    it will update to the Player's name
     * @return Result returns a generic move because it will never be reaches
    */
    public Result play(Board _board, Move move, String _captureLast){
        return new Result(true, "", _captureLast);
    }

    /**
     * Purpose : The function that trails a card from the hand to the board
     * @param _board the Board object containing the board
     * @param _trailCard the card that is going to be trailed to the board
     * @param _capturedLast the string of the players name that captued last, if the move is capture
     *                      it will update ti the Player's name
     * @return  Result returns a successful Result containing the string of what the user did
     * Help Received : none
     */
    public Result trailcard(Board _board, Card _trailCard, String _capturedLast){
        ArrayList<Card> temp = _board.GetBoard();
        temp.add(_trailCard);
        _board.setBoard(temp);
        RemoveFromHand(_trailCard);

        return new Result(true, "Computer trailed with ".concat(_trailCard.GetName()),_capturedLast);
    }

    /**
     * Purpose : The function that generates an AImove object to return the the appropriate place
     * @param _board The ArrayList that is contained in the Board object
     * @param _hand The ArrayList of the hand of the player that is calling generate room
     * @return Aimove The object that that conatins the move, the card to make the move and the reason why
     * Help Received : none
     */
    public Aimove generateMove(ArrayList<Card> _board, ArrayList<Card> _hand){
        Aimove aimove = canBuild(_board, _hand);
        if(aimove.getAIMove().equals("BUILD")){
            return aimove;
        }
        aimove = canCapture(_board, _hand);
        if(aimove.getAIMove().equals("CAPTURE")){
            return aimove;
        }

        aimove = canTrail(_board, _hand);
        if(aimove.getAIMove().equals("TRAIL")){
            return aimove;
        }

        return new Aimove();
    }

    /**
     * Purpose : The function that generates an Aimove if a valid build can be made else if returns
     *              an invalid aimove
     * @param _board The ArrayList that is contained in the Board object
     * @param _hand The ArrayList of the hand of the player that is calling generate room
     * @return Aimove that contains the "BUILD" or "NONE"
     * Help Received : none
     */

    public Aimove canBuild(ArrayList<Card> _board, ArrayList<Card> _hand){
        ArrayList<ArrayList<Card>> cardlist = new ArrayList<>();
        ArrayList<Card> cBoard = new ArrayList<>(_board);
        ArrayList<Card> yourBuilds = getYourBuilds(cBoard);
        ArrayList<Card> c_hand = pruneHand(yourBuilds, hand);

        //checking to see if you can make a build that will convert into a multibuild
        if (!yourBuilds.isEmpty()) {
            for (int i = 0; i < yourBuilds.size(); i++) {
                for (int j = 0; j < c_hand.size(); j++) {
                    rCaptureSets(cBoard, yourBuilds.get(i).GetValue(), new ArrayList<>(Arrays.asList(c_hand.get(j))), cardlist);
                    if (!cardlist.isEmpty()) {
                        return new Aimove("BUILD", "The AI wants to make a MultiBuild towards ".concat(Integer.toString(yourBuilds.get(i).GetValue())),
                                "It wants to make a MultiBuild to maximize the number of cards taken",
                                yourBuilds.get(i));
                    }
                }
            }
        }

        //checking to see if you can add a card that will extend an opponents build
        ArrayList<Card> opponentsBuild = getOpponentsBuild(cBoard);
        if(!opponentsBuild.isEmpty()){
            for(Card build : opponentsBuild){
                for(Card cCard : c_hand){
                    for(Card tCard : hand){
                        if(build.GetValue() + cCard.GetValue() == tCard.GetValue()){
                            return new Aimove("EXTENDBUILD", "The AI wants to extend an opponents build with ".concat(cCard.GetName() + " towards" + tCard.GetName()),
                                    "The AI wants to steall a Build from an opponent", build);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < c_hand.size(); i++) {
            for (int j = 0; j < c_hand.size(); j++) {
                if (i == j) {
                    continue;
                }
                rCaptureSets(cBoard, c_hand.get(i).GetValue(), new ArrayList<>(Arrays.asList(c_hand.get(j))), cardlist);
                if (!cardlist.isEmpty()) {
                    return new Aimove("BUILD", "The Ai wants to make a Build ".concat(c_hand.get(i).GetName() + ". "),
                            "It wants to secure cards from their opponent.", c_hand.get(i));
                }
            }
        }
        return new Aimove("none", "","", new Card());
    }

    /**
     * Purpose : To return an ArrayList containing all of your Builds
     * @param _cboard the Arraylist contain the board from the Board object
     * @return returns an ArrayList that contains all of the board
     * Help Received : none
     */

    public ArrayList<Card> getYourBuilds(ArrayList<Card> _cboard){
        ArrayList<Card> builds = new ArrayList<>();
        for(Card card: _cboard){
            if(card.isBuild() && card.getOwner().equals(GetName())){
                builds.add(card);
            }
        }
        _cboard.removeAll(builds);
        return builds;
    }

    /**
     * Purpose : To return a ArrayList that has all the cards that aren't the value of a build
     * @param _builds The ArrayList that contains all the Builds
     * @param _hand The ArrayList that contains all the hand cards from the user
     * @return ArrayList that has all the free cards from the hand
     * Help Received : none
     */

    public ArrayList<Card> pruneHand(ArrayList<Card> _builds, ArrayList<Card> _hand){
        ArrayList<Card> temphand = new ArrayList<>(_hand);
        ArrayList<Card> removeCards = new ArrayList<>();
        for(Card build : _builds){
            for(Card card : _hand){
                if(build.GetValue() == card.GetValue()){
                    removeCards.add(card);
                    break;
                }
            }
        }
        temphand.removeAll(removeCards);
        return temphand;
    }

    /**
     * Purpose : To return a ArrayList that contains a only the opponents builds
     * @param cBoard a ArrayList that contains a copy of the board
     * @return ArrayList that contains all the opponents builds
     * Help Received : none
     */

    public ArrayList<Card> getOpponentsBuild(ArrayList<Card> cBoard){
        ArrayList<Card> opponentsHand = new ArrayList<>();
        for(Card card : cBoard){
            if(card.isBuild() && card.isMultiBuild() && !card.getOwner().equals(GetName())){
                opponentsHand.add(card);
            }
        }
        return opponentsHand;
    }

    /**
     * Purpose : To extend an opponents build
     * @param _board the Board object
     * @param _aimove the Aimove that what created in generateMove
     * @param _captureLast the String of the last captued person
     * @return a Result with true if the extend was successful else Result with false
     * Help Received : none
     */

    public Result extendBuild(Board _board, Aimove _aimove, String _captureLast){
        ArrayList<Card> c_board = new ArrayList<>(_board.GetBoard());
        ArrayList<Card> freeHand = pruneHand(_board.GetBoard(), GetHand());
        Card targetBuildCard = _aimove.getCardToMakeMove();

        for(Card card1 : freeHand){
            for(Card card2 : hand){
                if(card1.GetValue() + targetBuildCard.GetValue() == card2.GetValue()){
                    ArrayList<Card> extendBuildCards = new ArrayList<>(Arrays.asList(targetBuildCard, card1));
                    Build build = new Build(extendBuildCards, GetName());
                    c_board.add(build);
                    c_board.remove(targetBuildCard);
                    hand.remove(card1);
                    _board.setBoard(c_board);
                    return new Result(true, _aimove.getRecommendMove().concat(_aimove.getReasonWhy()), _captureLast);
                }
            }
        }
        return new Result(false, "", _captureLast);
    }

    /**
     * Purpose : To create a build and add it the table
     * @param _board The board object from the Round class
     * @param _aimove The aimove that was given in the generatemove
     * @param _captuedLast The string that contained the name of the player who captured last
     * @return Result with true and the message to be displayed to the screen
     * Help Received : none
     */

    public Result createBuild(Board _board, Aimove _aimove, String _captuedLast){
        ArrayList<ArrayList<Card>> cardlist = new ArrayList<>();
        ArrayList<Card> c_board = new ArrayList<>(_board.GetBoard());
        ArrayList<Card> yourBuilds = getYourBuilds(c_board);
        Card targetCard = _aimove.getCardToMakeMove();
        ArrayList<Card> c_hand = pruneHand(yourBuilds, hand);
        c_hand.remove(targetCard);

        for (Card card : c_hand) {
            rCaptureSets(c_board, targetCard.GetValue(), new ArrayList<>(Arrays.asList(card)), cardlist);
        }

        Build build = new Build(cardlist.get(0), GetName());
        c_board.removeAll(cardlist.get(0));
        c_hand.removeAll(cardlist.get(0));

        Result result = createMultiBuild(_board, build, _captuedLast);
        hand.removeAll(cardlist.get(0));
        if(result.GetSuccess()){

            return result;
        }
        c_board.add(build);
        _board.setBoard(c_board);
        return new Result(true, _aimove.getRecommendMove().concat(". ").concat(_aimove.getReasonWhy()), _captuedLast);

    }

    /**
     * Purpose : To create a MultiBuild
     * @param _board The board object from the model
     * @param _build The build that is passed in from createBuild
     * @param _captuedLast The string that contains the name of the player who captured last
     * @return new Result with true if a mutlibuild is create or false if it doesn't
     * Help Received : none
     */

    public Result createMultiBuild(Board _board, Build _build, String _captuedLast){
        ArrayList<Card> c_board = new ArrayList<>(_board.GetBoard());
        for(Card card : c_board){
            if(card.isBuild() && !card.isMultiBuild() && card.getOwner().equals(GetName()) && card.GetValue() == _build.GetValue()){
                Build build = (Build) card;
                ArrayList<Build>buildList = new ArrayList<>(Arrays.asList(_build, build));
                Multibuild multibuild = new Multibuild(buildList);
                c_board.remove(build);
                c_board.add(multibuild);
                _board.setBoard(c_board);
                return new Result(true, "The Ai created a new MutliBuild with two builds", _captuedLast);
            }
            else if(card.isMultiBuild() && card.getOwner().equals(GetName()) && card.GetValue() == _build.GetValue()){
                Multibuild _multibuild = (Multibuild) card;
                Multibuild multibuild = new Multibuild(_multibuild, _build);
                c_board.remove(_multibuild);
                c_board.add(multibuild);
                _board.setBoard(c_board);
                return new Result(true, "The Ai added to a MultiBuild with a Build", _captuedLast);
            }
        }
        return new Result(false, "", _captuedLast);
    }

    /**
     * Purpose : To determine if the AI can capture any cards
     * @param _board The ArrayList that contains the board cards
     * @param _hand The hand from the user
     * @return Aimove with CAPTURE if cards exist to be captued else returns ""
     * Help Received : none
     */

    public Aimove canCapture(ArrayList<Card> _board, ArrayList<Card> _hand){
        ArrayList<ArrayList<Card>> cardlist = new ArrayList<>();
        int maxpairs = 0;
        int numOfCards = 0;
        Card targetCard = new Card();

        for(Card card : _hand){
            int samevalue = 0;
            for(Card card1 : _board){
                if(card1.GetValue() == card.GetValue()){
                    samevalue++;
                }
            }
            ArrayList<Card> cBoard = removeBuilds(_board);
            if(card.GetValue() == 1){
                rCaptureSets(cBoard, card.GetValue() + 13, new ArrayList<Card>(), cardlist);
            }
            else{
                rCaptureSets(cBoard, card.GetValue(), new ArrayList<Card>(), cardlist);
            }

            for(int i = 0; i < cardlist.size(); i++){
                numOfCards += cardlist.get(i).size();
            }

            if(numOfCards+ samevalue > maxpairs){
                targetCard = card;
                maxpairs = cardlist.size() + samevalue;
            }
            cardlist.clear();
            numOfCards = 0;
        }

        if (maxpairs == 0){
            return new Aimove();
        }
        return new Aimove("CAPTURE", "The AI wanted to capture with ".concat(targetCard.GetName()),
                "It wanted to maximumize the number of captures", targetCard);

    }

    /**
     * Purpose : To capture all the cards and sets with a specific card
     * @param _board The Board object from the model
     * @param _aimove The Aimove that is passed in from the generatemove function
     * @return returns Reault with true
     * Help Received : none
     */

    public Result captureSets(Board _board, Aimove _aimove){
        Card capturecard = _aimove.getCardToMakeMove();
        ArrayList<Card> tempBoard = _board.GetBoard();
        ArrayList<ArrayList<Card>> cardSets = new ArrayList<>();
        ArrayList<Card> cardsToBeAddedToPile = new ArrayList<>();
        do{
            cardSets.clear();
            ArrayList<Card> cBoard = removeBuilds(_board.GetBoard());
            if(capturecard.GetValue() == 1) {
                rCaptureSets(cBoard, capturecard.GetValue() + 13, new ArrayList<Card>(), cardSets);
            }
            else{
                rCaptureSets(cBoard, capturecard.GetValue(), new ArrayList<Card>(), cardSets);
            }
            if(cardSets.size() != 0){
                tempBoard.removeAll(cardSets.get(0));
                cardsToBeAddedToPile.addAll(cardSets.get(0));
            }
        }while(cardSets.size() != 0);

        ArrayList<Card> cardToBeRemoved = new ArrayList<>();

        for(Card card : tempBoard){
            if(card.GetValue() == capturecard.GetValue()){
                cardsToBeAddedToPile.add(card);
                cardToBeRemoved.add(card);
            }
        }

        for(Card card : cardsToBeAddedToPile){
            if(card.isBuild()){
                Build build = (Build) card;
                pile.addAll(build.getBuildCards());
            }
            else {
                pile.add(card);
            }
        }

        tempBoard.removeAll(cardToBeRemoved);
        _board.setBoard(tempBoard);
        hand.remove(capturecard);
        pile.add(capturecard);
        return new Result(true, _aimove.getRecommendMove().concat(". ").concat(_aimove.getReasonWhy()), GetName());
    }

    /**
     * Purpose : To remove all builds from the table and return a copy of it
     * @param _board The ArrayList that is the copy of the board object's board
     * @return ArrayList the board with only loose cards
     * Help Received : none
     */

    public ArrayList<Card> removeBuilds(ArrayList<Card> _board){
        ArrayList<Card> tempBoard = new ArrayList<>(_board);
        ArrayList<Card> cardToBeRemoved = new ArrayList<>();
        for(Card card : tempBoard){
            if(card.isBuild()){
                cardToBeRemoved.add(card);
            }
        }
        tempBoard.removeAll(cardToBeRemoved);
        return tempBoard;
    }

    /**
     * Purpose : canTrail
     * @param _board a ArrayList containing the cards from the board object
     * @param _hand a ArrayList that contains the hand cards of the player
     * @return returns an Aimove with "TRAIL"
     * Help Received : none
     */

    public Aimove canTrail(ArrayList<Card> _board, ArrayList<Card> _hand){
        return new Aimove("TRAIL", "The AI wants to trail a card",
                "It had no other move to play", _hand.get(0));
    }

    /**
     * Purpose : to find all sets that equal the target
     * @param _board The ArrayList that contains
     * @param target The value that the partial is aiming for
     * @param _partial The ArrayList contains all cards that will be summed
     * @param _cardlist The ArrayList that contains all the summed sets
     * Help Received : Algorithm from StackOverflow
     */

    public void rCaptureSets(ArrayList<Card> _board, int target, ArrayList<Card> _partial, ArrayList<ArrayList<Card>> _cardlist){
        int total = 0;
        for(Card card : _partial){
            total += card.GetValue();
        }
        if(total == target){
            _cardlist.add(_partial);
        }
        if(total >= target){
            return;
        }

        for(int i = 0; i < _board.size(); i++){
            ArrayList<Card> remaining = new ArrayList<>();
            Card card = _board.get(i);
            for(int j = i+1; j < _board.size(); j++){
                remaining.add(_board.get(j));
            }
            ArrayList<Card> partical_rec = new ArrayList<>(_partial);
            partical_rec.add(card);
            rCaptureSets(remaining, target, partical_rec, _cardlist);
        }
    }

    public void RemoveFromHand(Card _cardtobedeleted){
        hand.remove(_cardtobedeleted);
    }

    public String GetName(){
        return this.name;
    }

    public int GetScore(){
        return this.score;
    }

    public int GetPileSize(){
        return this.pile.size();
    }

    public ArrayList<Card> GetHand(){
        return this.hand;
    }

    public ArrayList<Card> GetPile(){
        return this.pile;
    }

    public void SetHand(ArrayList<Card> _cards){
        hand = new ArrayList<>(_cards);
    }

    public void SetScore(int _points){
        this.score = _points;
    }

    public void SetPile(ArrayList<Card> _pile){
        this.pile = _pile;
    }

    public void IncreaseScore(int _score){
        this.score += _score;
    }

    public void ClearPile(){
        this.pile.clear();
    }

}

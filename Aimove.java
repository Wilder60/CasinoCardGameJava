package andrew.opl.ramapo.edu.casino;

public class Aimove {
    private String AImove;
    private String recommendMove;
    private String reasonWhy;
    private Card cardToMakeMove;

    /**
     * Purpose : The default constructor for the Aimove class
     * Help Received : none
     */

    Aimove(){
        this.AImove ="None";
        this.recommendMove = "None";
        this.reasonWhy = "None";
        this.cardToMakeMove = new Card();
    }

    /**
     * Purpose : A constructor for the Aimove class
     * @param _move the name of the move that was made
     * @param _recommendMove the description of the move that was made
     * @param _reasonWhy the reason why the move was made
     * @param _cardToMakeMove the card that the move should be made with
     * Help Received : none
     */

    Aimove(String _move, String _recommendMove, String _reasonWhy, Card _cardToMakeMove){
        this.AImove = _move;
        this.recommendMove = _recommendMove;
        this.reasonWhy = _reasonWhy;
        this.cardToMakeMove = _cardToMakeMove;
    }

    public String getAIMove(){
        return this.AImove;
    }

    public String getRecommendMove(){
        return this.recommendMove;
    }

    public String getReasonWhy(){
        return this.reasonWhy;
    }

    public Card getCardToMakeMove(){
        return this.cardToMakeMove;
    }
}

package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;

public class Computer extends Player implements Serializable {

    /**
     * Purpose : The default constructor for the Computer class
     * Help Received : none
     */
    Computer(){
        this.name = "Computer";
        this.score = 0;
    }

    /**
     * Purpose : to determine what move to make from the Aimove
     * @param _board the Board object being passed in
     * @param move the move object that was passed in from the StartActivity containing
     *             what move the user wants to make and what cards
     * @param _captuedLast the string of the player that just captured last
     * @return Result with from the other function or a Result with false
     */
    @Override
    public Result play(Board _board, Move move, String _captuedLast){
        Aimove aimove = generateMove(_board.GetBoard(), GetHand());

        if(aimove.getAIMove().equals("EXTENDBUILD")){
            return extendBuild(_board, aimove, _captuedLast);
        }
        else if(aimove.getAIMove().equals("BUILD")){
            return createBuild(_board, aimove, _captuedLast);
        }
        else if (aimove.getAIMove().equals("CAPTURE")){
            return captureSets(_board, aimove);
        }
        else if(aimove.getAIMove().equals("TRAIL")){
            return trailcard(_board, aimove.getCardToMakeMove(), _captuedLast);
        }

        return new Result(false, "You shouldn't be seeing this", _captuedLast);
    }

}

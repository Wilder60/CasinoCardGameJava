package andrew.opl.ramapo.edu.casino;

public class Result {

    private boolean success;
    private String moveReason;
    private boolean endOfRound = false;
    private String capturedLast;


    /**
     * The constructor for the Result class to add information to the class
     * @param _success true if the move was successful or false if not
     * @param _moveReason the String that contains what the computer did and the reason why
     * @param _capturedLast the String of the players name that captured last
     * Help Received : none
     */
    Result(boolean _success, String _moveReason, String _capturedLast){
        SetSuccess(_success);
        SetMoveReason(_moveReason);
        SetCapturedLast(_capturedLast);
    }

    public void SetCapturedLast(String _capturedLast){
        this.capturedLast = _capturedLast;
    }

    public void SetendOfRound(boolean _endOfRound){
        this.endOfRound = _endOfRound;
    }

    public void SetSuccess(boolean _success){
        this.success =_success;
    }

    public void SetMoveReason(String _moveReason){
        this.moveReason = _moveReason;
    }

    public String GetCapturedLast(){
        return this.capturedLast;
    }

    public boolean isEndOfRound() {
        return endOfRound;
    }

    public boolean GetSuccess(){
        return this.success;
    }

    public String GetMoveReason(){
        return this.moveReason;
    }
}

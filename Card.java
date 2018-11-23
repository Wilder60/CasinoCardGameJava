package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;
import java.lang.String;

public class Card implements Serializable {

    private String Name;
    private int Value;
    private int CardID;
    private int CheckCardID;
    private String owner;

    /**
     * Purpose : The default constructor for the Cards class
     * Help Received : none
     */
    public Card(){
        this.Value = -1;
        this.Name = "NULL";
        this.owner = "NONE";
    }

    /**
     * The constructor for the card class to create a playing card with values
     * @param _CardName the name of card being passed in
     * @param _value the value of the card that is being created from the map
     * @param _ID the _ID of the image for the card
     * @param _CheckCardID the _ID of the checked image of the card
     * Help Received : none
     */
    public Card(String _CardName, int _value, int _ID, int _CheckCardID){
        SetName(_CardName);
        SetValue(_value);
        SetCardID(_ID);
        SetCheckCardID(_CheckCardID);
        this.owner = "NONE";
    }

    public boolean isBuild(){
        return false;
    }

    public boolean isMultiBuild(){
        return false;
    }

    public void SetName(String _name){
        this.Name = _name;
    }

    public void SetValue(int _value){
        this.Value = _value;
    }

    public void SetCardID(int _ID){
        this.CardID = _ID;
    }

    public void SetCheckCardID(int _CheckCardID){
        this.CheckCardID = _CheckCardID;
    }

    public String GetName(){
        return this.Name;
    }

    public int GetValue() {
        return this.Value;
    }

    public int getCardID(){
        return this.CardID;
    }

    public int GetCheckCardID(){
        return this.CheckCardID;
    }

    public String getOwner(){
        return this.owner;
    }
}



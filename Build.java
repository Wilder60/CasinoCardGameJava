package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;

public class Build extends Card implements Serializable {
    private ArrayList<Card> cardList = new ArrayList<>();
    private String owner;

    /**
     * Purpose : the default constructor for the Build class
     */

    public Build(){
        SetValue(0);
        setOwner("NULL");
    }

    /**
     * Purpose : the constructor for creating a build from an ArrayList of cards or builds
     * @param _buildCards The ArrayList containing the cards
     * @param _owner The owner of the Build that is being created
     * Help Received : none
     */

    public Build(ArrayList<Card> _buildCards, String _owner){
        String buildName = "[";
        ArrayList<Card> tempCardList = new ArrayList<>();
        int total = 0;
        for (int i = 0; i < _buildCards.size(); i++) {
            if (_buildCards.get(i).isBuild()) {
                Build build = (Build) _buildCards.get(i);
                String name = build.GetName();
                name = name.substring(1, name.length() -1);
                buildName =buildName.concat(name + " ");
                total += build.GetValue();
                ArrayList<Card> temp = build.getBuildCards();
                for (int j = 0; j < temp.size(); j++) {
                    tempCardList.add(temp.get(i));
                }

            }
		else {
                buildName = buildName.concat(_buildCards.get(i).GetName() + " ");
                total += _buildCards.get(i).GetValue();
                tempCardList.add(_buildCards.get(i));
            }
        }
        buildName = buildName.substring(0 , buildName.length() -1);
        buildName += "]";
        SetName(buildName);
        SetValue(total);
        setBuildCards(tempCardList);
        setOwner(_owner);
    }

    /**
     * Purpose : to check if it is a build or not
     * @return : true... because it is a build
     */

    @Override
    public  boolean isBuild(){
        return true;
    }

    /**
     * Purpose : to check if it a build or not
     * @return : false because it is not a MultiBuild
     */

    @Override
    public  boolean isMultiBuild(){
        return false;
    }

    public void setBuildCards(ArrayList<Card> _buildCards){
        cardList = new ArrayList<>(_buildCards);
    }

    public void setOwner(String _owner){
        this.owner = _owner;
    }

    public ArrayList<Card> getBuildCards(){
        return this.cardList;
    }

    @Override
    public String getOwner(){
        return this.owner;
    }
}

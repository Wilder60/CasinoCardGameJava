package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;
import java.util.ArrayList;


public class Multibuild extends Build implements Serializable {

    /**
     * Purpose : the constructor to create a MultiBuild of a ArrayList of Builds
     * @param _buildList the ArrayList of the Builds
     */
    //function for creating a new multibuild
    public Multibuild(ArrayList<Build> _buildList){
        ArrayList<Card> multiBuildCards = new ArrayList<>();
        String tempName = "[ ";

        for( Build build : _buildList){
            tempName = tempName.concat(build.GetName() + " ");
            multiBuildCards.addAll(build.getBuildCards());
        }
        tempName = tempName.concat("]");
        this.SetName(tempName);
        this.setBuildCards(multiBuildCards);
        this.SetValue(_buildList.get(0).GetValue());
        this.setOwner(_buildList.get(0).getOwner());
    }

    /**
     * Purpose : the constructor to extend a MultiBuild with an another Build
     * @param _multibuild the MultiBuild being extended
     * @param _build the Build that is being added to the MultiBuild
     */
    //function for extending a multibuild with a build
    public Multibuild(Multibuild _multibuild, Build _build){
        String tempName = _multibuild.GetName();
        tempName = tempName.substring(0, tempName.length() -1);
        tempName = tempName.concat(_build.GetName() +" ]");
        SetName(tempName);

        this.SetValue(_multibuild.GetValue());
        this.setOwner(_multibuild.getOwner());

        ArrayList<Card> buildCards = new ArrayList<>(_multibuild.getBuildCards());
        buildCards.addAll(_build.getBuildCards());
        this.setBuildCards(buildCards);
    }

    public boolean isBuild(){
        return true;
    }

    public boolean isMultiBuild(){
        return true;
    }

}

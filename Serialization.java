package andrew.opl.ramapo.edu.casino;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Serialization implements Serializable {

    private int roundNumber = 0;
    private int computerScore = 0;
    private ArrayList<Card> computerHand;
    private ArrayList<Card> computerPile;
    private int humanScore = 0;
    private ArrayList<Card> humanHand;
    private ArrayList<Card> humanPile;
    private ArrayList<Card> tableCards;
    private String lastCaptuerer;
    private ArrayList<Card> deck;
    private String currentPlayer;

    private static Map<Character, Integer> CardtoValue = createValueMap();
    /**
     * Purpose : to create a map that is populated with values of cards and their corrisponding letter
     * @return the Map that is populated with the values
     * Help Received : Stack Overflow on how to properly init a map
     */
    private static Map<Character, Integer> createValueMap(){
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 1);
        map.put('2', 2);
        map.put('3', 3);
        map.put('4', 4);
        map.put('5', 5);
        map.put('6', 6);
        map.put('7', 7);
        map.put('8', 8);
        map.put('9', 9);
        map.put('X', 10);
        map.put('J', 11);
        map.put('Q', 12);
        map.put('K', 13);
        return map;
    }

    private static Map<String, Integer> CardtoID = createIDMap();
    /**
     * Purpose : to create map that is populated with the image ID and their corrisponding name
     * @return the Map that is populated with the values added to the map
     * Help Received : Stack Overflow on how to properly init a map
     */
    private static Map<String, Integer> createIDMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("CA", R.drawable.ca);
        map.put("C2", R.drawable.c2);
        map.put("C3", R.drawable.c3);
        map.put("C4", R.drawable.c4);
        map.put("C5", R.drawable.c5);
        map.put("C6", R.drawable.c6);
        map.put("C7", R.drawable.c7);
        map.put("C8", R.drawable.c8);
        map.put("C9", R.drawable.c9);
        map.put("CX", R.drawable.cx);
        map.put("CJ", R.drawable.cj);
        map.put("CQ", R.drawable.cq);
        map.put("CK", R.drawable.ck);
        map.put("HA", R.drawable.ha);
        map.put("H2", R.drawable.h2);
        map.put("H3", R.drawable.h3);
        map.put("H4", R.drawable.h4);
        map.put("H5", R.drawable.h5);
        map.put("H6", R.drawable.h6);
        map.put("H7", R.drawable.h7);
        map.put("H8", R.drawable.h8);
        map.put("H9", R.drawable.h9);
        map.put("HX", R.drawable.hx);
        map.put("HJ", R.drawable.hj);
        map.put("HQ", R.drawable.hq);
        map.put("HK", R.drawable.hk);
        map.put("SA", R.drawable.sa);
        map.put("S2", R.drawable.s2);
        map.put("S3", R.drawable.s3);
        map.put("S4", R.drawable.s4);
        map.put("S5", R.drawable.s5);
        map.put("S6", R.drawable.s6);
        map.put("S7", R.drawable.s7);
        map.put("S8", R.drawable.s8);
        map.put("S9", R.drawable.s9);
        map.put("SX", R.drawable.sx);
        map.put("SJ", R.drawable.sj);
        map.put("SQ", R.drawable.sq);
        map.put("SK", R.drawable.sk);
        map.put("DA", R.drawable.da);
        map.put("D2", R.drawable.d2);
        map.put("D3", R.drawable.d3);
        map.put("D4", R.drawable.d4);
        map.put("D5", R.drawable.d5);
        map.put("D6", R.drawable.d6);
        map.put("D7", R.drawable.d7);
        map.put("D8", R.drawable.d8);
        map.put("D9", R.drawable.d9);
        map.put("DX", R.drawable.dx);
        map.put("DJ", R.drawable.dj);
        map.put("DQ", R.drawable.dq);
        map.put("DK", R.drawable.dk);
        return map;
    }

    private static Map<String, Integer> CardtoCheckID = createCheckIDMap();
    /**
     * Purpose : to create map that is populated with the checked image ID and their corrisponding name
     * @return the Map that is populated with the values added to the map
     * Help Received : Stack Overflow on how to properly init a map
     */
    private static Map<String, Integer> createCheckIDMap(){
        Map<String, Integer> map = new HashMap<>();
        map.put("CA", R.drawable.cacheck);
        map.put("C2", R.drawable.c2check);
        map.put("C3", R.drawable.c3check);
        map.put("C4", R.drawable.c4check);
        map.put("C5", R.drawable.c5check);
        map.put("C6", R.drawable.c6check);
        map.put("C7", R.drawable.c7check);
        map.put("C8", R.drawable.c8check);
        map.put("C9", R.drawable.c9check);
        map.put("CX", R.drawable.cxcheck);
        map.put("CJ", R.drawable.cjcheck);
        map.put("CQ", R.drawable.cqcheck);
        map.put("CK", R.drawable.ckcheck);
        map.put("HA", R.drawable.hacheck);
        map.put("H2", R.drawable.h2check);
        map.put("H3", R.drawable.h3check);
        map.put("H4", R.drawable.h4check);
        map.put("H5", R.drawable.h5check);
        map.put("H6", R.drawable.h6check);
        map.put("H7", R.drawable.h7check);
        map.put("H8", R.drawable.h8check);
        map.put("H9", R.drawable.h9check);
        map.put("HX", R.drawable.hxcheck);
        map.put("HJ", R.drawable.hjcheck);
        map.put("HQ", R.drawable.hqcheck);
        map.put("HK", R.drawable.hkcheck);
        map.put("SA", R.drawable.sacheck);
        map.put("S2", R.drawable.s2check);
        map.put("S3", R.drawable.s3check);
        map.put("S4", R.drawable.s4check);
        map.put("S5", R.drawable.s5check);
        map.put("S6", R.drawable.s6check);
        map.put("S7", R.drawable.s7check);
        map.put("S8", R.drawable.s8check);
        map.put("S9", R.drawable.s9check);
        map.put("SX", R.drawable.sxcheck);
        map.put("SJ", R.drawable.sjcheck);
        map.put("SQ", R.drawable.sqcheck);
        map.put("SK", R.drawable.skcheck);
        map.put("DA", R.drawable.dacheck);
        map.put("D2", R.drawable.d2check);
        map.put("D3", R.drawable.d3check);
        map.put("D4", R.drawable.d4check);
        map.put("D5", R.drawable.d5check);
        map.put("D6", R.drawable.d6check);
        map.put("D7", R.drawable.d7check);
        map.put("D8", R.drawable.d8check);
        map.put("D9", R.drawable.d9check);
        map.put("DX", R.drawable.dxcheck);
        map.put("DJ", R.drawable.djcheck);
        map.put("DQ", R.drawable.dqcheck);
        map.put("DK", R.drawable.dkcheck);
        return map;
    }

    /**
     * Purpose : The default constructor for the Serialization class
     */
    public Serialization(){
    computerHand = new ArrayList<>();
    computerPile = new ArrayList<>();
    humanHand = new ArrayList<>();
    humanPile = new ArrayList<>();
    tableCards = new ArrayList<>();
    deck = new ArrayList<>();
    }


    /**
     * Purpose : opens the directory and loads in all the files
     * @return ArrayList containing all the files
     * Help Received : none
     */
    public ArrayList<File> openDirectory(){

        File fileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "serialization");

        File[] files = fileDir.listFiles();
        Log.d("FILES", "Size: "+ files.length);
        return new ArrayList<File>(Arrays.asList(files));
    }

    /**
     * Purpose : decides if the save file is a seeded deck or a save state
     * @param _file the file that will be parsed
     * @return true if it is a file or false if it is a deck
     * Help Received : none
     */

    public boolean deckOrState(File _file){
        try(BufferedReader br = new BufferedReader(new FileReader(_file))) {
            String line = br.readLine();
            String[] s = line.split(" ");
            if(s[0].equals("Round:")){
                populateSerialization(_file);
                return true;
            }
            else{
                loadDeck(_file);
                return false;
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Purpose : iterate through the file and populate the appropiate variables
     * @param _file
     * Help Received : StaclOverFlow for iterating through a file
     */
    public void populateSerialization(File _file){
        try(BufferedReader br = new BufferedReader(new FileReader(_file))) {
            for(String line; (line = br.readLine()) != null; ) {
                line = line.trim();
                Log.i("STRINGINFILE", line);
                if(line.length() == 0){
                    continue;
                }
                String[] split = line.split(" ");

                if(split[0].equals("Round:")){
                    roundNumber = Integer.parseInt(split[1]);
                }
                else if(split[0].equals("Computer:")){
                    String compLine = br.readLine().trim();
                    populateComputerScore(compLine);
                    compLine = br.readLine().trim();
                    populateComputerHand(compLine);
                    compLine = br.readLine().trim();
                    populateComputerPile(compLine);
                }
                else if(split[0].equals("Human:")){
                    String humanLine = br.readLine().trim();
                    populateHumanScore(humanLine);
                    humanLine = br.readLine().trim();
                    populatehumanHand(humanLine);
                    humanLine = br.readLine().trim();
                    populateHumanPile(humanLine);
                }
                else if(split[0].equals("Table:")){
                    populateBoard(line.trim());
                }
                else if(split[0].equals("Build")){
                    createBuild(line.trim());
                }
                else if(split[0].equals("Last")){
                    createLastCapture(line.trim());
                }
                else if(split[0].equals("Deck:")){
                    createDeck(line.trim());
                }
                else if(split[0].equals("Next")){
                    createNextPlayer(line.trim());
                }


            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Purpose : to load a deck from the file that is a seeded deck
     * @param _file the file that is being parsed
     * Help Received : none
     */
    public void loadDeck(File _file){
        ArrayList<Card> tempDeck = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(_file))) {
            for(String line; (line = br.readLine()) != null; ) {
                line = line.trim();
                Card card = new Card(line, CardtoValue.get(line.charAt(1)), CardtoID.get(line), CardtoCheckID.get(line));
                tempDeck.add(card);
            }

            deck = new ArrayList<>(tempDeck);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Purpose : to get the computer score
     * @param _line the string that will be parsed to get the score
     * Help Received : none
     */
    public void populateComputerScore(String _line) {
        String[] split = _line.split(" ");
        computerScore = Integer.parseInt(split[1]);
    }

    /**
     * Purpose : to get the computer hands
     * @param _line the string that will be parsed to get the hand
     * Help Received : none
     */

    public void populateComputerHand(String _line){
        String[] split = _line.split(" ");
        for(int i = 1; i < split.length; i++){
            Card card = new Card(split[i], CardtoValue.get(split[i].charAt(1)), CardtoID.get(split[i]), CardtoCheckID.get(split[i]));
            this.computerHand.add(card);
        }
    }

    /**
     * Purpose : to get the computer pile
     * @param _line the string that will be parsed to get the pile
     * Help Received : none
     */
    public void populateComputerPile(String _line){
        String[] split = _line.split(" ");
        for(int i = 1; i < split.length; i++){
            Card card = new Card(split[i], CardtoValue.get(split[i].charAt(1)), CardtoID.get(split[i]), CardtoCheckID.get(split[i]));
            this.computerPile.add(card);

        }
    }

    /**
     * Purpose : to get the human score
     * @param _line the string that will be parsed to get the score
     * Help Received : none
     */
    public void populateHumanScore(String _line){
        String[] split = _line.split(" ");
        humanScore = Integer.parseInt(split[1]);
    }
    /**
     * Purpose : to get the human hands
     * @param _line the string that will be parsed to get the hand
     * Help Received : none
     */
    public void populatehumanHand(String _line){
        String[] split = _line.split(" ");
        for(int i = 1; i < split.length; i++){
            Card card = new Card(split[i], CardtoValue.get(split[i].charAt(1)), CardtoID.get(split[i]), CardtoCheckID.get(split[i]));
            this.humanHand.add(card);
        }
    }
    /**
     * Purpose : to get the human pile
     * @param _line the string that will be parsed to get the pile
     * Help Received : none
     */
    public void populateHumanPile(String _line){
        String[] split = _line.split(" ");
        for(int i = 1; i < split.length; i++){
            Card card = new Card(split[i], CardtoValue.get(split[i].charAt(1)), CardtoID.get(split[i]), CardtoCheckID.get(split[i]));
            this.humanPile.add(card);
        }
    }

    /**
     * Purpose : to get all of the cards on the board without builds
     * @param a_boardstring the String that will be parsed into the board
     * Help Received : none
     */
    public void populateBoard(String a_boardstring) {
        String board;
        String[] split = a_boardstring.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < split.length; i++){
            sb.append(split[i]);
        }
        board = sb.toString();

        int inBuild = 0;
        for (int i = 0; i < board.length()-1; i++) {
            if (board.charAt(i) == '[' || board.charAt(i) == '{') {
                inBuild++;
            }
            else if (board.charAt(i) == ']' || board.charAt(i) == '}') {
                inBuild--;
            }
            else if (0 == inBuild) {
                String cardname = board.substring(i, i+2);
                Card card = new Card(cardname, CardtoValue.get(cardname.charAt(1)),
                        CardtoID.get(cardname), CardtoCheckID.get(cardname));
                this.tableCards.add(card);
                i++;
            }
        }
    }

    /**
     * Purpose : to create a build and add it to the table
     * @param a_line the line that will become a build
     * Help Received : none
     */
    public void createBuild(String a_line) {
        String[] split = a_line.split(" ");
        String build, owner;
        StringBuilder sb = new StringBuilder();
        for(int i = 2; i < split.length -1; i++){
            sb.append(split[i]);
        }
        build = sb.toString();


        if(split[split.length-1].equals("Human")){
            owner = "Human";
        }
        else {
            owner = "Computer";
        }

        ArrayList<Build> builds = new ArrayList<>();
        ArrayList<Card> singlebuildcards = new ArrayList<>();
        for (int i = 1; i < build.length() -1; i++) {
            if (build.charAt(i) == '[') {
                int j = i + 1;
                ArrayList<Card> buildcards = new ArrayList<>();
                while (build.charAt(j) != ']') {
                    String cardname = build.substring(j, j+2);
                    Card card = new Card(cardname, CardtoValue.get(cardname.charAt(1)), CardtoID.get(cardname), CardtoCheckID.get(cardname));
                    j += 2;
                    buildcards.add(card);
                }
                Build build1 = new Build(buildcards, owner);
                builds.add(build1);
                i = j;
            }
            else {
                String cardname = build.substring(i, i+2);
                Card card = new Card(cardname, CardtoValue.get(cardname.charAt(1)), CardtoID.get(cardname), CardtoCheckID.get(cardname));
                i += 1;
                singlebuildcards.add(card);
            }
        }
        if (singlebuildcards.size() != 0) {
            Build build1 = new Build(singlebuildcards, owner);
            this.tableCards.add(build1);
        }
        if (builds.size() != 0) {
            Multibuild mbuild = new Multibuild(builds);
            this.tableCards.add(mbuild);
        }
    }

    /**
     * Purpose : to get the person to capture last
     * @param _line the line that will be parsed to file the captuerer
     * Help Received : none
     */

    public void createLastCapture(String _line){
        String[] split = _line.split(" ");
        lastCaptuerer = split[2];
    }

    /**
     * Purpose : to get a deck from a save state
     * @param _line the line that will be parsed to create the deck
     * Help Received : none
     */
    public void createDeck(String _line) {
        String[] fileDeck = _line.split(" ");
        //this will create the deck
        for(int i = 1; i < fileDeck.length; i++){
            if(fileDeck[i].length() != 0) {
                Card card = new Card(fileDeck[i], CardtoValue.get(fileDeck[i].charAt(1)), CardtoID.get(fileDeck[i]), CardtoCheckID.get(fileDeck[i]));
                this.deck.add(card);
            }
        }
    }

    /**
     * Purpose : to get the Nextplayer from the file
     * @param _line the line that will be parsed to create the deck
     * Help Received : none
     */
    public void createNextPlayer(String _line){
        String[] split = _line.split(" ");
        this.currentPlayer = split[2];
    }

    public int getRoundNumber(){
        return this.roundNumber;
    }

    public int getComputerScore(){
        return this.computerScore;
    }

    public ArrayList<Card> getComputerHand(){
        return this.computerHand;
    }

    public ArrayList<Card> getComputerPile(){
        return this.computerPile;
    }

    public int getHumanScore(){
        return humanScore;
    }

    public ArrayList<Card> getHumanHand(){
        return this.humanHand;
    }

    public ArrayList<Card> getHumanPile(){
        return this.humanPile;
    }

    public ArrayList<Card> getTableCards(){
        return this.tableCards;
    }

    public String getLastCaptuerer(){
        return this.lastCaptuerer;
    }

    public ArrayList<Card> getDeck(){
        return this.deck;
    }

    public String getCurrentPlayer(){
        return this.currentPlayer;
    }
}
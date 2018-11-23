package andrew.opl.ramapo.edu.casino;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Deck implements Serializable {

    private ArrayList<Card> deck = new ArrayList<>();
    private String[] mastercardlist = new String[]{
            "CA","C2","C3","C4","C5","C6","C7","C8","C9","CX","CJ","CQ","CK",
            "HA","H2","H3","H4","H5","H6","H7","H8","H9","HX","HJ","HQ","HK",
            "SA","S2","S3","S4","S5","S6","S7","S8","S9","SX","SJ","SQ","SK",
            "DA","D2","D3","D4","D5","D6","D7","D8","D9","DX","DJ","DQ","DK"};

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
     * Purpose : The default constructor that populates a deck and shuffles it
     * Help Received : none
     */
    Deck(){
        CreateDeck();
        ShuffleDeck();
    }

    /**
     * Purpose : To create a deck of cards with all cards in order
     * Help Received : none
     */
    public void CreateDeck(){
        for (String name: mastercardlist) {
            Card card = new Card(name, CardtoValue.get(name.charAt(1)), CardtoID.get(name), CardtoCheckID.get(name));
            deck.add(card);
        }
    }

    /**
     * Purpose : To remove 4 cards from the deck into an ArrayList and return it
     * @return ArrayList containing the 4 cards
     */

    public ArrayList<Card> DealCards(){
        ArrayList<Card> cardSet = new ArrayList<>(deck.subList(0, 4));
        for(int i = 3; i > -1; i--){
            this.deck.remove(i);
        }
        return cardSet;
    }

    public void ShuffleDeck(){
        Collections.shuffle(deck);
    }

    public boolean EmptyDeck(){
        return deck.isEmpty();
    }

    public void SetDeck(ArrayList<Card> _deck){
        deck = new ArrayList<>(_deck);
    }

    public ArrayList<Card> GetDeck(){
        return this.deck;
    }
}

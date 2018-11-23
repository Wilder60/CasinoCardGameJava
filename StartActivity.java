package andrew.opl.ramapo.edu.casino;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private Tournament tournament;
    private ArrayList<Card> tableCards = new ArrayList<>();
    private ArrayList<Card> handCards = new ArrayList<>();

    /**
     * Purpose : to load the XML files and setting the button
     * @param savedInstanceState The bundle sent containing info that androids needs and the
     *                           tournament class
     * Help Received : Android docs for onClick demo
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("Tournament");

        final Button buildButton = findViewById(R.id.buildButton);
        buildButton.setOnClickListener(handleButtonClick);

        final Button captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(handleButtonClick);

        final Button trailButton = findViewById(R.id.trailButton);
        trailButton.setOnClickListener(handleButtonClick);

        final Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(handleButtonClick);

        final Button NextTurnButton = findViewById(R.id.nextTurnButton);
        NextTurnButton.setOnClickListener(handleButtonClick);

        final Button moveLogButton = findViewById(R.id.moveLogButton);
        moveLogButton.setOnClickListener(handleButtonClick);

        final Button saveGameButton = findViewById(R.id.saveButton);
        saveGameButton.setOnClickListener(handleButtonClick);

        if(tournament.isLoadFlag() && !tournament.getCurrentPlayer().equals("")){
            tournament.setLoadFlag(false);
        }
        else if(tournament.isLoadFlag() && tournament.getCurrentPlayer().equals("")){
            tournament.loadSeededDeck();
            tournament.setLoadFlag(false);
        }
        else{
            tournament.loadNewGame();
        }

        UpdateDisplay(tournament.GetRound().GetPlayerList(), tournament.GetRound().GetBoard(), tournament.GetRound().GetDeck(), new Result(true, "", "none"));
    }

    /**
     * Purpose : to set the OnClickListeners for the buttons
     * Help Received : none
     */
    private View.OnClickListener handleButtonClick = new View.OnClickListener(){

        /**
         * Purpose : To set the functionality on the onClick
         * @param view the view being passed in
         * Help Received : none
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buildButton:
                    Result buildMove = tournament.MakeMove(new Move("Build", tableCards, handCards));
                    UpdateDisplay(tournament.GetRound().GetPlayerList(), tournament.GetRound().GetBoard(), tournament.GetRound().GetDeck(), buildMove);
                    ClearData();
                    break;

                case R.id.captureButton:
                    Result captureMove = tournament.MakeMove(new Move("Capture", tableCards, handCards));
                    UpdateDisplay(tournament.GetRound().GetPlayerList(), tournament.GetRound().GetBoard(), tournament.GetRound().GetDeck(), captureMove);
                    ClearData();
                    break;

                case R.id.trailButton:
                    Result trailMove = tournament.MakeMove(new Move("Trail", tableCards, handCards));
                    UpdateDisplay(tournament.GetRound().GetPlayerList(), tournament.GetRound().GetBoard(), tournament.GetRound().GetDeck(), trailMove);
                    ClearData();
                    break;

                case R.id.helpButton:
                    Result helpMove = tournament.MakeMove(new Move("Help", tableCards, handCards));
                    UpdateDisplay(tournament.GetRound().GetPlayerList(), tournament.GetRound().GetBoard(), tournament.GetRound().GetDeck(), helpMove);
                    ClearData();
                    break;

                case R.id.nextTurnButton:
                    Result computerMove = tournament.MakeMove(new Move("Computer", tableCards, handCards));
                    UpdateDisplay(tournament.GetRound().GetPlayerList(), tournament.GetRound().GetBoard(), tournament.GetRound().GetDeck(), computerMove);
                    ClearData();
                    break;

                case R.id.moveLogButton:
                    showMoveList(tournament.getMovelist());

                case R.id.saveButton:
                    tournament.saveGame(StartActivity.this);
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                    break;
            }
        }
    };

    /**
     * Purpose : to clear the tableCards selected and the handCards selected
     * Help Received : none
     */
    public void ClearData(){
        tableCards.clear();
        handCards.clear();
    }

    /**
     * Purpose : to update the display of the XML file with new row data
     * @param playerList the player, Human and Computer, in the tournament class
     * @param board the board from the tournament class
     * @param _deck the deck from the tournament class
     * @param result the result of the move that was made
     * Help Received : none
     */

    public void UpdateDisplay(Player playerList[], Board board, Deck _deck, Result result){
        if(result.isEndOfRound()){
            Intent intent = new Intent(StartActivity.this, EndActivity.class);
            intent.putExtra("Tournament" , tournament);
            startActivity(intent);
        }
        updateClickableImage(playerList[1].GetPile(), R.id.computerPile);
        updateImageRow(playerList[1].GetHand(), R.id.computerHandRow);
        updateTableButtonRow(board.GetBoard(), R.id.TableRow);
        updateHumanButtonRow(playerList[0].GetHand(), R.id.humanHandRow);
        updateClickableImage(playerList[0].GetPile(), R.id.humanPile);
        updateClickableImage(_deck.GetDeck(), R.id.deckPile);
        ((TextView) findViewById(R.id.roundCounter)).setText(getString(R.string.round_counter, tournament.GetRoundCount()));
        ((TextView) findViewById(R.id.moveReasonText)).setText(getString(R.string.move_reason, result.GetMoveReason()));
        ((TextView) findViewById(R.id.humanPoints)).setText(getString(R.string.player_score, playerList[0].GetScore()));
        ((TextView) findViewById(R.id.computerPoints)).setText(getString(R.string.player_score, playerList[1].GetScore()));

        if(tournament.GetRound().getCurrentPlayer().equals("Human")){
            highlightNextPlayer(R.id.humanHandRow, R.id.computerHandRow);
        }
        else{
            highlightNextPlayer(R.id.computerHandRow, R.id.humanHandRow);
        }

    }


    /**
     * Purpose : to highlight the row of the player that is currently playing
     * @param _hightLightID the ID of the person to be highlighted
     * @param _removeLightID the ID of the person to be dehighlighted
     * Help Received : none
     */
    private void highlightNextPlayer(int _hightLightID, int _removeLightID){
        LinearLayout layout = findViewById(_hightLightID);
        layout.setBackgroundColor(Color.parseColor("#f49242"));
        layout = findViewById(_removeLightID);
        layout.setBackgroundColor(Color.parseColor("#005B00"));
    }

    /**
     * Purpose : to update a LinearLayout row of ImageViews
     * @param _list the ArrayList of cards
     * @param _rowID the ID of the row that will be changed
     * Help Recieved : none
     */
    private void updateImageRow(ArrayList<Card> _list, int _rowID){
        LinearLayout row = findViewById(_rowID);
        row.removeAllViewsInLayout();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(50, 0, 50, 0);

        for(Card card : _list){
            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getCardID());
            image.setTag(card);
            row.addView(image);
        }
    }

    /**
     * Purpose : to update a row of buttons for a row ID
     * @param _list the ArrayList of cards that will update the view
     * @param _rowID the ID of the view that will be updated
     * Help Received : none
     */
    private void updateHumanButtonRow(ArrayList<Card> _list, int _rowID){
        LinearLayout row = findViewById(_rowID);
        row.removeAllViewsInLayout();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        params.setMargins(50, 0, 50, 0);
        for(final Card card : _list) {
            final ImageButton button = new ImageButton(this);
            button.setLayoutParams(params);
            button.setImageResource(card.getCardID());
            button.setTag(card);
            button.setClickable(true);
            row.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                /**
                 * Purpose : to add or removed the card from the handCards when clicked
                 * @param v the view being passed in
                 * Help Received : none
                 */
                @Override
                public void onClick(View v) {
                    if(handCards.contains(button.getTag())){
                        handCards.remove(button.getTag());
                        button.setImageResource(card.getCardID());
                    }
                    else{
                        handCards.add((Card) button.getTag());
                        button.setImageResource(card.GetCheckCardID());
                    }
                }
            });
        }
    }

    /**
     * Purpose : to update the table with ImageButtons or LinearLayouts that represent cards and multibuilds
     * @param _list the ArrayList that contains the Table cards
     * @param _rowID the ID of the table Row
     */
    private void updateTableButtonRow(ArrayList<Card> _list, int _rowID) {
        LinearLayout row = findViewById(_rowID);
        row.removeAllViewsInLayout();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        for (final Card card : _list) {
            if(card.isBuild() || card.isMultiBuild()){
                row.addView(CreateMultibuild(card, params));
            }
            else {
                row.addView(CreateButton(card, params));
            }
        }
    }

    /**
     * Purpose : to create a LinearLayout that conatins all the card in a build
     * @param _card that build that will be converted into the layout
     * @param params the layoutParams for the Layout
     * @return LineaerLayout that contains the Cards of the Build
     */
    private LinearLayout CreateMultibuild(final Card _card, LinearLayout.LayoutParams params){
        final LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.parseColor("#005B00"));
        layout.setClickable(true);
        Build build = (Build) _card;
        params.setMargins(10, 0, 10, 0);
        for(Card card : build.getBuildCards()){
            ImageView image = new ImageView(this);
            image.setLayoutParams(params);
            image.setImageResource(card.getCardID());
            image.setTag(card);
            layout.addView(image);
        }
        TextView textView = new TextView(this);
        textView.setText(build.GetName());
        textView.setLayoutParams(params);
        layout.addView(textView);
        layout.setTag(_card);
        layout.setOnClickListener(new View.OnClickListener() {
            /**
             * Purpose : to add the Build to the tableCards
             * @param v the view that is being passed in
             * Help Received : none
             */
            @Override
            public void onClick(View v) {
                if(tableCards.contains(layout.getTag())){
                    tableCards.remove(layout.getTag());
                    layout.setBackgroundColor(Color.parseColor("#005B00"));
                }
                else{
                    tableCards.add( (Card) layout.getTag());
                    layout.setBackgroundColor(Color.parseColor("aqua"));
                }
            }
        });
        return layout;


    }

    /**
     * To create a ImageButton of a card for the table
     * @param _card the card that will be created into the image
     * @param params the LayoutParams for the ImageButton
     * @return and Image button with the image of the card with the onClickListener
     * Help Received : none
     */
    private ImageButton CreateButton(final Card _card, LinearLayout.LayoutParams params){
        final ImageButton button = new ImageButton(this);
        button.setLayoutParams(params);
        button.setImageResource(_card.getCardID());
        button.setTag(_card);
        button.setClickable(true);
        button.setOnClickListener(new View.OnClickListener() {
            /**
             * Purpose : to set the card into the tableCards
             * @param v the view that is being passed in
             * Help Received : none
             */
            @Override
            public void onClick(View v) {
                if (tableCards.contains(button.getTag())) {
                    tableCards.remove(button.getTag());
                    button.setImageResource(_card.getCardID());
                }
                else {
                    tableCards.add((Card) button.getTag());
                    button.setImageResource(_card.GetCheckCardID());
                }
            }
        });
        return button;
    }

    /**
     * Purpose : to update the clickable images like the piles and deck to display the
     *              * information in the _list that is passed in
     * @param _list the Arraylist contain Card objects
     * @param _id the id of the image to be updated
     * Help Received : none
     */
    public void updateClickableImage(final ArrayList<Card> _list, int _id){
        ImageView image = findViewById(_id);
        image.setClickable(true);
        image.setOnClickListener(new View.OnClickListener(){
            /**
             * Purpose : to display the card in _list on the screen
             * @param v the view being passed in
             * Help Received : none
             */
            @Override
            public void onClick(View v){
                AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this).create();

                HorizontalScrollView horizontalScrollView = new HorizontalScrollView(StartActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                horizontalScrollView.setLayoutParams(layoutParams);

                LinearLayout linearLayout = new LinearLayout(StartActivity.this);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearParams.setMargins(50, 0, 50, 0);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(linearParams);
                horizontalScrollView.addView(linearLayout);

                for(Card card : _list){
                    ImageView image = new ImageView(StartActivity.this);
                    image.setLayoutParams(layoutParams);
                    image.setImageResource(card.getCardID());
                    image.setTag(card);
                    linearLayout.addView(image);
                }

                alertDialog.setView(horizontalScrollView);
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });
    }

    /**
     * Purpose : to display all the moves that have benn made in the game
     * @param _moveList the ArrayList of strings
     * Help Received : none
     */
    public void showMoveList(ArrayList<String> _moveList){
        AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this).create();

        LinearLayout layout = new LinearLayout(StartActivity.this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearParams.setMargins(50, 0, 50, 0);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(linearParams);

        for(String move : _moveList){
            TextView textView = new TextView(StartActivity.this);
            textView.setText(move);
            layout.addView(textView);
        }

        alertDialog.setView(layout);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }


}

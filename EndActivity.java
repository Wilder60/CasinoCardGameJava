package andrew.opl.ramapo.edu.casino;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class EndActivity extends AppCompatActivity {

    private Tournament tournament;
    private int numberOfSpades[] = {0, 0};

    /**
     * Purpose : The function to create the buttons and load the xml file
     * @param savedInstanceState The bundle sent containing info that androids needs
     * Help Received : none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("Tournament");

        updateScore();
        updateScreen();
        checkWinner();

        final Button nextRoundButton = findViewById(R.id.nextRound);
        nextRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tournament.GetHuman().ClearPile();
                tournament.GetComputer().ClearPile();
                tournament.IncreaseRoundCount();
                Intent intent = new Intent(EndActivity.this, StartActivity.class);
                intent.putExtra("Tournament" , tournament);
                startActivity(intent);
            }
        });
    }

    /**
     * Purpose : the Function to update that Scores of both players
     * Help Received : none
     */
    public void updateScore(){
        if(tournament.GetHuman().GetPileSize() > tournament.GetComputer().GetPileSize()){
            tournament.GetHuman().IncreaseScore(3);
        }
        else if(tournament.GetHuman().GetPileSize() < tournament.GetComputer().GetPileSize()){
            tournament.GetComputer().IncreaseScore(3);
        }

        for (int i = 0; i < 2; i++) {
            ArrayList<Card> pile = tournament.GetPlayers()[i].GetPile();
            for (Card card : pile) {
                if (card.GetName().charAt(0) == 'S') {
                    this.numberOfSpades[i]++;
                }
                if (card.GetName().charAt(1) == 'A') {
                    tournament.GetPlayers()[i].IncreaseScore(1);
                }
			else if (card.GetName().equals("DX")) {
                    tournament.GetPlayers()[i].IncreaseScore(2);
                }
			else if (card.GetName().equals("S2")) {
                    tournament.GetPlayers()[i].IncreaseScore(1);
                }
            }
        }

        if (this.numberOfSpades[0] > this.numberOfSpades[1]) {
            tournament.GetPlayers()[0].IncreaseScore(1);
        }
        else if (this.numberOfSpades[0] < this.numberOfSpades[1]) {
            tournament.GetPlayers()[1].IncreaseScore(1);
        }
    }

    /**
     * Purpose : the function used to update the XML with the model information
     * Help Received : none
     */
    public void updateScreen(){
        populatePile(tournament.GetHuman().GetPile(), R.id.endHandPile);
        populatePile(tournament.GetComputer().GetPile(), R.id.endComputerPile);
        ((TextView) findViewById(R.id.playerPoints)).setText(getString(R.string.player_score, tournament.GetHuman().GetScore()));
        ((TextView) findViewById(R.id.humanHandTotal)).setText(getString(R.string.number_of_cards, tournament.GetHuman().GetPile().size()));
        ((TextView) findViewById(R.id.humanSpadeTotal)).setText(getString(R.string.number_of_spades, numberOfSpades[0]));
        ((TextView) findViewById(R.id.computerPoints)).setText(getString(R.string.player_score, tournament.GetComputer().GetScore()));
        ((TextView) findViewById(R.id.computerCardTotal)).setText(getString(R.string.number_of_cards, tournament.GetComputer().GetPile().size()));
        ((TextView) findViewById(R.id.computerSpadeTotal)).setText(getString(R.string.number_of_spades, numberOfSpades[1]));
    }

    /**
     * Purpose : to populate the linearLayout of the IDs row with cards from the ArrayList
     * @param _list the list of cards to be placed in the row
     * @param _id the ID of the row to be updated
     * Help Received : Worked with Joe on understanding LayoutParams better
     */

    public void populatePile(ArrayList<Card> _list, int _id){
        LinearLayout row = findViewById(_id);
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
     * Purpose : To check to see if either player won the game
     * Help Received : none
     */
    public void checkWinner(){
        if(tournament.GetHuman().GetScore() >= 21 && tournament.GetComputer().GetScore() < 21){
            AlertDialog alertDialog = new AlertDialog.Builder(EndActivity.this).create();
            alertDialog.setTitle("Winner!");
            alertDialog.setMessage("You've won the Game!");
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                /**
                 * Purpose to close the app when the alert box is cancelled
                 * @param dialog
                 * Help Received : StackOverFlow for how to close out of the app
                 */
                @Override
                public void onCancel(DialogInterface dialog) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
            });
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();

        }
        else if(tournament.GetHuman().GetScore() < 21 && tournament.GetComputer().GetScore() >= 21){
            AlertDialog alertDialog = new AlertDialog.Builder(EndActivity.this).create();
            alertDialog.setTitle("Loser!");
            alertDialog.setMessage("You've lost the Game! \n Try again next time!");
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                /**
                 * Purpose to close the app when the alert box is cancelled
                 * @param dialog
                 * Help Received : StackOverFlow for how to close out of the app
                 */
                @Override
                public void onCancel(DialogInterface dialog) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                    finish();
                    System.exit(0);
                }
            });
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();//computer wins
        }

        else if(tournament.GetHuman().GetScore() >= 21 && tournament.GetComputer().GetScore() >= 21){
            AlertDialog alertDialog = new AlertDialog.Builder(EndActivity.this).create();
            alertDialog.setTitle("Tie!");
            alertDialog.setMessage("You've tied with the Computer!");
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                /**
                 * Purpose to close the app when the alert box is cancelled
                 * @param dialog
                 * Help Received : StackOverFlow for how to close out of the app
                 */
                @Override
                public void onCancel(DialogInterface dialog) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            });
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
        }

    }

}

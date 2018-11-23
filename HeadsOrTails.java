package andrew.opl.ramapo.edu.casino;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HeadsOrTails extends AppCompatActivity {

    private Tournament tournament;
    private String result;

    /**
     * Purpose : The function to create the buttons and load the xml file
     * @param savedInstanceState The bundle sent containing info that androids needs
     * Help Received : none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heads_or_tails);

        Intent intent = getIntent();
        tournament = (Tournament) intent.getSerializableExtra("Tournament");

        final Button headButton = findViewById(R.id.headsButton);
        headButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Purpose : to call the coinflip function and set the appropriate String to result
             * @param v the view being passed in
             * Help Received : none
             */
            @Override
            public void onClick(View v) {
                if(tournament.coinFlip(true)){
                    result = "You Go First!";
                }
                else {
                    result= "The Computer Goes First";
                }
                makeAlterBox();
            }
        });

        final Button tailButton = findViewById(R.id.tailsButton);
        tailButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Purpose : to call the coinflip function and set the appropriate String to result
             * @param v the view being passed in
             * Help Received : none
             */
            @Override
            public void onClick(View v) {
                if (tournament.coinFlip(false)){
                    result = "You Go First!";
                }
                else {
                    result= "The Computer Goes First";
                }
                makeAlterBox();
            }
        });
    }

    /**
     * Purpose : to make a AlertDialog with the result string to tell the player who is going first
     * Help Received : none
     */
    public void makeAlterBox(){
        AlertDialog alertDialog = new AlertDialog.Builder(HeadsOrTails.this).create();
        alertDialog.setTitle("Who is Playing First?");
        alertDialog.setMessage(result);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Intent intent = new Intent(HeadsOrTails.this, StartActivity.class);
                intent.putExtra("Tournament" , tournament);
                startActivity(intent);
            }
        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}

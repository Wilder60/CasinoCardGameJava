/************************************************************
 * Name:  your name here                                    *
 * Project:  number and name of the project here            *
 * Class:  class number and name here                       *
 * Date:  date of submission here                           *
 * ************************************************************/
package andrew.opl.ramapo.edu.casino;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Tournament tournament = new Tournament();

    /**
     * Purpose : The function to create the buttons and load the xml file
     * @param savedInstanceState The bundle sent containing info that androids needs
     * Help Received : none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button NewGameButton = findViewById(R.id.NewGameButton);
        NewGameButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Purpose : to check the intent to the Heads of Trails activity
             * @param v the view being passed in
             */
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, HeadsOrTails.class);
                intent.putExtra("Tournament" , tournament);
                startActivity(intent);
            }});

        final Button LoadGameButton = findViewById(R.id.LoadGameButton);
        LoadGameButton.setOnClickListener(new View.OnClickListener(){
            /**
             * Purpose : to load in a file from the download directory
             * @param v the view being passed in
             * Help Received : working with Joe with how to check permissions and StackOverFlow for
             *          the file reading
             */
            public void onClick(View v){
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);

                ArrayList<File> filelist = tournament.getSerialization().openDirectory();
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                LinearLayout parent = new LinearLayout(MainActivity.this);
                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.VERTICAL);
                for(File file : filelist){
                    final Button button = new Button(MainActivity.this);
                    button.setText(file.getName());
                    button.setTag(file);
                    button.setOnClickListener(new View.OnClickListener() {
                        /**
                         * Purpose : select that file to be loaded into the serialization class
                         * @param v the view being passed in
                         * Help Received : none
                         */
                        @Override
                        public void onClick(View v) {
                            if(tournament.getSerialization().deckOrState((File)button.getTag())) {
                                tournament.populateTournament();
                            }
                            else{
                                tournament.setDeck(tournament.getSerialization().getDeck());
                            }
                            Intent intent = new Intent(MainActivity.this, StartActivity.class);
                            intent.putExtra("Tournament" , tournament);
                            startActivity(intent);
                        }
                    });
                    parent.addView(button);
                }
                alertDialog.setView(parent);
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });
    }
}
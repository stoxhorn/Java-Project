package dm550.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class AppUI extends AppCompatActivity implements UserInterface {
    /*
    https://developer.android.com/reference/android/support/v7/app/AppCompatActivity.html
    Adds the Android AppBar. It i sthe bar at the top, which requires a common method, so it is familiar and a consistent experience
    
    Supports multiple API

    Does not defin the visual layout, but is a common way to gather actions and reponses.

    */

    /*
    two variables used to store the user's choices and to decide which game and how many players the user wishes for
    Stored as final for easier access accross methods, as they will be using getter anyway
    */

    // the chosen game, stored as intergers
    int chosenGame;

    // The amount of playuers  playing
    int np;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Overrides the previous oncreate method, u
        Then it uses the one from the superclass
        It then uses the method onBackPressed, from this class
        */
        super.onCreate(savedInstanceState);
        this.whichGame();
    }


    /**
     * The method called on first, to decide which game to play
     * */
    public void whichGame(){
        /*
        Adds a ui layout, that asks for which game the user wishes to play, displayed is a scrolling menu
        */
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // The presented question as TextView
        TextView tv = new TextView(this);
        tv.setText("Please choose the game you wish to play.");
        layout.addView(tv);

        // The given choice
        final NumberPicker gamePicker = new NumberPicker(this);
        String[] arrayStr= new String[]{"Tic Tac Toe","Connect Four"};
        gamePicker.setMinValue(0);
        gamePicker.setMaxValue(arrayStr.length-1);
        layout.addView(gamePicker);

        // A formatter that makes the possible choices readable
        gamePicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                switch(value){
                    case 0:
                        return "Tic Tac Toe";

                    case 1:
                        return "Connect Four";
                }
                return "try again";
            }
        });

        // the corresponding butten get's added
        Button b = new AppCompatButton(this);
        b.setText("OK");

                b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // And the choice is stored and the next ui is called
                chosenGame = gamePicker.getValue();
                onBackPressed();
                };
            });
        layout.addView(b);

        ScrollView2D sv = new ScrollView2D(this);
        sv.setContent(layout);
        this.setContentView(sv);
    }









    @Override
    public void onBackPressed() {
/*
        1. Sets a new linear layout with a vertical orientation
        2. adds a new textview to the layout, with the text asking for players
        3. adds a numberpicker, which is added to the layout
        4. adds a button saying "OK" with an onclick listener
        5. it overrides the method of onClick(), where it calls the method startGame(), when pressing the new button
        6. adds a scrollbar to the layout

        Summed up:
        Creates a vertical layout, asking for the number of players, which upon used uses the method startGame() which creates a new game with the number of players.
        and this layout has a scrollbar as well
*/

        // 1. Sets a new linear layout with a vertical orientation
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        // 2. adds a new textview to the layout, with the text asking for players
        TextView tv = new TextView(this);
        tv.setText("Please select the number of players!");
        layout.addView(tv);

        // 3. adds a numberpicker, which is added to the layout
        final NumberPicker npp = new NumberPicker(this);
        npp.setMinValue(2);
        npp.setMaxValue(6);
        layout.addView(npp);

        // 4. adds a button saying "OK" with an onclick listener
        Button b = new AppCompatButton(this);
        b.setText("OK");

        // 5. it overrides the method of onClick(), where it calls the method startGame(), when pressing the new button
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chosenGame == 0){
                    AppUI.this.startGame(new TTTGame(npp.getValue()));
                } else if (chosenGame == 1) {
                    AppUI.this.startGame(new CFGame(npp.getValue()));
            }
        }});
        layout.addView(b);

        // 6. adds a scrollbar to the layout
        ScrollView2D sv = new ScrollView2D(this);
        sv.setContent(layout);
        this.setContentView(sv);
    }

    @Override
    public void startGame(final Game game) {
        /*
        This is the method called before, which creates a new game
        1. creates the user interface
            1. Creates a new class called posbutton
            2. With the attribute pos, which is a coordinate type
            3. and creates/overrides two methods, one to set the size of the ui, and one to create a button, that assigns to the same attribute as the object it's used on

        2. Creates a list of this new type, and creates a grid, with each field being assigned a PosButton, and a listener to the same field, which waits for the mouseclick.
        */
        
        // 1. Creates the User Interface for the game
        game.setUserInterface(this);
        class PosButton extends AppCompatButton {
            // 1.1 Creates a position button, with a private attribute for it's position of the coordinate class
            // 1.2 The button is then assigned an attribute that is saved to the same value as the position
            private final XYCoordinate pos;
            public PosButton(XYCoordinate pos) {
                super(AppUI.this);
                this.pos = pos;
            }
            @Override
            public void onMeasure(int wSpec, int hSpec) {
                // 1.3 Overrides the method onMeasure, and uses the method of the superclass, to adjust for the new layout
                super.onMeasure(wSpec, hSpec);
                final int w = getMeasuredWidth();
                if ((chosenGame == 2)||(np > 3)){
                    setMeasuredDimension(w, w/2);
                } else {
                    setMeasuredDimension(w, w);
                }
                    
            }
        }

        // A new Arraylist of the class PosButton is created
        final List<PosButton> buttons = new ArrayList<PosButton>();
        
        // Creates the title with the method getTitle()
        AppUI.this.setTitle(game.getTitle());
        
        // Structurizes the new layout with the classe TableLayout
        TableLayout layout = new TableLayout(AppUI.this);
        final int xSize = game.getHorizontalSize();
        final int ySize = game.getVerticalSize();
        for (int i = 0; i < ySize; i++) {
            layout.setColumnStretchable(i, true);
        }
        // Each row, a new button is added for each size in the x-Axis
        for (int i = 0; i < ySize; i++) {
            TableRow row = new TableRow(AppUI.this);
            for (int j = 0; j < xSize; j++) {
                XYCoordinate pos = new XYCoordinate(j, i);
                PosButton b = new PosButton(pos);
                buttons.add(b);
                b.setText(" ");
                b.setOnClickListener(new View.OnClickListener() {
                    // overrides the listener for the onclick action
                    @Override
                    public void onClick(View view) {
                        // Uses the method isFree from the class game, to check if the the button at that position is free, and then it adds a move at that position
                        XYCoordinate pos = ((PosButton) view).pos;
                        if (game.isFree(pos)) {
                            game.addMove(pos);
                            for (PosButton button : buttons) {
                                // uses the method setText, with the text from the method getContent()
                                button.setText(game.getContent(button.pos));
                            }
                            game.checkResult();
                        }
                    }
                });
                row.addView(b);
            }
            layout.addView(row);
        }
        ScrollView2D sv = new ScrollView2D(this);
        sv.setContent(layout);
        setContentView(sv);
    }

    @Override
    public void showResult(final String message) {
        /*

        Creates a new Layout, which is vertical
        Adds a TextView with the text given as argument
        adds it to the layout, along with a button
        The layout is then set as the Contentview.

        The Method displays a method and runs the method that starts a new game.

        */        
        LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this);
        tv.setText(message);
        view.addView(tv);
        Button b = new Button(this);
        b.setText("OK");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUI.this.whichGame();
            }
        });
        view.addView(b);
        this.setContentView(view);
    }

}
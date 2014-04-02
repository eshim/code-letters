package com.example.codeletters;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class MainActivity extends Activity {
	
	private Map<String, String> listWU = new HashMap<String, String>(); // Dictionary of Western Union Phonetic Letters
	private ArrayList<Card> letterCardList = new ArrayList<Card>(); // List of Cards to scroll through
	private CardScrollView letterCardListScrollView;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		listWU.put("A", "Adams"); 
    	listWU.put("B", "Boston");
    	listWU.put("C", "Chicago");
    	listWU.put("D", "Denver");
    	listWU.put("E", "Easy");
    	listWU.put("F", "Frank");
    	listWU.put("G", "George");
    	listWU.put("H", "Henry");
    	listWU.put("I", "Ida");
    	listWU.put("J", "John");
    	listWU.put("K", "King");
    	listWU.put("L", "Lincoln");
    	listWU.put("M", "Mary");
    	listWU.put("N", "New York");
    	listWU.put("O", "Ocean");
    	listWU.put("P", "Peter");
    	listWU.put("Q", "Queen");
    	listWU.put("R", "Roger");
    	listWU.put("S", "Sugar");
    	listWU.put("T", "Thomas");
    	listWU.put("U", "Union");
    	listWU.put("V", "Victor");
    	listWU.put("W", "William");
    	listWU.put("X", "X-ray");
    	listWU.put("Y", "Young");
    	listWU.put("Z", "Zero");
    	// it would be better to store this dictionary somewhere instead of
    	// inputting values every time this program starts
    	
//    	displaySpeechRecognizer();
    
    	// transcribed text from voice prompt instead of activity
		List<String> voiceResults = getIntent().getExtras()
				.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

        String capturedWord = voiceResults.get(0);
		Card letterCard;
        
        for (int i = 0; i < capturedWord.length(); i++){
        	
        	char capturedLetter = Character.toUpperCase(capturedWord.charAt(i));
        	// want to match it to uppercase keys
        	String phoneticLetter = listWU.get(Character.toString(capturedLetter));
        	
        	letterCard = new Card(this);
        	letterCard.setText(phoneticLetter);
        	letterCardList.add(letterCard);
        }
    			
    	
    	// lifted wholesale from https://developers.google.com/glass/develop/gdk/ui/theme-widgets
    	letterCardListScrollView = new CardScrollView(this);
    	ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
    	letterCardListScrollView.setAdapter(adapter);
    	letterCardListScrollView.activate();
    	setContentView(letterCardListScrollView);
    	
//        setContentView(R.layout.activity_main);
        
        
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
//        } 
    }

    private static final int SPEECH_REQUEST = 0;

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startActivityForResult(intent, SPEECH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// Takes spoken text and turns each letter into a card
    	
        if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String capturedWord = results.get(0);

            Card letterCard;
            
            for (int i = 0; i < capturedWord.length(); i++){
            	
            	char capturedLetter = Character.toUpperCase(capturedWord.charAt(i));
            	// want to match it to uppercase keys
            	String phoneticLetter = listWU.get(Character.toString(capturedLetter));
            	
            	letterCard = new Card(this);
            	letterCard.setText(phoneticLetter);
            	letterCardList.add(letterCard);
            	
            	
            }
            // break word into characters
            // add each matching value to a list
            // match each character as a key to the dictionary
            // put each list entry into a card
            // move to next card by either pronouncing the word displayed or swiping
            // end card is "Again?"
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    
    private class ExampleCardScrollAdapter extends CardScrollAdapter {
    	// lifted wholesale from https://developers.google.com/glass/develop/gdk/ui/theme-widgets

        @Override
        public int findIdPosition(Object id) {
            return -1;
        }

        @Override
        public int findItemPosition(Object item) {
            return letterCardList.indexOf(item);
        }

        @Override
        public int getCount() {
            return letterCardList.size();
        }

        @Override
        public Object getItem(int position) {
            return letterCardList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return letterCardList.get(position).toView();
        }
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }
//
}

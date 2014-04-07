package com.example.codeletters;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class MainActivity extends Activity {
	
	private Map<String, String> listWU = new HashMap<String, String>(); // Dictionary of Western Union Phonetic Letters
	private ArrayList<Card> letterCardList = new ArrayList<Card>(); // List of Cards to scroll through
	private CardScrollView letterCardListScrollView;
	private ExampleCardScrollAdapter adapter;

	
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
    	displaySpeechRecognizer();
    
    	letterCardListScrollView = new CardScrollView(this);
    	adapter = new ExampleCardScrollAdapter();
    	letterCardListScrollView.setAdapter(adapter);
    	letterCardListScrollView.activate();
    	setContentView(letterCardListScrollView);
    }
	

    private static final int SPEECH_REQUEST = 0;

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startActivityForResult(intent, SPEECH_REQUEST);
    }

    /** 
     * onActivityResult()
     * Records spoken text and breaks each recorded word down by character. For
	 * each letter, a corresponding phonetic letter card is added to the 
	 * letterCardList. Finally the adapter is notified of the updated data.
	 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String capturedWord = results.get(0);
            
            
            for (int i = 0; i < capturedWord.length(); i++){
            	Card letterCard = new Card(this);
            	String phoneticLetter;
            	
            	char capturedLetter = Character.toUpperCase(capturedWord.charAt(i));
            	if (capturedLetter != ' ') {
            		phoneticLetter = listWU.get(Character.toString(capturedLetter));
            		letterCard.setText(phoneticLetter);
            	}
        		letterCard.setFootnote(capturedWord);
            	letterCardList.add(letterCard);
            }
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    
    private class ExampleCardScrollAdapter extends CardScrollAdapter {

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
}
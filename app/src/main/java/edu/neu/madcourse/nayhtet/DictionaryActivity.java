package edu.neu.madcourse.nayhtet;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * This Activity is called when Dictionary button is clicked
 */
public class DictionaryActivity extends AppCompatActivity{

    final int minLetters = 3;
    EditText textEntryBox;
    ListView wordList;
    ArrayList<String> listOfWords = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> dict = new ArrayList<>();
    String read;
    InputStream inputStream;
    BufferedReader bufferedReader;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        inputStream = getResources().openRawResource(R.raw.wordlist);
        bufferedReader =  new BufferedReader(new InputStreamReader(inputStream));
        wordList = (ListView)findViewById(R.id.dictionaryListWords);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listOfWords);
        wordList.setAdapter(adapter);
        textEntryBox = (EditText)findViewById(R.id.dictionaryEditText);

        textEntryBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() >= minLetters && !listOfWords.contains(editable.toString())){
                    if(dict.contains(editable.toString())){
                        adapter.add(editable.toString());
                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                    }
                }
            }
        });

        try{
            read = bufferedReader.readLine();
            // reads the contents of wordlist.txt into a string array
            while(read != null) {
                dict.add(read);
                read = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear(View view){
        textEntryBox.setText("");   //clears the text entry box
        adapter.clear();    //clears the list
    }
    public void acknowledgements(View view){
        Intent intent = new Intent(this, DictionaryAcknowledgmentsActivity.class);
        startActivity(intent);
    }
}

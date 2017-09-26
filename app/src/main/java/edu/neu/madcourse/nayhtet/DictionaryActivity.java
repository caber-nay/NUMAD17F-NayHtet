package edu.neu.madcourse.nayhtet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * This Activity is called when Dictionary button is clicked
 */

public class DictionaryActivity extends AppCompatActivity{

    EditText textEntryBox;
    ListView wordList;
    ArrayList<String> listOfWords = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        wordList = (ListView)findViewById(R.id.dictionaryListWords);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listOfWords);
        wordList.setAdapter(adapter);
        //wordList.setTextFilterEnabled(true);
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
                adapter.add(editable.toString());
            }
        });
    }




    public void clear(View view){
        textEntryBox.setText("");   //clears the text entry box
        adapter.clear();    //clears the list
    }

}

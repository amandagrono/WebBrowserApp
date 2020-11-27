package edu.temple.webbrowserapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class BookmarkActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Bookmark> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("BOOKMARKS");
        listView = findViewById(R.id.bookmarkView);
        listView.setAdapter(new BookmarkAdapter(list, getApplicationContext()));

        Intent returnIntent = new Intent();
        returnIntent.putExtra("RETURN_BOOKMARKS", list);
        setResult(2, returnIntent);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent_2 = new Intent();
                Bookmark bookmark = list.get(position);
                returnIntent_2.putExtra("BOOKMARK", bookmark);
                returnIntent_2.putExtra("RETURN_BOOKMARKS", list);
                setResult(3, returnIntent_2);
                finish();

            }
        });




    }
}
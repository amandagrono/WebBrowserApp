package edu.temple.webbrowserapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
        Bundle bundle_back = new Bundle();
        bundle_back.putParcelableArrayList("RETURN_BOOKMARKS", list);

        returnIntent.putExtra("BUNDLE", bundle_back);

        setResult(10, returnIntent);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent_2 = new Intent();
                Bookmark bookmark = list.get(position);

                Log.d("message", bookmark.getTitle() + "   " + bookmark.getUrl());

                Bundle bundle = new Bundle();
                bundle.putParcelable("BOOKMARK", bookmark);
                bundle.putParcelableArrayList("RETURN_BOOKMARKS", list);

                returnIntent_2.putExtra("BUNDLE", bundle);

                setResult(11, returnIntent_2);
                finish();

            }
        });




    }
}
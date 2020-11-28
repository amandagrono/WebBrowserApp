package edu.temple.webbrowserapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    public class BookmarkAdapter extends BaseAdapter implements ListAdapter {

        private ArrayList<Bookmark> list = new ArrayList<>();
        private Context context;

        public BookmarkAdapter(ArrayList<Bookmark> list, Context context){
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            View view = convertView;
            if(view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.layout_listview, null);
            }
            TextView pageTitle = (TextView) view.findViewById(R.id.pageTitle);
            pageTitle.setText(list.get(position).getTitle());

            Button deleteButton = (Button) view.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog alertDialog = new AlertDialog.Builder(BookmarkActivity.this)
                            .setTitle("Delete Bookmark")
                            .setMessage("Confirm Deleting Bookmark")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(BookmarkActivity.this, "Deleted: " + list.get(position).getTitle() + " Bookmark.", Toast.LENGTH_LONG).show();
                                    list.remove(position);
                                    notifyDataSetChanged();


                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(BookmarkActivity.this, "Bookmark Not Deleted.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setCancelable(false)
                            .create();
                    alertDialog.show();

                }
            });
            return view;
        }
    }
}
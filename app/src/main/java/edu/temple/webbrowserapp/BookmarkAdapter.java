package edu.temple.webbrowserapp;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

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

                AlertDialog alertDialog = new AlertDialog.Builder(convertView.getContext())
                        .setTitle("Delete Bookmark")
                        .setMessage("Confirm Deleting Bookmark")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(convertView.getContext(), "Deleted: " + list.get(position).getTitle() + " Bookmark.", Toast.LENGTH_LONG).show();
                                list.remove(position);
                                notifyDataSetChanged();


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(convertView.getContext(), "Bookmark Not Deleted.", Toast.LENGTH_SHORT).show();
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

package com.user.canopas.content_provider_use;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<User> arrayList;
    ListView list;
String URI="content://com.user.canopas.custom_contentprovider/Users";
    Cust_Adptr adptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<>();
        loadCursor();


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int pos, long l) {
                deleteContact(pos);
                return true;
            }
        });
    }

    private void deleteContact(final int pos) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("delete?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String _id = arrayList.get(pos).get_id();
                        getContentResolver().delete(Uri.parse(URI), "_id", new String[]{_id});
                        arrayList.clear();
                        adptr.notifyDataSetChanged();
                        loadCursor();

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }

    private void loadCursor() {
        Cursor cursor = getContentResolver().query(Uri.parse(URI), null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
                String no = cursor.getString(cursor.getColumnIndex("CONTACT_NO"));
                arrayList.add(new User(_id, name, no));
                adptr = new Cust_Adptr(this, arrayList);
                list.setAdapter(adptr);
            }
        }
    }


    private class Cust_Adptr extends BaseAdapter {
        Context c;
        ArrayList<User> arrayList;

        public Cust_Adptr(Context c, ArrayList<User> arrayList) {
            this.c = c;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(c).inflate(R.layout.rowdesign, viewGroup, false);
            TextView name = view.findViewById(R.id.txt_name);
            TextView contact_no = view.findViewById(R.id.txt_contact_no);
            name.setText(arrayList.get(i).getName());
            contact_no.setText(arrayList.get(i).getContact_no());

            return view;
        }
    }
}

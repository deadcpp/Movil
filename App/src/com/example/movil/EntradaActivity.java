package com.example.movil;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EntradaActivity extends ActionBarActivity {

    ListView mListView;
    int count = 0;
    final static String[] textview1 = {
            "Name1", "Name2", "Name3", "Name4", "Name5", "Name6", "Name7", "Name8"
    };
    final static String[] textview2 = {
            "Age1", "Age2", "Age3", "Age4", "Age5", "Age6", "Age7", "Age8"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);
        final CustomAdapter adapter = new CustomAdapter(this);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),
                        textview1[position] + "\n" + textview2[position], Toast.LENGTH_SHORT)
                        .show();

            }
        });

    }

    /*
     * CustomAdapter
     */
    public class CustomAdapter extends BaseAdapter
    {

        Activity sActivity;

        public CustomAdapter(final Activity mActivity) {
            this.sActivity = mActivity;
        }

        @Override
        public int getCount() {
            /*
             * Length of our listView
             */
            count = EntradaActivity.textview1.length;
            return count;
        }

        @Override
        public Object getItem(int position) {

            /*
             * Current Item
             */
            return position;
        }

        @Override
        public long getItemId(int position) {
            /*
             * Current Item's ID
             */
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View mView = convertView;
            if (mView == null)
            {
                /*
                 * LayoutInflater
                 */
                final LayoutInflater sInflater = (LayoutInflater) sActivity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                /*
                 * Inflate Custom List View
                 */
                mView = sInflater.inflate(R.layout.custom_list_view, null, false);

            }

            /* **************CUSTOM LISTVIEW OBJECTS**************** */

            /*
             * DO NOT MISS TO ADD "mView"
             */
            final TextView sTV1 = (TextView) mView.findViewById(R.id.textView1);
            //final TextView sTV2 = (TextView) mView.findViewById(R.id.textView2);
            final ImageView sIMG = (ImageView) mView.findViewById(R.id.imageView1);

            /* **************CUSTOM LISTVIEW OBJECTS**************** */

            /* **************ADDING CONTENTS**************** */
            sTV1.setText(EntradaActivity.textview1[position]);
            //sTV2.setText(EntradaActivity.textview2[position]);
            sIMG.setImageResource(R.drawable.ic_launcher);
            /* **************ADDING CONTENTS**************** */

            /*
             * Return View here
             */
            return mView;
        }

    }
}
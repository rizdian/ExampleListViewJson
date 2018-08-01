package com.example.ancientriz.examplelistviewjson;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity {
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        mCtx = this;

        JSONArray jsonArray = getJSonData("file.json");
        //File Harus Berbentuk JsonArray [ {},{},{} ]

        ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);

        final ListView listV = (ListView) findViewById(R.id.listv);
        ListAdapter adapter = new ListAdapter(this, R.layout.item_list, R.id.txtid, listItems);
        listV.setAdapter(adapter);
        //Adapter digunakan untuk setiap baris pada list .

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //pada saat item list di tekan
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kode = null;
                JSONObject itemValue = (JSONObject) listV.getItemAtPosition(position);
                //Mengambil nilai pada item list
                // id dan name
                try {
                    kode = itemValue.getString("id");
                    // cara ngambil id
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(mCtx, DetailActivity.class);
                i.putExtra("id", kode); // id dikirim ke detail activity
                startActivity(i);
            }
        });

    }

    private JSONArray getJSonData(String fileName) {

        JSONArray jsonArray = null;

        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();

            String json = new String(data, "UTF-8");
            jsonArray = new JSONArray(json);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }

        return jsonArray;
    }

    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray) {

        ArrayList<JSONObject> aList = new ArrayList<>();
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return aList;

    }
}

package com.example.ancientriz.examplelistviewjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class DetailActivity extends AppCompatActivity {
    private String id;
    TextView tvHeader,tvDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.id = getIntent().getStringExtra("id");
        tvHeader = (TextView) findViewById(R.id.txtHeader);
        tvDeskripsi = (TextView) findViewById(R.id.txtDeskripsi);

        JSONObject jsonObject = getJSonData("detail.json");
        //Bentuk jsonObject { 'id_1' :{}, 'id_2' : {} }

        if (jsonObject != null){
            try {
                tvHeader.setText(jsonObject.getString("name"));
                tvDeskripsi.setText(jsonObject.getString("desc"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private JSONObject getJSonData(String fileName) {
        JSONObject jsonObject = null;

        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();

            String json = new String(data, "UTF-8");

            JSONObject tempJsonObject = new JSONObject(json);

            Iterator<String> iter = tempJsonObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();

                if (key.equals(this.id)){
                    //mencari id yang sama.
                    jsonObject = tempJsonObject.getJSONObject(key);
                    return jsonObject;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}

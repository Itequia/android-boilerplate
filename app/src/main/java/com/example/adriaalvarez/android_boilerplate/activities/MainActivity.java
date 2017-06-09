package com.example.adriaalvarez.android_boilerplate.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.example.adriaalvarez.android_boilerplate.R;
import com.example.adriaalvarez.android_boilerplate.adapters.GridViewAdapter;
import com.example.adriaalvarez.android_boilerplate.models.Image;
import com.example.adriaalvarez.android_boilerplate.services.ImageService;
import com.example.adriaalvarez.android_boilerplate.services.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

/**
 * Created by adria.alvarez on 08/06/2017.
 */

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    private ImageService imageService;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        imageService = new ImageService();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        gridView = (GridView)findViewById(R.id.gridView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        downloadData();
    }

    private void downloadData() {
        final ArrayList<Image> imageItems = new ArrayList<>();
        final Context context = this;

        imageService.getAll(this).setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {

                for(JsonElement object : result) {
                    JsonObject imageJson = object.getAsJsonObject();

                    imageItems.add(
                        imageService.mapJson(imageJson)
                    );
                }
                gridAdapter = new GridViewAdapter(context, R.layout.grid_item_layout, imageItems);
                gridView.setAdapter(gridAdapter);
            }
        });
    }
}

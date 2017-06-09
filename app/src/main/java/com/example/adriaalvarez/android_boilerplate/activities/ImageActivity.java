package com.example.adriaalvarez.android_boilerplate.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adriaalvarez.android_boilerplate.R;
import com.example.adriaalvarez.android_boilerplate.models.Image;
import com.example.adriaalvarez.android_boilerplate.services.ImageService;
import com.example.adriaalvarez.android_boilerplate.services.StorageService;
import com.example.adriaalvarez.android_boilerplate.services.Utils;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adria.alvarez on 09/06/2017.
 */

public class ImageActivity extends AppCompatActivity {

    private Image image;
    private ImageService imageService;
    private StorageService storageService;

    private EditText author;
    private EditText tags;
    private ImageView imageView;
    private Button upload;

    private static final int CAMERA_REQUEST = 1888;
    private Context context;
    private String diskPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.activity_image);

        author = (EditText)findViewById(R.id.author);
        tags = (EditText)findViewById(R.id.tags);
        imageView = (ImageView)findViewById(R.id.image);
        upload = (Button)findViewById(R.id.upload);

        imageService = new ImageService();
        storageService = new StorageService();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            int imageId = bundle.getInt("imageId");
            imageService.get(imageId, this).setCallback(new FutureCallback<JsonObject>() {

                @Override
                public void onCompleted(Exception e, JsonObject result) {

                    JsonObject imageJson = result.getAsJsonObject();
                    image = imageService.mapJson(imageJson);

                    setEditLayout();
                }
            });
        }
        else {
            image = new Image();

            setCreateLayout();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            diskPath = storageService.saveImage(photo);
            imageView.setImageBitmap(photo);
        }
    }

    private void setCreateLayout() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageService.post(image,diskPath, context).setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
                        } else {
                            finish();
                        }
                    }
                });
            }
        });

        setInputBinders();
    }

    private void setEditLayout() {
        author.setText(image.getAuthor());
        tags.setText(image.getTags());
        imageService.downloadImage(image.getUrl(), imageView);

        upload.setText("UPDATE");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageService.put(image, context).setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            finish();
                        }
                    }
                });
            }
        });

        setInputBinders();
    }

    private void setInputBinders() {
        tags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                image.setTags(tags.getText().toString());
            }
        });

        author.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                image.setAuthor(author.getText().toString());
            }
        });
    }
}

package com.example.adriaalvarez.android_boilerplate.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.adriaalvarez.android_boilerplate.models.Image;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;

import java.io.File;

/**
 * Created by adria.alvarez on 08/06/2017.
 */

public class ImageService {

    private String apiHostname = "https://tech-images-api.azurewebsites.net";
    private Utils utils;

    public ImageService() {
        utils = new Utils();
    }

    public Future<JsonObject> get(int imageId, Context context) {
        return Ion.with(context)
                .load(apiHostname + "/api/images/" + imageId)
                .asJsonObject();
    }

    public Future<JsonArray> getAll(Context context) {
        return Ion.with(context)
                .load(apiHostname + "/api/images")
                .asJsonArray();
    }

    public Future<JsonObject> post(Image image, String imagePath, Context context) {
        return Ion.with(context)
            .load("post", apiHostname + "/api/images/")
            .setMultipartParameter("author", image.getAuthor())
            .setMultipartParameter("tags", image.getTags())
            .setMultipartFile("image", new File(imagePath))
            .asJsonObject();
    }

    public Future<JsonObject> put(Image image, Context context) {
        JsonObject json = new JsonObject();
        json.addProperty("author", image.getAuthor());
        json.addProperty("tags", image.getTags());

        return Ion.with(context)
                .load("put", apiHostname + "/api/images/" + image.getId())
                .setJsonObjectBody(json)
                .asJsonObject();
    }

    public void downloadImage(String url, ImageView view) {
        Ion.with(view)
            .load(apiHostname + url);
    }

    public Image mapJson(JsonObject json) {
        return new Image(
                json.get("id").getAsInt(),
                json.get("url").getAsString(),
                !json.get("author").isJsonNull() ? json.get("author").getAsString() : "",
                !json.get("tags").isJsonNull() ? json.get("tags").getAsString() : "",
                utils.convertToDate(json.get("creationDate").getAsString())
        );
    }
}

package com.example.adriaalvarez.android_boilerplate.services;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adria.alvarez on 09/06/2017.
 */

public class StorageService {

    private Utils utils;

    public  StorageService() {
        utils = new Utils();
    }

    public String saveImage(Bitmap bitmap) {
        String fileName = "/sdcard/" + utils.getCurrentDate() + ".jpg";
        storeCameraPhotoInSDCard(bitmap, fileName);
        return fileName;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String partFilename){
        File outputFile = new File(partFilename);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.android.dmk78.downloadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button buttonDownload;
    private String url = "https://go1.imgsmail.ru/imgpreview?key=10e54c77b123ff61&mb=imgdb_preview_132";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        buttonDownload = findViewById(R.id.buttonDownload);


    }


    public void onClickDownloadImage(View view) {
        DownloadImageTask task = new DownloadImageTask();
        Bitmap result=null;

        try {

            result = task.execute( url).get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(result);


    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                return bmp;



            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

    ;
}

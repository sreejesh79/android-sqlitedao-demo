package com.kriyatma.services;

import android.os.AsyncTask;

import com.kriyatma.events.Event;
import com.kriyatma.events.EventDispatcher;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sreejeshpillai on 26/05/15.
 */
public class DownloadService extends EventDispatcher {

    private Integer identifier;


    public void execute(Integer id,String input, String output){
        identifier = id;

        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
        downloadAsyncTask.execute(input,output);
    }

    private class DownloadAsyncTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... urls) {
            int count;
            String inputUrl = urls[0];
            String outputUrl = urls[1];
             try {
                 URL downloadUrl = new URL(inputUrl);
                 URLConnection connection = downloadUrl.openConnection();
                 connection.connect();
                 int lengthOfFile = connection.getContentLength();
                 InputStream inputStream = new BufferedInputStream(downloadUrl.openStream());
                 OutputStream outputStream = new FileOutputStream(outputUrl);

                 byte data[] = new byte[1024];
                 long total = 0;
                 while ((count = inputStream.read(data)) != -1) {
                     total += count;
                     publishProgress(""+(int)((total*100)/lengthOfFile));
                     outputStream.write(data, 0, count);
                 }
                 outputStream.flush();
                 outputStream.close();
                 inputStream.close();
                 dispatchEvent(new Event(Event.COMPLETE,this,identifier));

            }catch (Exception e){
                 dispatchEvent(new Event(Event.ERROR,this,identifier));
            }
            return null;
        }

        protected void onProgressUpdate(String... progress){
           // Log.d("ANDRO_ASYNC",progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
          //  Log.d("ANDRO_ASYNC","download complete");
           // dispatchEvent(new Event(Event.COMPLETE));
        }

    }
}

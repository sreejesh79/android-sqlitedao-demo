package com.kriyatma.services;

import android.os.AsyncTask;
import android.util.Log;

import com.kriyatma.events.EventDispatcher;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sreejeshpillai on 21/05/15.
 */
public class HTTPService extends EventDispatcher implements IHTTPService {

    private static final String GET_TYPE = "GET";
    private static final String POST_TYPE = "POST";

    private String strUrl;
    private String strType;
    private JSONObject jsonPost;
    private HTTPAsyncTask httpAsyncTask;

    public void get(String url)
    {
        strUrl = url;
        strType = GET_TYPE;
        execute();
    }

    public void post(String url,JSONObject params)
    {
        strUrl = url;
        strType = POST_TYPE;
        jsonPost = params;
        execute();
    }

    private void execute()
    {
        httpAsyncTask = new HTTPAsyncTask();
        httpAsyncTask.execute(strUrl,strType);
    }

    private class HTTPAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... arguments) {
            String response = "";
            String line = null;

            try{
                String link = (String)arguments[0];
                String type = (String)arguments[1];
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                HTTPFactory(connection);

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line+'\n');
                }
                response = stringBuilder.toString();

            }catch(Exception e){

            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
          // Log.d("result", result);
            Log.d("onPostExecute","onPostExecute");
            //dispatchEvent(new ServiceEvent(ServiceEvent.SERVICE_RESPONSE,result));
        }

        private void HTTPFactory(HttpURLConnection connection){
            switch (strType){
                case GET_TYPE:
                    doGet(connection);
                    break;
                case POST_TYPE:
                    doPost(connection);
                    break;
                default:
                    doGet(connection);
                    break;
            }
        }

        private void doGet(HttpURLConnection connection){
            try{
                connection.setRequestMethod(strType);
                connection.setDoInput(true);
                connection.connect();

            }catch(Exception e){

            }

        }

        private void doPost(HttpURLConnection connection){
            try{
                connection.setRequestMethod(strType);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
                connection.connect();

                //setup send
                OutputStream os = new BufferedOutputStream(connection.getOutputStream());
                os.write(jsonPost.toString().getBytes());
                //clean up
                os.flush();
                os.close();
            }catch(Exception e){

            }
        }
    }



}

package com.kriyatma.services;

import org.json.JSONObject;

/**
 * Created by sreejeshpillai on 21/05/15.
 */
public interface IHTTPService {
    public void get(String url);
    public void post(String url, JSONObject params);

}

package com.kriyatma.services;

import com.kriyatma.events.Event;

/**
 * Created by sreejeshpillai on 22/05/15.
 */
public class ServiceEvent extends Event{

    public static final String SERVICE_RESPONSE = "service_response";

    public ServiceEvent(String type,String data){
        super(type,null,data);
    }
}

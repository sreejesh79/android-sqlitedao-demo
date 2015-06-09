package com.kriyatma.events;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sreejeshpillai on 20/05/15.
 */
public class EventDispatcher implements IEventDispatcher {

    protected ArrayList<Listener> listenerList = new ArrayList<>();


    public void addEventListener(String type,IEventHandler handler){
        Listener listener = new Listener(type,handler);
        removeEventListener(type);
        listenerList.add(0,listener);
    }

    public void removeEventListener(String type){
        for(Iterator<Listener> iterator = listenerList.iterator();iterator.hasNext();) {
           Listener listener = (Listener) iterator.next();
            if(listener.getType() == type)
            {
                listenerList.remove(listener);
            }
        }
    }

    public void dispatchEvent(Event event){

        //Log.d("dispatchEvent",event.getType());
        for(Iterator<Listener> iterator = listenerList.iterator();iterator.hasNext();) {
            Listener listener = (Listener) iterator.next();
            if(event.getType() == listener.getType()){
                IEventHandler handler = listener.getHandler();
                handler.callback(event);
            }
        }
    }

    public Boolean hasEventListener(String type){
        Boolean bIsAvailable = false;
        for(Iterator<Listener> iterator = listenerList.iterator();iterator.hasNext();) {
            Listener listener = (Listener) iterator.next();
            if(type == listener.getType()){
                return true;
            }
        }
        return bIsAvailable;
    }

    public void removeAllListeners(){
        listenerList.clear();
    }
}

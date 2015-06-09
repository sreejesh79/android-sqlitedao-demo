package com.kriyatma.events;

/**
 * Created by sreejeshpillai on 20/05/15.
 */
public class Event {
    public static final  String CLICK = "click";
    public static final  String COMPLETE = "complete";
    public static final  String CHANGE = "change";
    public static final  String SELECT = "select";
    public static final  String ADDED = "added";
    public static final String ERROR = "on_error";

    private String type;
    private Object target;
    private Object params;
    public Event(String type){
        initProperties(type,null,null);
    }
    public  Event(String type,Object target){
        initProperties(type,target,null);
    }
    public Event(String type,Object target,Object params){
        initProperties(type,target,params);
    }
    protected void initProperties(String type,Object target,Object params)
    {
        this.type = type;
        this.target = target;
        this.params = params;
    }

    public String getType()
    {
        return type;
    }

    public Object getTarget(){
        return target;
    }

    public Object getParams(){
        return params;
    }

}

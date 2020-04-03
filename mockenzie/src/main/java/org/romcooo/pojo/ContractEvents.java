package org.romcooo.pojo;

public class ContractEvents
{
    private String eventType;
    
    private String eventDate;
    
    public String getEventType ()
    {
        return eventType;
    }
    
    public void setEventType (String eventType)
    {
        this.eventType = eventType;
    }
    
    public String getEventDate ()
    {
        return eventDate;
    }
    
    public void setEventDate (String eventDate)
    {
        this.eventDate = eventDate;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [eventType = "+eventType+", eventDate = "+eventDate+"]";
    }
}



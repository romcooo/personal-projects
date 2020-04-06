package org.romcooo.pojo;

public class ContractFullInfoRequest
{
    private Data data;
    
    private String systemEvent;
    
    public Data getData ()
    {
        return data;
    }
    
    public void setData (Data data)
    {
        this.data = data;
    }
    
    public String getSystemEvent ()
    {
        return systemEvent;
    }
    
    public void setSystemEvent (String systemEvent)
    {
        this.systemEvent = systemEvent;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", systemEvent = "+systemEvent+"]";
    }
}
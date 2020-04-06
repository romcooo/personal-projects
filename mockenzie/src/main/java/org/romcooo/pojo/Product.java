package org.romcooo.pojo;

public class Product
{
    private String code;
    
    private String versionNumber;
    
    public String getCode ()
    {
        return code;
    }
    
    public void setCode (String code)
    {
        this.code = code;
    }
    
    public String getVersionNumber ()
    {
        return versionNumber;
    }
    
    public void setVersionNumber (String versionNumber)
    {
        this.versionNumber = versionNumber;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", versionNumber = "+versionNumber+"]";
    }
}

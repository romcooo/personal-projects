package org.romcooo.pojo;

public class Person
{
    private String cuid;
    
    private String personRole;
    
    public String getCuid ()
    {
        return cuid;
    }
    
    public void setCuid (String cuid)
    {
        this.cuid = cuid;
    }
    
    public String getPersonRole ()
    {
        return personRole;
    }
    
    public void setPersonRole (String personRole)
    {
        this.personRole = personRole;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [cuid = "+cuid+", personRole = "+personRole+"]";
    }
}

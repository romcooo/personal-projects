package org.romcooo.pojo;

public class ContractService
{
    private String serviceVersion;
    
    private String serviceCode;
    
    private String serviceStatus;
    
    private String externalId;
    
    private String serviceName;
    
    private String serviceTypeCode;
    
    public String getServiceVersion ()
    {
        return serviceVersion;
    }
    
    public void setServiceVersion (String serviceVersion)
    {
        this.serviceVersion = serviceVersion;
    }
    
    public String getServiceCode ()
    {
        return serviceCode;
    }
    
    public void setServiceCode (String serviceCode)
    {
        this.serviceCode = serviceCode;
    }
    
    public String getServiceStatus ()
    {
        return serviceStatus;
    }
    
    public void setServiceStatus (String serviceStatus)
    {
        this.serviceStatus = serviceStatus;
    }
    
    public String getExternalId ()
    {
        return externalId;
    }
    
    public void setExternalId (String externalId)
    {
        this.externalId = externalId;
    }
    
    public String getServiceName ()
    {
        return serviceName;
    }
    
    public void setServiceName (String serviceName)
    {
        this.serviceName = serviceName;
    }
    
    public String getServiceTypeCode ()
    {
        return serviceTypeCode;
    }
    
    public void setServiceTypeCode (String serviceTypeCode)
    {
        this.serviceTypeCode = serviceTypeCode;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [serviceVersion = "+serviceVersion+", serviceCode = "+serviceCode+", serviceStatus = "+serviceStatus+", externalId = "+externalId+", serviceName = "+serviceName+", serviceTypeCode = "+serviceTypeCode+"]";
    }
}

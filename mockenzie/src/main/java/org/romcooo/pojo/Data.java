package org.romcooo.pojo;


public class Data
{
    private String contractOwnerCode;
    
    private ContractParameterREL contractParameterREL;
    
    private ContractService[] contractService;
    
    private String currentStatus;
    
    private String salesroomCode;
    
    private Person person;
    
    private ContractDocument[] contractDocument;
    
    private ContractEvents[] contractEvents;
    
    private String contractCode;
    
    public String getContractOwnerCode ()
    {
        return contractOwnerCode;
    }
    
    public void setContractOwnerCode (String contractOwnerCode)
    {
        this.contractOwnerCode = contractOwnerCode;
    }
    
    public ContractParameterREL getContractParameterREL ()
    {
        return contractParameterREL;
    }
    
    public void setContractParameterREL (ContractParameterREL contractParameterREL)
    {
        this.contractParameterREL = contractParameterREL;
    }
    
    public ContractService[] getContractService ()
    {
        return contractService;
    }
    
    public void setContractService (ContractService[] contractService)
    {
        this.contractService = contractService;
    }
    
    public String getCurrentStatus ()
    {
        return currentStatus;
    }
    
    public void setCurrentStatus (String currentStatus)
    {
        this.currentStatus = currentStatus;
    }
    
    public String getSalesroomCode ()
    {
        return salesroomCode;
    }
    
    public void setSalesroomCode (String salesroomCode)
    {
        this.salesroomCode = salesroomCode;
    }
    
    public Person getPerson ()
    {
        return person;
    }
    
    public void setPerson (Person person)
    {
        this.person = person;
    }
    
    public ContractDocument[] getContractDocument ()
    {
        return contractDocument;
    }
    
    public void setContractDocument (ContractDocument[] contractDocument)
    {
        this.contractDocument = contractDocument;
    }
    
    public ContractEvents[] getContractEvents ()
    {
        return contractEvents;
    }
    
    public void setContractEvents (ContractEvents[] contractEvents)
    {
        this.contractEvents = contractEvents;
    }
    
    public String getContractCode ()
    {
        return contractCode;
    }
    
    public void setContractCode (String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [contractOwnerCode = "+contractOwnerCode+", contractParameterREL = "+contractParameterREL+", contractService = "+contractService+", currentStatus = "+currentStatus+", salesroomCode = "+salesroomCode+", person = "+person+", contractDocument = "+contractDocument+", contractEvents = "+contractEvents+", contractCode = "+contractCode+"]";
    }
}


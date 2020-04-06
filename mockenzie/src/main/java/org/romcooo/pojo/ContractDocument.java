package org.romcooo.pojo;

import javax.xml.bind.annotation.XmlAttribute;

public class ContractDocument
{
    @XmlAttribute
    private String documentTypeCode;
    
    public String getDocumentTypeCode ()
    {
        return documentTypeCode;
    }
    
    public void setDocumentTypeCode (String documentTypeCode)
    {
        this.documentTypeCode = documentTypeCode;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [documentTypeCode = "+documentTypeCode+"]";
    }
}


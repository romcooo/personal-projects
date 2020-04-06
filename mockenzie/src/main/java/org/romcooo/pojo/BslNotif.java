package org.romcooo.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BslNotif {
    private ContractFullInfoRequest ContractFullInfoRequest;
    
    public ContractFullInfoRequest getContractFullInfoRequest() {
        return ContractFullInfoRequest;
    }
    
    public void setContractFullInfoRequest(ContractFullInfoRequest ContractFullInfoRequest) {
        this.ContractFullInfoRequest = ContractFullInfoRequest;
    }
    
    @Override
    public String toString() {
        return "ClassPojo [ContractFullInfoRequest = " + ContractFullInfoRequest + "]";
    }
}

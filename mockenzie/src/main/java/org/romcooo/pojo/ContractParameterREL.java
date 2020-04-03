package org.romcooo.pojo;

public class ContractParameterREL
{
    private Product product;
    
    private String productVariantCode;
    
    private String repaymentChannel;
    
    private String creditAccountNumber;
    
    private String minInstallmentAmount;
    
    private String creditType;
    
    private String productName;
    
    private String netCashPayment;
    
    private String productOfferLimitType;
    
    private String initTransactionType;
    
    private String billingDay;
    
    private String accountingMethod;
    
    private String creditLimit;
    
    private String currencyCode;
    
    private String providedCreditAmount;
    
    public Product getProduct ()
    {
        return product;
    }
    
    public void setProduct (Product product)
    {
        this.product = product;
    }
    
    public String getProductVariantCode ()
    {
        return productVariantCode;
    }
    
    public void setProductVariantCode (String productVariantCode)
    {
        this.productVariantCode = productVariantCode;
    }
    
    public String getRepaymentChannel ()
    {
        return repaymentChannel;
    }
    
    public void setRepaymentChannel (String repaymentChannel)
    {
        this.repaymentChannel = repaymentChannel;
    }
    
    public String getCreditAccountNumber ()
    {
        return creditAccountNumber;
    }
    
    public void setCreditAccountNumber (String creditAccountNumber)
    {
        this.creditAccountNumber = creditAccountNumber;
    }
    
    public String getMinInstallmentAmount ()
    {
        return minInstallmentAmount;
    }
    
    public void setMinInstallmentAmount (String minInstallmentAmount)
    {
        this.minInstallmentAmount = minInstallmentAmount;
    }
    
    public String getCreditType ()
    {
        return creditType;
    }
    
    public void setCreditType (String creditType)
    {
        this.creditType = creditType;
    }
    
    public String getProductName ()
    {
        return productName;
    }
    
    public void setProductName (String productName)
    {
        this.productName = productName;
    }
    
    public String getNetCashPayment ()
    {
        return netCashPayment;
    }
    
    public void setNetCashPayment (String netCashPayment)
    {
        this.netCashPayment = netCashPayment;
    }
    
    public String getProductOfferLimitType ()
    {
        return productOfferLimitType;
    }
    
    public void setProductOfferLimitType (String productOfferLimitType)
    {
        this.productOfferLimitType = productOfferLimitType;
    }
    
    public String getInitTransactionType ()
    {
        return initTransactionType;
    }
    
    public void setInitTransactionType (String initTransactionType)
    {
        this.initTransactionType = initTransactionType;
    }
    
    public String getBillingDay ()
    {
        return billingDay;
    }
    
    public void setBillingDay (String billingDay)
    {
        this.billingDay = billingDay;
    }
    
    public String getAccountingMethod ()
    {
        return accountingMethod;
    }
    
    public void setAccountingMethod (String accountingMethod)
    {
        this.accountingMethod = accountingMethod;
    }
    
    public String getCreditLimit ()
    {
        return creditLimit;
    }
    
    public void setCreditLimit (String creditLimit)
    {
        this.creditLimit = creditLimit;
    }
    
    public String getCurrencyCode ()
    {
        return currencyCode;
    }
    
    public void setCurrencyCode (String currencyCode)
    {
        this.currencyCode = currencyCode;
    }
    
    public String getProvidedCreditAmount ()
    {
        return providedCreditAmount;
    }
    
    public void setProvidedCreditAmount (String providedCreditAmount)
    {
        this.providedCreditAmount = providedCreditAmount;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [product = "+product+", productVariantCode = "+productVariantCode+", repaymentChannel = "+repaymentChannel+", creditAccountNumber = "+creditAccountNumber+", minInstallmentAmount = "+minInstallmentAmount+", creditType = "+creditType+", productName = "+productName+", netCashPayment = "+netCashPayment+", productOfferLimitType = "+productOfferLimitType+", initTransactionType = "+initTransactionType+", billingDay = "+billingDay+", accountingMethod = "+accountingMethod+", creditLimit = "+creditLimit+", currencyCode = "+currencyCode+", providedCreditAmount = "+providedCreditAmount+"]";
    }
}


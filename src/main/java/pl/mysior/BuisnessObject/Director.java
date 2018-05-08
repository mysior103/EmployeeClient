package pl.mysior.BuisnessObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Director extends Worker implements Serializable {
    @XmlTransient
    private BigDecimal businessAllowance;
    private String creditCardNumber;
    private BigDecimal costLimit;

    public Director() {
    }

    public BigDecimal getBusinessAllowance() {
        return businessAllowance;
    }

    public void setBusinessAllowance(BigDecimal businessAllowance) {
        this.businessAllowance = businessAllowance;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public BigDecimal getCostLimit() {
        return costLimit;
    }

    public void setCostLimit(BigDecimal costLimit) {
        this.costLimit = costLimit;
    }
}

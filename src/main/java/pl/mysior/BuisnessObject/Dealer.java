package pl.mysior.BuisnessObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dealer extends Worker implements Serializable {
    @XmlTransient
    private BigDecimal commission;
    private BigDecimal maxCommission;

    public Dealer() {
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getMaxCommission() {
        return maxCommission;
    }

    public void setMaxCommission(BigDecimal maxCommission) {
        this.maxCommission = maxCommission;
    }


}

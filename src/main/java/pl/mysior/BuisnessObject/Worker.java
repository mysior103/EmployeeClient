package pl.mysior.BuisnessObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class Worker implements Serializable {
    public static final Long MIN_ID = new Long("10000000000");
    public static final Long MAX_ID = new Long("99999999999");

    private String name;
    private String lastName;
    private Long ID;
    private String position;
    private BigDecimal salary;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        if (ID < MIN_ID || ID > MAX_ID) {
            throw new RuntimeException("Too short number");
        } else {
            this.ID = ID;
        }
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

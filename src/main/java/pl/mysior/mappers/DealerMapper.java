package pl.mysior.mappers;

import pl.mysior.BuisnessObject.Dealer;
import pl.mysior.BuisnessObject.Director;

public class DealerMapper {
    public Dealer map(pl.mysior.Dealer dealerWS){
        Dealer dealer = new Dealer();
        dealer.setID(dealerWS.getID());
        dealer.setName(dealerWS.getName());
        dealer.setLastName(dealerWS.getLastName());
        dealer.setPosition(dealerWS.getPosition());
        dealer.setSalary(dealerWS.getSalary());
        dealer.setPhoneNumber(dealerWS.getPhoneNumber());
        dealer.setCommission(dealerWS.getCommission());
        dealer.setMaxCommission(dealerWS.getMaxCommission());
        return dealer;
    }
}

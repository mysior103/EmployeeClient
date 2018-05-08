package pl.mysior.mappers;

import pl.mysior.BuisnessObject.Director;

public class DirectorMapper {

    public Director map(pl.mysior.Director directorWS){
        Director director = new Director();
        director.setID(directorWS.getID());
        director.setName(directorWS.getName());
        director.setLastName(directorWS.getLastName());
        director.setPosition(directorWS.getPosition());
        director.setSalary(directorWS.getSalary());
        director.setPhoneNumber(directorWS.getPhoneNumber());
        director.setCostLimit(directorWS.getCostLimit());
        director.setCreditCardNumber(directorWS.getCreditCardNumber());
        return director;
    }
}

package vd.parkmeapp.models;

public interface FilterData {

    boolean filter(User currentUser, User parkingOwner);
}

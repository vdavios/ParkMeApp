package vd.parkmeapp.models;

public class FilterDataImpl implements FilterData {
    @Override
    public boolean filter(User currentUser, User parkingOwner) {
        return !currentUser.getFirstName().equals(parkingOwner.getFirstName())
                && !currentUser.getLastName().equals(parkingOwner.getLastName())
                && parkingOwner.getHasParking().equals("yes")
                && parkingOwner.getRented().equals("no");
    }
}

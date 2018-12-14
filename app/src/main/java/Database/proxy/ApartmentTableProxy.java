package Database.proxy;

import java.util.Collection;

import Database.Apartment;
import Database.MySQL.ApartmentTable;

public abstract class ApartmentTableProxy {
    private static ApartmentTableProxy getInstance() { return new ApartmentTable(); }

    /** Abstraktní metody **/
    protected abstract Collection<Apartment> selectAll();

    /** Statické metody **/
    public static Collection<Apartment> SelectAll() {
        return getInstance().selectAll();
    }
}

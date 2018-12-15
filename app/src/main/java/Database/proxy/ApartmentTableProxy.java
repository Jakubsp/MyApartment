package Database.proxy;

import java.util.Collection;

import Database.Apartment;
import Database.ConfigurationManager;
import Database.DatabaseType;

public abstract class ApartmentTableProxy {
    private static ApartmentTableProxy getInstance() {
        if (ConfigurationManager.getInstance().getDatabaseType() == DatabaseType.MySQL)
            return new Database.MySQL.ApartmentTable();
        return new Database.XML.ApartmentTable();
    }

    /** Abstraktní metody **/
    protected abstract Collection<Apartment> selectAll();

    /** Statické metody **/
    public static Collection<Apartment> SelectAll() {
        return getInstance().selectAll();
    }
}

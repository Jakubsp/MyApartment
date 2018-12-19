package Database.proxy;

import java.util.Collection;

import Database.ConfigurationManager;
import Database.DatabaseType;
import Database.Overview;

public abstract class OverviewTableProxy {
    private static OverviewTableProxy getInstance() {
        //if (ConfigurationManager.getInstance().getDatabaseType().equals(DatabaseType.MySQL))
            return new Database.MySQL.OverviewTable();
        //return new Database.JSON.ApartmentTable();
    }

    /** Abstraktní metody **/
    protected abstract Collection<Overview> selectAll();

    protected abstract Overview selectNewestByApartmentId(int apartmentId);

    /** Statické metody **/
    public static Collection<Overview> SelectAll() {
        return getInstance().selectAll();
    }

    public static Overview SelectNewestByApartmentId(int apartmentId) {
        return getInstance().selectNewestByApartmentId(apartmentId);
    }
}

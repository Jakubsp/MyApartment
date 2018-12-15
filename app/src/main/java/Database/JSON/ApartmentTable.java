package Database.JSON;

import java.util.Collection;

import Database.Apartment;
import Database.proxy.ApartmentTableProxy;

public class ApartmentTable extends ApartmentTableProxy {
    @Override
    protected Collection<Apartment> selectAll() {
        return null;
    }
}

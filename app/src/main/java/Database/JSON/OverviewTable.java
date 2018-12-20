package Database.JSON;

import java.util.ArrayList;
import java.util.Collection;

import Database.Overview;
import Database.proxy.OverviewTableProxy;

public class OverviewTable extends OverviewTableProxy {

    private Collection<Overview> overviews;
    private Overview overview;

    @Override
    protected Collection<Overview> selectAll() {
        overviews = new ArrayList<>();

        return overviews;
    }

    @Override
    protected Overview selectNewestByApartmentId(int apartmentId) {
        return null;
    }
}

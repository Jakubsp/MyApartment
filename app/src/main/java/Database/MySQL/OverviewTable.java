package Database.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Database.DBConnect;
import Database.Overview;
import Database.proxy.OverviewTableProxy;

import static Database.MySQL.OverviewCmd.SELECTALL;

public class OverviewTable extends OverviewTableProxy {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Collection<Overview> overviews;
    Overview overview;

    @Override
    protected Collection<Overview> selectAll() {
        overviews = new ArrayList<>();

        if (connection == null)
            connection = DBConnect.getInstance().getConnection();
        if (connection == null)
            return overviews;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(SELECTALL);
                    resultSet = preparedStatement.executeQuery();
                    while(resultSet.next())
                        overviews.add(parse(resultSet));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return overviews;
    }

    @Override
    protected Overview selectNewestByApartmentId(int apartmentId) {
        overview = null;

        if (connection == null)
            connection = DBConnect.getInstance().getConnection();
        if (connection == null)
            return overview;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(SELECTALL);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    overview = parse(resultSet);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return overview;
    }

    public Overview parse(ResultSet result) throws SQLException {
        Overview overview = new Overview();
        overview.setId(result.getInt("ID"));
        overview.setDate(result.getDate("DATE"));
        overview.setWater(result.getInt("WATER"));
        overview.setGas(result.getInt("GAS"));
        overview.setElectricity(result.getInt("ELECTRICITY"));
        overview.setAvgTemp(result.getInt("AVG_TEMP"));
        overview.setApartmentId(result.getInt("APARTMENT_ID"));
        return overview;
    }
}

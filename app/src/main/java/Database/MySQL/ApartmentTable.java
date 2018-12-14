package Database.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import Database.Apartment;
import Database.DBConnect;
import Database.proxy.ApartmentTableProxy;

import static Database.MySQL.ApartmentCmd.*;

public class ApartmentTable extends ApartmentTableProxy {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Collection<Apartment> apartments;

    @Override
    protected Collection<Apartment> selectAll() {
        apartments = new ArrayList<>();
        if (connection == null)
            connection = DBConnect.getInstance().getConnection();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(SELECTALL);
                    resultSet = preparedStatement.executeQuery();
                    while(resultSet.next())
                        apartments.add(parse(resultSet));
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

        return apartments;
    }

    public Apartment parse(ResultSet result) throws SQLException {
        Apartment apartment = new Apartment();
        apartment.setId(result.getInt("ID"));
        apartment.setRooms(result.getInt("ROOMS"));
        apartment.setTenantId(result.getInt("PERSON_ID"));
        apartment.setUsableArea(result.getInt("USABLE_AREA"));
        apartment.setFloor(result.getInt("FLOOR"));
        apartment.setPsswd(result.getString("NFC_PSWD"));
        return apartment;
    }
}

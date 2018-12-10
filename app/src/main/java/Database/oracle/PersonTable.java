package Database.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;

import Database.DBConnect;
import Database.Person;
import Database.proxy.PersonTableProxy;

import static Database.oracle.PersonCmd.*;

public class PersonTable extends PersonTableProxy {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Collection<Person> people;

    @Override
    protected boolean insert(final Person person) {

        if (connection == null)
            connection = DBConnect.getInstance().getConnection();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(INSERT);
                    preparedStatement.setNull(1, Types.NULL);
                    preparedStatement.setString(2, person.getName());
                    preparedStatement.setString(3, person.getCompanyName());
                    preparedStatement.setNull(4, Types.NULL);
                    preparedStatement.setString(5, person.getRights());
                    preparedStatement.setString(6, person.getEmail());
                    preparedStatement.setString(7, person.getNfcUid());
                    preparedStatement.setString(8, person.getTask());
                    preparedStatement.setInt(9, person.getSuperiorId());

                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                    connection.commit();

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

        return true;
    }

    @Override
    protected boolean update(Person person) {
        return true;
    }

    @Override
    protected boolean delete(final int idPerson) {

        if (connection == null)
            connection = DBConnect.getInstance().getConnection();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(DELETE);
                    preparedStatement.setInt(1, idPerson);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                    connection.commit();

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

        return true;
    }

    @Override
    protected Collection<Person> selectAll() {
        people = new ArrayList<>();
        if (connection == null)
            connection = DBConnect.getInstance().getConnection();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(SELECTALL);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        people.add(parse(resultSet));
                    }
                    preparedStatement.close();
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

        return people;
    }


    public Person parse(ResultSet result) throws SQLException {
        Person person = new Person();
        person.setId(result.getInt("ID"));
        person.setName(result.getString("NAME"));
        person.setCompanyName(result.getString("COMPANY_NAME"));
        person.setDateOfBirth(result.getDate("DATE_OF_BIRTH"));
        person.setRights(result.getString("RIGHTS"));
        person.setEmail(result.getString("EMAIL"));
        person.setNfcUid(result.getString("NFC_UID"));
        person.setTask(result.getString("TASK"));
        person.setSuperiorId(result.getInt("PERSON_ID"));
        return person;
    }
}

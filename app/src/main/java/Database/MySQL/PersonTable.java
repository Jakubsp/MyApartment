package Database.MySQL;

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

import static Database.MySQL.PersonCmd.*;

public class PersonTable extends PersonTableProxy {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Collection<Person> people;
    private Person person;

    @Override
    protected boolean insert(final Person person) {

        if (connection == null)
            connection = DBConnect.getInstance().getConnection();

        if (connection == null)
            return false;

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
    protected boolean update(final Person person) {
        if (connection == null)
            connection = DBConnect.getInstance().getConnection();

        if (connection == null)
            return false;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(UPDATE);
                    preparedStatement.setString(1, person.getName());
                    preparedStatement.setString(2, person.getCompanyName());
                    preparedStatement.setNull(3, Types.NULL);
                    preparedStatement.setString(4, person.getRights());
                    preparedStatement.setString(5, person.getEmail());
                    preparedStatement.setString(6, person.getNfcUid());
                    preparedStatement.setString(7, person.getTask());
                    preparedStatement.setInt(8, person.getSuperiorId());
                    preparedStatement.setInt(9, person.getId());

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
    protected boolean delete(final int idPerson) {

        if (connection == null)
            connection = DBConnect.getInstance().getConnection();

        if (connection == null)
            return false;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(DELETE);
                    preparedStatement.setInt(1, idPerson);

                    preparedStatement.executeUpdate();
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

        return true;
    }

    @Override
    protected Collection<Person> selectAll() {
        people = new ArrayList<>();
        if (connection == null)
            connection = DBConnect.getInstance().getConnection();
        if (connection == null)
            return people;
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

    @Override
    protected Collection<Person> selectAllUsers() {
        people = new ArrayList<>();
        if (connection == null)
            connection = DBConnect.getInstance().getConnection();
        if (connection == null)
        return people;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(SELECTALLUSERS);
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

    @Override
    protected Person selectById(final int id) {
        person = new Person();

        if (connection == null)
            connection = DBConnect.getInstance().getConnection();
        if (connection == null)
            return person;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(SELECTBYID);
                    preparedStatement.setInt(1, id);
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    person = parse(resultSet);
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

        return person;
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

package Database.oracle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import Database.DBConnect;
import Database.Person;
import Database.proxy.PersonTableProxy;

import static Database.oracle.PersonCmd.SELECTALL;

public class PersonTable extends PersonTableProxy {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private Collection<Person> people;

    @Override
    protected int insert(Person person) {
        return 0;
    }

    @Override
    protected int update(Person person) {
        return 0;
    }

    @Override
    protected Collection<Person> select() {
        people = new ArrayList<>();
        if(connection == null)
            connection = DBConnect.getInstance().getConnection();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    preparedStatement = connection.prepareStatement(SELECTALL);
                    resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()) {
                        people.add(parse(resultSet));
                    }
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
    protected Person selectOne(int id) {
        return null;
    }

    @Override
    protected int delete(int id) {
        return 0;
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

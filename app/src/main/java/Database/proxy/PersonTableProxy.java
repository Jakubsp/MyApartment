package Database.proxy;

import java.util.Collection;

import Database.ConfigurationManager;
import Database.DatabaseType;
import Database.Person;

public abstract class PersonTableProxy {
    private static PersonTableProxy getInstance() {
        if (ConfigurationManager.getInstance().getDatabaseType().equals(DatabaseType.MySQL))
            return new Database.MySQL.PersonTable();
        return new Database.JSON.PersonTable();
    }

    /** Abstraktní metody **/
    protected abstract boolean insert(Person person);

    protected abstract boolean update(Person person);

    protected abstract boolean delete(int idPerson);

    protected abstract Collection<Person> selectAll();

    protected abstract Collection<Person> selectAllUsers();

    protected abstract Person selectById(int id);

    /** Statické metody **/
    public static boolean Insert(Person person) {
        return getInstance().insert(person);
    }

    public static boolean Update(Person person) {
        return getInstance().update(person);
    }

    public static Collection<Person> SelectAll() { return getInstance().selectAll(); }

    public static Collection<Person> SelectAllUsers() { return getInstance().selectAllUsers(); }

    public static Person SelectById(int id) { return getInstance().selectById(id); }

    public static boolean Delete(int idPerson) { return getInstance().delete(idPerson); }
}

package Database.proxy;

import java.util.Collection;

import Database.Person;
import Database.oracle.PersonTable;

public abstract class PersonTableProxy {
    private static PersonTableProxy getInstance() {
        return new PersonTable();
    }

    /** Abstraktní metody **/
    protected abstract boolean insert(Person person);

    protected abstract boolean update(Person person);

    protected abstract boolean delete(int idPerson);

    protected abstract Collection<Person> selectAll();

    /** Statické metody **/
    public static boolean Insert(Person person) {
        return getInstance().insert(person);
    }

    public static boolean Update(Person person) {
        return getInstance().update(person);
    }

    public static Collection<Person> SelectAll() {
        return getInstance().selectAll();
    }

    public static boolean Delete(int idPerson) { return getInstance().delete(idPerson); }
}

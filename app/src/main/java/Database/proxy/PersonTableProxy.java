package Database.proxy;

import java.util.Collection;

import Database.Person;
import Database.oracle.PersonTable;

public abstract class PersonTableProxy {
    private static PersonTableProxy getInstance() {
        return new PersonTable();
    }

    /** Abstraktní metody **/
    protected abstract int insert(Person person);

    protected abstract int update(Person person);

    protected abstract Collection<Person> selectAll();

    protected abstract Person selectOne(int id);

    protected abstract int delete(int id);

    /** Statické metody **/
    public static int Insert(Person person) {
        return getInstance().insert(person);
    }

    public static int Update(Person person) {
        return getInstance().update(person);
    }

    public static Collection<Person> SelectAll() {
        return getInstance().selectAll();
    }
}

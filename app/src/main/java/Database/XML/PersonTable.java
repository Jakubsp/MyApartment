package Database.XML;

import java.util.Collection;

import Database.Person;
import Database.proxy.PersonTableProxy;

public class PersonTable extends PersonTableProxy {
    @Override
    protected boolean insert(Person person) {
        return false;
    }

    @Override
    protected boolean update(Person person) {
        return false;
    }

    @Override
    protected boolean delete(int idPerson) {
        return false;
    }

    @Override
    protected Collection<Person> selectAll() {
        return null;
    }

    @Override
    protected Person selectById(int id) {
        return null;
    }
}

package Database.JSON;

import android.os.Environment;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Database.ConfigurationManager;
import Database.Person;
import Database.proxy.PersonTableProxy;

public class PersonTable extends PersonTableProxy {

    public String loadJSONFromFile() {
        String json = null;
        try {
            InputStream is = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/" + ConfigurationManager.getInstance().getJsonFileName());
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void saveJSONToFile(String json) {
        try {
            OutputStream is = new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/" + ConfigurationManager.getInstance().getJsonFileName());
            byte[] buffer = json.getBytes();
            is.write(buffer, 0, buffer.length);
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public PersonTable() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/" + ConfigurationManager.getInstance().getJsonFileName());

            if (!file.exists()) {
                OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                JSONObject myApartment = new JSONObject();
                myApartment.put("people", new JSONArray());
                myApartment.put("apartments", new JSONArray());
                os.write(myApartment.toString());
                os.flush();
                os.close();
            }

        } catch (JSONException|IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean insert(Person person) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromFile());

            JSONObject jsonPerson = new JSONObject();

            int id;
            if (((List<Person>)selectAll()).isEmpty())
                id = 1;
            else
                id = ((List<Person>)selectAll()).get(((List<Person>)selectAll()).size() - 1).getId() + 1;

            jsonPerson.put("id", id);
            jsonPerson.put("name", person.getName());
            jsonPerson.put("companyName", person.getCompanyName());
            jsonPerson.put("dateOfBirth", person.getDateOfBirth());
            jsonPerson.put("rights", person.getRights());
            jsonPerson.put("nfcUid", person.getNfcUid());
            jsonPerson.put("email", person.getEmail());
            jsonPerson.put("superiorId", person.getSuperiorId());
            jsonPerson.put("task", person.getTask());

            JSONArray jsonPeople = jsonObject.getJSONArray("people");
            jsonPeople.put(jsonPerson);
            saveJSONToFile(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return true;
    }

    @Override
    protected boolean update(Person person) {

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromFile());
            JSONArray jsonPeople = jsonObject.getJSONArray("people");
            JSONObject jsonPerson = jsonPeople.getJSONObject(0);

            for(int i = 0; i < jsonPeople.length(); i++) {
                jsonPerson = jsonPeople.getJSONObject(i);
                if (jsonPerson.getInt("id") == person.getId()) {
                    jsonPeople.remove(i);
                    break;
                }
            }
            jsonPerson.put("name", person.getName());
            jsonPerson.put("companyName", person.getCompanyName());
            jsonPerson.put("dateOfBirth", person.getDateOfBirth());
            jsonPerson.put("rights", person.getRights());
            jsonPerson.put("nfcUid", person.getNfcUid());
            jsonPerson.put("email", person.getEmail());
            jsonPerson.put("superiorId", person.getSuperiorId());
            jsonPerson.put("task", person.getTask());

            jsonPeople.put(jsonPerson);

            saveJSONToFile(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected boolean delete(int idPerson) {
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromFile());

            JSONArray jsonPeople = jsonObject.getJSONArray("people");

            for(int i = 0; i < jsonPeople.length(); i++) {
                JSONObject obj = jsonPeople.getJSONObject(i);
                if (obj.getInt("id") == idPerson)
                    jsonPeople.remove(i);
            }

            saveJSONToFile(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected Collection<Person> selectAll() {
        Collection<Person> people = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromFile());
            JSONArray jsonPeople = jsonObject.getJSONArray("people");
            if (jsonPeople.length() <= 0)
                return people;

            for(int i = 0; i < jsonPeople.length(); i++) {
                Person p = new Person();
                JSONObject person = jsonPeople.getJSONObject(i);
                p.setId(person.getInt("id"));
                p.setName(person.getString("name"));
                p.setCompanyName(person.getString("companyName"));
                p.setRights(person.getString("rights"));
                p.setNfcUid(person.getString("nfcUid"));
                p.setEmail(person.getString("email"));
                p.setTask(person.getString("task"));
                p.setSuperiorId(person.getInt("superiorId"));
                ((ArrayList<Person>) people).add(p);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    protected Collection<Person> selectAllUsers() {
        Collection<Person> people = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromFile());
            JSONArray jsonPeople = jsonObject.getJSONArray("people");
            if (jsonPeople.length() <= 0)
                return people;

            for(int i = 0; i < jsonPeople.length(); i++) {
                JSONObject person = jsonPeople.getJSONObject(i);
                /*if (!person.getString("rights").equals("OWN") &&
                        !person.getString("rights").equals("REN") &&
                        !person.getString("rights").equals("USR"))
                    continue;*/

                Person p = new Person();
                p.setId(person.getInt("id"));
                p.setName(person.getString("name"));
                p.setCompanyName(person.getString("companyName"));
                p.setRights(person.getString("rights"));
                p.setNfcUid(person.getString("nfcUid"));
                p.setEmail(person.getString("email"));
                p.setTask(person.getString("task"));
                p.setSuperiorId(person.getInt("superiorId"));
                ((ArrayList<Person>) people).add(p);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    protected Person selectById(int id) {
        Person person = new Person();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromFile());

            JSONArray jsonPeople = jsonObject.getJSONArray("people");

            for(int i = 0; i < jsonPeople.length(); i++) {
                JSONObject obj = jsonPeople.getJSONObject(i);
                if (obj.getInt("id") == id)
                {
                    person.setId(obj.getInt("id"));
                    person.setName(obj.getString("name"));
                    person.setCompanyName(obj.getString("companyName"));
                    person.setRights(obj.getString("rights"));
                    person.setNfcUid(obj.getString("nfcUid"));
                    person.setEmail(obj.getString("email"));
                    person.setTask(obj.getString("task"));
                    person.setSuperiorId(obj.getInt("superiorId"));
                }
            }

            saveJSONToFile(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return person;
    }
}

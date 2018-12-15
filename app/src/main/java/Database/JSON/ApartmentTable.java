package Database.JSON;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;

import Database.Apartment;
import Database.ConfigurationManager;
import Database.proxy.ApartmentTableProxy;

public class ApartmentTable extends ApartmentTableProxy {

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

    public ApartmentTable() {
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

        } catch (JSONException |IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Collection<Apartment> selectAll() {
        Collection<Apartment> apartments = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromFile());
            JSONArray jsonApartments = jsonObject.getJSONArray("apartments");
            if (jsonApartments.length() <= 0)
                return apartments;

            for(int i = 0; i < jsonApartments.length(); i++) {
                Apartment apartment = new Apartment();
                JSONObject obj = jsonApartments.getJSONObject(i);
                apartment.setId(obj.getInt("id"));
                apartment.setRooms(obj.getInt("rooms"));
                apartment.setFloor(obj.getInt("floor"));
                apartment.setUsableArea(obj.getInt("usableArea"));
                apartment.setTenantId(obj.getInt("tenantId"));
                apartment.setPsswd(obj.getString("psswd"));
                ((ArrayList<Apartment>)apartments).add(apartment);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return apartments;
    }
}

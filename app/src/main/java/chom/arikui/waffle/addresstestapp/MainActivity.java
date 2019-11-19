package chom.arikui.waffle.addresstestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainAct";

    //public static String SERVER_URL = "http://10.89.250.110:8080/ArikuiAddressBook/address_book_servlet";
    public static String SERVER_URL = "http://www.arikui.1strentalserver.info/ArikuiAddressBook/address_book_servlet";

    private ListView listAddress;
    private AddressListAdapter adapter;
    private Button buttonAdd;
    private Button buttonReload;

    private List<AddressData> addressData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listAddress = findViewById(R.id.list_address);
        adapter = new AddressListAdapter(this, R.layout.layout_address_list_item, addressData);
        listAddress.setAdapter(adapter);
        buttonAdd = findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressAddDialog dialog = new AddressAddDialog();
                dialog.show(getSupportFragmentManager(), TAG);
            }
        });
        buttonReload = findViewById(R.id.button_reload);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAddressData();
                JSONObject object = new JSONObject();
                try {
                    object.put("id", UserState.Id);
                    object.put("password", UserState.PASSWORD);
                    AsyncNetworkTaskPost taskPost = new AsyncNetworkTaskPost(MainActivity.this, AsyncNetworkTaskPost.GET_ADDRESS_DATA, null);
                    taskPost.execute(MainActivity.SERVER_URL, object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        LoginDialog dialog = new LoginDialog();
        dialog.show(getSupportFragmentManager(), TAG);
    }

    public void addAddressData (AddressData data) {
        adapter.add(data);
        adapter.notifyDataSetChanged();
    }

    public void clearAddressData () {
        adapter.clear();
        adapter.notifyDataSetChanged();
    }
}

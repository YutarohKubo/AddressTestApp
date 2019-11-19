package chom.arikui.waffle.addresstestapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AddressListAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<AddressData> data;

    public AddressListAdapter (Context context, int resource, List<AddressData> data) {
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AddressData item = (AddressData) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
        }

        TextView textName = convertView.findViewById(R.id.text_name);
        TextView textPhoneNum = convertView.findViewById(R.id.text_phone_num);
        TextView textFaxNum = convertView.findViewById(R.id.text_fax_num);
        TextView textEmail = convertView.findViewById(R.id.text_email);

        textName.setText(item.getName());
        textPhoneNum.setText(item.getPhone_num());
        textFaxNum.setText(item.getFax_num());
        textEmail.setText(item.getMail_address());

        return convertView;
    }

    public void add (AddressData addressData) {
        data.add(addressData);
    }

    public void clear () {
        data.clear();
    }
}

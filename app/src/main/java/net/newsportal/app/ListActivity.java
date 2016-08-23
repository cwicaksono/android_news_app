package net.newsportal.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        try{

            listView = (ListView) findViewById(R.id.list_view);

            ArrayList<NewsItem> arrayOfUsers = new ArrayList<NewsItem>();

            final UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);


            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String index = adapter.getItem(position).id;
                    String title = adapter.getItem(position).title;
                    String description = adapter.getItem(position).description;

                    Intent i = new Intent(ListActivity.this, DetailActivity.class);
                    i.putExtra("title", title);
                    i.putExtra("description", description);
                    i.putExtra("id", index);
                    startActivity(i);
                }
            });

            String[] id = new String[5];
            id[0] = "0";
            id[1] = "1";
            id[2] = "2";
            id[3] = "3";
            id[4] = "4";

            String[] title = new String[5];
            title[0] = "7 Device Android Terbaik";
            title[1] = "Tablet Murah Yang Cocok Untuk Gaming";
            title[2] = "Mengapa Harus Membeli Android";
            title[3] = "10 Mitos Tentang Device Android";
            title[4] = "Sekilas Tentang Game Tahu Bulat";

            String[] description = new String[5];
            description[0] = "Udah pada tahu belum ada  device android untuk...";
            description[1] = "Jajaran tablet ini layak untuk dimiliki sebagai teman bermain game...";
            description[2] = "Masih bingung pilih Android atau iPhone...";
            description[3] = "Ternyata ada 10 mitor paling populer seputar device Android...";
            description[4] = "Siapa yang tidak tahu game yang satu ini...";

            for(int i = 0; i < title.length; i++){
                System.out.println(title[i]);
                adapter.add(new NewsItem(id[i], title[i], description[i]));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // adapter to collect from data container and show it on the list
    private class UsersAdapter extends ArrayAdapter<NewsItem> {
        public UsersAdapter(Context context, ArrayList<NewsItem> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            NewsItem user = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView labelTitle = (TextView) convertView.findViewById(R.id.label_title);
            TextView labelDescription = (TextView) convertView.findViewById(R.id.label_description);

            labelTitle.setText(user.title);
            labelDescription.setText(user.description);

            return convertView;
        }

    }

    // Notification for create data container
    private class NewsItem {
        public String id;
        public String title;
        public String description;

        public NewsItem(String id, String name, String description) {
            this.id = id;
            this.title = name;
            this.description = description;
        }
    }
}

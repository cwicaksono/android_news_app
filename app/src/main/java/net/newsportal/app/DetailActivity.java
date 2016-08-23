package net.newsportal.app;

import android.app.ProgressDialog;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {

    TextView labelTitle;
    TextView labelDescription;
    ImageView image;

    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        labelTitle = (TextView)findViewById(R.id.label_title);
        labelDescription = (TextView)findViewById(R.id.label_description);
        image = (ImageView)findViewById(R.id.images);

        labelTitle.setText(getIntent().getStringExtra("title"));
        labelDescription.setText(getIntent().getStringExtra("description"));

        reloadData();
    }

    public void reloadData(){

        progress = ProgressDialog.show(this, "", "Please wait", true);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://dsgnkd.id/demo/api/api.php?id=" + getIntent().getStringExtra("id").toString();
        System.out.println(url);
        client.post(url,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        try{
                            String str = new String(response, "UTF-8");

                            JSONObject jObject = new JSONObject(str);
//                            String status = jObject.getString("status");
//                            String errorMessage = jObject.getString("error_message");
//                            JSONArray data = jObject.getJSONArray("data");

                            labelTitle.setText(jObject.getString("title"));
                            labelDescription.setText(jObject.getString("description"));
                            Picasso.with(getApplicationContext())
                                    .load(jObject.getString("images"))
                                    .into(image);

                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }

                        progress.dismiss();

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        try{
                            String str = new String(errorResponse, "UTF-8");
                            System.out.println(str);
                        }catch(Exception err){
                            System.out.println(err.getMessage());
                        }

                        progress.dismiss();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });

    }
}

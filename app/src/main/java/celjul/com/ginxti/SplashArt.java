package celjul.com.ginxti;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashArt extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private TextView evento;
    private ConstraintLayout splashArtContenedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_art);
        splashArtContenedor = findViewById(R.id.SplashArtContenedor);
        splashArtContenedor.setBackgroundColor(0xFFAAFFAA);
        evento = findViewById(R.id.text);

        mRequestQueue = Volley.newRequestQueue(this);
        fetchJsonResponse();
    }

    private void fetchJsonResponse() {
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://192.168.1.66:8080/ginxti/RESTgetEventos/", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("eventos");
                            int a = 0;
                            String result ="";
                            while(a<array.length()){
                                JSONObject resultado = array.getJSONObject(a);
                                result = result + resultado.get("descripcion")+ " ";
                                a++;
                            }
                            evento.setText(result);
                            splashArtContenedor.setBackgroundColor(0xFF00FF00);
                            Toast.makeText(SplashArt.this, result, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

		/* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }
}

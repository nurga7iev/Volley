package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final String url = "https://jsonplaceholder.typicode.com/posts";

    Button button;
    ListView listView;
    List<String> titles; // Список для заголовков
    List<Post> posts;    // Список объектов Post

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);
        titles = new ArrayList<>();
        posts = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAndDisplayData();
            }
        });

        // Обработчик нажатий на элемент списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Показать подробности о выбранном элементе
                Post selectedPost = posts.get(position);
                String details = "ID: " + selectedPost.getId() +
                        "\nTitle: " + selectedPost.getTitle() +
                        "\nBody: " + selectedPost.getBody();
                Toast.makeText(MainActivity.this, details, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchAndDisplayData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            titles.clear();
                            posts.clear();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject postJson = response.getJSONObject(i);

                                // Создаём объект Post
                                Post post = new Post(
                                        postJson.getInt("id"),
                                        postJson.getString("title"),
                                        postJson.getString("body")
                                );

                                posts.add(post); // Сохраняем объект в список
                                titles.add(post.getTitle()); // Добавляем заголовок в список titles
                            }

                            // Обновляем адаптер
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    MainActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    titles
                            );
                            listView.setAdapter(adapter);

                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing JSON", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching data", error);
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}

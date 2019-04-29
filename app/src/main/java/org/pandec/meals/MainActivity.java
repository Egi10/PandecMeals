package org.pandec.meals;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.pandec.meals.adapter.MealsAdapter;
import org.pandec.meals.model.Response;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidNetworking.initialize(getApplicationContext());
        swipeRefreshLayout = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.recycler_view);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        AndroidNetworking.get("https://www.themealdb.com/api/json/v1/1/categories.php")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Response.class, new ParsedRequestListener<Response>() {
                    @Override
                    public void onResponse(Response response) {
                        MealsAdapter mealsAdapter = new MealsAdapter(getBaseContext());
                        mealsAdapter.setList(response.getCategories());
                        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
                        recyclerView.setAdapter(mealsAdapter);

                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("Message", anError.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }
}

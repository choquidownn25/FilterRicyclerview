package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.MainActivity;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapter.RecyclerAdapterPost;
import co.com.ceiba.mobile.pruebadeingreso.adapter.RecyclerAdapterUsuario;
import co.com.ceiba.mobile.pruebadeingreso.api.IService;
import co.com.ceiba.mobile.pruebadeingreso.api.RetrofitClientInstance;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends Activity {

    //region Atributo
    private RecyclerView recyclerView;
    private RecyclerAdapterPost recyclerAdapterPost;
    private List<Post> postList;
    private TextInputEditText textInputEditText;
    private int id;
    private TextView txtname;
    private TextView txtphone;
    private TextView txtcontentEmail;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        txtname = (TextView)findViewById(R.id.name);
        txtphone = (TextView)findViewById(R.id.phone);
        txtcontentEmail=(TextView)findViewById(R.id.email);
        textInputEditText = (TextInputEditText)findViewById(R.id.editTextSearch);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPostsResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapterPost = new RecyclerAdapterPost();
        postList = new ArrayList<>();//Lista name

        if(bundle!=null)
        {
            String idDato =(String) bundle.get("id");
            String name =(String) bundle.get("name");
            String phone =(String) bundle.get("phone");
            String email =(String) bundle.get("email");
            id = Integer.parseInt(idDato);
            txtname.setText(name);
            txtphone.setText(phone);
            txtcontentEmail.setText(email);
        }

        IService apiService = RetrofitClientInstance.getRetrofitInstance().create(IService.class);
        Call<List<Post>> call = apiService.getAllPost();
        //Llama servicio

        String idUsuario = String.valueOf(id);
        Call<List<Post>> idCall = apiService.getbyIdUsuario(idUsuario);
        idCall.enqueue(new Callback<List<Post>>(){

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                postList = response.body();
                Log.d("TAG", "Response = " + postList);
                recyclerAdapterPost.setPostList(getApplicationContext(), postList);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("TAG","Response = "+t.toString());
                Toast.makeText(PostActivity.this, "Algo salió mal ... Inténtelo más tarde!", Toast.LENGTH_SHORT).show();

            }
        });
        //Llama Servicio
        //idCall.enqueue(new Callback<List<Post>>() {})

        // llenado de reciclerview
        recyclerView.setAdapter(recyclerAdapterPost);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}

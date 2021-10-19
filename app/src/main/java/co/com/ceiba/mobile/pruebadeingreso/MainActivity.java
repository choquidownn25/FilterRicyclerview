package co.com.ceiba.mobile.pruebadeingreso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.adapter.RecyclerAdapterUsuario;
import co.com.ceiba.mobile.pruebadeingreso.api.IService;
import co.com.ceiba.mobile.pruebadeingreso.api.RetrofitClientInstance;
import co.com.ceiba.mobile.pruebadeingreso.model.Usuario;
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    //region Atributos
    private RecyclerView recyclerView;
    private RecyclerAdapterUsuario recyclerAdapterUsuario;
    private List<Usuario> usuarioLists;
    private TextInputEditText textInputEditText;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textInputEditText = (TextInputEditText)findViewById(R.id.editTextSearch);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapterUsuario = new RecyclerAdapterUsuario();
        //Lista
        usuarioLists = new ArrayList<>();
        IService apiService = RetrofitClientInstance.getRetrofitInstance().create(IService.class);
        Call<List<Usuario>> call = apiService.getAllUsuario();
        call.enqueue(new Callback<List<Usuario>>() {  //Llama servicio
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                usuarioLists = response.body();
                Log.d("TAG", "Response = " + usuarioLists);
                recyclerAdapterUsuario.setUsuarioList(getApplicationContext(), usuarioLists);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("TAG","Response = "+t.toString());
                Toast.makeText(MainActivity.this, "Algo salió mal ... Inténtelo más tarde!", Toast.LENGTH_SHORT).show();

            }
        });
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        // llenado de reciclerview
        recyclerView.setAdapter(recyclerAdapterUsuario);

    }


    private void filter(String text) {
        ArrayList<Usuario> filteredList = new ArrayList<>();
        for (Usuario item : usuarioLists) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterUsuario.filterLists(filteredList);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "Modulo por construir : ", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
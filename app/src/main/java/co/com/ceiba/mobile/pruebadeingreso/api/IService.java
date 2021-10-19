package co.com.ceiba.mobile.pruebadeingreso.api;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IService {
    @GET("/users")
    Call<List<Usuario>> getAllUsuario();

    @POST("/users")
    Call<List<Usuario>> addUsuario();

    @GET("/posts")
    Call<List<Post>> getAllPost();

    @GET("/posts?")
    Call<List<Post>> getbyIdUsuario(@Query("page") String id);
}

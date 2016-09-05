package com.example.adam.tunisia.Model.Rest.Correct;

        import com.example.adam.tunisia.Model.Entities.Actualite;
        import com.example.adam.tunisia.Model.Entities.Feedback;
        import com.example.adam.tunisia.Model.Entities.Ligne;
        import com.example.adam.tunisia.Model.Entities.Perturbation;
        import com.example.adam.tunisia.Model.Entities.Societe;
        import com.example.adam.tunisia.Model.Entities.Station;
        import com.example.adam.tunisia.Model.Entities.Station_Ligne;
        import com.example.adam.tunisia.Model.Entities.Vehicule;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.Path;

public interface TunisiaApi {
    @GET("rest/actualites/crud")
    Call<List<Actualite>> getActualites();

    @POST("rest/feedbacks/crud/{IDENTIFICATEUR}")
    Call<Feedback> createUser(@Path("IDENTIFICATEUR") String IDENTIFICATEUR, @Body Feedback feedback);

    @GET("rest/lignes/crud")
    Call<List<Ligne>> getLignes();

    @GET("rest/perturbations/crud")
    Call<List<Perturbation>> getPerturbations();

    @GET("rest/societes/crud")
    Call<List<Societe>> getSocietes();

    @GET("rest/societes/crud/{IDENTIFICATEUR}")
    Call<Societe> getSociete(@Path("IDENTIFICATEUR") String IDENTIFICATEUR);

    @GET("rest/stations/crud")
    Call<List<Station>> getStations();

    @GET("rest/station_ligne/crud")
    Call<List<Station_Ligne>> getStation_Lignes_Horaires();

    @GET("rest/vehicules/crud")
    Call<List<Vehicule>> getVehicules();

}
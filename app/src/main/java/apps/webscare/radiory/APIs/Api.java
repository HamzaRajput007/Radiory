package apps.webscare.radiory.APIs;

import java.util.List;

import apps.webscare.radiory.Models.GetCountriesModel;
import apps.webscare.radiory.Models.RadioModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("countries")
    Call<List<GetCountriesModel>> getCountries();

    @GET("posts")
    Call<List<RadioModel>> getRadios(@Query("cat") int countryId);

    @GET("search/")
    Call<List<RadioModel>> searchRadio(@Query("s") String searchString);

}

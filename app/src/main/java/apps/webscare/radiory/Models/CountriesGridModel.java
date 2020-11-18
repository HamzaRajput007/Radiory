package apps.webscare.radiory.Models;

public class CountriesGridModel {
    String countryName ;
    int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public CountriesGridModel( int imageId  ,String countryName ) {
        this.imageId = imageId;
        this.countryName = countryName;
    }
}

package apps.webscare.radiory.Models;

public class CountriesModel {
    int imageId;
    String channelName , channeNumber;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChanneNumber() {
        return channeNumber;
    }

    public void setChanneNumber(String channeNumber) {
        this.channeNumber = channeNumber;
    }

    public CountriesModel(int imageId, String channelName, String channeNumber) {
        this.imageId = imageId;
        this.channelName = channelName;
        this.channeNumber = channeNumber;
    }
}

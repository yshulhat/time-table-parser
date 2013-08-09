package by.sands.vitebsktransport.stop;

public class Stop implements Comparable<Stop> {
    private String name;
    private String lat;
    private String lang;

    public Stop(String name, String lat, String lang) {
        this.name = name;
        this.lat = lat;
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public int compareTo(Stop s2) {
        return name.compareTo(s2.name);
    }
}

package io.github.floriangubler.quaky.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "magnitude",
        "type",
        "title",
        "date",
        "time",
        "updated",
        "url",
        "detailUrl",
        "felt",
        "cdi",
        "mmi",
        "alert",
        "status",
        "tsunami",
        "sig",
        "net",
        "code",
        "ids",
        "sources",
        "types",
        "nst",
        "dmin",
        "rms",
        "gap",
        "magType",
        "geometryType",
        "depth",
        "latitude",
        "longitude",
        "place",
        "distanceKM",
        "placeOnly",
        "location",
        "continent",
        "country",
        "subnational",
        "city",
        "locality",
        "postcode",
        "what3words",
        "timezone",
        "locationDetails"
})

public class Earthquake {

    @JsonProperty("id")
    private String id;
    @JsonProperty("magnitude")
    private String magnitude;
    @JsonProperty("type")
    private String type;
    @JsonProperty("title")
    private String title;
    @JsonProperty("date")
    private LocalDateTime date;
    @JsonProperty("time")
    private String time;
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("url")
    private String url;
    @JsonProperty("detailUrl")
    private String detailUrl;
    @JsonProperty("felt")
    private String felt;
    @JsonProperty("cdi")
    private String cdi;
    @JsonProperty("mmi")
    private String mmi;
    @JsonProperty("alert")
    private String alert;
    @JsonProperty("status")
    private String status;
    @JsonProperty("tsunami")
    private String tsunami;
    @JsonProperty("sig")
    private String sig;
    @JsonProperty("net")
    private String net;
    @JsonProperty("code")
    private String code;
    @JsonProperty("ids")
    private String ids;
    @JsonProperty("sources")
    private String sources;
    @JsonProperty("types")
    private String types;
    @JsonProperty("nst")
    private String nst;
    @JsonProperty("dmin")
    private String dmin;
    @JsonProperty("rms")
    private String rms;
    @JsonProperty("gap")
    private String gap;
    @JsonProperty("magType")
    private String magType;
    @JsonProperty("geometryType")
    private String geometryType;
    @JsonProperty("depth")
    private String depth;
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("place")
    private String place;
    @JsonProperty("distanceKM")
    private String distanceKM;
    @JsonProperty("placeOnly")
    private String placeOnly;
    @JsonProperty("location")
    private String location;
    @JsonProperty("continent")
    private String continent;
    @JsonProperty("country")
    private String country;
    @JsonProperty("subnational")
    private String subnational;
    @JsonProperty("city")
    private String city;
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("postcode")
    private String postcode;
    @JsonProperty("what3words")
    private String what3words;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("locationDetails")
    private List<LocationDetail> locationDetails;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("magnitude")
    public String getMagnitude() {
        return magnitude;
    }

    @JsonProperty("magnitude")
    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("date")
    public LocalDateTime getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("updated")
    public String getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("detailUrl")
    public String getDetailUrl() {
        return detailUrl;
    }

    @JsonProperty("detailUrl")
    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @JsonProperty("felt")
    public String getFelt() {
        return felt;
    }

    @JsonProperty("felt")
    public void setFelt(String felt) {
        this.felt = felt;
    }

    @JsonProperty("cdi")
    public String getCdi() {
        return cdi;
    }

    @JsonProperty("cdi")
    public void setCdi(String cdi) {
        this.cdi = cdi;
    }

    @JsonProperty("mmi")
    public String getMmi() {
        return mmi;
    }

    @JsonProperty("mmi")
    public void setMmi(String mmi) {
        this.mmi = mmi;
    }

    @JsonProperty("alert")
    public String getAlert() {
        return alert;
    }

    @JsonProperty("alert")
    public void setAlert(String alert) {
        this.alert = alert;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("tsunami")
    public String getTsunami() {
        return tsunami;
    }

    @JsonProperty("tsunami")
    public void setTsunami(String tsunami) {
        this.tsunami = tsunami;
    }

    @JsonProperty("sig")
    public String getSig() {
        return sig;
    }

    @JsonProperty("sig")
    public void setSig(String sig) {
        this.sig = sig;
    }

    @JsonProperty("net")
    public String getNet() {
        return net;
    }

    @JsonProperty("net")
    public void setNet(String net) {
        this.net = net;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("ids")
    public String getIds() {
        return ids;
    }

    @JsonProperty("ids")
    public void setIds(String ids) {
        this.ids = ids;
    }

    @JsonProperty("sources")
    public String getSources() {
        return sources;
    }

    @JsonProperty("sources")
    public void setSources(String sources) {
        this.sources = sources;
    }

    @JsonProperty("types")
    public String getTypes() {
        return types;
    }

    @JsonProperty("types")
    public void setTypes(String types) {
        this.types = types;
    }

    @JsonProperty("nst")
    public String getNst() {
        return nst;
    }

    @JsonProperty("nst")
    public void setNst(String nst) {
        this.nst = nst;
    }

    @JsonProperty("dmin")
    public String getDmin() {
        return dmin;
    }

    @JsonProperty("dmin")
    public void setDmin(String dmin) {
        this.dmin = dmin;
    }

    @JsonProperty("rms")
    public String getRms() {
        return rms;
    }

    @JsonProperty("rms")
    public void setRms(String rms) {
        this.rms = rms;
    }

    @JsonProperty("gap")
    public String getGap() {
        return gap;
    }

    @JsonProperty("gap")
    public void setGap(String gap) {
        this.gap = gap;
    }

    @JsonProperty("magType")
    public String getMagType() {
        return magType;
    }

    @JsonProperty("magType")
    public void setMagType(String magType) {
        this.magType = magType;
    }

    @JsonProperty("geometryType")
    public String getGeometryType() {
        return geometryType;
    }

    @JsonProperty("geometryType")
    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    @JsonProperty("depth")
    public String getDepth() {
        return depth;
    }

    @JsonProperty("depth")
    public void setDepth(String depth) {
        this.depth = depth;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("place")
    public String getPlace() {
        return place;
    }

    @JsonProperty("place")
    public void setPlace(String place) {
        this.place = place;
    }

    @JsonProperty("distanceKM")
    public String getDistanceKM() {
        return distanceKM;
    }

    @JsonProperty("distanceKM")
    public void setDistanceKM(String distanceKM) {
        this.distanceKM = distanceKM;
    }

    @JsonProperty("placeOnly")
    public String getPlaceOnly() {
        return placeOnly;
    }

    @JsonProperty("placeOnly")
    public void setPlaceOnly(String placeOnly) {
        this.placeOnly = placeOnly;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("continent")
    public String getContinent() {
        return continent;
    }

    @JsonProperty("continent")
    public void setContinent(String continent) {
        this.continent = continent;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("subnational")
    public String getSubnational() {
        return subnational;
    }

    @JsonProperty("subnational")
    public void setSubnational(String subnational) {
        this.subnational = subnational;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("locality")
    public String getLocality() {
        return locality;
    }

    @JsonProperty("locality")
    public void setLocality(String locality) {
        this.locality = locality;
    }

    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    @JsonProperty("postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @JsonProperty("what3words")
    public String getWhat3words() {
        return what3words;
    }

    @JsonProperty("what3words")
    public void setWhat3words(String what3words) {
        this.what3words = what3words;
    }

    @JsonProperty("timezone")
    public String getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty("locationDetails")
    public List<LocationDetail> getLocationDetails() {
        return locationDetails;
    }

    @JsonProperty("locationDetails")
    public void setLocationDetails(List<LocationDetail> locationDetails) {
        this.locationDetails = locationDetails;
    }

}

package io.github.floriangubler.quaky.entity;
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
        "httpStatus",
        "noun",
        "verb",
        "errorCode",
        "errors",
        "friendlyError",
        "result",
        "count",
        "data"
})
public class EveryEarthquakeResponse {

    @JsonProperty("httpStatus")
    private Integer httpStatus;
    @JsonProperty("noun")
    private String noun;
    @JsonProperty("verb")
    private String verb;
    @JsonProperty("errorCode")
    private String errorCode;
    @JsonProperty("errors")
    private List<Object> errors;
    @JsonProperty("friendlyError")
    private String friendlyError;
    @JsonProperty("result")
    private String result;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("data")
    private List<Earthquake> data;

    @JsonProperty("httpStatus")
    public Integer getHttpStatus() {
        return httpStatus;
    }

    @JsonProperty("httpStatus")
    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    @JsonProperty("noun")
    public String getNoun() {
        return noun;
    }

    @JsonProperty("noun")
    public void setNoun(String noun) {
        this.noun = noun;
    }

    @JsonProperty("verb")
    public String getVerb() {
        return verb;
    }

    @JsonProperty("verb")
    public void setVerb(String verb) {
        this.verb = verb;
    }

    @JsonProperty("errorCode")
    public String getErrorCode() {
        return errorCode;
    }

    @JsonProperty("errorCode")
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("errors")
    public List<Object> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    @JsonProperty("friendlyError")
    public String getFriendlyError() {
        return friendlyError;
    }

    @JsonProperty("friendlyError")
    public void setFriendlyError(String friendlyError) {
        this.friendlyError = friendlyError;
    }

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
    }

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("data")
    public List<Earthquake> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Earthquake> data) {
        this.data = data;
    }

}

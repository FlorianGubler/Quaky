package io.github.floriangubler.quaky.entity;

import java.util.LinkedHashMap;
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
        "wikidataId",
        "name",
        "description",
        "geonameId",
        "adminLevel"
})
public class LocationDetail {

    @JsonProperty("id")
    private String id;
    @JsonProperty("wikidataId")
    private String wikidataId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("geonameId")
    private String geonameId;
    @JsonProperty("adminLevel")
    private String adminLevel;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("wikidataId")
    public String getWikidataId() {
        return wikidataId;
    }

    @JsonProperty("wikidataId")
    public void setWikidataId(String wikidataId) {
        this.wikidataId = wikidataId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("geonameId")
    public String getGeonameId() {
        return geonameId;
    }

    @JsonProperty("geonameId")
    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
    }

    @JsonProperty("adminLevel")
    public String getAdminLevel() {
        return adminLevel;
    }

    @JsonProperty("adminLevel")
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

}

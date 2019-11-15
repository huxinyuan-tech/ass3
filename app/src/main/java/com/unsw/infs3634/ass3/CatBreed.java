package com.unsw.infs3634.ass3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CatBreed implements Serializable  {

    private String id;
    private String name;
    private Weight weight;
    private String description;
    private String temperament;
    private String origin;
    private String lifeSpan;
    private String wikiLink;
    private String dogFriendlinessLevel;

    public CatBreed(@JsonProperty("id") String id,
                    @JsonProperty("name") String name,
                    @JsonProperty("weight") Weight weight,
                    @JsonProperty("description") String description,
                    @JsonProperty("temperament") String temperament,
                    @JsonProperty("origin") String origin,
                    @JsonProperty("life_span") String lifeSpan,
                    @JsonProperty("wikipedia_url") String wikiLink,
                    @JsonProperty("dog_friendly") String dogFriendlinessLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.temperament = temperament;
        this.origin = origin;
        this.lifeSpan = lifeSpan;
        this.wikiLink = wikiLink;
        this.dogFriendlinessLevel = dogFriendlinessLevel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Weight getWeight() {
        return weight;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getOrigin() {
        return origin;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public String getDogFriendlinessLevel() {
        return dogFriendlinessLevel;
    }
}

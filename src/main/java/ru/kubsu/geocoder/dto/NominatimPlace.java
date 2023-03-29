package ru.kubsu.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Адрес.
 *
 * @param latitude широта
 * @param longitude долгота
 * @param displayName имя
 * @param type тип
 */

public record NominatimPlace (

    @JsonProperty("lat")
    Double latitude,
    @JsonProperty("lon")
    Double longitude,
    @JsonProperty("display_name")
    String displayName,
    @JsonProperty("type")
    String type) {
    public NominatimPlace() {
        this(45.02036085, 39.03099994504268, "Кубанский государственный университет", "");
    }
}

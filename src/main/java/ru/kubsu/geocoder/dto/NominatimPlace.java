package ru.kubsu.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Адрес.
 *
 * @param latitude широта
 * @param longitude долгота
 * @param displayName имя
 * @param type тип
 */

public record NominatimPlace(

    @JsonProperty("lat")
    Double latitude,
    @JsonProperty("lon")
    Double longitude,
    @JsonProperty("display_name")
    String displayName,
    @JsonProperty("type")
    String type) {
    public NominatimPlace() {
        this(45.020_360_85, 39.030_999_945_042_68, "Кубанский государственный университет", "");
    }
}

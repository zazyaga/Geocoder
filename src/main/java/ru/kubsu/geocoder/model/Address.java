package ru.kubsu.geocoder.model;

import ru.kubsu.geocoder.dto.NominatimPlace;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Anastasia Zozulya
 */

@Entity
@SuppressWarnings("PMD.AvoidFieldName<atchingTypeName")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String address;
  private Double latitude;
  private Double longitude;
  private String query;


  public Integer getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public String getQuery() { return query; }
  public void setQuery(String query) { this.query = query; }

  public void setId(final Integer id) {
    this.id = id;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public void setLatitude(final Double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(final Double longitude) {
    this.longitude = longitude;
  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(id, address1.id)
            && Objects.equals(address, address1.address)
            && Objects.equals(latitude, address1.latitude)
            && Objects.equals(longitude, address1.longitude)
            && Objects.equals(query, address1.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, latitude, longitude, query);
    }

    @Override
  public String toString() {
    return "Address[" +
        "id=" + id + ", " +
        "address=" + address + ", " +
        "latitude=" + latitude + ", " +
        "longitude=" + longitude + ", " +
        "query=" + query +  ']';
  }

  public static Address of(final NominatimPlace place, final String query) {
    Address result = new Address();
    result.setAddress(place.displayName());
    result.setLatitude(place.latitude());
    result.setLongitude(place.longitude());
    result.setQuery(query);
    return result;
  }
}

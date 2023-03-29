package ru.kubsu.geocoder.model;

import ru.kubsu.geocoder.dto.NominatimPlace;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@SuppressWarnings("PMD.AvoidFieldName<atchingTypeName")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String address;
  private Double latitude;
  private Double longitude;


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
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Address) obj;  //final Address вместо var
    return Objects.equals(this.id, that.id) &&
      Objects.equals(this.address, that.address) &&
      Objects.equals(this.latitude, that.latitude) &&
      Objects.equals(this.longitude, that.longitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, latitude, longitude);
  }

  @Override
  public String toString() {
    return "Address[" +
      "id=" + id + ", " +
      "address=" + address + ", " +
      "latitude=" + latitude + ", " +
      "longitude=" + longitude + ']';
  }

  public static Address of(final NominatimPlace place) {
    Address result = new Address();
    result.setAddress(place.getDisplayName());
    result.setLatitude(place.getLatitude());
    result.setLongitude(place.getLongitude());
    return result;
  }
}

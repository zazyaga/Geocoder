package ru.kubsu.geocoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kubsu.geocoder.model.Address;
import ru.kubsu.geocoder.service.AddressService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Anastasia Zozulya
 */
@RestController
@RequestMapping("geocoder")
public class GeocoderController {
  private final AddressService addressService;

  @Autowired
  public GeocoderController(final AddressService addressService) {
    this.addressService = addressService;
  }

  // curl "https://nominatim.openstreetmap.org/search?q=кубгу&format=json"
  // curl "http://localhost:8080/tests/search"
  @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Address> search(final @RequestParam("q") String query) {
    return addressService.search(query)
            .map(place -> ResponseEntity.status(HttpStatus.OK).body(place))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  //public NominatimPlace search() {return nominatimClient.search("кубгу", "json").get(0);}

  // curl "https://nominatim.openstreetmap.org/reverse?lat=45.02036085&lon=39.03099994504268&format=json"
  // curl "http://localhost:8080/tests/reverse"
  @GetMapping(value = "/reverse", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Address> reverse(final @RequestParam("lat") String latitude,
                                         final @RequestParam("lon") String longitude) {
    return addressService.reverse(latitude, longitude)
            .map(place -> ResponseEntity.status(HttpStatus.OK).body(place))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  //public NominatimPlace reverse() {return nominatimClient.reverse("45.016739", "38.963627", "json");}
}

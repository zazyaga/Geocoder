package ru.kubsu.geocoder.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.kubsu.geocoder.dto.RestApiError;
import ru.kubsu.geocoder.repository.TestRepository;
import ru.kubsu.geocoder.util.TestUtil;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

    @LocalServerPort
    Integer port;

    @Autowired
    TestRepository testRepository;

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @BeforeAll
    static void beforeAll() {
        System.out.println("BEFORE ALL");
    }

    @BeforeEach
    void setUp() {
        System.out.println("SET UP");
    }

    //for build 1
    @Test
    void integrationTest() {
        //System.out.println("TEST 1 ");

        ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate.
                getForEntity("http://localhost:" + port + "/tests/1?name=test",
                        ru.kubsu.geocoder.model.Test.class);

        final ru.kubsu.geocoder.model.Test body = response.getBody();
        assertEquals(1, body.getId());
        assertEquals("test", body.getName());
        assertEquals(null, body.getDone());
        assertEquals(null, body.getMark());

        //assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);
        //System.out.println(response.getBody());
    }

  //for build 2
    @Test
    void integrationTestWhenNameIsNull() {
        ResponseEntity<Map<String, String>> response = testRestTemplate
                .exchange("http://localhost:" + port + "/tests/1",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, String>>() {});

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final Map<String, String> body = response.getBody();
        assertEquals("400", body.get("status"));
        assertEquals("Bad Request", body.get("error"));
        assertEquals("/tests/1", body.get("path"));

        //final String body = response.getBody();

        //System.out.println(body);
    }

  //for build 3
    @Test
    void integrationTestWhenIdIsString() {
      ResponseEntity<RestApiError> response = testRestTemplate
              .exchange("http://localhost:" + port + "/tests/abc?name=test",
                      HttpMethod.GET,
                      null,
                      RestApiError.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final RestApiError body = response.getBody();
        assertEquals(400, body.getStatus());
        assertEquals("Bad Request", body.getError());
        assertEquals("/tests/abc", body.getPath());
    }

  //example test for working with repository
  @Test
  void ExampleTestWithRepository() {
    ResponseEntity<Void> response = testRestTemplate.
      getForEntity(
        "http://localhost:" + port + "/tests/save?name=test",
        Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  //for save 1  .......just like previous example, is it ok?
  @Test
  void saveTest() {
    ResponseEntity<Void> response = testRestTemplate.
      getForEntity(
        "http://localhost:" + port + "/tests/save?name=666",
        Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  //for save 2
  @Test
  void saveTestWhenNameIsNull() {
    ResponseEntity<Map<String, String>> response = testRestTemplate
      .exchange("http://localhost:" + port + "/tests/save",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Map<String, String>>() {});

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final Map<String, String> body = response.getBody();
    assertEquals("400", body.get("status"));
    assertEquals("Bad Request", body.get("error"));
    assertEquals("/tests/save", body.get("path"));
  }


  //for load 1
  @Test
  void loadTest() {
    ru.kubsu.geocoder.model.Test test = new ru.kubsu.geocoder.model.Test();
    test.setName("megaololo");
    testRepository.save(test);
    Integer id = test.getId();

    ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate.
      getForEntity("http://localhost:" + port + "/tests/load/megaololo",
        ru.kubsu.geocoder.model.Test.class);

    final ru.kubsu.geocoder.model.Test body = response.getBody();
    assertEquals(id, body.getId());
    assertEquals("megaololo", body.getName());
    assertEquals(null, body.getDone());
    assertEquals(null, body.getMark());
  }


  //for load 2
  @Test
  void loadTestWhenNameDoesNotExist() {

    ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate.
      getForEntity(
        "http://localhost:" + port + "/tests/load/NOTmegaololo",
        ru.kubsu.geocoder.model.Test.class);

    assertEquals(null, response.getBody());


//    ResponseEntity<RestApiError> response = testRestTemplate
//      .exchange(
//        "http://localhost:" + port + "/tests/load/NOTmegaololo",
//        HttpMethod.GET,
//        null,
//        RestApiError.class);
//
//    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//
//    final RestApiError body = response.getBody();
//    assertEquals(400, body.getStatus());
//    assertEquals("Bad Request", body.getError());
//    assertEquals("/tests/load/NOTmegaololo", body.getPath());
  }

  //for load 3
  @Test
  void loadTestWhenNameIsNull() {
    ResponseEntity<Map<String, String>> response = testRestTemplate
      .exchange(
        "http://localhost:" + port + "/tests/load/",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Map<String, String>>() {});

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final Map<String, String> body = response.getBody();
    assertEquals("400", body.get("status"));
    assertEquals("Bad Request", body.get("error"));
    assertEquals("/tests/load/", body.get("path"));
  }

  @AfterEach
  void tearDown() {
    System.out.println("TEAR DOWN");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("AFTER ALL");
  }

}

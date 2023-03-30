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
import ru.kubsu.geocoder.model.Mark;
import ru.kubsu.geocoder.repository.TestRepository;
import ru.kubsu.geocoder.util.TestUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TestRepository testRepository;
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

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
                getForEntity("http://localhost:" + this.port + "/tests/check/1?name=test",
                        ru.kubsu.geocoder.model.Test.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final ru.kubsu.geocoder.model.Test body = response.getBody();
        if (body == null) {
            fail("Body is null");
        }
        assertEquals(1, body.getId());
        assertEquals("test", body.getName());
        assertNull(body.getDone());
        assertNull(body.getMark());

        //assertEquals("{\"id\":1,\"name\":\"test\",\"done\":null,\"mark\":null}", body);
        //System.out.println(response.getBody());
    }

  //for build 2
    @Test
    void integrationTestWhenNameIsNull() {
        ResponseEntity<HashMap<String, String>> response = testRestTemplate
                .exchange("http://localhost:" + this.port + "/tests/check/1",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<HashMap<String, String>>() {});

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final HashMap<String, String> body = response.getBody();
        if(body == null) {
            fail("Body is null");
        }
        assertEquals("/tests/check/1", body.get("path"));
        body.remove("path");

        assertEquals("Bad Request", body.get("error"));
        body.remove("error");

        assertEquals("400", body.get("status"));
        body.remove("status");

        body.remove("timestamp");
        //assertEquals(true, body.isEmpty());
        assertTrue(body.isEmpty());

        //final String body = response.getBody();

        //System.out.println(body);
    }

  //for build 3
    @Test
    void integrationTestWhenIdIsString() {
      ResponseEntity<RestApiError> response = testRestTemplate
              .exchange("http://localhost:" + this.port + "/tests/check/abc?name=test",
                      HttpMethod.GET,
                      null,
                      RestApiError.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        final RestApiError body = response.getBody();
        if(body == null) {
            fail("Body is null");
        }

        assertEquals("/tests/check/test", body.path());
        assertEquals("Bad Request", body.error());
        assertEquals(400, body.status());
    }

  //example test for working with repository
  @Test
  void ExampleTestWithRepository() {
    ResponseEntity<Void> response = testRestTemplate.
      getForEntity(
        "http://localhost:" + port + "/tests/saveTest?name=test",
        Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  //for save 1  .......just like previous example, is it ok?
  @Test
  void saveTest() {
    ResponseEntity<Void> response = testRestTemplate.
      getForEntity(
        "http://localhost:" + port + "/tests/saveTest?name=666",
        Void.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  //for save 2
  @Test
  void saveTestWhenNameIsNull() {
    ResponseEntity<Map<String, String>> response = testRestTemplate
      .exchange("http://localhost:" + this.port + "/tests/saveTest",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<Map<String, String>>() {});

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final Map<String, String> body = response.getBody();
    assertEquals("400", body.get("status"));
    assertEquals("Bad Request", body.get("error"));
    assertEquals("/tests/saveTest", body.get("path"));
  }


  //for load 1
  @Test
  void loadTest() {
      ru.kubsu.geocoder.model.Test test =  new ru.kubsu.geocoder.model.Test();
      String nameTest = "newtest111";
      test.setName(nameTest);
      test.setDone(true);
      test.setMark(Mark.B);
      testRepository.save(test);

      ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate
          .exchange("http://localhost:"+this.port+"/tests/load/"+nameTest,
              HttpMethod.GET, null, ru.kubsu.geocoder.model.Test.class);

      assertEquals(HttpStatus.OK, response.getStatusCode());

      final ru.kubsu.geocoder.model.Test body = response.getBody();
      if(body == null) {
          fail("Body is null");
      }

      assertEquals(nameTest, body.getName());
      assertTrue(body.getDone());
      assertEquals(Mark.B, body.getMark());
  }


  //for load 2
  @Test
  void loadTestWhenNameDoesNotExist() {

    ResponseEntity<ru.kubsu.geocoder.model.Test> response = testRestTemplate.
      getForEntity(
        "http://localhost:" + this.port + "/tests/load/NOTmegaololo",
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
        "http://localhost:" + this.port + "/tests/load/",
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

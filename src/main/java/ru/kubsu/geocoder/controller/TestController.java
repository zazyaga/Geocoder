package ru.kubsu.geocoder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kubsu.geocoder.model.Test;
import ru.kubsu.geocoder.service.TestService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("tests")
public class TestController {

    private TestService service;

    @Autowired
    public TestController(TestService service) {
        this.service = service;
    }

    // GET /tests/1?name=test
    // RES body
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Test getTest(@PathVariable Integer id,
                        @RequestParam String name) {
        return service.build(id, name);
    }

    // GET /tests/save?name=test
    @GetMapping(value = "/save", produces = APPLICATION_JSON_VALUE)
    public void save(@RequestParam String name) {
         service.save(name);
    }

    // GET /tests/load/test
    // RES body
    @GetMapping(value = "/load/{name}", produces = APPLICATION_JSON_VALUE)
    public Test load(@PathVariable String name) {
        return service.load(name);
    }
}

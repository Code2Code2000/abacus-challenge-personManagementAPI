package com.codingchallenge.abacus;

import com.codingchallenge.abacus.Person.Person;
import com.codingchallenge.abacus.Person.PersonApplication;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PersonApplication.class} , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonApplicationIntegrationTest {

	@LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders httpHeaders = new HttpHeaders();

    private final String REQUEST_URI_BASE = "http://localhost:";
    private Person person = new Person();

    private String uriForRequest;

    @Before
    public void setup(){
        person.setName("John");
        person.setAge(44);
        person.setLocale("US");

        uriForRequest = REQUEST_URI_BASE+port+"/persons/data/";
    }


	@Test
	public void testPutGetDeleteForPerson() throws Exception {
        HttpEntity<Person> entity = new HttpEntity<>(person, httpHeaders);
        ResponseEntity<String> response = this.restTemplate.exchange(uriForRequest, HttpMethod.PUT, entity, String.class);
        assert(response.getStatusCode().equals(HttpStatus.OK));

        response = restTemplate.exchange(uriForRequest+"/1", HttpMethod.GET, entity, String.class);
        String expected = "{\"id\":1,\"name\":\"John\",\"age\":44,\"locale\":\"US\"}";

        assert(response.getStatusCode().equals(HttpStatus.OK));
        JSONAssert.assertEquals(expected, response.getBody(), true);

        response = restTemplate.exchange(uriForRequest+"/1", HttpMethod.DELETE, entity, String.class);
        assert(response.getStatusCode().equals(HttpStatus.OK));
        JSONAssert.assertEquals(expected, response.getBody(), true);
    }

    @Ignore /* { negative scenarios } */
    public void testDeleteRequest_NoPerson() {

        HttpEntity<Person> entity = new HttpEntity<>(person, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uriForRequest+"/1", HttpMethod.DELETE, entity, String.class);

        assert(response.getStatusCode().equals(HttpStatus.NOT_FOUND));

    }

    @Ignore /* { negative scenarios } */
    public void testGetRequest_NoPerson() {
        HttpEntity<Person> entity = new HttpEntity<>(person, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(uriForRequest+"/1", HttpMethod.GET, entity, String.class);

        assert(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

}

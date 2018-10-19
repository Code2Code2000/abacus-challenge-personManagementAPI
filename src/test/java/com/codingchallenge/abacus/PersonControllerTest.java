package com.codingchallenge.abacus;

import com.codingchallenge.abacus.Person.Person;
import com.codingchallenge.abacus.Person.PersonApplication;
import com.codingchallenge.abacus.Person.PersonController;
import com.codingchallenge.abacus.Person.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonController.class)
@ContextConfiguration(classes={PersonApplication.class})
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    Person person = new Person("John", 33, "DE");


    String personJson = "{\"name\":\"John\",\"age\":33,\"locale\":\"DE\"}";

    @Test
    public void putPerson() throws Exception {

        Mockito.when(personService.addPerson(Mockito.any(Person.class))).thenReturn(person);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/persons/data/")
                .accept(MediaType.APPLICATION_JSON).content(personJson).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    public void getPerson() throws Exception {

        Mockito.when(personService.findPerson(Mockito.any(String.class))).thenReturn(person);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/persons/data/{id}", 1);

        MvcResult result = mockMvc.perform(get("/persons/data/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains(person.getName()));
        assertTrue(response.getContentAsString().contains(String.valueOf(person.getAge())));
        assertTrue(response.getContentAsString().contains(person.getLocale()));

//Mockito.when(personService.findPerson(Mockito.anyString())).thenReturn(person);
//        Mockito.when(personService.deletePerson(Mockito.anyString())).thenReturn(person);
//

    }

    @Test
    public void deletePerson() throws Exception {

        Mockito.when(personService.findPerson(Mockito.any(String.class))).thenReturn(person);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/persons/data/{id}", 1);

        MvcResult result = mockMvc.perform(get("/persons/data/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains(person.getName()));
        assertTrue(response.getContentAsString().contains(String.valueOf(person.getAge())));
        assertTrue(response.getContentAsString().contains(person.getLocale()));

    }

}

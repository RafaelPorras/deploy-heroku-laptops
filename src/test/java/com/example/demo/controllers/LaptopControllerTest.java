package com.example.demo.controllers;

import com.example.demo.entities.Laptop;
import com.example.demo.repository.LaptopRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Necesario para que Spring cree y acepte las peticiones http
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class LaptopControllerTest {
    /**
     * Preparacion del entorno previo a los test
     */
    //objeto para lanzar las peticiones http
    private TestRestTemplate testRestTemplate;

    //Necesario para que Spring nos inyecte un builder para usar testRestTemplate
    @Autowired
    private RestTemplateBuilder restTemplateBuider;

    //variable sobre la que inyectaremos el puerto aleatorio
    @LocalServerPort
    private int port;

    //coleccion para guardar los laptos
    private LaptopRepository laptopRepository;

    //Instanciamos un objeto HttpHeaders donde introduciremos las cabeceras
    HttpHeaders headers = new HttpHeaders();


    //método que iniciará el objeto restTemplateBuilder
    @BeforeEach
    void setUp(){
        restTemplateBuider = restTemplateBuider.rootUri("http://localhost:"+port);

        //mediante este objeto haremos las llamadas http
        testRestTemplate = new TestRestTemplate(restTemplateBuider);

        //introducimos las cabeceras
        headers.setContentType(MediaType.APPLICATION_JSON); //indicamos que vamos a enviar un JSON
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); //indicamos que recibimos un JSON
    }

    @Test
    void findAll() {
        //realizamos la peticion get a la api para que nos traiga todos los laptops
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops",Laptop[].class);

        //creamos los asserts
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void findOneById() {
        Long id = null;
        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/"+id,Laptop.class);

        try{
            if(id == 1L){
                assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
            }

            if(id == -1L){
                assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
            }

        }
        catch(NullPointerException e){
           assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        }

    }

    @Test

    void create() {

        //Preparamos el json a enviar
        String json = """
                        {
                            "brand": "Dell",
                            "model": "XS",
                            "screen": "15 pulgadas",
                            "hdd": "1TB",
                            "ram": "8GB"
                        }
                         """;

        //Preparamos la peticion
        HttpEntity<String> request = new HttpEntity<String>(json,headers);

        //Ejecutamos la peticion
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST,request,Laptop.class);

        //Obtenemos el body de la peticion
        Laptop laptopResult = response.getBody();

        assertEquals(1L,laptopResult.getId());
        assertEquals("Dell", laptopResult.getBrand());
    }

    @Test
    void createNull() {

        //Preparamos el json a enviar
        String json = "{}";

        //Preparamos la peticion
        HttpEntity<String> request = new HttpEntity<String>(json,headers);

        //Ejecutamos la peticion
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST,request,Laptop.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

    }
    @Test
    void createExist(){
        //Preparamos el json a enviar
        String json = """
                        {
                            "id": 1,
                            "brand": "Dell",
                            "model": "XS",
                            "screen": "15 pulgadas",
                            "hdd": "1TB",
                            "ram": "8GB"
                        }
                         """;

        //Preparamos la peticion
        HttpEntity<String> request = new HttpEntity<String>(json,headers);

        //Ejecutamos la peticion
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST,request,Laptop.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

   @Test
    void update() { //Crear antes
        //Para poder enviar el json hay que crear las cabeceras necesarias
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
       String json = """
                        {
                            "brand": "Asus",
                            "model": "F555L",
                            "screen": "15 pulgadas",
                            "hdd": "500GB",
                            "ram": "4GB"
                        }
                         """;

       //Preparamos la peticion
       HttpEntity<String> request = new HttpEntity<String>(json,headers);

       //Ejecutamos la peticion
       ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.POST,request,Laptop.class);



        //Preparamos el json a enviar
                json = """
                        {
                            "id": 2,
                            "brand": "DellActualizado",
                            "model": "XS",
                            "screen": "15 pulgadas",
                            "hdd": "1TB",
                            "ram": "8GB"
                        }
                         """;


        //Preparamos la peticion
       request = new HttpEntity<String>(json,headers);

        System.out.println(request);


        //Ejecutamos la peticion
       ResponseEntity<Laptop>  responseUpdate = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT,request,Laptop.class);

       assertEquals(HttpStatus.OK,responseUpdate.getStatusCode());

    }

    @Test
    void updateNoFound(){
        //Preparamos el json a enviar
        String json = """
                        {
                            "id": 6,
                            "brand": "DellActualizado",
                            "model": "XS",
                            "screen": "15 pulgadas",
                            "hdd": "1TB",
                            "ram": "8GB"
                        }
                         """;

        //Preparamos la peticion
        HttpEntity<String> request = new HttpEntity<String>(json,headers);

        //Ejecutamos la peticion
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT,request,Laptop.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }


    @Test
    void updateNull(){
        String json = "{}";

        //Preparamos la peticion
        HttpEntity<String> request = new HttpEntity<String>(json,headers);

        System.out.println(request);


        //Ejecutamos la peticion
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptops", HttpMethod.PUT,request,Laptop.class);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
    }

    @Test
    void delete() {
        //Preparamos el json a enviar
        String json = """
                        {
                            "id": 1,
                        }
                         """;

        //Preparamos la peticion
        HttpEntity<String> request = new HttpEntity<String>(json,headers);

        ResponseEntity<String> response = testRestTemplate.exchange("/api/laptops/",HttpMethod.DELETE,request,String.class);
        //Obtenemos el body de la peticion
        String laptopResult = response.getBody();

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

    }

    @Test
    void deleteAll() {
        //Preparamos el json a enviar
        String json = "{}";

        //Preparamos la peticion
        HttpEntity<String> request = new HttpEntity<String>(json,headers);

        //realizamos la peticion get a la api para que nos traiga todos los laptops
        ResponseEntity<String> response = testRestTemplate.exchange("/api/laptops",HttpMethod.DELETE,request,String.class);
        System.out.println(response.getStatusCode());

        //creamos los asserts
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
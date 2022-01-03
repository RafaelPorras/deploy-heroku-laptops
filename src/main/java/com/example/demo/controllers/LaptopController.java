package com.example.demo.controllers;

import com.example.demo.entities.Laptop;
import com.example.demo.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
public class LaptopController {
    //atributos
    /**
     * variable para poder usar mensajes de control de la aplicacion
     */
    private final Logger log = LoggerFactory.getLogger(LaptopController.class);

    private LaptopRepository laptopRepository;

    //Constructor
    public LaptopController(LaptopRepository laptopRepository){
        this.laptopRepository = laptopRepository;
    }


    //métodos CRUD

    /**
     * findAll()
     * @return
     * http://localhost:8080/api/laptops
     */
    @GetMapping("/api/laptops")
    @ApiOperation("Devuelve el almacen de portatiles") //mensaje swagger
    public List<Laptop> findAll(){
        return laptopRepository.findAll();
    }

    /**
     * findOneById
     * @param id
     * @return
     * http://localhost:8080/api/laptops/id
     */
    @GetMapping("/api/laptops/{id}")
    @ApiOperation("Devuelve un portatil por id")
    public ResponseEntity<Laptop> findOneById(@ApiParam("Clave primaria de tipo long") //comentario para swagger
                                               @PathVariable Long id){
        if(id == null){
            return ResponseEntity.badRequest().build();
        }
        Optional<Laptop> laptopOpt = laptopRepository.findById(id);
        return laptopOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    /**
     * create laptop
     * @param laptop
     * @return
     * http://localhost:8080/api/laptops
     */
    @PostMapping("/api/laptops")
    @ApiOperation("Almacena un nuevo portatil")
    public ResponseEntity<Laptop> create(@ApiParam("Objeto laptop que contiene todos los atributos del nuevo portatil") //comentario swagger
                                          @RequestBody Laptop laptop){
        if(laptop.getBrand() ==null &&
           laptop.getModel() == null &&
           laptop.getScreen() == null &&
           laptop.getHdd() == null &&
           laptop.getRam() == null ||
           laptop.getId() != null
           ){
            return ResponseEntity.badRequest().build();
        }


        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);



    }

    /**
     * update laptop
     * @param laptop
     * @return
     * http://localhost:8080/api/laptops
     */
    @PutMapping("/api/laptops")
    @ApiOperation("Actualiza un portatil existente")
    public ResponseEntity<Laptop> update(@ApiParam("Parametro laptop viene en el body de la peticion") //swagger
                                             @RequestBody Laptop laptop) {
        System.out.println("id:" + laptop.getId());
        System.out.println("laptop " + laptop);
        System.out.println("Repository " + laptopRepository.findAll());

        //Si nos llega una respuesta null
        if (laptop.getId() == null || laptopRepository.existsById(laptop.getId()) == false) {
            return ResponseEntity.badRequest().build();
        }



        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);

    }

    /**
     * delete laptop
     * @param id
     * @return
     * http://localhost:8080/api/laptops/id
     */
    @DeleteMapping("/api/laptops/{id}")
    @ApiOperation("Borra un portatil")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){

        if(laptopRepository.existsById(id)){
            laptopRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    /**
     * deleteAll
     * @return
     * http://localhost:8080/api/laptops
     */
    @DeleteMapping("/api/laptops")
    @ApiOperation("Borra el almacen de portatiles")
    public ResponseEntity<Laptop> deleteAll(){

        if(laptopRepository.count() > 0){
            laptopRepository.deleteAll();
        }
        return ResponseEntity.noContent().build();
    }
}

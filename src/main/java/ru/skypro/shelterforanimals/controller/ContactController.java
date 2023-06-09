package ru.skypro.shelterforanimals.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.shelterforanimals.entity.Contact;
import ru.skypro.shelterforanimals.service.ContactService;


import java.util.List;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Operation(summary = "Поиск контакта по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный по id контакт",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            ))}, tags = "Contact" )

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Contact>> getContact(@Parameter(description = "id контакта, для корректного поиска нужно указать верный id", required = true, example = "1")
                                                        @PathVariable int id){
        Optional <Contact> contact = contactService.findContactById(id);
        if(contact.isEmpty() ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contact);
    }
    @Operation(summary = "Поиск всех контактов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все найденные в базе контакты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array=@ArraySchema(schema = @Schema(implementation = Contact.class))
                            ))}, tags = "Contact")
    @GetMapping
    public ResponseEntity<List<Contact>> findAllContacts(){
        List<Contact> contacts = contactService.getAllContacts();
        if(contacts == null ){
            log.warn("В базе данных нет контактов");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contacts);
    }

    @Operation(summary = "Добавление нового контакта в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новый контакт, который нужно добавить в базу данных. " +
                            "При заполнении данных контакта значение параметра id указывать не нужно," +
                            "потому что оно генерируется программой автоматически",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Contact.class)
                    )), tags = "Contact")

    @PostMapping("/newContact")
    public Contact addContact(@RequestBody Contact contact){
        return contactService.addContact(contact);
    }
}


package ru.skypro.shelterforanimals.controller;

import com.pengrad.telegrambot.TelegramBot;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.shelterforanimals.entity.Contact;
import ru.skypro.shelterforanimals.repository.*;
import ru.skypro.shelterforanimals.service.ContactService;
import ru.skypro.shelterforanimals.service.PetPhotoService;
import ru.skypro.shelterforanimals.service.RecordService;
import ru.skypro.shelterforanimals.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContactController.class)
@ExtendWith(MockitoExtension.class)
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactRepository contactRepositoryTest;

    @MockBean
    private ContactService contactService;

    JSONObject jsonContact = new JSONObject();
    private final int id = 1;
    private final String name = "Иванов Иван Иванович";
    private final String numberPhone = "89061875572";
    private final LocalDate  date = LocalDate.of(2023,4,5);

    private final String date2 = date.toString();

    private final int clientStatus = 2;

    Contact contact = new Contact(1, "Иванов Иван Иванович", "89061875572", date, 2);
    @Test
    public void getContactByIdTestPositive() throws Exception {

        Mockito.when(contactRepositoryTest.save(Mockito.any())).thenReturn(contact);
        Mockito.when(contactService.findContactById(id)).thenReturn(Optional.of(contact));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.numberPhone").value(numberPhone))
                .andExpect(jsonPath("$.date").value(date2))
                .andExpect(jsonPath("$.clientStatus").value(clientStatus));

    }

    @Test
    public void getContactByIdTestNegative() throws Exception {
        Mockito.when(contactRepositoryTest.findById(Mockito.any())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact/{id}", 100)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllContactTestPositive() throws Exception {

        Mockito.when(contactRepositoryTest.save(Mockito.any())).thenReturn(contact);
        Mockito.when(contactRepositoryTest.findAll()).thenReturn(List.of(contact));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void saveContactTestPositive() {
        Mockito.when(contactRepositoryTest.save(Mockito.any())).thenReturn(contact);
        try {
            mockMvc.perform(post("/contact/newContact")
                            .content(jsonContact.toString())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

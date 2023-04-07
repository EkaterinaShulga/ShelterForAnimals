package ru.skypro.shelterforanimals.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.shelterforanimals.entity.Volunteer;
import ru.skypro.shelterforanimals.repository.VolunteerRepository;
import ru.skypro.shelterforanimals.service.Impl.VolunteerServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class VolunteerServiceImplTest {

    @Mock
    VolunteerRepository volunteerRepository;


    @InjectMocks
    VolunteerServiceImpl volunteerService;

    private Volunteer volunteer;

    @BeforeEach
    void init(){
        volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("ShulgaEkaterina");
        volunteer.setStatus(1);
        volunteer.setChatId(1300060749);
    }

    @Test
    public void saveVolunteer(){
        verify(volunteerRepository, atMostOnce()).save(volunteer);
        assertNotNull(volunteer);
    }

    @Test
    public void findVolunteerTest(){
        when(volunteerRepository.findByChatId(anyLong())).thenReturn(volunteer);
        volunteerService.findVolunteer(volunteer.getChatId());
        assertNotNull(volunteer);
    }
}

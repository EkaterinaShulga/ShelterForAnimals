package ru.skypro.shelterforanimals.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.shelterforanimals.entity.Client;
import ru.skypro.shelterforanimals.repository.ClientRepository;
import ru.skypro.shelterforanimals.service.Impl.ClientServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    ClientRepository clientRepository;
    @InjectMocks
    ClientServiceImpl clientServiceImpl;

    private Client client;

    @BeforeEach
    void init() {
        client = new Client();
        client.setId(1L);
        client.setChatId(1300060749L);
        client.setStatus(2);

    }

    @Test
    public void saveClientTest() {
        verify(clientRepository, atMostOnce()).save(client);
        assertNotNull(client);
    }

    @Test
    public void findClientTest() {
        when(clientRepository.findByChatId(anyLong())).thenReturn(client);
        Client result = clientServiceImpl.findClient(client.getId());
        assertNotNull(result);
    }
}

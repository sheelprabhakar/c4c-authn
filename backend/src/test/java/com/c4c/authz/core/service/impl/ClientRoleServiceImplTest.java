package com.c4c.authz.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.c4c.authz.core.entity.ClientRoleEntity;
import com.c4c.authz.core.entity.ClientRoleId;
import com.c4c.authz.core.repository.ClientRoleRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientRoleServiceImplTest {

    /**
     * The Client role service.
     */
    @InjectMocks
    ClientRoleServiceImpl clientRoleService;
    /**
     * The Client role repository.
     */
    @Mock
    ClientRoleRepository clientRoleRepository;

    /**
     * Create ok.
     */
    @Test
    @DisplayName("Test Create new client role OK")
    void createOk() {
        when(this.clientRoleRepository.save(any(ClientRoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        ClientRoleEntity roleEntity = Instancio.create(ClientRoleEntity.class);
        ClientRoleEntity roleEntity1 = this.clientRoleService.create(roleEntity);
        assertEquals(roleEntity, roleEntity1);
    }

    /**
     * Update ok.
     */
    @Test
    @DisplayName("Test update client role OK")
    void updateOK() {
        when(this.clientRoleRepository.save(any(ClientRoleEntity.class))).thenAnswer(i -> i.getArguments()[0]);
        ClientRoleEntity roleEntity = Instancio.create(ClientRoleEntity.class);
        ClientRoleEntity roleEntity1 = this.clientRoleService.update(roleEntity);
        assertEquals(roleEntity, roleEntity1);
    }

    /**
     * Find by id ok.
     */
    @Test
    @DisplayName("Test find by id client role OK")
    void findByIdOk() {
        ClientRoleEntity roleEntity = Instancio.create(ClientRoleEntity.class);
        when(this.clientRoleRepository.findById(any(ClientRoleId.class))).thenReturn(Optional.of(roleEntity));

        ClientRoleEntity roleEntity1 = this.clientRoleService.findById(new ClientRoleId());
        assertEquals(roleEntity, roleEntity1);
    }

    /**
     * Find all ok.
     */
    @Test
    @DisplayName("Test find all client role OK")
    void findAllOk() {
        List<ClientRoleEntity> roleEntities = Instancio.ofList(ClientRoleEntity.class).size(5).create();
        when(this.clientRoleRepository.findAll()).thenReturn(roleEntities);

        List<ClientRoleEntity> roleEntities1 = this.clientRoleService.findAll();
        assertEquals(5, roleEntities1.size());
        assertEquals(roleEntities, roleEntities1);

        when(this.clientRoleRepository.findAll()).thenReturn(Collections.emptyList());
        roleEntities1 = this.clientRoleService.findAll();
        assertEquals(0, roleEntities1.size());
    }

    /**
     * Find by pagination ok.
     */
    @Test
    @DisplayName("Test find findByPagination client role OK")
    void findByPaginationOk() {
        List<ClientRoleEntity> roleEntities = Instancio.ofList(ClientRoleEntity.class).size(11).create();
        PageImpl<ClientRoleEntity> entityPage =
            new PageImpl<>(roleEntities);
        when(this.clientRoleRepository.findAll(any(Pageable.class))).thenReturn(entityPage);

        Page<ClientRoleEntity> roleEntities1 = this.clientRoleService.findByPagination(0, 11);

        assertEquals(roleEntities, roleEntities1.getContent());
        assertEquals(1, roleEntities1.getTotalPages());
    }

    /**
     * Delete by id ok.
     */
    @Test
    @DisplayName("Test deleteBy id OK")
    void deleteByIdOK() {
        doNothing().when(this.clientRoleRepository).deleteById(any(ClientRoleId.class));
        ClientRoleId clientRoleId = new ClientRoleId();
        this.clientRoleService.deleteById(clientRoleId);
        verify(this.clientRoleRepository, times(1)).deleteById(clientRoleId);
    }

    /**
     * Delete all by id ok.
     */
    @Test
    @DisplayName("Test deleteAllById OK")
    void deleteAllByIdOK() {
        doNothing().when(this.clientRoleRepository).deleteAllById(ArgumentMatchers.<ClientRoleId>anyList());
        List<ClientRoleId> clientRoleIds = Instancio.ofList(ClientRoleId.class).size(5).create();
        this.clientRoleService.deleteAllById(clientRoleIds);
        verify(this.clientRoleRepository, times(1)).deleteAllById(clientRoleIds);
    }
}
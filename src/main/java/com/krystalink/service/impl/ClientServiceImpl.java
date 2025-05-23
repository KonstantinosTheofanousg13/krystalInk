package com.krystalink.service.impl;

import com.krystalink.dto.ClientRequest;
import com.krystalink.dto.ClientResponse;
import com.krystalink.dto.ClientWithAppointmentsResponse;
import com.krystalink.dto.GlobalSearchRequest;
import com.krystalink.exception.ClientNotFoundException;
import com.krystalink.exception.DuplicateClientException;
import com.krystalink.model.Appointment;
import com.krystalink.model.Client;
import com.krystalink.repository.AppointmentRepository;
import com.krystalink.repository.ClientRepository;
import com.krystalink.service.ClientService;
import com.krystalink.util.BuilderHelper;
import com.krystalink.util.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final AppointmentRepository appointmentRepository;
    private final BuilderHelper builderHelper;

    @Override
    public ClientResponse createClient(ClientRequest request) {
        log.debug("createClient() started with phone number: {}", request.getPhoneNumber());
        List<Client> existingClients = clientRepository.findByPhoneNumberIn(List.of(request.getPhoneNumber()));

        if (!existingClients.isEmpty())
            throw new DuplicateClientException(request.getPhoneNumber());

        Client client = entityDtoMapper.toClient(request);
        Client savedClient = clientRepository.save(client);
        log.debug("Create client with phone number: {}", request.getPhoneNumber());
        return entityDtoMapper.toClientResponse(savedClient);
    }

    @Override
    public List<ClientWithAppointmentsResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(entityDtoMapper::toClientWithAppointments)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientWithAppointmentsResponse> getClientByPhone(List<String> phoneNumbers) {
        List<Client> clients = clientRepository.findByPhoneNumberIn(phoneNumbers);
        if (clients.isEmpty())
            return Collections.emptyList();

        return clients.stream()
                .map(entityDtoMapper::toClientWithAppointments)
                .collect(Collectors.toList());
    }

    @Override
    public void updateClient(ClientRequest clientRequest, String phoneNumber) throws ClientNotFoundException, DuplicateClientException {
        log.debug("updateClient() started for phone number: {}", phoneNumber);
        Client client = clientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ClientNotFoundException(Collections.singletonList(phoneNumber)));

        if (!client.getPhoneNumber().equals(clientRequest.getPhoneNumber())) {
            boolean phoneExists = clientRepository.findByPhoneNumber(clientRequest.getPhoneNumber()).isPresent();
            if (phoneExists)
                throw new DuplicateClientException(clientRequest.getPhoneNumber());
        }

        client.setFirstName(clientRequest.getFirstName());
        client.setLastName(clientRequest.getLastName());
        client.setPhoneNumber(clientRequest.getPhoneNumber());

        clientRepository.save(client);
        log.debug("updateClient() finished for phone number: {}", phoneNumber);
    }

    @Override
    public List<ClientWithAppointmentsResponse> globalSearch(GlobalSearchRequest request) {
        log.debug("globalSearch() started with request: {}", request);

        if(validateGlobalSearchRequest(request))
            return List.of();

        Client clientFromRequest = builderHelper.buildClientFromRequest(request);
        Appointment appointmentFromRequest = builderHelper.buildAppointmentFromRequest(request);

        Example<Client> clientExample = Example.of(clientFromRequest, builderHelper.buildClientMatcher());
        Example<Appointment> appointmentExample = Example.of(appointmentFromRequest, builderHelper.buildAppointmentMatcher());

        List<Client> clients = clientRepository.findAll(clientExample);
        List<Appointment> appointments = appointmentRepository.findAll(appointmentExample);

        Map<Long, List<Appointment>> appointmentsByClientId = appointments.stream()
                .filter(app -> app.getClient() != null)
                .collect(Collectors.groupingBy(app -> app.getClient().getId()));

        List<ClientWithAppointmentsResponse> result = clients.stream()
                .map(client -> entityDtoMapper.toClientWithAppointments(client, appointmentsByClientId.getOrDefault(client.getId(), List.of())))
                .collect(Collectors.toList());

        log.debug("globalSearch() finished with {} matching clients", result.size());
        return result;
    }

    private Boolean validateGlobalSearchRequest(GlobalSearchRequest request) {
        return (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank())
                && request.getAppointmentDate() == null
                && (request.getServiceName() == null || request.getServiceName().isBlank())
                && (request.getFullName() == null || request.getFullName().isBlank());
    }
}

package com.krystalink.service;

import com.krystalink.dto.ClientRequest;
import com.krystalink.dto.ClientResponse;
import com.krystalink.dto.ClientWithAppointmentsResponse;
import com.krystalink.dto.GlobalSearchRequest;
import com.krystalink.exception.ClientNotFoundException;
import com.krystalink.exception.DuplicateClientException;

import java.util.List;

public interface ClientService {
    ClientResponse createClient(ClientRequest request);

    List<ClientWithAppointmentsResponse> getAllClients();

    List<ClientWithAppointmentsResponse> getClientByPhone(List<String> phoneNumbers);

    List<ClientWithAppointmentsResponse> globalSearch(GlobalSearchRequest request);

    void updateClient(ClientRequest clientRequest, String phoneNumber) throws ClientNotFoundException, DuplicateClientException;
}

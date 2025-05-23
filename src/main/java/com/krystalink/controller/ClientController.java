package com.krystalink.controller;

import com.krystalink.dto.ClientWithAppointmentsResponse;
import com.krystalink.dto.GlobalSearchRequest;
import com.krystalink.service.ClientService;
import com.krystalink.dto.ClientRequest;
import com.krystalink.dto.ClientResponse;
import com.krystalink.helper.PhoneNumberQuery;
import com.krystalink.exception.ClientNotFoundException;
import com.krystalink.exception.DuplicateClientException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<ClientResponse> addClient(@Valid @RequestBody ClientRequest clientRequest) {
        ClientResponse created = clientService.createClient(clientRequest);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/edit")
    public ResponseEntity<ClientResponse> editClient(@Valid @RequestBody ClientRequest clientRequest, @RequestParam String phoneNumber) throws ClientNotFoundException,DuplicateClientException  {
        clientService.updateClient(clientRequest,phoneNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClientWithAppointmentsResponse>> getAllClients() {
        List<ClientWithAppointmentsResponse> response = clientService.getAllClients();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientWithAppointmentsResponse>> getClientByPhone(@Valid PhoneNumberQuery phoneNumberQuery)  {
        List<ClientWithAppointmentsResponse> response = clientService.getClientByPhone(phoneNumberQuery.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/global/search")
    public ResponseEntity<List<ClientWithAppointmentsResponse>> globalSearch(@Valid GlobalSearchRequest searchRequest){
        List<ClientWithAppointmentsResponse> response = clientService.globalSearch(searchRequest);
        return ResponseEntity.ok(response);

    }

    //TODO::Create a new Controller for the globalSearch to be more clean
    //TODO::Create a Delete api to delete a client
    //TODO::Unit Tests for all the scenarios


}

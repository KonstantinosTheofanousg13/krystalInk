package com.krystalink.repository;

import com.krystalink.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>, QueryByExampleExecutor<Client> {

    List<Client> findByPhoneNumberIn(List<String> phoneNumbers);

    Optional<Client> findByPhoneNumber(String phoneNumber);

}

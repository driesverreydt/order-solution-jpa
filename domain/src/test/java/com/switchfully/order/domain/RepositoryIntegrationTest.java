package com.switchfully.order.domain;

import com.switchfully.order.IntegrationTest;
import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerDatabase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;

 class RepositoryIntegrationTest extends IntegrationTest {

    @Inject
    private Repository<Customer, CustomerDatabase> repository;

    @AfterEach
    void resetDatabase() {
        repository.reset();
    }

    @Test
    void save() {
        Customer customerToSave = aCustomer().build();

        Customer savedCustomer = repository.save(customerToSave);

        Assertions.assertThat(savedCustomer.getId()).isNotNull();
        Assertions.assertThat(repository.get(savedCustomer.getId()))
                .isEqualToComparingFieldByField(savedCustomer);
    }

    @Test
    void update() {
        Customer customerToSave = aCustomer().withFirstname("Jo").withLastname("Jorissen").build();
        Customer savedCustomer = repository.save(customerToSave);


        Customer updatedCustomer = repository.update(aCustomer()
                .withId(savedCustomer.getId())
                .withFirstname("Joske")
                .withLastname("Jorissen")
                .build());

        Assertions.assertThat(updatedCustomer.getId()).isNotNull().isEqualTo(savedCustomer.getId());
        Assertions.assertThat(updatedCustomer.getFirstname()).isEqualTo("Joske");
        Assertions.assertThat(updatedCustomer.getLastname()).isEqualTo("Jorissen");
        Assertions.assertThat(repository.getAll()).hasSize(1);
    }

    @Test
    void get() {
        Customer savedCustomer = repository.save(aCustomer().build());

        Customer actualCustomer = repository.get(savedCustomer.getId());

        Assertions.assertThat(actualCustomer)
                .isEqualToComparingFieldByField(savedCustomer);
    }

    @Test
    void getAll() {
        Customer customerOne = repository.save(aCustomer().build());
        Customer customerTwo = repository.save(aCustomer().build());

        Map<UUID, Customer> allCustomers = repository.getAll();

        Assertions.assertThat(allCustomers.values())
                .containsExactlyInAnyOrder(customerOne, customerTwo);
    }

}

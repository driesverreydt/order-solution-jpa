package com.switchfully.order.domain;

import com.switchfully.order.domain.customers.Customer;
import com.switchfully.order.domain.customers.CustomerDatabase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.switchfully.order.domain.customers.CustomerTestBuilder.aCustomer;

class EntityDatabaseTest {

    @Test
    void poplulate_getAll() {
        EntityDatabase<Customer> database = new CustomerDatabase();
        Customer customer1 = aCustomer().build();
        Customer customer2 = aCustomer().build();
        Customer customer3 = aCustomer().build();

        customer1.generateId();
        customer2.generateId();
        customer3.generateId();

        database.populate(customer1, customer2, customer3);

        Assertions.assertThat(database.getAll()).hasSize(3);
        Assertions.assertThat(database.getAll().values()).containsExactlyInAnyOrder(customer1, customer2, customer3);
    }

    @Test
    void save_givenDifferentId_thenStoreBoth() {
        EntityDatabase<Customer> database = new CustomerDatabase();
        Customer customer = aCustomer().build();
        customer.generateId();
        database.populate(customer);

        Customer customer2 = aCustomer().build();
        customer2.generateId();
        database.save(customer2);

        Assertions.assertThat(database.getAll()).hasSize(2);
    }

}

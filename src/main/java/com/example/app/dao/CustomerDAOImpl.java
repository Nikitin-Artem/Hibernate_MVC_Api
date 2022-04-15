package com.example.app.dao;

import com.example.app.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        Session currentSession = sessionFactory.getCurrentSession();

        // sort by last name
        Query<Customer> query = currentSession.createQuery("from Customer order by lastName", Customer.class);

        List<Customer> customers = query.getResultList();

        return customers;
    }

    @Override
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.getCurrentSession();

        // save/update the customer
        session.saveOrUpdate(customer);

        session.save(customer);
    }

    @Override
    public Customer getCustomer(int id) {
        Session currentSession = sessionFactory.getCurrentSession();

        Customer customer = currentSession.get(Customer.class, id);

        return customer;
    }

    @Override
    public void deleteCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("delete from Customer where id =:customerId");
        query.setParameter("customerId", id);

        query.executeUpdate();
    }

    @Override
    public List<Customer> searchCustomer(String searchName) {
        Session session = sessionFactory.getCurrentSession();

        Query query = null;

        // only search by name/surname if not empty
        if (searchName != null && searchName.trim().length() > 0) {
            query = session.createQuery("from Customer where lower(firstName) like :theName " +
                    "or lower(lastName) like :theName", Customer.class);
            query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
        }else{
            // searched name is empty, then just get all customers
            query = session.createQuery("from Customer", Customer.class);
        }

        List<Customer> customers = query.getResultList();

        return customers;
    }
}

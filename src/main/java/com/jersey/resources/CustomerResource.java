package com.jersey.resources;

import com.jersey.persistence.CustomerDao;
import com.jersey.representations.Customer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@Component
public class CustomerResource {
    private CustomerDao customerDao;
    @Inject
    public CustomerResource(CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    /**
     * Get all Customers
     * @return customers
     */
    @GET
    public List<Customer> getAll(){
        List<Customer> customers = this.customerDao.findAll();
        return customers;
    }

    /**
     * Get single Customer
     * @param id
     * @return customer
     */
    @GET
    @Path("{id}")
    public Customer getOne(@PathParam("id")long id) {
        Customer customer = customerDao.findOne(id);
        if(customer == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }else {
            return customer;
        }
    }

    /**
     * Create new Customer
     * @param customer
     * @return new customer
     */
    @POST
    public Customer save(@Valid Customer customer) {
        return customerDao.save(customer);
    }

    /**
     * Update existing Customer
     * @param id
     * @param customer
     * @return updated customer
     */
    @PUT
    @Path("{id}")
    public Customer update(@PathParam("id")long id, @Valid Customer customer) {
        if(customerDao.findOne(id) == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }else {
            customer.setId(id);
            return customerDao.save(customer);
        }
    }

    /**
     * Delete customer
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id")long id) {
        Customer customer = customerDao.findOne(id);
        if(customer == null){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }else {
            customerDao.delete(customer);
        }
    }
}
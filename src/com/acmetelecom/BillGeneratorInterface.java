package com.acmetelecom;

import com.acmetelecom.customer.Customer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: andreipetric
 * Date: 14/11/2013
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public interface BillGeneratorInterface {
    void send(Customer customer, List<BillingSystem.LineItem> calls, String totalBill);
}

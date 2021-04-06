/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.paypal;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Food;
import java.util.List;

/**
 *
 * @author DELL
 */
public class PaymentServices {

    private static final String CLIENT_ID = "ARyX2FyVGtnkS9ODpufOdJiJqVz0xkyOSgauQkeUIqCmO6ef65CqtJYlUfGO0sJ3Wacrxf7Nl8GPwb8g";
    private static final String CLIENT_SECRET = "EDGWMJvebIRv53PPCCPxHWVaw40sbO1OtB-9BjHnYuAHEoPYqq4ZpCPCIbzy9OeBHDbZofa_IHcSCyRS";
    private static final String MODE = "sandbox";

    public String authorizePayment(CartShopping cartShopping)
            throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(cartShopping);
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);
        return getApprovalLink(approvedPayment);

    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("John")
                .setLastName("Doe")
                .setEmail("sb-ojmsp4703889@personal.example.com");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8084/J3.L.P0013/viewCart.jsp");
        redirectUrls.setReturnUrl("http://localhost:8084/J3.L.P0013/ExecutePaymentServlet");
        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(CartShopping cartShopping) {
        List<Transaction> listTransaction = new java.util.ArrayList<>();
        ItemList itemList = new ItemList();
        List<Item> items = new java.util.ArrayList<>();
        try {
            for (Food food : cartShopping.getCart().values()) {
                Item item = new Item();
                item.setCurrency("USD");
                item.setName(food.getFoodName());
                item.setPrice(Float.toString(food.getFoodPrice()) );
                item.setQuantity(Integer.toString(food.getFoodQuantity()));
                items.add(item);
            }
            Amount amount = new Amount();
            amount.setCurrency("USD");
            amount.setTotal(Float.toString(cartShopping.getTotalOrder()));

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setDescription(cartShopping.getCustomerName());
            itemList.setItems(items);
            transaction.setItemList(itemList);
            listTransaction.add(transaction);
        } catch (Exception e) {

        }
        return listTransaction;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId)
            throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        return payment.execute(apiContext, paymentExecution);
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

}

package com.boringco.sentrax.model;
import java.util.*;
import java.util.stream.Collectors;
/** Everything a rule may need to know about the customer: profile, recent history, known devices. */
public class CustomerContext {
    private final Customer customer; private final List<Transaction> history; private final Set<String> knownDevices;
    public CustomerContext(Customer customer, List<Transaction> history) {
        this.customer = customer; this.history = history;
        this.knownDevices = history.stream().map(Transaction::getDeviceId).filter(Objects::nonNull).collect(Collectors.toSet());
    }
    public Customer getCustomer() { return customer; }
    public List<Transaction> getHistory() { return history; }
    public Set<String> getKnownDevices() { return knownDevices; }
    public Optional<Transaction> getLastTransaction() { return history.stream().max(Comparator.comparing(Transaction::getTxnTime)); }
}

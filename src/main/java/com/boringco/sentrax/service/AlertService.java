package com.boringco.sentrax.service;
import com.boringco.sentrax.dao.AlertDAO;
import com.boringco.sentrax.model.Alert;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public class AlertService {
    private static final ExecutorService POOL = Executors.newFixedThreadPool(3);   // background alert writers
    private static final AtomicInteger queued = new AtomicInteger();               // thread-safe counter
    private static int resolvedCount;                                              // guarded by synchronized methods
    private final AlertDAO dao = new AlertDAO();

    /** Writes the alert on a pool thread so the analyst's page returns immediately. */
    public void raiseAsync(long transactionId) {
        queued.incrementAndGet();
        POOL.submit(() -> {
            try { dao.save(new Alert(transactionId)); }
            catch (RuntimeException e) { System.err.println("Alert write failed: " + e.getMessage()); }
        });
    }
    public List<Alert> openAlerts() { return dao.findOpen(); }
    public int openCount() { return dao.countOpen(); }
    public synchronized void resolve(long alertId) { dao.resolve(alertId); resolvedCount++; }
    public static synchronized int getResolvedCount() { return resolvedCount; }
    public static int getQueuedCount() { return queued.get(); }
    public static void shutdown() {
        POOL.shutdown();
        try { if (!POOL.awaitTermination(5, TimeUnit.SECONDS)) POOL.shutdownNow(); }
        catch (InterruptedException e) { POOL.shutdownNow(); Thread.currentThread().interrupt(); }
    }
}

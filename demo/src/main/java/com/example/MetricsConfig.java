package com.example;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import spark.Spark;

import static spark.Spark.get;

public class MetricsConfig {

    public static Counter ReceivedMessagesCounter;
    public static Counter SentMessagesCounter;

    public static void configureMetrics() {
        PrometheusMeterRegistry meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, CollectorRegistry.defaultRegistry, Clock.SYSTEM);
        new ClassLoaderMetrics().bindTo(meterRegistry);
        new JvmMemoryMetrics().bindTo(meterRegistry);
        new JvmGcMetrics().bindTo(meterRegistry);
        new ProcessorMetrics().bindTo(meterRegistry);
        new JvmThreadMetrics().bindTo(meterRegistry);

        ReceivedMessagesCounter = meterRegistry.counter("received_messages_count");
        SentMessagesCounter = meterRegistry.counter("sent_messages_count");

        Spark.port(9091);

        get("/metrics", (request, response) -> meterRegistry.scrape());
    }
}
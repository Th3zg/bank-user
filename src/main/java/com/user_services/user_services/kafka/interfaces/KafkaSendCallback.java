package com.user_services.user_services.kafka.interfaces;

import com.user_services.user_services.outbox.OutboxEvent;
import org.apache.kafka.clients.producer.ProducerRecord;

public interface KafkaSendCallback {
  void onSuccess(OutboxEvent data);
  void onFailure(OutboxEvent data, ProducerRecord<String, OutboxEvent> record, Exception ex);
}

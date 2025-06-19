package com.user_services.user_services.kafka.interfaces;

import com.user_services.user_services.outbox.OutboxEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.SendResult;

public interface KafkaSendCallback {
  void onSuccess(OutboxEvent data, SendResult<String, OutboxEvent> result);
  void onFailure(OutboxEvent data, ProducerRecord<String, OutboxEvent> record, Throwable ex);
}

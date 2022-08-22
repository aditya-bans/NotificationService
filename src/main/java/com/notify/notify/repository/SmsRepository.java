package com.notify.notify.repository;

import com.notify.notify.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<Sms, Long> {

}

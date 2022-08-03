package com.notify.notify.repository;

import com.notify.notify.model.SMS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SMSRepository extends JpaRepository<SMS,Long> {

}

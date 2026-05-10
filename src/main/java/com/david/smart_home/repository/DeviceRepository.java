package com.david.smart_home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.david.smart_home.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {

}

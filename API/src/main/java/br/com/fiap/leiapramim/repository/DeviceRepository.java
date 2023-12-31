package br.com.fiap.leiapramim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.leiapramim.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

  Device findBySourceDeviceId(String deviceId);

}

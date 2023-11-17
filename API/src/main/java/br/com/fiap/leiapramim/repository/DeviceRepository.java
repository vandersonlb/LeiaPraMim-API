package br.com.fiap.leiapramim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.leiapramim.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

  List<Device> findByDeviceId(String deviceId);

}

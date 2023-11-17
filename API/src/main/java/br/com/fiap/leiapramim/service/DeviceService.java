package br.com.fiap.leiapramim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.leiapramim.entity.Device;
import br.com.fiap.leiapramim.repository.DeviceRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("device")
public class DeviceService {

  @Autowired
  private DeviceRepository deviceRepository;

  @GetMapping
  public List<Device> listAll() {
    return deviceRepository.findAll();
  }

  @GetMapping("{id}")
  public Device getById(@PathVariable int id) {
    return deviceRepository.findById(id).get();
  }

  @GetMapping("source_device/{deviceId}")
  public Device getBySourceDeviceId(@PathVariable String deviceId) {
    return deviceRepository.findBySourceDeviceId(deviceId);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Device addDevice(@Valid @RequestBody Device device) {
    return deviceRepository.save(device);
  }

}

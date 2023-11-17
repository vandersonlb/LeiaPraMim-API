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
import br.com.fiap.leiapramim.entity.Text;
import br.com.fiap.leiapramim.repository.DeviceRepository;
import br.com.fiap.leiapramim.repository.TextRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("text")
public class TextService {

  @Autowired
  private TextRepository textRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @GetMapping
  public List<Text> listAll() {
    return textRepository.findAll();
  }

  @GetMapping("{id}")
  public Text getById(@PathVariable int id) {
    return textRepository.findById(id).get();
  }

  @GetMapping("device/{deviceId}")
  public List<Text> listAllByDeviceId(@PathVariable int deviceId) {
    Device device = deviceRepository.findById(deviceId).get();
    return textRepository.findAllByDevice(device);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Text add(@Valid @RequestBody Text text) {
    return textRepository.save(text);
  }

}

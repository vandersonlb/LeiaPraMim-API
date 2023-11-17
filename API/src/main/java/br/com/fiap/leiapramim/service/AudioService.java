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

import br.com.fiap.leiapramim.entity.Audio;
import br.com.fiap.leiapramim.entity.Text;
import br.com.fiap.leiapramim.repository.AudioRepository;
import br.com.fiap.leiapramim.repository.TextRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("audio")
public class AudioService {

  @Autowired
  private AudioRepository audioRepository;

  @Autowired
  private TextRepository textRepository;

  @GetMapping
  public List<Audio> listAll() {
    return audioRepository.findAll();
  }

  @GetMapping("{id}")
  public Audio getById(@PathVariable int id) {
    return audioRepository.findById(id).get();
  }

  @GetMapping("text/{textId}")
  public Audio getAudioByTextId(@PathVariable int textId) {
    Text text = textRepository.findById(textId).get();
    return audioRepository.findByText(text);
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Audio addAudio(@Valid @RequestBody Audio audio) {
    return audioRepository.save(audio);
  }

}

package br.com.fiap.leiapramim.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "T_LPM_AUDIO")
public class Audio {

  @Id
  @Column(name = "id_audio")
  @SequenceGenerator(name = "audio", sequenceName = "sq_lpm_audio", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audio")
  private Integer id;

  @Column(name = "url_audio")
  @NotNull(message = "URL do áudio obrigatória")
  private String audioURL;

  @Column(name = "dt_create")
  @NotNull(message = "Data de criação do registo obrigatória")
//  @Past(message = "Data de criação do registro deve ser uma data passada")
  private LocalDate createDate;

  @OneToOne
  @JoinColumn(name = "id_text")
  @NotNull(message = "Um áudio deve pertencer a algum texto")
  private Text text;

  public Audio() {
    super();
  }

  public Audio(Integer id, String audioURL, LocalDate createDate, Text text) {
    super();
    this.id = id;
    this.audioURL = audioURL;
    this.createDate = createDate;
    this.text = text;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAudioURL() {
    return audioURL;
  }

  public void setAudioURL(String audioURL) {
    this.audioURL = audioURL;
  }

  public LocalDate getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDate createDate) {
    this.createDate = createDate;
  }

  public Text getText() {
    return text;
  }

  public void setText(Text text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return String.format("Audio [id=%s, audioURL=%s, createDate=%s, text=%s]", id, audioURL, createDate, text);
  }

}

package br.com.fiap.leiapramim.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "T_LPM_TEXT")
public class Text {

  @Id
  @Column(name = "id_text")
  @SequenceGenerator(name = "text", sequenceName = "sq_lpm_text", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text")
  private Integer id;

  @Column(name = "url_photo")
  @NotNull(message = "URL da imagem obrigatória")
  private String photoURL;

  @Column(name = "ds_text")
  private String text;

  @Column(name = "dt_create")
  @NotNull(message = "Data de criação do registo obrigatória")
  @Past(message = "Data de criação do registo deve ser uma data passada")
  private LocalDate createDate;

  @ManyToOne
  @JoinColumn(name = "id_device")
  @NotNull(message = "Um texto deve pertencer a algum id de aparelho")
  private Device device;

  public Text() {
    super();
  }

  public Text(Integer id, String photoURL, String text, @Past LocalDate createDate, Device device) {
    super();
    this.id = id;
    this.photoURL = photoURL;
    this.text = text;
    this.createDate = createDate;
    this.device = device;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPhotoURL() {
    return photoURL;
  }

  public void setPhotoURL(String photoURL) {
    this.photoURL = photoURL;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDate getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDate createDate) {
    this.createDate = createDate;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  @Override
  public String toString() {
    return String.format("Text [id=%s, photoURL=%s, text=%s, createDate=%s, device=%s]", id, photoURL, text, createDate, device);
  }

}

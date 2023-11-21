package br.com.fiap.leiapramim.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "T_LPM_DEVICE", uniqueConstraints = {@UniqueConstraint(columnNames = "id_source", name = "uk_lpm_id_source")})
public class Device {

  @Id
  @Column(name = "id_device")
  @SequenceGenerator(name = "device", sequenceName = "sq_lpm_device", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device")
  private Integer id;

  @Column(name = "id_source")
  @NotNull(message = "ID de origem do aparelho obrigatório")
  private String sourceDeviceId;

  @Column(name = "nm_factory")
  private String deviceFactory;

  @Column(name = "ds_model")
  private String deviceModel;

  @Column(name = "vs_android")
  private String androidVersion;

  @Column(name = "vs_sdk")
  private Integer sdkVersion;

  @Column(name = "dt_record")
  @NotNull(message = "Data de criação do registo obrigatória")
//  @Past(message = "Data de criação do registro deve ser uma data passada")
  private LocalDate dateRecord;

  public Device() {
    super();
  }

  public Device(Integer id, String deviceId, String deviceFactory, String deviceModel, String androidVersion, Integer sdkVersion, LocalDate dateRecord) {
    super();
    this.id = id;
    this.sourceDeviceId = deviceId;
    this.deviceFactory = deviceFactory;
    this.deviceModel = deviceModel;
    this.androidVersion = androidVersion;
    this.sdkVersion = sdkVersion;
    this.dateRecord = dateRecord;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDeviceId() {
    return sourceDeviceId;
  }

  public void setDeviceId(String deviceId) {
    this.sourceDeviceId = deviceId;
  }

  public String getDeviceFactory() {
    return deviceFactory;
  }

  public void setDeviceFactory(String deviceFactory) {
    this.deviceFactory = deviceFactory;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getAndroidVersion() {
    return androidVersion;
  }

  public void setAndroidVersion(String androidVersion) {
    this.androidVersion = androidVersion;
  }

  public Integer getSdkVersion() {
    return sdkVersion;
  }

  public void setSdkVersion(Integer sdkVersion) {
    this.sdkVersion = sdkVersion;
  }

  public LocalDate getDateRecord() {
    return dateRecord;
  }

  public void setDateRecord(LocalDate dateRecord) {
    this.dateRecord = dateRecord;
  }

  @Override
  public String toString() {
    return String.format("Device [id=%s, sourceDeviceId=%s, deviceFactory=%s, deviceModel=%s, androidVersion=%s, sdkVersion=%s, dateRecord=%s]",
                          id, sourceDeviceId, deviceFactory, deviceModel, androidVersion, sdkVersion, dateRecord);
  }

}

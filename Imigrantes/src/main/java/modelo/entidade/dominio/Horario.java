package modelo.entidade.dominio;

import java.time.LocalDate;
import java.time.LocalTime;

public class Horario {
    private Long id;
    private Long salaId;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSalaId() { return salaId; }
    public void setSalaId(Long salaId) { this.salaId = salaId; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
}

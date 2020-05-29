package guia08.problema01;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class Tarea {
	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	public Tarea(Integer id, String descr, Integer duracionEstimada) {
		super();
		this.id = id;
		this.descripcion = descr;
		this.duracionEstimada = duracionEstimada;
		this.facturada = false;
	}
	
	public void asignarEmpleado(Empleado e) {
		// si la tarea ya tiene un empleado asignado
		// y tiene fecha de finalizado debe lanzar una excepcion
		try {
			if(this.empleadoAsignado == null && this.fechaFin == null) {
				this.empleadoAsignado = e;
			} else {
				if(this.empleadoAsignado != null) {
					throw new EmpleadoYaAsignadoException("e");
				}
				if(this.fechaFin != null) {
					throw new TareaFinalizadaException("F");
				}
			}
		}catch(EmpleadoYaAsignadoException err1){
            System.out.println(err1.getMessage());
		}catch(TareaFinalizadaException err2){
            System.out.println(err2.getMessage());
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	
	public long diferenciaDiasTarea() {
		// Calcula la diferencia entre la fecha de inicio y la de finalizacion
		// En caso de ser 0, quiere decir que la inicio y finalizo el mismo dia
		long diferencia = ChronoUnit.DAYS.between(this.fechaInicio, this.fechaFin);
		if(diferencia == 0) {
			return 1L;
		}
		
		return diferencia;
	}
	
	
	
}

package guia08.problema01;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class Empleado {
	public enum Tipo {
		CONTRATADO, EFECTIVO
	};

	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;

	private Function<Tarea, Double> calculoPagoPorTarea;
	private Predicate<Tarea> puedeAsignarTarea;
	
	public Integer getCuil() {
		return this.cuil;
	}

	public Empleado (Integer cuil, String nombre, Double costoHora, Tipo tipo) {
		//Crea un nuevo objeto Empleado, inicializa las variables e implementa calculoPagoPorTarea y puedeAsignarTarea
		//según el tipo pasado por parámetro
		super();
		this.cuil = cuil;
		this.nombre = nombre;
		this.costoHora = costoHora;
		this.tipo = tipo;
		this.tareasAsignadas = new ArrayList<Tarea>();
		if(tipo == Tipo.CONTRATADO) {
			this.calculoPagoPorTarea = (Tarea t) ->{
				long diferencia = t.diferenciaDiasTarea();
				if(diferencia * 4 < t.getDuracionEstimada()) {
					return ((0.3 * t.getEmpleadoAsignado().costoHora) + t.getEmpleadoAsignado().costoHora) * diferencia * 4;
				}
				
				if(diferencia - (t.getDuracionEstimada() / 4) > 2) {
					return diferencia * 4 * t.getEmpleadoAsignado().costoHora * 0.75;
				}
				
				return diferencia * 4 * t.getEmpleadoAsignado().costoHora;
			};
			
			this.puedeAsignarTarea = (Tarea t) -> this.tareasAsignadas.stream().filter((Tarea t2 )->(t2.getFechaFin() == null)).count()<5;
		} else {
			this.calculoPagoPorTarea = (Tarea t) ->{
				long diferencia = t.diferenciaDiasTarea();
				if(diferencia * 4 < t.getDuracionEstimada()) {
					return (((0.2 * t.getEmpleadoAsignado().costoHora) + t.getEmpleadoAsignado().costoHora) * diferencia * 4);
				}
				return diferencia * 4 * t.getEmpleadoAsignado().costoHora;
			};
			
			this.puedeAsignarTarea = ((Tarea t) -> this.tareasAsignadas.stream().filter((Tarea t2 )->(t2.getFechaFin() == null))
					                                                   .map((Tarea t3) ->t3.getDuracionEstimada())
					                                                   .reduce((Integer acum, Integer hs) -> {return acum + hs;})
					                                                   .orElse(0) <= 15);
		}
		
	}

	public Double salario() {
		//Calcula el salario del empleado en base a la lista de tareas
		//Si esta finalizada, calcula en base a las horas que tardo en realizarla y calcula un extra en caso de ser necesario
		//Si no, calcula en base a las horas estimadas
		double aux = 0;
		int i = 0;
		while(i < this.tareasAsignadas.size()) {
			if(this.tareasAsignadas.get(i).getFechaFin() != null) {
				aux += this.calculoPagoPorTarea.apply(this.tareasAsignadas.get(i));
			} else {
				aux += this.costoTarea(this.tareasAsignadas.get(i));
			}
			i++;
		}
		return aux;
	}

	
	public Double costoTarea(Tarea t) {
		//Devuelve el costo de la tarea en base a la duracion estimada para la tarea
		return (this.costoHora * t.getDuracionEstimada());
	}

	public Boolean asignarTarea(Tarea t) {
		//Evalua si puede asignar la tarea según el tipo
		//Si se puede, la asigna, asigna el empleado a la tarea y devuelve true
		//Si no, devuelve false
		if(this.puedeAsignarTarea.test(t)) {
			this.tareasAsignadas.add(t);
			t.asignarEmpleado(this);
			return true;
		} else {
			return false;
		}

	}
	
	

	public void comenzar(Integer idTarea) {
		// busca la tarea en la lista de tareas asignadas
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de inicio la fecha y hora actual
		try {
			int i = 0;
			int excepcion = 1;
			while(i < this.tareasAsignadas.size()) {
				if(this.tareasAsignadas.get(i).getId().equals(idTarea)) {
					excepcion = 0;
					this.tareasAsignadas.get(i).setFechaInicio(LocalDateTime.now());
					return;
				}
				i++;
			}
			if(excepcion != 0) {
				throw new TareaNoEncontradaException("Error, el Empleado no tiene esta tarea asignada");
			}
		}catch(TareaNoEncontradaException e){
            System.out.println(e.getMessage());
		}
	}

	public void finalizar(Integer idTarea) {
		// busca la tarea en la lista de tareas asignadas
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		try {
			int i = 0;
			int excepcion = 1;
			while(i < this.tareasAsignadas.size()) {
				if(this.tareasAsignadas.get(i).getId().equals(idTarea)) {
					excepcion = 0;
					this.tareasAsignadas.get(i).setFechaFin(LocalDateTime.now());
				}
				i++;
			}
			if(excepcion != 0) {
				throw new TareaNoEncontradaException("Error, el Empleado no tiene esta tarea asignada");
			}
		}catch(TareaNoEncontradaException e){
            System.out.println(e.getMessage());
		}
	}

	public void comenzar(Integer idTarea, String fecha) {
		// busca la tarea en la lista de tareas asignadas
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		try {
			int i = 0;
			int excepcion = 1;
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
			while(i < this.tareasAsignadas.size()) {
				if(this.tareasAsignadas.get(i).getId().equals(idTarea)) {
					excepcion = 0;
					this.tareasAsignadas.get(i).setFechaInicio(LocalDateTime.parse(fecha, formato));
				}
				i++;
			}
			if(excepcion != 0) {
				throw new TareaNoEncontradaException("Error, el Empleado no tiene esta tarea asignada");
			}
		}catch(TareaNoEncontradaException e){
            System.out.println(e.getMessage());
		}
	}

	public void finalizar(Integer idTarea, String fecha) {
		// busca la tarea en la lista de tareas asignadas
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		try {
			int i = 0;
			int excepcion = 1;
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
			while(i < this.tareasAsignadas.size()) {
				if(this.tareasAsignadas.get(i).getId().equals(idTarea)) {
					excepcion = 0;
					this.tareasAsignadas.get(i).setFechaFin(LocalDateTime.parse(fecha, formato));
				}
				i++;
			}
			if(excepcion != 0) {
				throw new TareaNoEncontradaException("Error, el Empleado no tiene esta tarea asignada");
			}
		}catch(TareaNoEncontradaException e){
            System.out.println(e.getMessage());
		}
	}
	
	public List<Tarea> getTareas(){
		//Esto es para poder comprobar cosas en los test
		return this.tareasAsignadas;
	}
}

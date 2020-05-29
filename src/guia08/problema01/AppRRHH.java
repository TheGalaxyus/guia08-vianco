package guia08.problema01;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

import guia08.problema01.Empleado;
import guia08.problema01.Tarea;
import guia08.problema01.Empleado.Tipo;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public AppRRHH(){
		super();
		this.empleados = new ArrayList<Empleado>();
	}
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		Empleado e = new Empleado(cuil, nombre, costoHora, Tipo.CONTRATADO);
		this.empleados.add(e);
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista	
		Empleado e = new Empleado(cuil, nombre, costoHora, Tipo.EFECTIVO);
		this.empleados.add(e);
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) {
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista	
		int i = 0;
		boolean encontrado = false;
		while(i < this.empleados.size() && encontrado != true) {
			if(this.empleados.get(i).getCuil().equals(cuil)) {
				encontrado = true;
			}
			i++;
		}
		if (encontrado) {
			Tarea t = new Tarea(idTarea, descripcion, duracionEstimada);
			this.empleados.get(i-1).asignarTarea(t);
		} else {
			System.out.println("No existe empleado con el cuil ingresado");
		}
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
		int i = 0;
		int indice = 0;
		boolean encontrado = false;
		while(i < this.empleados.size() && encontrado != true) {
			if(this.empleados.get(i).getCuil().equals(cuil)) {
				encontrado = true;
				indice = i;
			}
			i++;
		}
		if(encontrado) {
			this.empleados.get(indice).comenzar(idTarea);
		}
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		// crear un empleado
		// agregarlo a la lista		
		int i = 0;
		int indice = 0;
		boolean encontrado = false;
		while(i < this.empleados.size() && encontrado != true) {
			if(this.empleados.get(i).getCuil().equals(cuil)) {
				encontrado = true;
				indice = i;
			}
			i++;
		}
		if(encontrado) {
			this.empleados.get(indice).finalizar(idTarea);
		}
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		Scanner entrada = null;
		int cuil;
		String nombre;
		Double costoHora;
		try {
			entrada = new Scanner(new File(nombreArchivo));
			entrada.useDelimiter(";");
				while (entrada.hasNextInt()) {
					cuil = entrada.nextInt();
					nombre = entrada.next();
					costoHora = entrada.nextDouble();
					this.agregarEmpleadoContratado(cuil, nombre, costoHora);
				}
			 	entrada.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();

		}
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		Scanner entrada = null;
		int cuil;
		String nombre;
		Double costoHora;
		try {
			entrada = new Scanner(new File(nombreArchivo));
			entrada.useDelimiter(";");
				while (entrada.hasNextInt()) {
					cuil = entrada.nextInt();
					nombre = entrada.next();
					entrada.useDelimiter("/n");
					costoHora = entrada.nextDouble();
					this.agregarEmpleadoEfectivo(cuil, nombre, costoHora);
					entrada.useDelimiter(";");
				}
			 	entrada.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();

		}
	}

	public void cargarTareasCSV(String nombreArchivo) {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
		Scanner entrada = null;
		int cuil;
		int id;
		String descripcion;
		int duracionEstimada;
		try {
			entrada = new Scanner(new File(nombreArchivo));
			entrada.useDelimiter(";");
			
				while (entrada.hasNextInt()) {
					id = entrada.nextInt();
					descripcion = entrada.next();
					duracionEstimada = entrada.nextInt();
					cuil = entrada.nextInt();
					this.asignarTarea(cuil, id, descripcion, duracionEstimada);
				}
			 	entrada.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();

		}
	}
	
	private void guardarTareasTerminadasCSV() {
		// guarda una lista con los datos de la tarea que fueron terminadas y todavía no fueron facturadas y el nombre y cuil del empleado que la finalizó en formato CSV 
		int i = 0;
		int j = 0;
		int id;
		String descripcion;
		int duracionEstimada;
		int cuilEmpleado;
		try(Writer fileWriter= new FileWriter("tareas.csv")) {
			try(BufferedWriter out = new BufferedWriter(fileWriter)){
				while(i < this.empleados.size()) {
					while(j < this.empleados.get(i).getTareas().size()) {
						if(this.empleados.get(i).getTareas().get(j).getFechaFin() != null && 
								this.empleados.get(i).getTareas().get(j).getFacturada() == false) {
							id = this.empleados.get(i).getTareas().get(j).getId();
							descripcion = this.empleados.get(i).getTareas().get(j).getDescripcion();
							duracionEstimada = this.empleados.get(i).getTareas().get(j).getDuracionEstimada();
							cuilEmpleado = this.empleados.get(i).getCuil();
							out.write(id + ";" + descripcion + ";"+ duracionEstimada + ";" + cuilEmpleado);
							
						} 
						
						j++;
					}
					i++;
				}
				out.close();
			} 
		} catch (IOException err) {
			err.printStackTrace();
		}
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
	
	public List<Empleado> getEmpleados(){
		return this.empleados;
	}
}

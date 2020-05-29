package guia08.problema01.test;

import static org.junit.Assert.*;



import org.junit.Test;

import guia08.problema01.AppRRHH;

public class AppRRHHTest {

	@Test
	public void test() {
		AppRRHH r1 = new AppRRHH();
		r1.agregarEmpleadoEfectivo(111113, "Tulio Triviño", 1200.00);
		r1.asignarTarea(111113, 616, "Tulio, estamos al aire", 12);
		r1.empezarTarea(111113, 616);
		r1.terminarTarea(111113, 616);
		System.out.println(r1.getEmpleados().get(0).getTareas().get(0).getFechaFin());
		AppRRHH r2 = new AppRRHH();
		r2.cargarEmpleadosContratadosCSV("EmpleadosContratados.txt");
		System.out.println(r2.getEmpleados().get(0).getCuil());
		Double idk = r1.facturar();
		r2.cargarTareasCSV("tareas.txt");
		System.out.println(r2.getEmpleados().get(0).getCuil());
		assertTrue(r2.getEmpleados().get(0).getTareas().get(0).getId().equals(616));
	}

}

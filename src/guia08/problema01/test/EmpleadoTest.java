package guia08.problema01.test;

import static org.junit.Assert.*;


import org.junit.Test;

import guia08.problema01.Empleado;
import guia08.problema01.Empleado.Tipo;
import guia08.problema01.Tarea;


public class EmpleadoTest {

	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.
	
	@Test
	public void testSalario() {
		Empleado e = new Empleado(11111, "Bruce Wayne", 1000.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(616, "Pasarse el Dark Souls sin morir",8);
		Tarea t2 = new Tarea(666, "Aprobar DIED", 10);
		if(e.asignarTarea(t1)) {
			if(e.asignarTarea(t2)) {
				e.comenzar(616, "10-05-2020 10:00");
				e.finalizar(616, "10-05-2020 11:00");
				e.comenzar(666);
				System.out.println(666);
				System.out.println(e.getTareas().get(1).getId());
				System.out.println(e.getTareas().get(1).getId() == 666);
				double resultadoObtenido = e.salario();
				double resultadoEsperado = (10000.00) + ((1000 + (1000 * 0.3)) * 4);
				assertEquals(resultadoEsperado, resultadoObtenido,0);
			}
		}
	}

	@Test
	public void testCostoTarea() {
		Empleado e = new Empleado(11111, "Bruce Wayne", 1000.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(69, "Vencer al Señor de la Luna",8);
		double resultadoObtenido = e.costoTarea(t1);
		double resultadoEsperado = t1.getDuracionEstimada() * 1000;
		assertEquals(resultadoEsperado, resultadoObtenido,0);
	}

	@Test
	public void testAsignarTarea() {
		Empleado e = new Empleado(11111, "Bruce Wayne", 1000.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(616, "Pattinson entrena ya, cabron",8);
		assertTrue(e.asignarTarea(t1));
	}

	@Test
	public void testComenzarInteger() {
		Empleado e = new Empleado(11111, "Bruce Wayne", 1000.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(616, "Reiniciar al DCEU",8);
		if(e.asignarTarea(t1)) {
			e.comenzar(616);
		}
		assertTrue(e.getTareas().get(0).getFechaInicio() != null);
	}

	@Test
	public void testFinalizarInteger() {
		Empleado e = new Empleado(11111, "Bruce Wayne", 1000.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(616, "Reiniciar al DCEU",8);
		if(e.asignarTarea(t1)) {
			e.comenzar(616);
			e.finalizar(616);
		}
		assertTrue(e.getTareas().get(0).getFechaFin() != null);
	}

	@Test
	public void testComenzarIntegerString() {
		Empleado e = new Empleado(11111, "Bruce Wayne", 1000.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(616, "Reiniciar al DCEU",8);
		if(e.asignarTarea(t1)) {
			e.comenzar(616, "10-05-2020 10:00");
		}
		assertTrue(e.getTareas().get(0).getFechaInicio() != null);
	}

	@Test
	public void testFinalizarIntegerString() {
		Empleado e = new Empleado(11111, "Bruce Wayne", 1000.00, Tipo.CONTRATADO);
		Tarea t1 = new Tarea(616, "Reiniciar al DCEU",8);
		if(e.asignarTarea(t1)) {
			e.comenzar(616, "10-05-2020 10:00");
			e.finalizar(616, "10-06-2020 17:00");
		}
		assertTrue(e.getTareas().get(0).getFechaFin() != null);
	}

}

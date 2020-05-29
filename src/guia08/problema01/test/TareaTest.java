package guia08.problema01.test;

import static org.junit.Assert.*;

import org.junit.Test;

import guia08.problema01.Empleado;
import guia08.problema01.Empleado.Tipo;
import guia08.problema01.Tarea;

public class TareaTest {

	@Test
	public void asignarEmpleadoTest() {
		Empleado e = new Empleado(111112, "Selina Kile", 10000.00, Tipo.EFECTIVO);
		Tarea t = new Tarea (616, "Willy, un tornado", 16);
		t.asignarEmpleado(e);
		assertTrue(t.getEmpleadoAsignado() != null);
	}

}
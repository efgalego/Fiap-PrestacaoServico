package fiap.scj.prestacao_servico.repositorio;

import java.util.ArrayList;
import java.util.Collection;

import fiap.scj.prestacao_servico.to.CidadaoTO;

public class CidadaoRepositorio {

	private static Collection<CidadaoTO> listaCidadao = new ArrayList<CidadaoTO>();
	private static Integer proxRegistro = 5000;
	
	public String adicionar(CidadaoTO to) {
		String registro = String.format("%06d", proxRegistro++);
		to.setRegistro(registro);		
		listaCidadao.add(to);
		return registro;
	}
}

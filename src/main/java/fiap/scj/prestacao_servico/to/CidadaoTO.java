package fiap.scj.prestacao_servico.to;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("CidadaoTO")
public class CidadaoTO {

	private String registro;
	private String nomeCidadao;
	private String telefone;
	private String email;
	private String observacoes;

	public CidadaoTO(String registro, String nomeCidadao, String telefone, String email, String observacoes) {
		super();
		this.registro = registro;
		this.nomeCidadao = nomeCidadao;
		this.telefone = telefone;
		this.email = email;
		this.observacoes = observacoes;
	}

	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getNomeCidadao() {
		return nomeCidadao;
	}

	public void setNomeCidadao(String nomeCidadao) {
		this.nomeCidadao = nomeCidadao;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public String toString() {
		return "DadosCidadaoTO [registro=" + registro + ", nomeCidadao=" + nomeCidadao + ", telefone=" + telefone + ", email=" + email + ", observacoes=" + observacoes + "]";
	}
	
}

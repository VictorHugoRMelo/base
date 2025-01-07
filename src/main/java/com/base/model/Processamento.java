package com.base.model;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.base.enuns.ProcessamentoTipoEnum;
import com.base.utils.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="processamento")
public class Processamento {

	@Id
	@SequenceGenerator(name = "processamento_seq", sequenceName = "processamento_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processamento_seq")
	@Column(unique = true, nullable = false, name="id")
	private Long id;
	
	@Column(name = "nome_arquivo")
	private String nomeArquivo;

	@Column(name = "contet_type")
	private String contetType;

	@CreatedDate
	@Column(name = "data_start")
	private LocalDateTime dataStart;

	@Column(name = "data_inicio")
	private LocalDateTime dataInicio;
	
	@Column(name = "data_fim")
	private LocalDateTime dataFim;
	
	@Column(name = "tipo_processamento")
	private Integer tipoProcessamento;
	
	@Column(name = "executado")
	private Boolean executado;
	
	@Column(name = "reprocessar")
	private Boolean reprocessar;
	
	@Column(name = "qtd_reprocessar")
	private Integer qtdReprocessar;
	
	@Column(name = "qtd_reprocessado")
	private Integer qtdReprocessado;
	
	@Column(name = "resultado")
	private String resultado;
	
	@Column(name = "resultado_amigavel", length = 200)
	private String resultadoAmigavel;
	
	@Column(name = "parametro", length = 200)
	private String parametro;
	
	@Column(name = "arquivo_a_processar", length = 200)
	private String arquivoAProcessar;
	
	@Column(name = "arquivo_processado", length = 200)
	private String arquivoProcessado;

	@Column(name = "tamanho", length = 200)
	private String tamanho;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	public String getDescricaoProcessamento() {
		if(Objects.nonNull(tipoProcessamento)) {
			return ProcessamentoTipoEnum.getNomePeloID(tipoProcessamento);
		}
		return "";
	}

	public String getNomeArquivo() {
		if(Objects.nonNull(arquivoAProcessar)) {
			return Utils.extrairNomeParametro(arquivoAProcessar);
		}
		return "";
	}
	
	public String getArquivoAtual() {
		return Objects.isNull(getArquivoProcessado()) ? getArquivoAProcessar() : getArquivoProcessado();
	}
	
	public String getTamanhoArquivo() {
		if(Objects.nonNull(parametro)) {
			return parametro.split("#")[1];
		}
		return "";
	}
	
	public Boolean getExibeBTCancelar() {
		if(!executado) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Boolean getExibeBTReexecutar() {
		if(executado && "SUCESSO".equals(resultadoAmigavel)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
}

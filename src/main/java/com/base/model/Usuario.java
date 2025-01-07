package com.base.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 3321741296213606231L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "funcao")
    private String funcao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "cadastro_confirmado", nullable = false)
    private Boolean cadastroConfirmado;

    @Column(name = "id_confirmacao_cadastro")
    private String idConfirmacaoCadastro;

    @CreatedDate
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;
}
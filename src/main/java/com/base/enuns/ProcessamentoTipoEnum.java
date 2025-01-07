package com.base.enuns;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum ProcessamentoTipoEnum {

	INERTE(0, false, ""),
	ARQUIVO_ZT10013(1, false, "nome_arquivo;cod_id_site;cod_concessionaria_sap;ind_possibilidade_pagamento;cod_concessionaria_pagamento;cod_centro_custo_pagamento;"
    		+ "cod_local_negocio_sap;cod_unidade_consumidora_sap;ind_icms;val_perc_icms;num_cep_unidade_consumidora;sgl_uf_unidade_consumidora;dsc_rua_unidade_consumidora;"
    		+ "dsc_comp_unidade_consumidora;dsc_bairro_unidade_consumidora;cod_ordem_interna;cod_area_unidade_consumidora;dsc_tipo_tecnologia_site;dsc_tipo_site_medidor;"
    		+ "dsc_cidade_unidade_consumidora;ind_controle_area_adm;ind_controle_area_engenharia;ind_controle_area_energia;ind_inatividade_unidade;dat_inicio_unidade_consumidora;"
    		+ "dat_fim_unidade_consumidora;num_cnpj_concessionaria;dsc_concessionaria_energia;num_medidor_instalado_anterior;dsc_tipo_pagamento;cod_tipo_pagamento;"
    		+ "dsc_banco_pagamento;ind_imposto_tusd;cod_emissao_controle_sap;cod_vencimento_controle_sap;dsc_tipo_tensao_estabelec;dsc_tipo_classe_estabelec;"
    		+ "dsc_tipo_energia_estabelec;dat_migracao_concessionaria;dsc_usina_geradora_energia;num_usina_fornecedor_sap;num_cnpj_usina_fornecedor_sap;dsc_usina_fornecedor;"
    		+ "cod_cadastro_sistema_smartplan;ind_unidade_gera_energia;num_centro_custo_lucro;dat_geracao_arquivo;hor_geracao_arquivo;dat_ref;dat_referencia"),
    ARQUIVO_PDP(2, false, "Imagem|Id|Contrato|Empresa|Endereco|Cep|Municipio|Uf|Edificio|Sigla|Instalacao|Fornecedor|FornecedorSap|MedidorEnergia|Unidade|Grupo|"
    		+ "NumeroTarifacao|NumeroNotaFiscal|Data de Competência|DataEmissao|DataVencimento|DataLeitura Atual|ConsumoForaPontaIndutivoRegistrado|"
    		+ "ConsumoForaPontaCapacitivoRegistrado|DemandaPontaRegistrado|DemandaForaPontaIndutivaRegistrada|DemandaForaPontaCapacitivaRegistrada|"
    		+ "DemandaContratadaPonta|DemandaContratadaForaPonta|UFERPontaEnergiaReativaExcedentePontaFaturada|UFERForaPontaEnergiaReativaExcedentePontaFaturada|"
    		+ "UFDRPontaDemandaReativaExcedentePontaFaturada|UFDRForaPontaDemandaReativaExcedentePontaFaturada|DemandaPontaFaturada|"
    		+ "DemandaForaPontaFaturada|UltrapassagemDemandaPontaFaturada|UltrapassagemDemandaForaPontaFaturada|ConsumoEnergiaAtivaPontaFaturado|"
    		+ "ConsumoEnergiaAtivaForaPontaFaturado|R$ DemandaPonta|R$ DemandaForaPonta|R$ UltrapassagemDemandaPonta|R$ UltrapassagemDemandaForaPonta|"
    		+ "R$ ConsumoEnergiaAtivaPonta|R$ ConsumoEnergiaAtivaForaPonta|R$ UFERPontaEnergiaReativaExcedentePonta|R$ UFERForaPontaEnergiaReativaExcedentePonta|"
    		+ "R$ UFDRPontaDemandaReativaExcedentePonta|R$ UFDRForaPontaDemandaReativaExcedentePonta|R$ BaseCalculoICMS|% IcmsAliquota|% PisAliquota|"
    		+ "% CofinsAliquota|R$ ICMS|R$ PIS|R$ COFINS|R$ EncargosConexao|R$ PenalDicDmicFicDicri|R$ Multa|R$ JurosMora|R$ AtualizacaoMonetaria|"
    		+ "R$ CosipCipContribuicaoIluminacaoPub|R$ Descontos|R$ Ressarcimentos|R$ Outros|R$ Total a Pagar|Alerta de Desvio de Consumo|"
    		+ "Desligamento Programado|Dias|Data de Leitura Anterior|Media KWH|Total Auditado|Diferenca Ponta|Diferenca F.Ponta|"
    		+ "Faturamento|Alerta de ultrapassagem de demanda|Observação|CNPJ da Concessionária|Centro de Custo|Ordem Interna|"
    		+ "ConsumoPontaRegistrado|ConsumoForaPontaRegistrado|Energia injetada Ponta|Energia injetada Fora Ponta|Energia compensada Ponta|"
    		+ "R$ Crédito acumulado|Tipo de pagamento|Realizado o Processo de Auditoria|Fatura com pendência|Alerta de Consumo Mínimo|FCP|"
    		+ "Consumo do medidor - Leitura Atual|Consumo do medidor - Anterior|R$ Tarifa TUSD s/Imposto|R$ Tarifa TUSD c/Imposto|"
    		+ "R$ TUSD ICMS|R$ TUSD PIS|R$ TUSD COFINS|Alerta de Não Faturamento|Status da UC|InstalacaoAntiga|Descrição de Site|Tipo|"
    		+ "Nome da Usina|Fornecedor Usina|Fora Ponta - Tarifa TUSD s/Imposto|Fora Ponta - Tarifa TE s/Imposto|Fora Ponta - Tarifa TUSD c/Imposto|"
    		+ "Fora Ponta - Tarifa TE c/Imposto|Ponta - Tarifa TUSD s/Imposto|Ponta - Tarifa TE s/Imposto|Ponta - Tarifa TUSD c/Imposto|"
    		+ "Ponta - Tarifa TE c/Imposto|Dias Bandeira Verde|R$ Amarela|Dias Bandeira Amarela|R$ Vermelha P.1|Dias Bandeira Vermelha P.1|"
    		+ "R$ Vermelha P.2|Dias Bandeira Vermelha P.2|R$ Escassez Hídrica|Dias Bandeira E. Hídrica|ACL Total|R$ ACL s/Imposto|Injeção Total|"
    		+ "Data Envio|Crédito DIC|Crédito DICRI|Crédito DMIC|Crédito DVAM|Crédito FEC|Crédito FIC|Crédito Outro|Crédito LTUSD|Crédito PJEEN|Crédito Devolvido|"
    		+ "Consumo Intermediário KWH|Consumo Intermediário Tarifa|Consumo Intermediário Valor|Consumo Reservado KWH|Consumo Reservado Tarifa|"
    		+ "Consumo Reservado Valor|Código de Barras|Bandeira Verde|Tarifa Verde|Valor Consumo Verde|Bandeira Amarela|Tarifa Amarela|Valor Consumo Amarela|"
    		+ "Bandeira Vermelha|Tarifa Vermelha|Valor Consumo Vermelha|Classe ZT10013|Consumo Total Faturado|CNPJ Destinatário - Origem|Cód. Débito Automático - Origem|"
    		+ "Histórico Última Competência|Histórico Último Valor|Consumo Medidor Total|Energia Compensada Fora Ponta|Energia Compensada Intermediária|Saldo Acumulado|"
    		+ "Energia Injetada Intermediária|R$ Tarifa TE Sem Imposto|R$ Tarifa TE Com Imposto|Tarifa Energia Injetada Ponta|Tarifa Energia Injetada Fora Ponta|"
    		+ "Custo de Disponibilidade|Data Próxima Leitura|TarifaGDTE - Ponta|TarifaGDTE - Fora Ponta|TarifaGDTUSD - Ponta|TarifaGDTUSD - Fora Ponta|"
    		+ "Descrição - Custo Disponibilidade|Data Protocolo|Energia Injentada - Observação|Energia Compensada - Observação|Importe - Somar/Diminuir");

	private final Integer codigo;
    private final String stringValidacao;
    private final List<Integer> pais;
    private final Boolean reprocessarGeral;

    ProcessamentoTipoEnum(Integer codigo, Boolean reprocessarGeral, String nome, ProcessamentoTipoEnum... pais) {
        this.codigo = codigo;
		this.stringValidacao = nome;
        this.pais = Arrays.stream(pais)
                .map(p -> p.getCodigo())
                .collect(Collectors.toList());
        this.reprocessarGeral = reprocessarGeral;
    }

    public static String getNomePeloID(int id) {
        return Arrays.stream(values())
                .filter(p -> p.getCodigo() == id)
                .map(Enum::name)
                .findFirst()
                .orElse(null);
    }

    public static ProcessamentoTipoEnum getPeloID(int id) {
        return Arrays.stream(values())
                .filter(p -> p.getCodigo() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<ProcessamentoTipoEnum> getProcessamentosDependentes(int id) {
        return Arrays.stream(values())
                .filter(tipo -> tipo.getPais().contains(id))
                .collect(Collectors.toList());
    }
}


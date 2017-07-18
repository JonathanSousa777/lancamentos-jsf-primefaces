package com.algaworks.erp.controller;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.model.RamoAtividade;
import com.algaworks.erp.model.TipoEmpresa;
import com.algaworks.erp.repository.Empresas;
import com.algaworks.erp.repository.RamoAtividades;
import com.algaworks.erp.service.CadastroEmpresaService;
import com.algaworks.erp.util.FacesMessages;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class GestaoEmpresaBean implements Serializable {

    @Inject
    FacesMessages messages;

    @Inject
    private Empresas empresas;

    @Inject
    private CadastroEmpresaService cadastroEmpresaService;

    @Inject
    private RamoAtividades ramoAtividades;

    private List<Empresa> listaEmpresas;
    private String termoPesquisa;
    private Converter ramoAtividadeConverter;
    private Empresa empresa;

    public void prepararNovaEmpresa() {
        empresa = new Empresa();
    }
    
    public void prepararEdiciao() {
        ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));
    }

    public void salvar() {
        cadastroEmpresaService.salvar(empresa);

        atualizarRegistros();

        RequestContext.getCurrentInstance().update(Arrays.asList("frm:empresaDataTable", "frm:messages"));

        messages.info("Empresa salva com sucesso!");
    }
    
    public void excluir() {
        cadastroEmpresaService.excluir(empresa);
        empresa = null;
        
        atualizarRegistros();
        
        messages.info("Empresa excluída com sucesso!");
    }

    public void todasEmpresas() {
        listaEmpresas = empresas.todas();
    }

    public void pesquisar() {
        listaEmpresas = empresas.pesquisar(termoPesquisa);

        if (listaEmpresas.isEmpty()) {
            messages.info("Sua consulta não retornou registros.");
        }
    }
    
    public void atualizarRegistros() {
        if(jaHouvePesquisa()) {
            pesquisar();
        } else {
            todasEmpresas();
        }
    }

    public boolean jaHouvePesquisa() {
        return termoPesquisa != null && !"".equals(termoPesquisa);
    }

    public TipoEmpresa[] getTiposEmpresa() {
        return TipoEmpresa.values();
    }

    public List<RamoAtividade> completarRamoAtividade(String termo) {
        List<RamoAtividade> listaRamoAtividades = ramoAtividades.pesquisar(termo);

        ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);

        return listaRamoAtividades;
    }
    
    public boolean isEmpresaSelecionada() {
        return empresa != null && empresa.getId() != null;
    }

    public List<Empresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public Converter getRamoAtividadeConverter() {
        return ramoAtividadeConverter;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
package com.algaworks.erp.service;

import com.algaworks.erp.model.Empresa;
import com.algaworks.erp.repository.Empresas;
import com.algaworks.erp.util.Transacional;
import java.io.Serializable;
import javax.inject.Inject;

public class CadastroEmpresaService implements Serializable{

    @Inject
    private Empresas empresas;

    @Transacional
    public void salvar(Empresa empresa) {
        this.empresas.guardar(empresa);
    }

    @Transacional
    public void excluir(Empresa empresa) {
        this.empresas.remover(empresa);
    }
}

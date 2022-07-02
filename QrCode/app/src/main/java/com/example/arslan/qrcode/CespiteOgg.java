package com.example.arslan.qrcode;

import java.util.Date;

/**
 * Created by Arslan on 06/06/2017.
 */

public class CespiteOgg
{

        String NumInventario;
        String NomeCespite;
        String DtCatalogazione;
        String Aula;
        String NomeUser;

    public CespiteOgg(String numInventario, String nomeCespite, String dtCatalogazione, String aula, String nomeUser) {
        NumInventario = numInventario;
        NomeCespite = nomeCespite;
        DtCatalogazione = dtCatalogazione;
        Aula = aula;
        NomeUser = nomeUser;
    }

    public String getNumInventario()
    {
        return NumInventario;
    }

    public void setNumInventario(String numInventario)
    {
        NumInventario = numInventario;
    }

    public String getNomeCespite()
    {
        return NomeCespite;
    }

    public void setNomeCespite(String nomeCespite)
    {
        NomeCespite = nomeCespite;
    }

    public String getDtCatalogazione()
    {
        return DtCatalogazione;
    }

    public void setDtCatalogazione(String dtCatalogazione)
    {
        DtCatalogazione = dtCatalogazione;
    }

    public String getAula()
    {
        return Aula;
    }

    public void setAula(String aula)
    {
        Aula = aula;
    }

    public String getNomeUser()
    {
        return NomeUser;
    }

    public void setNomeUser(String nomeUser)
    {
        NomeUser = nomeUser;
    }
}

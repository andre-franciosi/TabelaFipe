package com.andre.TabelaFipe.principal;

import com.andre.TabelaFipe.models.Dados;
import com.andre.TabelaFipe.service.ConsumoAPI;
import com.andre.TabelaFipe.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class Principal {

    Scanner leitura = new Scanner(System.in);

    public void exibeMenu() {

        var URL_BASE = "https://parallelum.com.br/fipe/api/v1";
        var consumoAPI = new ConsumoAPI();

        var menu = """
                ***ESCOLHA UMA OPÇÃO DE VEÍCULO****
                
                CARRO
                MOTO
                CAMINHAO
                
                DIGITE A OPÇÃO DESEJADA
                """;

        System.out.println(menu);

        var tipo = leitura.nextLine();

        String endereco = "";
        if (tipo.toLowerCase().contains("car")) {
            endereco = URL_BASE + "/carros/marcas";
        } else if (tipo.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "/motos/marcas";
        } else if (tipo.toLowerCase().contains("cam")) {
            endereco = URL_BASE + "/caminhoes/marcas";
        } else {
            System.out.println("OPÇÃO INVÁLIDA");
            return;
        }

        var json = consumoAPI.obterDados(endereco);

        ConverteDados conversor = new ConverteDados();
        List<Dados> marcas = conversor.obterLista(json, Dados.class);

        String listaFormatada = conversor.formatarLista(marcas);
        System.out.println("\n Lista de Marcas:\n" + listaFormatada);

        System.out.println("\n Digite o código da marca: ");
        var marca = leitura.nextLine();

        endereco = endereco + "/" + marca + "/modelos";
        json = consumoAPI.obterDados(endereco);

        var jsonModelos = json.substring(json.indexOf("["));
        List<Dados> modelos = conversor.obterLista(jsonModelos, Dados.class);

        String modelosFormatado = conversor.formatarLista(modelos);
        System.out.println("\n Lista de Modelos: \n" + modelosFormatado);

        System.out.println("\n Digite o código do modelo: ");
        var modelo = leitura.nextLine();

        endereco = endereco + "/" + modelo + "/anos";
        json = consumoAPI.obterDados(endereco);

        List<Dados> anos = conversor.obterLista(json, Dados.class);

        String anosFormatado = conversor.formatarLista(anos);
        System.out.println("\n Lista de Anos: \n" + anosFormatado);

        // Para cada ano, faça uma requisição e exiba os dados do carro
        String finalEndereco = endereco;
        anos.stream()
                .map(ano -> {
                    var anoCodigo = ano.getCodigo();
                    var anoEndereco = finalEndereco + "/" + anoCodigo;
                    return consumoAPI.obterDados(anoEndereco);
                })
                .forEach(anoJson -> System.out.println("Dados do carro: \n" + anoJson));
        }
    }


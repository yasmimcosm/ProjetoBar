package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Configuracao;
import com.example.demo.yasmimJose.repository.ConfiguracaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracaoService {

    private final ConfiguracaoRepository repo;

    public ConfiguracaoService(ConfiguracaoRepository repo) {
        this.repo = repo;
    }

    public Configuracao salvar(Configuracao config) {
        // Busca o registro existente (id=1)
        Optional<Configuracao> existente = repo.findById(1L);
        if (existente.isPresent()) {
            Configuracao atual = existente.get();
            // Só atualiza campos que não são nulos
            if (config.getPrecoCouvert() != null) atual.setPrecoCouvert(config.getPrecoCouvert());
            if (config.getGorjetaComida() != null) atual.setGorjetaComida(config.getGorjetaComida());
            if (config.getGorjetaBebida() != null) atual.setGorjetaBebida(config.getGorjetaBebida());
            return repo.save(atual);
        } else {
            // Se não existir, cria um novo registro
            config.setId(1L);
            return repo.save(config);
        }
    }


    public Configuracao buscarAtual() {
        return repo.findAll().stream().findFirst().orElse(null);
    }
}

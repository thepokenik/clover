package com.API.getUser.Controller;

import com.API.getUser.DTO.DadosAtualizacaoUsers;
import com.API.getUser.DTO.DadosListagemUsers;
import com.API.getUser.DTO.DadosUserNovo;
import com.API.getUser.users.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manipulação de usuários.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("users")
public class UserController {

    @Autowired
    private UsersRepository repository;

    /**
     * Retorna uma página de usuários com base na paginação fornecida.
     *
     * @param paginacao Configurações de paginação.
     * @return ResponseEntity contendo a página de usuários.
     */
    @GetMapping
    public ResponseEntity<Page<DadosListagemUsers>> listar(@PageableDefault(size = 10, sort = {"username"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemUsers::new);
        return ResponseEntity.ok(page);
    }

    /**
     * Atualiza as informações de um usuário com base nos dados fornecidos.
     *
     * @param dados Dados de atualização do usuário.
     * @return ResponseEntity contendo os dados atualizados do usuário.
     */
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsers dados) {
        var user = repository.getReferenceById(dados.id_User());
        user.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosUserNovo(user));
    }

    /**
     * Retorna os detalhes de um usuário com base no ID fornecido.
     *
     * @param id_User ID do usuário.
     * @return ResponseEntity contendo os detalhes do usuário.
     */
    @GetMapping("/{id_User}")
    public ResponseEntity detalhar(@PathVariable Long id_User) {
        var user = repository.getReferenceById(id_User);
        return ResponseEntity.ok(new DadosUserNovo(user));
    }
}

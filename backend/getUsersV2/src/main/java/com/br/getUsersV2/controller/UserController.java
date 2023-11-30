package com.br.getUsersV2.controller;

import com.br.getUsersV2.DTO.DadosListagemUsers;
import com.br.getUsersV2.users.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UsersRepository repository;
    @GetMapping
    public ResponseEntity<Page<DadosListagemUsers>> listar(@PageableDefault(size = 10, sort = {"username"}) Pageable paginacao) {
        var page = repository.findAll(paginacao).map(DadosListagemUsers::new);
        return ResponseEntity.ok(page);
    }
    @Transactional
    @DeleteMapping
    public ResponseEntity deletar (@RequestBody @Valid UsersRepository repository){
        repository.deleteAll();
        return ResponseEntity.ok().build();
    }
}

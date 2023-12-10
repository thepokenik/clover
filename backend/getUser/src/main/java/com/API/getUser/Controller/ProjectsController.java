package com.API.getUser.Controller;

import com.API.getUser.DTO.AutenticarProjects;
import com.API.getUser.DTO.DadosAtualizacaoProject;
import com.API.getUser.DTO.DadosListagemProjects;
import com.API.getUser.DTO.DadosProjectsNovo;
import com.API.getUser.infra.security.errorNotFoundId;
import com.API.getUser.projects.Projects;
import com.API.getUser.projects.ProjectsRepository;
import com.API.getUser.users.Users;
import com.API.getUser.users.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    private ProjectsRepository repository;
    @Autowired
    private UsersRepository repositoryUser;

    @GetMapping
    public ResponseEntity<Page<DadosListagemProjects>> getProjects(@PageableDefault(size = 10, sort = {"projectName"}) Pageable paginacao) {

        Page<DadosListagemProjects> projectPage = repository.findAll(paginacao)
                .map(project -> {

                    Users user = project.getUser();
                    Long userId = user != null ? user.getId_User() : null;

                    return new DadosListagemProjects(
                            project.getIdProjects(),
                            project.getProjectName(),
                            project.getCreationDate(),
                            project.getProjectProgress(),
                            project.getProjectDescription(),
                            project.getProjectReadme(),
                            project.getProjectFile(),
                            userId
                    );
                });

        return ResponseEntity.status(200).body(projectPage);
    }

    @GetMapping("/{idProjects}")
    public ResponseEntity getProjects(@PathVariable Long idProjects) {
        try {
            Optional<Projects> optionalProject = repository.findById(idProjects);

            if (optionalProject.isPresent()) {
                Projects project = optionalProject.get();
                return ResponseEntity.ok(new DadosProjectsNovo(project));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new errorNotFoundId("Projeto não encontrado!", idProjects));
            }
        } catch (Exception ex) {
            String mensagemErro = "Erro ao pesquisar projeto: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
        }
    }


    @PostMapping("/newRepository")
    @Transactional
    public ResponseEntity<DadosListagemProjects> newRepository(@RequestBody @Valid AutenticarProjects dados) {

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Projects newProject = new Projects(
                dados.projectName(),
                dados.creationDate(),
                dados.projectProgress(),
                dados.projectDescription(),
                dados.projectReadme(),
                dados.projectFile(),
                user
        );

        Projects savedProject = repository.save(newProject);

        DadosListagemProjects responseDTO = new DadosListagemProjects(
                savedProject.getIdProjects(),
                savedProject.getProjectName(),
                savedProject.getCreationDate(),
                savedProject.getProjectProgress(),
                savedProject.getProjectDescription(),
                savedProject.getProjectReadme(),
                savedProject.getProjectFile(),
                user.getId_User()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{idProjects}")
    @Transactional
    public ResponseEntity deletarProjects(@PathVariable Long idProjects) {
        try {
            // Obter a entidade diretamente usando findById
            var optionalProject = repository.findById(idProjects);

            if (optionalProject.isPresent()) {
                repository.deleteById(idProjects);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new errorNotFoundId("Projeto não encontrado!", idProjects));
            }
        } catch (Exception ex) {
            String mensagemErro = "Erro ao excluir o projeto: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProject(@RequestBody @Valid DadosAtualizacaoProject dados) {
        Long projectId = dados.idProject();

        if (projectId == null) {
            // Lógica para lidar com ID nulo, se necessário
            return ResponseEntity.badRequest().body("ID do projeto não pode ser nulo");
        }

        try {
            var project = repository.getReferenceById(projectId);
            project.atualizarProject(dados);
            return ResponseEntity.ok(new DadosProjectsNovo(project));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new errorNotFoundId("Projeto não encontrado!", projectId));
        } catch (Exception ex) {
            String mensagemErro = "Erro ao atualizar o projeto: " + ex.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensagemErro);
        }

    }


   /* private ResponseEntity<Long> getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails + " ok!");
        return ResponseEntity.ok(((Users) userDetails).getId_User());
    }


    private Users associateUserToProject(Long userId) {
        Users user = repositoryUser.findById(userId).orElse(null);

        return user;
    }*/

}
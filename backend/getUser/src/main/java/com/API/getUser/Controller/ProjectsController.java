package com.API.getUser.Controller;

import com.API.getUser.DTO.AutenticarProjects;
import com.API.getUser.DTO.DadosAtualizacaoProject;
import com.API.getUser.DTO.DadosListagemProjects;
import com.API.getUser.DTO.DadosProjectsNovo;
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
        System.out.println(idProjects);
        var project = repository.getReferenceById(idProjects);

        if (project != null) {
            return ResponseEntity.ok(new DadosProjectsNovo(project));
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping("/newRepository")
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
    public ResponseEntity deletarProjects(@PathVariable Long idProjects){
        var delProjects= repository.findById(idProjects);
        if(repository.findById(idProjects).isEmpty()){
        repository.deleteById(idProjects);
        return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
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
            // Lógica para lidar com o caso em que a entidade não foi encontrada
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            // Lógica para lidar com outras exceções, se necessário
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o projeto");
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
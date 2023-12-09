package com.API.getUser.projectController;

import com.API.getUser.projectDTO.AutenticarProjects;
import com.API.getUser.projectDTO.DadosListagemProjects;
import com.API.getUser.userProjects.Projects;
import com.API.getUser.userProjects.ProjectsRepository;
import com.API.getUser.users.Users;
import com.API.getUser.users.UsersRepository;
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
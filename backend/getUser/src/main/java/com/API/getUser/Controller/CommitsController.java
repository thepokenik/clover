package com.API.getUser.Controller;

import com.API.getUser.DTO.DadosListagemCommit;
import com.API.getUser.DTO.DadosCommitNovo;
import com.API.getUser.models.commit.Commits;
import com.API.getUser.models.commit.CommitsRepository;
import com.API.getUser.models.projects.Projects;
import com.API.getUser.models.projects.ProjectsRepository;
import com.API.getUser.models.users.Users;
import com.API.getUser.models.users.UsersRepository;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping("projects/commits")
public class CommitsController {
    @Autowired
    private CommitsRepository repository;
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemCommit>> getCommit(@PageableDefault(size = 10, sort = {"commitDate"})Pageable paginacao){
        Page<DadosListagemCommit> commitPage = repository.findAll(paginacao)
                .map(commits -> {
                    Users users = commits.getUser();
                    Long userId = users != null ? users.getId_User() : null;

                    Projects projects = commits.getProject();
                    Long projectId = projects!= null ? projects.getIdProjects() : null;

                    return new DadosListagemCommit(
                            commits.getIdCommit(),
                            commits.getCommitMessage(),
                            commits.getCommitDate(),
                            projectId,
                            userId
                    );
                });
        return ResponseEntity.status(HttpStatus.OK).body(commitPage);
    }
    @PostMapping("/newcommit/{idProjects}")
    @Transactional
    public ResponseEntity newCommit(@PathVariable Long idProjects, @RequestBody @Valid DadosCommitNovo dados) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var project = projectsRepository.getReferenceById(idProjects);

        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }

        Commits newCommit = new Commits(
                dados.commitMessage(),
                LocalDateTime.now(),
                project,
                user
        );

        Commits saveCommit = repository.save(newCommit);

        DadosListagemCommit response = new DadosListagemCommit(
                saveCommit.getIdCommit(),
                saveCommit.getCommitMessage(),
                saveCommit.getCommitDate(),
                project.getIdProjects(),
                user.getId_User()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /*@GetMapping("/{projectId}/commits")
    public ResponseEntity<List<Commits>> getCommitsForProject(@PathVariable Long projectId) {
        Optional<Projects> projectOptional = projectsRepository.findById(projectId);

        if (projectOptional.isPresent()) {
            Projects project = projectOptional.get();
            List<Commits> commits = project.getCommits(); // Supondo que haja um método getCommits() na classe Projects
            return ResponseEntity.ok(commits);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

}

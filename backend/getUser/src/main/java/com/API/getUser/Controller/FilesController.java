package com.API.getUser.Controller;

import com.API.getUser.DTO.DadosAtualizacaoFiles;
import com.API.getUser.DTO.DadosFilesNovo;
import com.API.getUser.DTO.DadosListagemFiles;
import com.API.getUser.models.commit.Commits;
import com.API.getUser.models.commit.CommitsRepository;
import com.API.getUser.models.files.FileRepository;
import com.API.getUser.models.files.Files;
import com.API.getUser.models.projects.Projects;
import com.API.getUser.models.projects.ProjectsRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("projects/files")
public class FilesController {

    @Autowired
    private FileRepository repository;
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private CommitsRepository commitsRepository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemFiles>> getFilesForProjects(@PageableDefault(size = 10, sort = {"commit"})Pageable paginacao){
        Page<DadosListagemFiles> filePage = repository.findAll(paginacao)
                .map(files -> {
                    Commits commits = files.getCommit();
                    String  commitsMessage = commits != null ? commits.getCommitMessage() : null;

                    Projects projects = files.getProject();
                    Long projectId = projects!= null ? projects.getIdProjects() : null;

                    return new DadosListagemFiles(
                            files.getIdFile(),
                            files.getFileName(),
                            files.getFileType(),
                            files.getFileContent(),
                            projectId,
                            commitsMessage
                    );
                });
        return ResponseEntity.status(HttpStatus.OK).body(filePage);
    }

    @GetMapping("/{idProjects}/files/{idFile}/content")
    public ResponseEntity<byte[]> getFileContent(
            @PathVariable Long idProjects,
            @PathVariable Long idFile) {

        // Verifica se o projeto existe
        var project = projectsRepository.getReferenceById(idProjects);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // Verifica se o arquivo existe e pertence ao projeto
        var file = repository.findById(idFile).orElse(null);
        if (file == null || !file.getProject().equals(project)) {
            return ResponseEntity.notFound().build();
        }

        // Obtém o conteúdo do arquivo
        byte[] fileContent = file.getFileContent();

        // Retorna o conteúdo do arquivo
        return ResponseEntity.ok()
                .header("Content-Type", file.getFileType())
                .body(fileContent);
    }

    @PostMapping("/{idProjects}/uploadFile")
    @Transactional
    public ResponseEntity newFile(
            @PathVariable Long idProjects,
            @RequestParam("file") MultipartFile file,
            @RequestPart @Valid DadosFilesNovo dados) {

        var project = projectsRepository.getReferenceById(idProjects);

        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }

        var commits = project.getCommits();
        if (commits.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No commits found for the project");
        }

        var lastCommit = commits.get(commits.size() - 1);

        // Converte os bytes do arquivo em um array de bytes
        byte[] fileContent = null;
        try {
            fileContent = file.getBytes();
        } catch (IOException e) {
            // Trate a exceção, se necessário
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read file content");
        }

        Files newfiles = new Files(
                dados.fileName(),
                file.getContentType(),  // Obtém o tipo de arquivo do MultipartFile
                fileContent,
                project,
                lastCommit
        );

        Files saveFiles = repository.save(newfiles);

        DadosListagemFiles response = new DadosListagemFiles(
                saveFiles.getIdFile(),
                saveFiles.getFileName(),
                saveFiles.getFileType(),
                saveFiles.getFileContent(),
                project.getIdProjects(),
                lastCommit.getCommitMessage()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idProjects}/updatefiles")
    public ResponseEntity commitFiles(@PathVariable Long idProjects, @RequestBody @Valid DadosAtualizacaoFiles dados) {

        var project = projectsRepository.getReferenceById(idProjects);

        if (project == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
        }

        Long fileId = dados.idFile();
        if (fileId == null) {
            return ResponseEntity.notFound().build();
        }

        // Verifica se o arquivo pertence ao projeto
        Files fileToUpdate = repository.findById(fileId).orElse(null);

        if (fileToUpdate == null || !fileToUpdate.getProject().equals(project)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found in the specified project");
        }

        // Obtém a lista de commits associada ao projeto
        List<Commits> commits = project.getCommits();

        // Verifica se há algum commit
        if (commits.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No commits found for the project");
        }

        // Obtém o último commit
        Commits lastCommit = commits.get(commits.size() - 1);

        // Atualiza o conteúdo do arquivo
        fileToUpdate.setFileContent(dados.fileContent());
        fileToUpdate.setCommit(lastCommit);

        Files updatedFile = repository.save(fileToUpdate);

        DadosListagemFiles response = new DadosListagemFiles(
                updatedFile.getIdFile(),
                updatedFile.getFileName(),
                updatedFile.getFileType(),
                updatedFile.getFileContent(),
                project.getIdProjects(),
                lastCommit.getCommitMessage()
        );

        return ResponseEntity.ok(response);
    }


}

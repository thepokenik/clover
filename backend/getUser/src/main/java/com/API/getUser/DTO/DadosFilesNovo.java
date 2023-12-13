package com.API.getUser.DTO;

import com.API.getUser.models.files.Files;

public record DadosFilesNovo(String fileName, String fileType, byte[] fileContent) {
    public DadosFilesNovo (Files files){
        this(files.getFileName(), files.getFileType(), files.getFileContent());
    }
}

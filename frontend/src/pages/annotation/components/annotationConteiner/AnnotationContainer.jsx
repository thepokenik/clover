import React, { useCallback, useEffect, useState } from "react";

import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

import { updateAnnotation } from "../utils/updateAnnotation/UpdateAnnotation";
import { fetchAnnotation } from "./utils/FetchAnnotation";
import { deleteAnnotaion } from "./utils/deleteAnnotaion/deleteAnnotaion";
import { CardTitle } from "@/components/ui/card";
import { DropdownMenuAnnotation } from "./components/dropdownMenu/DropdownMenuAnnotation";

const AnnotationContainer = ({ quillRef, selectedAnnotation, idProject, handlePostNewAnnotation }) => {
    const token = localStorage.getItem('token');
    var [content, setContent] = useState({
        annotationContent: selectedAnnotation.annotationContent,
    });

    // Define uma função para buscar a anotação quando selectedAnnotation mudar
    const fetchAnnotationAndUpdateContent = useCallback(async () => {
        await fetchAnnotation(token, selectedAnnotation.idAnnotation, setContent);
    }, [token, selectedAnnotation.idAnnotation]);

    // Chama a função sempre que selectedAnnotation mudar
    useEffect(() => {
        fetchAnnotationAndUpdateContent();
    }, [fetchAnnotationAndUpdateContent]);


    const handleSaveAnnotation = async () => {
        // Usa diretamente o estado atualizado na função de atualização
        await updateAnnotation(token, content, selectedAnnotation.idAnnotation, idProject);
    }

    useEffect(() => {
        fetchAnnotation(token, selectedAnnotation.idAnnotation, setContent);
    }, []);


    const handleChange = (value) => {
        setContent({ annotationContent: value });
    };

    const handleDeleteAnnotation = async () => {
        await deleteAnnotaion(idProject, token, selectedAnnotation.idAnnotation);
    }

    return (
        <>
            <div className="nav-annotation">
                <CardTitle>{selectedAnnotation.title}</CardTitle>
                <DropdownMenuAnnotation
                    handleSaveAnnotation={handleSaveAnnotation}
                    handleDeleteAnnotation={handleDeleteAnnotation}
                    handlePostNewAnnotation={handlePostNewAnnotation}
                />
            </div>

            <div className="annotation-content text-black">
                <ReactQuill
                    className="reactQuill"
                    ref={quillRef}
                    theme="snow"
                    value={content.annotationContent}
                    onChange={handleChange}
                    modules={{
                        toolbar: [
                            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
                            ['bold', 'italic', 'underline', 'strike'],
                            [{ 'align': [] }],
                            [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                            [{ 'indent': '-1' }, { 'indent': '+1' }],
                            ['blockquote', 'code-block'],
                            ['link', 'image', 'video'],
                            ['clean'],
                        ],
                    }}
                />
            </div>
        </>
    )
}

export default AnnotationContainer;
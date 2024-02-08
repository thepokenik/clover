import React, { useEffect, useState } from "react"

import fileIcon from '../../assets/fileIcon.png'
import { calculateTimeDifference } from "../../../home/components/utils/calculateTimeDifference/CalculateTimeDifference"
import { getCommitsByFiles } from "../utils/getCommitsByFiles/GetCommitsByFiles";

const FileContent = ({ item, token }) => {


    const [commitsRequest, setCommitsRequest] = useState([]);

    useEffect(() => {
        getCommits();
    }, []);

    const getCommits = async () => {
        await getCommitsByFiles(token, item.idFile, setCommitsRequest);
    }

    return (
        <div className="file-folder-content">
            <div className="file-folder">
                <img src={fileIcon} alt="fileIcon" />
                <span>{item.fileName}</span>
            </div>
            <span>{commitsRequest && commitsRequest.length > 0 && commitsRequest[commitsRequest.length - 1].commitMessage}</span>
            <span>{calculateTimeDifference(item.creationFile, true, true, true, false, false)}</span>
        </div>
    )
}

export default FileContent
import { toast } from 'sonner';
export const deleteAnnotaion = async (idProject, token, id) => {
    try {
        const response = await fetch(`http://localhost:8080/projects/annotations/${idProject}/delete/${id}`, {
            method: "DELETE",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/json",
                'Authorization': `Bearer ${token}`,
            },
        });

        if (response.ok) {
            console.log("sucess delete")
            toast.success("Sucess!", {
                description: "Annotation deleted successfully!",
            });
        } else if (response.status === 404) {
            console.log("Request not found");
            // Handle the case where the request was not found
        } else {
            console.log("An unexpected error occurred: " + response.status);
        }
    } catch (error) {
        console.log("Error fetching the request:", error);
    }
}
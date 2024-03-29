'use client'
import { useParams } from "next/navigation";

export default function ArticleDetail() {
    
    const params = useParams();
    
    
    return (

        <h1>
            Article Detail{params.id} Page
        </h1>

    );
}
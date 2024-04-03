'use client'
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function ArticleDetail() {
    
    const params = useParams();

    const [article, setArticle] = useState({})

    useEffect(() => {
        fetch(`http://localhost:8090/api/v1/articles/${params.id}`)
                                .then(row => row.json())
                                .then(row => setArticle(row.data.article))
    }, [])
    
    
    return (
        <h1>
            Article Detail{params.id} Page
            <div>
                {article.id} / {article.title} / {article.content}
            </div>
        </h1>
    );
}
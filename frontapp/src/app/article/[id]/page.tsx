'use client'
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function ArticleDetail() {
    
    const params = useParams();

    const [article, setArticle] = useState([])
    useEffect(() => {
        getData()
    }, [])


    const getData = async() => {
        const result = await fetch("http://localhost:8090/api/v1/articles/"+params).then(row => row.json());
        setArticle(result.data.articles)
        console.log(result.data.articles)
    }
    
    
    return (

        <h1>
            Article Detail{params.id} Page
            <div>
                {article.id} {article.title} {article.content}
            </div>
        </h1>

    );
}
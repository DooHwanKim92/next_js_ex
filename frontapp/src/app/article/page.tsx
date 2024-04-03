'use client'

import Link from "next/link"
import { useEffect, useState } from "react"

export default function Article () {
    
    const [articles, setArticles] = useState([])
    
    useEffect(() => {
        fetch("http://localhost:8090/api/v1/articles")
            .then(row => row.json())
            .then(row => setArticles(row.data.articles))
    }, [articles])

    return (
        <>  
            <ArticleForm />
            <ul>
                게시물 리스트
                {articles.map((article) => <li> <Link href={`/article/${article.id}`}>{article.id}</Link> / {article.title} / {article.content}</li>)}
            </ul>
        </>
    )
}

function ArticleForm () {

    const [article, setArticle] = useState({title: '', content: ''})

    const handleSubmit = async  (e) => {
        e.preventDefault();

        const response = await fetch("http://localhost:8090/api/v1/articles", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(article)
        });

        if (response.ok) {
            alert('게시물이 성공적으로 등록되었습니다.');
        } else {
            alert('게시물 등록에 실패했습니다.');
        }

    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setArticle({ ...article, [name]: value });
        console.log({ ...article, [name]: value })
    }


    return (
        <>
            <form onSubmit={handleSubmit}>
                <input type="text" name="title" value={article.title} onChange={handleChange} />
                <input type="text" name="content" value={article.content} onChange={handleChange}/>
                <button type="submit">등록</button>
            </form>
        </>
    )
}
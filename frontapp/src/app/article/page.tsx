'use client'

import Link from "next/link"
import { useEffect, useState } from "react"

export default function Article () {

    const [articles, setArticles] = useState([])
    
    useEffect(() => {
        fetchArticle()
    }, [])

    const fetchArticle = () => {
        fetch("http://localhost:8090/api/v1/articles")
            .then(row => row.json())
            .then(row => setArticles(row.data.articles))
    }

    return (
        <>  
            <ArticleForm fetchArticle={fetchArticle} />
            <ul>
                게시물 리스트
                {articles.map((article) => <li> <Link href={`/article/${article.id}`}>{article.id}</Link> / {article.title}
                                                
                                                
                                            </li>)}
            </ul>
        </>
    )
}

// 게시글 등록
function ArticleForm ({fetchArticle}) {

    const [article, setArticle] = useState({title: '', content: ''})

    const handleSubmit = async (e) => {
        e.preventDefault();
        // 기존 button의 submit 기능을 막고 아래 함수를 실행시킨다.

        const response = await fetch("http://localhost:8090/api/v1/articles", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(article)
        });

        if (response.ok) {
            alert('게시물이 성공적으로 등록되었습니다.');
            fetchArticle()
            // 변경된 articleList 재정렬, Article()에서 매개변수로 받아온 함수
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
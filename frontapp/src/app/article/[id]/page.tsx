'use client'
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import Article from "../page";

export default function ArticleDetail() {
    
    const params = useParams();

    const [clicked, setClicked] = useState(true)

    const [article, setArticle] = useState({})

    const fetchArticle = () => {
        fetch(`http://localhost:8090/api/v1/articles/${params.id}`)
                                .then(row => row.json())
                                .then(row => setArticle(row.data.article))
    }

    // 게시글 삭제
    const deleteArticle = async (e) => {
        e.preventDefault();
        // 기존 button의 submit 기능을 막고 아래 함수를 실행시킨다.

        const response = await fetch(`http://localhost:8090/api/v1/articles/${params.id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(article)
        });

        if (response.ok) {
            alert('게시물 삭제 완료.');
            window.location.assign('http://localhost:3000/article');
        } else {
            alert('게시물 삭제 실패.');
        }

    }

    useEffect(() => {
        fetchArticle()
        modifyArticle()
    }, [])

    const modifyArticle = () => {
        (clicked ? setClicked(false) : setClicked(true))
    }
    
    return (
        <>
            Article Detail{params.id} Page
            <div>
                {article.id} / {article.title} / {article.content}
                <button onClick={modifyArticle}>수정</button>
                <button onClick={deleteArticle}>삭제</button>
                    <div>
                        {clicked ? <ArticleModify fetchArticle={fetchArticle}/> : null}
                    </div>
            </div>
        </>
    );
}

// 게시글 수정
function ArticleModify ({fetchArticle}) {

    const params = useParams();

    const [article, setArticle] = useState({title: '', content: ''})

    const handleSubmit = async (e) => {
        e.preventDefault();
        // 기존 button의 submit 기능을 막고 아래 함수를 실행시킨다.

        const response = await fetch(`http://localhost:8090/api/v1/articles/${params.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(article)
        });

        if (response.ok) {
            alert('게시물 수정 완료.');
            fetchArticle()
        } else {
            alert('게시물 수정 실패.');
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
                <button type="submit">수정</button>
            </form>
        </>
    )
}
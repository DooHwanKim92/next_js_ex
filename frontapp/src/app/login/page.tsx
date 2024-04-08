'use client'
import { useRouter } from "next/navigation";
import { useState } from "react"

export default function LogIn() {

    const router = useRouter();

    const [member, setMember] = useState({username: '', password: ''})

    const handleSubmit = async (e) => {
        e.preventDefault();

        const response = await fetch("http://localhost:8090/api/v1/members/login", {
            method: 'POST',
            // ↓ 브라우저 cookie에 token 값을 전달함
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(member)
        });

        if (response.ok) {
            alert('로그인 성공');
            router.push("/")
        } else {
            alert('로그인 실패');
        }

    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setMember({ ...member, [name]: value });
        console.log({ ...member, [name]: value })
    }

    return (
        <>
            로그인해라
            <form onSubmit={handleSubmit}>
                <div>아이디
                    <input type="text" name="username" value={member.username} onChange={handleChange}></input>
                </div>
                <div>비밀번호
                    <input type="password" name="password" value={member.password} onChange={handleChange}></input>
                </div>
                <button>로그인</button>
            </form>
        </>
    )
}
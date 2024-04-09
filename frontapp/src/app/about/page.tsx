'use client'

import { useEffect, useState } from "react";

export default function About() {

    const [member, setMember] = useState({});

    useEffect(() => {
        fetch('http://localhost:8090/api/v1/members/me', {
            method: 'GET',
            credentials: 'include', // 핵심 변경점
        })
        .then(result => result.json())
        .then(result => setMember(result.data.memberDto))
    }, [])



    return (

        <>
        <h5>소개 페이지</h5>
            <ul>
                <li>{member.id}</li>
                <li>{member.username}</li>
                <li>{member.email}</li>
            </ul>
        </>
        
        
    );
  }

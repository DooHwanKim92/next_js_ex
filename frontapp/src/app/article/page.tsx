import Link from "next/link";

export default function Article() {
    return (

        <h1>
            Article Page
            <div>
                <Link href="/article/post">POST</Link>
            </div>
            <div>
                <Link href="/article/me">ME</Link>
            </div>
            
        </h1>

    );
}
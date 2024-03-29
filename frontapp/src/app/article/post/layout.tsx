export default function ArticlePostLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <div>
            <h1>Article POST Layout</h1>
            {children}
        </div>
    );
}
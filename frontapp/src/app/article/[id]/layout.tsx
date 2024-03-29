export default function ArticleDetailLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <div>
            <h1>Article Detail Layout</h1>
            {children}
        </div>
    );
}
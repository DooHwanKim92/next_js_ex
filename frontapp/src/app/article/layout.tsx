export default function ArticleLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <div>
            <h1>Article Layout</h1>
            {children}
        </div>
    );
}
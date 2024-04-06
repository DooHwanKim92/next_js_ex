export default function ArticleDetailLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <div>
            Article Detail Layout
            {children}
        </div>
    );
}
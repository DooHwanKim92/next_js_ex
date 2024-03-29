export default function ArticleMeLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <div>
            <h1>Article ME Layout</h1>
            {children}
        </div>
    );
}
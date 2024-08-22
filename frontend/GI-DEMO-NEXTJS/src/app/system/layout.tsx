import { Inter } from "next/font/google";
import { PrimeReactProvider } from "primereact/api";

const inter = Inter({ subsets: ["latin"] });

export default function AdminLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <PrimeReactProvider>
      <div className="col-12 bg-blue-500 h-5rem"></div>
      <div>{children}</div>
    </PrimeReactProvider>
  );
}

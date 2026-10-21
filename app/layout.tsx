import { Metadata } from "next";
import { Kumbh_Sans } from "next/font/google";
import "./globals.css";

const kumbhSans = Kumbh_Sans({
  subsets: ["latin"],
  variable: "--font-kumbh-sans",
});

export const metadata: Metadata = {
  title: "Smart Wallet",
  description: "Smart Wallet",
};

export default function RootLayout({ children }: LayoutProps<"/">) {
  return (
    <html lang="pt-BR" className={`${kumbhSans.variable} font-kumbh`}>
      <body>{children}</body>
    </html>
  );
}

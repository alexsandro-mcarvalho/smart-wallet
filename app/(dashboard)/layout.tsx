import { Header } from "./_components/header";
import SideBar from "./_components/sidebar";

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex min-h-screen w-screen flex-row">
      <SideBar />
      <div className="flex-1">
        <Header />
        <main className="">{children}</main>
      </div>
    </div>
  );
}

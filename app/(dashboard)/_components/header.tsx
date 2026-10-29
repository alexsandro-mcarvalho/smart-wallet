"use client";

import { usePathname } from "next/navigation";

const pageTitles: Record<string, string> = {
  "/": "Dashboard",
  "/transactions": "Transações",
};

export function Header() {
  const pathname = usePathname();
  const title = pageTitles[pathname];

  return (
    <header>
      <div className="flex justify-between px-10 py-8">
        <h1 className="text-2xl font-semibold">{title}</h1>
        <div>
          <span>perfil</span>
        </div>
      </div>
    </header>
  );
}

"use client";

import { usePathname } from "next/navigation";
import Link from "next/link";
import { LayoutGridCircles, Wallet, LogOut, type LucideIcon } from "lucide-react";

type LinkElement = {
  label: string;
  href: string;
  icon: LucideIcon;
};

const navItems = [
  {
    label: "Dashboard",
    href: "/",
    icon: LayoutGridCircles,
  },
  {
    label: "Transações",
    href: "/transactions",
    icon: Wallet,
  },
];

const support = [
  {
    label: "Sair",
    href: "/login",
    icon: LogOut,
  },
];
export default function SideBar() {
  const pathname = usePathname();

  const renderNavItems = (item: LinkElement) => {
    const Icon = item.icon;

    const isActive = pathname === item.href;

    return (
      <li key={item.href}>
        <Link
          href={item.href}
          aria-current={isActive ? "page" : undefined}
          className={`flex items-center gap-2 rounded-lg p-4 text-sm font-medium transition-colors duration-300 ${isActive ? "bg-lime-300 text-text-1" : "text-text-3"}`}
        >
          <Icon size={20} />
          {item.label}
        </Link>
      </li>
    );
  };

  return (
    <div className="flex w-3xs flex-col gap-4 bg-gray-100">
      <div className="flex h-full flex-col justify-between px-5 py-12">
        <nav>
          <ul>{navItems.map(renderNavItems)}</ul>
        </nav>
        <div>
          <ul>{support.map(renderNavItems)}</ul>
        </div>
      </div>
    </div>
  );
}

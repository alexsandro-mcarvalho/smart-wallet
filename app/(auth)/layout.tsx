import Image from "next/image";

export default function AuthLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex h-screen">
      <div className="flex flex-1 flex-col justify-center p-36">{children}</div>

      <div>
        <Image
          src="/images/auth_background.png"
          alt="auth background"
          width={675}
          height={900}
          className="h-full w-auto"
        />
      </div>
    </div>
  );
}

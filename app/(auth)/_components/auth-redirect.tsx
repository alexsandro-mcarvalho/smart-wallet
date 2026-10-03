import Link from "next/link";

export default function AuthRedirect({
  description,
  redirectText,
  redirectUrl,
}: {
  description: string;
  redirectText: string;
  redirectUrl: string;
}) {
  return (
    <p className="text-center text-sm font-normal text-gray-500 mt-4">
      {description}{" "}
      <Link href={redirectUrl} className="font-medium text-black">
        {redirectText}
      </Link>
    </p>
  );
}

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
    <p className="mt-4 text-center text-sm font-normal text-gray-600">
      {description}{" "}
      <Link href={redirectUrl} className="font-medium text-black">
        {redirectText}
      </Link>
    </p>
  );
}

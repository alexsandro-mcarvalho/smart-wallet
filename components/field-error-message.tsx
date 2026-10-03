export default function FieldErrorMessage({ message }: { message?: string }) {
  if (!message) return null;
  return <p className="pl-2 text-sm text-red-500">{message}</p>;
}

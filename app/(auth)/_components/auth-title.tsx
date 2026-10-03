export default function AuthTitle({ title, description }: { title: string; description: string }) {
  return (
    <div className="mb-8">
      <h1 className="mb-2 text-3xl font-semibold text-text-1">{title}</h1>
      <p className="text-base font-normal text-text-3">{description}</p>
    </div>
  );
}

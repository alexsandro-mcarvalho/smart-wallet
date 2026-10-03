import Label from "./label";

type FormFieldProps = {
  label: string;
  htmlFor: string;
  children: React.ReactNode;
};

export default function FormField({ children, label, htmlFor }: FormFieldProps) {
  return (
    <div className="flex flex-col gap-2">
      <Label htmlFor={htmlFor}>{label}</Label>
      {children}
    </div>
  );
}

type InputVariant = "primary";

type InputProps = React.ComponentProps<"input"> & {
  variant?: InputVariant;
};
export default function Input({ variant = "primary", ...props }: InputProps) {
  return (
    <input
      className="border-gray-200 block w-full rounded-lg border px-5 py-4 font-medium text-text-3 transition-colors duration-300 outline-none focus:border-blue-400"
      {...props}
    />
  );
}

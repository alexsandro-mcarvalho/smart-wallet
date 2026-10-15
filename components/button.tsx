type ButtonProps = React.ComponentProps<"button"> & {
  variant?: "primary" | "secondary";
};

const buttonVariants = {
  primary:
    "bg-lime-300 cursor-pointer  font-base font-semibold text-text-1 enabled:hover:bg-lime-400 duration-300 transition-colors",
  secondary: "bg-gray-200 text-gray-700 hover:bg-gray-300 disabled:cursor-not-allowed disabled:bg-gray-400",
};

export default function Button({ children, variant = "primary", ...props }: ButtonProps) {
  return (
    <button
      {...props}
      className={`rounded-lg px-4 py-3 disabled:cursor-not-allowed disabled:opacity-50 ${buttonVariants[variant]}`}
    >
      {children}
    </button>
  );
}

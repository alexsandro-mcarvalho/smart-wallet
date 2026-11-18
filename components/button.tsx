import { cn } from "cn";

const buttonVariants = {
  primary: "bg-lime-300 text-text-1 enabled:hover:bg-lime-400 duration-300 transition-colors",
  secondary: "bg-gray-200 text-gray-700 hover:bg-gray-300 disabled:cursor-not-allowed disabled:bg-gray-400",
};

const buttonSizes = {
  sm: "text-sm font-medium",
  md: "text-base font-semibold",
};

export type ButtonVariant = keyof typeof buttonVariants;
export type ButtonSize = keyof typeof buttonSizes;

export type ButtonProps = React.ComponentProps<"button"> & {
  variant?: ButtonVariant;
  size?: ButtonSize;
};

export default function Button({ children, variant = "primary", size = "md", className, ...props }: ButtonProps) {
  return (
    <button
      {...props}
      className={cn(
        "cursor-pointer rounded-lg px-4 py-3 disabled:cursor-not-allowed disabled:opacity-50",
        buttonVariants[variant],
        buttonSizes[size],
        className,
      )}
    >
      {children}
    </button>
  );
}

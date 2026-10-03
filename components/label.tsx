type LabelProps = React.ComponentProps<"label"> & {
  children: React.ReactNode;
};

export default function Label({ children, ...props }: LabelProps) {
  return (
    <label className="text-sm font-medium text-text-1" {...props}>
      {children}
    </label>
  );
}

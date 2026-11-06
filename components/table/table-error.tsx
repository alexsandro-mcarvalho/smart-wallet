import { TableCell, TableRow } from "@/components/ui/table";

type TableErrorProps = {
  colSpan: number;
  message: string;
};

export default function TableError({ colSpan, message }: TableErrorProps) {
  return (
    <TableRow>
      <TableCell colSpan={colSpan} className="h-32 text-center">
        <p role="alert" className="text-red-600">
          {message}
        </p>
      </TableCell>
    </TableRow>
  );
}

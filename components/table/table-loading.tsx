import { LoaderCircle } from "lucide-react";
import { TableCell, TableRow } from "@/components/ui/table";

type TableLoadingProps = {
  colSpan: number;
  message?: string;
};

export default function TableLoading({ colSpan, message = "Carregando..." }: TableLoadingProps) {
  return (
    <TableRow>
      <TableCell colSpan={colSpan} className="h-32 text-center">
        <div role="status" className="flex items-center justify-center gap-2 text-gray-600">
          <LoaderCircle className="h-5 w-5 animate-spin" aria-hidden="true" />
          <span className="sr-only">{message}</span>
        </div>
      </TableCell>
    </TableRow>
  );
}

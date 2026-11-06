import { TableCell, TableRow } from "@/components/ui/table";

type TableEmptyProps = {
  colSpan: number;
};

export default function TableEmpty({ colSpan }: TableEmptyProps) {
  return (
    <TableRow>
      <TableCell colSpan={colSpan} className="h-32 text-center text-gray-600">
        <p role="status">Não há nenhuma transação.</p>
      </TableCell>
    </TableRow>
  );
}

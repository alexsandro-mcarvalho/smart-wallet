import Image from "next/image";
import { TableCell } from "@/components/ui/table";

type TableAssetCellProps = {
  logoUrl: string;
  name: string;
  symbol: string;
};

export default function TableAssetCell({ logoUrl, name, symbol }: TableAssetCellProps) {
  return (
    <TableCell>
      <div className="flex items-center gap-3">
        <Image src={logoUrl} alt={name} width={40} height={40} className="h-10 w-10 rounded-sm object-contain" />
        <div className="flex flex-col gap-1">
          <span className="text-sm font-medium text-text-1">{name}</span>
          <span className="text-xs font-light text-gray-600">{symbol}</span>
        </div>
      </div>
    </TableCell>
  );
}
